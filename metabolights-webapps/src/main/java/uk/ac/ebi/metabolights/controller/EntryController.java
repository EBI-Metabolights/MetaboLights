/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Dec-18
 * Modified by:   kenneth
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 8/14/14 10:24 AM
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.bioinvindex.model.AssayGroup;
import uk.ac.ebi.bioinvindex.model.AssayResult;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.processing.Assay;
import uk.ac.ebi.bioinvindex.model.term.FactorValue;
import uk.ac.ebi.bioinvindex.model.term.PropertyValue;
import uk.ac.ebi.metabolights.model.MLAssay;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.search.LuceneSearchResult;
import uk.ac.ebi.metabolights.service.*;
import uk.ac.ebi.metabolights.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.webservice.client.MetabolightsWsClient;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;


/**
 * Controller for entry (=study) details.
 *
 */
@Controller
public class EntryController extends AbstractController {


	private static final String ALTERNATIVE_ENTRY_PREFIX = "";
	private static final String ENTRY_PREFIX = "alt";
	private static Logger logger = LoggerFactory.getLogger(EntryController.class);
	private static String wsUrl;
	private final String DESCRIPTION="descr";

	@Autowired
	private StudyService studyService;

    @Autowired
    private AccessionService accessionService;

	@Autowired
	private SearchService searchService;

	@Autowired
	private UserService userService;

    public static final String METABOLIGHTS_ID_REG_EXP = "(?:MTBLS|mtbls).+";
	public static final String REVIEWER_OBFUSCATION_CODE_URL = "reviewer{obfuscationCode}";
	private static MetabolightsWsClient metabolightsWsClient;

	public enum PageActions{
		READ,
		EDIT
	}

	/**
	 * To get the WS client...this is not the right place, should it be a service?
	 */
	public static MetabolightsWsClient getMetabolightsWsClient() {
		return  getMetabolightsWsClient( LoginController.getLoggedUser());
	}
	public static MetabolightsWsClient getMetabolightsWsClient(MetabolightsUser user) {

		//compose the ws url..
		String wsUrl = getWsPath();

		MetabolightsWsClient wsClient = new MetabolightsWsClient(wsUrl);

		// If the user is null use the Logged user
		if (user == null) user = LoginController.getLoggedUser();

		// Use user token ...
		wsClient.setUserToken(user.getApiToken());
		return wsClient;
	}

	private static String getWsPath() {

		if (wsUrl != null) return wsUrl;

		String host = PropertiesUtil.getHost();

		// Add the webservice part...
		wsUrl = host + "webservice/";

		return wsUrl;

	}




	@RequestMapping(value = "/" + ALTERNATIVE_ENTRY_PREFIX + REVIEWER_OBFUSCATION_CODE_URL + "/assay/{assayNumber}/maf")
	public ModelAndView getAltReviewersMetabolitesIdentified(
			@PathVariable("obfuscationCode") String obfuscationCode,
			@PathVariable("assayNumber") int assayNumber){


		// Get the username from lucene index based on obfuscation code
		LuceneSearchResult indexedStudy = searchService.getStudyByObfuscationCode(obfuscationCode);
		String userName = indexedStudy.getSubmitter().getUserName();

		// Get the token from the database user based on username
		MetabolightsUser studyOwner = userService.lookupByUserName(userName);


		return getMetabolitesModelAndView(indexedStudy.getAccStudy(),assayNumber,studyOwner);
	}

	@RequestMapping(value = "/" + ALTERNATIVE_ENTRY_PREFIX + "{metabolightsId:" + METABOLIGHTS_ID_REG_EXP +"}/assay/{assayNumber}/maf")
	public ModelAndView getAltMetabolitesIdentified(
			@PathVariable("metabolightsId") String mtblsId,
			@PathVariable("assayNumber") int assayNumber){


		return getMetabolitesModelAndView(mtblsId, assayNumber, null);
	}

