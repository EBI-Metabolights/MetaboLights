/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 5/19/14 2:50 PM
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

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.model.queue.SubmissionItem;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.EmailService;
import uk.ac.ebi.metabolights.service.SearchService;
import uk.ac.ebi.metabolights.service.StudyService;
import uk.ac.ebi.metabolights.webservice.client.MetabolightsWsClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Make a study public. THis implies to change the status in the database, reindex, and move the zip file to the public ftp.
 * 
 * @author conesa
 */
@Controller
public class UpdateStudyController extends AbstractController {

	private static Logger logger = LoggerFactory.getLogger(UpdateStudyController.class);

	//Ftp locations
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
	public ModelAndView updatePublicReleaseDate(@RequestParam(required=true,value="study") String study,
												@RequestParam(required=false,value="date") String defaultDate,
												HttpServletRequest request) throws Exception{

		//Check access
		if (!EntryController.canUserEditStudy(study)) return getResctrictedAccessPage();

		// Get the correspondent ModelAndView
		return getModelAndView(study, defaultDate, false, false);


	}

//    @RequestMapping(value = { "/makestudyprivateform"})
//    public ModelAndView makeStudyPrivate(
//            @RequestParam(required=true,value="study") String study,
//            @RequestParam(required=false,value="date") String defaultDate,
//            HttpServletRequest request)
//            throws Exception{
//
//
//		//Check access
//		if (!EntryController.canUserEditStudy(study)) return getResctrictedAccessPage();
//
//
//        // Get the correspondent ModelAndView
//        return getModelAndView(study, defaultDate, false, true);
//
//    }

