package uk.ac.ebi.metabolights.service;

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

}
