package uk.ac.ebi.metabolights.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String handle500() {
		return "error";
	}

	@RequestMapping(value = "/errors/404")
	public String handle404() {
		return "error";
	}

	@RequestMapping(value = "/errors/403")
	public String handle403() {
		return "error";
	}

}
