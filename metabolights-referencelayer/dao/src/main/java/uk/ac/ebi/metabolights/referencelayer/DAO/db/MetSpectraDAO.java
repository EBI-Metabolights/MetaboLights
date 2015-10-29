/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 10/06/13 14:39
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.DAO.db;


import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.IDAO.IMetSpectraDAO;
import uk.ac.ebi.metabolights.referencelayer.model.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class MetSpectraDAO extends AbstractDAO implements IMetSpectraDAO {

    private AttributeDAO atd;

	/**
	 * @param connection to the Spectra
	 * @throws java.io.IOException
	 */
	public MetSpectraDAO(Connection connection) throws IOException{
		super(connection);
        setUp(this.getClass());

        this.atd = new AttributeDAO(connection);
	}


    /**
     * Setter for MetSpectra connection. It also sets the same connection
     * for the underlying objects.<br>
     * This method should be used with pooled connections, and only when the
     * previous one and its prepared statements have been properly closed
     * (returned).
     * @param con
     * @throws java.sql.SQLException
     */
	public void setConnection(Connection con) throws SQLException{
		this.setConnection(con);
        this.atd.setConnection(con);
	}

    public Spectra findBySpectraId(Long spectraId) throws DAOException {


        // It must return an array of one database....get the first one and only.
        Collection<Spectra> spectras = findBy("--where.metspectra.by.id", spectraId);
        return  (spectras ==null? null:spectras.iterator().next());


    }

    public Collection<Spectra> findByMetId(Long MetId) throws DAOException {

       // It must return an array of one Spectra....get the first one and only.
       Collection<Spectra> spectras = findBy("--where.metspectra.by.metid", MetId);

       return spectras;
	}

	private Collection <Spectra> findBy(String where, Object value)
	throws DAOException {
		ResultSet rs = null;
		try {

			PreparedStatement stm = sqlLoader.getPreparedStatement("--metspectra.core", where);
			stm.clearParameters();

			// If can be casted as long
			if (value instanceof Long){
				stm.setLong(1, (Long) value);
			} else {
				stm.setString(1, (String) value);
			}

			rs = stm.executeQuery();

			// Load all Spectra
			return loadMetSpectras(rs);

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

	private Set<Spectra> loadMetSpectras(ResultSet rs) throws SQLException, DAOException {

		Set<Spectra> result = new HashSet<Spectra>();

        while (rs.next()){

			Spectra spectra = loadSpectra(rs);
			result.add(spectra);
		}
		return result;

	}

	private Spectra loadSpectra(ResultSet rs) throws SQLException, DAOException {


        long id = rs.getLong("ID");
        String name = rs.getString("NAME");
        String pathToJson = rs.getString("PATH_TO_JSON");
        Spectra.SpectraType st = Spectra.SpectraType.valueOf(rs.getString("SPECTRA_TYPE"));

        Spectra spectra = new Spectra(id, pathToJson,name,st);

        loadChildren(spectra);

		return spectra;
	}

    private void loadChildren (Spectra spectra) throws DAOException {

        // Load attributes
        Collection<Attribute> attributes = atd.findBySpectraId(spectra.getId());

        spectra.getAttributes().addAll(attributes);


    }


    public void save(Spectra spectra, MetaboLightsCompound compound) throws DAOException {

        // Validate:
        // Name must exists
        if (spectra.getName() == null || spectra.getName().equals("")){
            String msg = "Spectra can't be saved without name";
            LOGGER.error(msg);
            throw new DAOException(msg);
        }

        // Path to jason must exists
        if (spectra.getPathToJsonSpectra() == null || spectra.getPathToJsonSpectra().equals("")){
            String msg = "Spectra can't be saved without PathToJsonfile";
            LOGGER.error(msg);
            throw new DAOException(msg);
        }

        // Name must exists
        if (spectra.getSpectraType() == null){
            String msg = "Spectra can't be saved without a type";
            LOGGER.error(msg);
            throw new DAOException(msg);
        }

        // Compound must exist
        if (compound == null ){
            String msg = "Spectra can't be saved without a compound object associated";
            LOGGER.error(msg);
            throw new DAOException(msg);
        }

		// If its a new Spectra
		if (spectra.getId() == null) {
			insert (spectra,compound);
		} else {
			update(spectra,compound);
		}

        // Now save the rest...cascade saving...
        saveAttributes(spectra);


    }

    private void saveAttributes(Spectra spectra) throws DAOException {

        for (Attribute attribute: spectra.getAttributes()){
            atd.saveSpectraAttribute(attribute, spectra);
        }

    }

	/**
	 * Updates core data concerning only to the Spectra
	 * @param spectra
	 * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
	 */
	private void update(Spectra spectra, MetaboLightsCompound compound ) throws DAOException {
		try {

			PreparedStatement stm = sqlLoader.getPreparedStatement("--update.metspectra");
			stm.clearParameters();
			stm.setLong(1,compound.getId());
            stm.setString(2, spectra.getName());
			stm.setString(3, spectra.getPathToJsonSpectra().getAbsolutePath());
            stm.setString(4, spectra.getSpectraType().toString());
            stm.setLong(5,spectra.getId());

			stm.executeUpdate();

		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}

	/**
	 * Inserts a new Spectra into the Spectras table
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void insert(Spectra spectra, MetaboLightsCompound compound) throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--insert.metspectra", new String[]{"ID"}, null);
			stm.clearParameters();
            stm.setLong(1, compound.getId());
            stm.setString(2, spectra.getName());
            stm.setString(3, spectra.getPathToJsonSpectra().getAbsolutePath());
            stm.setString(4, spectra.getSpectraType().toString());

			stm.executeUpdate();

			ResultSet keys = stm.getGeneratedKeys();

       		while (keys.next()) {
    			spectra.setId(keys.getLong(1));  //Should only be one
    	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("insertIntoSpectra: Spectra inserted with id:" + spectra.getId());
    		}

       		keys.close();


		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}

	/**
	 * Deletes a Spectra from the Spectra
	 * <br>
	 * @throws java.sql.SQLException
	 */
	public void delete(Spectra db) throws DAOException {

		// Delete the Spectra
		deleteSpectra(db);
	}

	/**
	 * Deletes a Spectra from the Spectra
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void deleteSpectra(Spectra db)	throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--delete.metspectra", "--where.metspectra.by.id");
			stm.clearParameters();
			stm.setLong(1, db.getId());
			stm.executeUpdate();

	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("Spectra deleted with id:" +db.getId());


		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
}