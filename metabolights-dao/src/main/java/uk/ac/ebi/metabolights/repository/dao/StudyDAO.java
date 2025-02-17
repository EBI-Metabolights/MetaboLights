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

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabReplacer;
import uk.ac.ebi.metabolights.repository.dao.hibernate.AccessionDAO;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.Backup;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validations;
import uk.ac.ebi.metabolights.repository.security.SecurityService;
import uk.ac.ebi.metabolights.repository.utils.FileAuditUtil;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO.getStudyFolder;

/**
 * User: conesa
 * Date: 27/01/15
 * Time: 08:33
 */
public class StudyDAO {

    private uk.ac.ebi.metabolights.repository.dao.hibernate.StudyDAO dbDAO = new uk.ac.ebi.metabolights.repository.dao.hibernate.StudyDAO();
    private uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO fsDAO;

    private static final Logger logger = LoggerFactory.getLogger(StudyDAO.class);


    public StudyDAO(String isaTabRootConfigurationFolder, String studiesFolder) {

        fsDAO = new uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO(isaTabRootConfigurationFolder, studiesFolder);
        dbDAO = new uk.ac.ebi.metabolights.repository.dao.hibernate.StudyDAO();

    }

    public Study getStudy(String studyIdentifier, String userToken, boolean includeMetabolites) throws DAOException {

        SecurityService.userAccessingStudy(studyIdentifier, userToken);
        Study study = dbDAO.findByAccession(studyIdentifier);

        if (study == null) {
            logger.warn("Requested study {} not found in the database.", studyIdentifier);
            return null;
        }

        getStudyFromFileSystem(study, includeMetabolites);
        return study;
    }


    public Study getStudy(String studyIdentifier) throws DAOException, IsaTabException {
        return getStudy(studyIdentifier, "", false);
    }

    public Study getStudy(String studyIdentifier, String userToken) throws DAOException, IsaTabException {
        return getStudy(studyIdentifier, userToken, false);
    }

    public Study getStudyByObfuscationCode(String obfuscationCode, boolean includeMetabolites) throws DAOException {
        Study study = dbDAO.findByObfuscationCode(obfuscationCode);
        return getStudyFromFileSystem(study, includeMetabolites);
    }


    private Study getStudyFromFileSystem(Study study, boolean includeMetabolites) throws DAOException {
        fsDAO.fillStudy(includeMetabolites, study);
        return study;
    }

    public Study getStudyFromDatabase(String studyId) throws DAOException {
        //Calculate the destination (by default to private)
        logger.info("Loading study from database and adding study location");
        File studyLocation = uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO.getDestinationFolder(studyId);
        logger.info(" - Study location is: "+ studyLocation);
        Study study = dbDAO.findByAccession(studyId);

        if (study != null && studyLocation != null) {
            study.setStudyLocation(studyLocation.getAbsolutePath());
            logger.info(" - Study accession from database is: "+ study.getStudyIdentifier());
        } else {
            logger.error("Could not load study for acc "+ studyId);
        }
        return study;
    }

    private Study getStudyFromFileSystem(Study study, boolean includeMetabolites, File studyFolder) throws DAOException, IsaTabException {
        fsDAO.fillStudyFromFolder(includeMetabolites, study, studyFolder);
        return study;
    }


    public Study getStudyByObfuscationCode(String obfuscationCode) throws DAOException, IsaTabException {
        return getStudyByObfuscationCode(obfuscationCode, false);
    }

    public String getStudyIdByObfuscationCode(String obfuscationCode) throws DAOException, IsaTabException {
        String studyId = dbDAO.findStudyIdByObfuscationCode(obfuscationCode);
        return studyId;
    }


    public List<String> getList(String userToken) throws DAOException {
        return dbDAO.getStudyListForUser(userToken);
    }

    public List<String> getPrivateStudyListForUser(String userToken) throws DAOException {
        return dbDAO.getPrivateStudyListForUser(userToken);
    }

    public String getListWithDetails(String userToken) throws DAOException {

        List<String> studies = dbDAO.getStudyListForUser(userToken);

        JSONArray ja = new JSONArray();

        for(String study :studies){
            Study tempStudy = dbDAO.findByAccession(study);
            getStudyFromFileSystem(tempStudy, false);
            JSONObject jo = new JSONObject();
            jo.put("id",study);
            jo.put("title", tempStudy.getTitle());
            jo.put("description", tempStudy.getDescription());
            ja.add(jo);
        }

        JSONObject mainObj = new JSONObject();
        mainObj.put("content", ja);

        return mainObj.toJSONString();
    }

    public String getCompleteStudyListForUserWithDetails(String userToken) throws DAOException {

        List<String> studies = dbDAO.getCompleteStudyListForUser(userToken);

        JSONArray ja = new JSONArray();

        for(String study :studies){
            Study tempStudy = dbDAO.findByAccession(study);
            getStudyFromFileSystem(tempStudy, false);
            JSONObject jo = new JSONObject();
            jo.put("id",study);
            jo.put("title", tempStudy.getTitle());
            jo.put("description", tempStudy.getDescription());
            ja.add(jo);
        }

        return ja.toJSONString();
    }

