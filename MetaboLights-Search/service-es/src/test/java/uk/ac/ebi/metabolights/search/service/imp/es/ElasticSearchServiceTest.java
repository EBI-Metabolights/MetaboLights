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
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import uk.ac.ebi.metabolights.repository.dao.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.HibernateUtil;
import uk.ac.ebi.metabolights.repository.dao.hibernate.SessionWrapper;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.StudyData;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserData;
import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.search.service.IndexingFailureException;
import uk.ac.ebi.metabolights.search.service.SearchQuery;
import uk.ac.ebi.metabolights.search.service.SearchResult;
import uk.ac.ebi.metabolights.search.service.SearchUser;
import uk.ac.ebi.metabolights.search.service.imp.es.resultsmodel.LiteStudy;

import java.util.List;
import java.util.Properties;

public class ElasticSearchServiceTest {

	private static final String SEARCH_TO_HIT_ALL = "metabolite";
	private static final int STUDIES_COUNT = 3;

	ElasticSearchService elasticSearchService = new ElasticSearchService("conesa");
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


	@Before
	public void init() throws DAOException {


		String studiesFolderName = System.getenv("STUDIES_FOLDER");
		Assert.assertNotNull("STUDIES_FOLDER: Studies folder variable provided.", studiesFolderName);

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
		notOwner = createUser(AppRole.ROLE_SUBMITTER, "notOwner");
		session.save(notOwner);

		curator = createUser(AppRole.ROLE_SUPER_USER, "curator");
		session.save(curator);

		owner = createUser(AppRole.ROLE_SUBMITTER, "owner");
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

	private UserData createUser(AppRole role, String userName){

		UserData newUser = new UserData();
		newUser.setRole(role.ordinal());
		newUser.setUserName(userName);

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
		indexStudy(publicStudy.getAcc());

	}

	@Test
	public void testIndexAll() throws Exception {

		elasticSearchService.resetIndex();

		List<String> studies = studyDAO.getList(curator.getApiToken());

		for (String accession : studies) {

			indexStudy(accession);
		}

	}

	private void indexStudy(String accession) throws IndexingFailureException, DAOException {

		Study study = studyDAO.getStudy(accession, curator.getApiToken());
		elasticSearchService.index(study);
	}
}