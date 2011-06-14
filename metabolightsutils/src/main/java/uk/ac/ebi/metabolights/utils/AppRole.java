package uk.ac.ebi.metabolights.utils;

import org.springframework.security.core.GrantedAuthority;

/**
 * Represents an authority granted to a Spring Authentication object. 
 */
public enum AppRole implements GrantedAuthority {
    
	ROLE_SUBMITTER (0),

    // not used:
    ROLE_SUPER_USER (1),
    ROLE_WANKER (2);

    private final int bit;

    /**
     * Creates an authority with a specific bit representation. It's important that this doesn't
     * change as it will be used in the database. The enum ordinal is less reliable as the enum may be
     * reordered or have new roles inserted which would change the ordinal values.
     *
     * @param bit the permission bit which will represent this authority in the datastore.
     */
    AppRole(int bit) {
        this.bit = bit;
    }

    public int getBit() {
        return bit;
    }

    public String getAuthority() {
        return toString();
    }
}
