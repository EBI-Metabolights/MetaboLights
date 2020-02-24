/*
 * EBI MetaboLights - https://www.ebi.ac.uk/metabolights
 * Metabolomics team
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2018-Jul-04
 * Modified by:   kalai
 *
 * Copyright 2018 EMBL - European Bioinformatics Institute
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

import org.apache.commons.compress.compressors.FileNameUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.utils.FileUtil;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.webservice.utils.SecurityUtil;
import uk.ac.ebi.metabolights.webservice.utils.Zipper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("dataFiles")
public class FilesController {

    private @Value("#{ondemand}")
    String zipOnDemandLocation;
    public static final String METABOLIGHTS_ID_REG_EXP = "(?:MTBLS|mtbls).+";
    protected static final Logger logger = LoggerFactory.getLogger(FilesController.class);

    @RequestMapping(value = "uploadFile", method = RequestMethod.POST,consumes = {"multipart/form-data"})
    @ResponseBody
    public RestResponse<String> uploadFile(
            HttpServletRequest request,
            @RequestParam(required = true ,value = "file") MultipartFile file,
            @RequestParam(required=true,value="publicDate") String publicDate,
            @RequestParam(required=true,value="study") String studyID) throws Exception {

        RestResponse restResponse = new RestResponse();
        User user = SecurityUtil.validateJWTToken(request);


        if (user == null || user.getRole().equals(AppRole.ANONYMOUS)) {
            restResponse.setContent("Authentication failed ");
            return restResponse;
        }

        String FILE_NAME_SEP = "~";

        String fileName = user.getApiToken() + FILE_NAME_SEP + studyID + FILE_NAME_SEP + publicDate + FILE_NAME_SEP +
                FilenameUtils.removeExtension(file.getOriginalFilename()) + "_" + FileUtil.getCurrentTimeStamp() ;

        String uploadDirectory = PropertiesUtil.getProperty("uploadDirectory") + "queue/";
        // create zip with pattern in ondemand folder
        // move multipart file to onto the zip folder
        // move the zip folder to the upload directory/queue

        String zipFile = zipOnDemandLocation + fileName + ".zip";
        String zipFileFolder = zipOnDemandLocation + fileName ;

        if (!FileUtil.fileExists(zipFileFolder))  // Just to be sure that the file *doesn't already* exist
        {
            File zip_file_folder = new File(zipFileFolder);
            zip_file_folder.mkdir();
            String destinationFileName =  zip_file_folder + File.separator + file.getOriginalFilename();
            file.transferTo(new File(destinationFileName));
            if(FilenameUtils.isExtension(destinationFileName,"zip")){
                Zipper.unzip2(destinationFileName);
                File unzippedStudyDirectory = new File(zip_file_folder + File.separator
                        + FilenameUtils.removeExtension(file.getOriginalFilename()));
                if(unzippedStudyDirectory.isDirectory()){
                     for(File subFile : unzippedStudyDirectory.listFiles()){
                        if(subFile.isFile()){
                            FileUtils.moveFileToDirectory(subFile,zip_file_folder,false);
                        } else{
                            FileUtils.moveDirectoryToDirectory(subFile,zip_file_folder,false);
                        }
                     }
                }
                FileUtils.deleteDirectory(unzippedStudyDirectory);
                new File(destinationFileName).delete();

            }
            Zipper.zip(zipFileFolder, zipFile);

            FileUtils.deleteDirectory(zip_file_folder);
            FileUtils.moveFileToDirectory(new File(zipFile),new File(uploadDirectory), false);
            restResponse.setMessage("Success");
        }else{
            restResponse.setMessage("Something went wrong, Please try again!");
        }
        return restResponse;
    }

    @RequestMapping(value = "uploadFileToQueue", method = RequestMethod.POST,consumes = {"multipart/form-data"})
    @ResponseBody
    public RestResponse<String> uploadFileToQueue(
            HttpServletRequest request,
            @RequestParam(required = true ,value = "file") MultipartFile file,
            @RequestParam(required=true,value="publicDate") String publicDate,
            @RequestParam(required=true,value="study") String studyID) throws Exception {

        RestResponse restResponse = new RestResponse();
        User user = SecurityUtil.validateJWTToken(request);

        if (isValidUser(user)) {

            String FILE_NAME_SEP = "~";
            String fileName = "";

            if(FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("zip")){
               fileName = user.getApiToken() + FILE_NAME_SEP + studyID + FILE_NAME_SEP + publicDate + FILE_NAME_SEP +
                    FilenameUtils.removeExtension(file.getOriginalFilename()) + "_" + FileUtil.getCurrentTimeStamp() + ".zip";
            }  else{
                fileName = user.getApiToken() + FILE_NAME_SEP + studyID + FILE_NAME_SEP + publicDate + FILE_NAME_SEP +
                         FilenameUtils.getName(file.getOriginalFilename());
            }

            String uploadDirectory = PropertiesUtil.getProperty("uploadDirectory") + "queue/";

            //create file name with pattern on queue.
            // transfer file contents to the queue folder
            // set message
            // within queue model, make sure unzipped files are processed

            String toBeQueuedFile = uploadDirectory + fileName;

            if (!FileUtil.fileExists(toBeQueuedFile))  // Just to be sure that the file *doesn't already* exist
            {
                file.transferTo(new File(toBeQueuedFile));
                restResponse.setMessage("Success");
            }else{
                restResponse.setMessage("Something went wrong, Please try again!");
            }
            return restResponse;

        }  else{
            restResponse.setContent("Authentication failed ");
            return restResponse;
        }

    }
    
    @RequestMapping(value = "/download/{studyId:" + METABOLIGHTS_ID_REG_EXP + "}/{fileNamePattern:.+}", method = RequestMethod.GET)
    public void downloadSingleFile(HttpServletRequest request,
                              @PathVariable("studyId") String studyId,
                              @PathVariable("fileNamePattern") String fileNamePattern,
                              HttpServletResponse response) {

        if(isValidUser(request)){
            if (fileNamePattern== null || fileNamePattern.isEmpty())
            {
                fileNamePattern = "";
            } else if ("metadata".equals(fileNamePattern)){
                fileNamePattern =  ".*\\.txt|.*.\\.tsv|.*\\.maf";
            }
            streamFile(studyId,fileNamePattern,response);
        }  else{
            response.setStatus(401);
            return;
        }
    }

    @RequestMapping(value = "/downloadAll/{studyId:" + METABOLIGHTS_ID_REG_EXP + "}", method = RequestMethod.GET)
    @ResponseBody
    public void downloadWholeStudy(HttpServletRequest request,
                              @PathVariable("studyId") String studyId, HttpServletResponse response) {

        if(isValidUser(request)){
            streamFile(studyId,"",response);
        }  else{
            response.setStatus(401);
            return;
        }
    }

    private boolean isValidUser(HttpServletRequest request){
        User user = SecurityUtil.validateJWTToken(request);
        return (user != null && !user.getRole().equals(AppRole.ANONYMOUS));
    }

    private boolean isValidUser(User user){
       return (user != null && !user.getRole().equals(AppRole.ANONYMOUS));
    }

    // Stream the file or folder to the response
    private void streamFile(String studyId, String fileNamePattern, HttpServletResponse response) {

        try{
            File[] files = null;
            File fileToStream;

            // Get the complete path for the study folder
            File studyFolder = getPathForFile(studyId, fileNamePattern);

            // if file is null...
            if (studyFolder == null) {

                logger.info("File requested not found: Filename was '" + fileNamePattern + "' for the study " + studyId);
                response.setStatus(404);
                return;
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
            fileToStream = Zipper.createZipFile(zipOnDemandLocation, files, studyId);

            // Now we have a file (normal file, or zipped folder)
            // We need to stream it...

            streamFile(fileToStream, response);

        } catch (Exception e){
            logger.error(e.getMessage());
            response.setStatus(500);
        }
    }


    private File getPathForFile(String studyId, String fileName){

        File file = null;

        if(!fileName.isEmpty()){
             file = new File (PropertiesUtil.getProperty("studiesLocation") + studyId +"/" + fileName);
        } else{
            file = file = new File (PropertiesUtil.getProperty("studiesLocation") + studyId);
        }

        if (file.exists()) {
            return new File (PropertiesUtil.getProperty("studiesLocation") + studyId);
        }
        // File not found
        return null;

    }

    // Get the file, stream to browser
    // Stream a file to the browser
    public static void streamFile(File file, HttpServletResponse response){

        // get the extension
        String extension = StringUtils.getFilenameExtension(file.getName());

        // let the browser know the type of file
        String contentType = "application/" + extension;

        streamFile(file,response,contentType);

    }

    public static void streamFile(File file, HttpServletResponse response, String contentType ){

        try {

            // get your file as InputStream
            InputStream is = new FileInputStream(file);

            // let the browser know the type of file
            response.setContentType(contentType);

            // Specify the file name
            response.setHeader( "Content-Disposition", "filename=" + file.getName()   );
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition" );
           
            // copy it to response's OutputStream
            IOUtils.copy(is, response.getOutputStream());

            response.flushBuffer();

        } catch (FileNotFoundException e) {
            logger.info("Can't stream file "+ file.getAbsolutePath() + "!, File not found.");
            response.setStatus(404);
        } catch (IOException ex) {
            logger.info("Error writing file to output stream. Filename was '"+ file.getAbsolutePath() + "'");
            response.setStatus(500);
        }

    }


}
