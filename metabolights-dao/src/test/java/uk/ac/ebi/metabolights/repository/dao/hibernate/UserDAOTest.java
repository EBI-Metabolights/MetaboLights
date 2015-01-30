/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-21
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

import org.junit.Assert;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserData;
import uk.ac.ebi.metabolights.repository.model.User;

import java.util.List;

public class UserDAOTest  extends DAOTest {

	private static final String USER_NAME = "UserName";
	private static final String LAST_NAME = "last name";
	private static final String FIRST_NAME = "first name";
	private static final String ADDRESS = "address";
	private static final String AFFILIATION = "affiliation";
	private static final String AFFILIATIONURL = "affiliationurl";
	private static final String EMAIL = "email";

	@Test
	public void testConstructor() throws Exception {

		UserDAO userDAO = new UserDAO();

		Assert.assertEquals("Class match test", UserData.class, userDAO.getDataModelClass());

	}

	@Test
	public void testCRUDUserDAO() throws DAOException {

		UserDAO userDAO = new UserDAO();

		User newUser = getUser();

		userDAO.save(newUser);

		Assert.assertNotNull("User id it's been populated", newUser.getUserId());


		// Test find
		User savedUser = userDAO.findById(newUser.getUserId());
		Assert.assertEquals("Test username", newUser.getUserName(), savedUser.getUserName());


		// Test update
		savedUser.setUserName(USER_NAME + System.currentTimeMillis());
		userDAO.save(savedUser);

		// Test findById
		savedUser = userDAO.findById(newUser.getUserId());
		Assert.assertNotSame("Test username", newUser.getUserName(), savedUser.getUserName());

		// Test findByName
		User byNameUser = userDAO.findByName(savedUser.getUserName());
		Assert.assertEquals("Test findByUserName: username match", savedUser.getUserName(), byNameUser.getUserName());

		// Test findByName
		User byTokenUser = userDAO.findByToken(savedUser.getApiToken());
		Assert.assertEquals("Test findByToken: username match", savedUser.getUserName(), byTokenUser.getUserName());

		// Test findByEmail
		User byEmailUser = userDAO.findByEmail(savedUser.getEmail());
		Assert.assertEquals("Test findByEmail: username match", savedUser.getUserName(), byTokenUser.getUserName());


		// Now delete the user
		userDAO.delete(savedUser);


	}

	public static User getUser() {
		User newUser = new User();

		newUser.setUserName(USER_NAME + System.currentTimeMillis());
		newUser.setLastName(LAST_NAME);
		newUser.setFirstName(FIRST_NAME);
		newUser.setAddress(ADDRESS);
		newUser.setAffiliation(AFFILIATION);
		newUser.setAffiliationUrl(AFFILIATIONURL);
		newUser.setDbPassword("password");
		newUser.setEmail(EMAIL);
		return newUser;
	}

	@Test
	public void testFindAll() throws DAOException {

		UserDAO userDAO = new UserDAO();

		List<User> users = userDAO.findAll();

		logger.info(users.size() + " users found.");


	}
}


