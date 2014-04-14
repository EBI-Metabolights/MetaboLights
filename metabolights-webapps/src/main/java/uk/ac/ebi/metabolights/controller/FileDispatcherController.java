package uk.ac.ebi.metabolights.controller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.StudyService;
import uk.ac.ebi.metabolights.utils.FileUtil;
import uk.ac.ebi.metabolights.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.utils.Zipper;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Controller for dispatching study files .
 * 
 */
@Controller
public class FileDispatcherController extends AbstractController {

    private static Logger logger = Logger.getLogger(FileDispatcherController.class);

    private static final String URL_4_FILES = "files";
	@Autowired
	private StudyService studyService;

//	private  @Value("#{publicFtpLocation}") String publicFtpDirectory;
//	private  @Value("#{privateFtpStageLocation}") String privateStageDirectory;
	private  @Value("#{ondemand}") String zipOnDemandLocation;     // To store the zip files requested from the Entry page, both public and private files goes here


    // Get a single file from a study
    @RequestMapping(value = "/{studyId:" + EntryController.METABOLIGHTS_ID_REG_EXP + "}/" + URL_4_FILES + "/{fileNamePattern:.+}")
    public ModelAndView getSingleFile(@PathVariable("studyId") String studyId,
                              @PathVariable("fileNamePattern") String fileNamePattern,
                              HttpServletResponse response) {


		// We can receive any regularexpresion pattern...but some of them may not be allowed in the URL:
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
        return streamFile(studyId, fileNamePattern,response);

    }

	@RequestMapping(value = "/{studyId:" + EntryController.METABOLIGHTS_ID_REG_EXP + "}/" + URL_4_FILES + "/selection")
	public ModelAndView getSomeFiles(@PathVariable("studyId") String studyId,
									  @RequestParam("file") List<String> selectedFiles,
									  HttpServletResponse response) {


		// Join the files to make a regular expression
		String fileNamePattern = org.apache.commons.lang.StringUtils.join(selectedFiles, "|");

		return getSingleFile(studyId, fileNamePattern, response);
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
        streamFile(studyId,mafFileName,response);

    }


    //Create the requested zip file based on the study folder
    private File createZipFile( File[] files, String studyId) throws IOException {

		String zipFile="";

		// If there is only one file
		if (files.length == 1 && files[0].getName().equals(studyId) )
		{
			zipFile = zipOnDemandLocation + files[0].getName() + ".zip";
		} else {

			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

			zipFile = studyId + "_" +  timeStamp;

			zipFile = System.getProperty("java.io.tmpdir") + "/"+ zipFile + ".zip";

		}



        if (!FileUtil.fileExists(zipFile))  // Just to be sure that the file *doesn't already* exist
            Zipper.zip(files, zipFile);


        File file = new File(zipFile);

        //Set the last access timestamp, this will stop the cron job removing the file as being old
        Date date = new Date();
        file.setLastModified(date.getTime());

        return file;

    }

    // Returns the download link for a study
	public static String getDownloadLink(String study, VisibilityStatus status){

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
    private ModelAndView streamFile(String studyId, String fileNamePattern, HttpServletResponse response) {

        try{
			File[] files = null;
			File fileToStream;

            // Get the complete path for the study folder
			File studyFolder = getPathForFileAndCheckAccess(studyId, "");

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

			// Create a zip file if aply:
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
    private File getPathForFileAndCheckAccess (String studyId, String fileName){

        // Get where the file is (either PUBLIC or PRIVATE)
        String pathS = getPathForFile(studyId,fileName);

        // Check if it exists
        File file = new File(pathS);

        // if it exists
        // Actually, we could get the study from the database to check the status.
        // With this approach we are assuming that the files are properly placed based on the study status (PRIVATE/PUBLIC)
        if (file.exists()){

            // Check the user has privileges to access the file
            if (canUserAccessFile(studyId, file)){

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
        File file = new File (PropertiesUtil.getProperty("publicFtpLocation") + studyId +"/" + fileName);

        if (file.exists()) return file.getAbsolutePath();


        // Try the private folder
        file = new File(PropertiesUtil.getProperty("privateFtpStageLocation") + studyId + "/" + fileName);

        if (file.exists()) return file.getAbsolutePath();

        // File not found
        return "";

    }
	private String getPathForStudy(String studyId)
	{
		return getPathForFile(studyId, "");
	}

    // Returns true if the user is allowed to get the file
    private boolean canUserAccessFile (String studyId, File file){


        // First check if the file is in the public folder...
        if (file.getAbsolutePath().indexOf(PropertiesUtil.getProperty("publicFtpLocation"))>-1) return true;

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

            try {
                // Check if the user is granted to access the study
                // Get the study
                Study study = studyService.getBiiStudy(studyId,true);

                return true;

            } catch (IllegalAccessException e){

                // User can't access the file
                logger.info(metabolightsUser.getUserName() + " not allowed to access " + studyId + " files");
                return false;

            }
        }
    } // End of method

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
}
