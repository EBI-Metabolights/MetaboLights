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
import uk.ac.ebi.metabolights.referencelayer.IDAO.IRefEntryDAO;
import uk.ac.ebi.metabolights.referencelayer.domain.RefEntry;


class RefEntryDAO{// implements IRefEntryDAO{
	

	private Logger LOGGER = Logger.getLogger(RefEntryDAO.class);
	
	protected Connection con;
	protected SQLLoader sqlLoader;
	
	/**
	 * @param connection to the database
	 * @throws IOException
	 */
	public RefEntryDAO(Connection connection) throws IOException{
		this.con = connection;
		this.sqlLoader = new SQLLoader(this.getClass(), con);
	}


    /**
     * Setter for database connection. It also sets the same connection
     * for the underlying objects.<br>
     * This method should be used with pooled connections, and only when the
     * previous one and its prepared statements have been properly closed
     * (returned).
     * @param con
     * @throws SQLException
     */
	public void setConnection(Connection con) throws SQLException{
		this.con = con;
		sqlLoader.setConnection(con);
	}

	@Override
	protected void finalize() throws Throwable {
        super.finalize();
		close();
	}

	/**
	 * Closes prepared statements, but not the connection.
	 * If you want to close the connection or return it to a pool,
	 * please call explicitly the method {@link Connection#close()} or
	 * {@link #returnPooledConnection()} respectively.
	 */
	public void close() throws DAOException {
        try {
            sqlLoader.close();
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
	}
	
	/**
	 * Closes (returns to the pool) prepared statements and connection.
	 * This method should be called explicitly before finalising this object,
	 * in case its connection belongs to a pool.
	 * <br>
	 *      *
     * @throws DAOException while closing the compound reader.
     * @throws SQLException while setting the compound reader connection to
     *      null.
     */
	public void returnPooledConnection() throws DAOException, SQLException{
		
		close();
		if (con != null){
			con.close();
			con = null;
		}
	}


	public RefEntry findByrefEntryId(Long refEntryId) throws DAOException {
		
		// It must return an array of one RefEntry....get the first one and only.
		Collection<RefEntry> refEntries = findBy("--where.refentry.by.id", refEntryId); 
		return refEntries ==null? null:refEntries.iterator().next();
	}

	private Set <RefEntry> findBy(String where, Object value)
	throws DAOException {
		ResultSet rs = null;
		try {

			PreparedStatement stm = sqlLoader.getPreparedStatement("--refEntry.core", where);
			stm.clearParameters();
			
			// If can be casted as long
			if (value instanceof Long){
				stm.setLong(1, (Long) value);
			} else {
				stm.setString(1, (String) value);
			}
			
			
			rs = stm.executeQuery();
			
			// Load all databases
			return loadRefEntrys(rs);
			
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
	
	private Set<RefEntry> loadRefEntrys(ResultSet rs) throws SQLException{
		
		Set<RefEntry> result = null;
		while (rs.next()){
			
			if (result == null) result = new HashSet<RefEntry>();
			RefEntry refEntry = loadRefEntry(rs);
			result.add(refEntry);
		}
		return (result == null)? null : result;
		
	}

	private RefEntry loadRefEntry(ResultSet rs) throws SQLException {
		RefEntry refEntry = null;
	
		// It should have a valid record
		refEntry = new RefEntry();
		long id = rs.getLong("ID");
		String name = rs.getString("DB_NAME");
		
		refEntry.setId(id);
		//refEntry.setName(name);
	
		return refEntry;
	}

	public void save(RefEntry refEntry) throws DAOException {
		
		// If its a new database
		if (refEntry.getId() == 0) {
			insert (refEntry);
		} else {
			update(refEntry);
		}
		
	}
	
	/**
	 * Updates core data concerning only to the database 
	 * @param refEntry
	 * @throws DAOException
	 */
	private void update(RefEntry refEntry ) throws DAOException {
		try {
		
			PreparedStatement stm = sqlLoader.getPreparedStatement("--update.database");
			stm.clearParameters();
			//stm.setString(1, refEntry.getName());
			stm.setLong(2, refEntry.getId());
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
	private void insert(RefEntry refEntry) throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--insert.database", new String[]{"ID"}, null);
			stm.clearParameters();
			//stm.setString(1, refEntry.getName());
			stm.executeUpdate();
	
			ResultSet keys = stm.getGeneratedKeys();
			
       		while (keys.next()) {
    			refEntry.setId(keys.getLong(1));  //Should only be one 
    	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("insertIntoRefEntry: database inserted with id:" +refEntry.getId()); 
    		}
    		
       		keys.close();
       		
		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
	
	/**
	 * Deletes a database from the database 
	 * <br>
	 * @throws SQLException
	 */
	public void delete(RefEntry refEntry) throws DAOException {
		
		// Delete the database
		deleteRefEntry(refEntry);
	}

	/**
	 * Deletes a database from the database
	 * <br>
	 * @throws SQLException
	 */
	private void deleteRefEntry(RefEntry refEntry)	throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--delete.database", "--where.database.by.id");
			stm.clearParameters();
			stm.setLong(1, refEntry.getId());
			stm.executeUpdate();
	
	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("database deleted with id:" +refEntry.getId()); 
    		
       		
		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
}