    public List<String> getStudiesToGoLiveList(String userToken) throws DAOException {
        return dbDAO.getStudiesToGoLiveList(userToken);
    }

	public List<String> getStudiesToGoLiveList(String userToken, int numbeOfDays) throws DAOException {
		return dbDAO.getStudiesToGoLiveList(userToken, numbeOfDays);
	}

    private String getAccessionNumber() throws DAOException {
        AccessionDAO accessionDAO = DAOFactory.getInstance().getAccessionDAO();
        // Using default prefix...we should change this to allow DEV IDs.
        return accessionDAO.getStableId();
    }

    public Study update(File submiisionFolder, String studyIdentifier, String userToken) throws Exception {
        return update(submiisionFolder, studyIdentifier, null, userToken);
    }

    public Study update(File submissionFolder, String studyIdentifier, Date newPublicReleaseDate, String userToken) throws Exception {

        // Security: Check if the user can add a study... it will throw and exception if not authorised
        User user = SecurityService.userUpdatingStudy(studyIdentifier, userToken);

        logger.info("{} update started by {}. Submission folder is {}", studyIdentifier, user.getFullName(), submissionFolder.getAbsolutePath());

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
        study.setStudySubmissionDate(new Date());
        // Add the user
        study.getUsers().add(user);

        return saveOrUpdate(submissionFolder, study);
    }

    public Study addEmptyStudy(Date publicReleaseDate, User user) throws Exception {

        logger.info("{} new submission of an empty study", user.getFullName());

        String newStudyIdentifier = getAccessionNumber();

        // Create the study
        Study study = new Study();
        study.setStudyIdentifier(newStudyIdentifier);
        study.setStudyPublicReleaseDate(publicReleaseDate);
        study.setStudySubmissionDate(new Date());
        study.getUsers().add(user);

        dbDAO.save(study);

        return study;
    }

    private Study saveOrUpdate(File submissionFolder, Study study) throws Exception {

        // Now we move file to the final location, this is creating a backup
        File finalDestination = moveStudyFolderToFinalDestination(study.getStudyIdentifier(), submissionFolder);

        String studyIdentifier = study.getStudyIdentifier();

        // And this will create a back up again for the i_investigation file..if the destination exist an exception is thrown
        // Giving 1 second to get a new audit folder name.
        Thread.sleep(1000);

        //Replace the id with the new id and other fields: Public Release Date
        IsaTabReplacer isaTabIdReplacer = new IsaTabReplacer(finalDestination.getAbsolutePath());
        isaTabIdReplacer.setPublicDate(study.getStudyPublicReleaseDate());
        isaTabIdReplacer.setStudyIdentifier(studyIdentifier);
        isaTabIdReplacer.setSubmissionDate(study.getStudySubmissionDate());

        try {
            isaTabIdReplacer.execute();
        }
        catch(Exception e){
             logger.error(e.getMessage());
             study.getIsatabErrorMessages().add(e.getMessage());
        }

        // Save it regardless
        dbDAO.save(study);

        // Load the study from the filesystem.
        try {
            getStudyFromFileSystem(study, true, finalDestination);

        } catch (Exception e) {

            logger.info("new isatab files for {} can't be loaded.  Error: {}", studyIdentifier, e.getMessage());
            study.getIsatabErrorMessages().add(e.getMessage());
            study.getIsatabErrorMessages().add("Can't load the isatab files you have submitted. The study identifier is: " + studyIdentifier);
            throw new IsaTabException("Can't load the isatab files you have submitted. The study identifier is: " + studyIdentifier, e);
        }

        logger.info("Submission successfully processed: {}. From: {}", studyIdentifier, submissionFolder);

        return study;
    }

    private File moveStudyFolderToFinalDestination(String studyIdentifier, File studyFolder) throws IOException {

        //Calculate the destination (by default to private)
        File finalDestination = uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO.getDestinationFolder(studyIdentifier);

        logger.info("Moving study folder ({}) to {}", studyFolder.getAbsolutePath(), finalDestination.getAbsolutePath());

        // We need to copy, since submissions could be partial...(only maf file submitted)
        FileAuditUtil.moveFolderContentToAuditFolder(studyFolder, finalDestination, true);

//		// Get the backupFolder
//		File backUpFolder = FileAuditUtil.getBackUpFolder(finalDestination);
//
//		// Go through the files to move
//		for (File file : studyFolder.listFiles()) {
//
//			FileAuditUtil.moveFileToAuditedFolder(file, finalDestination, backUpFolder, false);
//		}

        return finalDestination;
    }


