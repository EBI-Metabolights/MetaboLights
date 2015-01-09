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
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.model.User;

import java.sql.SQLException;

public class UsersDAOTest extends DBTestCaseBase{

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(SQLQueryMapperTest.class.getClassLoader().getResourceAsStream("SQLQueryMapper.xml"));
	}

	@Before
	public void setUp(){

		DAOFactory.setConnectionProvider(this.connectionProvider);

	}
	@Test
	public void testInsertionOfAUser() throws DAOException {

		// Create a new user
		User newUser = new User();
		newUser.setUserName("testnewuser");
		newUser.setDbPassword("password");


		UsersDAO usersDAO  = DAOFactory.getUsersDAO();

		usersDAO.save(newUser);


	}

	@Test
	public void testFindBy() throws DAOException, SQLException {

		// Create a new user
		User newUser = new User();
		newUser.setUserName("testnewuser");
		newUser.setDbPassword("password");


		UsersDAO usersDAO  = DAOFactory.getUsersDAO();

		usersDAO.save(newUser);


	}

}