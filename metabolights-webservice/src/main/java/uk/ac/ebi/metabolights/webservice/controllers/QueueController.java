package uk.ac.ebi.metabolights.webservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.queue.SubmissionQueueManager;

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





}