    public void delete(String studyIdentifier, String apiToken) throws DAOException, IOException, InterruptedException {

        // Security: Check if the user can delete a study... it will throw and exception if not authorised
        User user = SecurityService.userDeletingStudy(studyIdentifier, apiToken);

        logger.info("Deleting study {}, requested by {}", studyIdentifier, user.getFullName());

        // Get the study from the DB
        Study dbData = dbDAO.findByAccession(studyIdentifier);

        if (dbData == null) {
            logger.info("Study {} does not exist in the database.", studyIdentifier);
            throw new DAOException("Study " + studyIdentifier + " does not exist in the database.");
        }

        // Get the folder where the study is.
        File studyFolder = getStudyFolder(studyIdentifier);

        boolean success = false;
        int delay = 60, loops = 0;
        while (!success || loops == 5) {
            try {
                loops++;
                FileUtils.deleteDirectory(studyFolder);
                success = true; // Folder was deleted
            } catch (IOException e) {
                logger.error("Study " + studyIdentifier + " could not be deleted; .nfs files locking. Trying again in "+delay+" seconds");
                TimeUnit.SECONDS.sleep(delay);
            }
        }

        // Delete the data from the database
        dbDAO.delete(dbData);

        logger.info("Study {} successfully deleted from filesystem and Database.", studyIdentifier);

    }

    public void updateReleaseDate(String studyIdentifier, Date newPublicReleaseDate, String userToken) throws DAOException {

        // Security: Check if the user can edit the study
        SecurityService.userUpdatingStudy(studyIdentifier, userToken);

        Study study = dbDAO.findByAccession(studyIdentifier);

        // Change the date in the database
        study.setStudyPublicReleaseDate(newPublicReleaseDate);
        dbDAO.save(study);

        // Get the location of the study
        File studyFolder = getStudyFolder(studyIdentifier);

        try {
            // Change the file....NOTE: This will not be audited....unless we implement some kind of audit system in the replacer
            //Replace the id with the new id and other fields: Public Release Date
            IsaTabReplacer isaTabReplacer = new IsaTabReplacer(studyFolder.getAbsolutePath());
            isaTabReplacer.setPublicDate(newPublicReleaseDate);
            isaTabReplacer.execute();

        } catch (Exception e) {
            logger.warn("Couldn't replace release date in isatab files.", e);
            study.getIsatabErrorMessages().add(e.getMessage());
            study.getIsatabErrorMessages().add("Couldn't replace release date in isatab files.");
        }

        logger.info("{} public release date successfully updated", studyIdentifier);

    }

    public Study updateStatus(String studyIdentifier, LiteStudy.StudyStatus newStatus, String userToken) throws DAOException {

        // Security: Check if the user can edit the study
        SecurityService.userUpdatingStudy(studyIdentifier, userToken, newStatus);

        // Find the study data from the DB
        Study study = dbDAO.findByAccession(studyIdentifier);
        fsDAO.fillStudy(false, study);

        // If new status is INREVIEW
        if (newStatus == LiteStudy.StudyStatus.INREVIEW) {
            //...and release date has passed...promote it to PUBLIC
            if (study.getStudyPublicReleaseDate().before(new Date())) {
                newStatus = LiteStudy.StudyStatus.PUBLIC;
            }
        }

        // Change the status
        // Change to Private, based on the study validation.
//        if (newStatus == LiteStudy.StudyStatus.PRIVATE) {
//            if (!user.isCurator() && study.getValidations().getStatus() != Status.GREEN) {
//        if (!user.isCurator() && !study.getValidations().isPassedMinimumRequirement()) {
//                logger.warn("The study is not valid and you don't have enough privileges to change the status to PRIVATE");
//                throw new DAOException("The study is not valid and you don't have enough privileges to change the status to PRIVATE");
//            }
//        }
//
//        study.setStudyStatus(newStatus);
//        logger.info("status changed: " + newStatus);
//
//        if (newStatus == LiteStudy.StudyStatus.PUBLIC) {
//            study.setStudyPublicReleaseDate(new Date());
//        }

        dbDAO.save(study);
        return study;

    }

    public void restoreBackup(String studyIdentifier, String userToken, String backUpIdentifier) throws DAOException, IOException {

        // Security: Check if the user can edit the study
        SecurityService.userUpdatingStudy(studyIdentifier, userToken);

        // Restore back up...

        // Get the StudyFolder
        File studyFolder = getStudyFolder(studyIdentifier);

        // Get the list of backup
        Collection<Backup> backups = FileAuditUtil.getBackupsCollection(studyFolder);

        File backupFolder = null;

        for (Backup backup : backups) {

            if (backup.getBackupId().equals(backUpIdentifier)) {
                backupFolder = backup.getFolder();
                break;
            }
        }

        // If backup found
        if (backupFolder != null) {

            FileAuditUtil.moveFolderContentToAuditFolder(backupFolder, studyFolder, false);

        } else {
            throw new DAOException("Backup (" + backUpIdentifier + ") not found for " + studyIdentifier);
        }

    }

    public void updateValidations(String studyIdentifier, Validations validations, String userToken) throws DAOException  {

        // Security: Check if the user can override the study validations
        SecurityService.userOverridingStudyValidations(studyIdentifier, userToken);

        Study study = dbDAO.findByAccession(studyIdentifier);

        // Change the date in the database
//        study.setValidations(validations);
        dbDAO.save(study);

        logger.info("{} curator validations successfully updated", studyIdentifier);
    }
}



