/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/24/12 12:40 PM
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

package uk.ac.ebi.metabolights.dao;

import java.util.List;

import uk.ac.ebi.metabolights.model.MetabolightsUser;

/**
 * DAO interface for MetabolightsUsers.
 *
 */
public interface UserDAO {
	/**
	 * Find a user by means of the userName.
	 * @param userName
	 */
    public MetabolightsUser findByName(String userName);

    /**
     * Find a user by means of the email address.
     * @param email
     */
    public MetabolightsUser findByEmail(String email);

    /**
     * Get all users.
     */
    public List<MetabolightsUser> getAll();
    
    /**
     * Store a user in the database.
     * @param user
     * @return the database id for the user
     */
    public Long insert(MetabolightsUser user);


    /**
     * Update a user
     * @param user
     * @return the database id for the user
     */
    public void update(MetabolightsUser user);


    /**
	 * Find a user by means of the Id.
	 * @param Id
	 */
    public MetabolightsUser findById(Long id);


    /**
     * Delete a user
     * @param user
     * @return the database id for the user
     */
    public void delete(MetabolightsUser user);


}
