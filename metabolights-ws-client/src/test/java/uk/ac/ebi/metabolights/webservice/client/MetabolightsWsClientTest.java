package uk.ac.ebi.metabolights.webservice.client;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.model.Study;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * User: conesa
 * Date: 10/06/2014
 * Time: 13:10
 */
public class MetabolightsWsClientTest {

	String SUBMMITER_TOKEN;
	String CURATOR_TOKEN;

	@Before
	public void setUp(){

		// Get token from environment...Do not commit them..
		SUBMMITER_TOKEN = System.getenv("SUBMMITER_TOKEN");
		CURATOR_TOKEN = System.getenv("CURATOR_TOKEN");
	}
	@Test
	public void testGetStudy() throws Exception {

		// If not setup skip (Avoid failing tests).
		if (SUBMMITER_TOKEN == null) return;

		MetabolightsWsClient wsClient = new MetabolightsWsClient("http://localhost.ebi.ac.uk:8080/metabolights/webservice/");

		// ****** Default anonymous access ********
		// MTBLS1 is meant to be public...
		Study study = wsClient.getStudy("MTBLS1");
		assertNotSame("Study should be accessible from an anonymous user", "PRIVATE STUDY", study.getTitle());

		// MTBLS4 is meant to be private...
		study = wsClient.getStudy("MTBLS4");
		assertEquals("Study shouldn't be accessible from an anonymous user", "PRIVATE STUDY", study.getTitle());



		// ****** CUrator anonymous access ********
		wsClient.setUserToken(CURATOR_TOKEN);

		// MTBLS4 is meant to be private...
		study = wsClient.getStudy("MTBLS1");
		assertNotSame("Study should be accessible from an curator user", "PRIVATE STUDY", study.getTitle());

		study = wsClient.getStudy("MTBLS4");
		// MTBLS4 is meant to be private...
		assertNotSame("Study should be accessible from curator", "PRIVATE STUDY", study.getTitle());





	}
}
