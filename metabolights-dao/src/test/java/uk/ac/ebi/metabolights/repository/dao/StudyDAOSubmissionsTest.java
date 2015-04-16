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

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.AccessionDAO;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOTest;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserData;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserDataTest;
import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.utils.FileAuditUtil;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class StudyDAOSubmissionsTest extends DAOTest {


	private UserData curator;
	private UserData owner;
	private UserData notOwner;
	private StudyDAO studyDAO;

	@Before
	public void initData() throws DAOException {


		// Get users: curator and owner and not owner (persisted already)
		curator = UserDataTest.addUserToDB(AppRole.ROLE_SUPER_USER);
		owner = UserDataTest.addUserToDB(AppRole.ROLE_SUBMITTER);
		notOwner = UserDataTest.addUserToDB(AppRole.ROLE_SUBMITTER);


		AccessionDAO.setDefaultPrefix(StudyDAOSubmissionsTest.class.getSimpleName() + "_");

		// Initialise de DAO
		studyDAO = DAOFactory.getInstance().getStudyDAO();
	}

	@Test
	public void testCRUDStudy() throws Exception {


		File submissionFolder = getStudyFolderToSubmit("MTBLS1", true);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		Date publicReleaseDate = cal.getTime();

		Study study = studyDAO.add(submissionFolder, publicReleaseDate, curator.getApiToken());

		assertNotNull("studyAccession filled up", study.getStudyIdentifier());
		assertNotNull("Accession starts with prefix", study.getStudyIdentifier().startsWith(AccessionDAO.getDefaultPrefix()));
		assertTrue("Public release date set properly", DateUtils.isSameDay(study.getStudyPublicReleaseDate(), publicReleaseDate));
		assertEquals("All files are moved",0,submissionFolder.list().length);

		// Test READ...
		Study readStudy = studyDAO.getStudy(study.getStudyIdentifier(), curator.getApiToken());

		assertNotNull("Reading study just added", readStudy);


		// Test a failure submission
		File emptyInvestigationFile = new File(submissionFolder, "i_Investigation.txt");
		emptyInvestigationFile.createNewFile();


		try {
			studyDAO.update(submissionFolder,readStudy.getStudyIdentifier(),curator.getApiToken());
			assertTrue("Update with invalid isaTab files should throw an exception", false);
		} catch (IsaTabException e) {
			// Expected
		}


		// Test audit has happened
		File auditFolder = FileAuditUtil.getAuditFolder(uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO.getDestinationFolder(readStudy.getStudyIdentifier()));

		assertEquals("Audit folder has a subfolder", 1, auditFolder.list().length);


		// Test the deletion
		testDeletion(readStudy);


	}

	public void testDeletion(Study readStudy) throws IOException, DAOException, IsaTabException {

		// Test delete by anonymous
		try {
			studyDAO.delete(readStudy.getStudyIdentifier(), "lalala");

			assertTrue("Anonymous user shouldn't be able to delete a study", false);

		} catch (DAOException e){
			// Expected
			logger.info("Anonymous can't delete a study.");
		}


		// Test delete by notOwner
		try {
			studyDAO.delete(readStudy.getStudyIdentifier(), notOwner.getApiToken());

			assertTrue("A not owner shouldn't be able to delete a study", false);

		} catch (DAOException e){
			// Expected
			logger.info("A not owner can't delete a study either.");
		}

		// Test delete by owner
		studyDAO.delete(readStudy.getStudyIdentifier(), curator.getApiToken());

		assertNull("Study folder no longer exists", uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO.getStudyFolder(readStudy.getStudyIdentifier(),readStudy.isPublicStudy()));
		assertNull("Study is not longer in the database", studyDAO.getStudy(readStudy.getStudyIdentifier(), curator.getApiToken()));

		logger.info("Study {} deleted by curator", readStudy.getStudyIdentifier());
	}

	private File getStudyFolderToSubmit(String studyFolder, boolean isPublic) throws IOException {

		String parentFolder = isPublic?publicStudiesLocation:privateStudiesLocation;

		File sourceFolder = new File(parentFolder, studyFolder);

		File destination = new File(FileUtils.getTempDirectory(), studyFolder);
		destination.delete();

		FileUtils.copyDirectory(sourceFolder, destination);

		return destination;

	}





}