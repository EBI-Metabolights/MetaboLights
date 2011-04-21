package uk.ac.ebi.metabolights.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uk.ac.ebi.metabolights.properties.PropertyLookup;

/**
 * Controller for Metabolights searching.  
 * @author markr
 *
 */
@Controller
public class SearchController {
	
	/**
	 * Redirects to BII.
	 * @param request
	 * @return URL String to BII search with the user's puery pasted in
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST )
	public String zoeken (HttpServletRequest request) {
    	String biiUrlPrefix = PropertyLookup.getMessage("url.bii.prefix.search");
    	String redirect="redirect:"+biiUrlPrefix+"?searchPattern="+request.getParameter("query");
    	return redirect;
	}
    
}

