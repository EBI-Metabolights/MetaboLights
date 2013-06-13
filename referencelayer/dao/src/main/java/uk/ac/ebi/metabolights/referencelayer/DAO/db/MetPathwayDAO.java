package uk.ac.ebi.metabolights.referencelayer.DAO.db;


import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.IDAO.IMetPathwayDAO;
import uk.ac.ebi.metabolights.referencelayer.domain.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class MetPathwayDAO extends AbstractDAO implements IMetPathwayDAO{

    private DatabaseDAO dbd;
    private AttributeDAO atd;
    
	
	/**
	 * @param connection to the MetPathway
	 * @throws java.io.IOException
	 */
	public MetPathwayDAO(Connection connection) throws IOException{
		super(connection);
        setUp(this.getClass());

        this.dbd = new DatabaseDAO(connection);
        this.atd = new AttributeDAO(connection);

	}


    /**
     * Setter for MetPathway connection. It also sets the same connection
     * for the underlying objects.<br>
     * This method should be used with pooled connections, and only when the
     * previous one and its prepared statements have been properly closed
     * (returned).
     * @param con
     * @throws java.sql.SQLException
     */
	public void setConnection(Connection con) throws SQLException{
		this.setConnection(con);
		this.dbd.setConnection(con);
        this.atd.setConnection(con);
	}

	public Collection<Pathway> findByMetId(Long MetId) throws DAOException {

       // It must return an array of one MetPathway....get the first one and only.
       Collection<Pathway> pathways = findBy("--where.pathway.by.metid", MetId);

       return pathways;
	}

	private Collection <Pathway> findBy(String where, Object value)
	throws DAOException {
		ResultSet rs = null;
		try {

			PreparedStatement stm = sqlLoader.getPreparedStatement("--pathway.core", where);
			stm.clearParameters();
			
			// If can be casted as long
			if (value instanceof Long){
				stm.setLong(1, (Long) value);
			} else {
				stm.setString(1, (String) value);
			}

			rs = stm.executeQuery();
			
			// Load all MetPathway
			return loadPathways(rs);
			
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
	
	private Set<Pathway> loadPathways(ResultSet rs) throws SQLException, DAOException {
		
		Set<Pathway> result = new HashSet<Pathway>();

        while (rs.next()){

			Pathway pathway = loadMetPathway(rs);
			result.add(pathway);
		}
		return result;
		
	}

	private Pathway loadMetPathway(ResultSet rs) throws SQLException, DAOException {


        long id = rs.getLong("ID");
        String path_to_pathway_file = rs.getString("PATH_TO_PATHWAY_FILE");
		long db_id = rs.getLong("PATHWAY_DB_ID");
        String name = rs.getString("NAME");


        // Get the referenced objects
        Database db = dbd.findByDatabaseId(db_id);

        Pathway pathway = new Pathway(id, name, db, new File(path_to_pathway_file));

        loadChildren(pathway);

		return pathway;
	}

    private void loadChildren (Pathway pathway) throws DAOException {

        // Load attributes
        Collection<Attribute> attributes = atd.findByPathWayId(pathway.getId());

        pathway.getAttributes().addAll(attributes);


    }

	public void save(Pathway pathway, MetaboLightsCompound compound) throws DAOException {

        // Validate:
        // Database must exist
        if (pathway.getDatabase() == null ){
            String msg = "Pathway can't be saved without a Database object associated";
            LOGGER.error(msg);
            throw new DAOException(msg);
        }

        // Compound must exist
        if (compound == null ){
            String msg = "Pathway can't be saved without a compound object associated";
            LOGGER.error(msg);
            throw new DAOException(msg);
        }


        // Before saving the Pathway data we need to save the foreign key entities if apply
        // We are assuming the compound it's been saved and the compound DAO is the one calling this method
        if (pathway.getDatabase().getId() == 0) dbd.save(pathway.getDatabase());


		// If its a new Pathway
		if (pathway.getId() == 0) {
			insert (pathway,compound);
		} else {
			update(pathway,compound);
		}

        // Now save the rest...cascade saving...
        saveAttributes(pathway);

    }

    private void saveAttributes(Pathway pathway) throws DAOException {

        for (Attribute attribute: pathway.getAttributes()){
            atd.savePathwayAttribute(attribute, pathway);
        }

    }


    /**
	 * Updates core data concerning only to the Pathway
	 * @param pathway
	 * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
	 */
	private void update(Pathway pathway, MetaboLightsCompound compound ) throws DAOException {
		try {
		
			PreparedStatement stm = sqlLoader.getPreparedStatement("--update.pathway");
			stm.clearParameters();
			stm.setLong(1,compound.getId());
			stm.setLong(2, pathway.getDatabase().getId());
            stm.setString(3, pathway.getName());
            stm.setString(4, pathway.getPathToPathwayFile().getAbsolutePath());
            stm.setLong(5, pathway.getId());

			stm.executeUpdate();
	
		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
	
	/**
	 * Inserts a new Pathway into the Pathway table
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void insert(Pathway pathway, MetaboLightsCompound compound) throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--insert.pathway", new String[]{"ID"}, null);
			stm.clearParameters();
            stm.setLong(1,compound.getId());
            stm.setLong(2, pathway.getDatabase().getId());
            stm.setString(3, pathway.getName());
            stm.setString(4, pathway.getPathToPathwayFile().getAbsolutePath());
			stm.executeUpdate();
	
			ResultSet keys = stm.getGeneratedKeys();
			
       		while (keys.next()) {
    			pathway.setId(keys.getLong(1));  //Should only be one
    	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("insertIntoPathway: Pathway inserted with id:" + pathway.getId());
    		}
    		
       		keys.close();

       		
		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
	
	/**
	 * Deletes a Pathway from the Pathway system
	 * <br>
	 * @throws java.sql.SQLException
	 */
	public void delete(Pathway pathway) throws DAOException {
		
		// Delete the Pathway
		deletePathway(pathway);
	}

	/**
	 * Deletes a Pathway from the Pathway table
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void deletePathway(Pathway pathway)	throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--delete.pathway", "--where.pathway.by.id");
			stm.clearParameters();
			stm.setLong(1, pathway.getId());
			stm.executeUpdate();
	
	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("Pathway deleted with id:" + pathway.getId());
    		

		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
}