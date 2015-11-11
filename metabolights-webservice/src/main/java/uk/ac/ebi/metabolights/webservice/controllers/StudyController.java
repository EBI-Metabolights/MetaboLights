/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Apr-22
 * Modified by:   kenneth
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.webservice.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import uk.ac.ebi.metabolights.repository.dao.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MzTabDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.*;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.search.service.IndexingFailureException;
import uk.ac.ebi.metabolights.webservice.models.StudyRestResponse;
import uk.ac.ebi.metabolights.webservice.services.EmailService;
import uk.ac.ebi.metabolights.webservice.services.IndexingService;
import uk.ac.ebi.metabolights.webservice.utils.FileUtil;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("study")
public class StudyController extends BasicController{

	@Autowired
	private EmailService emailService;
	@Autowired
	private IndexingService indexingService;

    public static final String METABOLIGHTS_ID_REG_EXP = "(?:MTBLS|mtbls).+";
	private final static Logger logger = LoggerFactory.getLogger(StudyController.class.getName());
	private StudyDAO studyDAO;

    @RequestMapping(value = "{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}", method = RequestMethod.GET)
	@ResponseBody
	public StudyRestResponse getStudyById(@PathVariable("studyIdentifier") String studyIdentifier) throws DAOException {

		logger.info("Requesting " + studyIdentifier + " to the webservice");

		return getStudy(studyIdentifier, false, null);
	}

	@RequestMapping("obfuscationcode/{obfuscationcode}")
	@ResponseBody
	public StudyRestResponse getStudyByObfuscationCode(@PathVariable("obfuscationcode") String obfuscationCode) throws DAOException {

		logger.info("Requesting study by obfuscation code " + obfuscationCode + " to the webservice");


		return getStudy(null, false, obfuscationCode);
	}


	@RequestMapping("{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/full")
	@ResponseBody
	public RestResponse<Study> getFullStudyById(@PathVariable("studyIdentifier") String studyIdentifier) throws DAOException {

		logger.info("Requesting full study " + studyIdentifier + " to the webservice");

		return getStudy(studyIdentifier, true, null);

	}

	@RequestMapping("list")
	@ResponseBody
	public RestResponse<String[]> getAllStudyIdentifiers() throws DAOException {

		logger.info("Requesting a list of all public studies from the webservice");

		RestResponse<String[]> response = new RestResponse<>();

		studyDAO = getStudyDAO();


		try {
			List<String> studyList = studyDAO.getList(getUser().getApiToken());

			String[] strarray = studyList.toArray(new String[0]);
			response.setContent(strarray);
		} catch (DAOException e) {
			logger.error("Can't get the list of studies", e);
			response.setMessage("Can't get the study requested.");
			response.setErr(e);
		}

		return response;

	}

	@RequestMapping("goinglive/{days}")
	@ResponseBody
	public RestResponse<String[]> getAllStudyIdentifiersGoingLive(@PathVariable("days") int numberOfDays) throws DAOException {

		logger.info("Requesting a list of all public studies that should go live to the webservice");

		RestResponse<String[]> response = new RestResponse<>();

		studyDAO = getStudyDAO();


		try {
			List<String> studyList = studyDAO.getStudiesToGoLiveList(getUser().getApiToken(), numberOfDays);

			String[] strarray = studyList.toArray(new String[0]);
			response.setContent(strarray);
		} catch (DAOException e) {
			logger.error("Can't get the list of studies going live", e);
			response.setMessage("Can't get the studies requested.");
			response.setErr(e);
		}

		return response;

	}

	@RequestMapping("makestudiespublic")
	@ResponseBody
	public RestResponse<ArrayList<String>> makeStudiesPublic() throws DAOException {

		logger.info("Making studies public");

		RestResponse<ArrayList<String>> response = new RestResponse<>();
		response.setContent(new ArrayList<String>());

		studyDAO = getStudyDAO();

		List<String> studyList = studyDAO.getStudiesToGoLiveList(getUser().getApiToken());

		String itemLog;
		int errors = 0;

		for (String studyIdentifier : studyList) {

			try {
				updateStatus(studyIdentifier, LiteStudy.StudyStatus.PUBLIC);
				itemLog = "Study " + studyIdentifier + " made PUBLIC.";

			} catch (Exception e) {

				itemLog = "Can't make " + studyIdentifier + " PUBLIC: " + e.getMessage();
				logger.error(itemLog, studyIdentifier,e);
				errors++;
				response.setErr(e);
			}

			response.getContent().add(itemLog);

		}

		if (errors > 0) {
			response.setMessage("There were " + errors + " errors out of " + studyList.size() + ". Check content for details. Last error is in the error object.");
		} else {
			if (studyList.size()== 0){
				response.setMessage("There isn't any study due to be public today.");
			} else {

				response.setMessage("All studies were made public. See content for list.");
			}
		}

		return response;

	}


