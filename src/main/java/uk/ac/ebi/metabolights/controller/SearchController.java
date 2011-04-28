package uk.ac.ebi.metabolights.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for Metabolights searching.  
 * @author markr
 *
 */
@Controller
public class SearchController extends AbstractController{
	
	private @Value("#{appProperties.urlBiiPrefixSearch}") String urlBiiPrefixSearch;
	
	/**
	 * Redirects to BII.
	 * @param request
	 * @return URL String to BII search with the user's query pasted in
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST )
	public String zoeken (HttpServletRequest request) {
    	String redirect="redirect:"+urlBiiPrefixSearch+"?searchPattern="+request.getParameter("query");
    	return redirect;
	}
    
}

