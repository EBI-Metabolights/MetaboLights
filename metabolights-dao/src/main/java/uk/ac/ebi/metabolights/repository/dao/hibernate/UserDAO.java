/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-May-07
 * Modified by:   kenneth
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.repository.dao.hibernate;

import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserData;
import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.User;

/**
 * User: conesa
 * Date: 21/01/15
 * Time: 13:18
 */
public class UserDAO extends DAO <User,UserData>{

	/**
	 * Find a user by means of the userName.
	 * @param userName
	 */
	public User findByName(String userName) throws DAOException {

		return findSingle("userName=:username",new Filter(new Object[]{"username",userName}));

	};

	/**
	 * Find a user by means of the user tokken.
	 * @param userToken
	 */
	public User findByToken(String userToken) throws DAOException {

		return findSingle("apiToken=:apitoken",new Filter(new Object[]{"apitoken",userToken}));

	}

	/**
	 * Find a user by means of the email address.
	 * @param email
	 */
	public User findByEmail(String email) throws DAOException {

		return findSingle("email=:email",new Filter(new Object[]{"email",email}));
	};

	@Override
	protected void init() {
		// Nothing to initialize
	}

	@Override
	protected Class getDataModelClass() {
		return UserData.class;
	}

	@Override
	protected void preSave(UserData datamodel) throws DAOException {
		// Nothing to do. No parents.
	}


	public boolean hasUserThisRole(AppRole role, String userToken) throws DAOException {

		String query = "select count(*) from " + UserData.class.getSimpleName() +
				" where role= :role AND apiToken = :apiToken";

		Long count = (Long)this.getUniqueValue(query, new Filter(new Object[]{"role",  role.ordinal(),"apiToken", userToken}));

		return (count!=0L);

	}
}
