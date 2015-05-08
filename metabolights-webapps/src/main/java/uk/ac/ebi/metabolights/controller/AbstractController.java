/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 7/14/14 10:09 AM
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.TextUtils;

/**
 * Abstract controller providing common functionality to the real controllers.
 * @author markr
 *
 */
public abstract class AbstractController {
	protected static Logger logger = LoggerFactory.getLogger(AbstractController.class);

	@ExceptionHandler(Exception.class)
	public ModelAndView handleAnyException(Exception ex) {
		logger.error("Exception encountered");
		
		//TODO dump stacktrace in log4j

		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("error");
		mav.addObject("errorStack", TextUtils.getErrorStackAsHTML(ex));
		mav.addObject("errorMainMessage", ex.getMessage());
		return mav;
	}
	public ModelAndView printMessage(String title, String message){
		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("message");
		mav.addObject("title",title);
		mav.addObject("message", message);
		
		return mav;
	}
	public ModelAndView printMessageFromLookup(String titleTag, String messageTag){
		
		return printMessage(PropertyLookup.getMessage(titleTag), PropertyLookup.getMessage(messageTag));
	}

	protected ModelAndView getResctrictedAccessPage() {
		return printMessage(PropertyLookup.getMessage("msg.cantAccessPage.title"), PropertyLookup.getMessage("msg.cantAccessPage.message"));
	}

	protected ModelAndView redirect(String where){
		return  new ModelAndView ("redirect:" + where);
	}
}
