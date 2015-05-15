/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 5/19/14 10:19 AM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.service.AppContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Controls multi part file upload as described in Reference Documentation 3.0,
 * chapter "15.8 Spring's multipart (fileupload) support".
 *
 * @author conesa
 */
@Controller
public class SubmissionController extends AbstractController {

	private @Value("#{isatabuploaderconfig}")String configFolder;

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

}