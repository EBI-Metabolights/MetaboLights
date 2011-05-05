package uk.ac.ebi.metabolights.controller;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPFile;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.metabolights.authenticate.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;

/**
 * Controls multi part file upload as described in Reference Documentaion 3.0,
 * chapter "15.8 Spring's multipart (fileupload) support".
 * 
 * @author markr
 * 
 */
@Controller
public class FileUploadController extends AbstractController {

	private static Logger logger = Logger.getLogger(FileUploadController.class);
	
	@RequestMapping(value = { "/submit" })
	public String submit(HttpServletRequest request) {
		return GenericController.lastPartOfUrl(request);
	}

	@RequestMapping(value = "/uploadExperiment", method = RequestMethod.POST)
	public ModelAndView handleFormUpload(@RequestParam("file") MultipartFile file) throws Exception {
		// public String handleFormUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			//writeFile(file);
			writeFileToFTPServer(file);
			return new ModelAndView("redirect:uploadSuccess");
		} else {
			return new ModelAndView("submit", "message", PropertyLookup.getMessage("msg.upload.notValid"));
		}
	}

	@RequestMapping(value = { "/uploadSuccess" })
	public ModelAndView uploadOk(HttpServletRequest request) {
		return new ModelAndView("submitOk", "message","");
	}
	
	private @Value("#{appProperties.ftpServer}") String ftpServer;
	private @Value("#{appProperties.ftpServerMainSubDir}") String ftpServerMainSubDir;
	private @Value("#{appProperties.ftpUser}") String ftpUser;
	private @Value("#{appProperties.ftpPassword}") String ftpPassword;

	/**
	 * Upload a file to private FTP server.
	 * @param file
	 * @throws Exception
	 */
	private void writeFileToFTPServer(MultipartFile file) throws Exception {
		
		MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		String userName = user.getUserName();

		logger.info("Upload by user "+user.getUserId()+" "+user.getUserName());
		logger.info("File size in bytes="+file.getBytes().length + ", file name="+ file.getOriginalFilename());

		FTPClient client = new FTPClient();
		client.setSecurity(FTPClient.SECURITY_FTPES); // enables FTPES
		client.connect(ftpServer);
		client.login(ftpUser, ftpPassword); 
		client.changeDirectory(ftpServerMainSubDir);

		// create if necessary a sub directory for the user (using the username)
		boolean createSubDir=true;
		FTPFile[] list = client.list();
		findSubDir:
		for (FTPFile f : list) {
			if (f.getName().equals(userName)) {
				createSubDir=false;
				break findSubDir;
			}
		}
		if (createSubDir) {
			client.createDirectory(userName);
		}
		client.changeDirectory(userName);

		// do da upload
		client.upload(file.getOriginalFilename(),file.getInputStream(),0, 0, null);
		client.disconnect(true);
		
		//TODO send an alert email to metabolights or some other destination

	}


	//private @Value("#{appProperties.uploadDirectory}") String uploadDirectory;
	/**
	 * Writes a user upload file to designated target directory.
	 * 
	 * @param file user upload
	 * @throws IOException
	 */
	/* private void writeFile(MultipartFile file) throws IOException {
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
		FileOutputStream fos = new FileOutputStream(uploadDirectory+ "/"+user.getUserId()+"/"+file.getOriginalFilename()); 
		fos.write(bytes);
		fos.close();
	}
	 */


}