	/**
	 * To update the public release date of a study.
	 * @param studyIdentifier
	 * @param newPublicReleaseDate
	 * @return
	 */
	@RequestMapping(value = "{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/publicreleasedate", method= RequestMethod.PUT)
	@ResponseBody
	public RestResponse<Boolean> updatePublicReleaseDate(@PathVariable("studyIdentifier") String studyIdentifier, @RequestBody Date newPublicReleaseDate) throws Exception {

		User user = getUser();

		logger.info("User {} requested to update {} public release date to {}", user.getFullName(),studyIdentifier, newPublicReleaseDate);

		studyDAO= getStudyDAO();

		// Update the public release date
		studyDAO.updateReleaseDate(studyIdentifier, newPublicReleaseDate, user.getApiToken());

		// NOTE: Using IndexController as a Service..this could be refactored. We could have a Index service and a StudyService.
		// Like this we might have concurrency issues?
		Study study = studyDAO.getStudy(studyIdentifier,user.getApiToken());

		indexingService.indexStudy(study);

		RestResponse<Boolean> restResponse = new RestResponse<>();
		restResponse.setContent(true);
		restResponse.setMessage("Public release date for " + studyIdentifier + " updated to " + study.getStudyPublicReleaseDate() );

		logger.info("public release date updated.");


		// Email about this
		emailService.sendPublicReleaseDateUpdated(study);

		return restResponse;

	}

	/**
	 * To update the status of a study.
	 * @param studyIdentifier
	 * @param newStatus
	 * @return
	 */
	@RequestMapping(value = "{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/status", method= RequestMethod.PUT)
	@ResponseBody
	public RestResponse<Boolean> updateStatusUrl(@PathVariable("studyIdentifier") String studyIdentifier, @RequestBody LiteStudy.StudyStatus newStatus) {

		RestResponse<Boolean> restResponse = new RestResponse<>();

		try {
			newStatus = updateStatus(studyIdentifier, newStatus);

			restResponse.setContent(true);
			restResponse.setMessage("Status for " + studyIdentifier + " updated to " + newStatus );

		} catch (Exception e) {

			restResponse.setMessage("Couldn't update status for " + studyIdentifier + ": " + e.getMessage());
			restResponse.setErr(e);
		}


		return restResponse;

	}


	private LiteStudy.StudyStatus updateStatus(String studyIdentifier, LiteStudy.StudyStatus newStatus) throws DAOException, IsaTabException, IndexingFailureException {

		User user = getUser();

		logger.info("User {} requested to update {} status to {}", user.getFullName(),studyIdentifier, newStatus.name());

		studyDAO= getStudyDAO();

		// Update the status
		Study study = studyDAO.updateStatus(studyIdentifier, newStatus, user.getApiToken());

		// NOTE: Using IndexController as a Service..this could be refactored. We could have a Index service and a StudyService.
		// Like this we might have concurrency issues?

		indexingService.indexStudy(study);

		logger.info("{} study status updated." , studyIdentifier);

		// Email about this
		emailService.sendStatusChanged(study);

		return study.getStudyStatus();
	}


	private StudyRestResponse getStudy(String studyIdentifier, boolean includeMAFFiles, String obfuscationCode) throws DAOException {

		StudyRestResponse response = new StudyRestResponse();

		studyDAO= getStudyDAO();

		// Get the study
		try {

			Study study = null;

			if (obfuscationCode == null) {
				study = studyDAO.getStudy(studyIdentifier.toUpperCase(), getUser().getApiToken(), includeMAFFiles);
			} else {
				study = studyDAO.getStudyByObfuscationCode(obfuscationCode, includeMAFFiles);
			}

			response.setContent(study);

		} catch (DAOException e) {
			logger.error("Can't get the study requested " + studyIdentifier, e);
			response.setMessage("Can't get the study requested.");
			response.setErr(e);
		}

		return  response;

	}

	private uk.ac.ebi.metabolights.repository.dao.StudyDAO getStudyDAO() throws DAOException {

		if (studyDAO == null){

			studyDAO = DAOFactory.getInstance().getStudyDAO();

		}
		return studyDAO;
	}

