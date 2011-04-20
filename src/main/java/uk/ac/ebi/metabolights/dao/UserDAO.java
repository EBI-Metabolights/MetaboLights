package uk.ac.ebi.metabolights.dao;

import uk.ac.ebi.metabolights.authenticate.MetabolightsUser;

public interface UserDAO {
    public MetabolightsUser findByName(String userName);
}
