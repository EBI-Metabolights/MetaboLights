package uk.ac.ebi.metabolights.controller;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.bioinvindex.model.security.User;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.StudyService;
import uk.ac.ebi.metabolights.utils.FileUtil;
import uk.ac.ebi.metabolights.utils.Zipper;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Principal;
import java.util.*;

/**
 * Controller for dispatching study files .
 * 
 */
@Controller
public class FileDispatcherController extends AbstractController {

    private static final String PUBLICFILES = "publicfiles";
    private static final String PRIVATEFILES = "privatefiles";
    private static Logger logger = Logger.getLogger(FileDispatcherController.class);

    private static final String URL_4_FILES = "files";
	@Autowired
	private StudyService studyService;

	private @Value("#{publicFtpLocation}") String publicFtpDirectory;
	private @Value("#{privateFtpStageLocation}") String privateFtpDirectory;
	private @Value("#{ondemand}") String zipOnDemandLocation;     // To store the zip files requested from the Entry page, both public and private files goes here


    // Get a single file from a study
    @RequestMapping(value = "/{studyId:" + EntryController.METABOLIGHTS_ID_REG_EXP + "}/" + URL_4_FILES + "/{fileName:.+}")
    public void getSingleFile(@PathVariable("studyId") String studyId, @PathVariable("fileName") String fileName, HttpServletResponse response) {

        // For the whole zip file fileName must match the studyId
        if (studyId.equals(fileName))
            fileName = "";

        // Stream the file
        streamFile(studyId,fileName,response);

    }

    //Create the requested zip file based on the study folder
    private File createZipFile( File folder) throws IOException {

        // Compose the path and name of the zip folder
        String zipFile = zipOnDemandLocation + folder.getName() + ".zip";

        if (!FileUtil.fileExists(zipFile))  // Just to be sure that the file *don't already* exist
            Zipper.zip(folder.getAbsolutePath(), zipFile);

        return new File(zipFile);

    }

    // Returns the download link for a study
	public static String getDownloadLink(String study, VisibilityStatus status){

		String ftpLocation = null;


    	ftpLocation = study + "/" + URL_4_FILES + "/" + study;  //Private download, file stream

		return ftpLocation;
	}

    // Get the file, stream to browser
    // Stream a file to the browser
    private void streamFile(File file, HttpServletResponse response){
        try {

            // get your file as InputStream
            InputStream is = new FileInputStream(file);

            // get the extension
            String extension = StringUtils.getFilenameExtension(file.getName());

            // let the browser know the type of file
            response.setContentType("application/" + extension);

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
    private void streamFile(String studyId, String fileName, HttpServletResponse response) {

        try{
            // Get the complete path of the file requested
            File file = getPathForFile(studyId,fileName);

            // if file is null...
            if (file == null){

                logger.info("File requested not found: Filename was '"+ fileName + "' for the study " + studyId);
                throw new RuntimeException(PropertyLookup.getMessage("Entry.fileMissing"));

            // If the file is a directory ...
            } else if (file.isDirectory()){

                // Request the zip file for the folder...if it doesn't exist it will be created
                file = createZipFile(file);

            }

            // Now we have a file (normal file, or zipped folder)
            // We need to stream it...
            streamFile(file, response);
        } catch (Exception e){
            logger.error(e.getMessage(), e);
        }
    }

    // Compose and return the path where the file is meant to be
    // If user has no permission throws an exception...
    private File getPathForFile (String studyId, String fileName){

        // Try first the public folder
        String pathS = publicFtpDirectory + studyId + "/" + fileName;

        // Check if it exists
        File file = new File(pathS);

        // if exists....
        // Actually, we could get the study from the database to check the status.
        // With this approach we are assuming that the files are properly placed based on the study status (PRIVATE/PUBLIC)
        if (file.exists()){
            return file;

        // ... file must be private
        }else{

            // Check the user has privileges to access the file
            if (canUserAccessPrivateFile(studyId)){

                // Compose the path of the file assuming it is in the private folder
                file = new File (privateFtpDirectory + studyId + "/" + fileName);

                if (file.exists()){
                    return file;
                }
            } else {

                // User is not allowed to access the file
                throw new RuntimeException(PropertyLookup.getMessage("Entry.notAuthorised"));
            }
        }

        // If code reaches this line it's because there is no file.
        return null;

    }

    // Returns true if the user is allowed to get the file
    private boolean canUserAccessPrivateFile (String studyId){

        //TODO, not very elegant, this is just to determine if the logged in user us a curator
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal().equals(new String("anonymousUser"))){

            // User is not logged in...can't access the study
            logger.info("anonymousUser not allowed to access " + studyId + " files");
            return false;
        }

        // We have a proper MetaboLights user
        MetabolightsUser metabolightsUser = (MetabolightsUser) auth.getPrincipal();


        // ... if user is a curator:
        if (metabolightsUser.isCurator()) {
            return true;

        // ... the user is not a curator but is logged in:
        } else {

            // Check if the user is granted to access the study
            // Get the study
            Study study = studyService.getBiiStudy(studyId,true);

            // Get the list of users authorised to view the study
            Collection<User> users = study.getUsers();
            Iterator<User> iter = users.iterator();

            // Go through the list of users
            while (iter.hasNext()){
                User user = iter.next();

                if (user.getUserName().equals(metabolightsUser.getUserName())){
                    return true;
                }
            }
        }

        // User can't access the file
        logger.info(metabolightsUser.getUserName() + " not allowed to access " + studyId + " files");
        return false;

    } // End of method
}
