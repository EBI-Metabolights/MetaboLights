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
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.*;
import uk.ac.ebi.chebi.webapps.chebiWS.model.Entity;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.*;
import uk.ac.ebi.metabolights.referencelayer.model.CrossReference;
import uk.ac.ebi.metabolights.referencelayer.model.MetaboLightsCompound;
import uk.ac.ebi.metabolights.referencelayer.model.Species;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import uk.ac.ebi.metabolights.repository.dao.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MzTabDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.*;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Status;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validations;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.search.service.IndexingFailureException;
import uk.ac.ebi.metabolights.webservice.models.StudyRestResponse;
import uk.ac.ebi.metabolights.webservice.services.AppContext;
import uk.ac.ebi.metabolights.webservice.services.EmailService;
import uk.ac.ebi.metabolights.webservice.services.IndexingService;
import uk.ac.ebi.metabolights.webservice.utils.FileUtil;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;


import javax.naming.NamingException;
import javax.xml.namespace.QName;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Controller
@RequestMapping("study")
public class StudyController extends BasicController{

	@Autowired
	private EmailService emailService;
	@Autowired
	private IndexingService indexingService;

    private ChebiWebServiceClient chebiWS;
    private MetaboLightsCompoundDAO mcd;
    private CrossReferenceDAO crd;
    private DatabaseDAO dbd;

	private final static Logger logger = LoggerFactory.getLogger(StudyController.class.getName());
    private final String chebiWSUrl = "http://www.ebi.ac.uk/webservices/chebi/2.0/webservice?wsdl";

	private StudyDAO studyDAO;
    private SpeciesDAO speciesDAO;
    private MetSpeciesDAO msDAO;

    public static final String METABOLIGHTS_ID_REG_EXP = "(?:MTBLS|mtbls).+";

    private static final Long MTBLS_DB_ID = new Long(2);

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

    private Entity getChebiEntity(String chebiId) throws ChebiWebServiceFault_Exception {
        return getChebiWS().getCompleteEntity(chebiId);
    }

