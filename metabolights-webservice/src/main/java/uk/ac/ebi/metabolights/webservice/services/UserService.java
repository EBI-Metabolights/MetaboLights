package uk.ac.ebi.metabolights.webservice.services;

import uk.ac.ebi.metabolights.webservice.model.User;

import java.util.List;

public interface UserService extends org.springframework.security.core.userdetails.UserDetailsService{

	/**
	 * Find a user by means of the Id.
	 * @param id
	 */
	public User lookupById(Long id);
	
	/**
	 * Get all users.
	 */
	public List<User> getAll();
	

	/**
	 * Find a user by means of the userName.
	 * @param username
	 */
	public User lookupByUserName(String username);

    /**
     * Find a user by means of the email address.
     * @param email
     */
	public User lookupByEmail(String email);

    /**
     * Insert user in the database.
     * @param user
     * @return the database id for the user
     */
	public Long insert(User user);

    /**
     * Update existing user in database.
     * @param user
     * @return the database id for the user
     */
	public void update(User user);

    /**
     * Remove user from database.
     * @param user
     * @return the database id for the user
     */
	public void delete(User user);

}
