package uk.ac.ebi.metabolights.repository.accessionmanager;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.ac.ebi.metabolights.model.StableId;
import uk.ac.ebi.metabolights.service.AccessionService;
import uk.ac.ebi.metabolights.service.AccessionServiceImpl;
import uk.ac.ebi.metabolights.service.SearchService;

public class AccessionManagerTest2 {

	final String DEFAULT_PREFIX = "HELLO";
	final String hqlDelete = "DELETE StableId";
	final String hqlSelect = "FROM StableId";
	private static ApplicationContext applicationContext;
	private static SessionFactory sessionFactory;
    private static Session session;
    private static AccessionService accessionService;

	@Before
	public void letsGo() {
		if (applicationContext==null){
			applicationContext =new ClassPathXmlApplicationContext("test.spring.xml");
			sessionFactory = applicationContext.getBean(SessionFactory.class);
			session = sessionFactory.openSession();
			accessionService =  applicationContext.getBean(AccessionService.class);
		}
	}
    
	@After
	public void Finish(){
		
	}
	

	@Test
	public void testDefaultPrefix() {
		
		accessionService.setDefaultPrefix(DEFAULT_PREFIX);
		assertEquals(DEFAULT_PREFIX, accessionService.getDefaultPrefix());
		
	}

	@Test
	public void testOnlyOneRecordInTheDatabase(){
	
		List result;
		StableId extraStableId;
		
		//Delete all records
		deleteStablesIds();
		
		//Get List of stable Id
		result = getStablesIds();
		
		//We expect none
		assertEquals(0, result.size());
	
		//Save extra StableId, so we will have 2 rows
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
		session.close();
		
		//Ask for the accession number, this must throw an exception
		try {
			
			accessionService.getAccessionNumber();
			fail("The AccessionNumber manager does not throw an error when there are more than one record in the StableId table");
		
		}catch (Exception e){
		
			assertEquals(NonUniqueResultException.class, e.getClass());
			
		}
	}

	@Test
	public void testStableIdAutoCreationRecord() {
	//TODO Dangerous for production. There must be a dev database to connect to it for testing. Otherwise the production record will be deleted
		String accession;
		StableId stableId;
		
		List result;
		
		//Get delete the table...
		deleteStablesIds();
				
		session.close();
	
		//Set up the accessionManager with a prefix
		accessionService.setDefaultPrefix(DEFAULT_PREFIX);
		
		//Ask for the accession
		accession = accessionService.getAccessionNumber();
		
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
			
		//Get the current session
		session.beginTransaction();
				
		//Get the list of StableIds
		result = session.createQuery(hqlSelect).list();
				
		//Commit it
		session.getTransaction().commit();
		
		return result;
		
	}
	
	private void deleteStablesIds() {
		List result;
		
		//Get the current session
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
