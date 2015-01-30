/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-19
 * Modified by:   conesa
 *
 *
 * Copyright 2015 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel;

import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.User;

import javax.persistence.*;
import java.util.*;


/**
 * User: conesa
 * Date: 19/01/15
 * Time: 14:12
 */
@Entity
@Table(name = "users")
public class UserData extends DataModel<User> {

	private String address;
	private String email;
	private Date joinDate;
	private String password;
	private int role;
	private String userName;
	private String affiliation;
	private String firstName;
	private String lastName;
	private int status;
	private String affiliationUrl;
	private String apiToken = java.util.UUID.randomUUID().toString();
	private Set<StudyData> studies = new HashSet<>();


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAffiliationUrl() {
		return affiliationUrl;
	}

	public void setAffiliationUrl(String affiliationUrl) {
		this.affiliationUrl = affiliationUrl;
	}

	public String getApiToken() {
		return apiToken;
	}

	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	@ManyToMany
	@JoinTable(name="study_user", joinColumns=@JoinColumn(name="userid"), inverseJoinColumns=@JoinColumn(name="studyid"))
	public Set<StudyData> getStudies() {
		return studies;
	}

	public void setStudies(Set<StudyData> studies) {
		this.studies = studies;
	}

	@Override
	protected void setBusinessModelId(Long id) {
		businessModelEntity.setUserId(id);
	}

	@Override
	protected void businessModelToDataModel() {

		this.id = businessModelEntity.getUserId();
		this.address =businessModelEntity.getAddress();
		this.email =businessModelEntity.getEmail();
		this.joinDate =businessModelEntity.getJoinDate();
		this.password =businessModelEntity.getDbPassword();
		this.role =businessModelEntity.getRole().ordinal();
		this.userName =businessModelEntity.getUserName();
		this.affiliation =businessModelEntity.getAffiliation();
		this.firstName =businessModelEntity.getFirstName();
		this.lastName =businessModelEntity.getLastName();
		this.status =businessModelEntity.getStatus().ordinal();
		this.affiliationUrl =businessModelEntity.getAffiliationUrl();
		this.apiToken =businessModelEntity.getApiToken();

	}

	@Override
	public User dataModelToBusinessModel() {

		businessModelEntity = new User();

		businessModelEntity.setUserId(this.id);
		businessModelEntity.setAddress(this.address);
		businessModelEntity.setEmail(this.email);
		businessModelEntity.setJoinDate(this.joinDate);
		businessModelEntity.setDbPassword(this.password);
		businessModelEntity.setRole(AppRole.values()[this.role]);
		businessModelEntity.setUserName(this.userName);
		businessModelEntity.setAffiliation(this.affiliation);
		businessModelEntity.setFirstName(this.firstName);
		businessModelEntity.setLastName(this.lastName);
		businessModelEntity.setStatus(User.UserStatus.values()[this.status]);
		businessModelEntity.setAffiliationUrl(this.affiliationUrl);
		businessModelEntity.setApiToken(this.apiToken);

		// Studies associated as Study Lite elements (Don't want the full studies pending from an user)
		businessModelEntity.setStudies(StudyData.studyDataToStudyLite(studies));

		return  businessModelEntity;
	}

	protected static Set<UserData> businessModelToDataModel(Collection<User> bussinesUsers){

		Set<UserData> usersData = new HashSet<UserData>();

		for (User bussinesItem :bussinesUsers){

			UserData user = new UserData();
			user.setBussinesModelEntity(bussinesItem);

			// Add it to the collection
			usersData.add(user);
		}

		return usersData;
	}


	public static Set<User> dataModelToBusinessModel(Set<UserData> usersData) {

		Set<User> users = new HashSet<User>();

		for (UserData userData :usersData){

			User user = new User();
			user = userData.dataModelToBusinessModel();

			// Add it to the collection
			users.add(user);
		}

		return users;


	}
}
