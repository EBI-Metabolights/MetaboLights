/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 8/14/14 1:27 PM
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.metabolights.service.AppContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Generic class to get Spring controlled forwards to a java server page.<br><br>
 * Handler methods that are annotated with @RequestMapping can have very flexible signatures, see more on<br>
 * http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/mvc.html#mvc-ann-requestmapping-arguments
 */

@Controller
public class GenericController {

	private static Logger logger = LoggerFactory.getLogger(GenericController.class);

	/**
	 * Forwards to the jsp based on the last part of the requested URL.
	 *
	 * @param request
	 * @return String indicating JSP target
	 */
	@RequestMapping(value={ "/about","/help","/download", "/useroptions", "/pleasewait" ,"/analysis"})
	public ModelAndView modelAndView (HttpServletRequest request) {
		return lastPartOfUrl(request);
	}

    /**
     * Redirects to ensure older links still work
     */
    @RequestMapping(value={ "/submithelp"})
    public ModelAndView oldSubmitHelp (HttpServletRequest request) {
        return new ModelAndView ("redirect:help");
    }

    @RequestMapping(value={ "/downloadplugin"})
    public ModelAndView olddownloadPlugin (HttpServletRequest request) {
        return new ModelAndView ("redirect:download");
    }




	/**
	 * Forwards to the jsp based on the last part of the requested URL.
	 *
	 * @param request
	 * @return String indicating JSP target
	 */
	public static ModelAndView lastPartOfUrl (HttpServletRequest request) {
		String requestUrl = request.getRequestURL().toString();
		String target=requestUrl.replaceFirst("^(.)*/", "");
		logger.debug("target is "+target);

		target = target!=null&&!target.equals("")?target:"index";
		return AppContext.getMAVFactory().getFrontierMav(target);

	}
}


