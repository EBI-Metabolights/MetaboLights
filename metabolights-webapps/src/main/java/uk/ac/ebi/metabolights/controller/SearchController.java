package uk.ac.ebi.metabolights.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.metabolights.search.Filter;
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
	

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/search", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView luceneSearch (HttpServletRequest request) {

		//Search results
		List<LuceneSearchResult> resultList = new ArrayList<LuceneSearchResult>();
		HashMap<Integer, List<LuceneSearchResult>> searchResultHash = new HashMap<Integer, List<LuceneSearchResult>>(); // Number of documents and the result list found
		Integer totalHits = 0;	//Number of documents found in the search
	   
		//Instantiate a filter class
		Filter filter = new Filter(request);
		
		String luceneQuery = filter.getLuceneQuery();
			    
		try {
			logger.info("searching for "+ luceneQuery);
			
			searchResultHash = searchService.search(luceneQuery); //Total hits, searchResults
			totalHits = searchResultHash.entrySet().iterator().next().getKey(); //Number of hist reported by Lucene
			resultList = searchResultHash.entrySet().iterator().next().getValue(); //Search results
	
			logger.debug("Found #results = "+resultList.size());
			
			//Load filter with unique data items
			filter.loadFilter(resultList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		ModelAndView mav = new ModelAndView("searchResult");
    	mav.addObject("searchResults", resultList);
       	mav.addObject("filters", filter);
       	mav.addObject("freeTextQuery", filter.getFreeTextQuery());
    	mav.addObject("totalHits", totalHits);
       	if (!filter.getFreeTextQuery().isEmpty())
    		mav.addObject("userQueryClean", filter.getFreeTextQuery().replaceAll("\\*", "").replaceAll("\\%", ""));
    	
    	return mav;
	}


}

