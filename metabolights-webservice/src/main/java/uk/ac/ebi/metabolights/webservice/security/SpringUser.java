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
