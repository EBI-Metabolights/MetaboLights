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

import junit.framework.Assert;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOTest;
import uk.ac.ebi.metabolights.repository.dao.hibernate.HibernateUtil;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.SessionWrapper;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.StudyData;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserData;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserDataTest;
import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.Study;

public class StudyDAOTest extends DAOTest {


	@Test
	public void testGetPublicStudy() throws DAOException {


		// Add MTBLS1 to the database
		StudyData studyData = new StudyData();
		studyData.setAcc("MTBLS1");
		studyData.setStatus(Study.StudyStatus.PUBLIC.ordinal());


		// Save it
		SessionWrapper session = HibernateUtil.getSession();
		session.needSession();
		session.saveOrUpdate(studyData);
		session.noNeedSession();


		// Use now the Hybrid DAO...
		StudyDAO studyDAO = DAOFactory.getInstance().getStudyDAO();

		Study study = studyDAO.getStudy("MTBLS1");

		Assert.assertEquals("Test DB part it's been populated: obfuscation code", studyData.getObfuscationcode(), study.getObfuscationCode());
		Assert.assertEquals("Test DB part it's been populated: study status", studyData.getStatus(), study.getStudyStatus().ordinal());
		Assert.assertNotNull("Test FS part it's been populated: study title", study.getTitle());

	}

	@Test
	public void testGetPrivateStudy() throws DAOException {


		// Get users: curator and owner and not owner (persisted already)
		UserData curator = UserDataTest.addUserToDB(AppRole.ROLE_SUPER_USER);
		UserData owner = UserDataTest.addUserToDB(AppRole.ROLE_SUBMITTER);
		UserData notOwner = UserDataTest.addUserToDB(AppRole.ROLE_SUBMITTER);

		// Add MTBLS3 to the database
		StudyData studyData = new StudyData();
		studyData.setAcc("MTBLS3");
		studyData.setStatus(Study.StudyStatus.PRIVATE.ordinal());

		// Add the owner
		studyData.getUsers().add(owner);

		// Save it
		SessionWrapper session = HibernateUtil.getSession();
		session.needSession();
		session.saveOrUpdate(studyData);
		session.noNeedSession();

		// Use now the Hybrid DAO...
		StudyDAO studyDAO = DAOFactory.getInstance().getStudyDAO();

		Study study = null;

		// Try with an anonymous user
		try {
			// Should fail
			studyDAO.getStudy(studyData.getAcc());
			throw new AssertionError("MTBLS3 is private and getStudy(\"Accession\") should throw an exception");

		} catch (SecurityException e) {
			logger.info("Security exception expected");
		}

		// Try with the not owner user
		try {
			// Should fail
			studyDAO.getStudy(studyData.getAcc(), notOwner.getApiToken());
			throw new AssertionError("MTBLS3 is private and a not owner access should throw an exception");

		} catch (SecurityException e) {
			logger.info("Security exception expected");
		}

		// Try with the curator
		// Shouldn't fail
		studyDAO.getStudy(studyData.getAcc(),curator.getApiToken());

		// Try with the owner
		// Shouldn't fail
		studyDAO.getStudy(studyData.getAcc(),owner.getApiToken());

	}

	@Test
	public void testGetStudyByObfuscationCode() throws DAOException {

		// Add MTBLS3 to the database
		StudyData studyData = new StudyData();
		studyData.setAcc("MTBLS3");

		studyData.setStatus(Study.StudyStatus.PRIVATE.ordinal());

		// Save it
		SessionWrapper session = HibernateUtil.getSession();
		session.needSession();
		session.saveOrUpdate(studyData);
		session.noNeedSession();

		// Use now the Hybrid DAO...
		StudyDAO studyDAO = DAOFactory.getInstance().getStudyDAO();

		Study study = null;

		// Try with the obfuscationcode
		study = studyDAO.getStudyByObfuscationCode(studyData.getObfuscationcode());

		Assert.assertEquals("Test DB part it's been populated: obfuscation code", studyData.getObfuscationcode(), study.getObfuscationCode());
		Assert.assertEquals("Test DB part it's been populated: study status", studyData.getStatus(), study.getStudyStatus().ordinal());
		Assert.assertNotNull("Test FS part it's been populated: study title", study.getTitle());

	}
}