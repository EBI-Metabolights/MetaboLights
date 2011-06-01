package uk.ac.ebi.metabolights.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
		return new ModelAndView("error", "errorMainMessage", "500 - Internal Server Error. The server encountered an unexpected condition which prevented it from fulfilling the request.");
	}

	@RequestMapping(value = "/errors/404")
	public ModelAndView handle404() {
		return new ModelAndView("error", "errorMainMessage", "404 - Not Found. The server has not found anything matching the Request-URI");
	}

	@RequestMapping(value = "/errors/403")
	public ModelAndView handle403() {
		return new ModelAndView("error", "errorMainMessage", "403 Forbidden - The server understood the request, but is refusing to fulfill it.");
	}

}
