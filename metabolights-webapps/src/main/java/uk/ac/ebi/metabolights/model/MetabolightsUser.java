/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 6/10/14 11:57 AM
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

package uk.ac.ebi.metabolights.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import uk.ac.ebi.metabolights.authenticate.AppRole;
import uk.ac.ebi.metabolights.service.CountryService;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * Represents a user of the Metabolights / Isatab application.
 * @author markr
 *
 */
@Entity
//@Table(name = "USER_DETAIL")
@Table(name = "users")
//Oracle sequence, comment this *out* for MySQL
//@SequenceGenerator(name="userSeq", sequenceName="USER_DETAIL_SEQ", allocationSize=1)
//
public class MetabolightsUser implements Serializable{

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public Long getJwtTokenExpireTime() {
		return jwtTokenExpireTime;
	}

	public void setJwtTokenExpireTime(Long jwtTokenExpireTime) {
		this.jwtTokenExpireTime = jwtTokenExpireTime;
	}

	public String getLocalUserData() {
		return localUserData;
	}

	public void setLocalUserData(String localUserData) {
		this.localUserData = localUserData;
	}

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
		this.status=UserStatus.NEW;
		this.joinDate=new java.util.Date();
		this.apiToken = UUID.randomUUID().toString();
		this.role = AppRole.ROLE_SUBMITTER.ordinal();
		this.jwtToken = null;
	}

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
    // Use this line for ORACLE DB
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="userSeq")

    // Use this for MYSQL
    // @GeneratedValue
	private Long userId;

	@Column(name="EMAIL")
	@Email
    @NotEmpty
    private String email;

	@Column(name="USERNAME")
    private String userName;

	@Column(name="PASSWORD")
    @NotEmpty
	private String dbPassword;

	@Transient
//    @NotEmpty
    private String userVerifyDbPassword;

	@Transient
	private String jwtToken;

	@Transient
	private Long jwtTokenExpireTime;

	@Transient
	private String localUserData;
	@Transient
	private Set<AppRole> authorities;

	@Column(name="JOINDATE")
	private Date joinDate;

	@Column(name="FIRSTNAME")
    @NotEmpty
	private String firstName;

	@Column(name="LASTNAME")
    @NotEmpty
	private String lastName;

	@Column(name="ADDRESS")
    @NotEmpty
	private String address;

	@Column(name="AFFILIATION")
    @NotEmpty
	private String affiliation;

	@Column(name="AFFILIATIONURL")
	@NotEmpty
    private String affiliationUrl;

	// Extra Metabolights column to be able to create a user account
	// that still needs approval.
	@Column(name="STATUS")
    @NotNull
	private UserStatus status;

    @Column(name="ROLE")
    private Integer role;

    @Column(name="APITOKEN")
    @NotEmpty
    private String apiToken;

	@Column(name="ORCID")
	private String orcId;


	//_______________________________________________
	// Getters and setters
	//_______________________________________________

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

	public String getAffiliationUrl() {
		return affiliationUrl;
	}

	public void setAffiliationUrl(String affiliationURL) {
		this.affiliationUrl = affiliationURL;
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

		if (isCurator()){
			if (!authorities.contains(AppRole.ROLE_SUPER_USER)){
				authorities.add(AppRole.ROLE_SUPER_USER);
			}
		}

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
			userName=userName.trim().toLowerCase();
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
	public String getCountry(){
		return CountryService.lookupCountry(address);
	}

	public Map<String,String> getListOfAllCountries() {
		return CountryService.getCountries();
	}
	public UserStatus[] getListOfAllStatus(){
		return UserStatus.values();
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public UserStatus getStatus() {
		return status;
	}


    public Boolean isCurator(){
        if (getRole().equals(AppRole.ROLE_SUPER_USER.ordinal())) //Integer 1 is stored in the database if you are a curator
            return true;

        return false;
    }

    public Integer getRole() {
        if (role == null)  //Normal users don't have a role (only curators) so the default field is null
            return 0;

        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

	public String getOrcId() {
		return orcId;
	}

	public void setOrcId(String orcid) {
		this.orcId = orcid;
	}

	public String getFullName(){
		return  (
				(firstName == null?"":firstName) + " " +
						(lastName == null?"":lastName)
		).trim();
	}
}
