package uk.ac.ebi.metabolights.referencelayer.DAO.db;


import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.IDAO.ICrossReferenceDAO;
import uk.ac.ebi.metabolights.referencelayer.domain.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class CrossReferenceDAO extends AbstractDAO implements ICrossReferenceDAO{

    private DatabaseDAO dbd;

	/**
	 * @param connection to the Database
	 * @throws java.io.IOException
	 */
	public CrossReferenceDAO(Connection connection) throws IOException{

        super(connection);
        setUp(this.getClass());
        this.dbd = new DatabaseDAO(connection);

	}

    public CrossReference findByCrossReferenceId(Long crossReferenceId) throws DAOException {

        // Try to get it from the identity map...
        CrossReference cr =  CrossReferenceIdentityMap.getCrossReference(crossReferenceId);

        // If not loaded yet
        if (cr == null){
            // It must return an array of one crossreference....get the first one and only.
            Collection<CrossReference> crs = findBy("--where.crossreference.by.id", crossReferenceId);
            cr =  (crs.size()==0? null:crs.iterator().next());

        }

        return cr;

    }

    @Override
    public CrossReference findByCrossReferenceAccession(String accession) throws DAOException {

        Collection<CrossReference> crs = findBy("--where.crossreference.by.acc", accession);
        return   (crs.size()==0? null:crs.iterator().next());
    }


    private Collection <CrossReference> findBy(String where, Object value)
	throws DAOException {
		ResultSet rs = null;
		try {

			PreparedStatement stm = sqlLoader.getPreparedStatement("--crossreference.core", where);
			stm.clearParameters();
			
			// If can be casted as long
			if (value instanceof Long){
				stm.setLong(1, (Long) value);
			} else {
				stm.setString(1, (String) value);
			}

			rs = stm.executeQuery();
			
			// Load all MetSpecies
			return loadCrossReferences(rs);
			
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
	
	private Set<CrossReference> loadCrossReferences(ResultSet rs) throws SQLException, DAOException {
		
		Set<CrossReference> result = new HashSet<CrossReference>();

        while (rs.next()){

			CrossReference cr = loadCrossReference(rs);
			result.add(cr);
		}
		return result;
		
	}

	private CrossReference loadCrossReference(ResultSet rs) throws SQLException, DAOException {


        long id = rs.getLong("ID");
		String accesion = rs.getString("ACC");
		long db_id = rs.getLong("DB_ID");

        // Get the referenced objects
        Database db = dbd.findByDatabaseId(db_id);

        CrossReference cr = new CrossReference();
        cr.setId(id);
        cr.setAccession(accesion);
        cr.setDb(db);

        // Add the crossreference to the identity map
        CrossReferenceIdentityMap.addCrossReference(cr);

        return cr;
	}

	public void save(CrossReference crossReference) throws DAOException {

        // Validate:
        // CrossReference must exist
        if (crossReference.getDb() == null ){
            String msg = "CrossReference can't be saved without a Database object associated";
            LOGGER.error(msg);
            throw new DAOException(msg);
        }

        // Before saving the CrossReference data we need to save the foreign key entities if apply
        if (crossReference.getDb().getId() == 0) dbd.save(crossReference.getDb());

		// If its a new CrossReference
		if (crossReference.getId() == 0) {
			insert (crossReference);
		} else {
			update(crossReference);
		}
		
	}
	
	/**
	 * Updates core data concerning only to the CrossReference
	 * @param crossReference
	 * @throws uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException
	 */
	private void update(CrossReference crossReference ) throws DAOException {
		try {
		
			PreparedStatement stm = sqlLoader.getPreparedStatement("--update.crossreference");
			stm.clearParameters();
			stm.setString(1, crossReference.getAccession());
            stm.setLong(2, crossReference.getDb().getId());
            stm.setLong(3, crossReference.getId());
			stm.executeUpdate();
	
		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
	
	/**
	 * Inserts a new CrossReference into the REF_XREF table
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void insert(CrossReference crossReference) throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--insert.crossreference", new String[]{"ID"}, null);
			stm.clearParameters();
            stm.setString(1, crossReference.getAccession());
            stm.setLong(2, crossReference.getDb().getId());
			stm.executeUpdate();
	
			ResultSet keys = stm.getGeneratedKeys();
			
       		while (keys.next()) {
    			crossReference.setId(keys.getLong(1));  //Should only be one
    	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("insertIntoCrossReference: CrossReference inserted with id:" + crossReference.getId());
    		}
    		
       		keys.close();

            // Add the crossreference to the identity map
            CrossReferenceIdentityMap.addCrossReference(crossReference);

       		
		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
	
	/**
	 * Deletes a CrossReference from the REF_XREF table
	 * <br>
	 * @throws java.sql.SQLException
	 */
	public void delete(CrossReference crossReference) throws DAOException {
		
		// Delete the CrossReference
		deleteCrossReference(crossReference);

        // Delete the crossreference from the identity map
        CrossReferenceIdentityMap.removeCrossReference(crossReference);

    }

	/**
	 * Deletes a MetSpecies from the MetSpecies
	 * <br>
	 * @throws java.sql.SQLException
	 */
	private void deleteCrossReference(CrossReference crossReference)	throws DAOException {
		try {
			PreparedStatement stm = sqlLoader.getPreparedStatement("--delete.crossreference");
			stm.clearParameters();
			stm.setLong(1, crossReference.getId());
			stm.executeUpdate();
	
	        if (LOGGER.isDebugEnabled())
    	            LOGGER.debug("crossreference deleted with id:" + crossReference.getId());
    		

		} catch (SQLException ex) {
            throw new DAOException(ex);
		}
	}
}