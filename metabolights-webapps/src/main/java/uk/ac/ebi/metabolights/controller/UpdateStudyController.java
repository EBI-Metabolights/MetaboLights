package uk.ac.ebi.metabolights.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.isatools.tablib.utils.logging.TabLoggingEventWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabUploader;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.model.queue.SubmissionItem;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.search.LuceneSearchResult;
import uk.ac.ebi.metabolights.service.EmailService;
import uk.ac.ebi.metabolights.service.SearchService;
import uk.ac.ebi.metabolights.service.StudyService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Make a study public. THis implies to change the status in the database, reindex, and move the zip file to the public ftp.
 * 
 * @author conesa
 */
@Controller
public class UpdateStudyController extends AbstractController {

	private static Logger logger = Logger.getLogger(UpdateStudyController.class);

	//Ftp locations
	private @Value("#{publicFtpStageLocation}") String publicFtpLocation;
	private @Value("#{privateFtpStageLocation}") String privateFtpLocation;         //TODO, short term fix until filesystem is mounted RW
	private @Value("#{uploadDirectory}") String uploadDirectory;
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private StudyService studyService;

	@Autowired
	private EntryController entryController;
	
	@Autowired
	private EmailService emailService;
		
	@Autowired
	private SubmissionController submissionController;

	/**
	 * Receives the study that is going to be published and shows the updateStudy Page to let the user to set the public release date.
	 * 
	 * @param study
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/updatepublicreleasedateform"})
	public ModelAndView updatePublicReleaseDate(@RequestParam(required=true,value="study") String study, HttpServletRequest request) throws Exception{
		
		// Get the correspondent ModelAndView
		return getModelAndView(study, false);
		
	}
	
	/**
	 * Receives the study that is going to be updated and shows the updateStudy Page to let the user to set the public release date and upload the new file.
	 * 
	 * @param study
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/updatestudyform"})
	public ModelAndView updateStudy(@RequestParam(required=true,value="study") String study, HttpServletRequest request) throws Exception{
		
		// Get the correspondent ModelAndView
		return getModelAndView(study, true);
	
	}
	
	
	/**
	 * Return the model and view ready to be rendered in the jsp that share 2 modes. Update and MakeStudyPublic
	 * @param study
	 * @param isUpdateMode
	 * @return
	 * @throws Exception 
	 */
	private ModelAndView getModelAndView(String study, boolean isUpdateMode) throws Exception{
		
		//Get the study data
		LuceneSearchResult luceneStudy = getStudy(study);
		
		ModelAndView mav = new ModelAndView("updateStudyForm");
		
		// Add objects to the model and view
		mav.addObject("searchResult", luceneStudy);
		mav.addObject("isUpdateMode", isUpdateMode);
		mav.addObject("study", study);
		
		
		String title ="", msg ="", action="", submitText="";
		
		String studyShortTitle = luceneStudy.getTitle();
		if (studyShortTitle.length() > 47) studyShortTitle = (studyShortTitle.substring(0, 47) + "...");
		
		// Fill the output title, msg, ...depending on the mode
		if (isUpdateMode){
			
			title = PropertyLookup.getMessage("msg.updatestudy.title", study,  studyShortTitle);
			msg = PropertyLookup.getMessage("msg.updatestudy.msg");
			submitText = PropertyLookup.getMessage("label.updatestudy");
			
			// Redirect to que the queue system: action = "updatestudy";
			action ="queueExperiment";
			
			
			// Get the DownloadLink
			String ftpLocation = entryController.getDownloadLink(luceneStudy.getAccStudy(), luceneStudy.getIsPublic()? VisibilityStatus.PUBLIC: VisibilityStatus.PRIVATE );
			mav.addObject("ftpLocation", ftpLocation);
			
		} else {
			
			title = PropertyLookup.getMessage("msg.makestudypublic.title", study, studyShortTitle);
			msg = PropertyLookup.getMessage("msg.makestudypublic.msg");
			submitText = PropertyLookup.getMessage("label.makestudypublic");
			action = "updatepublicreleasedate";
			
		}
		
		mav.addObject("title", title);
		mav.addObject("message", msg);
		mav.addObject("action", action);
		mav.addObject("submitText", submitText);
		return mav;
	}
	
	
	private ModelAndView validateParameters(RequestParameters params) throws Exception{
		

		// Validate the parameters
		params.validate();
				
		// If there is validation message...
		if (!params.validationmsg.isEmpty()){
			
			// Prepare the same view but this time with the validation message.
			ModelAndView validation = getModelAndView(params.studyId, params.isUpdateStudyMode);
			validation.addObject("validationmsg", params.validationmsg);
			return validation;
		}
				
		// Calculate the date and status
		params.calculateStatusAndDate();
		
		return null;
	}

