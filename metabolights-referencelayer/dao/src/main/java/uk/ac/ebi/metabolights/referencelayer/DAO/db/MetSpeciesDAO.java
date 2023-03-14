/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/06/13 16:08
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.DAO.db;


import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.IDAO.IMetSpeciesDAO;
import uk.ac.ebi.metabolights.referencelayer.model.CrossReference;
import uk.ac.ebi.metabolights.referencelayer.model.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.model.MetaboLightsCompound;
import uk.ac.ebi.metabolights.referencelayer.model.Species;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class MetSpeciesDAO extends AbstractDAO implements IMetSpeciesDAO{

    private CrossReferenceDAO crd;
    private SpeciesDAO spd;


	/**
	 * @param connection to the MetSpecies
	 * @throws java.io.IOException
	 */
	public MetSpeciesDAO(Connection connection) throws IOException{
		super(connection);
        setUp(this.getClass());

        this.crd = new CrossReferenceDAO(connection);
        this.spd = new SpeciesDAO(connection);

	}


    /**
     * Setter for MetSpecies connection. It also sets the same connection
     * for the underlying objects.<br>
     * This method should be used with pooled connections, and only when the
     * previous one and its prepared statements have been properly closed
     * (returned).
     * @param con
     * @throws java.sql.SQLException
     */
	public void setConnection(Connection con) throws SQLException{
		this.setConnection(con);
		this.crd.setConnection(con);
        this.spd.setConnection(con);
	}

	public Collection<MetSpecies> findByMetId(Long MetId) throws DAOException {

       // It must return an array of one MetSpecies....get the first one and only.
       Collection<MetSpecies> metSpecies = findBy("--where.metspecies.by.metid", MetId);

       return metSpecies;
	}

	private Collection <MetSpecies> findBy(String where, Object value)
	throws DAOException {
		ResultSet rs = null;
		try {

			PreparedStatement stm = sqlLoader.getPreparedStatement("--metspecies.core", where);
			stm.clearParameters();

			// If can be casted as long
			if (value instanceof Long){
				stm.setLong(1, (Long) value);
			} else {
				stm.setString(1, (String) value);
			}

			rs = stm.executeQuery();

			// Load all MetSpecies
			return loadMetSpeciess(rs);

        } catch (SQLException e){
           throw new DAOException(e);
		} finally {
			if (rs != null) try {
                rs.close();
            } catch (SQLException ex) {
                LOGGER.error("Closing ResultSet", ex);
            }
		}
	}

	private Set<MetSpecies> loadMetSpeciess(ResultSet rs) throws SQLException, DAOException {

		Set<MetSpecies> result = new HashSet<MetSpecies>();

        while (rs.next()){

			MetSpecies MetSpecies = loadMetSpecies(rs);
			result.add(MetSpecies);
		}
		return result;

	}

	private MetSpecies loadMetSpecies(ResultSet rs) throws SQLException, DAOException {


        long id = rs.getLong("ID");
		long species_id = rs.getLong("SPECIES_ID");
		long cr_id = rs.getLong("REF_XREF_ID");

        // Get the referenced objects
        CrossReference cr = crd.findByCrossReferenceId(cr_id);
        Species sp = spd.findBySpeciesId(species_id);

        MetSpecies metSpecies = new MetSpecies(id, sp, cr);

		return metSpecies;
	}

	public void save(Species species, MetaboLightsCompound compound, CrossReference crossReference) throws DAOException {

		// Validate:
		// CrossReference must exist
		if (crossReference == null ){
			String msg = "MetSpecies can't be saved without a CrossReference object associated";
			LOGGER.error(msg);
			throw new DAOException(msg);
		}


		// Species must exist
		if (species.getId() == null ){
			String msg = "MetSpecies can't be saved without a Species object associated";
			LOGGER.error(msg);
			throw new DAOException(msg);
		}

		// Compound must exist
		if (compound == null ){
			String msg = "MetSpecies can't be saved without a compound object associated";
			LOGGER.error(msg);
			throw new DAOException(msg);
		}

		insert(species,compound,crossReference);

	}

	/**
	 * Inserts a new MetSpecies into the MetSpecies
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void insert(Species species, MetaboLightsCompound compound, CrossReference crossReference) throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--insert.metspecies", new String[]{"id"}, (Object)null);
			stm.clearParameters();
			stm.setLong(1,compound.getId());
			stm.setLong(2, species.getId());
			stm.setLong(3, crossReference.getId());
			stm.executeUpdate();

			ResultSet keys = stm.getGeneratedKeys();

			keys.close();

		} catch (SQLException ex) {
			throw new DAOException(ex);
		}
	}

	public void save(MetSpecies metSpecies, MetaboLightsCompound compound) throws DAOException {

        // Validate:
        // CrossReference must exist
        if (metSpecies.getCrossReference() == null ){
            String msg = "MetSpecies can't be saved without a CrossReference object associated";
            LOGGER.error(msg);
            throw new DAOException(msg);
        }


        // Species must exist
        if (metSpecies.getSpecies() == null ){
            String msg = "MetSpecies can't be saved without a Species object associated";
            LOGGER.error(msg);
            throw new DAOException(msg);
        }

        // Compound must exist
        if (compound == null ){
            String msg = "MetSpecies can't be saved without a compound object associated";
            LOGGER.error(msg);
            throw new DAOException(msg);
        }


        // Before saving the MetSpecies data we need to save the foreign key entities if apply
        // We are assuming the compound it's been saved and the compound DAO is the ona calling this method
        if (metSpecies.getCrossReference().getId() == null) crd.save(metSpecies.getCrossReference());
        if (metSpecies.getSpecies().getId() == null) spd.save(metSpecies.getSpecies());

		// If its a new MetSpecies
		if (metSpecies.getId() == null) {
			insert (metSpecies,compound);
		} else {
			update(metSpecies,compound);
		}

	}

	/**
	 * Updates core data concerning only to the MetSpecies
	 * @param metSpecies
	 * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
	 */
	private void update(MetSpecies metSpecies, MetaboLightsCompound compound ) throws DAOException {
		try {

			PreparedStatement stm = sqlLoader.getPreparedStatement("--update.metspecies");
			stm.clearParameters();
			stm.setLong(1,compound.getId());
            stm.setLong(2, metSpecies.getSpecies().getId());
			stm.setLong(3, metSpecies.getCrossReference().getId());
            stm.setLong(4, metSpecies.getId());

			stm.executeUpdate();

		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}

	/**
	 * Inserts a new MetSpecies into the MetSpecies
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void insert(MetSpecies metSpecies, MetaboLightsCompound compound) throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--insert.metspecies", new String[]{"id"}, (Object)null);
			stm.clearParameters();
            stm.setLong(1,compound.getId());
            stm.setLong(2, metSpecies.getSpecies().getId());
            stm.setLong(3, metSpecies.getCrossReference().getId());
			stm.executeUpdate();

			ResultSet keys = stm.getGeneratedKeys();

       		while (keys.next()) {
    			metSpecies.setId(keys.getLong(1));  //Should only be one
    	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("insertIntoMetSpecies: MetSpecies inserted with id:" + metSpecies.getId());
    		}

       		keys.close();


		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}

	/**
	 * Deletes a MetSpecies from the MetSpecies
	 * <br>
	 * @throws java.sql.SQLException
	 */
	public void delete(MetSpecies db) throws DAOException {

		// Delete the MetSpecies
		deleteMetSpecies(db);
	}

	/**
	 * Deletes a MetSpecies from the MetSpecies
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void deleteMetSpecies(MetSpecies db)	throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--delete.metspecies", "--where.metspecies.by.id");
			stm.clearParameters();
			stm.setLong(1, db.getId());
			stm.executeUpdate();

	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("MetSpecies deleted with id:" +db.getId());


		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
}
