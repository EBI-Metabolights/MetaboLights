package uk.ac.ebi.metabolights.repository.accessionmanager;

import org.hibernate.Session;

import java.util.List;

import uk.ac.ebi.metabolights.repository.accessionmanager.StableId;
import uk.ac.ebi.metabolights.utils.HibernateUtil;
/**
 * Manages the accession number and persist into the database
 * If the table does not exists, it will create it and insert one row with 1 as Id and a prefix passed as parameter. TODO --> Test this
 * 
 * @author conesa
 *
 */
public class AccessionManager {
	private static AccessionManager mgr;
	private Session session;
	private StableId stableId;
	private String defaultPrefix;
		
	/**
	 * Get singleton instance
	 * @return AccessionManager unique instance
	 */
	public static AccessionManager getInstance(){
		
		//Lazy load
		if (mgr == null){
			mgr = new AccessionManager();
		}
		
		return mgr;
	}
	
	/**
	 * Make it a singleton, hide the constructor.
	 */
	private AccessionManager(){}
	
	/**
	 * Default prefix to use in case of creating the table from scratch
	 * @return String with the default prefix
	 */
	public String getDefaultPrefix (){
		return defaultPrefix;
	}
	public void setDefaultPrefix(String defaultPrefix){
		this.defaultPrefix = defaultPrefix;
	}
	
	/**
	 * It has the logic concatenating the Id and the prefix. It also increase the Id by one,
	 * and invoke persistance of the new value to the database with the new Id increased
	 * @return String with the concatenated Accession number
	 */
	public String getAccessionNumber(){
		
		String accession;
		
		//Get the stable id object (this is also a static variable, shall we use it instead...)
		StableId stableId = getStableId();
		
		//Compose the accession number
		accession = stableId.getPrefix() + stableId.getSeq();
		
		//Increment the id by one
		stableId.setSeq(stableId.getSeq() + 1);
		
		//Persist the new Id (incremented by one)
		//TODO: See how to manage the sessions differently. Now, for after transaction commit, the session will close. 
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(stableId);
		session.getTransaction().commit();
				
		//Return the accession
		return accession;
	}
	
	/**
	 * Returns the stableId object.
	 * If the stableId is already instantiated it will return it, avoiding connection to the database (concurrency is not considered).
	 * If there isn't any it will create a default one using 1 as Id and the defaultPrefix as prefix.
	 * If there is more than one row an exception will be thrown.
	 * If there is only one (expected) it will return it. 
	 * @return Unique StableId row in the database
	 * @throws ExceptionInInitializerError
	 */
	private StableId getStableId() throws ExceptionInInitializerError {
		
		//If we already have an instance of the StableId...
		if (stableId != null) {
			//...return it, everything should be ok...
			//TODO: What happens with concurrency. Not solved this way. 
			return stableId;
		}
	
		//Force initialization to have the database session object instantiated
		Initialize();
		
		//Start a transaction
		session.beginTransaction();
		
		//Get the list of StableId, there should be only one...
		List <StableId> result = session.createQuery("from StableId").list();
		
		//If there is more than one...
		if (result.size() > 1){
			
			//Commit the transaction before throwing the exception
			session.getTransaction().commit();
			
			throw new ExceptionInInitializerError("There is more than one row for Managing the accesion number in the database");
			
		}else if (result.size()==0) {
			
			//Instantiate the StableId object
			stableId = new StableId();
			
			//Populate with default values
			//Populate de id of the row.
			stableId.setId(1);
			stableId.setSeq(1);
			stableId.setPrefix(defaultPrefix);
			
			//Persist it
			session.save(stableId);
			
		}else{
			//Get the session
			stableId = result.get(0);
		}
		
		//Commit transaction
		session.getTransaction().commit();
		
		//Return stableId instance

		return stableId;
	}
	/**
	 * Initialize the local objects if not initialized already: session
	 */
	private void Initialize(){
		
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		
	}
}
