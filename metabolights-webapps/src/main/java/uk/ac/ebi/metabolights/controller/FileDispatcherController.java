/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 8/14/14 10:32 AM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.controller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.utils.FileUtil;
import uk.ac.ebi.metabolights.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.utils.Zipper;
import uk.ac.ebi.metabolights.webservice.client.MetabolightsWsClient;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller for dispatching study files .
 * 
 */
@Controller
public class FileDispatcherController extends AbstractController {

    private static Logger logger = LoggerFactory.getLogger(FileDispatcherController.class);

    private static final String URL_4_FILES = "files";

    private static final String URL_4_ERROR_FILES = "errorFile";

	private  @Value("#{ondemand}") String zipOnDemandLocation;     // To store the zip files requested from the Entry page, both public and private files goes here


    // Get a single file from a study
    @RequestMapping(value = "/{studyId:" + EntryController.METABOLIGHTS_ID_REG_EXP + "}/" + URL_4_FILES + "/{fileNamePattern:.+}")
    public ModelAndView getSingleFile(@PathVariable("studyId") String studyId,
									  @RequestParam(value="token", defaultValue = "0") String obfuscationCode, @PathVariable("fileNamePattern") String fileNamePattern,
									  HttpServletResponse response) {


		// We can receive any regular expression pattern...but some of them may not be allowed in the URL:
		//Replace them here
		//Example (asterisks works): fileNamePattern = fileNamePattern.replace("~", "*");

        // For the whole zip file fileNamePattern must match the studyId
        if (studyId.equals(fileNamePattern))
		{
			fileNamePattern = "";
		} else if ("metadata".equals(fileNamePattern)){
			fileNamePattern = ".*\\.txt|.*.\\.tsv|.*\\.maf";
		}

        // Stream the file
        return streamFile(studyId, obfuscationCode,fileNamePattern,response);

    }

    /**
     * Delete a series of files selected from the 'Study Files' tab
     * Requires confirmation.
     * Only Curators (ROLE_SUPER_USER) should be accessing this
     *
     * @param studyId the ID of the study
     * @param obfuscationCode, the user credentials
     * @param selectedFiles, the list of files to be deleted
     * @param response
     * @author: jrmacias
     * @date: 20151012
     */
    @RequestMapping(value = "/{studyId:" + EntryController.METABOLIGHTS_ID_REG_EXP + "}/" + URL_4_FILES + "/deleteSelFiles")
    public ModelAndView deleteSelectedFiles(@PathVariable("studyId") String studyId,
                                    @RequestParam(value="token", defaultValue = "0") String obfuscationCode,
                                    @RequestParam("file") List<String> selectedFiles,
                                    HttpServletResponse response){

        // Using the WebService-client to actually delete the files
        MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();
        String rslt = wsClient.deleteFilesFromStudy(studyId, obfuscationCode, selectedFiles).getMessage();

        // parse WS response for user feedback
        List<String> msg = new LinkedList<>();
        String[] str = rslt.split("\\|");
        for (String line:str){
            msg.add(line);
        }

        return printMessage("Deleting files from study...", msg, studyId);
    }

	@RequestMapping(value = "/{studyId:" + EntryController.METABOLIGHTS_ID_REG_EXP + "}/" + URL_4_FILES + "/downloadSelFiles",
            method = RequestMethod.POST)
	public ModelAndView getSomeFiles(@PathVariable("studyId") String studyId,
									 @RequestParam(value="token", defaultValue = "0") String obfuscationCode,
                                     @RequestParam("file") List<String> selectedFiles,
									  HttpServletResponse response) {

		// Join the files to make a regular expression
		String fileNamePattern = org.apache.commons.lang.StringUtils.join(selectedFiles, "|");

		return getSingleFile(studyId, obfuscationCode, fileNamePattern, response);
	}

