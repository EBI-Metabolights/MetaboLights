package uk.ac.ebi.metabolights.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
			String ftpLocation = EntryController.getDownloadLink(luceneStudy.getAccStudy(), luceneStudy.getIsPublic()? VisibilityStatus.PUBLIC: VisibilityStatus.PRIVATE );
			mav.addObject("ftpLocation", ftpLocation);
			
		}else{
			
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
			ModelAndView validation = getModelAndView(params.study, params.isUpdateStudyMode);
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

		// Get the user
		MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		// Log start
		logger.info("Updating the public release date of the study " + study + " owned by " + user.getUserName());

		// Instantiate the param objects
		RequestParameters params = new RequestParameters(publicReleaseDateS, publicExp, study);
		
		// Validate the parameters...
		ModelAndView validation = validateParameters(params);
		
		// If there is validation view...return it
		if (validation != null){return validation;}

		
		// Add the user id to the unzip folder
		String unzipFolder = uploadDirectory + user.getUserId();
		
		// Create the uploader
		IsaTabUploader itu = new IsaTabUploader();
		
		// Set properties for file copying...
		itu.setCopyToPrivateFolder(privateFtpLocation);
		itu.setCopyToPublicFolder(publicFtpLocation);

		//Create the view
		ModelAndView mav = new ModelAndView("updateStudy");
		
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
			
			// Add the Public release date field with the new value...
			replacementHash.put("Study Public Release Date", new SimpleDateFormat("dd/MM/yyyy").format(params.publicReleaseDate));

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
	
	@RequestMapping(value = { "/updatestudy" })
	public ModelAndView updateStudy(
			@RequestParam("file") MultipartFile file, 
			@RequestParam(required=true,value="study") String study,
			@RequestParam(required=false,value="public") Boolean publicExp,
			@RequestParam(required=false, value="pickdate") String publicReleaseDateS,
			HttpServletRequest request) throws Exception{
		
		
		// Instantiate the param objects
		RequestParameters params = new RequestParameters(publicReleaseDateS, publicExp, study, file);
		
		// Validate the parameters...
		ModelAndView validation = validateParameters(params);
		
		// If there is validation view...return it
		if (validation != null){return validation;}
		
		try {

			//if (file.isEmpty()){ throw new Exception(PropertyLookup.getMessage("BIISubmit.fileEmpty"));}
			
			// Write the file to the proper location
			//String isaTabFile = BIISubmissionController.writeFile(file, null);
			
			
			
		}catch (Exception e){
			
			
		}
		
		
		return null;
		
	}
	
	/**
	 * Gets the study that has just been published.
	 * @param study
	 * @return
	 * @throws Exception 
	 */
	private LuceneSearchResult getStudy(String study) throws Exception{
		
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
		String study;
		String validationmsg;
		Boolean isUpdateStudyMode;
		
		
		public RequestParameters(String publicReleaseDateS, Boolean publicExp, String study){
			
			// "normalize" the date
			this.publicReleaseDateS = publicReleaseDateS == null? "": publicReleaseDateS;
			this.publicExp = publicExp;
			this.study = study;
			this.isUpdateStudyMode = false;		
		}
		
		
		public RequestParameters(String publicReleaseDateS, Boolean publicExp, String study, MultipartFile file){
			
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
				
				// a date is required
				validationmsg = PropertyLookup.getMessage("msg.makestudypublic.daterequired") + ". ";
			}
			
			if (isUpdateStudyMode && file == null){
				// file is required
				validationmsg = validationmsg +  PropertyLookup.getMessage("msg.upload.notValid"); 
			}
			
			return validationmsg ;

		}
						
		public void calculateStatusAndDate() throws ParseException{
			
			// If there is not a publicExp param
			if (publicExp == null) {
				publicReleaseDate =  new SimpleDateFormat("dd-MM-yyyy").parse(publicReleaseDateS);
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