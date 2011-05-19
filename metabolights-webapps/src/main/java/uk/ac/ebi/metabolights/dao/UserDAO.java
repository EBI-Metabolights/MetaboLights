package uk.ac.ebi.metabolights.dao;

import uk.ac.ebi.metabolights.model.MetabolightsUser;

/**
 * DAO interface for MetabolightsUsers.
 *
 */
public interface UserDAO {
    public MetabolightsUser findByName(String userName);
    public MetabolightsUser findByEmail(String email);

}
