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
import uk.ac.ebi.metabolights.referencelayer.IDAO.IMetaboLightsCompoundDAO;
import uk.ac.ebi.metabolights.referencelayer.domain.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.domain.MetaboLightsCompound;


public class MetaboLightsCompoundDAO implements IMetaboLightsCompoundDAO{
	

	private Logger LOGGER = Logger.getLogger(MetaboLightsCompoundDAO.class);
	
	protected Connection con;
	protected SQLLoader sqlLoader;
    private  MetSpeciesDAO msd;
	
	/**
	 * @param connection to the database
	 * @throws IOException
	 */
	public MetaboLightsCompoundDAO(Connection connection) throws IOException{
		this.con = connection;
		this.sqlLoader = new SQLLoader(this.getClass(), con);
        this.msd = new MetSpeciesDAO(connection);
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

	public MetaboLightsCompound findByCompoundName(String name) throws DAOException {

        Collection<MetaboLightsCompound> compounds = findByCompound("--where.compound.by.name",name);
        return compounds ==null? null:compounds.iterator().next();
	}


	public MetaboLightsCompound findByCompoundAccession(String accession) throws DAOException {

        Collection<MetaboLightsCompound> compounds = findByCompound("--where.compound.by.accession",accession);
        return compounds ==null? null:compounds.iterator().next();
	}

	public MetaboLightsCompound findByCompoundId(Long compoundId) throws DAOException {

        Collection<MetaboLightsCompound> compounds = findByCompound("--where.compound.by.id", compoundId);;
        return compounds ==null? null:compounds.iterator().next();
	}

    public Set<MetaboLightsCompound> getAllCompounds() throws DAOException {

        return findByCompound("--where.compound.all",null);
    }


    public boolean doesCompoundExists(Long compoundId) throws DAOException {

        ResultSet rs = null;
        try {
            PreparedStatement stm = sqlLoader.getPreparedStatement("--exist.compound", "--where.compound.by.id");
            stm.clearParameters();

            stm.setLong(1, compoundId);

            rs = stm.executeQuery();

            // Move to the first and only row
            rs.next();

            return (rs.getInt(1)!=0);

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

    private Set<MetaboLightsCompound> findByCompound(String where, Object value)
	throws DAOException {
		ResultSet rs = null;
		try {
			MetaboLightsCompound result = null;
			PreparedStatement stm = sqlLoader.getPreparedStatement("--compound.core", where);
			stm.clearParameters();

            // If not null...
            if (value != null){
                // If can be casted as long
                if (value instanceof Long){
                    stm.setLong(1, (Long) value);
                } else {
                    stm.setString(1, (String) value);
                }
            }

			rs = stm.executeQuery();

			return loadCompounds(rs);
			
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

    private Set<MetaboLightsCompound> loadCompounds(ResultSet rs) throws SQLException, DAOException {

        Set<MetaboLightsCompound> result = null;
        while (rs.next()){

            if (result == null) result = new HashSet<MetaboLightsCompound>();
            MetaboLightsCompound compound = loadCompound(rs);
            result.add(compound);
        }
        return (result == null)? null : result;

    }


    public void save(MetaboLightsCompound compound) throws DAOException {
		
		// If its a new Compound
		if (compound.getId() == 0) {
			insert (compound);
		} else {
			update(compound);
		}
		
		// Now save the rest...cascade saving...
		saveMetSpecies(compound);
	}
	
	private void saveMetSpecies(MetaboLightsCompound compound) throws DAOException {

        for (MetSpecies ms: compound.getMetSpecies()){
            msd.save(ms, compound);
        }

    }
	/**
	 * Deletes a compound from the database and all the children
	 * <br>
	 * @throws SQLException
	 */
	public void delete(MetaboLightsCompound compound) throws DAOException {

        // Need to delete associated children data
        // DBMS will take care of this....

		// Delete the compound
		deleteCompound(compound);
	}

	/**
	 * Deletes a compound from the database
	 * <br>
	 * @throws SQLException
	 */
	private void deleteCompound(MetaboLightsCompound compound)	throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--delete.compound", "--where.compound.by.id");
			stm.clearParameters();
			stm.setLong(1, compound.getId());
			stm.executeUpdate();
	
	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("Compound deleted with id:" +compound.getId()); 
    		
       		
		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}


	private MetaboLightsCompound loadCompound(ResultSet rs) throws SQLException, DAOException {
		MetaboLightsCompound compound = null;

        compound = new MetaboLightsCompound();
        long id = rs.getLong("ID");
        String ACC = rs.getString("ACC");
        String name = rs.getString("NAME");
        String description = rs.getString("DESCRIPTION");
        String inchi = rs.getString("INCHI");
        String chebiId = rs.getString("TEMP_ID");
        String iupacNames = rs.getString("IUPAC_NAMES");
        String formula = rs.getString("FORMULA");

        compound.setId(id);
        compound.setAccession(ACC);
        compound.setName(name);
        compound.setDescription(description);
        compound.setInchi(inchi);
        compound.setChebiId(chebiId);
        compound.setFormula(formula);
        compound.setIupacNames(iupacNames);


        // Load children entities
        loadChildren(compound);

		return compound;
	}

    private void loadChildren (MetaboLightsCompound compound) throws DAOException {

        // Load metSpecies
        Collection<MetSpecies> metSpeciess = msd.findByMetId(compound.getId());

        compound.getMetSpecies().addAll(metSpeciess);

    }


	/**
	 * Inserts a new compound into the database
	 * <br>
	 * @throws SQLException
	 */
	private void insert(MetaboLightsCompound compound)	throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--insert.compound", new String[]{"ID"}, null);
			stm.clearParameters();
			stm.setString(1, compound.getAccession());
			stm.setString(2, compound.getName());
			stm.setString(3, compound.getDescription());
			stm.setString(4, compound.getInchi());
			stm.setString(5, compound.getChebiId());
            stm.setString(6, compound.getIupacNames());
            stm.setString(7, compound.getFormula());
			stm.executeUpdate();
	
			ResultSet keys = stm.getGeneratedKeys();

       		while (keys.next()) {
    			compound.setId(keys.getLong(1));  //Should only be one 
    	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("insertIntoCompound: Compound inserted with id:" +compound.getId()); 
    		}
    		
       		keys.close();
       		
		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
	
	/**
	 * Updates core data concerning only to the compound 
	 * @param compound
	 * @throws DAOException
	 */
	private void update(MetaboLightsCompound compound ) throws DAOException {
		try {
		
			PreparedStatement stm = sqlLoader.getPreparedStatement("--update.compound");
			stm.clearParameters();
			stm.setString(1, compound.getAccession());
			stm.setString(2, compound.getName());
			stm.setString(3, compound.getDescription());
			stm.setString(4, compound.getInchi());
			stm.setString(5, compound.getChebiId());
            stm.setString(6, compound.getIupacNames());
            stm.setString(7, compound.getFormula());
			stm.setLong(8, compound.getId());
			stm.executeUpdate();
	
		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
}