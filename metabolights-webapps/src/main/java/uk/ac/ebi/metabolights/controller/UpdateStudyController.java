package uk.ac.ebi.metabolights.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
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
import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabUploader;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.search.LuceneSearchResult;
import uk.ac.ebi.metabolights.service.SearchService;
import uk.ac.ebi.metabolights.service.StudyService;

/**
 * Make a study public. THis implies to change the status in the database, reindex, and move the zip file to the public ftp.
 * 
 * @author conesa
 */
@Controller
public class UpdateStudyController extends AbstractController {

	private static Logger logger = Logger.getLogger(UpdateStudyController.class);

	//Ftp locations
	private @Value("#{appProperties.publicFtpLocation}") String publicFtpLocation;
	private @Value("#{appProperties.privateFtpLocation}") String privateFtpLocation;
	private @Value("#{appProperties.uploadDirectory}") String uploadDirectory;
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private StudyService studyService;

	@Autowired
	private EntryController entryController;
	
	@Autowired
	private BIISubmissionController submissionController;

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
			action = "updatestudy";
			
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
	
	@RequestMapping(value = { "/updatepublicreleasedate" })
	public ModelAndView changePublicReleaseDate(
								@RequestParam(required=true,value="study") String study,
								@RequestParam(required=false,value="public") Boolean publicExp,
								@RequestParam(required=false, value="pickdate") String publicReleaseDateS,
								HttpServletRequest request) throws Exception {


		// Instantiate the param objects
		RequestParameters params = new RequestParameters(publicReleaseDateS, publicExp, study);

		// Log start
		logger.info("Updating the public release date of the study " + study + " owned by " + params.user.getUserName());
		
		// Validate the parameters...
		ModelAndView validation = validateParameters(params);
		
		// If there is validation view...return it
		if (validation != null){return validation;}
		
		// Add the user id to the unzip folder
		String unzipFolder = uploadDirectory + params.user.getUserId() + "/" + study;
		
		// Create the uploader
		IsaTabUploader itu = new IsaTabUploader();
		
		// Set properties for file copying...
		itu.setCopyToPrivateFolder(privateFtpLocation);
		itu.setCopyToPublicFolder(publicFtpLocation);

		//Create the view
		ModelAndView mav = new ModelAndView("updateStudyForm");
		
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
			biiStudy.setReleaseDate(params.publicReleaseDate);
			biiStudy.setStatus(params.status);
			
			logger.info("Updating study (database)");
			// Save it
			studyService.update(biiStudy);

			
			// ************************
			// Index it...
			// ************************
			//Get the path for the config folder (where the hibernate properties for the import layer are).
			String configPath = UpdateStudyController.class.getClassLoader().getResource("").getPath()+ "biiconfig/";
		
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
			
			// Add the Public release date field with the new value...TODO: What if the date comes empty, shall we remove the line
			replacementHash.put("Study Public Release Date", new SimpleDateFormat("yyyy-MM-dd").format(params.publicReleaseDate));

			logger.info("Replacing Study Public Release Date in zip file. with " + params.publicReleaseDate);
			// Call the replacement method...
			itu.changeStudyFields(study, replacementHash);
			
			// If the new status is public...
			if (params.status == VisibilityStatus.PUBLIC){
		
				// Move the file from the private
				itu.moveFile(study, VisibilityStatus.PRIVATE);  //Need to pass the old status, not the new public status
				
				// Change the permissions
				String studyPath = itu.getStudyFilePath(study, VisibilityStatus.PUBLIC);
				itu.changeFilePermissions(studyPath, VisibilityStatus.PUBLIC);
			}
									
			
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
	 * @param publicExp
	 * @param publicReleaseDateS
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = { "/updatestudy" })
	public ModelAndView updateStudy(
			@RequestParam("file") MultipartFile file, 
			@RequestParam(required=true,value="study") String study,
			@RequestParam(required=false,value="public") Boolean publicExp,
			@RequestParam(required=false, value="pickdate") String publicReleaseDateS,
			HttpServletRequest request) throws Exception{
		
		
		logger.info("Starting Updating study " + study);
		
		// Instantiate the param objects
		RequestParameters params = new RequestParameters(publicReleaseDateS, publicExp, study, file);
		
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
			
		}catch (Exception e){
			
			// If there is a need of restoring the backup
			if (needRestore){
			 
				// Restore process...
				// Calculate the previous status
				VisibilityStatus oldStatus = params.study.getIsPublic()?VisibilityStatus.PUBLIC: VisibilityStatus.PRIVATE;

				// Get the uploader configured
				IsaTabUploader itu = submissionController.getIsaTabUploader(backup.getAbsolutePath(), oldStatus, null);
				
				// Upload the old study
				itu.Upload();
				
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
		Boolean publicExp;
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
		 * @param publicExp
		 * @param studyId
		 * @throws Exception
		 */
		public RequestParameters(String publicReleaseDateS, Boolean publicExp, String studyId) throws Exception{
			
			// "normalize" the date to IsaTabFormat..
			if (publicReleaseDateS == null){
				this.publicReleaseDateS = "";
			}else{
				this.publicReleaseDateS = publicReleaseDateS;
			}
			 
			this.publicExp = publicExp;
			this.studyId = studyId;
			this.study = getStudy(studyId);
			this.isUpdateStudyMode = false;	
			
			// Get the user
			this.user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

			
		}
		
		
		public RequestParameters(String publicReleaseDateS, Boolean publicExp, String study, MultipartFile file) throws Exception{
			
			this(publicReleaseDateS, publicExp, study);
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
			if ( publicReleaseDateS.isEmpty() && (publicExp == null)){
				
				// a date is required, not any more
				//validationmsg = PropertyLookup.getMessage("msg.makestudypublic.daterequired");
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
						
		public void calculateStatusAndDate() throws ParseException{
			
			// If there is not a publicExp param
			if (publicExp == null) {
				
				// Public release date can be null, in this case it will never be public
				if (!publicReleaseDateS.isEmpty()){
					publicReleaseDate =  new SimpleDateFormat("dd-MMM-yyyy").parse(publicReleaseDateS);
				}
				
				status = VisibilityStatus.PRIVATE;
				publicExp = false;
			}else{
				// publicExp must be true
				publicReleaseDate= DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
				status = VisibilityStatus.PUBLIC;
			}
			
			
			
		}
		
		
		
	}
}