	@RequestMapping("{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/assay/{assayIndex}/maf")
	@ResponseBody
	public RestResponse<MetaboliteAssignment> getMetabolites(@PathVariable("studyIdentifier") String studyIdentifier, @PathVariable("assayIndex") String assayIndex) throws DAOException {


		logger.info("Requesting maf file of the assay " + assayIndex + " of the study " + studyIdentifier + " to the webservice");

		// Get the study....
		// TODO: optimize this, since we are loading the whole study to get the MAF file name of one of the assay, and maf file can be loaded having only the maf
		RestResponse<Study> response = getStudy(studyIdentifier, false, null);

		// Get the assay based on the index
		Assay assay = response.getContent().getAssays().get(Integer.parseInt(assayIndex)-1);

		MzTabDAO mzTabDAO = new MzTabDAO();
		MetaboliteAssignment metaboliteAssignment = new MetaboliteAssignment();


		String filePath = assay.getMetaboliteAssignment().getMetaboliteAssignmentFileName();

		if (filePath != null && !filePath.isEmpty()) {
			if (checkFileExists(filePath)) {
				logger.info("MAF file found, starting to read data from " + filePath);
				metaboliteAssignment = mzTabDAO.mapMetaboliteAssignmentFile(filePath);
			} else {
				logger.error("MAF file " + filePath + " does not exist!");
				metaboliteAssignment.setMetaboliteAssignmentFileName("ERROR: " + filePath + " does not exist!");
			}
		}

		return new RestResponse<MetaboliteAssignment>(metaboliteAssignment);
	}

	@RequestMapping("obfuscationcode/{obfuscationcode}/assay/{assayIndex}/maf")
	@ResponseBody
	public RestResponse<MetaboliteAssignment> getMetabolitesByObfuscationCode(@PathVariable("obfuscationcode") String obfuscationCode, @PathVariable("assayIndex") String assayIndex) throws DAOException, IsaTabException {

		logger.info("Requesting maf file of the assay " + assayIndex + " by obfuscationcode " + obfuscationCode + " to the webservice");

		studyDAO = getStudyDAO();

		String studyIdentifier = studyDAO.getStudyIdByObfuscationCode(obfuscationCode);


		return getMetabolites(studyIdentifier, assayIndex);

	}


	private boolean checkFileExists(String filePath){

		if (filePath == null || filePath.isEmpty())
			return false;        // No filename given

		File mafFile = new File(filePath);

		if (mafFile.exists())
			return true;

		return false;

	}

	/**
	 * To update the status of a study.
	 * @param studyIdentifier
	 * @return
	 */
	@RequestMapping(value = "{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}", method= RequestMethod.DELETE)
	@ResponseBody
	public RestResponse<Boolean> deleteStudy(@PathVariable("studyIdentifier") String studyIdentifier) throws DAOException, IsaTabException, IndexingFailureException, IOException {

		User user = getUser();

		logger.info("User {} requested to delete {}", user.getFullName(), studyIdentifier);

		studyDAO= getStudyDAO();

		// Get the study
		Study studyToDelete = studyDAO.getStudy(studyIdentifier,user.getApiToken());

		// Update the status
		studyDAO.delete(studyIdentifier, user.getApiToken());

		indexingService.deleteStudy(studyIdentifier);

		RestResponse<Boolean> restResponse = new RestResponse<>();
		restResponse.setContent(true);
		restResponse.setMessage("Study " + studyIdentifier + " deleted." );

		logger.info("{} study deleted.", studyIdentifier);

		// Email about this
		emailService.sendStudyDeleted(studyToDelete);

		return restResponse;


	}


	/**
	 *
	 * @param studyIdentifier
	 * @param fileNames
	 * @return
	 * @author jrmacias
	 * @date 20151012
	 */
	@PreAuthorize( "hasRole('ROLE_SUPER_USER')")
	@RequestMapping(value = "{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/deleteFiles", method= RequestMethod.POST)
	@ResponseBody
	public RestResponse<Boolean> deleteFiles(@PathVariable("studyIdentifier") String studyIdentifier,
											 @RequestBody List<String> fileNames) {

		User user = getUser();
		logger.info("User {} requested to delete files from study {}", user.getFullName(), studyIdentifier);

		// look for the study path
		String studyLocation = "";
		try{
			 studyLocation = getStudyById(studyIdentifier).getContent().getStudyLocation();
		}  catch (DAOException ex) {
			logger.error("Error looking for study {}: {}", studyIdentifier, ex.getMessage());
		}

		// compose full file pathnames
		List<String> filePaths = new LinkedList<>();
		for (String filename:fileNames){
			filePaths.add(studyLocation + File.separator + filename);
		}

		// delete the files
		String result = FileUtil.deleteFiles(filePaths);

		RestResponse<Boolean> restResponse = new RestResponse<>();
		restResponse.setContent(true);
		restResponse.setMessage(result);
		return restResponse;
	}