	private ModelAndView getMetabolitesModelAndView(String mtblsId, int assayNumber, MetabolightsUser user) {
		MetabolightsWsClient wsClient = getMetabolightsWsClient(user);

		RestResponse<MetaboliteAssignment> response = wsClient.getMetabolites( mtblsId,assayNumber);
		MetaboliteAssignment metaboliteAssignment = response.getContent();

		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("metabolitesIdentified");

		mav.addObject("metaboliteAssignment", metaboliteAssignment);
		mav.addObject("assayNumber", assayNumber);

		return mav;
	}

	@RequestMapping(value = {"/" + ENTRY_PREFIX + REVIEWER_OBFUSCATION_CODE_URL})
    public ModelAndView showReviewerEntry(@PathVariable("obfuscationCode") String obfuscationCode, HttpServletRequest request) {
        Study study = null;

        try {
            study = getStudy(null, obfuscationCode, request);
        }  catch (IllegalAccessException e){
            notLoggedIn(obfuscationCode);
        }

        if (study ==null || study.getAcc() == null || study.getAcc().equals("Error"))
            return new ModelAndView ("redirect:index?message="+ PropertyLookup.getMessage("msg.noStudyFound") + ". Please contact the submitter directly for correct reviewer access code.");

        return getStudyMAV(study);

    }

	@RequestMapping(value = {"/" + ALTERNATIVE_ENTRY_PREFIX + REVIEWER_OBFUSCATION_CODE_URL})
	public ModelAndView showAltReviewerEntry(@PathVariable("obfuscationCode") String obfuscationCode) {


		return getWSEntryMAV(null, obfuscationCode);

	}

    /**
     * Get the study based on either the study if or obfuscation code
     * @param mtblsId
     * @param obfusationCode
     * @param request
     * @return
     */
    private Study getStudy(String mtblsId, String obfusationCode, HttpServletRequest request) throws IllegalAccessException {
        Study study = null;

        if (mtblsId != null && obfusationCode != null)
            return study;

        try {
            request.setCharacterEncoding("UTF-8");

            if (mtblsId != null) { //Get study on the MTBLS id
                mtblsId = mtblsId.toUpperCase(); //This method maps to both MTBLS and mtbls, so make sure all further references are to MTBLS
                study = studyService.getBiiStudy(mtblsId,true);
            } else if (obfusationCode != null){
                study = studyService.getBiiStudyOnObfuscation(obfusationCode, true);

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //TODO, change
        } catch (IllegalAccessException e){
           throw e;
        }

        return study;

    }

    private ModelAndView getStudyMAV(Study study){

        Collection<String> organismNames = getOrganisms(study);
        Collection<MLAssay> mlAssays = getMLAssays(study);

        // Get the DownloadLink
        //String fileLocation = FileDispatcherController.getDownloadLink(study.getAcc(), study.getStatus());

        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("entry");
        mav.addObject("study", study);
        mav.addObject("organismNames", organismNames);
        mav.addObject("factors", getFactorsSummary(study));
        mav.addObject("assays", mlAssays);
        mav.addObject("hasMetabolites", getHasMetabolites(mlAssays));
        mav.addObject("submittedID", accessionService.getSubmittedId(study.getAcc()));
		mav.addObject("studyXRefs", accessionService.getStudyXRefs(study.getAcc()));
        mav.addObject("pageTitle", study.getAcc() + ":" +study.getTitle() );
        mav.addObject("files", new FileDispatcherController().getStudyFileList(study.getAcc()));

        //Have to give the user the download stream as the study is not on the public ftp
        //if (!study.getAcc().equals(VisibilityStatus.SUBMITTED.toString()))
        //mav.addObject("fileLocation",fileLocation); //All zip files are now generated on the fly, so ftpLocation is really fileLocation

        return mav;

    }

	//(value = "/entry/{metabolightsId}")
	@RequestMapping(value = { "/" + ENTRY_PREFIX + "{metabolightsId:" + METABOLIGHTS_ID_REG_EXP +"}"})
	public ModelAndView showEntry(@PathVariable("metabolightsId") String mtblsId, HttpServletRequest request) {
		logger.info("requested entry " + mtblsId);

		Study study = null;

        mtblsId = mtblsId.toUpperCase(); //This method maps to both MTBLS and mtbls, so make sure all further references are to MTBLS

		String referrer = request.getHeader("referer");
		// If not comming from the wait page...
		if (referrer == null || !referrer.contains("pleasewait")){
			return new ModelAndView("redirect:pleasewait?goto=" + ENTRY_PREFIX + mtblsId);
		}

        try {
            study = getStudy(mtblsId, null, request);       //Get on MTBLS ID
        }  catch (IllegalAccessException e){
            return notLoggedIn(mtblsId);
        }

		// Is there a study with this name?  Was there an error?  Have you tried to access a SUBMITTED study?
		if (study ==null || study.getAcc() == null || study.getAcc().equals("Error"))
 			return new ModelAndView ("redirect:index?message="+ PropertyLookup.getMessage("msg.noStudyFound") + " (" + mtblsId + ")");

		return getStudyMAV(study);
	}


    /**
     * Display a different screen if the user is not logged in and trying to access a private study
     * @param mtblsId
     * @return
     */
    private ModelAndView notLoggedIn(String mtblsId){

		/// The current user is not allowed to access the study...
		// If there isn't a logged in user...
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getPrincipal().equals("anonymousUser")){
			// redirect force login...
			return new ModelAndView("redirect:securedredirect?url=" + mtblsId);

			// The user is logged in but it's not authorised.
		} else {
			return new ModelAndView ("redirect:/index?message="+ PropertyLookup.getMessage("msg.studyAccessRestricted") + " (" + mtblsId + ")");
		}

    }

