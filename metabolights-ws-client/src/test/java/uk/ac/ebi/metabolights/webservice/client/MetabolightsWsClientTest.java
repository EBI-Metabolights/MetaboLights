/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Mar-30
 * Modified by:   kenneth
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.referencelayer.model.Citation;
import uk.ac.ebi.metabolights.referencelayer.model.Compound;
import uk.ac.ebi.metabolights.referencelayer.model.Reaction;
import uk.ac.ebi.metabolights.repository.model.Entity;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.search.service.SearchQuery;
import uk.ac.ebi.metabolights.search.service.SearchResult;
import uk.ac.ebi.metabolights.webservice.client.models.ArrayListOfStrings;
import uk.ac.ebi.metabolights.webservice.client.models.CitationsList;
import uk.ac.ebi.metabolights.webservice.client.models.MixedSearchResult;
import uk.ac.ebi.metabolights.webservice.client.models.ReactionsList;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * User: conesa
 * Date: 10/06/2014
 * Time: 13:10
 */
public class MetabolightsWsClientTest {

	public static final String PRIVATE_STUDY = "MTBLS10";
	public static final String PRIVATE_STUDY_OC = "4faafe88-495c-4536-b818-a3cedcd6e768";

	public static final String PUBLIC_STUDY = "MTBLS1";
	public static final String PUBLIC_STUDY_OC = "c17f47f3-2f8c-4e30-b019-a13d4e519eb3";
	public static final String COMPOUND = "MTBLC10440";
	String SUBMITTER_TOKEN;
	String CURATOR_TOKEN;
	private MetabolightsWsClient wsClient;

	private static final Logger logger = LoggerFactory.getLogger(MetabolightsWsClientTest.class);


	@Before
	public void setUp(){

		// Get token from environment...Do not commit them..
		SUBMITTER_TOKEN = System.getenv("SUBMITTER_TOKEN");
		if (SUBMITTER_TOKEN == null) {
			SUBMITTER_TOKEN="owner";
		}

		CURATOR_TOKEN = System.getenv("CURATOR_TOKEN");
		if (CURATOR_TOKEN == null) {
			CURATOR_TOKEN="curator";
		}
		wsClient = new MetabolightsWsClient("http://localhost:8080/metabolights/webservice/");
	}

	@Test
	public void testGetStudy() throws Exception {

		// If not setup skip (Avoid failing tests).
		if (SUBMITTER_TOKEN == null) return;

		// ****** Default anonymous access ********
		// MTBLS1 is meant to be public...
		RestResponse<Study> response = wsClient.getStudy(PUBLIC_STUDY);
		Study study = response.getContent();

		assertEquals("Study should be accessible to an anonymous user", PUBLIC_STUDY, study.getStudyIdentifier());

		assertEquals("Sample data is present", 132, study.getSampleTable().getData().size());
		assertEquals("Assay data is present", 132, study.getAssays().get(0).getAssayTable().getData().size());

		assertEquals("Table fields are serialised and deserialised properly", 7, study.getSampleTable().getFields().size());
		assertEquals("Assay Table fields are serialised and deserialised properly, incuding duplicates.", 31, study.getAssays().get(0).getAssayTable().getFields().size());

		// MTBLS5 is meant to be private...
		response = wsClient.getStudy(PRIVATE_STUDY);
		study = response.getContent();

		assertNull("Study shouldn't be accessible to an anonymous user and should be null", study);
		assertNotNull("Response should have a message", response.getMessage());

		// ****** Curator access ********
		wsClient.setUserToken(CURATOR_TOKEN);

		response = wsClient.getStudy(PUBLIC_STUDY);
		study = response.getContent();

		assertEquals("Study should be accessible to a curator user", PUBLIC_STUDY, study.getStudyIdentifier());

		response = wsClient.getStudy(PRIVATE_STUDY);
		study = response.getContent();

		// MTBLS5 is meant to be private...
		assertNotNull("Study should be accessible to a curator user and shouldn't be null", study);
		assertEquals("Study should be populated", PRIVATE_STUDY, study.getStudyIdentifier());

	}

