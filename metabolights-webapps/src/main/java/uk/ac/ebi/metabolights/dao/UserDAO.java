package uk.ac.ebi.metabolights.dao;

import uk.ac.ebi.metabolights.authenticate.MetabolightsUser;

/**
 * DAO interface for MetabolightsUsers.
 *
 */
public interface UserDAO {
    public MetabolightsUser findByName(String userName);
}