	private boolean getHasMetabolites(Collection<MLAssay> mlAssays) {

		for (MLAssay assay: mlAssays)
		{
			if (assay.getMetabolitesGUI() != null && assay.getMetabolitesGUI().size()>0)
			{
				return true;
			}
		}

		return false;
	}

	@RequestMapping(value = { "/" + ALTERNATIVE_ENTRY_PREFIX + "{metabolightsId:" + METABOLIGHTS_ID_REG_EXP +"}"})
    public ModelAndView showWSEntry(@PathVariable("metabolightsId") String mtblsId, HttpServletRequest request) {

		return getWSEntryMAV(mtblsId, null);
    }

	private ModelAndView getWSEntryMAV(String mtblsId, String obfuscationCode) {

		// Get the user
		MetabolightsUser user = LoginController.getLoggedUser();

		MetabolightsWsClient wsClient = getMetabolightsWsClient(user);


		RestResponse<uk.ac.ebi.metabolights.repository.model.Study> response;

		if (obfuscationCode == null) {

			logger.info("requested entry " + mtblsId);

			mtblsId = mtblsId.toUpperCase(); //This method maps to both MTBLS and mtbls, so make sure all further references are to MTBLS

			response = wsClient.getStudy(mtblsId);
		} else {

			logger.info("requested entry by obfuscation " + obfuscationCode);

			response = wsClient.getStudybyObfuscationCode(obfuscationCode);
		}

		uk.ac.ebi.metabolights.repository.model.Study study = response.getContent();


		// In case of reviewer mode, the user will not be anonymous.
		// Change: ws is not returning the study anymore if private it returns null and an error message/object
		// For now I'm assuming if it's null == it's private. Bu we may want to check the error message instead.
		if (user.getUserName().equals(LoginController.ANONYMOUS_USER.toLowerCase()) && (study == null)) {
			return notLoggedIn(ALTERNATIVE_ENTRY_PREFIX + mtblsId);
		}

		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("entry2");

		mav.addObject("pageTitle", study.getStudyIdentifier() + ":" +study.getTitle() );

		mav.addObject("study", study);

		mav.addObject("studyStatuses", LiteStudy.StudyStatus.values());

		// Thing that don't come from the web service: xRefs...studydbId
		mav.addObject("files", new FileDispatcherController().getStudyFileList(study.getStudyIdentifier()));

		return  mav;
	}

