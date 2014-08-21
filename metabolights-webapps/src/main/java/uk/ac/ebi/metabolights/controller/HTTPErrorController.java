/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/22/12 3:13 PM
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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.metabolights.service.AppContext;

/**
 * Controller to forward to default error page (error.jsp)
 * instead of dumping an ugly (detailed) message to the user.
 * We get here through web.xml
 *  	<error-page>
 *	    <error-code>500</error-code>
 *	    <location>/errors/500</location>
 *	</error-page>
 * etc.
 * <p>
 * However, preferably our own Controllers should extend AbstractController
 * which has ExceptionHandler annotation allowing for more refined handling
 * based on exception type and content. 
 * @author markr
 */

@Controller
public class HTTPErrorController {

	//TODO .. logging etc. Get current error... but how?	

	@RequestMapping(value = "/errors/500")
	public ModelAndView handle500() {
		return AppContext.getMAVFactory().getFrontierMav("error", "errorMainMessage", "500 - Internal Server Error. The server encountered an unexpected condition which prevented it from fulfilling the request.");
	}

	@RequestMapping(value = "/errors/404")
	public ModelAndView handle404() {
		return AppContext.getMAVFactory().getFrontierMav("error", "errorMainMessage", "404 - Not Found. The server has not found anything matching the Request-URI");
	}

	@RequestMapping(value = "/errors/403")
	public ModelAndView handle403() {
		return AppContext.getMAVFactory().getFrontierMav("error", "errorMainMessage", "403 Forbidden - The server understood the request, but is refusing to fulfill it.");
	}
	
}