	@Test
	public void testSearch() {

		RestResponse<? extends MixedSearchResult> response = wsClient.search();

		assertNotNull(response);
		assertNotSame("Something has been returned", 0, response.getContent().getResults().size());

		Entity entity = response.getContent().getResults().iterator().next();




	}

	@Test
	public void testSearchWithQuery() {

		SearchQuery query = new SearchQuery("human");
		wsClient.setUserToken(CURATOR_TOKEN);

		RestResponse<? extends SearchResult> response = wsClient.search(query);

		assertNotNull(response);
		assertNotSame("Something has been returned", 0, response.getContent().getResults().size());
		assertEquals("Content is NOT deserialized into proper LiteStudy class", LiteStudy.class, response.getContent().getResults().iterator().next().getClass());

	}

	@Test
	public void testSearchStudy() {

		searchStudy(CURATOR_TOKEN, PUBLIC_STUDY);

	}

	public LiteStudy searchStudy(String userToken, String studyIdentifier) {

		wsClient.setUserToken(userToken);

		RestResponse<? extends SearchResult> response = wsClient.searchStudyWithResponse(studyIdentifier);

		assertNotNull(response);
		assertEquals("Search study method didn't return 1 item", 1, response.getContent().getResults().size());
		LiteStudy study = (LiteStudy) response.getContent().getResults().iterator().next();

		assertEquals("Study returned is not the expected.", studyIdentifier,study.getStudyIdentifier());

		study = wsClient.searchStudy(studyIdentifier);

		assertNotNull(study);
		assertEquals("Study returned is not the expected.", studyIdentifier,study.getStudyIdentifier());

		return study;
	}


	@Test
	public void testDelete() {


		// There should be a study
		RestResponse<String> response = wsClient.deleteStudy(PRIVATE_STUDY);

		assertNotNull(response);
		assertNotNull("Anonymous user shouldn't be allowed to delete his own study", response.getErr());
		logger.info("Anonymous not allowed to delete response is : {}", response.getMessage());


		// There should be a study
		wsClient.setUserToken(SUBMITTER_TOKEN);

		// Test study is in the index
		LiteStudy study = wsClient.searchStudy(PRIVATE_STUDY);

		assertNotNull("Study to be deleted not indexed", study);
		assertEquals("Study returned is not the expected.", PRIVATE_STUDY, study.getStudyIdentifier());


		response = wsClient.deleteStudy(PRIVATE_STUDY);

		assertNotNull(response);
		assertNotNull("Owner shouldn't be allowed to delete his own study", response.getErr());
		logger.info("Owner not allowed to delete response is : {}", response.getMessage());


		// There should be a study
		wsClient.setUserToken(CURATOR_TOKEN);
		response = wsClient.deleteStudy(PRIVATE_STUDY);

		assertNotNull(response);
		assertNull("Curator should be allowed to delete any study", response.getErr());
		logger.info("Curator allowed to delete response is : {}", response.getMessage());

		// Test study is NOT in the index anymore
		study = wsClient.searchStudy(PRIVATE_STUDY);

		assertNull("Study deleted is still in the index", study);


	}



	@Test
	public void testIndexAdmin() {


		wsClient.setUserToken(CURATOR_TOKEN);

		RestResponse<String> resetResponse = wsClient.resetIndex();
		logger.info(resetResponse.getMessage());
		assertNull("Index couldn't be reset", resetResponse.getErr());


		RestResponse status = wsClient.getIndexStatus();
		logStatus(status);


		// Delete studies index and compounds
		RestResponse<ArrayListOfStrings> response = wsClient.deleteCompoundsIndex();
		assertNull("Compounds index couldn't be deleted" , response.getErr());

		status = wsClient.getIndexStatus();
		logStatus(status);

		// Studies index
		response = wsClient.deleteStudiesIndex();
		assertNull("Studies index couldn't be deleted" , response.getErr());

		status = wsClient.getIndexStatus();
		logStatus(status);


		// Index a compound
		response = wsClient.indexCompound(COMPOUND);
		assertNull("Compound couldn't be indexed" , response.getErr());

		// Index a mix of studies and compounds
		response = wsClient.indexStudy(PUBLIC_STUDY);
		assertNull("Study couldn't be indexed" , response.getErr());


		// Index all?

		// Delete index
		response = wsClient.deleteIndex();
		assertNull("Whole index couldn't be deleted" , response.getErr());

		status = wsClient.getIndexStatus();
		logStatus(status);


	}

