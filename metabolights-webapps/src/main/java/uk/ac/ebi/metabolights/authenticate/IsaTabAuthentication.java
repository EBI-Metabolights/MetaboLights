package uk.ac.ebi.metabolights.authenticate;

import java.util.Collection;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import uk.ac.ebi.metabolights.controller.FileUploadController;
import uk.ac.ebi.metabolights.model.MetabolightsUser;

/**
 * For ISATab authentication in the Metabolights website.<br> 
 * Represents the token for an authentication request or for an authenticated principal once the request has been processed 
 * by the AuthenticationManager.authenticate(Authentication) method.
 * Once the request has been authenticated, the Authentication will usually be stored in a thread-local SecurityContext 
 * managed by the SecurityContextHolder by the authentication mechanism which is being used.  
 *
 */
public class IsaTabAuthentication implements Authentication {
	private static Logger logger = Logger.getLogger(IsaTabAuthentication.class);

	private static final long serialVersionUID = 1593454290845634907L;

	private final MetabolightsUser principal;
    private final Object details;
    private boolean authenticated;

    public IsaTabAuthentication(MetabolightsUser principal, Object details) {
    	logger.info("Authenticating "+principal.getUserName());
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
