/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 6/10/14 10:23 AM
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

package uk.ac.ebi.metabolights.webservice.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uk.ac.ebi.metabolights.webservice.model.AppRole;
import uk.ac.ebi.metabolights.webservice.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: conesa
 * Date: 10/06/2014
 * Time: 10:09
 */
public class SpringUser implements UserDetails {

	private User metaboLightsUser;
	private Collection<? extends GrantedAuthority> grantedAuthorities;

	public SpringUser(User metaboLightsUser){
		this.metaboLightsUser = metaboLightsUser;
		grantedAuthorities = getAuthorities(metaboLightsUser.getRole());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return metaboLightsUser.getDbPassword();
	}

	@Override
	public String getUsername() {
		return metaboLightsUser.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return metaboLightsUser.getStatus().equals(User.UserStatus.ACTIVE);
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return metaboLightsUser.getStatus().equals(User.UserStatus.ACTIVE);
	}

	public User getMetaboLightsUser() {
		return metaboLightsUser;
	}

	public void setMetaboLightsUser(User metaboLightsUser) {
		this.metaboLightsUser = metaboLightsUser;
	}

	public static Collection<? extends GrantedAuthority> getAuthorities(AppRole role) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();

		authList.add( new SimpleGrantedAuthority(role.getName()));

		return  authList;

	}
}
