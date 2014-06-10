package uk.ac.ebi.metabolights.webservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.metabolights.webservice.dao.UserDAO;
import uk.ac.ebi.metabolights.webservice.model.User;
import uk.ac.ebi.metabolights.webservice.security.SpringUser;

import java.util.List;

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
	public User lookupByUserName(String userName) {
		return  userDAO.findByName(userName);
	}

	@Transactional
	public User lookupByEmail(String email) {
		return  userDAO.findByEmail(email);
	}

	@Transactional
	public Long insert(User user) {
		return userDAO.insert(user);	
	}

	@Transactional
	public void update(User user) {
		userDAO.update(user);	
	}

	@Transactional
	public User lookupById(Long id) {
		return userDAO.findById(id);
	}

	@Transactional
	public void delete(User user) {
		userDAO.delete(user);
	}

	@Transactional
	public List<User> getAll() {
		return userDAO.getAll();
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

		User user= userDAO.findByUserToken(s);

		if (user == null){
			user = new User();
			user.setUserName(s);
		}

		SpringUser sUser = new SpringUser(user);

		return sUser;

	}

}