/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/02/14 14:37
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.DAO.db;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.IDAO.ISpeciesDAO;
import uk.ac.ebi.metabolights.referencelayer.model.Species;
import uk.ac.ebi.metabolights.referencelayer.model.SpeciesMembers;

import java.io.IOException;
import java.sql.*;
import java.util.*;


public class SpeciesDAO extends AbstractDAO implements ISpeciesDAO{

	private static Logger LOGGER = LoggerFactory.getLogger(SpeciesDAO.class);
	private static GenericIdentityMap<Species> identityMap = new GenericIdentityMap<Species>();
    private List<String> autoCompleteList;

	/**
	 * @param connection to the Species
	 * @throws java.io.IOException
	 */
	public SpeciesDAO(Connection connection) throws IOException{
		super(connection);
		setUp(this.getClass());
	}


	public Species findBySpeciesId(Long SpeciesId) throws DAOException {

        // Try to get it from the identity map...
        Species sp =  identityMap.getEntity(SpeciesId);

       // If not loaded yet
       if (sp == null){
           // It must return an array of one Species....get the first one and only.
           Collection<Species> Species = findBy("--where.species.by.id", SpeciesId);
           sp =  (Species ==null? null:Species.iterator().next());

       }

       return sp;
	}

    public Species findBySpeciesTaxon(String taxon) throws DAOException {

        // It must return an array of one Species....get the first one and only.
        Collection<Species> Species = findBy("--where.species.by.taxon", taxon);

        Species sp =  (Species ==null? null:Species.iterator().next());

        return sp;

    }

    public Species findBySpeciesName(String speciesName) throws DAOException {

        // It must return an array of one Species....get the first one and only.
        Collection<Species> Species = findBy("--where.species.by.species", speciesName);

        Species sp =  (Species ==null? null:Species.iterator().next());

        return sp;
    }


    @Override
    public List<String> getAllNamesForAutoComplete() throws DAOException, SQLException {

        if (autoCompleteList == null){
            autoCompleteList  = new ArrayList<String>();
            ResultSet rs = null;
            PreparedStatement statement = sqlLoader.getPreparedStatement("--all.autocomplete");
            rs = statement.executeQuery();

            while (rs.next()){
                String speciesName = null;
                speciesName = rs.getString("species");
                if (speciesName != null)
                    autoCompleteList.add(speciesName + ",");
            }

        }

        return autoCompleteList;
    }


    public Set<Species> findAll() throws DAOException {

		return findBy("--where.species.all",null);
	}

	public Collection<Species> findByGroupId(long groupId) throws DAOException {


		return findBy("--where.species.by.group", groupId);
	}

	public Set<Species> findWithoutSpeciesMember() throws DAOException {

		return findBy("--where.species.withoutspeciesmember",null);
	}

	public Set<Species> findWithSpeciesMember() throws DAOException {

		return findBy("--where.species.withspeciesmember",null);
	}

	private Set <Species> findBy(String where, Object value)
	throws DAOException {
		ResultSet rs = null;
		try {

			PreparedStatement stm = sqlLoader.getPreparedStatement("--species.core", where);
			stm.clearParameters();

            // If value is not null
            if (value != null){
                // If can be casted as long
                if (value instanceof Long){
                    stm.setLong(1, (Long) value);
                } else {
                    stm.setString(1, (String) value);
                }

            }


			rs = stm.executeQuery();

			// Load all Speciess
			return loadSpeciess(rs);

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

	private Set<Species> loadSpeciess(ResultSet rs) throws SQLException, DAOException {

		Set<Species> result = null;
		while (rs.next()){

			if (result == null) result = new HashSet<Species>();
			Species Species = loadSpecies(rs);
			result.add(Species);
		}
		return (result == null)? null : result;

	}

	private Species loadSpecies(ResultSet rs) throws SQLException, DAOException {
		Species sp;

		// It should have a valid record
		sp = new Species();
		long id = rs.getLong("ID");
        String species = rs.getString("SPECIES");
		String description = rs.getString("DESCRIPTION");
        String taxon = rs.getString("TAXON");
		long speciesMemberId = rs.getLong("SPECIES_MEMBER");


		// Get the referenced objects
		SpeciesMembers spm = DAOFactory.getSpeciesMembersDAO().getById(speciesMemberId);


		sp.setId(id);
        sp.setSpecies(species);
		sp.setDescription(description);
        sp.setTaxon(taxon);
		sp.setSpeciesMember(spm);

        // Add the Species to the identity map
        identityMap.addEntity(sp);

		return sp;
	}

	public void save(Species sp) throws DAOException {


		// Before saving the species, we might need to save the species member.
		if (sp.getSpeciesMember() != null && sp.getSpeciesMember().getId() == null) DAOFactory.getSpeciesMembersDAO().save(sp.getSpeciesMember());

		// If its a new Species
		if (sp.getId() == null) {
			insert (sp);
		} else {
			update(sp);
		}

	}

	/**
	 * Updates core data concerning only to the Species
	 * @param sp
	 * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
	 */
	private void update(Species sp ) throws DAOException {
		try {

			PreparedStatement stm = sqlLoader.getPreparedStatement("--update.species");
			stm.clearParameters();
			stm.setString(1, sp.getSpecies());
            stm.setString(2, sp.getDescription());
            stm.setString(3, sp.getTaxon());
			if (sp.getSpeciesMember() !=null ){
				stm.setLong(4, sp.getSpeciesMember().getId());
			} else {
				stm.setNull(4, Types.INTEGER);
			}
			stm.setLong(5, sp.getId());
			stm.executeUpdate();

		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}

	/**
	 * Inserts a new Species into the Species
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void insert(Species sp) throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--insert.species", new String[]{"ID"}, null);
			stm.clearParameters();
			stm.setString(1, sp.getSpecies());
            stm.setString(2, sp.getDescription());
            stm.setString(3, sp.getTaxon());
			if (sp.getSpeciesMember() !=null ){
				stm.setLong(4, sp.getSpeciesMember().getId());
			} else {
				stm.setNull(4, Types.INTEGER);
			}

			stm.executeUpdate();

			ResultSet keys = stm.getGeneratedKeys();

       		while (keys.next()) {
    			sp.setId(keys.getLong(1));  //Should only be one
    	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("insertIntoSpecies: Species inserted with id:" +sp.getId());
    		}

       		keys.close();

            // Add Species to the identity map
            identityMap.addEntity(sp);

		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}

	/**
	 * Deletes a Species from the Species
	 * <br>
	 * @throws java.sql.SQLException
	 */
	public void delete(Species sp) throws DAOException {

		// Delete the Species
		deleteSpecies(sp);
	}

	/**
	 * Deletes a Species from the Species
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void deleteSpecies(Species sp)	throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--delete.species");
			stm.clearParameters();
			stm.setLong(1, sp.getId());
			stm.executeUpdate();

            LOGGER.debug("Species deleted with id:" +sp.getId());

       		identityMap.removeEntity(sp);


		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
}
