package uk.ac.ebi.metabolights.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.bioinvindex.search.hibernatesearch.StudyBrowseField;
import uk.ac.ebi.metabolights.search.Filter;
import uk.ac.ebi.metabolights.search.FilterItem;
import uk.ac.ebi.metabolights.search.LuceneSearchResult;
import uk.ac.ebi.metabolights.search.LuceneSearchResult.Assay;
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
		List<LuceneSearchResult> resultSet = new ArrayList<LuceneSearchResult>();
	   
		//Instantiate a filter class
		Filter filter = new Filter(request);
		
		String luceneQuery = filter.getLuceneQuery();
			    
		try {
			logger.info("searching for "+ luceneQuery);
			resultSet = searchService.search(luceneQuery);
			logger.debug("Found #results = "+resultSet.size());
			
			//Load filter with unique data items
			filter.loadFilter(resultSet);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		ModelAndView mav = new ModelAndView("searchResult");
    	mav.addObject("searchResults", resultSet);
       	mav.addObject("filters", filter);
       	mav.addObject("freeTextQuery", filter.getFreeTextQuery());
       	if (!filter.getFreeTextQuery().isEmpty())
    		mav.addObject("userQueryClean", filter.getFreeTextQuery().replaceAll("\\*", "").replaceAll("\\%", ""));

    	return mav;
	}


}

