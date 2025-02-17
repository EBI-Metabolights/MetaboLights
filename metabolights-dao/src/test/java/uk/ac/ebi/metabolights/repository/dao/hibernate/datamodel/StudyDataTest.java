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

package uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOTest;
import uk.ac.ebi.metabolights.repository.dao.hibernate.HibernateUtil;
import uk.ac.ebi.metabolights.repository.dao.hibernate.SessionWrapper;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validations;
import uk.ac.ebi.metabolights.repository.utils.ClobJsonUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class StudyDataTest  extends DAOTest {

	private static final String ACC = "ACC";
	private static final String OCODE = "OCODE";

	@Test
	public void testStudyDataCRUD(){

		SessionWrapper session = HibernateUtil.getSession();

		session.needSession();
		StudyData studyData = getStudyData();

		// add validation

		// Add validations
//		ValidationData vd = DataModelFactory.getValidationDataInstance(new Validation());
//		vd.setPassed(true);
//		vd.setRequirement(Requirement.OPTIONAL);
//		vd.setValidationid(new Integer(3));
//		vd.setStudyData(studyData);
//		session.save(vd);

		Validation validation = new Validation();
		validation.setMessage("Hello");
		validation.setPassedRequirement(true);
		Validations validations = new Validations();
		validations.getEntries().add(validation);

		studyData.setValidations(ClobJsonUtils.parseToJSONString(validations));




		//studyData.getValidationsDataSet().add(vd);



		 //Add an user
//		UserData user = UserDataTest.getNewUserData();
//		session.save(user);
//		studyData.getUsers().add(user);
//
		session.saveOrUpdate(studyData);

		Assert.assertNotNull("Id it's been populated", studyData.id);
		logger.info("New studyData id populated: " + studyData.id);

		// Close the session
		session.noNeedSession();



		// Open it again
		session.needSession();

		// Try get to it
		studyData = (StudyData)session.get(StudyData.class, studyData.id);

		// Check some values..
		Assert.assertEquals("StudyData retrieved test: Obfuscation code", OCODE,studyData.getObfuscationcode());
		Assert.assertTrue("Accession starts with test:", studyData.getAcc().startsWith(ACC));
		logger.info("New studies id populated: " + studyData.getId());

		// Check users collection is retrieved
		//Assert.assertEquals("Are study users populated?", 1,studyData.getUsers().size());
//
//		Assert.assertEquals("Are study validations populated?", 1,studyData.getValidationsDataSet().size());
//		logger.info("Validations populated: " + studyData.getValidationsDataSet().size());

		// test validation
		 Validations validations1 = ClobJsonUtils.parseJson(studyData.getValidations(), Validations.class);
		Assert.assertEquals("Are study validations populated?", 1 , validations1.getEntries().size());
		logger.info("Validations populated: " + validations1.getEntries().size());

		// Test deletion
		session.delete(studyData);

		session.noNeedSession();
	}

	@Test
	public void testStudyDataCleanUsersSetUpdate(){

		SessionWrapper session = HibernateUtil.getSession();

		session.needSession();
		StudyData studyData = getStudyData();

		// Add an user
		UserData user1 = UserDataTest.getNewUserData();
		/// Save it
		session.save(user1);

		// Same for user 2
		UserData user2 = UserDataTest.getNewUserData();
		session.save(user2);


		// Add 2 users
		studyData.getUsers().add(user1);
		studyData.getUsers().add(user2);

		session.save(studyData);

		// Now we "clean" the users set but using the same user (Trying to simulate a serialization: This is called detached objects.).
		// To see if hibernate will match id?
		Assert.assertNotNull("Id it's been populated", studyData.id);
		logger.info("New studyData id populated: " + studyData.id);
		Assert.assertNotNull("Id of the bussinessMOdelEntity must be populated", studyData.getBussinesModelEntity().getId());

		session.noNeedSession();


		// New session
		session.needSession();

		UserData user3 = UserDataTest.getNewUserData();
		session.save(user3);

		// Instantiate a "clean" new set
		HashSet<UserData> cleanUsers = new HashSet<>();

		// Add one of the existing users and a new one
		cleanUsers.add(user1);
		cleanUsers.add(user3);

		studyData.setUsers(cleanUsers);

		session.saveOrUpdate(studyData);

		session.noNeedSession();


	}

	@Test
	public void testDefaultValues(){

		// Test default values of StudyData
		StudyData studyData = new StudyData();

		Date expectedreleaseDate = DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH);

		Calendar cal = Calendar.getInstance();
		cal.setTime(expectedreleaseDate);
		cal.add(Calendar.DATE, 30); //minus number would decrement the days
		expectedreleaseDate = cal.getTime();

		Assert.assertEquals("Release date is initialised properly", expectedreleaseDate.toString(), studyData.getReleaseDate().toString());
	}

	public static StudyData getStudyData() {

		StudyData studyData = DataModelFactory.getStudyDataInstance(new Study());
		studyData.setAcc (ACC + System.currentTimeMillis());
		studyData.setObfuscationcode(OCODE);
		studyData.setStatus(Study.StudyStatus.PROVISIONAL.ordinal());
		studyData.setReleaseDate(new Date());
		studyData.setUpdateDate(new Date());
		studyData.setSubmissionDate(new Date());



		return studyData;
	}

	public static StudyData addStudyToDB(StudyData studyData) {
		SessionWrapper session = HibernateUtil.getSession();
		session.needSession();
		session.save(studyData);
		session.noNeedSession();

		return studyData;
	}
}