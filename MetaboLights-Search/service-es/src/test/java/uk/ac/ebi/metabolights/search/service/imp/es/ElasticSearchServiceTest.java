/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Dec-08
 * Modified by:   conesa
 *
 *
 * Copyright 2014 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.search.service.imp.es;

import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import uk.ac.ebi.metabolights.repository.dao.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.HibernateUtil;
import uk.ac.ebi.metabolights.repository.dao.hibernate.SessionWrapper;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.StudyData;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserData;
import uk.ac.ebi.metabolights.repository.model.*;
import uk.ac.ebi.metabolights.search.service.*;

import java.util.List;
import java.util.Properties;

public class ElasticSearchServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(ElasticSearchServiceTest.class);
	private static final String SEARCH_TO_HIT_ALL = "metabolite";
	private static final int STUDIES_COUNT = 3;

	ElasticSearchService elasticSearchService = new ElasticSearchService();
	StudyDAO studyDAO;
	private static String ISATAB_CONFIG_FOLDER;
	private static String PRIVATE_FOLDER;
	private static String PUBLIC_FOLDER;
	private UserData notOwner;
	private UserData curator;
	private UserData owner;
	private StudyData privateStudy;
	private StudyData publicStudy;
	private StudyData publicStudy2;
	private String curatorToken;


	@Before
	public void init() throws DAOException {


		String studiesFolderName = System.getenv("STUDIES_FOLDER");
		Assert.assertNotNull("STUDIES_FOLDER: Studies folder variable provided.", studiesFolderName);

		curatorToken = System.getenv("CURATOR_TOKEN");

		PRIVATE_FOLDER = studiesFolderName + "/private";
		PUBLIC_FOLDER = studiesFolderName + "/public";


		ISATAB_CONFIG_FOLDER = System.getenv("ISATAB_CONFIG_FOLDER");

		Assert.assertNotNull("ISATAB_CONFIG_FOLDER: ISA Configuration folder variable provided.", ISATAB_CONFIG_FOLDER);


		// Configure database connection
		Configuration configuration = new Configuration();

		// Get property file (an empty one is taking default hibernate.properties)!!!
		Properties hibernateProperties = new Properties();

		configuration.setProperties(hibernateProperties);

		DAOFactory.initialize(ISATAB_CONFIG_FOLDER, PUBLIC_FOLDER, PRIVATE_FOLDER, configuration);

		// Get the studyDAO.
		studyDAO = DAOFactory.getInstance().getStudyDAO();

		initDB();


	}

	private void initDB() throws DAOException {

		// Delete all data
		SessionWrapper session = HibernateUtil.getSession();
		session.needSession();
		session.createQuery("delete from StudyData").executeUpdate();
		session.createQuery("delete from UserData").executeUpdate();


		// Add DB data for a private study and a public study
		notOwner = createUser(AppRole.ROLE_SUBMITTER, "notOwner", "poor", "guy");
		session.save(notOwner);

		curator = createUser(AppRole.ROLE_SUPER_USER, "curator", "power", "guy");
		session.save(curator);

		owner = createUser(AppRole.ROLE_SUBMITTER, "owner", "have", "something");
		session.save(owner);

		// Add study data
		uk.ac.ebi.metabolights.repository.dao.hibernate.StudyDAO dbStudyDAO = new uk.ac.ebi.metabolights.repository.dao.hibernate.StudyDAO();

		privateStudy = new StudyData();
		privateStudy.setStatus(Study.StudyStatus.PRIVATE.ordinal());
		privateStudy.setAcc("MTBLS5");
		privateStudy.getUsers().add(owner);
		session.save(privateStudy);

		publicStudy = new StudyData();
		publicStudy.setStatus(Study.StudyStatus.PUBLIC.ordinal());
		publicStudy.setAcc("MTBLS1");
		publicStudy.getUsers().add(owner);
		session.save(publicStudy);

		publicStudy2 = new StudyData();
		publicStudy2.setStatus(Study.StudyStatus.PUBLIC.ordinal());
		publicStudy2.setAcc("MTBLS10");
		publicStudy2.getUsers().add(curator);
		session.save(publicStudy2);


		session.noNeedSession();


	}

	private UserData createUser(AppRole role, String userName, String firstName, String lastName){

		UserData newUser = new UserData();
		newUser.setRole(role.ordinal());
		newUser.setUserName(userName);
		newUser.setStatus(User.UserStatus.ACTIVE.ordinal());
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);

		return newUser;
	}

	@Test
	public void testGetStatus() throws Exception {


		Assert.assertTrue("ElasticSearch service status", elasticSearchService.getStatus());

	}

	@Test
	public void testSearch(){

		SearchQuery query = new SearchQuery(publicStudy.getAcc());

		SearchResult<LiteEntity> result = elasticSearchService.search(query);

		Assert.assertTrue(publicStudy.getAcc() + " study is found" ,result.getResults().size() ==1);

		LiteStudy mtbls1 = (LiteStudy) result.getResults().iterator().next();

		Assert.assertEquals(publicStudy.getAcc() + " LiteStudy id populated", publicStudy.getAcc() , mtbls1.getStudyIdentifier());
		Assert.assertNotNull(publicStudy.getAcc() + " LiteStudy title populated", mtbls1.getTitle());
		Assert.assertEquals(publicStudy.getAcc() + " LiteStudy public release date populated", publicStudy.getReleaseDate(), mtbls1.getStudyPublicReleaseDate());


	}

	@Test
	public void testSecureSearch(){


		// Test a not owner
		SearchQuery query = new SearchQuery(SEARCH_TO_HIT_ALL);
		query.setUser(new SearchUser(notOwner.getUserName(),false));

		// Test notOwner all available
		// This should return all
		SearchResult<LiteEntity> result = elasticSearchService.search(query);
		Assert.assertTrue(" Only public studies should be found" ,result.getResults().size() ==2);


		// Ask for a private study, not owned
		query.setText(privateStudy.getAcc());
		result = elasticSearchService.search(query);

		Assert.assertTrue(privateStudy.getAcc() + " study shouldn't be found since is private and user is not an owner" ,result.getResults().size() ==0);


		// Test empty user
		query.setUser(null);

		result = elasticSearchService.search(query);

		Assert.assertTrue(privateStudy.getAcc() + " study shouldn't be found since is private and user is empty" ,result.getResults().size() ==0);


		// Test owner
		query.setUser(new SearchUser(owner.getUserName(), false));

		result = elasticSearchService.search(query);

		Assert.assertTrue(privateStudy.getAcc() + " study should be found since is private and user is the owner" ,result.getResults().size() ==1);


		// Test curator
		query.setUser(new SearchUser(curator.getUserName(), true));
		// This should return all
		query.setText("metabolite");

		result = elasticSearchService.search(query);

		Assert.assertTrue("All studies should be found, private and public" ,result.getResults().size() == STUDIES_COUNT);

	}

	@Test
	public void testPagination(){

		final int PAGE_SIZE = 2;

		SearchQuery query = new SearchQuery(SEARCH_TO_HIT_ALL);
		query.setUser(new SearchUser(curator.getUserName(), true));
		query.getPagination().setPageSize(PAGE_SIZE);

		SearchResult<LiteEntity> result = elasticSearchService.search(query);

		Assert.assertEquals("Results size should fit pageSize",PAGE_SIZE, result.getResults().size());
		Assert.assertEquals("Total count test", STUDIES_COUNT, result.getQuery().getPagination().getItemsCount());
		Assert.assertEquals("Page should be 1",query.getPagination().getPage(), result.getQuery().getPagination().getPage());

		// Request second page
		query.getPagination().setPage(2);
		result = elasticSearchService.search(query);

		Assert.assertEquals("Page should be kept", 2, result.getQuery().getPagination().getPage());
		Assert.assertEquals("Total count test", STUDIES_COUNT, result.getQuery().getPagination().getItemsCount());
		Assert.assertEquals("Returned items should be one", 1, result.getResults().size());

	}

	@Test
	public void testEmptyFacets(){


		SearchQuery query = new SearchQuery(SEARCH_TO_HIT_ALL);
		query.setUser(new SearchUser(curator.getUserName(), true));

		// Add facets
		Facet technologyFacet = new Facet("assays.technology");
		query.getFacets().add(technologyFacet);

		SearchResult<LiteEntity> result = elasticSearchService.search(query);

		// There should be two lines in the technology facet
		Assert.assertEquals("1 facet group",1 , result.getQuery().getFacets().size());

		// Check there are 2 lines
		Assert.assertEquals("Number of technology facet lines",2 , technologyFacet.getLines().size());


		// Try now with not owner
		query.setUser(new SearchUser(notOwner.getUserName(), false));

		// Add facets
		query.getFacets().clear();
		Facet studyStatus = new Facet("publicStudy");
		query.getFacets().add(studyStatus);

		result = elasticSearchService.search(query);

		// There should be one facet
		Assert.assertEquals("1 facet group",1 , result.getQuery().getFacets().size());

		// Check there is 1 line (Only Public studies)
		Assert.assertEquals("Number of studyStatus facet lines",1 , studyStatus.getLines().size());

		// Clean lines..
		studyStatus.getLines().clear();
		technologyFacet.getLines().clear();

		// Add technology facet
		query.getFacets().add(technologyFacet);

		result = elasticSearchService.search(query);

		// There should be 2 facets
		Assert.assertEquals("2 facet groups",2 , result.getQuery().getFacets().size());

		Assert.assertEquals("Technology must have 2 lines (MS and NMR)",2 , technologyFacet.getLines().size());

	}

	@Test
	public void testCheckedFacets(){

		SearchQuery query = new SearchQuery(SEARCH_TO_HIT_ALL);
		query.setUser(new SearchUser(curator.getUserName(), true));

		FacetLine line;
		// Add facets
		Facet technologyFacet = new Facet("assays.technology");

		line = new FacetLine("NMR spectroscopy");
		line.setChecked(true);
		technologyFacet.getLines().add(line);

		// Add an unchecked: should not afect the filter
		line = new FacetLine("mass spectrometry");
		line.setChecked(false);
		technologyFacet.getLines().add(line);

		query.getFacets().add(technologyFacet);

		SearchResult<LiteEntity> result = elasticSearchService.search(query);

		// There should be two lines in the technology facet
		Assert.assertEquals("1 facet group",1 , result.getQuery().getFacets().size());

		// Check there are still 2 lines
		Assert.assertEquals("Number of technology facet lines",2 , technologyFacet.getLines().size());

		// Check facet line has effect in the total count (filter applied)
		Assert.assertEquals("Number of results",1 , query.getPagination().getItemsCount());


		// Check orFilter (2 lines in one facet group)
		query = new SearchQuery(SEARCH_TO_HIT_ALL);
		query.setUser(new SearchUser(curator.getUserName(), true));

		// Add facets
		technologyFacet = new Facet("assays.technology");

		line = new FacetLine("NMR spectroscopy");
		line.setChecked(true);
		technologyFacet.getLines().add(line);

		line = new FacetLine("mass spectrometry");
		line.setChecked(true);
		technologyFacet.getLines().add(line);
		query.getFacets().add(technologyFacet);

		result = elasticSearchService.search(query);

		// There should be two lines in the technology facet
		Assert.assertEquals("1 facet group",1 , result.getQuery().getFacets().size());

		// Check there are still 2 lines
		Assert.assertEquals("Number of technology facet lines",2 , technologyFacet.getLines().size());

		// Check facet line has effect in the total count (filter applied as or...so all must be returned)
		Assert.assertEquals("Or filter, Number of results",3 , query.getPagination().getItemsCount());


		// Check andFilter (2 lines in diferent facet groups)
		query = new SearchQuery(SEARCH_TO_HIT_ALL);
		query.setUser(new SearchUser(curator.getUserName(), true));

		// Add facets (MS and Public)
		technologyFacet = new Facet("assays.technology");

		line = new FacetLine("mass spectrometry");
		line.setChecked(true);
		technologyFacet.getLines().add(line);

		query.getFacets().add(technologyFacet);

		Facet studyStatus = new Facet("publicStudy");

		line = new FacetLine("T");
		line.setChecked(true);
		studyStatus.getLines().add(line);


		query.getFacets().add(studyStatus);

		result = elasticSearchService.search(query);

		// There should be two lines in the technology facet
		Assert.assertEquals("2 facet group",2 , result.getQuery().getFacets().size());

		// Check there are 2 lines
		Assert.assertEquals("Number of technology facet lines",2 , technologyFacet.getLines().size());

		// Check facet line has effect in the total count (filter applied as or...so all must be returned)
		Assert.assertEquals("And filter(MS + public), Number of results",1 , query.getPagination().getItemsCount());

	}

	@Test
	public void testDelete() throws IndexingFailureException {

		elasticSearchService.delete(publicStudy.getAcc());
	}


	@Test
	public void testResetIndex() throws IndexingFailureException, DAOException {

		// Call reset index...
		elasticSearchService.resetIndex();

		// Index should be empty but exist and should be configured.
		Assert.assertEquals("Index exists", true, elasticSearchService.doesIndexExists());

		// Index one study
		indexStudy(publicStudy.getAcc(), "");

	}

	@Test
	public void testIndexAll() throws Exception {

		elasticSearchService.resetIndex();


		String userToken = curator.getApiToken();

		// For Dev testing, real data
//		String userToken = curatorToken;

		List<String> studies = studyDAO.getList(userToken);
		for (String accession : studies) {

			try {
				indexStudy(accession, userToken);

			} catch (DAOException e) {
				logger.warn("There was a problem retrieving the study." , e);
			}
		}

	}

	private void indexStudy(String accession, String userToken) throws IndexingFailureException, DAOException {

		Study study = studyDAO.getStudy(accession, userToken);

		elasticSearchService.index(study);
	}
}