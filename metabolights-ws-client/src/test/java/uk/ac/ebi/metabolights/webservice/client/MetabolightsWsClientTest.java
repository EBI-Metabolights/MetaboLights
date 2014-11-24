/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 6/11/14 9:56 AM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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

		MetabolightsWsClient wsClient = new MetabolightsWsClient("http://localhost:8080/metabolights/webservice/");

		// ****** Default anonymous access ********
		// MTBLS1 is meant to be public...
		Study study = wsClient.getStudy("MTBLS1");
		assertNotSame("Study should be accessible from an anonymous user", "PRIVATE STUDY", study.getTitle());

		assertEquals("Sample data is present", 132, study.getSampleTable().getData().size());
		assertEquals("Assay data is present", 132, study.getAssays().get(0).getAssayTable().getData().size());

		assertEquals("Table fields are serialised and deserialised properly", 7, study.getSampleTable().getFields().size());
		assertEquals("Assay Table fields are serialised and deserialised properly, incuding duplicates.", 31, study.getAssays().get(0).getAssayTable().getFields().size());

		// MTBLS4 is meant to be private...
		study = wsClient.getStudy("MTBLS4");
		assertEquals("Study shouldn't be accessible from an anonymous user", "PRIVATE STUDY", study.getTitle());

		// ****** Curator anonymous access ********
		wsClient.setUserToken(CURATOR_TOKEN);

		// MTBLS4 is meant to be private...
		study = wsClient.getStudy("MTBLS1");
		assertNotSame("Study should be accessible from an curator user", "PRIVATE STUDY", study.getTitle());

		study = wsClient.getStudy("MTBLS4");
		// MTBLS4 is meant to be private...
		assertNotSame("Study should be accessible from curator", "PRIVATE STUDY", study.getTitle());





	}
}
