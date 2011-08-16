package uk.ac.ebi.metabolights.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import uk.ac.ebi.bioinvindex.model.security.User;
import uk.ac.ebi.bioinvindex.model.term.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.bioinvindex.model.AssayResult;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.StudyService;

/**
 * Controller for entry (=study) details.
 * 
 */
@Controller
public class EntryController extends AbstractController {

	private static Logger logger = Logger.getLogger(EntryController.class);

	@Autowired
	private StudyService studyService;

    private @Value("#{appProperties.publicFtpLocation}") String publicFtpDirectory;
    private @Value("#{appProperties.privateFtpLocation}") String privateFtpDirectory;

	//(value = "/entry/{metabolightsId}")
	@RequestMapping(value = "/{metabolightsId}") 
	public ModelAndView showEntry(@PathVariable("metabolightsId") String mtblId) {
		logger.info("requested entry " + mtblId);		
		Study study = studyService.getBiiStudy(mtblId);
		
		Collection<String> organismNames = new TreeSet<String>();
		for (AssayResult assRes : study.getAssayResults()) {
			for (PropertyValue<?> pv : assRes.getCascadedPropertyValues()) {
				if (pv.getType().getValue().equals("organism")) {
					organismNames.add(pv.getValue());
					//logger.debug("adding "+pv.getValue());
				}
			}
		}

		String ftpLocation = null;
		
		if (study.getStatus().equals(VisibilityStatus.PRIVATE)){	// Only for the submitter
			ftpLocation = "privatefiles/" + study.getAcc();  //Private download, file stream
		}  else {  //Serve back public ftp link
			ftpLocation = publicFtpDirectory.replaceFirst("/ebi/ftp/","ftp://ftp.ebi.ac.uk/") + study.getAcc() +".zip";
		}

		ModelAndView mav = new ModelAndView("entry");
		mav.addObject("study", study);
		mav.addObject("organismNames", organismNames);
        mav.addObject("ftpLocation",ftpLocation);

		return mav;
	}
	
	@RequestMapping(value = "/privatefiles/{file_name}")
	public void getFile(@PathVariable("file_name") String fileName,	HttpServletResponse response) {
		try {

			Boolean validUser = false;
			Long userId = new Long(0);

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!auth.getPrincipal().equals(new String("anonymousUser"))){
				MetabolightsUser principal = (MetabolightsUser) auth.getPrincipal();
				userId = principal.getUserId();
			}

			if (userId>0){ //Check if the logged in user can access the study
				Study study = studyService.getBiiStudy(fileName);
				Collection<User> users = study.getUsers();
				Iterator<User> iter = users.iterator();
				while (iter.hasNext()){
					User user = (User) iter.next();
					if (user.getId().equals(userId)){
						validUser = true;
						break;
					}
				}

			}

			if (!validUser)
				throw new RuntimeException(PropertyLookup.getMessage("Entry.notAuthorised")); 


			try {
				// get your file as InputStream
				InputStream is = new FileInputStream(privateFtpDirectory + fileName + ".zip");

				// let the browser know it's a zip file
				response.setContentType("application/zip");

				// copy it to response's OutputStream
				IOUtils.copy(is, response.getOutputStream());
			} catch (Exception e) {
				throw new RuntimeException(PropertyLookup.getMessage("Entry.fileMissing")); 
			}

			response.flushBuffer();

		} catch (IOException ex) {
			logger.info("Error writing file to output stream. Filename was '"+ fileName + "'");
			throw new RuntimeException("IOError writing file to output stream");
		}

	}

}
