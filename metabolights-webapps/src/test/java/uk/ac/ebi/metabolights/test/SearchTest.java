package uk.ac.ebi.metabolights.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.ac.ebi.metabolights.service.SearchService;


/**
 * Tests searching with Lucene.
 * Uses the local Spring config and database properties, but otherwise relies on
 * the configurations in the ./main directory.
 * To run this test:
 * - have a test database with some public/private Studies per user
 * - have a Lucene index configure for this database in ./main
 * 
 * @author markr
 * @date aug 2011
 */
public class SearchTest { //extends HibernateIntegrationTestCase {

	private ApplicationContext applicationContext;
	private SessionFactory sessionFactory;
    private Session session;
    private SearchService searchService;
	@Before
	public void letsGo() {
		if (applicationContext==null){
			applicationContext =new ClassPathXmlApplicationContext("test.spring.xml");
			sessionFactory = applicationContext.getBean(SessionFactory.class);
			session = sessionFactory.openSession();
			searchService =  applicationContext.getBean(SearchService.class);
		}
	}
	
	@Test
	/**
	 * Test the count difference in searching public studies only versus searching for private studies included.
	 * Requires a user in test database with. Lucene index should be in sync with this data.
	 *  
	 */
	public void testPublicVsPrivateSearch () throws Exception {

		//First we look in the database, to find users with private and public studies
        String queryForUsersWithPublicAndPrivateStudies=
        "select * from " +
        "  ( select username," +
        "           (select count(*) from study s, study2user s2u where status=1 and s2u.study_id = s.id and s2u.user_id = u.id) privateCount, " +  
        "           (select count(*) from study s, study2user s2u where status=0 and s2u.study_id = s.id and s2u.user_id = u.id) publicCount,   " +
        "           (select count(*) from study s, study2user s2u where s2u.study_id = s.id and s2u.user_id = u.id) totalCount   " +
        "    from user_detail u) counts " +
        " where privatecount>0 and publiccount>0 order by 1 desc";
        @SuppressWarnings("unchecked")
		java.util.List<Object[]> userCounts = session.createSQLQuery(queryForUsersWithPublicAndPrivateStudies).list();
        assertTrue("Test needs a least one user with private as well as public studies", userCounts.size()>0);
        
        for (Object[] countResult : userCounts) {
	        String user = (String)countResult[0]; 
	        int usrPrivateCountDb = ((BigDecimal)countResult[1]).intValue(); 
	        int usrPublicCountDb = ((BigDecimal)countResult[2]).intValue();
	        int usrTotalCountDb = ((BigDecimal)countResult[3]).intValue();
	
	        //Then with the same user we perform the queries that the web application does.
	        //These (Lucene) search result counts should match.

			// Make sure that: the index is based on the db schema you are testing with, and that it is up-to-date (use /reindex)

	        String privStudyQuery = "+(status:PUBLIC user:username\\:"+user+"*) +status:PRIVATE";
	        Map<Integer, ?> privateSearchResults = searchService.search(privStudyQuery);
	        Iterator<Integer> iterator = privateSearchResults.keySet().iterator();
			int usrPrivateCountLucene = iterator.next();
			assertEquals("Expecting "+usrPrivateCountDb+" private studies for "+user, usrPrivateCountDb, usrPrivateCountLucene);
	
	        String pubStudyQuery = "+(status:PUBLIC user:username\\:"+user+"*) +(+status:PUBLIC +user:username\\:"+user+"|*)";
	        Map<Integer, ?> pubSearchResults = searchService.search(pubStudyQuery);
	        iterator = pubSearchResults.keySet().iterator();
			int usrPubCountLucene = iterator.next();
			assertEquals("Expecting "+usrPublicCountDb+" public studies for "+user, usrPublicCountDb, usrPubCountLucene);

	        String totalStudyQuery = "+(status:PUBLIC user:username\\:"+user+"*) +user:username\\:"+user+"|*";
	        Map<Integer, ?> totalSearchResults = searchService.search(totalStudyQuery);
	        iterator = totalSearchResults.keySet().iterator();
			int usrTotalCountLucene = iterator.next();
			assertEquals("Expecting "+usrTotalCountDb+" total studies for "+user, usrTotalCountDb, usrTotalCountLucene);
        }

	}        
}
