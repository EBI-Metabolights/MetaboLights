package uk.ac.ebi.metabolights.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.search.Filter;
import uk.ac.ebi.metabolights.search.LuceneSearchResult;
import uk.ac.ebi.metabolights.service.CountryService;
import uk.ac.ebi.metabolights.service.SearchService;

/**
 * Controller for Metabolights searching.  
 * @author markr
 */
@Controller
public class SearchController extends AbstractController{
	
	private static Logger logger = Logger.getLogger(SearchController.class);
	
	@Autowired
	private SearchService searchService;
	

	/**
	 * Controller for a search request, including possible filters. 
	 * @param request
	 */
	@RequestMapping(value = "/search", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView luceneSearch (HttpServletRequest request) {

		//Prepare the filter
		Filter filter = prepareFilter(request);
				
		//Trigger the search based on the filter
		ModelAndView mav = search(filter);
		
		//Add the action to the ModelAndView
		mav.addObject("action", "search");

		
		return mav;
	}


	private ModelAndView search(Filter filter) {

		//Search results
		List<LuceneSearchResult> totalResultList = new ArrayList<LuceneSearchResult>();
		HashMap<Integer, List<LuceneSearchResult>> searchResultHash = new HashMap<Integer, List<LuceneSearchResult>>(); // Number of documents and the result list found
		List<LuceneSearchResult> displayedResultList = new ArrayList<LuceneSearchResult>();
		Integer totalHits = 0;
	  
				
		//Get the query...	
		String luceneQuery = filter.getFreeTextQuery();
		
		try {
			logger.info("Searching for "+ luceneQuery);
			
			searchResultHash = searchService.search(filter.getLuceneQuery()); 
			
			totalHits = searchResultHash.entrySet().iterator().next().getKey(); //Number of documents found in the search, reported by Lucene
			totalResultList = searchResultHash.entrySet().iterator().next().getValue(); //Search results
	
			logger.debug("Found #results = "+totalResultList.size());
			
			//Load filter with unique data items
			filter.loadFilter(totalResultList);

			//Make a result set to actually display on the page
			int startIdxDisplay=(filter.getPageNumber()-1)*filter.getPageSize();
		    int endIdxDisplay=(filter.getPageNumber())*filter.getPageSize();
		    for (int i = startIdxDisplay; i < endIdxDisplay; i++) {
		    	if (i< totalResultList.size())
		    		displayedResultList.add(totalResultList.get(i));
			}
		    
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		ModelAndView mav = new ModelAndView("searchResult");
    	mav.addObject("searchResults", displayedResultList);
       	mav.addObject("filters", filter);
       	mav.addObject("freeTextQuery", filter.getFreeTextQuery());
       	mav.addObject("pageNumber", filter.getPageNumber());
       	mav.addObject("pageSize", filter.getPageSize());
       	mav.addObject("totalHits", totalHits);
       	
       	int[] pagingBoundaries=searchService.getPagingRange(filter.getPageSize(), totalHits, filter.getPageNumber());
       	mav.addObject("pagerLeft", pagingBoundaries[0]);
       	mav.addObject("pagerRight", pagingBoundaries[1]);
       	mav.addObject("totalNumberOfPages", pagingBoundaries[2]);

       	if (!filter.getFreeTextQuery().isEmpty())
    		mav.addObject("userQueryClean", filter.getFreeTextQuery().replaceAll("\\*", "").replaceAll("\\%", ""));
    	
    	return mav;
	}


	private Filter prepareFilter(HttpServletRequest request){
		final String FILTER_SESSION_ATRIBUTE = "filter";
		
		//Get the filter object from the session
		Filter filter = (Filter)request.getSession().getAttribute(FILTER_SESSION_ATRIBUTE);
		
		//If we have to reset the filters....
		// 1.- First time visit --> filter is null
		// 2.- new search term...
		if (filter == null){
			//Instantiate a filter class
			filter = new Filter(request);
			
			//Add it to the session
			request.getSession().setAttribute( FILTER_SESSION_ATRIBUTE, filter);
			
		}else{
			//We already have a filter object that was stored in the session. We need to refresh it with the filter items checked or unchecked.
			filter.parseRequest(request);
		}
		
		return filter;
		
	}
	@RequestMapping(value = "/mysubmissions")
	public ModelAndView MySubmissionsSearch (HttpServletRequest request) {
		
		//Get the filter object prepared (inside there is a clean up process that revoves any initial filter)
		Filter filter = prepareFilter(request);
		
		//As this page requires authentication we can be sure there is an user
		MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		
		//Set the filter to private studies
		filter.getFss().get("mystudies").getFilterItems().get(user.getUserName()).setIsChecked(true);
		
		//Get the ModelAndView
		ModelAndView mav = search(filter);
		
		//Add the action to the ModelAndView
		mav.addObject("action", "mysubmissions");
		
		//Add the message to the user
		String welcomeMessage;
		
		//If he doesn't have any study
		if (filter.getInitialHits() ==0){
			
			welcomeMessage = PropertyLookup.getMessage("msg.welcomeWithoutStudies",
					user.getFirstName());
			
		}else{
			welcomeMessage =  PropertyLookup.getMessage("msg.welcomeWithStudies",
					user.getFirstName(), Integer.toString(filter.getInitialHits()));
		}
		
		// Add the message to the response
		mav.addObject("welcomemessage", welcomeMessage);
			
		return mav;
	}
}

