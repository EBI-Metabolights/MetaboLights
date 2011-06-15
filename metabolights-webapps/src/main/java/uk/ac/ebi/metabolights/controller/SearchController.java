package uk.ac.ebi.metabolights.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.search.LuceneSearchResult;
import uk.ac.ebi.metabolights.service.SearchService;

/**
 * 
 * Controller for Metabolights searching.  
 * @author markr
 *
 */
@Controller
public class SearchController extends AbstractController{
	
	private static Logger logger = Logger.getLogger(SearchController.class);
	
	@Autowired
	private SearchService searchService;

	
	private @Value("#{appProperties.urlBiiPrefixSearch}") String urlBiiPrefixSearch;
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST )
	public ModelAndView  luceneSearch (HttpServletRequest request) {

	    List<LuceneSearchResult> resultSet = new ArrayList<LuceneSearchResult>();
	    String query = request.getParameter("query");
		try {
			logger.info("searching for "+query);
			resultSet = searchService.search(query);
			logger.debug("Found #results = "+resultSet.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	ModelAndView mav = new ModelAndView("searchResult");
    	mav.addObject("searchResults", resultSet);
    	mav.addObject("userQuery", request.getParameter("query"));
    	return mav;
	}

	

	/**
	 * Redirects to BII, temp solution to invoke BII on JBoss.
	 * @param request
	 * @return URL String to BII search with the user's query pasted in
	 *
	RequestMapping(value = "/search", method = RequestMethod.POST )
	public String zoeken (HttpServletRequest request) {
    	String redirect="redirect:"+urlBiiPrefixSearch+"?searchPattern="+request.getParameter("query");
    	return redirect;
	}
	*/
	

}

