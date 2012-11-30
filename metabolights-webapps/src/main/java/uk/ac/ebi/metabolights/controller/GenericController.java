package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
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

	private static Logger logger = Logger.getLogger(GenericController.class);

	/** 
	 * Forwards to the jsp based on the last part of the requested URL.
	 *  
	 * @param request
	 * @return String indicating JSP target
	 */
	@RequestMapping(value={"/index","/about","/submitHelp","/download","/downloadplugin", "/useroptions"})
	public ModelAndView useMoreDeodorant (HttpServletRequest request) {
		return lastPartOfUrl(request);
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