	private void logStatus(RestResponse<ArrayListOfStrings> status) {

		String message  = "********INDEX STATUS *********\n";

		for (String line : status.getContent()) {

			message = message + line + "\n";
		}

		logger.info(message);
	}

	@Test
	public void testUpdatePublicReleaseDate() {

		Date newPublicReleaseDate = new Date();

		wsClient.setUserToken(CURATOR_TOKEN);
		RestResponse<String> response = wsClient.updatePublicReleaseDate(newPublicReleaseDate, PRIVATE_STUDY);

		assertNull("Public release date update threw an exception", response.getErr());
	}

	@Test
	public void testUpdateStatus() {

		wsClient.setUserToken(CURATOR_TOKEN);
		RestResponse<String> response = wsClient.updateStatus(LiteStudy.StudyStatus.INCURATION, PRIVATE_STUDY);

		assertNull("Status update threw an exception", response.getErr());

	}

	@Test
	public void testIndexingAll() {

		RestResponse<String> response = wsClient.reindex();
		assertNotNull("Anonymous user was allowed to reindex all!!", response.getErr());
		logger.info("Anonymous user wasn't allowed to reindex, good!: {}", response.getMessage());


		wsClient.setUserToken(CURATOR_TOKEN);
		response = wsClient.reindex();
		assertNull("Curator user was NOT allowed to reindex all!!", response.getErr());
		logger.info("Curator user allowed to reindex, good!: {}", response.getMessage());


	}

	@Test
	public void testIndexingStudy() {

		RestResponse<ArrayListOfStrings> response = wsClient.indexStudy(PRIVATE_STUDY);
		assertNotNull("Anonymous user was allowed to reindex a private study!!", response.getErr());
		logger.info("Anonymous user wasn't allowed to reindex, good!: {}", response.getMessage());

		// Only curators are allowed to use the indexing interface, not even owners.
		wsClient.setUserToken(SUBMITTER_TOKEN);
		response = wsClient.indexStudy(PRIVATE_STUDY);
		assertNotNull("Owner user was allowed to reindex "+ PRIVATE_STUDY + "!!", response.getErr());
		logger.info("Owner user wasn't allowed to reindex, good!: {}", response.getMessage());


		wsClient.setUserToken(CURATOR_TOKEN);
		response = wsClient.indexStudy(PRIVATE_STUDY);
		assertNull("Curator user was NOT allowed to reindex "+ PRIVATE_STUDY + "!!", response.getErr());
		logger.info("Curator user allowed to reindex, good!: {}", response.getMessage());


	}

	@Test
	public void testGetMetabolites(){

		RestResponse<MetaboliteAssignment> metabolitesResponse = wsClient.getMetabolites(PUBLIC_STUDY, 1);

		assertNull("WS.getMetabolites returned an error", metabolitesResponse.getErr());

		metabolitesResponse = wsClient.getMetabolitesByObfuscationCode("4cfc95c7-51f3-422f-b609-38f977133a29", 1);


		assertNull("WS.getMetabolitesByObfuscationCode returned an error", metabolitesResponse.getErr());


	}

