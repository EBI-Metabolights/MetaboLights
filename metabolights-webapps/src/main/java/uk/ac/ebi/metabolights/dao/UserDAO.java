package uk.ac.ebi.metabolights.dao;

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
