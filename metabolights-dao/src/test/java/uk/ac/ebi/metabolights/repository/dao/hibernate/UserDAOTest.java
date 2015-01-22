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
import uk.ac.ebi.metabolights.repository.model.User;

import java.util.List;

public class UserDAOTest  extends HibernateTest{

	@Test
	public void testConstructor() throws Exception {

		UserDAO userDAO = new UserDAO();

		Assert.assertEquals("Table has the users table", Constants.USERS_TABLE, userDAO.getDataModelName());


	}
	@Test
	public void testFindAll(){

		UserDAO userDAO = new UserDAO();

		List<User> users = userDAO.findAll();


	}
}
