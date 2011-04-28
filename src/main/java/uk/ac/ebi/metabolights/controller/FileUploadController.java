package uk.ac.ebi.metabolights.controller;

import java.io.FileOutputStream;
import java.io.IOException;

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
 * Controls mult part file upload as described in Reference Documentaion 3.0,
 * chapter "15.8 Spring's multipart (fileupload) support".
 * 
 * @author markr
 * 
 */
@Controller
public class FileUploadController extends AbstractController {

	private static Logger logger = Logger.getLogger(FileUploadController.class);


	private @Value("#{appProperties.uploadDirectory}") String uploadDirectory;

	@RequestMapping(value = { "/submit" })
	public String submit(HttpServletRequest request) {
		return GenericController.lastPartOfUrl(request);
	}

	@RequestMapping(value = "/uploadExperiment", method = RequestMethod.POST)
	public ModelAndView handleFormUpload(@RequestParam("file") MultipartFile file) throws IOException {
 // public String handleFormUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			writeFile(file);
			return new ModelAndView("redirect:uploadSuccess");
		} else {
			return new ModelAndView("submit", "message", PropertyLookup.getMessage("msg.upload.notValid"));
		}
	}

	@RequestMapping(value = { "/uploadSuccess" })
	public ModelAndView useMoreDeodorant(HttpServletRequest request) {
		return new ModelAndView("index", "message",
				PropertyLookup.getMessage("msg.upload.ok"));
	}

	private void writeFile(MultipartFile file) throws IOException {
		//TODO get separator from props
		//TODO create dir if not exists

		byte[] bytes = file.getBytes();
		MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		logger.info("Upload by user "+user.getUserId()+" "+user.getUserName());
		logger.info("File #bytes=" + bytes.length + " for file "+ file.getOriginalFilename()+" from user.. ");
		logger.info("must write to " + uploadDirectory);
		FileOutputStream fos = new FileOutputStream(uploadDirectory+ "/"+user.getUserId()+"/"+file.getOriginalFilename()); // or original..
		fos.write(bytes);
		fos.close();
	
	}

}
