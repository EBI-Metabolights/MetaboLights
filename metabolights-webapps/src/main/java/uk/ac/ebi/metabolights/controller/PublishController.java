package uk.ac.ebi.metabolights.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabUploader;
import uk.ac.ebi.metabolights.model.MetabolightsUser;

/**
 * Make a study public. THis implies to change the status in the database, reindex, and move the zip file to the public ftp.
 * 
 * @author conesa
 */
@Controller
public class PublishController extends AbstractController {

	private static Logger logger = Logger.getLogger(PublishController.class);

	//Ftp locations
	private @Value("#{appProperties.publicFtpLocation}") String publicFtpLocation;
	private @Value("#{appProperties.privateFtpLocation}") String privateFtpLocation;

	@RequestMapping(value = { "/publish" })
	public ModelAndView publish(@RequestParam(required=true,value="study") String study, HttpServletRequest request) throws Exception {

		// Get the user
		MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		// Log start
		logger.info("Publishing the study " + study + " owned by " + user.getUserName());
		
		//Get the path for the config folder (where the hibernate properties for the import layer are).
		String configPath = PublishController.class.getClassLoader().getResource("").getPath()+ "biiconfig/";
	
		//Create the uploader
		IsaTabUploader itu = new IsaTabUploader();
		
		// Set the config folder, and the ftp folders
		itu.setDBConfigPath(configPath);
		itu.setCopyToPrivateFolder(privateFtpLocation);
		itu.setCopyToPublicFolder(publicFtpLocation);
		
		//Create the view
		ModelAndView mav = new ModelAndView("publishOk");
		
		// Change the status
		try {
			
			// Publish the study...
			itu.PublishStudy(study);
			
			// TODO...this is just to test.
			mav.addObject("message", "Study " + study + " has been made public.");
		
		} catch (Exception e) {
			
			// Auto-generated catch block
			e.printStackTrace();

			// Add the error to the page
			mav.addObject("error", new Exception ("There's been a problem while making the study " + study + " public\n" + e.getMessage()));
			
		}
		
		//Return the ModelAndView
		return mav;
		
	}


}