	/**
	 * To update the public release date of a study.
	 * @param studyIdentifier
	 * @param backupIdentifier
	 * @return
	 */
	@RequestMapping(value = "{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/restore", method= RequestMethod.PUT)
	@ResponseBody
	public RestResponse<Boolean> restore(@PathVariable("studyIdentifier") String studyIdentifier, @RequestBody String backupIdentifier) throws Exception {

		User user = getUser();

		logger.info("User {} requested to restore {} backup of {}", user.getFullName(),backupIdentifier,studyIdentifier);

		studyDAO= getStudyDAO();

		// Update the public release date
		studyDAO.restoreBackup(studyIdentifier, user.getApiToken(), backupIdentifier);

		// NOTE: Using IndexController as a Service..this could be refactored. We could have a Index service and a StudyService.
		// Like this we might have concurrency issues?
		Study study = studyDAO.getStudy(studyIdentifier,user.getApiToken());

		indexingService.indexStudy (study);

		RestResponse<Boolean> restResponse = new RestResponse<>();
		restResponse.setContent(true);
		restResponse.setMessage("Backup (" + backupIdentifier + ") of " + studyIdentifier + " restored.");

		logger.info("Backup restored.");

		return restResponse;


	}

	/**
	 * Create a private FTP folder for a Study, so the user can upload big files using ftp.
	 *
	 * @param studyIdentifier
	 * @return
	 * @throws DAOException
	 * @author jrmacias
	 * @date 20151102
	 */
	@PreAuthorize("hasRole('ROLE_SUPER_USER') or hasRole('ROLE_SUBMITTER')")
	@RequestMapping("{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/files/requestFtpFolder")
	@ResponseBody
	public RestResponse<String> getStudyPrivateFtpFolder(@PathVariable("studyIdentifier") String studyIdentifier) throws DAOException {
		String privateFTPServer = PropertiesUtil.getProperty("privateFTPServer");	// ftp-private.ebi.ac.uk
		String privateFTPUser = PropertiesUtil.getProperty("privateFTPUser");		// mtblight
		String privateFTPPass = PropertiesUtil.getProperty("privateFTPPass");		// gs4qYabh
		String linkFTPUploadDoc = PropertiesUtil.getProperty("linkFTPUploadDoc");	// ...

		RestResponse<String> restResponse = new RestResponse<>();

		User user = getUser();
		String userToken = user.getApiToken();
		Study study;
		String obfuscationCode = "";
		try {
			study = getStudyDAO().getStudy(studyIdentifier,userToken);
			obfuscationCode = study.getObfuscationCode();
		} catch (IsaTabException e) {
			e.printStackTrace();
			restResponse.setErr(e);
			return restResponse;
		}
		logger.info("[WS] User {0} has requested a private FTP folder for the study {1}", user.getUserName(),studyIdentifier);

		// FTP folder is composed with part of the user identifier + the obfuscation code of the study
		String userPart = user.getApiToken().split("-")[0];
		String ftpFolder = userPart + "_" + obfuscationCode;

		// create the folder
		try {
			FileUtil.createFtpFolder(ftpFolder);
		} catch (IOException e) {
			e.printStackTrace();
			restResponse.setMessage("Error creating FTP folder.");
			restResponse.setErr(e);
			return restResponse;
		}

		restResponse.setContent("Private FTP folder for Study.");
		restResponse.setMessage("Your requested FTP folder is being created. Details for access will be mailed to you shortly.");

		// send FTP folder details by email
		String subject = "Requested Study FTP folder.";
		StringBuilder body = new StringBuilder().append("We are happy to inform you that your FTP folder for study ")
				.append("<b>").append(studyIdentifier).append("</b>")
				.append(" has been successfully created and is now ready for use. To access, please use your favorite FTP client with the following account details:").append('\n').append('\n')
				.append('\t').append("user: ")
				.append("<b>").append(privateFTPUser).append("</b>").append('\n')
				.append('\t').append("password: ")
				.append("<b>").append(privateFTPPass).append("</b>").append('\n')
				.append('\t').append("server: ")
				.append("<b>").append(privateFTPServer).append("</b>").append('\n')
				.append('\t').append("remote folder: ")
				.append("<b>").append("/private/").append(ftpFolder).append("</b>").append('\n')
				.append('\n')
				.append("Please, note that the remote folder needs to be entirely typed, as the folder is not browsable. So use ")
				.append("\"").append("<b>").append("cd ").append("/private/").append(ftpFolder).append("</b>").append("\"").append(" to access your private folder.")
				.append(" More extensive instructions can be found here: ").append(linkFTPUploadDoc)
				.append('\n').append('\n')
				.append("We would be grateful for any feedback on the upload procedure and any issues you may find.")
				.append('\n');
		emailService.sendCreatedFTPFolderEmail(user.getEmail(), subject, body.toString());
		logger.info("FTP folder details sent to user: {0}, by email: {1} .", user.getUserName(), user.getEmail());

		return restResponse;
	}

