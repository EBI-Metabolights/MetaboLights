package uk.ac.ebi.metabolights.test;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.metabolights.model.MetabolightsUser;

/**
 * Test class to allow testing Hibernate only, so without going through Spring.
 * Takes the connection details (eventually) from test.db.properties.
 * 
 * @author markr
 */
public class DAOWithoutSpringTest extends HibernateIntegrationTestCase{

	private SessionFactory sf;  
	private Session session;  	

	@Before 
	public void initialize() {
		sf = getSessionFactory(); 
		session=sf.openSession();
		try {
			DatabaseMetaData dbmmd = session.connection().getMetaData(); // why deprectaed?
			System.out.println("\n\n__Database Meta data________________________________");
			System.out.println("url : "+dbmmd.getURL());
			System.out.println("user: "+dbmmd.getUserName());
			System.out.println("______________________________________________________\n");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testStudiesAvailable () throws Exception {
		Query q = session.createQuery("from Study");
		List<Study> list = q.list();
		assertTrue ("Expecting some studies ..",list.size()>0);
		session.clear();
	}        


	@Test
	public void testUsersAvailable () throws Exception {
		Query q = session.createQuery("from MetabolightsUser");
		List<MetabolightsUser> list = q.list();
		assertTrue ("Expecting some users ..",list.size()>0);
		session.clear();
	}        

}
