package uk.ac.ebi.metabolights.repository.accessionmanager;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;
import uk.ac.ebi.metabolights.utils.HibernateUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ebi.metabolights.repository.accessionmanager.AccessionManager;

public class AccessionManagerTest {

	final String DEFAULT_PREFIX = "HELLO";
	final String hqlDelete = "DELETE StableId";
	final String hqlSelect = "FROM StableId";
	private Session session;

	@Before
	public void Setup(){
		
	}
	@After
	public void Finish(){
		
	}
	

	@Test
	public void testDefaultPrefix() {
		AccessionManager mgr = AccessionManager.getInstance();
		mgr.setDefaultPrefix(DEFAULT_PREFIX);
		assertEquals(DEFAULT_PREFIX, mgr.getDefaultPrefix());
		
	}

	@Test
	public void testGetInstance() {
		
		AccessionManager mgr1 = AccessionManager.getInstance();
		mgr1.setDefaultPrefix(DEFAULT_PREFIX);
		
		AccessionManager mgr2 = AccessionManager.getInstance();
		assertEquals(DEFAULT_PREFIX, mgr2.getDefaultPrefix());
				
	}
	@Test
	public void testOnlyOneRecordInTheDatabase(){
	
		List result;
		StableId extraStableId;
		AccessionManager mgr = AccessionManager.getInstance();
		String accessionfoo;
		
		//Delete all records
		deleteStablesIds();
		
		//Get List of stable Id
		result = getStablesIds();
		
		//We expect to be one
		assertEquals(0, result.size());
	
		//Save extra StableId, so we will have 2 rows
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//Add an extra StableId
		extraStableId = new StableId();
		extraStableId.setId(1);
		extraStableId.setSeq(1);
		extraStableId.setPrefix("MOO");
		session.save(extraStableId);
		
		//Add an extra StableId
		extraStableId = new StableId();
		extraStableId.setId(2);
		extraStableId.setSeq(1);
		extraStableId.setPrefix("FOO");

		session.save(extraStableId);
		session.getTransaction().commit();

		
		//Ask for the accession number, this must throw an exception
		try {
			
			accessionfoo = mgr.getAccessionNumber();
			fail("The AccessionNumber manager does not throw an error when there are more than one record in the StableId table");
		
		}catch (Error e){
		
			assertEquals(ExceptionInInitializerError.class, e.getClass());
			
		}
	}

	@Test
	public void testStableIdAutoCreationRecord() {
	//TODO Dangerous for production. There must be a dev database to connect to it for testing. Otherwise the production record will be deleted
		AccessionManager mgr = AccessionManager.getInstance();
		String accession;
		StableId stableId;
		
		List result;
		
		//Get delete the table...
		deleteStablesIds();
				
		//Set up the accessionManager with a prefix
		mgr.setDefaultPrefix(DEFAULT_PREFIX);
		
		//Ask for the accession
		accession = mgr.getAccessionNumber();
		
		//Check the accession returned
		assertEquals(DEFAULT_PREFIX + 1 , accession);
			
		//Select the record (there must be only one)
		result = getStablesIds();
		
		//Test there is only one
		assertEquals(1, result.size());
		
		//Test the ID has been incremented by 1
		stableId = (StableId) result.get(0);
		assertEquals((Integer)2, stableId.getSeq());
				
	}
	/**
	 * @param hqlDelete
	 * @param hqlSelect
	 */
	private List getStablesIds() {
		
		List result;
			
		//Connect to the database. How? Can I use hibernate...
		//Get the current session
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
				
		//Get the list of StableIds
		result = session.createQuery(hqlSelect).list();
				
		//Commit it
		session.getTransaction().commit();
		
		return result;
		
	}
	
	private void deleteStablesIds() {
		List result;
		
		//Connect to the database. How? Can I use hibernate...
		//Get the current session
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		//Delete the records
		session.createQuery(hqlDelete).executeUpdate();
				
		//Commit it
		session.getTransaction().commit();
		
		//Check deletion
		result = getStablesIds();
						
		//Test the record has been deleted
		assertEquals(0, result.size());
	
	}
		

}
