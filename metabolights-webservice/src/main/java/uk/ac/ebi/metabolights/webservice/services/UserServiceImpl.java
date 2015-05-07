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

/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 6/10/14 5:12 PM
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

package uk.ac.ebi.metabolights.webservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.UserDAO;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.webservice.security.SpringUser;

import javax.annotation.Resource;

/**
 * Implementation for UserService. Note the Spring annotations such as @Service, @Autowired and @Transactional.
 * These annotations are called Spring stereotype annotations.<br>
 * The @Service stereotype annotation used to decorate the UserServiceImpl class is a specialized form of the @Component annotation.
 * It is appropriate to annotate the service-layer classes with @Service to facilitate processing by tools or anticipating any future
 * service-specific capabilities that may be added to this annotation.
 */
@Service
public class UserServiceImpl implements UserService{

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource(name = "userDAO")
	private UserDAO userDAO;

	public UserServiceImpl() throws DAOException {

		//userDAO = DAOFactory.getInstance().getUserDAO();
	}


//	@Transactional
//	public User lookupByUserName(String userName) {
//		return  null;
//	}
//
//	@Transactional
//	public User lookupByEmail(String email) {
//		return  null;
//	}
//
//	@Transactional
//	public Long insert(User user) {
//		return null;
//	}
//
//	@Transactional
//	public void update(User user) {}
//
//	@Transactional
//	public User lookupById(Long id) {
//		return null;
//	}
//
//	@Transactional
//	public void delete(User user) {}
//
//	@Transactional
//	public List<User> getAll() {return null;}

	@Override
//	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

		User user= null;
		if (s != null && !s.isEmpty()) {
			try {
				user = userDAO.findByToken(s);

			} catch (DAOException e) {
				logger.error("Can't find user by token.", e);
			}
		}

		if (user == null){
			user = new User();
			user.setUserName(s);
			user.setStatus(User.UserStatus.ACTIVE);
		}

		SpringUser sUser = new SpringUser(user);

		return sUser;

	}
}
