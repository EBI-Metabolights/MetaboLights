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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.utils.FileUtil;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.webservice.utils.SecurityUtil;
import uk.ac.ebi.metabolights.webservice.utils.Zipper;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
@RequestMapping("dataFiles")
public class FilesController {

    private @Value("#{ondemand}")
    String zipOnDemandLocation;

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
            file.transferTo(new File(zip_file_folder + File.separator + file.getOriginalFilename()));
            Zipper.zip(zipFileFolder, zipFile);

            FileUtils.deleteDirectory(zip_file_folder);
            FileUtils.moveFileToDirectory(new File(zipFile),new File(uploadDirectory), false);
            restResponse.setMessage("Success");
        }else{
            restResponse.setMessage("Something went wrong, Please try again!");
        }
        return restResponse;
    }

}
