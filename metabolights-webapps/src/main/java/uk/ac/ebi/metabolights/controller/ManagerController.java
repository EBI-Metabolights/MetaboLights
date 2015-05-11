/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 5/21/14 12:36 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.controller;


import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.model.MetaboLightsParameters;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.model.queue.SubmissionItem;
import uk.ac.ebi.metabolights.model.queue.SubmissionQueue;
import uk.ac.ebi.metabolights.search.LuceneSearchResult;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.MetaboLightsParametersService;
import uk.ac.ebi.metabolights.service.SearchService;
import uk.ac.ebi.metabolights.service.UserService;
import uk.ac.ebi.metabolights.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.webapp.StudyHealth;

import javax.naming.Binding;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.io.*;
import java.text.ParseException;
import java.util.*;


/**
 * Controller management stuff 
 * @author Pablo Conesa
 *
 */
@Controller
public class ManagerController extends AbstractController{

    public static final String INSTANCES_SEP = ",";
    /**
	 * Show config properties and check if some of them are consistent
	 * @return
	 */
	@Autowired
	private UserService userService;

	@Autowired
	private SearchService searchService;

    @Autowired
    private MetaboLightsParametersService parametersService;

    @Autowired
	private HomePageController homePageController;

	private static Logger logger = LoggerFactory.getLogger(ManagerController.class);

	private @Value("#{isatabuploaderconfig}") String isatabUploaderConfig;
	
	@RequestMapping({"/config"})
	public ModelAndView config() {
		

		Map<String,String> properties=null;
		
		properties = PropertiesUtil.getProperties();


		// Get the hibernate properties...
		String hibernatePropertiesPath = isatabUploaderConfig + "hibernate.properties";
		InputStream inputStream = null;
		Properties hibernateProperties = new Properties();
		try {
			inputStream = new FileInputStream(hibernatePropertiesPath);

			hibernateProperties.load(inputStream);

			inputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		NamingEnumeration<Binding> contextProps= null;

		// Context bindings
		try {
			contextProps = PropertiesUtil.getEnvCtx().listBindings("");
		} catch (NamingException e) {
			e.printStackTrace();
		}


		DataSource metabolightsDs = null;

		// Context bindings
		try {

			NamingEnumeration<Binding> jdbcBindings= null;

			jdbcBindings = PropertiesUtil.getEnvCtx().listBindings("jdbc");

			while (jdbcBindings.hasMore()){
				Binding item =jdbcBindings.next();

				if (item.getName().equals("metabolights")){

					metabolightsDs = (DataSource) item.getObject();

					break;
				}
			}

		} catch (NamingException e) {
			e.printStackTrace();
		}


		// Validation result
		Map<String,Boolean> validationResult = new HashMap<String,Boolean>();

		// database connection consistency
		validationResult.put("Database connection consistency", (hibernateProperties.getProperty("hibernate.connection.url").equals(metabolightsDs.getPoolProperties().getUrl())));

		// Validate end character of path properties
		validationResult.put("uploadDirectory ends with /", (PropertiesUtil.getProperty("uploadDirectory").endsWith("/")));
		validationResult.put("studiesLocation ends with /", (PropertiesUtil.getProperty("studiesLocation").endsWith("/")));

		// Validate paths variable exists
		validationResult.put("uploadDirectory existence", (new File(PropertiesUtil.getProperty("uploadDirectory")).exists()));
		validationResult.put("studiesLocation existence", (new File(PropertiesUtil.getProperty("studiesLocation")).exists()));
		
		
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
		mav.addObject("contextProps", contextProps);
		mav.addObject("hibernateProperties", hibernateProperties);
		mav.addObject("connection", metabolightsDs);
		mav.addObject("validation", validationResult);
		mav.addObject("queue", queue);
		mav.addObject("processFolder", (getFilesInFolder(new File(SubmissionQueue.getProcessFolder()))));
		mav.addObject("errorFolder", (getFilesInFolder(new File(SubmissionQueue.getErrorFolder()))));
		mav.addObject("backUpFolder", (getFilesInFolder(new File(SubmissionQueue.getBackUpFolder()))));
//		mav.addObject("queuerunnig", SubmissionQueueManager.getIsRunning());
		mav.addObject("studiesHealth", getStudiesHealth());

        // Return ftp locations
        mav.addObject("studiesLocation", (getFilesInFolder(new File(PropertiesUtil.getProperty("studiesLocation")))));
        mav.addObject("galleryIds", homePageController.getGalleryItemsIds());


        MetaboLightsParameters instances = parametersService.getMetaboLightsParametersOnName("instances");

        if (instances.getParameterValue() != null) {
            mav.addObject("instances", instances.getParameterValue().split(INSTANCES_SEP));
        }


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
	
	@RequestMapping(value = "/togglequeue", method = RequestMethod.GET)
	public @ResponseBody String switchqueue() {


		try{

//			if (SubmissionQueueManager.getIsRunning()){
//
//				SubmissionQueueManager.stop();
//
//
//			}else{
//				SubmissionQueueManager.start();
//
//			}

		}catch (Exception e){
			// Do nothing, better return error message.
		}

	    return getQueueStatus();
	}

    @RequestMapping(value = "/queuestatus", method = RequestMethod.GET)
    public @ResponseBody String getQueueStatus() {

        String result = "";

//        return SubmissionQueueManager.getIsRunning()?"ON":"OFF";
		return "NOT IMPLEMENTED";
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

