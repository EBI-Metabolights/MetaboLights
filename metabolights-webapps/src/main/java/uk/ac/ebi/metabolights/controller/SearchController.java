package uk.ac.ebi.metabolights.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
	
	@Autowired
	private SearchService searchService;

	
	private @Value("#{appProperties.urlBiiPrefixSearch}") String urlBiiPrefixSearch;
	
	@RequestMapping(value = "/search", method = RequestMethod.POST )
	public ModelAndView  luceneSearch (HttpServletRequest request) {

	    List<LuceneSearchResult> resultSet = new ArrayList<LuceneSearchResult>();
		try {
			
			resultSet = searchService.search(request.getParameter("query"));
			System.out.println("++++++++ we found #results : "+resultSet.size());
			System.out.println("the directory is "+searchService.getIndexDirectory());
			
		} catch (Exception e) {
			e.printStackTrace();
			//TODO - handle various shit here
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
	 */
	/*
	RequestMapping(value = "/search", method = RequestMethod.POST )
	public String zoeken (HttpServletRequest request) {
		try {
			
			List<LuceneSearchResult> resultSet = searchService.search(request.getParameter("query"));
			System.out.println("++++++++ we found #results : "+resultSet.size());
			System.out.println("the directory is "+searchService.getIndexDirectory());
			
		} catch (Exception e) {
			e.printStackTrace();
			//TODO - handle shit here
		}
		
    	String redirect="redirect:"+urlBiiPrefixSearch+"?searchPattern="+request.getParameter("query");
    	return redirect;
	}
	*/

	

}

