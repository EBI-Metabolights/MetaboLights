package uk.ac.ebi.metabolights.controller;


import org.apache.tiles.context.MapEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
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
import uk.ac.ebi.metabolights.service.UserService;
import uk.ac.ebi.metabolights.utils.PropertiesUtil;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


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
		
		
		ModelAndView mav = new ModelAndView("config");
		mav.addObject("props", properties);
		mav.addObject("validation", validationResult);
		mav.addObject("queue", queue);
		mav.addObject("queuerunnig", SubmissionQueueManager.getIsRunning());
		return mav;

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
		ModelAndView mav = new ModelAndView("users");
		
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

