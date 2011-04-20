package uk.ac.ebi.metabolights.authenticate;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "USER_DETAIL")
public class MetabolightsUser implements Serializable{
	private static final long serialVersionUID = -775643268878161432L;

	public MetabolightsUser() {
		//default:
		this.authorities = EnumSet.of(AppRole.ROLE_SUBMITTER);
	}


    @Id
    @Column(name="ID")
	private String userId;

    @Column(name="EMAIL")
	private String email;

    @Column(name="USERNAME")
	private String userName;

    @Column(name="PASSWORD")
	private String dbPassword;
    
    @Transient
    private Set<AppRole> authorities;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setAuthorities(Set<AppRole> authorities) {
		this.authorities = authorities;
	}

	public Set<AppRole> getAuthorities() {
		return authorities;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbPassword() {
		return dbPassword;
	}

}
