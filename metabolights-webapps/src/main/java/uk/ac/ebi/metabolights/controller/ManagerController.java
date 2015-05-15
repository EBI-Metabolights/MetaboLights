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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.model.MetaboLightsParameters;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.model.queue.SubmissionItem;
import uk.ac.ebi.metabolights.model.queue.SubmissionQueue;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.MetaboLightsParametersService;
import uk.ac.ebi.metabolights.service.SearchService;
import uk.ac.ebi.metabolights.service.UserService;
import uk.ac.ebi.metabolights.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.webapp.StudyHealth;
import uk.ac.ebi.metabolights.webservice.client.MetabolightsWsClient;
import uk.ac.ebi.metabolights.webservice.client.models.StudySearchResult;

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
		
		List <StudyHealth> studiesHealth = new ArrayList<StudyHealth>();

		MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();

		// Careful this returns all...and now it's fine but once compounds are added it will not work.
		RestResponse<StudySearchResult> response = (RestResponse<StudySearchResult>) wsClient.search();


		StudySearchResult studies = response.getContent();
		for (LiteStudy liteStudy : studies.getResults()) {

			StudyHealth newSH = new StudyHealth(liteStudy);
			studiesHealth.add(newSH);
		}
		
		return studiesHealth;
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

	/**
	 * Will reindex the whole index, to use carefully.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reindex") //must accept parameters else reindex all studies looping through
	public ModelAndView reindexall(@RequestParam(required = false, value = "study") String studyIdentifier){


		MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();

		RestResponse<String> response;

		if(studyIdentifier != null){

			logger.info("Re-indexing studyIdentifier: " + studyIdentifier);

			response = wsClient.index(studyIdentifier);

		} else {

			logger.info("Re-indexing all studies");

			response = wsClient.reindex();

		}

		return printMessage(response.getMessage(), response.getContent());

	}

}