    /*
        Update the studies release date and status
     */
    public void updatePublicReleaseDate(String study, VisibilityStatus status, Date publicReleaseDate, Long userId) throws Exception {

        // Add the user id to the unzip folder
        String unzipFolder = uploadDirectory + userId + "/" + study;

        // Create the uploader
        IsaTabUploader itu = new IsaTabUploader();

        // Set properties for file copying...
        itu.setCopyToPrivateFolder(privateFtpLocation);
        itu.setCopyToPublicFolder(publicFtpLocation);

        // Change the status
        try {

            //Check if the zip file exists before changing anything else
            File zipFile = new File (itu.getStudyFilePath(study, VisibilityStatus.PUBLIC));

            // If not in public folder...
            if (!zipFile.exists()){

                // Try it in the private
                zipFile = new File (itu.getStudyFilePath(study, VisibilityStatus.PRIVATE));

                // Check if it exists
                if (!zipFile.exists()){

                    // Throw an exception
                    throw new FileNotFoundException (PropertyLookup.getMessage("msg.makestudypublic.nofilefound", study));

                }
            }


            // ************************
            // Update the database first...
            // ************************
            // Get the study object
            Study biiStudy = studyService.getBiiStudy(study,false);

            // Set the new Public Release Date
            biiStudy.setReleaseDate(publicReleaseDate);
            biiStudy.setStatus(status);

            logger.info("Updating study (database)");
            // Save it
            studyService.update(biiStudy);


            // ************************
            // Index it...
            // ************************
            //Get the path for the config folder (where the hibernate properties for the import layer are).
            //String configPath = UpdateStudyController.class.getClassLoader().getResource("").getPath() + "biiconfig/";
            String configPath = UpdateStudyController.class.getClassLoader().getResource("").getPath();

            // Set the config folder, and the ftp folders
            itu.setDBConfigPath(configPath);

            // reindex the study...
            itu.reindexStudies(study);


            // ************************
            // Change the zip file
            // ************************
            // Set the unzip folder
            itu.setUnzipFolder(unzipFolder);

            // Create the replacement Hash
            HashMap<String,String> replacementHash = new HashMap<String,String>();

            // Add the Public release date field with the new value
            replacementHash.put("Study Public Release Date", new SimpleDateFormat("yyyy-MM-dd").format(publicReleaseDate));

            logger.info("Replacing Study Public Release Date in zip file. with " + publicReleaseDate);
            // Call the replacement method...
            itu.changeStudyFields(study, replacementHash);

            // If the new status is public...
            if (status == VisibilityStatus.PUBLIC){

                // Move the file from the private
                itu.moveFile(study, VisibilityStatus.PRIVATE);  //Need to pass the old status, not the new public status

                // Change the permissions
                String studyPath = itu.getStudyFilePath(study, VisibilityStatus.PUBLIC);
                itu.changeFilePermissions(studyPath, VisibilityStatus.PUBLIC);
            }




        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IsaTabException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            throw new Exception("updatePublicReleaseDate error");
        }


    }


