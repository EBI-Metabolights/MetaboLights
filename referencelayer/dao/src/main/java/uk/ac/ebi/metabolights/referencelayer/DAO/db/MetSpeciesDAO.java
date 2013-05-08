package uk.ac.ebi.metabolights.referencelayer.DAO.db;


import org.apache.log4j.Logger;
import uk.ac.ebi.biobabel.util.db.SQLLoader;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.IDAO.IMetSpeciesDAO;
import uk.ac.ebi.metabolights.referencelayer.domain.CrossReference;
import uk.ac.ebi.metabolights.referencelayer.domain.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.domain.MetaboLightsCompound;
import uk.ac.ebi.metabolights.referencelayer.domain.Species;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class MetSpeciesDAO implements IMetSpeciesDAO{
	

	private Logger LOGGER = Logger.getLogger(MetSpeciesDAO.class);
	
	protected Connection con;
	protected SQLLoader sqlLoader;

    private CrossReferenceDAO crd;
    private SpeciesDAO spd;

	
	/**
	 * @param connection to the MetSpecies
	 * @throws java.io.IOException
	 */
	public MetSpeciesDAO(Connection connection) throws IOException{
		this.con = connection;
		this.sqlLoader = new SQLLoader(this.getClass(), con);
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
		this.con = con;
		sqlLoader.setConnection(con);
        this.crd.setConnection(con);
        this.spd.setConnection(con);
	}

	@Override
	protected void finalize() throws Throwable {
        super.finalize();
		close();
	}

	/**
	 * Closes prepared statements, but not the connection.
	 * If you want to close the connection or return it to a pool,
	 * please call explicitly the method {@link java.sql.Connection#close()} or
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
     * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException while closing the compound reader.
     * @throws java.sql.SQLException while setting the compound reader connection to
     *      null.
     */
	public void returnPooledConnection() throws DAOException, SQLException{
		
		close();
		if (con != null){
			con.close();
			con = null;
		}
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
        if (metSpecies.getCrossReference().getId() == 0) crd.save(metSpecies.getCrossReference());
        if (metSpecies.getSpecies().getId() == 0) spd.save(metSpecies.getSpecies());

		// If its a new MetSpecies
		if (metSpecies.getId() == 0) {
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
			PreparedStatement stm = sqlLoader.getPreparedStatement("--insert.metspecies", new String[]{"ID"}, null);
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