    // Get the metabolite identification file of an assay
    /*

    Having an assay file find and return the correspondent metabolite identification file

    e.g.: for an assay file "a_myassay.txt"

    So far we ave 2 different versions:

    1st: a_myassay_maf.csv
    2nd: m_myassay_V2_maf.tsv


     */
    @RequestMapping(value = "/{studyId:" + EntryController.METABOLIGHTS_ID_REG_EXP + "}/" + URL_4_FILES + "/{assayFileName:.+}/maf")
    public void getMafFile(@PathVariable("studyId") String studyId,
                              @PathVariable("assayFileName") String assayFileName,
							  @RequestParam(value="token", defaultValue = "0") String obfuscationCode,
                              HttpServletResponse response) {

        String mafFileName =  assayFileName.replaceFirst("a_","m_");
        mafFileName = mafFileName.replace(".txt","_v2_maf.tsv");

        String fullPath = getPathForFile(studyId, mafFileName);
        File mafFile = new File(fullPath);

        // If it doesn't exists, try the old version
        if (!mafFile.exists()){
            mafFileName = assayFileName.replace(".txt","_maf.csv");
        }

        // Stream the file
        streamFile(studyId, obfuscationCode, mafFileName,response);

    }


    //Create the requested zip file based on the study folder
    private File createZipFile( File[] files, String studyId) throws IOException {

		String zipFile="";

		// If there is only one file, and it matches the studyId...
		if (files.length == 1 && files[0].getName().equals(studyId) )
		{
			// User wanrts all the data...keep it in the zipondemand location for future requests
			zipFile = zipOnDemandLocation + files[0].getName() + ".zip";

			// Change File[] with the list of files.
			files = files[0].listFiles();

		} else {

			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

			zipFile = studyId + "_" +  timeStamp;

			zipFile = zipOnDemandLocation + zipFile + ".zip";

		}



        if (!FileUtil.fileExists(zipFile))  // Just to be sure that the file *doesn't already* exist
            Zipper.zip(files, zipFile);


        File file = new File(zipFile);

        //Set the last access timestamp, this will stop the cron job removing the file as being old
        Date date = new Date();
        file.setLastModified(date.getTime());

        return file;

    }


    // Download the requested error file
    @RequestMapping(value =  URL_4_ERROR_FILES + "/{errorFileName}")
    public ModelAndView getErrorFile(@PathVariable("errorFileName") String errorFileName,
                                     @RequestParam(value="token", defaultValue = "0") String obfuscationCode,
                                     HttpServletResponse response) {
        try{
            String errorFilePath = PropertiesUtil.getProperty("uploadDirectory") + "queueerrors/" + errorFileName + ".zip";
            File errorFile = new File (errorFilePath);
            streamFile(errorFile,response);
            return null;
        } catch (Exception e){
            logger.error(e.getMessage());
            return  new ModelAndView ("redirect:/index?message="+ e.getMessage());
        }
    }



    // Returns the download link for a study
	public static String getDownloadLink(String study){

		String ftpLocation = null;

    	ftpLocation = study + "/" + URL_4_FILES + "/" + study;  //Private download, file stream

		return ftpLocation;
	}

    // Get the file, stream to browser
    // Stream a file to the browser
    public static void streamFile(File file, HttpServletResponse response){

        // get the extension
        String extension = StringUtils.getFilenameExtension(file.getName());

        // let the browser know the type of file
        String contenType = "application/" + extension;

        streamFile(file,response,contenType);

    }

    public static void streamFile(File file, HttpServletResponse response, String contentType ){

        try {

            // get your file as InputStream
            InputStream is = new FileInputStream(file);

            // let the browser know the type of file
            response.setContentType(contentType);

            // Specify the file name
            response.setHeader( "Content-Disposition", "filename=" + file.getName()   );

            // copy it to response's OutputStream
            IOUtils.copy(is, response.getOutputStream());

        } catch (FileNotFoundException e) {
            logger.info("Can't stream file "+ file.getAbsolutePath() + "!, File not found.");
            throw new RuntimeException(PropertyLookup.getMessage("Entry.fileMissing"));
        } catch (IOException ex) {
            logger.info("Error writing file to output stream. Filename was '"+ file.getAbsolutePath() + "'");
            throw new RuntimeException(PropertyLookup.getMessage("Entry.fileMissing"));
        }

    }