	/**
	 * @param study
	 * @return
	 */
	private Collection<String> getOrganisms(Study study) {

		Collection<String> organismNames = new TreeSet<String>();

		for (AssayResult assRes : study.getAssayResults()) {
			for (PropertyValue<?> pv : assRes.getCascadedPropertyValues()) {
				if (pv.getType().getValue().equalsIgnoreCase("organism")) {
					organismNames.add(pv.getValue());
				}
			}
		}
		return organismNames;
	}

	private Collection<MLAssay> getMLAssays(Study study){

		HashMap<String,MLAssay> mlAssays = new HashMap<String, MLAssay>();

		for (AssayResult ar : study.getAssayResults()){

			MLAssay mlAssay= null;

			for (Assay assay: ar.getAssays()){

				String assayName = MLAssay.getAssayNameFromAssay(assay);

				// If we don't have the MLAssay
				if (!mlAssays.containsKey(assayName)){
					mlAssay = new MLAssay(assay);
					mlAssay.setStudy(study);
					mlAssays.put(assayName, mlAssay);
				} else {
					mlAssay = mlAssays.get(assayName);
				}

				mlAssay.addAssayLines(assay);
                //files = MLProcessingUtils.findAllDataInAssay(assay);
			}

			// Add the assay line to the assay
			mlAssay.addAssayResult(ar);


			if (ar.getAssays().size()!=1) logger.info("*****WARNING:Assays collection's size is different from 1, we expect to have a one-to-one relationship!!!: " + ar.getAssays().size());

		}

		// Assign AssayGroup and metabolites
		for (AssayGroup ag: study.getAssayGroups()){
			MLAssay mlAssay = mlAssays.get(ag.getFileName());

			if (mlAssay == null){
				logger.info("Oh! Not MLAssay found for AssayGroup: " + ag.getFileName());
			}else{
				// First set the study
				mlAssay.setAssayGroup(ag);
			}
		}

		return mlAssays.values();
	}

	private HashMap<String,ArrayList<String>> getFactorsSummary(Study study){

		HashMap<String, ArrayList<String>> fvSummary = new HashMap<String, ArrayList<String>>();

		// Loop through the assay results
		for (AssayResult ar:study.getAssayResults()){

			// For each factor value in Data item
			for (FactorValue fv:ar.getData().getFactorValues()){

				// ... if we don't have the factor already in the collection
				if (!fvSummary.containsKey(fv.getType().getValue())){
					fvSummary.put(fv.getType().getValue(),new ArrayList<String>());
				}

				// Get the values for that factor...
				ArrayList<String> values = fvSummary.get(fv.getType().getValue());


				String value;

				// Add the new value
				if (!(fv.getUnit() == null)){
					value = fv.getValue() + " " + fv.getUnit().getValue();
				} else {
					value = fv.getValue();
				}

				if (!values.contains(value)) values.add(value);
			}
		}
		return fvSummary;
	}

	public static boolean canUserViewStudy(String study) {
		return canUserDoThisToStudy(study,null,PageActions.READ);
	}

	public static boolean canUserEditStudy(String study) {
		return canUserDoThisToStudy(study,null,PageActions.EDIT);
	}


	/**
	 *
	 * @param studyId
	 * @param user
	 * @param action - Actions could be "EDIT" or "READ"
	 * @return
	 */
	public static boolean canUserDoThisToStudy(String studyId, MetabolightsUser user, PageActions action) {

		// Get the user if not passsed
		if (user == null) {

			 user = LoginController.getLoggedUser();
		}

		// Return true if curator: curators can do anything.
		if (user.isCurator()) {
			return true;
		} else {

			// Get the study
			LuceneSearchResult study = AppContext.getSearchService().getStudy(studyId);

			boolean userOwnstudy = study.getSubmitter().getUserName().equals(user.getUserName());

			// If accion READ
			if (action == PageActions.READ){

				// If public...
				if(study.getIsPublic()){
					// Allowed to any.
					return true;
				} else {

					// Allowed to owner only.
					return userOwnstudy;
				}
			// Write action or null or whatever (more restrictive), just in case.
			} else {

				return userOwnstudy;

			}
		}
	}

}