     @RequestMapping(value = { "/updatepublicreleasedate" })
	public ModelAndView changePublicReleaseDate(
								@RequestParam(required=true,value="study") String study,
								@RequestParam(required=true, value="pickdate") String publicReleaseDateS,
								HttpServletRequest request) throws Exception {


		// Instantiate the param objects
		RequestParameters params = new RequestParameters(publicReleaseDateS, study);

		// Log start
		logger.info("Updating the public release date of the study " + study + " owned by " + params.user.getUserName());
		
		// Validate the parameters...
		ModelAndView validation = validateParameters(params);
		
		// If there is validation view...return it
		if (validation != null){return validation;}

        //Create the view
        ModelAndView mav = new ModelAndView("updateStudyForm");

        try{

        	//mav = queuePublicReleaseDate(study, params.publicReleaseDate, params.user);
        	
            updatePublicReleaseDate(study, params.status, params.publicReleaseDate, params.user.getUserId());
			
			// Compose the messages...
			mav.addObject("title", PropertyLookup.getMessage("msg.makestudypublic.ok.title"));
			mav.addObject("message", PropertyLookup.getMessage("msg.makestudypublic.ok.msg"));
			mav.addObject("searchResult", getStudy(study));
			mav.addObject("updated", true);
		
		} catch (Exception e) {
			
			String message = "There's been a problem while changing the Public Release date of the study " + study + "\n" + e.getMessage();
			
			// Auto-generated catch block
			logger.error(message);
			
			// Add the error to the page
			throw new Exception (message);
			
		}
		
		//Return the ModelAndView
		return mav;
		
	}
    
    /**
     * This method will create a file in the queue folder that represent the task of updating the public release date of a single study.
     * @param study
     * @param status
     * @param publicReleaseDate
     * @param userName
     * @return
     * @throws IOException
     * @throws IllegalStateException 
     */
    private ModelAndView queuePublicReleaseDate(String accesion, Date publicReleaseDate, MetabolightsUser user) throws IllegalStateException, IOException{
    	 
    	String hostName = java.net.InetAddress.getLocalHost().getHostName();
    	
    	SubmissionItem si = new SubmissionItem(null,user,publicReleaseDate,accesion);
    	si.submitToQueue();
    	
		// Cannot load the queue
		emailService.sendQueuedPRLUpdate(si.getUserId(), si.getPublicReleaseDate(), hostName, accesion);
		
    	return new ModelAndView("redirect:itemQueued");

    	 
    }
	
