package uk.ac.ebi.metabolights.webservice.dao;

import uk.ac.ebi.metabolights.webservice.model.User;

import java.util.List;

/**
 * DAO interface for Users.
 *
 */
public interface UserDAO {
	/**
	 * Find a user by means of the userName.
	 * @param userName
	 */
    public User findByName(String userName);

	/**
	 * Find a user by means of the user tokken.
	 * @param userToken
	 */
	public User findByUserToken(String userToken);

    /**
     * Find a user by means of the email address.
     * @param email
     */
    public User findByEmail(String email);

    /**
     * Get all users.
     */
    public List<User> getAll();
    
    /**
     * Store a user in the database.
     * @param user
     * @return the database id for the user
     */
    public Long insert(User user);


    /**
     * Update a user
     * @param user
     * @return the database id for the user
     */
    public void update(User user);


    /**
	 * Find a user by means of the Id.
	 * @param id
	 */
    public User findById(Long id);


    /**
     * Delete a user
     * @param user
     * @return the database id for the user
     */
    public void delete(User user);


}
