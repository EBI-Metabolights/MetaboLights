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


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.ChebiWebServiceFault_Exception;
import uk.ac.ebi.chebi.webapps.chebiWS.model.Entity;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.*;
import uk.ac.ebi.metabolights.referencelayer.model.CrossReference;
import uk.ac.ebi.metabolights.referencelayer.model.MetaboLightsCompound;
import uk.ac.ebi.metabolights.referencelayer.model.Species;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import uk.ac.ebi.metabolights.repository.dao.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MetExploreDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MzTabDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.*;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validations;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.search.service.IndexingFailureException;
import uk.ac.ebi.metabolights.webservice.models.StudyRestResponse;
import uk.ac.ebi.metabolights.webservice.services.AppContext;
import uk.ac.ebi.metabolights.webservice.services.EmailService;
import uk.ac.ebi.metabolights.webservice.services.IndexingService;
import uk.ac.ebi.metabolights.webservice.services.UserServiceImpl;
import uk.ac.ebi.metabolights.webservice.utils.FileUtil;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private final String chebiWSUrl = "https://www.ebi.ac.uk/webservices/chebi/2.0/webservice?wsdl";

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

	@RequestMapping("{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/lite")
	@ResponseBody
	public RestResponse<Study> getLiteStudyById(@PathVariable("studyIdentifier") String studyIdentifier) throws DAOException {

		logger.info("Requesting full study " + studyIdentifier + " to the webservice");

		return getLiteStudy(studyIdentifier);
	}

    private Entity getChebiEntity(String chebiId) throws ChebiWebServiceFault_Exception {
        return getChebiWS().getCompleteEntity(chebiId);
    }

    public ChebiWebServiceClient getChebiWS() {
        if (chebiWS == null)
            try {
                logger.info("Starting a new instance of the ChEBI ChebiWebServiceClient");
                chebiWS = new ChebiWebServiceClient(new URL(chebiWSUrl),new QName("https://www.ebi.ac.uk/webservices/chebi",	"ChebiWebServiceService"));
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

    @RequestMapping({"/parallelCoordinatesData"})
    @ResponseBody
    public RestResponse getFactorsDistribution(@RequestParam("study") String study){

        String ftpUrl = "ftp://ftp.ebi.ac.uk/pub/databases/metabolights/derived/parallel_coordinates/factorsDistribution.json";

        study = study.replace("\"","");

        if(study != null && !study.isEmpty()){
            ftpUrl = "ftp://ftp.ebi.ac.uk/pub/databases/metabolights/derived/parallel_coordinates/" + study + ".json";
        }

        StringBuffer sbf = new StringBuffer();

        try {
            URL url = new URL(ftpUrl);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ( (inputLine = in.readLine()) != null) sbf.append(inputLine);
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }

        RestResponse restResponse = new RestResponse();

        restResponse.setContent(sbf.toString());

        return restResponse;
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

	private RestResponse getLiteStudy(String studyIdentifier) throws DAOException {

		RestResponse response = new StudyRestResponse();

		studyDAO= getStudyDAO();

		try {

			Study study = null;
			study = studyDAO.getStudy(studyIdentifier);

			if (study.isPublicStudy()){
				Map<String, String> liteStudyMap = new HashMap<String, String>();
				liteStudyMap.put("title", study.getTitle());
				liteStudyMap.put("description", study.getDescription());
				response.setContent(liteStudyMap);
			} else {
				response.setMessage("Study is not public");
				logger.error("Can't get the study requested " + studyIdentifier + ". Study is not public");
			}

			if (study==null){
				response.setMessage("Study not found");
				logger.error("Can't get the study requested " + studyIdentifier);
			}

		} catch (Exception e) {
			logger.error("Can't get the study requested " + studyIdentifier, e);
			response.setMessage("Can't get the study requested.");
			response.setErr(e);
		}

		return  response;

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
			if(study == null){
				response.setMessage("Study not found");
				logger.error("Can't get the study requested " + studyIdentifier);
			}

		} catch (DAOException e) {
			logger.error("Can't get the study requested " + studyIdentifier, e);
			response.setMessage("Can't get the study requested.");
			response.setErr(e);
		}

		return response;

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
		RestResponse<Study> response = getStudy(studyIdentifier, true, null);
        return new RestResponse<MetaboliteAssignment>(getMAFContentFromAssay(response,assayIndex));
	}


    private String serializeObject(Object objectToSerialize) {
        logger.debug("Serializing object to a Json string:" + objectToSerialize.getClass());

        // Get the mapper
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(objectToSerialize);
        } catch (IOException e) {
            logger.error("Can't serialize " + objectToSerialize.getClass());
        }

        return null;
    }

    @RequestMapping("{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/assay/{assayIndex}/jsonmaf")
    @ResponseBody
    public String getMetabolitesJSON(@PathVariable("studyIdentifier") String studyIdentifier, @PathVariable("assayIndex") String assayIndex) throws DAOException {

        logger.info("Requesting maf file of the assay " + assayIndex + " of the study " + studyIdentifier + " to the webservice");
        RestResponse<Study> response = getStudy(studyIdentifier, true, null);
        return serializeObject(getMAFContentFromAssay(response,assayIndex));
    }

    @RequestMapping("{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/assay/{assayIndex}/jsonmaf/{fileName}")
    @ResponseBody
    public String getMetabolitesJSON(@PathVariable("studyIdentifier") String studyIdentifier, @PathVariable("assayIndex") String assayIndex, @PathVariable("fileName") String fileName) {

        logger.info("Requesting maf file " + fileName + " as part of the assay " + assayIndex + " of study " + studyIdentifier + " from the webservice");
        return serializeObject(getMAFContentOnFilename(fileName));
    }

	private MetaboliteAssignment getMAFContentFromAssay(RestResponse<Study> response, String assayIndex){
		// Get the assay based on the index
		Assay assay = response.getContent().getAssays().get(Integer.parseInt(assayIndex)-1);
		return getMAFContentOnFilename(assay.getMetaboliteAssignment().getMetaboliteAssignmentFileName());
	}

    private MetaboliteAssignment getMAFContentOnFilename(String filePath){

        MzTabDAO mzTabDAO = new MzTabDAO();
        MetaboliteAssignment metaboliteAssignment = new MetaboliteAssignment();

        if (filePath != null && !filePath.isEmpty()) {
            if (checkFileExists(filePath)) {
                logger.info("MAF file found, starting to read data from " + filePath);
                metaboliteAssignment = mzTabDAO.mapMetaboliteAssignmentFile(filePath);
            } else {
                logger.error("MAF file " + filePath + " does not exist!");
                metaboliteAssignment.setMetaboliteAssignmentFileName("ERROR: " + filePath + " does not exist!");
            }
        }
        return metaboliteAssignment;
    }

	@RequestMapping("obfuscationcode/{obfuscationcode}/assay/{assayIndex}/maf")
	@ResponseBody
	public RestResponse<MetaboliteAssignment> getMetabolitesByObfuscationCode(@PathVariable("obfuscationcode") String obfuscationCode, @PathVariable("assayIndex") String assayIndex) throws DAOException, IsaTabException {

		logger.info("Requesting maf file of the assay " + assayIndex + " by obfuscationcode " + obfuscationCode + " to the webservice");
        // Get the study....
		// TODO: optimize this, since we are loading the whole study to get the MAF file name of one of the assay, and maf file can be loaded having only the maf
		RestResponse<Study> response = getStudy(null, true, obfuscationCode);
		return new RestResponse<MetaboliteAssignment>(getMAFContentFromAssay(response,assayIndex));
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
	public RestResponse<Boolean> deleteStudy(@PathVariable("studyIdentifier") String studyIdentifier) throws DAOException, IsaTabException, IndexingFailureException, IOException, InterruptedException {

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
	private String getSubmitterEmail(String studyId, User user)
			throws DAOException, IsaTabException {

        Study study = getStudyDAO().getStudy(studyId, user.getApiToken());
        if (study.getUsers().size() == 0) {
            return "";
        }

        return study.getUsers().get(0).getEmail();
    }

	/**
	 * Get the obfuscation code of the study
	 *
	 * @param studyId
	 * @param user
	 * @return obfuscationCode from the study
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
	 * Create a private upload folder for a Study, so the user can upload big files using ftp.
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
		logger.info("User {} has requested a private upload folder for the study {}", user.getUserName(),studyIdentifier);

		// FTP folder is composed with the study identifier + the obfuscation code
		String ftpFolder = studyIdentifier.toLowerCase() + "-" + getObfuscationCode(studyIdentifier, user);

		// create the folder
		FileUtil.createFtpFolder(ftpFolder);
		RestResponse<String> restResponse = new RestResponse<>();
		restResponse.setContent("Private upload folder for Study.");
		restResponse.setMessage("Your requested upload folder is being created. Details for access will be mailed to you shortly.");

		// send FTP folder details by email
		String subject = "Requested Study upload folder.";
		StringBuilder body = new StringBuilder().append("We are happy to inform you that your upload folder for study ")
				.append("<b>").append(studyIdentifier).append("</b>")
				.append(" has been successfully created and is now ready for use. You can use either FTP or Aspera Client to upload your study files.").append('\n').append('\n')
				.append("<b>").append("Using FTP Client:").append("</b>").append('\n')
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
				.append('\n')
				.append('\n')
				.append("<b>").append("Using Aspera Client:").append("</b>").append('\n')
				.append("You can also use the high-speed Aspera client to upload with the same username and password listed above using the command below.\n" +
						"ascp -QT -P 33001 -L-  -l 300M your_local_data_folder mtblight@hx-fasp-1.ebi.ac.uk:").append(ftp_path).append(ftpFolder)
				.append('\n')
				.append('\n')
				.append(" Detailed Instructions on data upload through FTP/Aspera is available here: ").append(linkFTPUploadDoc)
				.append('\n').append('\n')
				.append("We would be grateful for any feedback on the upload procedure and any issues you may find.")
				.append('\n');
		emailService.sendCreatedFTPFolderEmail(user.getEmail(),getSubmitterEmail(studyIdentifier,user), subject, body.toString());
		logger.info("Private upload folder details sent to user: {}, by email: {} .", user.getUserName(), user.getEmail());
		logger.info(subject);

		return restResponse;
	}

	/**
	 * Move files from the Study private upload folder to its MetaboLights folder.
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
	 * Check if a Study has a private upload folder
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
	 * Get a list of files in the private upload folder of a Study
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
		restResponse.setMessage("List of files in your private upload folder");

		return restResponse;
	}

	/**
	 * Delete a list of files from the private upload folder of the study
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
		logger.info("User {} requested to delete files from study {} private upload folder.", user.getFullName(), studyIdentifier);

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
	 * Returns list of  Metabolites identified in the given Metabolights Study - MTBLSX
	 *
	 * @param   studyIdentifier
	 * @return  ChebiIds Array
	 * @author  CS76
	 * @date    20160108
	 */

	@RequestMapping("{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/getMetabolitesInchi")
	@ResponseBody
	public RestResponse<Study> getMetabolitesInchi(@PathVariable("studyIdentifier") String studyIdentifier) throws DAOException {

		logger.info("Requesting " + studyIdentifier + "metabolite mapping update");

		RestResponse response = new RestResponse();

		List<String> metabolites = getMetabolitesINCHIFromMAF(studyIdentifier);

		response.setContent(metabolites);

		return response;
	}

	/**
	 * Returns list of  Metabolites identified in the given Metabolights Study - MTBLSX
	 *
	 * @param   studyIdentifier
	 * @return  ChebiIds Array
	 * @author  CS76
	 * @date    20160108
	 */

	@RequestMapping(value = "{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/getPermissions", method= RequestMethod.POST)
	@ResponseBody
	public RestResponse<String> getPermissions(@PathVariable("studyIdentifier") String studyIdentifier, HttpServletRequest request, HttpServletResponse response) throws DAOException {

		logger.info("Requesting " + studyIdentifier + " permission rights");

        RestResponse<String> restResponse = new RestResponse<String>();

        // check if the user exists and valid
        UserServiceImpl usi = null;

        try {

            usi = new UserServiceImpl();

            String token = request.getParameter("token");

            User user = usi.lookupByToken(token);

            if (user.getEmail() == null || token == null){

                Study study = getStudyDAO().getStudy(studyIdentifier);

                if(study.getStudyStatus() == Study.StudyStatus.PUBLIC){

                    restResponse.setContent( "{ \"read\" : true, \"write\": false  }" );

                    return restResponse;

                }else {

                    restResponse.setContent( "{ \"read\" : false, \"write\": false  }"  );

                    response.setStatus(Response.Status.FORBIDDEN.getStatusCode());

                    return restResponse;
                }

            }else{

                if (user.isCurator()){

                    restResponse.setContent( "{ \"read\" : true, \"write\": true  }" );

                    return restResponse;

                }else{

                    Study study = getStudyDAO().getStudy(studyIdentifier);

                    boolean userOwnstudy = doesUserOwnsTheStudy(user.getUserName(), study);

                    if (userOwnstudy && study.getStudyStatus() == Study.StudyStatus.SUBMITTED){

                        restResponse.setContent( "{ \"read\" : true, \"write\": true  }"  );

                        return restResponse;

                    }else if(userOwnstudy && study.getStudyStatus() != Study.StudyStatus.SUBMITTED){

                        restResponse.setContent( "{ \"read\" : true, \"write\": false  }" );

                        return restResponse;

                    }else if(!userOwnstudy && study.getStudyStatus() == Study.StudyStatus.PUBLIC){

                        restResponse.setContent( "{ \"read\" : true, \"write\": false  }"  );

                        return restResponse;

                    }

                }

            }

        } catch (DAOException e) {

            response.setStatus(Response.Status.FORBIDDEN.getStatusCode());

            return restResponse;

        } catch (IsaTabException e) {

            e.printStackTrace();

        }

        restResponse.setContent( "{ 'read' : false, 'write': false  }"  );

        return restResponse;
	}

    private static boolean doesUserOwnsTheStudy(String userName, LiteStudy study) {

        for (User user : study.getUsers()) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }

        return false;
    }




	/**
	 * Returns list of  Metabolites identified in the given Metabolights Study - MTBLSX
	 *
	 * @param   studyIdentifier
	 * @return  ChebiIds Array
	 * @author  CS76
	 * @date    20160108
	 */

	@RequestMapping("{studyIdentifier:" + METABOLIGHTS_ID_REG_EXP +"}/getMetExploreMappingData")
	@ResponseBody
	public RestResponse getMetExploreMappingData(@PathVariable("studyIdentifier") String studyIdentifier) throws DAOException {

		logger.info("Requesting " + studyIdentifier + "MetExplore Mapping Data");

		RestResponse response = new RestResponse();

        String MetExploreJSONFileName = PropertiesUtil.getProperty("studiesLocation") + studyIdentifier + File.separator + "metexplore_mapping.json";

        MetExploreDAO metexploredao = new MetExploreDAO();

        String MetExplorePathwaysMAppingData = metexploredao.getMetExploreJSONData(MetExploreJSONFileName, studyIdentifier);

        response.setContent(MetExplorePathwaysMAppingData);

		// response.setContent(studyIdentifier);

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

	private List<String> getMetabolitesINCHIFromMAF(String studyIdentifier){

		List<String> metabolites = new ArrayList<String>();

		try {

			studyDAO= getStudyDAO();

			Study study = studyDAO.getStudy(studyIdentifier.toUpperCase(), getUser().getApiToken(), true);

			if(study==null) {

				logger.error("Can't get the study requested " + studyIdentifier);

			} else {

				for (Assay assay: study.getAssays()){

					for (MetaboliteAssignmentLine mal : assay.getMetaboliteAssignment().getMetaboliteAssignmentLines()){

						String databaseIdentifier = mal.getDatabaseIdentifier();

						String inchi = mal.getInchi();

						if (databaseIdentifier != null && !databaseIdentifier.isEmpty() && databaseIdentifier != "unknown"){

							if (inchi != null && !inchi.isEmpty())

								metabolites.add(inchi);

						}
					}
				}
			}

		} catch (DAOException e) {

			e.printStackTrace();

		}

		return metabolites;

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
		Exception ex = emailService.sendValidationStatus(study);
		restResponse.setErr(ex);
		if (null == ex){
			restResponse.setMessage("Your requested Validations Status Report is being created.<br/>" +
					"It will be emailed to the study's submitter shortly.");
		} else {
			restResponse.setMessage("");
		}

		return restResponse;

	}

}