	/**
	 * Re-submit process:
		Each step must successfully validate, if not then stop and display an error
		no need to allocate a new MTBLS id during this process
		OK - Check that the logged in user owns the chosen study
		OK - Check that the study is PRIVATE (? what do we think ?).  This could stop submitters from "nullifying" a public study.
		OK - Unzip the new zipfile and check that the study id is matching (MTBLS id)
		OK - Update new zipfile with Public date from the resubmission form
		OK - Unload the old study
		OK - IF SUCCESSFULLY UNLOADED =  DO NOT Remove old study zipfile
		OK - IF ERROR = Reupload the old zipfile, DO NOT Remove old study zipfile
		OK - Upload the new study (includes Lucene re-index)
		OK - IF ERROR = Reupload the old zipfile, DO NOT Remove old study zipfile
		OK - Copy the new zipfile to the correct folder (public or private locations)
		OK - Remove old study zipfile
		Display a success or error page to the submitter.  Email metabolights-help and submitter with results
	 * @param file
	 * @param study
	 * @param publicReleaseDateS
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = { "/updatestudy" })
	public ModelAndView updateStudy(
			@RequestParam("file") MultipartFile file, 
			@RequestParam(required=true,value="study") String study,
			@RequestParam(required=false, value="pickdate") String publicReleaseDateS,
			HttpServletRequest request) throws Exception{
		
		
		logger.info("Starting Updating study " + study);
		
		// Instantiate the param objects
		RequestParameters params = new RequestParameters(publicReleaseDateS, study, file);
		
		// Validate the parameters...
		ModelAndView validation = validateParameters(params);
		
		// If there is validation view...return it
		if (validation != null){return validation;}
		
		
		// Define the back up path of the existing file 
		File backup = new File(uploadDirectory + "backup/" + study + ".zip");
		boolean needRestore = false;
		
		try {
			
			// Write the file to the proper location
			File isaTabFile = new File(submissionController.writeFile(file, null));
			
			// Get the uploader configured...
			IsaTabUploader itu = submissionController.getIsaTabUploader(isaTabFile.getAbsolutePath(), params.status, params.publicReleaseDateS);
						
			// Check that the new zip file has the same studyID
			Map<String,String> zipValues = itu.getStudyFields(isaTabFile, new String[]{"Study Identifier"});
			
			String newStudyId = zipValues.get("Study Identifier");
			
			// If Ids do not match...
			if (!study.equals(newStudyId)){
				validation = getModelAndView(study, true);
				validation.addObject("validationmsg", PropertyLookup.getMessage("msg.validation.studyIdDoNotMatch",newStudyId,study));
				//throw new Exception(PropertyLookup.getMessage("msg.validation.studyIdDoNotMatch",newStudyId,study));
				return validation;
			}
			
			// Check there is a previous back up
			if (backup.exists()){
				throw new Exception(PropertyLookup.getMessage("msg.validation.backupFileExists", study));
			}
			
			//Validate the new file
			try{
				// It has to be a directory...the call to getStudyFile has unzipped the file to unzip folder. We will use it.
				itu.validate(itu.getUnzipFolder());
				
			}catch (Exception e){
				
				validation = getModelAndView(study, true);
				validation.addObject("validationmsg", PropertyLookup.getMessage("msg.validation.invalid"));
				List<TabLoggingEventWrapper> isaTabLog = itu.getSimpleManager().getLastLog();
				validation.addObject("isatablog", isaTabLog);
				
				return validation;
			}
			
			// Make the backup...
			File currentFile = new File(itu.getStudyFilePath(study, VisibilityStatus.PRIVATE));
			FileUtils.copyFile(currentFile, backup);
			
			// Unload the study, this will remove the file too.
			logger.info("Deleting previous study " + study);
			itu.unloadISATabFile(study);
			
			// From this point restoring the backup must be done in case of an exception
			needRestore = true;
			
			// upload the new study with the new date
			// NOTE: this will unzip again the file (done previously in the getStudyFields
			// To avoid unziping it twice (specially for large files) we can set the isaTabFile property
			// to the unzipped folder and it should work...
			itu.setIsaTabFile(itu.getUnzipFolder());
			
			// To test the restore backup
			//if (needRestore){throw new Exception("fake exception");}
			
			logger.info("Uploading new study"); 
			
			itu.UploadWithoutIdReplacement(study);

			// Remove the backup
			needRestore = false;
			backup.delete();
			
			// Return the result
			// Compose the messages...
			ModelAndView mav = new ModelAndView("updateStudyForm");
			mav.addObject("title", PropertyLookup.getMessage("msg.updatestudy.ok.title", study));
			mav.addObject("message", PropertyLookup.getMessage("msg.updatestudy.ok.msg",study));
			// We need the new study, the old might have wrong Public release date.
			mav.addObject("searchResult", getStudy(study));
			mav.addObject("updated", true);
				
			return mav;
			
		} catch (Exception e){
			
			// If there is a need of restoring the backup
			if (needRestore){
			 
				// Restore process...
				// Calculate the previous status
				VisibilityStatus oldStatus = params.study.getIsPublic()?VisibilityStatus.PUBLIC: VisibilityStatus.PRIVATE;

				
//				// Error:
//				Caused by: java.lang.InterruptedException: sleep interrupted
//				at java.lang.Thread.sleep(Native Method)
//				at org.isatools.isatab.commandline.AbstractImportLayerShellCommand.createDataLocationManager(AbstractImportLayerShellCommand.java:402)
				
				// Get the uploader configured
				
				IsaTabUploader itu = submissionController.getIsaTabUploader(backup.getAbsolutePath(), oldStatus, null);
				
				// Upload the old study
				itu.UploadWithoutIdReplacement(study);
				
				// Delete the backup
				backup.delete();
				
				// TODO: Send email. Return a different response...
				throw new Exception("There was an error while updating the study. We have restored the previous experiment. " + e.getMessage());
				
			}else{
				throw e;
			}
		}
		
	}
	
	/**
	 * Gets the study that has just been published.
	 * @param study
	 * @return
	 * @throws Exception 
	 */
	public LuceneSearchResult getStudy(String study) throws Exception{
		
		//Search results
		HashMap<Integer, List<LuceneSearchResult>> searchResultHash = new HashMap<Integer, List<LuceneSearchResult>>(); // Number of documents and the result list found
				
		//Get the query...	
		String luceneQuery = "acc:"+ study;
		
		logger.info("Searching for "+ luceneQuery);
		
		//Get the search result...
		searchResultHash = searchService.search(luceneQuery); 
		
		// Get the result (Study)
		// There must be only one
		LuceneSearchResult result = searchResultHash.values().iterator().next().get(0); 
		
		return result;
			
	
	}
	
