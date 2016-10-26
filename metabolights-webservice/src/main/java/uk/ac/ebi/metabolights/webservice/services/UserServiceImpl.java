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
import sun.misc.BASE64Encoder;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.UserDAO;
import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.webservice.security.SpringUser;

import javax.annotation.Resource;
import java.security.MessageDigest;

/**
 * Implementation for UserService. Note the Spring annotations such as @Service, @Autowired and @Transactional.
 * These annotations are called Spring stereotype annotations.<br>
 * The @Service stereotype annotation used to decorate the UserServiceImpl class is a specialized form of the @Component annotation.
 * It is appropriate to annotate the service-layer classes with @Service to facilitate processing by tools or anticipating any future
 * service-specific capabilities that may be added to this annotation.
 */
//@Service
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
	public UserDetails loadUserByUsername(String userToken) throws UsernameNotFoundException {

		User user = getUser(userToken);

		SpringUser sUser = new SpringUser(user);

		return sUser;

	}


	public User authenticateUser(String email, String password){
		User user = getUserById(email, true);

		if(user != null){
			if(user.getDbPassword().equalsIgnoreCase(encode(password))) {
				return user;
			}else{
                return null;
            }
		}
		return user;
	}

	private String encode(String plaintext) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(plaintext.getBytes("UTF-8"));
			byte raw[] = md.digest();
			String hash = (new BASE64Encoder()).encode(raw);
			return hash;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Could not encrypt password for ISA db verification");
		}
	}

    public User getUserById(String email, Boolean strict) {
        String userEmailId = email;
        User user = null ;
        UserDAO udao = new UserDAO();
        try {
            user = udao.findByEmail(userEmailId);

        } catch (DAOException e) {
            logger.error("Can't find user by token {}",email, e);
        }
        return user;
    }

	public User getUserById(String email) {
		String userEmailId = email;
		User user = null ;
		UserDAO udao = new UserDAO();
		try {
			user = udao.findByEmail(userEmailId);

			if (user == null){
				user = new User();
				user.setUserName(userEmailId);
				user.setStatus(User.UserStatus.ACTIVE);
				user.setRole(AppRole.ANONYMOUS);
				user.setApiToken("");
			}

		} catch (DAOException e) {
			logger.error("Can't find user by token {}",email, e);
		}
		return user;
	}


	private User getUser(String userToken) {
		User user = null ;
		try {
			if (userDAO == null){
				userDAO = DAOFactory.getInstance().getUserDAO();
			}
			user = userDAO.findByToken(userToken);

			if (user == null){
				user = new User();
				user.setUserName(userToken);
				user.setStatus(User.UserStatus.ACTIVE);
				user.setRole(AppRole.ANONYMOUS);
				user.setApiToken(userToken);
			}

		} catch (DAOException e) {
			logger.error("Can't find user by token {}",userToken, e);
		}
		return user;
	}

	@Override
	public User lookupByToken(String userToken) {
		return getUser(userToken);
	}
}
