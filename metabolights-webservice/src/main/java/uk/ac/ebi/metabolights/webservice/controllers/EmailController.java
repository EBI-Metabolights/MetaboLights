/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Feb-10
 * Modified by:   conesa
 *
 *
 * Copyright 2015 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.webservice.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import uk.ac.ebi.metabolights.repository.dao.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.services.EmailService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("email")
@PreAuthorize( "hasRole('ROLE_SUPER_USER')")
public class EmailController extends BasicController {

	@Autowired
	private EmailService emailService ;

    @RequestMapping(method= RequestMethod.POST, value = "test")
	@ResponseBody
	public RestResponse<String> sendTestEmail(@RequestParam(required=true,value="useremail") String useremail) {

		logger.debug("About to send a test email.");

		emailService.sendSimpleEmail(new String[]{useremail}, "This is a test email from metabolights", "This is trying to test if the email settings and servers involved are working properly.");

		RestResponse<String> response = new RestResponse<>();

		response.setMessage("Test email sent");

		return response;

	}


	@RequestMapping(method= RequestMethod.GET, value = "goinglive/{numberOfDays}")
	@ResponseBody
	public RestResponse<ArrayList<String>> studyGoingLiveEmail(@PathVariable(value="numberOfDays") int numberOfDays) throws DAOException {

		logger.debug("About to send studies going live in {} days emails.", numberOfDays);

		// Get the study DAO
		StudyDAO studyDAO = DAOFactory.getInstance().getStudyDAO();

		// Get the list of studies going live in the days specified
		List<String> studiesGoingLive = studyDAO.getStudiesToGoLiveList(getUser().getApiToken(),numberOfDays);

		RestResponse<ArrayList<String>> response = new RestResponse<>();
		response.setContent(new ArrayList<String>());

		String itemLog = null;
		int errors = 0;

		for (String studyId : studiesGoingLive) {

			// NOTE: this loads all the data, files system data included, could be improved if we only load data from DB (enough)
			Study study = null;
			try {
				study = studyDAO.getStudy(studyId, getUser().getApiToken());

				emailService.sendStudyGoingPublicNotification(study);

				itemLog = "Going live email for " + studyId + " sent successfully.";


			} catch (IsaTabException e) {

				itemLog = "Can't get study " + studyId + " to send going live email: " + e.getMessage();
				logger.error(itemLog, studyId,e);
				errors++;
				response.setErr(e);

			} catch (Exception e){
				itemLog = "Can't send study going live email for " + studyId + ": " + e.getMessage();
				logger.error(itemLog, studyId,e);
				errors++;
				response.setErr(e);
			}

			response.getContent().add(itemLog);

		}

		if (errors > 0) {
			response.setMessage("There were " + errors + " errors out of " + studiesGoingLive.size() + ". Check content for details. Last error is in the error object.");
		} else {
			response.setMessage("Study going live email sent");
		}

		return response;

	}

}
