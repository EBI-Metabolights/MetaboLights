package uk.ac.ebi.metabolights.model;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import uk.ac.ebi.metabolights.authenticate.AppRole;
import uk.ac.ebi.metabolights.service.CountryService;

/**
 * Represents a user of the Metabolights / Isatab application.
 * @author markr
 *
 */
@Entity
@Table(name = "USER_DETAIL")
@SequenceGenerator(name="userSeq", sequenceName="USER_DETAIL_SEQ", allocationSize=1)

public class MetabolightsUser implements Serializable{
	
	public static enum UserStatus {
		
		NEW("NEW"), VERIFIED("VERIFIED"), ACTIVE("ACTIVE"), FROZEN("FROZEN"); 

		private String val;
		UserStatus(String val) {
			this.val = val;
		}
		public String getValue() {
			return val;
		}
	}; 
	
	private static final long serialVersionUID = -775643268878161432L;

	public MetabolightsUser() {
		//Some default values
		this.authorities = EnumSet.of(AppRole.ROLE_SUBMITTER);
		this.objectType="Person";
		this.status=UserStatus.NEW.getValue();
		this.joinDate=new java.util.Date(); 
	}

	@Id
	@Column(name="ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="userSeq")
	private Long userId;

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

	// Extra Metabolights column to be able to create a user account
	// that still needs approval. 
	@Column(name="STATUS")
    @NotEmpty
	private String status;
	
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

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setAuthorities(Set<AppRole> authorities) {
		this.authorities = authorities;
	}

	public Set<AppRole> getAuthorities() {
		return authorities;
	}

	public void setEmail(String email) {
		if (email!=null)
			email=email.trim();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setUserName(String userName) {
		if (userName!=null)
			userName=userName.trim();
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setDbPassword(String dbPassword) {
		if (dbPassword!=null)
			dbPassword=dbPassword.trim();
		this.dbPassword = dbPassword;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setUserVerifyDbPassword(String userVerifyDbPassword) {
		if (userVerifyDbPassword!=null)
			userVerifyDbPassword=userVerifyDbPassword.trim();
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

	public Map<String,String> getListOfAllCountries() {
		return CountryService.getCountries();
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
	
}