    // Stream the file or folder to the response
    private ModelAndView streamFile(String studyId, String obfuscationCode, String fileNamePattern, HttpServletResponse response) {

        try{
			File[] files = null;
			File fileToStream;

            // Get the complete path for the study folder
			File studyFolder = getPathForFileAndCheckAccess(studyId, obfuscationCode, "");

            // if file is null...
            if (studyFolder == null) {

				logger.info("File requested not found: Filename was '" + fileNamePattern + "' for the study " + studyId);
				throw new RuntimeException(PropertyLookup.getMessage("Entry.fileMissing"));
			}

			// If file name is NOT empty...then we have to look for the pattern.
			if (!fileNamePattern.equals("")) {

				FileFilter filter = new RegexFileFilter(fileNamePattern);
				files = studyFolder.listFiles(filter);
			} else {

				files = new File[1];
				files[0] = studyFolder;
			}

			// Create a zip file if apply:
			fileToStream = createZipFile(files, studyId);

            // Now we have a file (normal file, or zipped folder)
            // We need to stream it...

            streamFile(fileToStream, response);

            return null;

        } catch (Exception e){
            logger.error(e.getMessage());

            return  new ModelAndView ("redirect:/index?message="+ e.getMessage());

        }
    }

    // Compose and return the path where the file is meant to be
    // If user has no permission throws an exception...
    private File getPathForFileAndCheckAccess (String studyId, String obfuscationCode, String fileName){

        // Get where the file is (either PUBLIC or SUBMITTED)
        String pathS = getPathForFile(studyId,fileName);

        // Check if it exists
        File file = new File(pathS);

        // if it exists
        // Actually, we could get the study from the database to check the status.
        // With this approach we are assuming that the files are properly placed based on the study status (SUBMITTED/PUBLIC)
        if (file.exists()){

            // Check the user has privileges to access the file
            if (canUserAccessFile(studyId, obfuscationCode)){

                    return file;
            } else {
                // User is not allowed to access the file
                throw new RuntimeException(PropertyLookup.getMessage("Entry.notAuthorised"));
            }
        }

        return null;
    }

    private String getPathForFile(String studyId, String fileName){

        // Try the public folder
        File file = new File (PropertiesUtil.getProperty("studiesLocation") + studyId +"/" + fileName);

        if (file.exists()) return file.getAbsolutePath();

        // File not found
        return "";

    }
	private String getPathForStudy(String studyId)
	{
		return getPathForFile(studyId, "");
	}

    // Returns true if the user is allowed to get the file
    private boolean canUserAccessFile(String studyId, String obfuscationCode){

		// Get the study form the index
        MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();


        boolean granted;
        if (obfuscationCode.equals("0")) {
            granted= wsClient.canViewStudy(studyId);
        } else {
            granted= wsClient.canViewStudyByObfuscationCode(obfuscationCode);
        }

        // if granted...user can access the study...
        return granted;

    }

	public File[] getStudyFileList(String studyId) {

		String studyFolderS = getPathForStudy(studyId);

		// If found
		if (!studyFolderS.equals(""))
		{
			File studyFolder = new File(studyFolderS);

			return studyFolder.listFiles();
		}

		return null;
	}

    /**
     * Get a list of files from private upload folder for a Study
     * Only Submiters (ROLE_SUBMITTER) and Curators (ROLE_SUPER_USER) should be accessing this
     *
     * @param studyId
     * @return
     * @author: jrmacias
     * @date: 20151110
     */
    public File[] getPrivateFtpFileList(String studyId) {

        // Using the WebService-client to do the job
        MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();

        return wsClient.getPrivateFtpFileList(studyId).getContent();
    }

