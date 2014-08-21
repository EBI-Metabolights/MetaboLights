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

package uk.ac.ebi.metabolights.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.ebi.metabolights.dao.UserDAO;
import uk.ac.ebi.metabolights.model.MetabolightsUser;

/**
 * Implementation for UserService. Note the Spring annotations such as @Service, @Autowired and @Transactional. 
 * These annotations are called Spring stereotype annotations.<br>
 * The @Service stereotype annotation used to decorate the UserServiceImpl class is a specialized form of the @Component annotation.
 * It is appropriate to annotate the service-layer classes with @Service to facilitate processing by tools or anticipating any future
 * service-specific capabilities that may be added to this annotation.
 */
@Service 
public class UserServiceImpl implements UserService{
	 
    @Autowired
    private UserDAO userDAO;
    
	@Transactional
	public MetabolightsUser lookupByUserName(String userName) {
		return  userDAO.findByName(userName);
	}

	@Transactional
	public MetabolightsUser lookupByEmail(String email) {
		return  userDAO.findByEmail(email);
	}

	@Transactional
	public Long insert(MetabolightsUser user) {
		return userDAO.insert(user);	
	}

	@Transactional
	public void update(MetabolightsUser user) {
		userDAO.update(user);	
	}

	@Transactional
	public MetabolightsUser lookupById(Long id) {
		return userDAO.findById(id);
	}

	@Transactional
	public void delete(MetabolightsUser user) {
		userDAO.delete(user);
	}

	@Transactional
	public List<MetabolightsUser> getAll() {
		return userDAO.getAll();
	}

}
