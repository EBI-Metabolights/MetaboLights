package uk.ac.ebi.metabolights.service;

import uk.ac.ebi.metabolights.authenticate.MetabolightsUser;

public interface UserService {
	public MetabolightsUser lookupByName(String username);
}