	/**
	 * Parameters from the request. It parses and validate the parameters.
	 * @author conesa
	 *
	 */
	public class RequestParameters{
		
		String publicReleaseDateS;
		Date publicReleaseDate;
		VisibilityStatus status;
		MultipartFile file;
		String studyId;
		String validationmsg;
		Boolean isUpdateStudyMode;
		LuceneSearchResult study;
		MetabolightsUser user;
		
		/**
		 * 
		 * @param publicReleaseDateS: Should be in a "dd/mm/yyyy" format
		 * @param studyId
		 * @throws Exception
		 */
		public RequestParameters(String publicReleaseDateS, String studyId) throws Exception{
			
			// "normalize" the date to IsaTabFormat..
			if (publicReleaseDateS == null){
				this.publicReleaseDateS = "";
			} else {
				this.publicReleaseDateS = publicReleaseDateS;
			}

			this.studyId = studyId;
			this.study = getStudy(studyId);
			this.isUpdateStudyMode = false;	
			
			// Get the user
			this.user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

			
		}
		
		
		public RequestParameters(String publicReleaseDateS, String study, MultipartFile file) throws Exception{
			
			this(publicReleaseDateS, study);
			this.isUpdateStudyMode = true;
			this.file = file;

		}
		
		/**
		 * Validate the parameters
		 * @return
		 */
		public String validate(){
			
			validationmsg = "";
			
			// If public release date is empty...
			if ( publicReleaseDateS.isEmpty()){
				
				// a date is required
				validationmsg = PropertyLookup.getMessage("msg.makestudypublic.daterequired");
			}
			
			if (isUpdateStudyMode && file.isEmpty()){
				// file is required
				validationmsg = validationmsg +  PropertyLookup.getMessage("msg.upload.notValid"); 
			}
			
			// Double check the user owns the study
			if (!study.getSubmitter().getUserName().equals(user.getUserName())){
				// ... user do not own the study
				validationmsg = validationmsg +  PropertyLookup.getMessage("msg.validation.studynotowned");
			}
			
			// Do not continue if the study is public.
			if (study.getIsPublic()){
				// ... a public study cannot be modified
				validationmsg = validationmsg +  PropertyLookup.getMessage("msg.validation.publicstudynoteditable");
			}
			
			return validationmsg ;

		}

		public void calculateStatusAndDate() throws ParseException {
			
			//Check if the study is public today
            status = VisibilityStatus.PRIVATE;   //Defaults to a private study
            publicReleaseDate = DateUtils.truncate(new Date(),Calendar.DAY_OF_MONTH); //Defaults to today.  Should come from the form, so just to be sure
            
            if (!publicReleaseDateS.isEmpty()) {
            	publicReleaseDate = new SimpleDateFormat("dd-MMM-yyyy").parse(publicReleaseDateS);  //Date from the form             

                if (publicReleaseDate.before(new Date())){  //The date received from the form does not contain time, so this should always be before "now"
                    status = VisibilityStatus.PUBLIC;
                }
            }

		}
		
	}


}