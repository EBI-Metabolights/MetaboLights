package uk.ac.ebi.metabolights.authenticate;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


public class IsaTabAuthentication implements Authentication {
	private static final long serialVersionUID = 1593454290845634907L;

	private final MetabolightsUser principal;
    private final Object details;
    private boolean authenticated;

    public IsaTabAuthentication(MetabolightsUser principal, Object details) {
        this.principal = principal;
        this.details = details;
        authenticated = true;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return new HashSet<GrantedAuthority>(principal.getAuthorities());
    }

    public Object getCredentials() {
        throw new UnsupportedOperationException();
    }

    public Object getDetails() {
        return null;
    }

    public Object getPrincipal() {
        return principal;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        authenticated = isAuthenticated;
    }

    public String getName() {
        return principal.getUserId();
    }

    @Override
    public String toString() {
        return "IsaTabAuthentication{" +
                "principal=" + principal +
                ", details=" + details +
                ", authenticated=" + authenticated +
                '}';
    }


}
