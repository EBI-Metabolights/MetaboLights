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

	String SUBMMITER_TOKEN = "1b297b50-3dc3-4afc-820a-c45a511081d5";
	String CURATOR_TOKEN = "cf7c2bbe-2cca-47d7-8e91-a1fd3cb45c79";

	@Before
	public void setUp(){

		// Get token from environment...Do not commit them..
		SUBMMITER_TOKEN = System.getenv("SUBIMMTER_TOKEN");
		CURATOR_TOKEN = System.getenv("CURATOR_TOKEN");
	}
	@Test
	public void testGetStudy() throws Exception {


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
