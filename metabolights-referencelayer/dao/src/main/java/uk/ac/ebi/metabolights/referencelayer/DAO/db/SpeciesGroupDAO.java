/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 31/01/14 09:31
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.DAO.db;


import org.hamcrest.Matchers;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.IDAO.ISpeciesGroupDAO;
import uk.ac.ebi.metabolights.referencelayer.model.Species;
import uk.ac.ebi.metabolights.referencelayer.model.SpeciesGroup;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static ch.lambdaj.Lambda.*;


public class SpeciesGroupDAO extends AbstractDAO implements ISpeciesGroupDAO {

	private static GenericIdentityMap<SpeciesGroup> identityMap = new GenericIdentityMap<SpeciesGroup>();

    public SpeciesGroupDAO(Connection connection) throws IOException {
        super(connection);
        setUp(this.getClass());
    }
	@Override
	public SpeciesGroup getById(Long speciesGroupId) throws DAOException {

		// This mathod does not make sense to me anymore after adding childrens.
		// But I'll leave it here just in case, not populating the children (since it will lead to a recursive load)
        LOGGER.warn("SpeciesGroup children are not populated, neither species collection");

        // Try to get it from the identity map...
        SpeciesGroup spg =  identityMap.getEntity(speciesGroupId);

       // If not loaded yet
       if (spg == null){
           // It must return an array of one speciesGroup....get the first one and only.
           Collection<SpeciesGroup> speciesGroup = findBy("--where.speciesgroup.by.id", speciesGroupId);
           spg =  (speciesGroup ==null? null:speciesGroup.iterator().next());

       }

       return spg;
	}

    @Override
    public SpeciesGroup getByName(String speciesGroupName) throws DAOException {

		// This mathod does not make sense to me anymore after adding childrens.
		// But I'll leave it here just in case, not populating the children (since it will lead to a recursive load)
		LOGGER.warn("SpeciesGroup children are not populated, neither species collection");

		// It must return an array of one speciesGroup....get the first one and only.
        Collection<SpeciesGroup> speciesGroup = findBy("--where.speciesgroup.by.name", speciesGroupName);
        SpeciesGroup spg = (speciesGroup == null? null:speciesGroup.iterator().next());

        return spg;
    }

	@Override
    public Set<SpeciesGroup> getAll() throws DAOException {

		Set<SpeciesGroup> result = findBy("--where.speciesgroup.all",null);

		// Assign children ...
		AssignSpecies(result);

		// Assign species
		AssignChildren(result);

		return result;

	}


    private void AssignSpecies(Set<SpeciesGroup> speciesGroups) throws DAOException {

		// go through the species group set...
		for (SpeciesGroup sg : speciesGroups)
		{
			// Get all the children...
			Collection<Species> specieses = DAOFactory.getSpeciesDAO().findByGroupId(sg.getId());

			// If children is not null
			if (specieses != null)
			{
				sg.setSpecieses(specieses);
			}
		}


	}

	private void AssignChildren(Set<SpeciesGroup> speciesGroups){

		// go through the species group set...
		for (SpeciesGroup sg : speciesGroups)
		{
			// Get all the children...
			List<SpeciesGroup> children = filter(having(on(SpeciesGroup.class).getParentId(), Matchers.equalTo(sg.getId())),speciesGroups);

			// If children is not null
			if (children != null)
			{
				sg.setChildren(children);
			}
		}
	}

	private Set <SpeciesGroup> findBy(String where, Object value)
	throws DAOException {
		ResultSet rs = null;
		try {

			PreparedStatement stm = sqlLoader.getPreparedStatement("--speciesgroup.core", where);
			stm.clearParameters();

			if (value != null){
				// If can be casted as long
				if (value instanceof Long){
					stm.setLong(1, (Long) value);
				} else {
					stm.setString(1, (String) value);
				}
			}

			rs = stm.executeQuery();

			// Load all speciesGroups
			return loadSpeciesGroups(rs);

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

	private Set<SpeciesGroup> loadSpeciesGroups(ResultSet rs) throws SQLException{

		Set<SpeciesGroup> result = null;
		while (rs.next()){

			if (result == null) result = new HashSet<SpeciesGroup>();
			SpeciesGroup speciesGroup = loadSpeciesGroup(rs);
			result.add(speciesGroup);
		}
		return (result == null)? null : result;

	}

	private SpeciesGroup loadSpeciesGroup(ResultSet rs) throws SQLException {
		SpeciesGroup spg = null;

		// It should have a valid record
		spg = new SpeciesGroup();
		long id = rs.getLong("ID");
		String name = rs.getString("NAME");
		long parentId = rs.getLong("PARENT_ID");

		spg.setId(id);
		spg.setName(name);
		spg.setParentId(parentId);

        // Add the speciesGroup to the identity map
        identityMap.addEntity(spg);

		return spg;
	}

	public void save(SpeciesGroup spg) throws DAOException {

		// If its a new speciesGroup
		if (spg.getId() == null) {
			insert (spg);
		} else {
			update(spg);
		}

	}

	/**
	 * Updates core data concerning only to the speciesGroup
	 * @param spg
	 * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
	 */
	public void update(SpeciesGroup spg) throws DAOException {
		try {

			PreparedStatement stm = sqlLoader.getPreparedStatement("--update.speciesgroup");
			stm.clearParameters();
			stm.setString(1, spg.getName());
			stm.setLong(2, spg.getId());
			stm.setLong(3,spg.getParentId());
			stm.executeUpdate();

		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}

	/**
	 * Inserts a new speciesGroup into the speciesGroup
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void insert(SpeciesGroup spg) throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--insert.speciesgroup", new String[]{"id"}, null);
			stm.clearParameters();
			stm.setString(1, spg.getName());
			stm.setLong(2,spg.getParentId());
			stm.executeUpdate();

			ResultSet keys = stm.getGeneratedKeys();

       		while (keys.next()) {
    			spg.setId(keys.getLong(1));  //Should only be one
    	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("insertIntoSpeciesGroup: speciesGroup inserted with id:" +spg.getId());
    		}

       		keys.close();

            // Add speciesGroup to the identity map
            identityMap.addEntity(spg);

		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}

	/**
	 * Deletes a speciesGroup from the speciesGroup
	 * <br>
	 * @throws java.sql.SQLException
	 */
	public void delete(SpeciesGroup spg) throws DAOException {

		// Delete the speciesGroup
		deleteSpeciesGroup(spg);
	}

	/**
	 * Deletes a speciesGroup from the speciesGroup
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void deleteSpeciesGroup(SpeciesGroup spg)	throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--delete.speciesgroup", "--where.speciesgroup.by.id");
			stm.clearParameters();
			stm.setLong(1, spg.getId());
			stm.executeUpdate();

	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("SpeciesGroup deleted with id:" +spg.getId());

       		identityMap.removeEntity(spg);


		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
}
