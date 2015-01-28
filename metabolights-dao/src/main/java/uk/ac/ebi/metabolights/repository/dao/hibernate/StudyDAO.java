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
import uk.ac.ebi.metabolights.repository.model.Study;

/**
 * User: conesa
 * Date: 21/01/15
 * Time: 13:18
 */
public class StudyDAO extends DAO <Study,StudyData>{

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
	protected Class getDataModelClass() {
		return StudyData.class;
	}

	@Override
	protected void preSave(StudyData study) throws DAOException {
		// Save users first
		UserDAO usersDAO = new UserDAO();
		usersDAO.setSession(this.session);

		usersDAO.save(study.getUsers());

	}

}
