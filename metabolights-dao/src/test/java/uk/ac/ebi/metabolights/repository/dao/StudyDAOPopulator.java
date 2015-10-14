/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-28
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

package uk.ac.ebi.metabolights.repository.dao;

import org.junit.Test;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOTest;
import uk.ac.ebi.metabolights.repository.dao.hibernate.HibernateUtil;
import uk.ac.ebi.metabolights.repository.dao.hibernate.SessionWrapper;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.StudyData;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserData;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserDataTest;
import uk.ac.ebi.metabolights.repository.model.AppRole;

import java.io.File;

public class StudyDAOPopulator extends DAOTest {


	private UserData curator;
	private UserData owner;
	private UserData notOwner;


	@Test
	public void initData() throws DAOException {


		// Get users: curator and owner and not owner (persisted already)
		curator = UserDataTest.addUserToDB(AppRole.ROLE_SUPER_USER);
		owner = UserDataTest.addUserToDB(AppRole.ROLE_SUBMITTER);
		notOwner = UserDataTest.addUserToDB(AppRole.ROLE_SUBMITTER);


		String studiesFolderPath = "/nfs/public/rw/homes/tc_cm01/metabolights/dev/studies/";

		File studiesFolder = new File(studiesFolderPath);

		// Save all
		SessionWrapper session = HibernateUtil.getSession();
		session.needSession();

		File[] studies = studiesFolder.listFiles();

		for (int i = 0; i < studies.length; i++) {

			File studyFolder = studies[i];
			StudyData newStudy = new StudyData();

			newStudy.setAcc(studyFolder.getName());
			newStudy.getUsers().add(owner);
			session.save(newStudy);

		}


		session.noNeedSession();


	}

}