    /**
     * Create a private upload folder for a Study, so the user can upload big files using ftp.
     * Only Submiters (ROLE_SUBMITTER) and Curators (ROLE_SUPER_USER) should be accessing this
     *
     * @param studyId the ID of the study
     * @author: jrmacias
     * @date: 20151105
     */
    @RequestMapping(value = "/{studyId:" + EntryController.METABOLIGHTS_ID_REG_EXP + "}/" + URL_4_FILES + "/requestFtpFolder")
    public ModelAndView requestFtpFolder(@PathVariable("studyId") String studyId) {

        logger.info("Requesting a private upload folder for the study {}", studyId);

        // Using the WebService-client to do the job
        MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();
        String rslt = wsClient.requestFtpFolder(studyId).getMessage();

        // parse WS response for user feedback
        List<String> msg = new LinkedList<>();
        String[] str = rslt.split("\\|");
        for (String line:str){
            msg.add(line);
        }

        return printMessage("Creating private upload folder for Study...", msg, null, studyId);
    }

    /**
     * Move files from private upload folder for a Study.
     * Only Submiters (ROLE_SUBMITTER) and Curators (ROLE_SUPER_USER) should be accessing this
     *
     * @param studyId the ID of the study
     * @author: jrmacias
     * @date: 20151105
     */
    @RequestMapping(value = "/{studyId:" + EntryController.METABOLIGHTS_ID_REG_EXP + "}/" + URL_4_FILES + "/moveFilesfromFtpFolder",
            method = RequestMethod.POST)
    public ModelAndView moveFilesfromFtpFolder(@PathVariable("studyId") String studyId,
                                               @RequestParam("ftpFile") List<String> selectedFiles,
                                               HttpServletResponse response) {

        logger.info("Moving files from private upload folder for the study {}", studyId);

        // Using the WebService-client to do the job
        MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();
        String rslt = wsClient.moveFilesfromFtpFolder(studyId, selectedFiles).getMessage();

        // parse WS response for user feedback
        List<String> msg = new LinkedList<>();
        String[] str = rslt.split("\\|");
        for (String line:str){
            msg.add(line);
        }
        return printMessage("Moving files from private upload folder...", msg, null, studyId);
    }

    /**
     * Check if a Study has a private upload folder
     *
     * @param studyId
     * @return
     * @author: jrmacias
     * @date: 20151012
     */
    public boolean hasPrivateFtpFolder(String studyId) {

        // Using the WebService-client to do the job
        MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();

        return wsClient.hasPrivateFtpFolder(studyId);
    }

    /**
     * Delete a series of files selected from the private upload folder for a Study
     * Requires confirmation.
     * Only Submiters (ROLE_SUBMITTER) and Curators (ROLE_SUPER_USER) should be accessing this
     *
     * @param studyId the ID of the study
     * @param selectedFiles, the list of files to be deleted
     * @param response
     * @author: jrmacias
     * @date: 20151012
     */
    @RequestMapping(value = "/{studyId:" + EntryController.METABOLIGHTS_ID_REG_EXP + "}/" + URL_4_FILES + "/deleteSelFtpFiles",
            method = RequestMethod.POST)
    public ModelAndView deleteSelectedFtpFiles(@PathVariable("studyId") String studyId,
                                            @RequestParam("ftpFile") List<String> selectedFiles,
                                            HttpServletResponse response){

        // Using the WebService-client to actually delete the files
        MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();
        String rslt = wsClient.deletePrivateFtpFiles(studyId, selectedFiles).getMessage();

        // parse WS response for user feedback
        List<String> msg = new LinkedList<>();
        String[] str = rslt.split("\\|");
        for (String line:str){
            msg.add(line);
        }

        return printMessage("Deleting files from private upload folder...", msg, null, studyId);
    }
}
