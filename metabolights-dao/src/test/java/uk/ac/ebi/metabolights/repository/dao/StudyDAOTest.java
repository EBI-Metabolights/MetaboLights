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

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOTest;
import uk.ac.ebi.metabolights.repository.dao.hibernate.HibernateUtil;
import uk.ac.ebi.metabolights.repository.dao.hibernate.SessionWrapper;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.StudyData;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserData;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserDataTest;
import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.Study;

import java.util.Date;
import java.util.List;

import static junit.framework.Assert.*;

public class StudyDAOTest extends DAOTest {


	private StudyData publicStudy;
	private UserData curator;
	private UserData owner;
	private UserData notOwner;
	private StudyData privateStudy;
	private StudyDAO studyDAO;
	private StudyData dbOnlyStudy;
	// DB data plus folder with wrong isatab files....(so metadata doesn't load)
	private StudyData inconsistent;

	@Before
	public void init() throws DAOException {

		// Add MTBLS4 to the database (files misplaced intentionally)
		publicStudy = new StudyData();
		publicStudy.setAcc("MTBLS1");
		publicStudy.setStatus(Study.StudyStatus.PUBLIC.ordinal());
		publicStudy.setReleaseDate(new Date());
		publicStudy.setUpdateDate(new Date());
		publicStudy.setSubmissionDate(new Date());

		// Save all
		SessionWrapper session = HibernateUtil.getSession();
		session.needSession();
		session.saveOrUpdate(publicStudy);

		session.noNeedSession();

		// Initialise de DAO
		studyDAO = DAOFactory.getInstance().getStudyDAO();
	}

	public void initData() throws DAOException {

		// Add MTBLS4 to the database (files misplaced intentionally)
		publicStudy = new StudyData();
		publicStudy.setAcc("MTBLS1");
		publicStudy.setStatus(Study.StudyStatus.PUBLIC.ordinal());
		publicStudy.setReleaseDate(new Date());


		// Add MTBLSXXX to the database
		dbOnlyStudy = new StudyData();
		dbOnlyStudy.setAcc("MTBLSXXX");
		dbOnlyStudy.setStatus(Study.StudyStatus.PUBLIC.ordinal());


		// Add inconsistent (wrong isatab files or empty)
		inconsistent = new StudyData();
		inconsistent.setAcc("inconsistent");
		inconsistent.setStatus(Study.StudyStatus.PROVISIONAL.ordinal());


		// Add MTBLS3 to the database
		privateStudy = new StudyData();
		privateStudy.setAcc("MTBLS3");
		privateStudy.setStatus(Study.StudyStatus.PROVISIONAL.ordinal());

		// Add MTBLS2 to the database
		StudyData curatorsStudy = new StudyData();
		curatorsStudy.setAcc("MTBLS5");
		curatorsStudy.setStatus(Study.StudyStatus.PROVISIONAL.ordinal());

		// Get users: curator and owner and not owner (persisted already)
		curator = UserDataTest.addUserToDB(AppRole.ROLE_SUPER_USER);
		owner = UserDataTest.addUserToDB(AppRole.ROLE_SUBMITTER);
		notOwner = UserDataTest.addUserToDB(AppRole.ROLE_SUBMITTER);


		// Add the owner
		privateStudy.getUsers().add(owner);
		// Add the curator to have 2 owner and test unique results in getList().
		privateStudy.getUsers().add(curator);


		// Add owner to the inconsistent
		inconsistent.getUsers().add(owner);


		// Save all
		SessionWrapper session = HibernateUtil.getSession();
		session.needSession();
		session.saveOrUpdate(publicStudy);
		session.saveOrUpdate(privateStudy);
		session.saveOrUpdate(curatorsStudy);
		session.saveOrUpdate(dbOnlyStudy);
		session.saveOrUpdate(inconsistent);

		session.noNeedSession();

		// Initialise de DAO
		studyDAO = DAOFactory.getInstance().getStudyDAO();
	}

	@Test
	public void testGetPublicStudy() throws DAOException, IsaTabException {


		Study study = studyDAO.getStudy(publicStudy.getAcc());

		assertEquals("Test DB part it's been populated: obfuscation code", publicStudy.getObfuscationcode(), study.getObfuscationCode());
		assertEquals("Test DB part it's been populated: study status", publicStudy.getStatus(), study.getStudyStatus().ordinal());
		assertNotNull("Test FS part it's been populated: study title", study.getTitle());
		assertTrue("Test release date is the one from the DB and not from the file", DateUtils.isSameDay(publicStudy.getReleaseDate(), study.getStudyPublicReleaseDate()));

	}

	@Test
	public void testGetNonExistingStudy() {


		Study study = null;
		try {
			study = studyDAO.getStudy("foo");


			throw new AssertionError("Accessing a non existing study should throw an exception");

		} catch (DAOException e) {
			logger.info("Expected exception accessing non existing study");
		} catch (IsaTabException e) {
			e.printStackTrace();
		}


	}

