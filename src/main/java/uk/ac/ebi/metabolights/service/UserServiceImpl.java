package uk.ac.ebi.metabolights.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.ebi.metabolights.authenticate.MetabolightsUser;
import uk.ac.ebi.metabolights.dao.UserDAO;

@Service
public class UserServiceImpl implements UserService{
	 
    @Autowired
    private UserDAO userDAO;
    
	@Transactional
	public MetabolightsUser lookupByName(String userName) {
		return  userDAO.findByName(userName);

	}

}
