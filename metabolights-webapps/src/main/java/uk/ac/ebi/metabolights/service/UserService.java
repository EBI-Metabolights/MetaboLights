/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 1/17/13 4:49 PM
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

package uk.ac.ebi.metabolights.service;

import uk.ac.ebi.metabolights.model.MetabolightsUser;

import java.util.List;

public interface UserService {

	/**
	 * Find a user by means of the Id.
	 * @param id
	 */
	public MetabolightsUser lookupById(Long id);
	
	/**
	 * Get all users.
	 */
	public List<MetabolightsUser> getAll();
	

	/**
	 * Find a user by means of the userName.
	 * @param username
	 */
	public MetabolightsUser lookupByUserName(String username);

    /**
     * Find a user by means of the email address.
     * @param email
     */
	public MetabolightsUser lookupByEmail(String email);

    /**
     * Insert user in the database.
     * @param user
     * @return the database id for the user
     */
	public Long insert(MetabolightsUser user);

    /**
     * Update existing user in database.
     * @param user
     * @return the database id for the user
     */
	public void update(MetabolightsUser user);

    /**
     * Remove user from database.
     * @param user
     * @return the database id for the user
     */
	public void delete(MetabolightsUser user);

}
