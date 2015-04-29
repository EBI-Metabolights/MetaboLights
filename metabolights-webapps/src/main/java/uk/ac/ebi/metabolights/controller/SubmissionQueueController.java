/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 5/22/14 12:28 PM
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

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.model.queue.SubmissionItem;
import uk.ac.ebi.metabolights.model.queue.SubmissionQueue;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.EmailService;
import uk.ac.ebi.metabolights.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Controls multi part file upload as described in Reference Documentation 3.0,
 * chapter "15.8 Spring's multipart (fileupload) support".
 * 
 * @author conesa
 */
@Controller
public class SubmissionQueueController extends AbstractController {

	@Autowired
	private EmailService emailService;

    @Autowired
    private UserService userService;


    private static Logger logger = LoggerFactory.getLogger(SubmissionQueueController.class);

    private @Value("#{uploadDirectory}") String uploadDirectory;
    private @Value("#{userSpace}") String uploadSpectraDirectory;

    static class BIIException extends Exception {
        public BIIException(String message){
            super(message);
        }
    }


    @RequestMapping(value = { "/referencespectraupload" })
    public ModelAndView referenceSpectraUpload(HttpServletRequest request) {
        MetabolightsUser user = null;
        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("referencespectraupload"); // Call the Submission form page

        if (request.getUserPrincipal() != null)
            user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (user != null){
            //mav.addObject("user", user);
            try {
                mav.addObject("queueditems",SubmissionQueue.getQueuedForUserId(user.getUserName().toString()));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // If the user is a curator
            if (user.isCurator()){
                mav.addObject("users", userService.getAll());
            }
        }

        return mav;
    }
	
	@RequestMapping(value = { "/submittoqueue" })
	public ModelAndView preSubmit(HttpServletRequest request) {
		MetabolightsUser user = null;
		
		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("submittoqueue"); // Call the Submission form page
		if (request.getUserPrincipal() != null)
			user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		if (user != null){
			//mav.addObject("user", user);
			try {
				mav.addObject("queueditems",SubmissionQueue.getQueuedForUserId(user.getUserName().toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // If the user is a curator
            if (user.isCurator()){
                mav.addObject("users", userService.getAll());
            }
		}
		
		
		return mav;
	}

// Not used?: commented on 29-04-2015.
//    public ModelAndView generatePTPFile(
//            @RequestParam(required=false,value="study") String study,
//            @RequestParam(required=false,value="owner") String owner,
//            HttpServletRequest request){
//
//        StringBuffer messageBody = new StringBuffer();
//        String hostName = null;
//        Date publicDateD = null;
//
//        // Get the user
//        MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        String submitter = user.getUserName();
//
//        try {
//            hostName = java.net.InetAddress.getLocalHost().getHostName();
//            messageBody.append("Study submission started from machine " + hostName);
//
//            if (user.isCurator()){
//
//                // Overwrite the submitter with the owner...
//                submitter = owner;
//            }
//
//
//            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
//            publicDateD = sdf.parse(publicDate);
//
//            if (publicDate.isEmpty())
//                throw new BIIException(PropertyLookup.getMessage("BIISubmit.dateEmpty"));
//
//
//
//        // Extend the message...
//        messageBody.append("\nUser: " + user.getUserName() + "on behalf of " + submitter);
//        messageBody.append("\nSTUDY: " + study);
//        messageBody.append("\nPublic Release Date: " + publicDate);
//
//        SubmissionItem si = new SubmissionItem(null, submitter, publicDateD, study, true);
//        // Submit the item to the queue...
//        si.submitToQueue();
//
//        messageBody.append("\n\n File Successfully queued.");
//
//        logger.info("Queued study. Adding data to session");
//        HttpSession httpSession = request.getSession();
//        httpSession.setAttribute("itemQueued", "msg.studyQueueSuccesfully");
//
//        // Cannot load the queue
//        emailService.sendQueuedStudyEmail(si.getUserId(),si.getOriginalFileName() , FileUtils.byteCountToDisplaySize(si.getFileQueued().length()), si.getPublicReleaseDate(), hostName, study);
//
//        } catch (BIIException e) {
//            ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("submitError");
//            logger.error("Submision exception", e);
//            mav.addObject("error", e);
//            mav.addObject("studyId", study);
//            return mav;
//        } catch (Exception e) {
//
//            ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("submitError");
//            logger.error("Submission exception",e);
//            mav.addObject("error", e);
//
//            // Add the study id...
//            mav.addObject("studyId", study);
//            messageBody.append("\n\nERROR!!!!!\n\n" + e.getMessage() );
//            emailService.sendSimpleEmail( "msg.UpdatePTPFailed " + hostName + " by " + user.getUserName() , messageBody.toString());
//            return mav;
//        }
//
//        return new ModelAndView("redirect:itemQueued");
//    }
	
	@RequestMapping(value = "/queueExperiment", method = RequestMethod.POST)
	public ModelAndView queueExperiment(
			@RequestParam(required = true ,value = "file") MultipartFile file,
			@RequestParam(required=true,value="pickdate") String publicDate,
			@RequestParam(required=false,value="study") String study,
            @RequestParam(required=false,value="owner") String ownerId,
			@RequestParam(required=false,value="validated", defaultValue ="false") boolean validated,
			HttpServletRequest request) 
		throws Exception {

		//Start the submission process...
	    logger.info("Queue Experiment. Start");
	    
        StringBuffer messageBody = new StringBuffer();
        String hostName = java.net.InetAddress.getLocalHost().getHostName();
        messageBody.append("Study submission started from machine " + hostName);

        // Get the actual user (could be a curator).
        MetabolightsUser actualUser = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        // The submitter, by default is the same user
        MetabolightsUser submitter = actualUser;


        // If the user is a curator but there is an owner...
        if (ownerId != null && actualUser.isCurator()){

            // Overwrite the submitter with the owner...
            submitter = userService.lookupByEmail(ownerId);
        }

        try {

            if (file.isEmpty())
				throw new BIIException(PropertyLookup.getMessage("BIISubmit.fileEmpty"));

			if (publicDate.isEmpty())
				throw new BIIException(PropertyLookup.getMessage("BIISubmit.dateEmpty"));
			
			if (!file.getOriginalFilename().toLowerCase().endsWith("zip"))
				throw new BIIException(PropertyLookup.getMessage("BIISubmit.fileExtension"));

            if (!validated)
                throw new BIIException(PropertyLookup.getMessage("BIISubmit.notValidated"));


            //Check if the study is public today
            VisibilityStatus status = VisibilityStatus.PRIVATE;         //Defaults to a private study

            Date publicDateD;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            publicDateD = sdf.parse(publicDate);               //Date from the form

            if (publicDateD.before(new Date())){  //The date received from the form does not contain time, so this should always be before "now"
                status = VisibilityStatus.PUBLIC;
            }

            // Extend the message...
          	messageBody.append("\nFileName: " + file.getOriginalFilename() );

           if (submitter.getUserName().equals(actualUser.getUserName())){
                messageBody.append("\nUser: " + actualUser.getUserName());
           } else {
               messageBody.append("\nUser: " + actualUser.getUserName() + "on behalf of " + submitter.getUserName());
           }
    		if (study==null){
    			messageBody.append("\nNEW STUDY");
    		}else{
    			messageBody.append("\nSTUDY: " + study);
    		}
    		messageBody.append("\nPublic Release Date: " + publicDate);
    		
    		
            logger.info("Queueing study");
			SubmissionItem si = new SubmissionItem(file, submitter.getApiToken(), publicDateD, study, false);
            
			// Submit the item to the queue...
            si.submitToQueue();
            
            messageBody.append("\n\n File Successfully queued.");
            
            logger.info("Queued study. Adding data to session");
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("itemQueued", "msg.studyQueueSuccesfully");
			
			// Cannot load the queue
			emailService.sendQueuedStudyEmail(submitter.getEmail(),si.getOriginalFileName() , FileUtils.byteCountToDisplaySize(si.getFileQueued().length()), si.getPublicReleaseDate(), hostName, study);
			

	    	return new ModelAndView("redirect:itemQueued");
	    	
		} catch (BIIException e){

            ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("submitError");
            logger.error("Submission exception", e);
            mav.addObject("error", e);
            mav.addObject("studyId", study);
            return mav;

        } catch (Exception e){
			
            ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("submitError");
            logger.error("Submission exception",e);
            mav.addObject("error", e);
            // Add the study id...
            mav.addObject("studyId", study);

            messageBody.append("\n\nERROR!!!!!\n\n" + e.getMessage() );
            emailService.sendSimpleEmail( "queueExperiment FAILED in " + hostName + " by " + actualUser.getUserName() , messageBody.toString());

            return mav;
		
        }
	}

    @RequestMapping(value = "/submitCompoundSpectra", method = RequestMethod.POST)
    public ModelAndView uploadSpectra(
            @RequestParam("file") MultipartFile file,
            @RequestParam(required=true,value="compoundid") String compound,
            @RequestParam(required=false,value="owner") String owner,
            HttpServletRequest request)
            throws Exception {

        //Start the submission process...
        logger.info("Compound Reference Spectra Upload. Start");

        StringBuffer messageBody = new StringBuffer();
        String hostName = java.net.InetAddress.getLocalHost().getHostName();
        messageBody.append("Compound Reference Spectra submission started from machine " + hostName);

        // Get the user
        MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        String submitter = user.getUserName();

        // If the user is a curator but there is an owner...
        if (owner != null && user.isCurator()){

            // Overwrite the submitter with the owner...
            submitter = owner;
        }


        try {

            // Extend the message...
            messageBody.append("\nFileName: " + file.getOriginalFilename() );
            messageBody.append("\nCompound Identifier: " + compound );

            if (submitter.equals(user.getUserName())){
                messageBody.append("\nUser: " + user.getUserName());
            } else {
                messageBody.append("\nUser: " + user.getUserName() + "on behalf of " + submitter);
            }


            byte[] bytes = file.getBytes();

            File dir = new File( uploadSpectraDirectory + File.separator + user.getUserName() + File.separator + compound );
            if (!dir.exists())
                dir.mkdirs();

            // Create the file on server
            File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();

            logger.info("Server File Location="
                    + serverFile.getAbsolutePath());

            ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("referencespectraupload");
            mav.addObject("compoundId", compound);
            mav.addObject("successfulUpload", "Compound Reference Spectra Uploaded Successfully");

            return mav;

        }  catch (Exception e){

            ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("submitError");
            logger.error("Submission exception",e);
            mav.addObject("error", e);
            // Add the study id...
            mav.addObject("Compound ID", compound);
            messageBody.append("\n\nERROR!!!!!\n\n" + e.getMessage() );
            return mav;
        }


    }
	
	/**
	 * Redirection after a user has successfully submitted. This prevents double submit with F5.
	 */
	@RequestMapping(value={"/itemQueued"})
	public ModelAndView queueComplete(HttpServletRequest request) {
		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("index"); // default action for this request, unless the session has candy in it.
		if (request.getSession().getAttribute("itemQueued")!=null) {
			mav = AppContext.getMAVFactory().getFrontierMav("queuedOk");
			mav.addObject("msg", request.getSession().getAttribute("itemQueued"));
			request.getSession().removeAttribute("itemQueued");

		}
		return mav;
	}

}