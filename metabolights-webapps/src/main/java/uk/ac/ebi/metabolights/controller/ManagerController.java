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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import uk.ac.ebi.metabolights.repository.model.Entity;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.search.service.SearchQuery;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.MetaboLightsParametersService;
import uk.ac.ebi.metabolights.service.UserService;
import uk.ac.ebi.metabolights.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.webapp.StudyHealth;
import uk.ac.ebi.metabolights.webservice.client.MetabolightsWsClient;
import uk.ac.ebi.metabolights.webservice.client.models.ArrayListOfStrings;
import uk.ac.ebi.metabolights.webservice.client.models.MixedSearchResult;

import javax.naming.Binding;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;
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

    public static final String INSTANCES_SEP = ",";
    /**
	 * Show config properties and check if some of them are consistent
	 * @return
	 */
	@Autowired
	private UserService userService;

    @Autowired
    private MetaboLightsParametersService parametersService;

    @Autowired
	private HomePageController homePageController;

	private static Logger logger = LoggerFactory.getLogger(ManagerController.class);

	@RequestMapping({"/config"})
	public ModelAndView config() {


		MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();

		Map<String,String> properties=null;
		
		properties = PropertiesUtil.getProperties();


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
		try {
			mav.addObject("connection", metabolightsDs.getConnection().getMetaData().getURL());
		} catch (SQLException e) {
			mav.addObject("connection", e.getMessage());
		}
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

		mav.addObject("status", wsClient.getIndexStatus().getContent());

		return mav;
		
		
    }
	private File[] getFilesInFolder(File folder){
		if (!folder.exists()) return new File[]{};
		
		return folder.listFiles();
	}


	private List<StudyHealth> getStudiesHealth(){
		
		List <StudyHealth> studiesHealth = new ArrayList<StudyHealth>();

		MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();

		// Careful this returns all (actually not all only the first 10!)...and now it's fine but once compounds are added it will not work.
		SearchQuery query = new SearchQuery();
		query.setText("'_type:study");
		query.setPagination(null);
		RestResponse<? extends MixedSearchResult> response = wsClient.search(query);

		MixedSearchResult studies = response.getContent();
		for (Entity entity : studies.getResults()) {

			if (entity instanceof LiteStudy) {
				LiteStudy liteStudy = (LiteStudy) entity;

				StudyHealth newSH = new StudyHealth(liteStudy);
				studiesHealth.add(newSH);
			} else {

				logger.warn("We are getting something else than LiteStudy objects when requesting all the studies. Class: {}", entity.getClass().getCanonicalName());

			}
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
	 * Will reindex all studies, to use carefully.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reindexstudies") //must accept parameters else reindex all studies looping through
	public ModelAndView reindexstudies(@RequestParam(required = false, value = "study") String studyIdentifier){


		MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();

		RestResponse<ArrayListOfStrings> response;

		if(studyIdentifier != null){

			logger.info("Re-indexing studyIdentifier: " + studyIdentifier);

			response = wsClient.indexStudy(studyIdentifier);

		} else {

			logger.info("Re-indexing all studies");

			response = wsClient.indexAllStudies();

		}

		return printMessage(response.getMessage(), response.getContent());

	}

	/**
	 * Will delete the studies index, to use carefully.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteindexedstudies")
	public ModelAndView deleteindexedstudies(){


		MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();

		RestResponse<ArrayListOfStrings> response;



		logger.info("Deleting studies index");

		response = wsClient.deleteStudiesIndex();

		return printMessage(response.getMessage(), response.getContent());

	}

	/**
	 * Will reindex all compound, to use carefully.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reindexcompounds") //must accept parameters else reindex all studies looping through
	public ModelAndView reindexcompounds(@RequestParam(required = false, value = "compound") String compound){


		MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();

		RestResponse<ArrayListOfStrings> response;

		if(compound != null){

			logger.info("Re-indexing compound: " + compound);

			response = wsClient.indexCompound(compound);

		} else {

			logger.info("Re-indexing all compounds");

			response = wsClient.indexAllCompounds();

		}

		return printMessage(response.getMessage(), response.getContent());

	}

	/**
	 * Will delete the studies index, to use carefully.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteindexedcompounds")
	public ModelAndView deleteIndexedCompounds(){


		MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();

		RestResponse<ArrayListOfStrings> response;

		logger.info("Deleting compounds index");

		response = wsClient.deleteCompoundsIndex();

		return printMessage(response.getMessage(), response.getContent());

	}



}

