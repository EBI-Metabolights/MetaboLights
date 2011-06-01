package uk.ac.ebi.metabolights.service;

import uk.ac.ebi.metabolights.model.MetabolightsUser;

public interface UserService {

	/**
	 * Find a user by means of the Id.
	 * @param Id
	 */
	public MetabolightsUser lookupById(Long id);

	/**
	 * Find a user by means of the userName.
	 * @param userName
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

}
