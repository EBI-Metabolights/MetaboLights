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
