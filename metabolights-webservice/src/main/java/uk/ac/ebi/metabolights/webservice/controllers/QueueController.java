package uk.ac.ebi.metabolights.webservice.controllers;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.queue.SubmissionQueueManager;
import uk.ac.ebi.metabolights.webservice.utils.FileUtil;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.webservice.utils.Zipper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.ZipOutputStream;

/**
 * User: conesa
 * Date: 08/09/15
 * Time: 16:13
 */
@Controller
@RequestMapping("queue")
@PreAuthorize( "hasRole('ROLE_SUPER_USER')")
public class QueueController extends BasicController {

	private static final Logger logger = LoggerFactory.getLogger(QueueController.class);

	@Autowired
	private SubmissionQueueManager queueManager;

	private  @Value("#{ondemand}") String zipOnDemandLocation;

	@RequestMapping("status")
	@ResponseBody
	public RestResponse<Boolean> getStatus() {

		logger.info("Queue status requested");

		RestResponse<Boolean> response = new RestResponse();

		Boolean running = queueManager.getIsRunning();
		response.setContent(running);
		response.setMessage("Queue IS " + (running?"":"NOT ") +  "running.");
		return response;

	}

	@RequestMapping("toggle")
	@ResponseBody
	public RestResponse<Boolean> toggle() {

		logger.info("Queue toggle requested");

		RestResponse<Boolean> response = new RestResponse();

		if (queueManager.getIsRunning()) {

			queueManager.stop();
		} else {
			queueManager.start();
		}

		return getStatus();

	}

	@RequestMapping(value = "uploadFile", method = RequestMethod.POST,consumes = {"multipart/form-data"})
	@ResponseBody
	public RestResponse<String> uploadFile(
			@RequestParam(required = true ,value = "file") MultipartFile file,
			@RequestParam(required=true,value="publicDate") String publicDate,
			@RequestParam(required=true,value="study") String study,
			@RequestParam(required=true,value="apiKey") String apiKey) throws Exception {

		RestResponse restResponse = new RestResponse();


		String FILE_NAME_SEP = "~";
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	//	String date = publicDate == null?"":sdf.format(publicDate);

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String timeStamp = sdf.format(cal.getTime());

		String fileName = apiKey + FILE_NAME_SEP + study + FILE_NAME_SEP + timeStamp + FILE_NAME_SEP + FilenameUtils.removeExtension(file.getOriginalFilename());

		String uploadDirectory = PropertiesUtil.getProperty("uploadDirectory") + "queue/";
		// create zip with pattern in ondemand folder
		// move multipart file to onto the zip folder
		// move the zip folder to the upload directory or the queue

		String zipFile = zipOnDemandLocation + fileName + ".zip";
		String zipFileFolder = zipOnDemandLocation + fileName ;

		if (!FileUtil.fileExists(zipFileFolder))  // Just to be sure that the file *doesn't already* exist
		{File zip_file_folder = new File(zipFileFolder);
		zip_file_folder.mkdir();
		file.transferTo(new File(zip_file_folder + File.separator + file.getOriginalFilename()));

			Zipper.zip(zipFileFolder, zipFile);

			FileUtils.deleteDirectory(zip_file_folder);
			FileUtils.moveFileToDirectory(new File(zipFile),new File(uploadDirectory), false);

		}




		restResponse.setMessage("Success");

		return restResponse;
	}





	}