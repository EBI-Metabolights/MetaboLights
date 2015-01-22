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

import uk.ac.ebi.metabolights.repository.model.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;


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
	private String apiToken;


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

	@Override
	protected void setBussinessModelId(Long id) {
		bussinesModelEntity.setUserId(id);		
	}

	@Override
	protected void bussinesModelToDataModel() {

		this.id = bussinesModelEntity.getUserId();
		this.address =bussinesModelEntity.getAddress();
		this.email =bussinesModelEntity.getEmail();
		this.joinDate =bussinesModelEntity.getJoinDate();
		this.password =bussinesModelEntity.getDbPassword();
		this.role =bussinesModelEntity.getRole().ordinal();
		this.userName =bussinesModelEntity.getUserName();
		this.affiliation =bussinesModelEntity.getAffiliation();
		this.firstName =bussinesModelEntity.getFirstName();
		this.lastName =bussinesModelEntity.getLastName();
		this.status =bussinesModelEntity.getStatus().ordinal();
		this.affiliationUrl =bussinesModelEntity.getAffiliationUrl();
		this.apiToken =bussinesModelEntity.getApiToken();

	}
	//private Set<StudyLite> studies = new HashSet<StudyLite>();

}
