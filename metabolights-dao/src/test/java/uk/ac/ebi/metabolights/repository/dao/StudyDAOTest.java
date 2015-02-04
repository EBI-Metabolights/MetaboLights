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
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOTest;
import uk.ac.ebi.metabolights.repository.dao.hibernate.HibernateUtil;
import uk.ac.ebi.metabolights.repository.dao.hibernate.SessionWrapper;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.StudyData;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserData;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserDataTest;
import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.Study;

import java.util.List;

public class StudyDAOTest extends DAOTest {


	private static final StudyData publicStudy = new StudyData();
	private UserData curator;
	private UserData owner;
	private UserData notOwner;
	private StudyData privateStudy;
	private StudyDAO studyDAO;

	@Before
	public void initData() throws DAOException {

		// Add MTBLS1 to the database
		publicStudy.setAcc("MTBLS1");
		publicStudy.setStatus(Study.StudyStatus.PUBLIC.ordinal());


		// Get users: curator and owner and not owner (persisted already)
		curator = UserDataTest.addUserToDB(AppRole.ROLE_SUPER_USER);
		owner = UserDataTest.addUserToDB(AppRole.ROLE_SUBMITTER);
		notOwner = UserDataTest.addUserToDB(AppRole.ROLE_SUBMITTER);

		// Add MTBLS3 to the database
		privateStudy = new StudyData();
		privateStudy.setAcc("MTBLS3");
		privateStudy.setStatus(Study.StudyStatus.PRIVATE.ordinal());

		// Add MTBLS5 to the database (no diles needed so far
		StudyData curatorsStudy = new StudyData();
		curatorsStudy.setAcc("MTBLS5");
		curatorsStudy.setStatus(Study.StudyStatus.PRIVATE.ordinal());


		// Add the owner
		privateStudy.getUsers().add(owner);
		// Add the curator to have 2 owner and test unique results in getList().
		privateStudy.getUsers().add(curator);

		// Save all
		SessionWrapper session = HibernateUtil.getSession();
		session.needSession();
		session.saveOrUpdate(publicStudy);
		session.saveOrUpdate(privateStudy);
		session.saveOrUpdate(curatorsStudy);
		session.noNeedSession();


		// Initialise de DAO
		studyDAO = DAOFactory.getInstance().getStudyDAO();
	}

	@Test
	public void testGetPublicStudy() throws DAOException {


		Study study = studyDAO.getStudy(publicStudy.getAcc());

		Assert.assertEquals("Test DB part it's been populated: obfuscation code", publicStudy.getObfuscationcode(), study.getObfuscationCode());
		Assert.assertEquals("Test DB part it's been populated: study status", publicStudy.getStatus(), study.getStudyStatus().ordinal());
		Assert.assertNotNull("Test FS part it's been populated: study title", study.getTitle());

	}

	@Test
	public void testGetPrivateStudy() throws DAOException {


		Study study = null;

		// Try with an anonymous user
		try {
			// Should fail
			studyDAO.getStudy(privateStudy.getAcc());
			throw new AssertionError("MTBLS3 is private and getStudy(\"Accession\") should throw an exception");

		} catch (SecurityException e) {
			logger.info("Security exception expected");
		}

		// Try with the not owner user
		try {
			// Should fail
			studyDAO.getStudy(privateStudy.getAcc(), notOwner.getApiToken());
			throw new AssertionError("MTBLS3 is private and a not owner access should throw an exception");

		} catch (SecurityException e) {
			logger.info("Security exception expected");
		}

		// Try with the curator
		// Shouldn't fail
		studyDAO.getStudy(privateStudy.getAcc(), curator.getApiToken());

		// Try with the owner
		// Shouldn't fail
		studyDAO.getStudy(privateStudy.getAcc(), owner.getApiToken());

	}

	@Test
	public void testGetStudyByObfuscationCode() throws DAOException {


		Study study = null;

		// Try with the obfuscationcode
		study = studyDAO.getStudyByObfuscationCode(privateStudy.getObfuscationcode());

		Assert.assertEquals("Test DB part it's been populated: obfuscation code", privateStudy.getObfuscationcode(), study.getObfuscationCode());
		Assert.assertEquals("Test DB part it's been populated: study status", privateStudy.getStatus(), study.getStudyStatus().ordinal());
		Assert.assertNotNull("Test FS part it's been populated: study title", study.getTitle());

	}

	@Test
	public void testGetStudyListForUser() throws Exception {

		List studies = studyDAO.getList(owner.getApiToken());
		Assert.assertEquals("Owner can access 2 out of 3 studies (public and owned private", 2, studies.size());

		studies = studyDAO.getList(curator.getApiToken());
		Assert.assertEquals("Curator should access 3 (all) studies" , 3, studies.size());

		studies = studyDAO.getList(notOwner.getApiToken());
		Assert.assertEquals("not Owner should access 1 study (public one)" , 1, studies.size());


	}
}