	/**
	 * Receives the study that is going to be updated and shows the updateStudy Page to let the user to set the public release date and upload the new file.
	 * 
	 * @param study
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/updatestudyform"})
	public ModelAndView updateStudyForm(@RequestParam(required=true,value="study") String study,
									@RequestParam(required=false,value="date") String defaultDate,
									HttpServletRequest request) throws Exception {

		//Check access
		if (!EntryController.canUserEditStudy(study)) return getResctrictedAccessPage();

		// Get the correspondent ModelAndView
		return getModelAndView(study, defaultDate, true, false);

	}

	/**
     * Send an email (each day) for 7 days before the study goes live.  Let's hope this is not too much for the submitter!
     */
    @RequestMapping(value = { "/findstudiesgoinglive"})
    public ModelAndView findStudiesGoingLive(){

        List<String> studiesList = studyService.findStudiesGoingLive();
        Iterator iter = studiesList.iterator();
        while (iter.hasNext()){
            String acc = (String) iter.next();
            try {
                LiteStudy study = getStudy(acc);
                emailService.sendStudyGoingPublicNotification(study);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ModelAndView ("index");

    }

	/**
	 * Return the model and view ready to be rendered in the jsp that share 2 modes. Update and MakeStudyPublic
	 * @param study
	 * @param isUpdateMode
	 * @return
	 * @throws Exception 
	 */
	private ModelAndView getModelAndView(String study, String defaultDate, boolean isUpdateMode, boolean isPublic) throws Exception{


		//Get the study data
		LiteStudy liteStudy = getStudy(study);
		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("updateStudyForm");
		
		// Add objects to the model and view
		mav.addObject("liteStudy", liteStudy);
		mav.addObject("isUpdateMode", isUpdateMode);
		mav.addObject("study", study);

		// If there is a default date...
		if (defaultDate != null) mav.addObject("defaultDate", new SimpleDateFormat("dd-MMM-yyyy").parse(defaultDate));
		
		String title ="", msg ="", action="", submitText="";
		
		String studyShortTitle = liteStudy.getTitle();
		if (studyShortTitle.length() > 50) studyShortTitle = (studyShortTitle.substring(0, 47) + "...");
		
		// Fill the output title, msg, ...depending on the mode
		if (isUpdateMode){
			
			title = PropertyLookup.getMessage("msg.updatestudy.title", study,  studyShortTitle);
			msg = PropertyLookup.getMessage("msg.updatestudy.msg");
			submitText = PropertyLookup.getMessage("label.updatestudy");
			
			// Redirect to the queue system: action = "updatestudy";
			action ="queueExperiment";
			
			
			// Get the DownloadLink
			String ftpLocation = FileDispatcherController.getDownloadLink(liteStudy.getStudyIdentifier(), liteStudy.isPublicStudy() ? VisibilityStatus.PUBLIC : VisibilityStatus.PRIVATE);
			mav.addObject("ftpLocation", ftpLocation);
			
		} else if(isPublic){
            title = PropertyLookup.getMessage("msg.makeStudyPrivatestudy.title", study,  studyShortTitle);
            submitText = PropertyLookup.getMessage("label.publictoprivate");

            action = "updatepublicreleasedate";

            logger.info("Method for making studies private, action: "+action);

            // Link to download the study
            String ftpLocation = FileDispatcherController.getDownloadLink(liteStudy.getStudyIdentifier(), liteStudy.isPublicStudy() ? VisibilityStatus.PUBLIC : VisibilityStatus.PRIVATE);
            mav.addObject("ftpLocation", ftpLocation);
        } else {
			
			title = PropertyLookup.getMessage("msg.makestudypublic.title", study, studyShortTitle);
			msg = PropertyLookup.getMessage("msg.makestudypublic.msg");
			submitText = PropertyLookup.getMessage("label.updateReleaseDate");
			action = "updatepublicreleasedate";
			
		}
		
		mav.addObject("title", title);
		mav.addObject("message", msg);
		mav.addObject("action", action);
		mav.addObject("submitText", submitText);
        mav.addObject("studyCheck", getStudy(study));
		return mav;
	}
	
	
	private ModelAndView validateParameters(RequestParameters params) throws Exception{
		

		// Validate the parameters
		params.validate();

		// If there is validation message...
		if (!params.validationmsg.isEmpty()){
			
			// Prepare the same view but this time with the validation message.
			ModelAndView validation = getModelAndView(params.studyId, params.publicReleaseDateS, params.isUpdateStudyMode, false);
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
								@RequestParam(required=true, value="pickdate") String publicReleaseDateS) throws Exception {


		//Check access
		if (!EntryController.canUserEditStudy(study)) return getResctrictedAccessPage();

		// Instantiate the param objects
		RequestParameters params = new RequestParameters(publicReleaseDateS, study);

		// Log start
		logger.info("Updating the public release date of the study " + study + " owned by " + params.user.getUserName());
		
		// Validate the parameters...
		ModelAndView mav = validateParameters(params);
		
		// If there is validation view...return it
		if (mav != null){return mav;}

        try{

            // Use de submitter user name in the study instead of the User (could be a curator).
//        	mav = queuePublicReleaseDate(request,study, params.publicReleaseDate, params.study.getSubmitter().getUserName());

			MetabolightsWsClient wsClient =EntryController.getMetabolightsWsClient();

			RestResponse<String> response = wsClient.updatePublicReleaseDate(params.publicReleaseDate, study);

			if (response.getErr() == null)
				return this.printMessage("Study public release updated", study + " public release date has been updated successfully to " + publicReleaseDateS);

			else {

				throw new Exception(response.getErr().getMessage());
			}

		} catch (Exception e) {
			
			String message = "There's been a problem while changing the Public Release date of the study " + study + "\n" + e.getMessage();
			
			// Auto-generated catch block
			logger.error(message);
			
			// Add the error to the page
			throw new Exception (message);
			
		}
		

	}

	@RequestMapping(value = { "/updatestatus" })
	public ModelAndView updateStatus(
			@RequestParam(required=true,value="study") String study,
			@RequestParam(required=true, value="newStatus") LiteStudy.StudyStatus newStatus) throws Exception {


		//Check access
		if (!EntryController.canUserEditStudy(study)) return getResctrictedAccessPage();

		MetabolightsUser user = LoginController.getLoggedUser();

		// Log start
		logger.info("Updating status of the study " + study + " owned by " + user.getUserName());

		try{

			MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();

			RestResponse<String> response = wsClient.updateStatus(newStatus, study);

			if (response.getErr() == null)
				//return this.printMessage("Study status updated", response.getMessage());
				return this.redirect(study);

			else {

				throw new Exception(response.getErr().getMessage());
			}

		} catch (Exception e) {

			String message = "There's been a problem while changing the status of the study " + study + " to "  + newStatus + "\n" + e.getMessage();

			// Auto-generated catch block
			logger.error(message);

			// Add the error to the page
			throw new Exception (message);

		}


	}



    @RequestMapping(value = { "/deleteStudy" })
 	public ModelAndView deleteStudy(
 								@RequestParam(required=true,value="study") String studyId,
 								HttpServletRequest request) throws Exception {

//    	MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//    	LiteStudy study = getStudy(studyId);
//
// 		// Log start
// 		logger.info("Deletion request of " + study + " by " + user.getUserName());
//
// 		// Validate the parameters...
// 		String validationMsg = "";
//
// 		// Check the user is the owner
// 		if (!user.getUserName().equals(study.getSubmitter().getUserName()))
// 		{
// 			// User must match
// 			validationMsg =  PropertyLookup.getMessage("msg.deleteStudy.userValidation", user.getUserName(), studyId,study.getSubmitter().getUserName());
// 		}
//
// 		// Check if it's public
// 		if (study.getIsPublic())
// 		{
// 			validationMsg = validationMsg + PropertyLookup.getMessage("msg.deleteStudy.statusValidation",studyId);
// 		}
//
// 		// If there is validation view...return it. Curators still delete a study regardless
// 		if (!user.isCurator() && validationMsg != ""){
//            return printMessage(PropertyLookup.getMessage("msg.deleteStudy.titleValidation"), validationMsg);
//        }
//
//
// 		ModelAndView mav;
//
//        try{
//
//             //Create the view
//             mav = queueDeleteStudy(request, studyId, user);
//
// 		} catch (Exception e) {
//
// 			String message = "There's been a problem while deleting the study " + studyId + "\n" + e.getMessage();
//
// 			// Auto-generated catch block
// 			logger.error(message);
//
// 			// Add the error to the page
// 			throw new Exception (message);
//
// 		}
//
// 		//Return the ModelAndView
// 		return mav;

		return printMessage("Not implemented", "Not implemented! with the new architecture!");
//
 	}

//	NOt used commented on 29-04-2015
//    /**
//     * This method will create a file in the queue folder that represent the task of updating the public release date of a single study.
//     * @param request
//     * @param accession
//     * @param publicReleaseDate
//     * @param user
//     * @return
//     * @throws IOException
//     * @throws IllegalStateException
//     */
//    private ModelAndView queuePublicReleaseDate(HttpServletRequest request, String accession, Date publicReleaseDate, String user) throws IllegalStateException, IOException{
//
//    	String hostName = java.net.InetAddress.getLocalHost().getHostName();
//
//    	SubmissionItem si = new SubmissionItem(null,user,publicReleaseDate,accession, false);
//    	si.submitToQueue();
//
//		// Cannot load the queue
//		emailService.sendQueuedPRLUpdate(si.getUserId(), si.getPublicReleaseDate(), hostName, accession);
//
//        logger.info("Queued study for Public Release Date update: " + accession);
//		HttpSession httpSession = request.getSession();
//		httpSession.setAttribute("itemQueued", "msg.PRDUpdateQueued");
//
//    	return new ModelAndView("redirect:itemQueued");
//
//
//    }
    /**
     * This method will create a file in the queue folder that represent the task for deleting a single study.
     * @param request
     * @param accession
     * @param user
     * @return
     * @throws IOException
     * @throws IllegalStateException 
     */
    private ModelAndView queueDeleteStudy(HttpServletRequest request, String accession, MetabolightsUser user) throws IllegalStateException, IOException{
    	 
    	String hostName = java.net.InetAddress.getLocalHost().getHostName();
    	
    	SubmissionItem si = new SubmissionItem(null,user.getUserName(),null,accession, false);
		si.submitToQueue();

		// Cannot load the queue
		emailService.sendQueuedDeletion(si.getUserId(),  hostName, accession);
		
        logger.info("Queued delete study: " + accession);
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("itemQueued", "msg.deleteStudyQueued");
    	return new ModelAndView("redirect:itemQueued");

    	 
    }
	
	/**
	 * Gets the study that has just been published.
	 * @param study
	 * @return
	 * @throws Exception 
	 */
	public LiteStudy getStudy(String study) throws Exception{
		

		MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();

		return wsClient.searchStudy(study);

	
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
		LiteStudy study;
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
				this.publicReleaseDate=  new SimpleDateFormat("dd-MMM-yyyy").parse(publicReleaseDateS);
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

            // If the user is not a curator
            if (!user.isCurator()){
                // Double check the user owns the study or the user is a curator
                if (!doesUserOwnsStudy()){
                    // ... user do not own the study
                    validationmsg = validationmsg +  PropertyLookup.getMessage("msg.validation.studynotowned");
                }
            }
			// Do not continue if the study is public.
			//if (study.getIsPublic()){
				// ... a public study cannot be modified
				//validationmsg = validationmsg +  PropertyLookup.getMessage("msg.validation.publicstudynoteditable");
			//}
			
			return validationmsg ;

		}

		private boolean doesUserOwnsStudy(){

			for (User owner : study.getUsers()) {
				if (owner.getUserName().equals(user.getUserName())) {
					return true;
				}
			}

			return false;
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