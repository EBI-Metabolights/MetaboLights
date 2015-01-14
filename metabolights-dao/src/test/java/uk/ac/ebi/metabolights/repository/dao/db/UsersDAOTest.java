/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-08
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

package uk.ac.ebi.metabolights.repository.dao.db;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.User;

import java.util.Date;

public class UsersDAOTest extends DBTestCaseBase{

	String[] expected_new = ("testnewuser" + System.currentTimeMillis() + "_password_address_affiliation_affiliationurl_apitoken_email_firstname_" + new Date() + "_lastname_" + AppRole.ROLE_SUBMITTER.ordinal() + "_" + User.UserStatus.FROZEN.ordinal()).split("_");
	String[] expected_updated = ("updated_passwordU_addressU_affiliationU_affiliationurlU_apitokenU_emailU_firstnameU_" + new Date() + "_lastnameU_" + AppRole.ROLE_SUPER_USER.ordinal() + "_" + User.UserStatus.ACTIVE.ordinal()).split("_");
	private enum EXPECTED {
		USER_NAME,PASSWORD,ADDRESS,AFF,AFF_URL,API_TOKEN,EMAIL, FIRST_NAME, JOIN_DATE, LAST_NAME, ROLE, STATUS
	}


	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(SQLQueryMapperTest.class.getClassLoader().getResourceAsStream("UsersDAODataTest.xml"));
	}

	@Test
	public void testCRUD() throws DAOException {

		User newUser = fillUserFromExpected(expected_new, null);

		UsersDAO usersDAO  = DAOFactory.getUsersDAO();

		// Save the new user
		usersDAO.save(newUser);

		// Test id it's been populated
		Assert.assertNotNull("User id shoud be populated", newUser.getUserId());

		// Test find now.
		User findByUser = usersDAO.findById(newUser.getUserId());

		// Test id it's been populated
		assertUser(findByUser,expected_new);

		// Test update...
		fillUserFromExpected(expected_updated,findByUser);

		// Save changes
		usersDAO.save(findByUser);

		findByUser = usersDAO.findById(findByUser.getUserId());

		assertUser(findByUser,expected_updated);

		// Delete the user
		usersDAO.delete(findByUser);

		// try to get it
		findByUser = usersDAO.findById(findByUser.getUserId());

		Assert.assertNull(findByUser);

	}

	private User fillUserFromExpected(String[] values, User user) {

		// Create a new user
		if (user ==null) user = new User();

		user.setUserName(values[EXPECTED.USER_NAME.ordinal()]);
		user.setDbPassword(values[EXPECTED.PASSWORD.ordinal()]);
		user.setAddress(values[EXPECTED.ADDRESS.ordinal()]);
		user.setAffiliation(values[EXPECTED.AFF.ordinal()]);
		user.setAffiliationUrl(values[EXPECTED.AFF_URL.ordinal()]);
		user.setApiToken(values[EXPECTED.API_TOKEN.ordinal()]);
		user.setEmail(values[EXPECTED.EMAIL.ordinal()]);
		user.setFirstName(values[EXPECTED.FIRST_NAME.ordinal()]);
		user.setJoinDate(new Date(values[EXPECTED.JOIN_DATE.ordinal()]));
		user.setLastName(values[EXPECTED.LAST_NAME.ordinal()]);

		int roleOrdinal = Integer.parseInt(values[EXPECTED.ROLE.ordinal()]);
		user.setRole(AppRole.values()[roleOrdinal]);

		int statusOrdinal = Integer.parseInt(values[EXPECTED.STATUS.ordinal()]);
		user.setStatus(User.UserStatus.values()[statusOrdinal]);

		return user;
	}


	private void assertUser(User user, String[] expected){

		// Test id it's been populated
		Assert.assertEquals("userName test", expected[EXPECTED.USER_NAME.ordinal()], user.getUserName());
		Assert.assertEquals("password test", expected[EXPECTED.PASSWORD.ordinal()], user.getDbPassword());
		Assert.assertEquals("address test", expected[EXPECTED.ADDRESS.ordinal()], user.getAddress());
		Assert.assertEquals("affiliation test", expected[EXPECTED.AFF.ordinal()], user.getAffiliation());
		Assert.assertEquals("affiliationUrl test", expected[EXPECTED.AFF_URL.ordinal()], user.getAffiliationUrl());
		Assert.assertEquals("token test", expected[EXPECTED.API_TOKEN.ordinal()], user.getApiToken());
		Assert.assertEquals("email test", expected[EXPECTED.EMAIL.ordinal()], user.getEmail());
		Assert.assertEquals("firstname test", expected[EXPECTED.FIRST_NAME.ordinal()], user.getFirstName());
		Assert.assertEquals("joindate test", new Date(expected[EXPECTED.JOIN_DATE.ordinal()]), user.getJoinDate());
		Assert.assertEquals("lastname test", expected[EXPECTED.LAST_NAME.ordinal()], user.getLastName());
		Assert.assertEquals("role test", Integer.parseInt(expected[EXPECTED.ROLE.ordinal()]), user.getRole().ordinal());
		Assert.assertEquals("status test", Integer.parseInt(expected[EXPECTED.STATUS.ordinal()]), user.getStatus().ordinal());

	}

}