	/**
	 * Move files from the Study private FTP folder to its MetaboLights folder.
	 *
	 * @param studyIdentifier
	 * @param fileNames
     * @return
	 * @author jrmacias
	 * @date 20151104
     */
	@PreAuthorize("hasRole('ROLE_SUPER_USER') or hasRole('ROLE_SUBMITTER')")
	@RequestMapping(value = "{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/files/moveFilesfromFtpFolder", method= RequestMethod.POST)
	@ResponseBody
	public RestResponse<Boolean> moveFilesFromPrivateFtpFolder(@PathVariable("studyIdentifier") String studyIdentifier,
															   @RequestBody List<String> fileNames) throws DAOException {

		RestResponse<Boolean> restResponse = new RestResponse<>();

		// get the study folder
		Study study;
		String studyFolder = "";
		String obfuscationCode = "";
		User user = getUser();
		String userPart = user.getApiToken().split("-")[0];
		studyDAO = getStudyDAO();
		try {
			study = getStudyDAO().getStudy(studyIdentifier,user.getApiToken());
			obfuscationCode = study.getObfuscationCode();
			studyFolder = study.getStudyLocation();
		} catch (DAOException ex) {
			logger.error("Can't get the study", ex);
			restResponse.setMessage("Can't get the study requested.");
			restResponse.setErr(ex);
			return restResponse;
		} catch (IsaTabException ex) {
			logger.error("Can't get the study", ex);
			restResponse.setMessage("Can't get the study requested.");
			restResponse.setErr(ex);
			return restResponse;
		}
		String ftpFolder = userPart + "_" + obfuscationCode;

		// move the files
		String result = FileUtil.moveFilesFromPrivateFtpFolder(fileNames, ftpFolder, studyFolder);

		restResponse.setContent(true);
		restResponse.setMessage(result);
		return restResponse;
	}


	/**
	 * Create a private FTP folder for a Study, so the user can upload big files using ftp.
	 *
	 * @param studyIdentifier
	 * @return
	 * @throws DAOException
	 * @author jrmacias
	 * @date 20151102
	 */
	@PreAuthorize("hasRole('ROLE_SUPER_USER') or hasRole('ROLE_SUBMITTER')")
	@RequestMapping("{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/files/privateFtpFolder/files")
	@ResponseBody
	public RestResponse<File[]> getPrivateFtpFileList(@PathVariable("studyIdentifier") String studyIdentifier) throws DAOException {
		String privateFTPRoot = PropertiesUtil.getProperty("privateFTPRoot");	// ~/ftp_private/

		RestResponse<File[]> restResponse = new RestResponse<>();

		User user = getUser();
		String userToken = user.getApiToken();
		Study study;
		String obfuscationCode = "";
		try {
			study = getStudyDAO().getStudy(studyIdentifier,userToken);
			obfuscationCode = study.getObfuscationCode();
		} catch (IsaTabException e) {
			e.printStackTrace();
			restResponse.setErr(e);
			return restResponse;
		}
		String userPart = user.getApiToken().split("-")[0];
		String ftpFolder = userPart + "_" + obfuscationCode;

		// read folder content
		File[] files = FileUtil.getFtpFolderList(privateFTPRoot + File.separator + ftpFolder);
		restResponse.setContent(files);

		return restResponse;
	}

}
