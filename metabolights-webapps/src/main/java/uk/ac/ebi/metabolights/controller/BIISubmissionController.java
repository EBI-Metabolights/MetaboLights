package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
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
import uk.ac.ebi.metabolights.checklists.CheckList;
import uk.ac.ebi.metabolights.checklists.SubmissionProcessCheckListSeed;
import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabUploader;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Controls multi part file upload as described in Reference Documentation 3.0,
 * chapter "15.8 Spring's multipart (fileupload) support".
 * 
 * @author conesa
 */
@Controller
public class BIISubmissionController extends AbstractController {

	private static Logger logger = Logger.getLogger(BIISubmissionController.class);

    @Autowired
    private IsaTabUploader itu;
	
	@RequestMapping(value = { "/biisubmit" })
	public String submit(HttpServletRequest request) {
		return GenericController.lastPartOfUrl(request);
	}
	
	@RequestMapping(value = { "/presubmit" })
	public ModelAndView preSubmit(HttpServletRequest request) {
		MetabolightsUser user = null;
		
		ModelAndView mav = new ModelAndView("submitPre"); // Call the Pre-Submit page
		if (request.getUserPrincipal() != null)
			user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		if (user != null)
			mav.addObject("user", user);
		
		return mav;
	}
	
	@RequestMapping(value = "/biiuploadExperiment", method = RequestMethod.POST)
	public ModelAndView handleFormUpload(
			@RequestParam("file") MultipartFile file,
			@RequestParam(required=true,value="pickdate") String publicDate,
			HttpServletRequest request) 
		throws Exception {

		//Start the submission process...
		//Create a check list to report back the user..
		CheckList cl = new CheckList(SubmissionProcessCheckListSeed.values());

		try {

            if (file.isEmpty())
				throw new Exception(PropertyLookup.getMessage("BIISubmit.fileEmpty"));

			if (publicDate.isEmpty())
				throw new Exception(PropertyLookup.getMessage("BIISubmit.dateEmpty"));

            //Check if the study is public today
            VisibilityStatus status = VisibilityStatus.PRIVATE;         //Defaults to a private study

            if (publicDate != null){
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                Date publicDateD = sdf.parse(publicDate);               //Date from the form

                if (publicDateD.before(new Date())){  //The date received from the form does not contain time, so this should always be before "now"
                    status = VisibilityStatus.PUBLIC;
                }
            }
			
			String isaTabFile = writeFile(file, cl);
						
			//Upload to BII
			HashMap<String,String> accessions = uploadToBii(isaTabFile, status, cl, publicDate);
			
			//Log it
			logger.info(PropertyLookup.getMessage("BIISubmit.newAccNumbers") + accessions);
			if (publicDate != null){ //The submitter picked the public date
				logger.info("Public release date has been given by the submitter as " + publicDate + " for accession " +accessions);
			}
			

			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("accessionsOK", accessions);
			httpSession.setAttribute("clOK", cl);
			
	    	return new ModelAndView("redirect:submitComplete");

		} catch (Exception e){
			
			ModelAndView mav = new ModelAndView("submitError");
			mav.addObject("cl", cl);
			mav.addObject("error", e);
			return mav;
		}

	}

