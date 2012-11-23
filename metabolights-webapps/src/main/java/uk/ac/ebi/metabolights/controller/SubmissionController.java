package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.metabolights.checklists.CheckList;
import uk.ac.ebi.metabolights.checklists.SubmissionProcessCheckListSeed;
import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabUploader;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.EmailService;
import uk.ac.ebi.metabolights.utils.FileUtil;
import uk.ac.ebi.metabolights.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
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
public class SubmissionController extends AbstractController {

	@Autowired
	private EmailService emailService;
	
	private static Logger logger = Logger.getLogger(SubmissionController.class);

    //@Autowired
    private IsaTabUploader itu = new IsaTabUploader();
	
//	@RequestMapping(value = { "/biisubmit" })
//	public ModelAndView submit(HttpServletRequest request) {
//		return GenericController.lastPartOfUrl(request);
//	}
	
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
	@RequestMapping(value = "/reindex")
	public ModelAndView reindex() throws Exception{
		
		//Get the path for the config folder (where the hibernate properties for the import layer are).
		String configPath = SubmissionController.class.getClassLoader().getResource("").getPath();

		itu.setDBConfigPath(configPath);
		itu.reindex();
		
		return AppContext.getMAVFactory().getFrontierMav("index", "message", PropertyLookup.getMessage("msg.indexed"));
			
	}
	private @Value("#{uploadDirectory}") String uploadDirectory;
	private @Value("#{publicFtpStageLocation}") String publicFtpLocation;      //TODO, short term fix until filesystem is mounted RW
	private @Value("#{privateFtpStageLocation}") String privateFtpLocation;
	
	
//	/**
//	 * Writes a user upload file to designated target directory.
//	 * 
//	 * @param file user upload
//	 * @param cl
//	 * @throws IOException 
//	 * @throws Exception 
//	 */
//	public String writeFile(MultipartFile file, CheckList cl) throws IOException  {
//		//TODO get separator from props
//
//        logger.info("BII how large is the file = "+file.getSize());
//        logger.info("BII writeFile to bytes");
//		//byte[] bytes = file.getBytes();
//
//        logger.info("BII find user info");
//		MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//		String targetDir=uploadDirectory+ "/"+user.getUserId()+"/";
//
//		logger.info("Upload by user = "+user.getUserId()+" "+user.getUserName());
//		logger.info("File size      = "+file.getSize() + " for file "+ file.getOriginalFilename()+" from user.. ");
//		logger.info("Target dir     = "+targetDir);
//
//		// Check if dir needs to be created
//		File dir=new File(targetDir);
//		if (!dir.exists()) {
//			boolean success = (new File(targetDir)).mkdir();
//			if (success) {
//				logger.info("Target dir " +targetDir+" created");
//			}    
//		}
//
//		//Set up file name and unzip folder
//		String isaTabFile = uploadDirectory+ "/"+user.getUserId()+"/"+file.getOriginalFilename();
//		
//		//Write the file in the file system
//        logger.info("Write the file in the file system "+ isaTabFile);
//        File newFile = new File(isaTabFile);
//        logger.info("The new file is created, now we transfer the file from memory to the filesystem");
//        file.transferTo(newFile);
//		//FileOutputStream fos = new FileOutputStream(isaTabFile); // or original..
//		//fos.write(bytes);
//		//fos.close();
//		
//		// If there is a CheckList
//		if (cl != null){
//			//Check Item in CheckList
//			cl.CheckItem(SubmissionProcessCheckListSeed.FILEUPLOAD.getKey(), PropertyLookup.getMessage("BIISubmit.fileUploadComplete") +" "+ file.getOriginalFilename());
//		}
//		
//		return isaTabFile;
//	}
//
	
//	/**
//	 * Upload the IsaTabFile (zip) into BII database replacing the id with our own accession numbers.
//	 * @param isaTabFile
//	 * @param status
//	 * @return
//	 * @throws Exception 
//	 */
//	private HashMap<String,String> uploadToBii (String isaTabFile, VisibilityStatus status, CheckList cl, String publicDate) throws Exception{
//		
//	
//		// Upload the file to bii
//		//IsaTabUploader itu = getIsaTabUploader(isaTabFile, status, publicDate);
//        itu = getIsaTabUploader(isaTabFile, status, publicDate);
//		
//		// Set the CheckList to get feedback
//		itu.setCheckList(cl);
//		
//		// Upload the file
//		return itu.Upload();
//	}
	
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

	}


}