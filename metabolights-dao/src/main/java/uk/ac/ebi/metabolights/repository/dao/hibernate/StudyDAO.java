/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-21
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

package uk.ac.ebi.metabolights.repository.dao.hibernate;

import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.StudyData;
import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.Study;

import java.util.List;

/**
 * User: conesa
 * Date: 21/01/15
 * Time: 13:18
 */
public class StudyDAO extends DAO <Study,StudyData>{

	private List<String> studyList;
	private UserDAO usersDAO;

	/**
	 * Find a user by means of the accession number.
	 * @param accession
	 */
	public Study findByAccession(String accession) throws DAOException {

		return findSingle("acc=:acc",new Filter(new Object[]{"acc",accession}));

	};

	public boolean isStudyPublic(String accession) throws DAOException {
		String query = "select count(*) from " + StudyData.class.getSimpleName() +
				" where status= :status AND acc = :acc";

		Long count = (Long)this.getUniqueValue(query, new Filter(new Object[]{"status",  Study.StudyStatus.PUBLIC.ordinal(),"acc", accession}));

		return (count!=0L);
	}
	@Override
	protected void init(){

		usersDAO = new UserDAO();
		usersDAO.setSession(this.session);
	}

	@Override
	protected Class getDataModelClass() {
		return StudyData.class;
	}

	@Override
	protected void preSave(StudyData study) throws DAOException {

		// NOTE: Here we only save the users that do not exist. Any update of users should be done through usersDAO.save.
		// Save new users first
		usersDAO.save(study.getUsers(), true);

	}

	public Study findByObfuscationCode(String obfuscationCode) throws DAOException {
		return findSingle("obfuscationcode=:oc",new Filter(new Object[]{"oc",obfuscationCode}));
	}

	public List<String> getStudyListForUser(String userToken) throws DAOException {

		// Hibernate left join:
		// from Cat as cat left join cat.mate.kittens as kittens
		// https://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html/ch16.html#queryhql-joins

		String query = "select distinct study.acc from " + StudyData.class.getSimpleName() + " study" +
				" left join study.users user";

		// Create an empty filter
		Filter filter = new Filter();

		// is the user a curator?
		boolean isCurator = usersDAO.hasUserThisRole(AppRole.ROLE_SUPER_USER, userToken);

		// If not...
		if (!isCurator) {

			query = query + " where (study.status= " + Study.StudyStatus.PUBLIC.ordinal() + ") OR (user.apiToken=:apiToken) ";

			// Add clause to where..
			filter.fieldValuePairs.put("apiToken", userToken);
		}

		List<String> studies = this.getList(query, filter);

		return studies;
	}
}
