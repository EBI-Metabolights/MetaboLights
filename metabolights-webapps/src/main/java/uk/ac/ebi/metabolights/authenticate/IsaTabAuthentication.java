/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/24/12 12:40 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.authenticate;

import java.util.Collection;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

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
        //return principal.getUserId();
    	return principal.getUserName();
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