	@Test
	public void testQueryPermissions(){

		// BY ID

		// Anonymous
		boolean response = wsClient.canViewStudy(PUBLIC_STUDY);

		Assert.assertTrue("Anonymous user should be able to access a public study", response);

		response = wsClient.canViewStudy(PRIVATE_STUDY);

		Assert.assertFalse("Anonymous user should NOT be able to access a private study", response);


		// Curator
		wsClient.setUserToken(CURATOR_TOKEN);

		response = wsClient.canViewStudy(PUBLIC_STUDY);
		Assert.assertTrue("Curator should be able to access a public study", response);

		response = wsClient.canViewStudy(PRIVATE_STUDY);
		Assert.assertTrue("Curator user should be able to access a private study", response);


		// Owner
		wsClient.setUserToken(SUBMITTER_TOKEN);

		response = wsClient.canViewStudy(PUBLIC_STUDY);
		Assert.assertTrue("Owner should be able to access a public study", response);

		response = wsClient.canViewStudy(PRIVATE_STUDY);
		Assert.assertTrue("Owner should be able to access his own private study", response);


		// BY OC (Obfuscation code)

		// Anonymous
		response = wsClient.canViewStudyByObfuscationCode(PUBLIC_STUDY_OC);

		Assert.assertTrue("Anonymous user should be able to access a public study", response);

		response = wsClient.canViewStudyByObfuscationCode(PRIVATE_STUDY_OC);

		Assert.assertTrue("Anonymous user should be able to access a private study by OC", response);


		// Curator
		wsClient.setUserToken(CURATOR_TOKEN);

		response = wsClient.canViewStudyByObfuscationCode(PUBLIC_STUDY_OC);
		Assert.assertTrue("Curator should be able to access a public study", response);

		response = wsClient.canViewStudyByObfuscationCode(PRIVATE_STUDY_OC);
		Assert.assertTrue("Curator user should be able to access a private study by its OC", response);


		// Owner
		wsClient.setUserToken(SUBMITTER_TOKEN);

		response = wsClient.canViewStudyByObfuscationCode(PUBLIC_STUDY_OC);
		Assert.assertTrue("Owner should be able to access a public study by OC", response);

		response = wsClient.canViewStudyByObfuscationCode(PRIVATE_STUDY_OC);
		Assert.assertTrue("Owner should be able to access his own private study by OC", response);

	}

	@Test
	public void testGetCompound() throws Exception {
		RestResponse<Compound> response = wsClient.getCompound(COMPOUND);

		// We should get the compound

		assertNotNull("There was an error getting a compound", response.getErr());
		assertEquals("Compound doesn't have the id", COMPOUND, response.getContent().getMc().getAccession());

	}

	@Test
	public void testGetReactions() throws Exception {
		RestResponse<ReactionsList> response = wsClient.getCompoundReactions(COMPOUND);

		// We should get the a list of reactions
		assertNull("There was an error getting reactions", response.getErr());
		assertEquals("Reactions are not returned as class", response.getContent().get(0).getClass(), Reaction.class);

	}

	@Test
	public void testGetCitations() throws Exception {
		RestResponse<CitationsList> response = wsClient.getCompoundCitations(COMPOUND);

		// We should get the a list of citations
		assertNull("There was an error getting citations", response.getErr());
		assertEquals("Citations are not returned as class", response.getContent().get(0).getClass(), Citation.class);

	}


	@Test
	public void testUpdateMetaboliteStudyMappings() throws Exception {
		RestResponse<ArrayListOfStrings> response = wsClient.updateMetaboliteStudyMappings();
		System.out.println(response.getContent());
	}

	@Test
    public void testGetNumberOfMAFs() {

        Map<Integer,Integer> mafs = wsClient.getAssayOrderAndNumberOfMAFs(PUBLIC_STUDY);
        Map<Integer,Integer> mafMap = new HashMap<>();
        mafMap.put(1,1);       //Only one assay (1) and one MAF (1) in MTBLS1
        assertEquals(mafMap,mafs);

    }

    @Test
    public void testGetMetabolitesJSON() {
        Map<Integer,Integer> mafs = wsClient.getAssayOrderAndNumberOfMAFs(PUBLIC_STUDY);
        String jsonMaf = null;

        for (Map.Entry<Integer, Integer> entry : mafs.entrySet()) {
            Integer assayNumber = entry.getKey();
            Integer maf = entry.getValue();

            if (maf == 1)
                jsonMaf = wsClient.getMetabolitesJSON(PUBLIC_STUDY, assayNumber);

        }

        assertNotNull(jsonMaf);

    }



}
