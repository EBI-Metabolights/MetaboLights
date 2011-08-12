package uk.ac.ebi.metabolights.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.metabolights.utils.StringUtils;

import uk.ac.ebi.metabolights.checklists.CheckList;
import uk.ac.ebi.metabolights.checklists.SubmissionProcessCheckListSeed;
import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabUploader;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;

/**
 * Controls multi part file upload as described in Reference Documentaion 3.0,
 * chapter "15.8 Spring's multipart (fileupload) support".
 * 
 * @author conesa
 */
@Controller
public class BIISubmissionController extends AbstractController {

	private static Logger logger = Logger.getLogger(BIISubmissionController.class);
	
	@RequestMapping(value = { "/biisubmit" })
	public String submit(HttpServletRequest request) {
		return GenericController.lastPartOfUrl(request);
	}
	
	@RequestMapping(value = "/biiuploadExperiment", method = RequestMethod.POST)
	public ModelAndView handleFormUpload(
			@RequestParam("file") MultipartFile file, 
			@RequestParam(required=false,value="public") Boolean publicExp,
			@RequestParam(required=false,value="pickdate") String publicDate,
			HttpServletRequest request) 
		throws Exception {

		//Convert boolean publicExp into VisibilityStatus
		VisibilityStatus status =  (publicExp != null)? VisibilityStatus.PUBLIC : VisibilityStatus.PRIVATE;
		
		//Start the submission process...
		//Create a check list to report back the user..
		CheckList cl = new CheckList(SubmissionProcessCheckListSeed.values());
	
		
		try {

			if (file.isEmpty()){ throw new Exception("File must not be empty.");}
			
			String isaTabFile = writeFile(file, cl);
						
			//Upload to BII
			HashMap<String,String> accessions = uploadToBii(isaTabFile, status, cl, publicDate);
			
			//Log it
			logger.info("These are the new accession numbers: " + accessions);
			if (publicExp == null && publicDate != null){ //Not set to public by the submitter and a public date has been given
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
	 * TODO document method cabron!
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reindex")
	public ModelAndView reindex() throws Exception{
		
		//Get the path for the config folder (where the hibernate properties for the import layer are).
		String configPath = BIISubmissionController.class.getClassLoader().getResource("").getPath()+ "biiconfig/";
	
		//Upload the file to bii
		IsaTabUploader itu = new IsaTabUploader();
		itu.setConfigPath(configPath);
		itu.reindex();
		
		return new ModelAndView("index", "message", PropertyLookup.getMessage("msg.indexed"));
			
	}
	private @Value("#{appProperties.uploadDirectory}") String uploadDirectory;
	/**
	 * Writes a user upload file to designated target directory.
	 * 
	 * @param file user upload
	 * @param status 
	 * @throws IOException 
	 * @throws Exception 
	 */
	private String writeFile(MultipartFile file, CheckList cl) throws IOException  {
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
		
		//Check Item in CheckList
		cl.CheckItem(SubmissionProcessCheckListSeed.FILEUPLOAD.getKey(), "File upload complete for " + file.getOriginalFilename());
		
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
		
		//Get the user
		MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		
		//Calculate the unzip folder (remove the extension + .)
		String unzipFolder = StringUtils.truncate(isaTabFile, 4);
		
		//Get the path for the config folder (where the hibernate properties for the import layer are).
		String configPath = BIISubmissionController.class.getClassLoader().getResource("").getPath()+ "biiconfig/";
		
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // New ISAtab format (1.4)
		String submissionDate = formatter.format(currentDate.getTime());
	
		//Upload the file to bii
		IsaTabUploader itu = new IsaTabUploader(isaTabFile, unzipFolder , user.getUserName(), status, configPath, publicDate, submissionDate);

		//Set the CheckList to get feedback
		itu.setCheckList(cl);
		
		//Upload the file
		return itu.Upload();
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