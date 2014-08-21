/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 6/9/14 11:51 AM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.webservice.dao;

import uk.ac.ebi.metabolights.webservice.model.User;

import java.util.List;

/**
 * DAO interface for Users.
 *
 */
public interface UserDAO {
	/**
	 * Find a user by means of the userName.
	 * @param userName
	 */
    public User findByName(String userName);

	/**
	 * Find a user by means of the user tokken.
	 * @param userToken
	 */
	public User findByUserToken(String userToken);

    /**
     * Find a user by means of the email address.
     * @param email
     */
    public User findByEmail(String email);

    /**
     * Get all users.
     */
    public List<User> getAll();
    
    /**
     * Store a user in the database.
     * @param user
     * @return the database id for the user
     */
    public Long insert(User user);


    /**
     * Update a user
     * @param user
     * @return the database id for the user
     */
    public void update(User user);


    /**
	 * Find a user by means of the Id.
	 * @param id
	 */
    public User findById(Long id);


    /**
     * Delete a user
     * @param user
     * @return the database id for the user
     */
    public void delete(User user);


}
