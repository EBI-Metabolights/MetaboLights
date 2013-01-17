package uk.ac.ebi.metabolights.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.model.queue.SubmissionItem;
import uk.ac.ebi.metabolights.model.queue.SubmissionQueue;
import uk.ac.ebi.metabolights.model.queue.SubmissionQueueManager;
import uk.ac.ebi.metabolights.search.LuceneSearchResult;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.SearchService;
import uk.ac.ebi.metabolights.service.UserService;
import uk.ac.ebi.metabolights.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.webapp.StudyHealth;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Controller management stuff 
 * @author Pablo Conesa
 *
 */
@Controller
public class ManagerController extends AbstractController{
	
	/**
	 * Show config properties and check if some of them are consistent
	 * @return
	 */
	@Autowired
	private UserService userService;

	@Autowired
	private SearchService searchService;

    @Autowired
	private HomePageController homePageController;

	private static Logger logger = Logger.getLogger(ManagerController.class);
	
	@RequestMapping({"/config"})
	public ModelAndView config() {
		

		Map<String,String> properties=null;
		
		properties = PropertiesUtil.getProperties();
			
		
		// Validation result
		Map<String,Boolean> validationResult = new HashMap<String,Boolean>();
		
		// Validate lucene index
		validationResult.put("Lucene index consistency", (PropertiesUtil.getProperty("hibernate.search.default.indexBase").equals(PropertiesUtil.getProperty("luceneIndexDirectoryShort"))));
		
		// Validate end character of path properties
		validationResult.put("uploadDirectory ends with /", (PropertiesUtil.getProperty("uploadDirectory").endsWith("/")));
		validationResult.put("publicFtpLocation ends with /", (PropertiesUtil.getProperty("publicFtpLocation").endsWith("/")));
		validationResult.put("privateFtpLocation ends with /", (PropertiesUtil.getProperty("privateFtpLocation").endsWith("/")));
		validationResult.put("publicFtpStageLocation ends with /", (PropertiesUtil.getProperty("publicFtpStageLocation").endsWith("/")));
		validationResult.put("privateFtpStageLocation ends with /", (PropertiesUtil.getProperty("privateFtpStageLocation").endsWith("/")));
		validationResult.put("luceneIndexDirectory ends with /", (PropertiesUtil.getProperty("luceneIndexDirectory").endsWith("/")));
		validationResult.put("luceneIndexDirectoryShort ends with /", (PropertiesUtil.getProperty("luceneIndexDirectoryShort").endsWith("/")));
		
		// Validate paths variable exists
		validationResult.put("uploadDirectory existance", (new File(PropertiesUtil.getProperty("uploadDirectory")).exists()));
		validationResult.put("publicFtpLocation existance", (new File(PropertiesUtil.getProperty("publicFtpLocation")).exists()));
		validationResult.put("privateFtpLocation existance", (new File(PropertiesUtil.getProperty("privateFtpLocation")).exists()));
		validationResult.put("publicFtpStageLocation existance", (new File(PropertiesUtil.getProperty("publicFtpStageLocation")).exists()));
		validationResult.put("privateFtpStageLocation existance", (new File(PropertiesUtil.getProperty("privateFtpStageLocation")).exists()));
		validationResult.put("luceneIndexDirectory existance", (new File(PropertiesUtil.getProperty("luceneIndexDirectory")).exists()));
		validationResult.put("luceneIndexDirectoryShort existance", (new File(PropertiesUtil.getProperty("luceneIndexDirectoryShort")).exists()));
		
		
		List<SubmissionItem> queue= null;
		
		// Get the files in the queue
		try {
			queue = SubmissionQueue.getQueue();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("config");
		mav.addObject("props", properties);
		mav.addObject("validation", validationResult);
		mav.addObject("queue", queue);
		mav.addObject("processFolder", (getFilesInFolder(new File(SubmissionQueue.getProcessFolder()))));
		mav.addObject("errorFolder", (getFilesInFolder(new File(SubmissionQueue.getErrorFolder()))));
		mav.addObject("backUpFolder", (getFilesInFolder(new File(SubmissionQueue.getBackUpFolder()))));
		mav.addObject("queuerunnig", SubmissionQueueManager.getIsRunning());
		mav.addObject("studiesHealth", getStudiesHealth());
		
		mav.addObject("galleryIds", homePageController.getGalleryItemsIds());
		return mav;
		
		
    }
	private File[] getFilesInFolder(File folder){
		if (!folder.exists()) return new File[]{};
		
		return folder.listFiles();
	}
	private List<StudyHealth> getStudiesHealth(){
		
		List <StudyHealth> studies = new ArrayList<StudyHealth>();
		List<LuceneSearchResult> totalResultList = new ArrayList<LuceneSearchResult>();
		
		HashMap<Integer,List<LuceneSearchResult>> studiesResult;
		try {
			studiesResult = searchService.search("*");
		} catch (Exception e) {
			logger.info("Can't search for all the studies in lucene index." + e.getMessage());
			return null;
		}
		
		// Get the total result list
		totalResultList = studiesResult.entrySet().iterator().next().getValue();
		
		// Create the List of StudyHealths
		for (LuceneSearchResult lsr:totalResultList){
			StudyHealth newSH = new StudyHealth(lsr);
			studies.add(newSH);
		}
		
		return studies;
	}
	
	@RequestMapping(value = "/switchqueue", method = RequestMethod.GET)
	public @ResponseBody String switchqueue(@RequestParam String command) {
		
		String result = "";
		
		try{
			
			if (command.equals("on")){
			
				SubmissionQueueManager.start();
			
				result = "Queue started";
			}else{
				SubmissionQueueManager.stop();
				result = "Queue stopped";
			}
			
		}catch (Exception e){
			result = e.getMessage();
		}
		
	    return result;
	}
	
	@RequestMapping({"/users"})
	public ModelAndView users(){
		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("users");
		
		List<MetabolightsUser> users = userService.getAll();
		
		mav.addObject("users",users);
		mav.addObject("usersMap", getUsersMap(users));
		return mav;
	}

    private Map<String,Integer> getUsersMap(List<MetabolightsUser> users){
    	Map<String,Integer> map = new HashMap<String,Integer>();
    	
    	for (MetabolightsUser user: users){
    		// If we do NOT have already the country
    		if(!map.containsKey(user.getAddress())){
    			// ... add the country.
    			map.put(user.getAddress(), 1);
    		}else{
    			Integer value = map.get(user.getAddress());
    			map.put( user.getAddress() , value+1);
    		}
    	}
    	
    	return map;
    }
}