    public ChebiWebServiceClient getChebiWS() {
        if (chebiWS == null)
            try {
                logger.info("Starting a new instance of the ChEBI ChebiWebServiceClient");
                chebiWS = new ChebiWebServiceClient(new URL(chebiWSUrl),new QName("http://www.ebi.ac.uk/webservices/chebi",	"ChebiWebServiceService"));
            } catch (MalformedURLException e) {
                logger.error("Error instanciating a new ChebiWebServiceClient "+ e.getMessage());
            }
        return chebiWS;
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

	@RequestMapping("listWithDetails")
	@ResponseBody
	public String getAllStudiesDetails() throws DAOException {

		logger.info("Requesting a list of all public studies from the webservice");

		RestResponse<String[]> response = new RestResponse<>();

		studyDAO = getStudyDAO();
		String studyList = "";

		try {
			studyList = studyDAO.getListWithDetails(getUser().getApiToken());
		} catch (DAOException e) {
			logger.error("Can't get the list of studies", e);
			response.setMessage("Can't get the study requested.");
			response.setErr(e);
		}

		return studyDAO.getListWithDetails(getUser().getApiToken());
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

	/**
	 * To update the status of a study.
	 * @param studyIdentifier
	 * @param validations
	 * @return
	 */
	@RequestMapping(value = "{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/overridevalidations", method= RequestMethod.POST)
	@ResponseBody
	public RestResponse<Boolean> updateCuratorsValidationOverride(@PathVariable("studyIdentifier") String studyIdentifier, @RequestBody Validations validations) {

		RestResponse<Boolean> restResponse = new RestResponse<>();

		try {
			User user = getUser();

			logger.info("User {} requested to override validations of {}", user.getFullName(), studyIdentifier);

			studyDAO= getStudyDAO();
			// Update the overriden validations
			studyDAO.updateValidations(studyIdentifier, validations, user.getApiToken());


			restResponse.setContent(true);
			restResponse.setMessage("Validations for " + studyIdentifier + " successfully updated");

		} catch (Exception e) {

			restResponse.setMessage("Error applying curator's validations override for " + studyIdentifier + ": " + e.getMessage());
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
			if(study==null){
				response.setMessage("Study not found");
				logger.error("Can't get the study requested " + studyIdentifier);
			}

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
	 * Delete a file from the Study folder
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
	 * Get the obfuscation code of the study
	 *
	 * @param studyId
	 * @param user
	 * @return
	 * @throws DAOException
	 * @author jrmacias
	 * @date 20151112
	 */
	private String getObfuscationCode(String studyId, User user)
			throws DAOException, IsaTabException {

		Study study = getStudyDAO().getStudy(studyId,user.getApiToken());
		String obfuscationCode = study.getObfuscationCode();

		return obfuscationCode;
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
	public RestResponse<String> createPrivateFtpFolder(@PathVariable("studyIdentifier") String studyIdentifier)
			throws DAOException, IOException, IsaTabException {

		String privateFTPServer = PropertiesUtil.getProperty("privateFTPServer");	// ftp-private.ebi.ac.uk
		String privateFTPRoot = PropertiesUtil.getProperty("privateFTPRoot");
		String privateFTPUser = PropertiesUtil.getProperty("privateFTPUser");		// mtblight
		String privateFTPPass = PropertiesUtil.getProperty("privateFTPPass");		// gs4qYabh
		String linkFTPUploadDoc = PropertiesUtil.getProperty("linkFTPUploadDoc");	// ...
		// get the version of the app currently in use from the last part of the path
		String[] ftp_paths = privateFTPRoot.split("/");
		String ftp_path = "/"+ftp_paths[ftp_paths.length - 1]+"/";

		User user = getUser();
		logger.info("User {} has requested a private FTP folder for the study {}", user.getUserName(),studyIdentifier);

		// FTP folder is composed with the study identifier + the obfuscation code
		String ftpFolder = studyIdentifier.toLowerCase() + "-" + getObfuscationCode(studyIdentifier, user);

		// create the folder
		FileUtil.createFtpFolder(ftpFolder);
		RestResponse<String> restResponse = new RestResponse<>();
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
				.append("<b>").append(ftp_path).append(ftpFolder).append("</b>").append('\n')
				.append('\n')
				.append("Please, note that the remote folder needs to be entirely typed, as the folder is not browsable. So use ")
				.append("\"").append("<b>").append("cd ").append(ftp_path).append(ftpFolder).append("</b>").append("\"").append(" to access your private folder.")
				.append(" More extensive instructions can be found here: ").append(linkFTPUploadDoc)
				.append('\n').append('\n')
				.append("We would be grateful for any feedback on the upload procedure and any issues you may find.")
				.append('\n');
		emailService.sendCreatedFTPFolderEmail(user.getEmail(), subject, body.toString());
		logger.info("Private FTP folder details sent to user: {}, by email: {} .", user.getUserName(), user.getEmail());

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
															   @RequestBody List<String> fileNames)
			throws DAOException, IsaTabException {

		// get the study folder
		User user = getUser();
		Study study = getStudyDAO().getStudy(studyIdentifier,user.getApiToken());
		String studyFolder = study.getStudyLocation();
		String ftpFolder = studyIdentifier.toLowerCase() + "-" + getObfuscationCode(studyIdentifier, user);


		// move the files
		String result = FileUtil.moveFilesFromPrivateFtpFolder(fileNames, ftpFolder, studyFolder);
		RestResponse<Boolean> restResponse = new RestResponse<>();
		restResponse.setContent(true);
		restResponse.setMessage(result);
		return restResponse;
	}


	/**
	 * Check if a Study has a private FTP folder
	 *
	 * @param studyIdentifier
	 * @return
	 * @throws DAOException
	 * @author jrmacias
	 * @date 20151112
     */
	@PreAuthorize("hasRole('ROLE_SUPER_USER') or hasRole('ROLE_SUBMITTER')")
	@RequestMapping("{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/files/privateFtpFolder")
	@ResponseBody
	public RestResponse<Boolean> hasPrivateFtpFolder(@PathVariable("studyIdentifier") String studyIdentifier)
			throws DAOException, IsaTabException {

		String privateFTPRoot = PropertiesUtil.getProperty("privateFTPRoot");	// ~/ftp_private/

		User user = getUser();
		String ftpFolder = studyIdentifier.toLowerCase() + "-" + getObfuscationCode(studyIdentifier, user);

		boolean result = FileUtil.getFtpFolder(privateFTPRoot + File.separator + ftpFolder);
		RestResponse<Boolean> restResponse = new RestResponse<>();
		restResponse.setContent(result);
		restResponse.setMessage(result?ftpFolder:"");
		return restResponse;
	}

	/**
	 * Get a list of files in the private FTP folder of a Study
	 *
	 * @param studyIdentifier
	 * @return
	 * @throws DAOException
	 * @author jrmacias
	 * @date 20151102
	 */
	@PreAuthorize("hasRole('ROLE_SUPER_USER') or hasRole('ROLE_SUBMITTER')")
	@RequestMapping("{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/files/privateFtpFolder/list")
	@ResponseBody
	public RestResponse<String[]> getPrivateFtpFileList(@PathVariable("studyIdentifier") String studyIdentifier)
			throws DAOException, IsaTabException {

		String privateFTPRoot = PropertiesUtil.getProperty("privateFTPRoot");	// ~/ftp_private/

		User user = getUser();
		String ftpFolder = studyIdentifier.toLowerCase() + "-" + getObfuscationCode(studyIdentifier, user);

		// read folder content
		String[] fileNames = FileUtil.getFtpFolderList(privateFTPRoot + File.separator + ftpFolder);
		RestResponse<String[]> restResponse = new RestResponse<>();
		restResponse.setContent(fileNames);
		restResponse.setMessage("List of files in your private FTP folder");

		return restResponse;
	}

	/**
	 * Delete a list of files from the private FTP folder of the study
	 *
	 * @param studyIdentifier
	 * @return
	 * @author jrmacias
	 * @date 20151112
	 */
	@PreAuthorize("hasRole('ROLE_SUPER_USER') or hasRole('ROLE_SUBMITTER')")
	@RequestMapping(value = "{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/files/deleteFilesfromFtpFolder", method= RequestMethod.POST)
	@ResponseBody
	public RestResponse<Boolean> deleteFilesFromPrivateFtpFolder(@PathVariable("studyIdentifier") String studyIdentifier,
																 @RequestBody List<String> fileNames)
			throws DAOException, IsaTabException, IndexingFailureException, IOException {

		User user = getUser();
		logger.info("User {} requested to delete files from study {} private FTP folder.", user.getFullName(), studyIdentifier);

		// look for the private FTP folder
		String privateFTPRoot = PropertiesUtil.getProperty("privateFTPRoot");
		String ftpFolder = studyIdentifier.toLowerCase() + "-" + getObfuscationCode(studyIdentifier, user);

		// delete the files
		String result = FileUtil.deleteFilesFromPrivateFtpFolder(fileNames, ftpFolder);

		// compose a response
		RestResponse<Boolean> restResponse = new RestResponse<>();
		restResponse.setContent(true);
		restResponse.setMessage(result);
		return restResponse;

	}


	/**
	 * Returns list of  Metabolites identified in the given Metabolights Study - MTBLSX
	 *
	 * @param   studyIdentifier
	 * @return  ChebiIds Array
	 * @author  CS76
	 * @date    20160108
	 */

	@RequestMapping("{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/getMetabolites")
	@ResponseBody
	public RestResponse<Study> getIdentifiedMetabolites(@PathVariable("studyIdentifier") String studyIdentifier) throws DAOException {

		logger.info("Requesting " + studyIdentifier + "metabolite mapping update");

		RestResponse response = new RestResponse();

		Map<String, String> metabolites = getMetabolitesFromMAF(studyIdentifier);

		response.setContent(metabolites);

		return response;
	}

    /**
     * Update Metabolites and Metabolights study mappings
     *
     * @param   studyIdentifier
     * @author  CS76
     * @date    20160108
     */

    @RequestMapping("{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/updateMetabolitesMapping")
    @ResponseBody
    public RestResponse<Study> updateStudyMetaboliteMapping(@PathVariable("studyIdentifier") String studyIdentifier) throws DAOException {

        logger.info("Requesting " + studyIdentifier + "metabolite mapping update");

        RestResponse response = new RestResponse();

        Map<String, String> metabolites = getMetabolitesFromMAF(studyIdentifier);


        for (Map.Entry<String, String> entry : metabolites.entrySet()) {
			if (entry.getValue() != null && !entry.getValue().isEmpty())
            	mapCompound(entry.getKey(), studyIdentifier, entry.getValue());
        }

        return response;

    }


    private boolean mapCompound(String chebiId, String studyIdentifier, String species){

        try {

            Entity entity = getChebiEntity(chebiId);

            // Check if the metabolite entry exits

            if (entity.getSmiles()== null){
                return false;
            }

            initializeDAOs();

            String accession = "";

            try {
               accession  = MetaboLightsCompoundDAO.chebiID2MetaboLightsID(entity.getChebiId());
            }catch (Exception e){
                System.out.println(e.getMessage());
                return false;
            }

            // Check if we have already the Metabolite (since querying the WS is what takes more...)
            MetaboLightsCompound mc = mcd.findByCompoundAccession(accession);

            if(mc != null){

                    CrossReference cr = crd.findByCrossReferenceAccession(studyIdentifier);

                    // If not CrossReference was found
                    if (cr == null){

                        cr = new CrossReference();
                        cr.setAccession(studyIdentifier);
                        cr.setDb(dbd.findByDatabaseName("MTBLS"));
                        crd.save(cr);

                    }else{

                        cr.setDb(dbd.findByDatabaseName("MTBLS"));
						crd.save(cr);

					}

                    Species spe = speciesDAO.findBySpeciesName(species);

					if(spe != null){
						mc.setHasSpecies(true);
						mcd.save(mc);
					}

                    msDAO.save(spe,mc,cr);

            }

        } catch (ChebiWebServiceFault_Exception e) {

            e.printStackTrace();

        } catch (uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException e) {

            e.printStackTrace();
        }

        return false;
    }

    private void initializeDAOs(){

        Connection connection = null;

        try {

            connection = AppContext.getConnection();

            mcd = new MetaboLightsCompoundDAO(connection);
            crd = new CrossReferenceDAO(connection);
            dbd = new DatabaseDAO(connection);
            speciesDAO = new SpeciesDAO(connection);
            msDAO = new MetSpeciesDAO(connection);

        }  catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }



    private Map<String, String> getMetabolitesFromMAF(String studyIdentifier){

        Map<String, String> metabolites = new HashMap<>();

        try {
            studyDAO= getStudyDAO();

            Study study = studyDAO.getStudy(studyIdentifier.toUpperCase(), getUser().getApiToken(), true);

            if(study==null) {

                logger.error("Can't get the study requested " + studyIdentifier);

            } else {

                for (Assay assay: study.getAssays()){

                    for (MetaboliteAssignmentLine mal : assay.getMetaboliteAssignment().getMetaboliteAssignmentLines()){

                        String databaseIdentifier = mal.getDatabaseIdentifier();

                        if (databaseIdentifier != null && !databaseIdentifier.isEmpty() && databaseIdentifier != "unknown"){

                            if (databaseIdentifier.startsWith("CHEBI:"))

                                metabolites.put(databaseIdentifier, mal.getSpecies());

                        }
                    }
                }
            }

        } catch (DAOException e) {

            e.printStackTrace();

        }

        return metabolites;

    }

	/**
	 * Send a mail to the submitter with a Validations Status Report
	 * with all the current validations results,
	 * ordered by status, so the submitter can find what is failing to pass
	 * and accordingly update the study.
	 *
	 * @param studyIdentifier
	 * @return
	 * @throws DAOException
	 * @throws IsaTabException
	 * @author jrmacias
	 * @date 20160125
     */
	@PreAuthorize("hasRole('ROLE_SUPER_USER') or hasRole('ROLE_SUBMITTER')")
	@RequestMapping("{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/validations/statusReportByMail")
	@ResponseBody
	public RestResponse<String> sendValidationsStatusMail(@PathVariable("studyIdentifier") String studyIdentifier)
			throws DAOException, IsaTabException{

		User user = getUser();
		logger.info("User {} has requested a validations status report to be sent by mail for the study {}", user.getUserName(),studyIdentifier);

		// get the validations status report
		Study study = getStudyDAO().getStudy(studyIdentifier, user.getApiToken());

		// compose the response
		RestResponse<String> restResponse = new RestResponse<>();
		restResponse.setContent("Validations Status Report for Study.");
		restResponse.setMessage("Your requested Validations Status Report is being created. It will be emailed to the study's submitter shortly.");

		logger.info("Sending Validations status report to user {} by email {} .", user.getUserName(), user.getEmail());
		emailService.sendValidationStatus(study);


		return restResponse;

	}

}
