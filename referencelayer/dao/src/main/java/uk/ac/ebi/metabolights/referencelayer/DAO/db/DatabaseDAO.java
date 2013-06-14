package uk.ac.ebi.metabolights.referencelayer.DAO.db;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import uk.ac.ebi.biobabel.util.db.SQLLoader;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.IDAO.IDatabaseDAO;
import uk.ac.ebi.metabolights.referencelayer.domain.Database;


public class DatabaseDAO extends AbstractDAO implements IDatabaseDAO {
	

    public DatabaseDAO (Connection connection) throws IOException {
        super(connection);
        setUp(this.getClass());
    }

	public Database findByDatabaseId(Long databaseId) throws DAOException {

        // Try to get it from the identity map...
        Database db =  DatabaseIdentityMap.getDatabase(databaseId);

       // If not loaded yet
       if (db == null){
           // It must return an array of one database....get the first one and only.
           Collection<Database> database = findBy("--where.database.by.id", databaseId);
           db =  (database ==null? null:database.iterator().next());

       }

       return db;
	}

    @Override
    public Database findByDatabaseName(String databaseName) throws DAOException {

        // It must return an array of one database....get the first one and only.
        Collection<Database> database = findBy("--where.database.by.name", databaseName);
        Database db =  (database ==null? null:database.iterator().next());

        return db;
    }

    public Set<Database> findAll() throws DAOException {
	
		return findBy("--where.database.all",1);
	}

	private Set <Database> findBy(String where, Object value)
	throws DAOException {
		ResultSet rs = null;
		try {

			PreparedStatement stm = sqlLoader.getPreparedStatement("--database.core", where);
			stm.clearParameters();
			
			// If can be casted as long
			if (value instanceof Long){
				stm.setLong(1, (Long) value);
			} else {
				stm.setString(1, (String) value);
			}

			rs = stm.executeQuery();
			
			// Load all databases
			return loadDatabases(rs);
			
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
	
	private Set<Database> loadDatabases(ResultSet rs) throws SQLException{
		
		Set<Database> result = null;
		while (rs.next()){
			
			if (result == null) result = new HashSet<Database>();
			Database database = loadDatabase(rs);
			result.add(database);
		}
		return (result == null)? null : result;
		
	}

	private Database loadDatabase(ResultSet rs) throws SQLException {
		Database db = null;
	
		// It should have a valid record
		db = new Database();
		long id = rs.getLong("ID");
		String name = rs.getString("DB_NAME");
		
		db.setId(id);
		db.setName(name);

        // Add the database to the identity map
        DatabaseIdentityMap.addDatabase(db);

		return db;
	}

	public void save(Database db) throws DAOException {
		
		// If its a new database
		if (db.getId() == 0) {
			insert (db);
		} else {
			update(db);
		}
		
	}
	
	/**
	 * Updates core data concerning only to the database 
	 * @param db
	 * @throws DAOException
	 */
	private void update(Database db ) throws DAOException {
		try {
		
			PreparedStatement stm = sqlLoader.getPreparedStatement("--update.database");
			stm.clearParameters();
			stm.setString(1, db.getName());
			stm.setLong(2, db.getId());
			stm.executeUpdate();
	
		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
	
	/**
	 * Inserts a new database into the database
	 * <br>
	 * @throws SQLException
	 */
	private void insert(Database db) throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--insert.database", new String[]{"ID"}, null);
			stm.clearParameters();
			stm.setString(1, db.getName());
			stm.executeUpdate();
	
			ResultSet keys = stm.getGeneratedKeys();
			
       		while (keys.next()) {
    			db.setId(keys.getLong(1));  //Should only be one 
    	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("insertIntoDatabase: database inserted with id:" +db.getId()); 
    		}
    		
       		keys.close();

            // Add database to the identity map
            DatabaseIdentityMap.addDatabase(db);
       		
		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
	
	/**
	 * Deletes a database from the database 
	 * <br>
	 * @throws SQLException
	 */
	public void delete(Database db) throws DAOException {
		
		// Delete the database
		deleteDatabase(db);
	}

	/**
	 * Deletes a database from the database
	 * <br>
	 * @throws SQLException
	 */
	private void deleteDatabase(Database db)	throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--delete.database", "--where.database.by.id");
			stm.clearParameters();
			stm.setLong(1, db.getId());
			stm.executeUpdate();
	
	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("database deleted with id:" +db.getId());
    		
       		DatabaseIdentityMap.removeDatabase(db);


		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
}