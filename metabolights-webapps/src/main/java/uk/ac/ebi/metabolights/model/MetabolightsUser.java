package uk.ac.ebi.metabolights.model;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import uk.ac.ebi.metabolights.authenticate.AppRole;
import uk.ac.ebi.metabolights.service.CountryService;


@Entity
@Table(name = "USER_DETAIL")
public class MetabolightsUser implements Serializable{
	private static final long serialVersionUID = -775643268878161432L;

	public MetabolightsUser() {
		//default values
		this.authorities = EnumSet.of(AppRole.ROLE_SUBMITTER);
		this.objectType="Person";
	}

	@Id
	@Column(name="ID")
	private String userId;

	@Column(name="EMAIL")
	@Email
    @NotEmpty
    private String email;

	@Column(name="USERNAME")
    @NotEmpty
    private String userName;

	@Column(name="PASSWORD")
    @NotEmpty
	private String dbPassword;

	@Transient
    @NotEmpty
	private String userVerifyDbPassword;

	@Transient
	private Set<AppRole> authorities;

	@Column(name="OBJ_TYPE")
	private String objectType;

	@Column(name="JOINDATE")
	private Date joinDate;

	@Column(name="FIRSTNAME")
    @NotEmpty
	private String firstName;

	@Column(name="LASTNAME")
    @NotEmpty
	private String lastName;

	@Column(name="ADDRESS")
	private String address;

	@Column(name="AFFILIATION")
	private String affiliation;

	//_______________________________________________
	// Getters and setters
	//_______________________________________________

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	public void setUserVerifyDbPassword(String userVerifyDbPassword) {
		this.userVerifyDbPassword = userVerifyDbPassword;
	}

	public String getUserVerifyDbPassword() {
		return userVerifyDbPassword;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public Map<String,String> getCountries() {
		return CountryService.getCountries();
	}
	
}
