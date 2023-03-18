/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-19
 * Modified by:   conesa
 *
 *
 * Copyright 2015 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.repository.dao.hibernate;

import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.StudyData;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.StudyDataTest;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserData;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserDataTest;

import java.util.Properties;

public class DDLTest {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Before
	public void setUp() throws Exception {

		Configuration configuration = new Configuration();

		// Get property file
		Properties hibernateProperties = new Properties();
		hibernateProperties.load(DDLTest.class.getResourceAsStream("/hibernate.hidden.properties"));

		configuration.setProperties(hibernateProperties);

		HibernateUtil.initialize(configuration);

	}

	protected void dropTables(){

		SessionWrapper session = HibernateUtil.getSession();
		dropTable(session,Constants.STUDY_USER_TABLE);
		dropTable(session, Constants.STUDIES_TABLE);
		dropTable(session, Constants.USERS_TABLE);
		dropTable(session, Constants.STABLE_ID_TABLE);


	}

	private void dropTable(SessionWrapper session, String table){

		session.needSession();

		try {
			session.createSQLQuery("DROP TABLE " + table).executeUpdate();
		} catch (SQLGrammarException e) {
			logger.info("Expected exception, when table does not exists");
		}

		session.noNeedSession();

	}

	@Test
	public void testSchemaCreation() throws Exception {

		dropTables();

		// Set it up again
		setUp();


		// Check constraints: Create 2 users same username
		UserData userData = UserDataTest.getNewUserData();
		UserDataTest.addUserToDB(userData);

		userData.setId(null);

		try {
			UserDataTest.addUserToDB(userData);

			throw new AssertionError("Username should be unique and exception should be thrown");
		} catch (ConstraintViolationException e){
			logger.info("Exception expected. Username unique constraint in place");
		}

		// Change username to test apiToken uniqueness
		UserData sameApiToken = UserDataTest.getNewUserData();
		sameApiToken.setApiToken(userData.getApiToken());
		try {
			UserDataTest.addUserToDB(sameApiToken);

			throw new AssertionError("ApiToken should be unique and exception should be thrown");
		} catch (ConstraintViolationException e){
			logger.info("Exception expected. ApiToken unique constraint in place");
		}


		// Check constraints: Create 2 studies same acc
		StudyData studyData = StudyDataTest.getStudyData();
		StudyDataTest.addStudyToDB(studyData);

		studyData.setId(null);

		try {
			StudyDataTest.addStudyToDB(studyData);

			throw new AssertionError("Study accession should be unique and exception should be thrown");
		} catch (ConstraintViolationException e){
			logger.info("Exception expected. Study accession unique constraint in place");
		}

		// Check constraints: Test obfuscation code
		StudyData sameOCode = StudyDataTest.getStudyData();
		sameOCode.setObfuscationcode(studyData.getObfuscationcode());

		try {
			StudyDataTest.addStudyToDB(sameOCode);

			throw new AssertionError("Study obfuscation code should be unique and exception should be thrown");
		} catch (ConstraintViolationException e){
			logger.info("Exception expected. Study obfuscation code unique constraint in place");
		}

		// Stable id table created...
		AccessionDAO accessionDAO = new AccessionDAO();
		accessionDAO.getStableId("something");

	}


}