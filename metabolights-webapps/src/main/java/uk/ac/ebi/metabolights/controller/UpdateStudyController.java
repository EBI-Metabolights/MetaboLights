package uk.ac.ebi.metabolights.controller;

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
	@RequestMapping(value = { "/makestudypublic"})
	public ModelAndView makeStudyPublic(@RequestParam(required=true,value="study") String study, HttpServletRequest request) throws Exception{
		
		// Get the correspondent ModelAndView
		return getModelAndView(study, false);
		
		
		
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
		
		ModelAndView mav = new ModelAndView("updateStudy");
		
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
			action = "resubmit";
			
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
	
	@RequestMapping(value = { "/updatepublicreleasedate" })
	public ModelAndView changePublicReleaseDate(@RequestParam(required=true,value="study") String study, 
								@RequestParam(required=true, value="pickdate") String publicReleaseDateS,
								HttpServletRequest request) throws Exception {

		// Get the user
		MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		// Log start
		logger.info("Updating the public release date of the study " + study + " owned by " + user.getUserName());
		
		//Get the path for the config folder (where the hibernate properties for the import layer are).
		String configPath = UpdateStudyController.class.getClassLoader().getResource("").getPath()+ "biiconfig/";
	
		// Convert the date
		Date publicReleaseDate =  new SimpleDateFormat("yyyy-MM-dd").parse(publicReleaseDateS);
		
		// Add the user id to the unzip folder
		String unzipFolder = uploadDirectory + user.getUserId();
		
		//Create the view
		ModelAndView mav = new ModelAndView("updateStudy");
		
		// Change the status
		try {

			// ************************
			// Update the database first...
			// ************************
			// Get the study object
			Study biiStudy = studyService.getBiiStudy(study,false);
			
			// Set the new Public Release Date
			biiStudy.setReleaseDate(publicReleaseDate);
			
			// Get the difference
			long datediff = publicReleaseDate.getTime() - DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH).getTime() ; 
									
			// Set the status, it may turn into public...
			VisibilityStatus status = (datediff< 0) ?VisibilityStatus.PRIVATE:VisibilityStatus.PUBLIC;
			biiStudy.setStatus(status);
			
			logger.info("Updating study (database)");
			// Save it
			studyService.update(biiStudy);


			
			// ************************
			// Index it...
			// ************************
			
			// Create the uploader
			IsaTabUploader itu = new IsaTabUploader();
			
			// Set the config folder, and the ftp folders
			itu.setDBConfigPath(configPath);
			
			// reindex the study...
			itu.reindexStudies(study);


			
			// ************************
			// Change the zip file
			// ************************
			
			// Set properties for file copying...
			itu.setCopyToPrivateFolder(privateFtpLocation);
			itu.setCopyToPublicFolder(publicFtpLocation);
			itu.setUnzipFolder(unzipFolder);

			// Create the replacement Hash
			HashMap<String,String> replacementHash = new HashMap<String,String>();
			
			// Add the Public release date field with the new value...
			replacementHash.put("Study Public Release Date", new SimpleDateFormat("dd/MM/yyyy").format(publicReleaseDate));

			logger.info("Replacing Study Public Release Date in zip file. with " + publicReleaseDate);
			// Call the replacement method...
			itu.changeStudyFields(study, replacementHash);
			
			
			// Compose the messages...
			mav.addObject("title", PropertyLookup.getMessage("msg.makestudypublic.ok.title"));
			mav.addObject("message", PropertyLookup.getMessage("msg.makestudypublic.ok.msg"));
			mav.addObject("searchResult", getStudy(study));
			mav.addObject("updated", true);
		
		} catch (Exception e) {
			
			// Auto-generated catch block
			e.printStackTrace();
			
			// Add the error to the page
			mav.addObject("error", new Exception ("There's been a problem while making the study " + study + " public\n" + e.getMessage()));
			
		}
		
		//Return the ModelAndView
		return mav;
		
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


}