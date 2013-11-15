/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 20/06/13 14:18
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.DAO.db;


import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.IDAO.ISpeciesMembersDAO;
import uk.ac.ebi.metabolights.referencelayer.domain.SpeciesGroup;
import uk.ac.ebi.metabolights.referencelayer.domain.SpeciesMembers;
import uk.ac.ebi.metabolights.referencelayer.domain.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SpeciesMembersDAO extends AbstractDAO implements ISpeciesMembersDAO{

    private SpeciesGroupDAO spgd;
	private static GenericIdentityMap<SpeciesMembers> identityMap = new GenericIdentityMap<SpeciesMembers>();

	/**
	 * @param connection to the Database
	 * @throws java.io.IOException
	 */
	public SpeciesMembersDAO(Connection connection) throws IOException{

        super(connection);
        setUp(this.getClass());
        this.spgd = new SpeciesGroupDAO(connection);

	}

	@Override
    public SpeciesMembers getById(Long speciesMemberId) throws DAOException {

        // Try to get it from the identity map...
        SpeciesMembers cr =  identityMap.getEntity(speciesMemberId);

        // If not loaded yet
        if (cr == null){
            // It must return an array of one crossreference....get the first one and only.
            Collection<SpeciesMembers> spms = getBy("--where.speciesmembers.by.id", speciesMemberId);
            cr =  (spms.size()==0? null:spms.iterator().next());

        }

        return cr;

    }

    @Override
    public SpeciesMembers getByTaxon(String taxon) throws DAOException {

        Collection<SpeciesMembers> spms = getBy("--where.speciesmembers.by.taxon", taxon);
        return   (spms.size()==0? null:spms.iterator().next());
    }

	@Override
	public Collection<SpeciesMembers> getAllBySpeciesGroup(Long speciesGroupId) throws DAOException {


		Collection<SpeciesMembers> spms = getBy("--where.speciesmembers.by.groupid", speciesGroupId);
		return spms;
	}


	private Collection <SpeciesMembers> getBy(String where, Object value)
	throws DAOException {
		ResultSet rs = null;
		try {

			PreparedStatement stm = sqlLoader.getPreparedStatement("--speciesmembers.core", where);
			stm.clearParameters();

			// If can be casted as long
			if (value instanceof Long){
				stm.setLong(1, (Long) value);
			} else {
				stm.setString(1, (String) value);
			}

			rs = stm.executeQuery();

			// Load all MetSpecies
			return loadSpeciesMembers(rs);

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

	private Set<SpeciesMembers> loadSpeciesMembers(ResultSet rs) throws SQLException, DAOException {

		Set<SpeciesMembers> result = new HashSet<SpeciesMembers>();

        while (rs.next()){

			SpeciesMembers cr = loadSpeciesMember(rs);
			result.add(cr);
		}
		return result;

	}

	private SpeciesMembers loadSpeciesMember(ResultSet rs) throws SQLException, DAOException {


        long id = rs.getLong("ID");
		Long spgId = rs.getLong("GROUP_ID");
		String taxon = rs.getString("TAXON");
		String taxon_desc = rs.getString("TAXON_DESC");

        // Get the referenced objects
        SpeciesGroup spg = spgd.getById(spgId);

        SpeciesMembers spm = new SpeciesMembers();
		spm.setId(id);
		spm.setSpeciesGroup(spg);
		spm.setTaxon(taxon);
        spm.setTaxonDesc(taxon_desc);


        // Add the speciesmembers to the identity map
        identityMap.addEntity(spm);

        return spm;
	}

	public void save(SpeciesMembers speciesMember) throws DAOException {

        // Validate:
        // SpeciesMembers must exist
        if (speciesMember.getSpeciesGroup() == null ){
            String msg = "SpeciesMembers can't be saved without a Species group object associated";
            LOGGER.error(msg);
            throw new DAOException(msg);
        }

        // Before saving the SpeciesMembers data we need to save the foreign key entities if apply
        if (speciesMember.getSpeciesGroup().getId() == 0) spgd.save(speciesMember.getSpeciesGroup());

		// If its a new SpeciesMembers
		if (speciesMember.getId() == 0) {
			insert (speciesMember);
		} else {
			update(speciesMember);
		}

	}

	/**
	 * Updates core data concerning only to the SpeciesMembers
	 * @param speciesMember
	 * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
	 */
	private void update(SpeciesMembers speciesMember ) throws DAOException {
		try {

			PreparedStatement stm = sqlLoader.getPreparedStatement("--update.speciesmembers");
			stm.clearParameters();

            stm.setLong(1, speciesMember.getSpeciesGroup().getId());
			stm.setString(2, speciesMember.getTaxon());
			stm.setString(3, speciesMember.getTaxonDesc());
			stm.setLong(4, speciesMember.getId());
			stm.executeUpdate();

		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}

	/**
	 * Inserts a new SpeciesMembers into the REF_XREF table
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void insert(SpeciesMembers speciesMember) throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--insert.speciesmembers", new String[]{"ID"}, null);
			stm.clearParameters();
			stm.setLong(1, speciesMember.getSpeciesGroup().getId());
			stm.setString(2, speciesMember.getTaxon());
			stm.setString(3, speciesMember.getTaxonDesc());

			stm.executeUpdate();

			ResultSet keys = stm.getGeneratedKeys();

       		while (keys.next()) {
    			speciesMember.setId(keys.getLong(1));  //Should only be one
    	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("insertIntoSpeciesMembers: SpeciesMembers inserted with id:" + speciesMember.getId());
    		}

       		keys.close();

            // Add the speciesmembers to the identity map
            identityMap.addEntity(speciesMember);


		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}

	/**
	 * Deletes a SpeciesMembers from the REF_XREF table
	 * <br>
	 * @throws java.sql.SQLException
	 */
	public void delete(SpeciesMembers speciesMember) throws DAOException {

		// Delete the SpeciesMembers
		deleteSpeciesMembers(speciesMember);

        // Delete the speciesmembers from the identity map
        identityMap.removeEntity(speciesMember);

    }

	/**
	 * Deletes a MetSpecies from the MetSpecies
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void deleteSpeciesMembers(SpeciesMembers speciesMember)	throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--delete.speciesmembers");
			stm.clearParameters();
			stm.setLong(1, speciesMember.getId());
			stm.executeUpdate();

	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("speciesmembers deleted with id:" + speciesMember.getId());


		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
}