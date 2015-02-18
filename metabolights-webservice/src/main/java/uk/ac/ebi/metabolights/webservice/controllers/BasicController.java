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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.security.SpringUser;

/**
 * User: conesa
 * Date: 10/02/15
 * Time: 17:11
 */
public class BasicController {

	protected static final Logger logger = LoggerFactory.getLogger(BasicController.class);

	protected User getUser(){

		User user ;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.getPrincipal().equals(new String("anonymousUser"))){

			 user = ((SpringUser) auth.getPrincipal()).getMetaboLightsUser();

		} else {

			user = new User();
			user.setUserName(auth.getPrincipal().toString());
		}

		return user;
	}

	@ExceptionHandler
	@ResponseBody
	public RestResponse handleException(Exception e) {

		RestResponse response = new RestResponse(e);

		response.setMessage("There's been an error processing your request. See err object for technical details.");

		return response;

	}
}