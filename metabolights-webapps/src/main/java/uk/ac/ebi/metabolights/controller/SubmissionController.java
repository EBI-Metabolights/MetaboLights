package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabUploader;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.EmailService;
import uk.ac.ebi.metabolights.service.StudyService;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

/**
 * Controls multi part file upload as described in Reference Documentation 3.0,
 * chapter "15.8 Spring's multipart (fileupload) support".
 *
 * @author conesa
 */
@Controller
public class SubmissionController extends AbstractController {

	@Autowired
	private EmailService emailService;

    @Autowired
    private StudyService studyService;

	private static Logger logger = Logger.getLogger(SubmissionController.class);

    //@Autowired
    private IsaTabUploader itu = new IsaTabUploader();

	@RequestMapping(value = { "/presubmit" })
	public ModelAndView preSubmit(HttpServletRequest request) {
		MetabolightsUser user = null;

		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("submitPre"); // Call the Pre-Submit page
		if (request.getUserPrincipal() != null)
			user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		if (user != null)
			mav.addObject("user", user);

		return mav;
	}

	/**
	 * Will reindex the whole index, to use carefully.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reindex") //must accept parameters else reindex all studies looping through
	public ModelAndView reindexall(
            @RequestParam(required = false, value = "study") String study
            ) throws Exception{

		//Get the path for the config folder (where the hibernate properties for the import layer are).
		String configPath = SubmissionController.class.getClassLoader().getResource("").getPath();

		itu.setDBConfigPath(configPath);

        if(study != null){

            logger.info("Re-indexing study: " + study);
            try {
                itu.reindexStudy(study);
            } catch (Exception e){
                logger.error("Re-indexing of study "+study+" failed.");
            }

            return new ModelAndView ("redirect:index?message="+ PropertyLookup.getMessage("msg.studyIndexed")+" ("+study+")");
        } else {

            //loop through the studies
            List<String> studyList = studyService.findAllStudies();
            Iterator<String> stringIterator = studyList.iterator();

            while (stringIterator.hasNext()){
                String acc = stringIterator.next();
                logger.info("Re-indexing study: " +acc);
                try{
                    itu.reindexStudy(acc);
                } catch (Exception e) {
                    logger.error("Re-indexing of study "+acc+" failed.");
                }

            }

            return new ModelAndView ("redirect:index?message="+ PropertyLookup.getMessage("msg.indexed"));
        }

	}

    /**
     * Will reindex the whole database, to use carefully.
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reindexall")
    public ModelAndView reindex() throws Exception{

        //Get the path for the config folder (where the hibernate properties for the import layer are).
        String configPath = SubmissionController.class.getClassLoader().getResource("").getPath();

        itu.setDBConfigPath(configPath);
        itu.reindex();

        return new ModelAndView ("redirect:index?message="+ PropertyLookup.getMessage("msg.indexed"));

    }

	private @Value("#{uploadDirectory}") String uploadDirectory;
	private @Value("#{publicFtpStageLocation}") String publicFtpLocation;      //TODO, short term fix until filesystem is mounted RW
	private @Value("#{privateFtpStageLocation}") String privateFtpLocation;


	/**
	 * Returns a default configured uploader. After it you may probably need to set:
	 * UnzipFolder
	 * publicDate : Format expected --> dd-MMM-yyyy
	 * @return
	 * @throws ParseException
	 */
/*	public IsaTabUploader getIsaTabUploader(String isaTabFile, VisibilityStatus status, String publicDate) throws ParseException{

		//ISA TAB FORMAT spec: Dates should be supplied in the ISO 8601 format "YYYY-MM-DD"
		if (publicDate == null) publicDate = "";
		// Get the user
		MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		// Get the path for the config folder (where the hibernate properties for the import layer are).
		//String configPath = BIISubmissionController.class.getClassLoader().getResource("").getPath()+ "biiconfig/";
        String configPath = SubmissionController.class.getClassLoader().getResource("").getPath();

		// Get today's date.
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat isaTabFormatter = new SimpleDateFormat("yyyy-MM-dd"); // New ISAtab format (1.4)
		String submissionDate = isaTabFormatter.format(currentDate.getTime());

		// If there is public date
		if (!publicDate.equals("")){
			SimpleDateFormat webDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
			Date publicDateD = webDateFormatter.parse(publicDate);
			// Format public date to IsaTab format date
			publicDate = isaTabFormatter.format(publicDateD);
		}

		//IsaTabUploader itu = new IsaTabUploader();

		// Set common properties
		itu.setOwner(user.getUserName());
		itu.setCopyToPrivateFolder(privateFtpLocation);
		itu.setCopyToPublicFolder(publicFtpLocation);
		itu.setDBConfigPath(configPath);
		itu.setSubmissionDate(submissionDate);

		// Set properties related with the file itself...
		// Calculate the unzip folder (remove the extension + .)
		String unzipFolder = StringUtils.truncate(isaTabFile, 4);
		itu.setUnzipFolder(unzipFolder);
		itu.setIsaTabFile(isaTabFile);
		itu.setPublicDate(publicDate);
		itu.setStatus(status);

		return itu;

	}*/


}