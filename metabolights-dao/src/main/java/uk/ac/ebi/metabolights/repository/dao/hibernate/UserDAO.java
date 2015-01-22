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

import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserData;

/**
 * User: conesa
 * Date: 21/01/15
 * Time: 13:18
 */
public class UserDAO extends DAO {

//	public User findByName(String userName){
//
//		Session session = getSession();
//
//		Query query = session.createQuery("from users u");
//
//		List<User> users = query.list();
//
//	};

//	/**
//	 * Find a user by means of the userName.
//	 * @param userName
//	 */
//	public User findByName(String userName){
//
//		Session session = getSession();
//
//		Query query = session.createQuery("from users u");
//
//		List<User> users = query.list();
//
//	};
//
//	/**
//	 * Find a user by means of the user tokken.
//	 * @param userToken
//	 */
//	public User findByUserToken(String userToken){};
//
//	/**
//	 * Find a user by means of the email address.
//	 * @param email
//	 */
//	public User findByEmail(String email){
//
//		findBy("")
//	};
//
//	/**
//	 * Get all users.
//	 */
//	public List<User> getAll(){
//
//	};
//
//	/**
//	 * Store a user in the database.
//	 * @param user
//	 */
//	public void save (User user){};
//
//	/**
//	 * Find a user by means of the Id.
//	 * @param id
//	 */
//	public User findById(Long id) {};
//
//
//	/**
//	 * Delete a user
//	 * @param user
//	 * @return the database id for the user
//	 */
//	public void delete(User user){
//
//	};

	@Override
	protected String getDataModelName() {
		return UserData.class.getName();
	}


}