	@Test
	public void testGetNonConsistentStudies() {


		Study study = null;
		try {
			study = studyDAO.getStudy(dbOnlyStudy.getAcc());

			throw new AssertionError("Accessing a db Only study should throw an exception");

		} catch (DAOException e) {
			logger.info("Expected exception accessing db only existing study");
		} catch (IsaTabException e) {
			e.printStackTrace();
		}


	}

	@Test
	public void testGetPrivateStudy() throws DAOException, IsaTabException {


		Study study = null;

		// Try with an anonymous user
		try {
			// Should fail
			studyDAO.getStudy(privateStudy.getAcc());
			throw new AssertionError("MTBLS3 is private and getStudy(\"Accession\") should throw an exception");

		} catch (DAOException e) {
			logger.info("Security exception expected");
		}

		// Try with the not owner user
		try {
			// Should fail
			studyDAO.getStudy(privateStudy.getAcc(), notOwner.getApiToken());
			throw new AssertionError("MTBLS3 is private and a not owner access should throw an exception");

		} catch (DAOException e) {
			logger.info("Security exception expected");
		}

		// Try with a made up user token
		try {
			// Should fail
			studyDAO.getStudy(privateStudy.getAcc(), java.util.UUID.randomUUID().toString());
			throw new AssertionError("MTBLS3 is private and a not existing user access should throw an exception");

		} catch (DAOException e) {
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
	public void testGetStudyByObfuscationCode() throws DAOException, IsaTabException {

		// Try with the obfuscation code
		Study study = studyDAO.getStudyByObfuscationCode(privateStudy.getObfuscationcode());

		assertEquals("Test DB part it's been populated: obfuscation code", privateStudy.getObfuscationcode(), study.getObfuscationCode());
		assertEquals("Test DB part it's been populated: study status", privateStudy.getStatus(), study.getStudyStatus().ordinal());
		assertNotNull("Test FS part it's been populated: study title", study.getTitle());

	}

	@Test
	public void testGetStudyListForUser() throws Exception {

		List studies = studyDAO.getList(owner.getApiToken());
		assertEquals("Owner can access 3 out of 4 studies (2 public and owned private)", 3, studies.size());

		studies = studyDAO.getList(curator.getApiToken());
		assertEquals("Curator should access 4 (all) studies", 4, studies.size());

		studies = studyDAO.getList(notOwner.getApiToken());
		assertEquals("not Owner should access 2 studies (public ones)", 2, studies.size());


	}
	
	@Test
	public void testStatusesForSubmitter() throws Exception {


		// Get the full study
		Study study = studyDAO.getStudy(inconsistent.getAcc(), owner.getApiToken());

		assertEquals("Default status of a study must be provisional", LiteStudy.StudyStatus.PROVISIONAL, study.getStudyStatus());

		// Change the statuses as owner
		Study savedStudy = updateStatus(study, owner, LiteStudy.StudyStatus.PRIVATE, false);
		updateStatus(study, owner, LiteStudy.StudyStatus.INREVIEW, true);
		updateStatus(study, owner, LiteStudy.StudyStatus.PUBLIC, true);

		updateStatus(study, notOwner, LiteStudy.StudyStatus.PROVISIONAL, true);
		updateStatus(study, notOwner, LiteStudy.StudyStatus.PRIVATE, true);
		updateStatus(study, notOwner, LiteStudy.StudyStatus.INREVIEW, true);
		updateStatus(study, notOwner, LiteStudy.StudyStatus.PUBLIC, true);
		assertFalse("Public release date has been changed", DateUtils.isSameDay(new Date(), savedStudy.getStudyPublicReleaseDate()));

		// Change the status as curator
		updateStatus(study, curator, LiteStudy.StudyStatus.PRIVATE, false);

		// Change the statuses as owner, shouldn't be allowed.
		updateStatus(study, owner, LiteStudy.StudyStatus.PROVISIONAL, true);
		updateStatus(study, owner, LiteStudy.StudyStatus.PRIVATE, true);
		updateStatus(study, owner, LiteStudy.StudyStatus.INREVIEW, true);
		updateStatus(study, owner, LiteStudy.StudyStatus.PUBLIC, true);

		updateStatus(study, notOwner, LiteStudy.StudyStatus.PROVISIONAL, true);
		updateStatus(study, notOwner, LiteStudy.StudyStatus.PRIVATE, true);
		updateStatus(study, notOwner, LiteStudy.StudyStatus.INREVIEW, true);
		updateStatus(study, notOwner, LiteStudy.StudyStatus.PUBLIC, true);
		assertFalse("Public release date has been changed", DateUtils.isSameDay(new Date(), savedStudy.getStudyPublicReleaseDate()));


		updateStatus(study, curator, LiteStudy.StudyStatus.INREVIEW, false);
		// Change the statuses as owner, shouldn't be allowed.
		updateStatus(study, owner, LiteStudy.StudyStatus.PROVISIONAL, true);
		updateStatus(study, owner, LiteStudy.StudyStatus.PRIVATE, true);
		updateStatus(study, owner, LiteStudy.StudyStatus.INREVIEW, true);
		updateStatus(study, owner, LiteStudy.StudyStatus.PUBLIC, true);

		updateStatus(study, notOwner, LiteStudy.StudyStatus.PROVISIONAL, true);
		updateStatus(study, notOwner, LiteStudy.StudyStatus.PRIVATE, true);
		updateStatus(study, notOwner, LiteStudy.StudyStatus.INREVIEW, true);
		updateStatus(study, notOwner, LiteStudy.StudyStatus.PUBLIC, true);

		assertFalse("Public release date has been changed", DateUtils.isSameDay(new Date(), savedStudy.getStudyPublicReleaseDate()));


		// Make it public
		savedStudy = updateStatus(study, curator, LiteStudy.StudyStatus.PUBLIC, false);

		assertTrue("Public release date not updated when changing study to public", DateUtils.isSameDay(new Date(), savedStudy.getStudyPublicReleaseDate()));

		// Change the statuses as owner, shouldn't be allowed.
		updateStatus(study, owner, LiteStudy.StudyStatus.PROVISIONAL, true);
		updateStatus(study, owner, LiteStudy.StudyStatus.PRIVATE, true);
		updateStatus(study, owner, LiteStudy.StudyStatus.INREVIEW, true);
		updateStatus(study, owner, LiteStudy.StudyStatus.PUBLIC, true);

		updateStatus(study, notOwner, LiteStudy.StudyStatus.PROVISIONAL, true);
		updateStatus(study, notOwner, LiteStudy.StudyStatus.PRIVATE, true);
		updateStatus(study, notOwner, LiteStudy.StudyStatus.INREVIEW, true);
		updateStatus(study, notOwner, LiteStudy.StudyStatus.PUBLIC, true);


		// Make study Private again
		updateStatus(study, curator, LiteStudy.StudyStatus.PROVISIONAL, false);

		// Change the release date to yesterday
		studyDAO.updateReleaseDate(inconsistent.getAcc(), new Date(new Date().getTime()-DateUtils.MILLIS_PER_DAY),owner.getApiToken());

		// Make study PRIVATE again
		savedStudy = updateStatus(study, owner, LiteStudy.StudyStatus.PRIVATE, false);

		assertFalse("Public release date updated when changing study to PRIVATE and date has passed", DateUtils.isSameDay(new Date(), savedStudy.getStudyPublicReleaseDate()));

		// Make study READY again this should change de public release date to today and the status to public
		updateStatus(savedStudy, curator, LiteStudy.StudyStatus.INREVIEW, false);


	}

	private Study updateStatus(Study study, UserData user, LiteStudy.StudyStatus newStatus, boolean exceptionExpected) throws IsaTabException {
		try {
			// Should fail
			// Change the status to PUBLIC.
			studyDAO.updateStatus(study.getStudyIdentifier(), newStatus, user.getApiToken());

			if (exceptionExpected) {
				throw new AssertionError("User " + user.getUserName() + " should NOT be allowed to update the study status from " + study.getStudyStatus() + " to " + newStatus);
			} else {

				// Check status has changed
				Study savedStudy = studyDAO.getStudy(study.getStudyIdentifier(), user.getApiToken());

				// For approved we need to check promotion to public.
				if (newStatus == LiteStudy.StudyStatus.INREVIEW && study.getStudyPublicReleaseDate().before(new Date())) {

					assertTrue("Public release not updated when changing study to INREVIEW and date has passed", DateUtils.isSameDay(new Date(), savedStudy.getStudyPublicReleaseDate()));
					assertEquals("Status not promoted to PUBLIC when changing study to INREVIEW and date has passed", LiteStudy.StudyStatus.PUBLIC, savedStudy.getStudyStatus());


				} else {
					assertEquals("Study status hasn't been persisted", newStatus, savedStudy.getStudyStatus());
				}


				return savedStudy;
			}

		} catch (DAOException e) {

			if (exceptionExpected) {
				logger.info("Security exception expected");
			} else {
				throw new AssertionError("User " + user.getUserName() + " should be allowed to update the study status from " + study.getStudyStatus() + " to " + newStatus);
			}
		}

		return null;
	}
	@Test
	public void testGetStudiesToGoLive() throws DAOException {


		// Change status ot the 2 public studies
		studyDAO.updateStatus(dbOnlyStudy.getAcc(), LiteStudy.StudyStatus.INREVIEW, curator.getApiToken());

		List<String> studiesToGoLive = studyDAO.getStudiesToGoLiveList(curator.getApiToken());

		assertEquals("Wrong number of studies to go live for a curator", 0, studiesToGoLive.size());


	}

	@Test
	public void testGetStudyIdByObfuscationCode() throws IsaTabException, DAOException {

		String studyId = studyDAO.getStudyIdByObfuscationCode(privateStudy.getObfuscationcode());

		assertEquals("StudyId returned by obfuscation code is wrong", privateStudy.getAcc(),studyId);



	}
}