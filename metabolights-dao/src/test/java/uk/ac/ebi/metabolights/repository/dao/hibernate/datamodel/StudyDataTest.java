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

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.dao.hibernate.HibernateTest;
import uk.ac.ebi.metabolights.repository.dao.hibernate.HibernateUtil;
import uk.ac.ebi.metabolights.repository.model.Study;

import java.util.HashSet;

public class StudyDataTest  extends HibernateTest{

	private static final String ACC = "ACC";
	private static final String OCODE = "OCODE";

	@Before
	public void cleanDB(){

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.createQuery("delete from StudyData").executeUpdate();
		session.createQuery("delete from UserData").executeUpdate();
		session.close();
	}

	@Test
	public void testStudyDataCRUD(){

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		StudyData studyData = getStudyData();


		// Add an user
		UserData user = UserDataTest.getNewUserData();
		session.save(user);
		studyData.getUsers().add(user);

		session.saveOrUpdate(studyData);

		Assert.assertNotNull("Id it's been populated", studyData.id);
		logger.info("New studyData id populated: " + studyData.id);

		// Close the session
		session.getTransaction().commit();
		session.close();

		// Open it again
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		// Try get to it
		studyData = (StudyData)session.get(StudyData.class, studyData.id);

		// Check some values..
		Assert.assertEquals("StudyData retrieved test: Obfuscation code", OCODE,studyData.getObfuscationcode());
		Assert.assertTrue("Accession starts with test:", studyData.getAcc().startsWith(ACC));

		// Check users collection is retrieved
		Assert.assertEquals("Are study users populated?", 1,studyData.getUsers().size());

		// Test deletion
		session.delete(studyData);

		session.getTransaction().commit();
	}

	@Test
	public void testStudyDataCleanUsersSetUpdate(){

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
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


		session.getTransaction().commit();

		// Close session, studyData becomes detached
		session.close();


		// New session
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();


		UserData user3 = UserDataTest.getNewUserData();
		session.save(user3);

		// Instantiate a "clean" new set
		HashSet<UserData> cleanUsers = new HashSet<>();

		// Add one of the existing users and a new one
		cleanUsers.add(user1);
		cleanUsers.add(user3);

		studyData.setUsers(cleanUsers);

		session.saveOrUpdate(studyData);

		session.getTransaction().commit();


	}

	public static StudyData getStudyData() {

		StudyData studyData = DataModelFactory.getStudyDataInstance(new Study());
		studyData.setAcc (ACC + System.currentTimeMillis());
		studyData.setObfuscationcode (OCODE);

		return studyData;
	}

}