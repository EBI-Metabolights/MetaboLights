package uk.ac.ebi.metabolights.service;

import uk.ac.ebi.metabolights.model.MetabolightsUser;

public interface UserService {
	public MetabolightsUser lookupByName(String username);
	public MetabolightsUser lookupByEmail(String email);

}
