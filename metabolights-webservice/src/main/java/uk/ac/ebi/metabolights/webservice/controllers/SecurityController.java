/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Apr-22
 * Modified by:   kenneth
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.webservice.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import uk.ac.ebi.metabolights.repository.dao.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.repository.security.SecurityService;
import uk.ac.ebi.metabolights.webservice.services.EmailService;

@Controller
@RequestMapping("security")
public class SecurityController extends BasicController{

	@Autowired
	private EmailService emailService;


	private final static Logger logger = LoggerFactory.getLogger(SecurityController.class.getName());
	private StudyDAO studyDAO;

    @RequestMapping(value = "studies/{studyIdentifier:" + StudyController.METABOLIGHTS_ID_REG_EXP +"}/view", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse<Boolean> canUserViewStudy(@PathVariable("studyIdentifier") String studyIdentifier) {

		logger.debug("Requesting " + studyIdentifier + " permissions to the webservice");

		RestResponse<Boolean> response = new RestResponse<>();

		try {
			SecurityService.userAccessingStudy(studyIdentifier, getUser().getApiToken());

			response.setContent(true);
			response.setMessage("User " + getUser().getFullName() + " can view " + studyIdentifier);

		} catch (Exception e) {

			response.setContent(false);
			response.setMessage("User " + getUser().getFullName() + " not granted to view the study " +studyIdentifier + ".");
			response.setErr(e);

		}


		return response;

	}

	@RequestMapping("studies/obfuscationcode/{obfuscationcode}/view")
	@ResponseBody
	public RestResponse<Boolean> canUserViewStudyByObfuscationCode(@PathVariable("obfuscationcode") String obfuscationCode) throws DAOException {


		logger.debug("Requesting permissions of a study with obfuscation code (" + obfuscationCode + ") to the webservice");


		// If nothing happens (exception) return true;
		RestResponse<Boolean> response = new RestResponse<>();

		try {
			studyDAO = DAOFactory.getInstance().getStudyDAO();

			String studyIdentifier = studyDAO.getStudyIdByObfuscationCode(obfuscationCode);

			if (studyIdentifier == null) {
				throw new IsaTabException("Null study identifier returned fot obfuscation code " + obfuscationCode);
			}

			response.setContent(true);
			response.setMessage("User " + getUser().getFullName() + " can view study " + studyIdentifier + " (" + obfuscationCode+ ").");

		} catch (IsaTabException e) {
			response.setContent(false);
			response.setMessage("User " + getUser().getFullName() + " not granted to view the study with this obfuscation code: " + obfuscationCode+ ".");
			response.setErr(e);

		}

		return response;
	}

}
