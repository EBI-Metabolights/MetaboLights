/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-27
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabIdReplacer;
import uk.ac.ebi.metabolights.repository.dao.hibernate.AccessionDAO;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.security.SecurityService;
import uk.ac.ebi.metabolights.repository.utils.FileAuditUtil;
import uk.ac.ebi.metabolights.repository.utils.IsaTab2MetaboLightsConverter;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * User: conesa
 * Date: 27/01/15
 * Time: 08:33
 */
public class StudyDAO {

	private uk.ac.ebi.metabolights.repository.dao.hibernate.StudyDAO dbDAO = new uk.ac.ebi.metabolights.repository.dao.hibernate.StudyDAO();
	private uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO fsDAO;

	private static final Logger logger = LoggerFactory.getLogger(StudyDAO.class);



	public StudyDAO(String isaTabRootConfigurationFolder, String publicFolder, String privateFolder){

		fsDAO = new uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO(isaTabRootConfigurationFolder, publicFolder, privateFolder);
		dbDAO = new uk.ac.ebi.metabolights.repository.dao.hibernate.StudyDAO();

	}

	public Study getStudy(String studyIdentifier, String userToken, boolean includeMetabolites) throws DAOException {

		SecurityService.userAccessingStudy(studyIdentifier, userToken);

		Study study = dbDAO.findByAccession(studyIdentifier);

		if (study == null) {
			logger.warn("Requested study {} not found in the database.", studyIdentifier);
			return null;
		}

		try {

			 getStudyFromFileSystem(study,includeMetabolites);
		} catch (IsaTabException e) {
			logger.warn(e.getMessage(),e);
		}

		return study;
	}


	public Study getStudy(String studyIdentifier) throws DAOException, IsaTabException {

		return getStudy(studyIdentifier,"",false);
	}

	public Study getStudy(String studyIdentifier, String userToken) throws DAOException, IsaTabException {
		return getStudy(studyIdentifier,userToken,false);
	}

	public Study getStudyByObfuscationCode(String obfuscationCode, boolean includeMetabolites) throws DAOException, IsaTabException {

		Study study = dbDAO.findByObfuscationCode(obfuscationCode);

		return getStudyFromFileSystem(study, includeMetabolites);
	}


	private Study getStudyFromFileSystem(Study study, boolean includeMetabolites) throws DAOException, IsaTabException {

		fsDAO.fillStudy(includeMetabolites, study);

		return study;

	}

	private Study getStudyFromFileSystem(Study study, boolean includeMetabolites, File studyFolder) throws DAOException, IsaTabException {

		fsDAO.fillStudyFromFolder(includeMetabolites, study,studyFolder);

		return study;

	}


	public Study getStudyByObfuscationCode(String obfuscationCode) throws DAOException, IsaTabException {
		return getStudyByObfuscationCode(obfuscationCode,false);
	}

	public List<String> getList(String userToken) throws DAOException {
		return dbDAO.getStudyListForUser(userToken);
	}

	private String getAccessionNumber() throws DAOException {
		AccessionDAO accessionDAO = DAOFactory.getInstance().getAccessionDAO();

		// Using default prefix...we should change this to allow DEV IDs.
		return accessionDAO.getStableId();

	}

	public Study update(File submiisionFolder, String studyIdentifier, String userToken) throws Exception {
		return update(submiisionFolder,studyIdentifier,null, userToken);
	}

	public Study update(File submissionFolder, String studyIdentifier, Date newPublicReleaseDate,String userToken) throws Exception {

		// Security: Check if the user can add a study... it will throw and exception if not authorised
		User user = SecurityService.userUpdatingStudy(studyIdentifier,userToken);

		logger.info("{} update started by {}. Submission folder is {}",studyIdentifier, user.getFullName(), submissionFolder.getAbsolutePath());

		Study studyToUpdate = dbDAO.findByAccession(studyIdentifier);

		// If the Release date is not null, we update it.
		if (newPublicReleaseDate != null) studyToUpdate.setStudyPublicReleaseDate(newPublicReleaseDate);

		return saveOrUpdate(submissionFolder, studyToUpdate);

	}

	public Study add(File submissionFolder, Date publicReleaseDate, String userToken) throws Exception {

		// Security: Check if the user can add a study... it will throw and exception if not authorised
		User user = SecurityService.userCreatingStudy(userToken);

		logger.info("{} new submission. Submission folder is {}", user.getFullName(), submissionFolder.getAbsolutePath());

		// We need to move it to the final destination first.
		// For that we need a new id.
		String newStudyIdentifier = getAccessionNumber();

		// Create the study
		Study study = new Study();

		study.setStudyIdentifier(newStudyIdentifier);
		study.setStudyPublicReleaseDate(publicReleaseDate);
		// Status should be PRIVATE by default.
		// Add the user
		study.getUsers().add(user);

		return saveOrUpdate(submissionFolder,study);
	}


	private Study saveOrUpdate(File submissionFolder,  Study study) throws Exception {

		// Now we move file to the final location
		File finalDestination = moveStudyFolderToFinalDestination(study.getStudyIdentifier(), submissionFolder);

		String studyIdentifier = study.getStudyIdentifier();

		//Replace the id with the new id and other fields: Public Release Date
		IsaTabIdReplacer isaTabIdReplacer = new IsaTabIdReplacer(finalDestination.getAbsolutePath());
		isaTabIdReplacer.setPublicDate(study.getStudyPublicReleaseDate());
		isaTabIdReplacer.setStudyIdentifier(studyIdentifier);
		isaTabIdReplacer.setSubmissionDate(IsaTab2MetaboLightsConverter.date2IsaTabDate(new Date()));
		isaTabIdReplacer.execute();

		// Save it regardless
		dbDAO.save(study);

		// Load the study from the filesystem.
		try {
			getStudyFromFileSystem(study,true, finalDestination);

		} catch (Exception e) {

			logger.info("new isatab files for {} can't be loaded.  Error: {}", studyIdentifier, e.getMessage());
			throw new IsaTabException("Can't load the isatab files you have submitted. The study identifier is: " + studyIdentifier, e);
		}

		logger.info("Submission successfully processed: {}. From: {}",studyIdentifier, submissionFolder);

		return study;
	}

	private File moveStudyFolderToFinalDestination(String studyIdentifier, File studyFolder) throws IOException {

		//Calculate the destination (by default to private)
		File finalDestination =  fsDAO.getDestinationFolder(studyIdentifier);

		logger.info("Moving study folder ({}) to {}", studyFolder.getAbsolutePath(), finalDestination.getAbsolutePath());

		// Get the backupFolder
		File backUpFolder = FileAuditUtil.getBackUpFolder(finalDestination);

		// Go through the files to move
		for (File file : studyFolder.listFiles()) {

			FileAuditUtil.moveFileToAuditedFolder(file, finalDestination, backUpFolder);
		}

		return finalDestination;
	}


	public void delete(String studyIdentifier, String apiToken) throws DAOException, IOException {

		// Security: Check if the user can add a study... it will throw and exception if not authorised
		User user = SecurityService.userDeletingStudy(studyIdentifier,apiToken);

		logger.info("Deleting study {}, requested by ", studyIdentifier, user.getFullName());

		// Get the study from the DB
		Study dbData = dbDAO.findByAccession(studyIdentifier);

		// Get the folder where the study is.
		File studyFolder = fsDAO.getStudyFolder(studyIdentifier,dbData.isPublicStudy());

		FileUtils.deleteDirectory(studyFolder);

		// Delete the data from the database
		dbDAO.delete(dbData);

		logger.info("Study {} successfully deleted from filesystem and Database.", studyIdentifier);

	}
}



