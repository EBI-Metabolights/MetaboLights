package uk.ac.ebi.metabolights.webservice.model;

/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 4/3/14 3:29 PM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * Represents a user of the Metabolights / Isatab application.
 * @author markr
 *
 */
@Entity
@Table(name = "USER_DETAIL")
//Oracle sequence, comment this *out* for MySQL
@SequenceGenerator(name="userSeq", sequenceName="USER_DETAIL_SEQ", allocationSize=1)

public class User {

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

//
	public User() {
		//Some default values
		this.role = AppRole.ROLE_SUBMITTER;
		this.objectType="Person";
		this.status=UserStatus.NEW;
		this.joinDate=new java.util.Date();
	}

	@Id
	@Column(name="ID")
	// Use this line for ORACLE DB
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="userSeq")

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
	@NotEmpty
	private String userVerifyDbPassword;

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
	@NotEmpty
	private String address;

	@Column(name="AFFILIATION")
	@NotEmpty
	private String affiliation;

	@Column(name="AFFILIATION_URL")
	@NotEmpty
	private String affiliationUrl;

	// Extra Metabolights column to be able to create a user account
	// that still needs approval.
	@Column(name="STATUS")
	@NotEmpty
	private UserStatus status;

	@Column(name="ROLE")
	@Enumerated(EnumType.ORDINAL)
	private AppRole role;

	@Column(name="API_TOKEN")
	@NotEmpty
	private String apiToken;

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
		return getRole().equals(AppRole.ROLE_SUPER_USER);
	}

	public Boolean isReviewer(){
		return getRole().equals(AppRole.ROLE_REVIEWER);
	}

	public AppRole getRole() {
		if (role == null)  //Normal users don't have a role (only curators) so the default field is null
			return AppRole.ROLE_SUBMITTER;

		return role;
	}

	public void setRole(AppRole role) {
		this.role = role;
	}

	public String getApiToken() {
		return apiToken;
	}

	public void setApiToken(String api_token) {
		this.apiToken = apiToken;
	}
}
