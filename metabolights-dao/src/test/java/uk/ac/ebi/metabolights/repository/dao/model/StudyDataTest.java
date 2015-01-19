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

package uk.ac.ebi.metabolights.repository.dao.model;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.dao.hibernate.HibernateTest;
import uk.ac.ebi.metabolights.repository.dao.hibernate.HibernateUtil;

import java.util.HashSet;

public class StudyDataTest  extends HibernateTest{


	@Test
	public void testStudyDataCRUD(){

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		StudyData studyData = getStudyData();


		// Add an user
		UserData user = UserDataTest.getNewUserData();

		studyData.users.add(user);

		session.save(studyData);

		Assert.assertNotNull("Id it's been populated", studyData.id);
		logger.info("New studyData id populated: " + studyData.id);

		session.delete(studyData);

		session.getTransaction().commit();
	}

	@Test
	public void testStudyDataCleanUsersSetUpdate(){

		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		StudyData studyData = getStudyData();

		// Add an user
		UserData user = UserDataTest.getNewUserData();

		// Save the user
		session.save(user);

		studyData.users.add(user);

		session.save(studyData);

		// Now we "clean" the users set but using the same user (Trying to simulate a serialization: This is called detached objects.).
		// To see if hibernate will match id?
		Assert.assertNotNull("Id it's been populated", studyData.id);
		logger.info("New studyData id populated: " + studyData.id);

		session.getTransaction().commit();
		session.close();



		// New session (
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();


		// Instantiate a "clean" new set
		HashSet<UserData> cleanUsers = new HashSet<>();
		cleanUsers.add(user);

		studyData.users = cleanUsers;

		session.saveOrUpdate(studyData);

		session.getTransaction().commit();



	}

	public static StudyData getStudyData() {

		StudyData studyData = new StudyData();
		studyData.acc = "ACC";
		studyData.obfuscationcode = "OCODE";

		return studyData;
	}

}