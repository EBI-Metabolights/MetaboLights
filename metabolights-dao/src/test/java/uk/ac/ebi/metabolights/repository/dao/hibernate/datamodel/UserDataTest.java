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
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.dao.hibernate.HibernateTest;
import uk.ac.ebi.metabolights.repository.dao.hibernate.HibernateUtil;
import uk.ac.ebi.metabolights.repository.model.User;

import java.util.Date;

public class UserDataTest extends HibernateTest{


	private static final String ADDRESS = "address";
	private static final String AFFILIATION = "affiliation";
	private static final String WWWAFFILIATION = "wwwaffiliation";
	private static final String TOKEN = "token";
	private static final String EMAIL = "email";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String PASSWORD = "password";
	private static final String USERNAME = "username";
	private static final int ROLE = 1;
	private static final int STATUS = 0;

	@Test
	public void testUserDataCRUD(){

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		// Get a new user
		UserData userData = getNewUserData();

		// Test persistence
		session.save(userData);

		Assert.assertNotNull("Id it's been populated", userData.id);
		logger.info("New userData id populated: " + userData.id);
		Assert.assertNotNull("Id of the bussinessModelEntity must be populated", userData.getBussinesModelEntity().getUserId());

		session.getTransaction().commit();
		session.close();

		// Test new data is been persisted
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		userData = (UserData) session.get(UserData.class,userData.id);

		// Check some values..
		Assert.assertEquals("UserData retrieved test: address", ADDRESS,userData.getAddress());
		Assert.assertEquals("UserData retrieved test: affiliation", AFFILIATION,userData.getAffiliation());
		Assert.assertEquals("UserData retrieved test: affiliation url", WWWAFFILIATION,userData.getAffiliationUrl());
		Assert.assertEquals("UserData retrieved test: token", TOKEN,userData.getApiToken());
		Assert.assertEquals("UserData retrieved test: email", EMAIL,userData.getEmail());
		Assert.assertEquals("UserData retrieved test: first name", FIRST_NAME,userData.getFirstName());
		Assert.assertEquals("UserData retrieved test: last name",LAST_NAME ,userData.getLastName());
		Assert.assertEquals("UserData retrieved test: password",PASSWORD ,userData.getPassword());
		Assert.assertEquals("UserData retrieved test: role", ROLE,userData.getRole());
		Assert.assertEquals("UserData retrieved test: ",STATUS ,userData.getStatus());
		Assert.assertTrue("UserData retrieved test: userName startswith", userData.getUserName().startsWith(USERNAME));

		session.delete(userData);

		session.getTransaction().commit();
	}

	public static UserData getNewUserData() {
		UserData userData = DataModelFactory.getUserDataInstance(new User());

		userData.setAddress (ADDRESS);
		userData.setAffiliation (AFFILIATION);
		userData.setAffiliationUrl (WWWAFFILIATION);
		userData.setApiToken (TOKEN);
		userData.setEmail(EMAIL);
		userData.setFirstName(FIRST_NAME);
		userData.setJoinDate(new Date());
		userData.setLastName(LAST_NAME);
		userData.setPassword(PASSWORD);
		userData.setRole(ROLE);
		userData.setStatus(STATUS);
		userData.setUserName(USERNAME + System.currentTimeMillis());

		return userData;
	}
}