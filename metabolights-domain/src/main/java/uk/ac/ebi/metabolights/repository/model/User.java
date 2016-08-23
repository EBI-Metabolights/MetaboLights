/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Aug-21
 * Modified by:   conesa
 *
 *
 * Copyright 2014 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user of the Metabolights / Isatab application.
 * @author markr
 *
 */

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
		this.role = AppRole.ANONYMOUS;
		this.status= UserStatus.NEW;
		this.joinDate=new Date();
	}

	private Long userId;

	private String email;

	private String userName;

	private String dbPassword;

	private String userVerifyDbPassword;

	private String objectType;

	private Date joinDate;

	private String firstName;

	private String lastName;

	private String address;

	private String affiliation;

	private String affiliationUrl;

	private UserStatus status;

	private AppRole role;

	private String apiToken = java.util.UUID.randomUUID().toString();

	private String orcid;

	private String mobilePhoneNumber;

	private String officePhoneNumber;

	private Set<LiteStudy> studies = new HashSet<LiteStudy>();

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

	public String getFullName(){
		return  (
					(firstName == null?"":firstName) + " " +
					(lastName == null?"":lastName)
				).trim();
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

	@JsonIgnore
	public UserStatus[] getListOfAllStatus(){
		return UserStatus.values();
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public UserStatus getStatus() {
		return status;
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
		this.apiToken = api_token;
	}

	public String getOrcid() {
		return orcid;
	}

	public void setOrcid(String orcid) {
		this.orcid = orcid;
	}

	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public String getOfficePhoneNumber() {
		return officePhoneNumber;
	}

	public void setOfficePhoneNumber(String officePhoneNumber) {
		this.officePhoneNumber = officePhoneNumber;
	}


	@JsonIgnore
	public Set<LiteStudy> getStudies() {
		return studies;
	}

	public void setStudies(Set<LiteStudy> studies) {
		this.studies = studies;
	}


	public Boolean isCurator(){
		return getRole().equals(AppRole.ROLE_SUPER_USER);
	}

	public Boolean doesUserOwnsTheStudy(String accession){

		// For each of the studies
		for (LiteStudy study : studies) {

			if (study.getStudyIdentifier().equals(accession)) {
				return true;
			}

		}

		return false;
	}

}