	/**
	 * Redirection after a user has successfully submitted. This prevents double submit with F5.
	 */
	@RequestMapping(value={"/submitComplete"})
	public ModelAndView accountRequested(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("index"); // default action for this request, unless the session has candy in it.
		if (request.getSession().getAttribute("accessionsOK")!=null) {
			mav = new ModelAndView("submitOk");
			mav.addObject("accessions", request.getSession().getAttribute("accessionsOK"));
			mav.addObject("cl", request.getSession().getAttribute("clOK"));
            request.getSession().removeAttribute("accessionsOK");
			request.getSession().removeAttribute("clOK");
		}
		return mav;
	}

	
	/**
	 * Will reindex the whole index, to use carefully. After it, a tomcat restart is needed.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reindex")
	public ModelAndView reindex() throws Exception{
		
		//Get the path for the config folder (where the hibernate properties for the import layer are).
		String configPath = BIISubmissionController.class.getClassLoader().getResource("").getPath();
	
		//Upload the file to bii
		//IsaTabUploader itu = new IsaTabUploader();
		itu.setDBConfigPath(configPath);
		itu.reindex();
		
		return new ModelAndView("index", "message", PropertyLookup.getMessage("msg.indexed"));
			
	}
	private @Value("#{appProperties.uploadDirectory}") String uploadDirectory;
	private @Value("#{appProperties.publicFtpLocation}") String publicFtpLocation;
	private @Value("#{appProperties.privateFtpLocation}") String privateFtpLocation;
	
	
	/**
	 * Writes a user upload file to designated target directory.
	 * 
	 * @param file user upload
	 * @param CheckList
	 * @throws IOException 
	 * @throws Exception 
	 */
	public String writeFile(MultipartFile file, CheckList cl) throws IOException  {
		//TODO get separator from props

		byte[] bytes = file.getBytes();
		MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		String targetDir=uploadDirectory+ "/"+user.getUserId()+"/";

		logger.info("Upload by user= "+user.getUserId()+" "+user.getUserName());
		logger.info("File #bytes   = "+bytes.length + " for file "+ file.getOriginalFilename()+" from user.. ");
		logger.info("Target dir    = "+targetDir);

		// Check if dir needs to be created
		File dir=new File(targetDir);
		if (!dir.exists()) {
			boolean success = (new File(targetDir)).mkdir();
			if (success) {
				logger.info("Target dir " +targetDir+" created");
			}    
		}

		//Set up file name and unzip folder
		String isaTabFile = uploadDirectory+ "/"+user.getUserId()+"/"+file.getOriginalFilename();
		
		//Write the file in the file system
		FileOutputStream fos = new FileOutputStream(isaTabFile); // or original..
		fos.write(bytes);
		fos.close();
		
		// If there is a CheckList
		if (cl != null){
			//Check Item in CheckList
			cl.CheckItem(SubmissionProcessCheckListSeed.FILEUPLOAD.getKey(), PropertyLookup.getMessage("BIISubmit.fileUploadComplete") +" "+ file.getOriginalFilename());
		}
		
		return isaTabFile;
	}
	
	/**
	 * Upload the IsaTabFile (zip) into BII database replacing the id with our own accession numbers.
	 * @param isaTabFile
	 * @param status
	 * @return
	 * @throws Exception 
	 */
	private HashMap<String,String> uploadToBii (String isaTabFile, VisibilityStatus status, CheckList cl, String publicDate) throws Exception{
		
	
		// Upload the file to bii
		//IsaTabUploader itu = getIsaTabUploader(isaTabFile, status, publicDate);
        itu = getIsaTabUploader(isaTabFile, status, publicDate);
		
		// Set the CheckList to get feedback
		itu.setCheckList(cl);
		
		// Upload the file
		return itu.Upload();
	}

	/**
	 * Returns a default configured uploader. After it you may probably need to set:
	 * UnzipFolder
	 * publicDate : Format expected --> dd-MMM-yyyy
	 * @return
	 * @throws ParseException 
	 */
	public IsaTabUploader getIsaTabUploader(String isaTabFile, VisibilityStatus status, String publicDate) throws ParseException{

		//ISA TAB FORMAT spec: Dates should be supplied in the ISO 8601 format "YYYY-MM-DD"
		if (publicDate == null) publicDate = "";
		// Get the user
		MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		// Get the path for the config folder (where the hibernate properties for the import layer are).
		//String configPath = BIISubmissionController.class.getClassLoader().getResource("").getPath()+ "biiconfig/";
        String configPath = BIISubmissionController.class.getClassLoader().getResource("").getPath();

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

	}
	
	
	@RequestMapping(value = "/uploadtest")
	public ModelAndView test(){
		
		//For testing success
		HashMap<String,String> accessions = new HashMap<String,String>();
		accessions.put("ID1", "MTBL1");
		accessions.put("ID2", "MTBL2");
		
		//For testing failure
		Exception e = new Exception("This is a test exception");
		
		//For testing checklist
		CheckList cl = new CheckList(SubmissionProcessCheckListSeed.values());
		cl.CheckItem(SubmissionProcessCheckListSeed.FILEUPLOAD.getKey(), "File upload checked");
		
		
		ModelAndView mav = new ModelAndView("submitOk");
		mav.addObject("accessions", accessions);
		mav.addObject("error", e);
		mav.addObject("cl", cl);
		
		return mav;
	}

}