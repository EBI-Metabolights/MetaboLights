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

@Controller
@RequestMapping("email")
@PreAuthorize( "hasRole('ROLE_SUPER_USER')")
public class EmailController extends BasicController {

	@Autowired
	private EmailService emailService ;

    @RequestMapping(method= RequestMethod.POST, value = "studydeleted")
	@ResponseBody
	public RestResponse<String> studydeletedemail(@RequestParam(required=true,value="useremail") String useremail,
												  @RequestParam(required=true,value="hostname") String hostname,
												  @RequestParam(required=true,value="studyid") String studyid) {

		logger.debug("About to send study deleted email.");

		emailService.sendQueuedDeletion(useremail,hostname,studyid);

		RestResponse<String> response = new RestResponse<>();

		response.setMessage("Study deleted email sent");

		return response;

	}

	@RequestMapping(method= RequestMethod.GET, value = "studygoinglive/{studyId}")
	@ResponseBody
	public RestResponse<String> studyGoingLiveEmail(@PathVariable(value="studyId") String studyId) throws DAOException, IsaTabException {

		logger.debug("About to send study going live email.");

		// Get the study
		StudyDAO studyDAO = DAOFactory.getInstance().getStudyDAO();

		// NOTE: this loads all the data, files system data included, could be improved if we only load data from DB (enough)
		Study study = studyDAO.getStudy(studyId, getUser().getApiToken());

		emailService.sendStudyGoingPublicNotification(study);

		RestResponse<String> response = new RestResponse<>();

		response.setMessage("Study going live email sent");

		return response;

	}

}
