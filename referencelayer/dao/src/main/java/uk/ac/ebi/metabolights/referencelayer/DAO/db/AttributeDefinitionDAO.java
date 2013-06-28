package uk.ac.ebi.metabolights.referencelayer.DAO.db;


import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.IDAO.IAttributeDefinitionDAO;
import uk.ac.ebi.metabolights.referencelayer.domain.AttributeDefinition;
import uk.ac.ebi.metabolights.referencelayer.domain.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class AttributeDefinitionDAO extends AbstractDAO implements IAttributeDefinitionDAO {


    public AttributeDefinitionDAO(Connection connection) throws IOException {
        super(connection);
        setUp(this.getClass());
    }

	public AttributeDefinition findByAttributeDefinitionId(Long attributeDefinitionId) throws DAOException {

        // Try to get it from the identity map...
        AttributeDefinition ad =  AttributeDefinitionIdentityMap.getAttributeDefinition(attributeDefinitionId);

       // If not loaded yet
       if (ad == null){
           // It must return an array of one attributeDefinition....get the first one and only.
           Collection<AttributeDefinition> attributeDefinition = findBy("--where.attributedefinition.by.id", attributeDefinitionId);
           ad =  (attributeDefinition ==null? null:attributeDefinition.iterator().next());

       }

       return ad;
	}

    @Override
    public AttributeDefinition findByAttributeDefinitionName(String attributeDefinitionName) throws DAOException {

        // It must return an array of one attributeDefinition....get the first one and only.
        Collection<AttributeDefinition> attributeDefinition = findBy("--where.attributedefinition.by.name", attributeDefinitionName);

        return   (attributeDefinition ==null? null:attributeDefinition.iterator().next());
    }

    public Set<AttributeDefinition> findAll() throws DAOException {

		return findBy("--where.attributedefinition.all",1);
	}

	private Set <AttributeDefinition> findBy(String where, Object value)
	throws DAOException {
		ResultSet rs = null;
		try {

			PreparedStatement stm = sqlLoader.getPreparedStatement("--attributedefinition.core", where);
			stm.clearParameters();

			// If can be casted as long
			if (value instanceof Long){
				stm.setLong(1, (Long) value);
			} else {
				stm.setString(1, (String) value);
			}


			rs = stm.executeQuery();

			// Load all databases
			return loadAtributeDefinitions(rs);

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

	private Set<AttributeDefinition> loadAtributeDefinitions(ResultSet rs) throws SQLException{

		Set<AttributeDefinition> result = null;
		while (rs.next()){

			if (result == null) result = new HashSet<AttributeDefinition>();
			AttributeDefinition attributeDefinition = loadAttributeDefinition(rs);
			result.add(attributeDefinition);
		}
		return (result == null)? null : result;

	}

	private AttributeDefinition loadAttributeDefinition(ResultSet rs) throws SQLException {

        AttributeDefinition attributeDefinition = null;

		// It should have a valid record
		attributeDefinition = new AttributeDefinition();
		long id = rs.getLong("ID");
		String name = rs.getString("NAME");
        String description = rs.getString("DESCRIPTION");

		attributeDefinition.setId(id);
		attributeDefinition.setName(name);
        attributeDefinition.setDescription(description);

        // Add the attributeDefinition to the identity map
        AttributeDefinitionIdentityMap.addAttributeDefinition(attributeDefinition);

		return attributeDefinition;
	}

	public void save(AttributeDefinition attributeDefinition) throws DAOException {

		// If its a new attributedefinition
		if (attributeDefinition.getId() == 0) {
			insert (attributeDefinition);
		} else {
			update(attributeDefinition);
		}

	}

	/**
	 * Updates core data concerning only to the attributedefinition
	 * @param attributeDefinition
	 * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
	 */
	private void update(AttributeDefinition attributeDefinition ) throws DAOException {
		try {

			PreparedStatement stm = sqlLoader.getPreparedStatement("--update.attributedefinition");
			stm.clearParameters();
			stm.setString(1, attributeDefinition.getName());
            stm.setString(2, attributeDefinition.getDescription());
			stm.setLong(3, attributeDefinition.getId());
			stm.executeUpdate();

		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}

	/**
	 * Inserts a new database into the database
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void insert(AttributeDefinition attributeDefinition) throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--insert.attributedefinition", new String[]{"ID"}, null);
			stm.clearParameters();
			stm.setString(1, attributeDefinition.getName());
            stm.setString(2, attributeDefinition.getDescription());
			stm.executeUpdate();

			ResultSet keys = stm.getGeneratedKeys();

       		while (keys.next()) {
    			attributeDefinition.setId(keys.getLong(1));  //Should only be one
    	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("insertInto AttributeDefinition: attributeDefinition inserted with id:" +attributeDefinition.getId());
    		}

       		keys.close();

            // Add attributedefinition to the identity map
            AttributeDefinitionIdentityMap.addAttributeDefinition(attributeDefinition);

		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}

	/**
	 * Deletes an attributeDefinition from the database
	 * <br>
	 * @throws java.sql.SQLException
	 */
	public void delete(AttributeDefinition attributeDefinition) throws DAOException {

		// Delete the attributeDefinition
		deleteAttributeDefinition(attributeDefinition);
	}

	/**
	 * Deletes an attributeDefinition from the database
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void deleteAttributeDefinition(AttributeDefinition attributeDefinition)	throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--delete.attributedefinition", "--where.attributedefinition.by.id");
			stm.clearParameters();
			stm.setLong(1, attributeDefinition.getId());
			stm.executeUpdate();
	
	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("attributedefinition deleted with id:" + attributeDefinition.getId());
    		
       		AttributeDefinitionIdentityMap.removeAttributeDefinition(attributeDefinition);


		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
}