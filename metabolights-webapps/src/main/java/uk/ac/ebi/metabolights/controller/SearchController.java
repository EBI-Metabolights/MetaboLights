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
import uk.ac.ebi.metabolights.search.Filter;
import uk.ac.ebi.metabolights.search.LuceneSearchResult;
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

		//Search results
		List<LuceneSearchResult> totalResultList = new ArrayList<LuceneSearchResult>();
		HashMap<Integer, List<LuceneSearchResult>> searchResultHash = new HashMap<Integer, List<LuceneSearchResult>>(); // Number of documents and the result list found
		List<LuceneSearchResult> displayedResultList = new ArrayList<LuceneSearchResult>();
		Integer totalHits = 0;
	   
		String userName = "";
		
		//If there is any user...
		if ( SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof MetabolightsUser){
		
			//Get the user
			MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		
			userName =user.getUserName();
		}
		
		//Instantiate a filter class
		Filter filter = new Filter(request, userName);

		String luceneQuery = filter.getLuceneQuery();
			    
		try {
			logger.info("Searching for "+ luceneQuery);
			
			searchResultHash = searchService.search(luceneQuery); 
			
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
       	
       	int[] pagingBoundaries=getPagingRange(filter.getPageSize(), totalHits, filter.getPageNumber());
       	mav.addObject("pagerLeft", pagingBoundaries[0]);
       	mav.addObject("pagerRight", pagingBoundaries[1]);
       	mav.addObject("totalNumberOfPages", pagingBoundaries[2]);

       	if (!filter.getFreeTextQuery().isEmpty())
    		mav.addObject("userQueryClean", filter.getFreeTextQuery().replaceAll("\\*", "").replaceAll("\\%", ""));
    	
    	return mav;
	}


	/**
	 * Calculates values for page links to page through search results, used for display in JSP.<br>
	 * Example: there are 304 results, we use a display page size of 10. So there are 31 pages to show in total.
	 * We keep track of the current page, and with this function define a page range around that.<br>
	 * If the user were to be on page 14, and we show a maximum of 10 other pages to go to, we'd want a result
	 * here of [9 10 11 12 13 14 15 16 17 18 19] as page-links to click. The is conform Google pagin style, you limit
	 * the span because if you have a large amount of pages, you don't show them all but only the ones close by. 
	 * 
	 * @param pageSize
	 * @param totalHits
	 * @param currentPage
	 * @return {l,r,t} with l being the left most page number of the pager, r the right, t total number of pages
	 */
	private int[] getPagingRange (double pageSize, double totalHits, int currentPage) {

		// how many links should be displayed max
		int maxPagerSpan=10;
		
		int totalNumberOfPages = (int)(Math.ceil(totalHits/pageSize));
		int left=currentPage, right=currentPage;

		calc_span:
		for (int i = 0; i < maxPagerSpan-1; ) {
			int incr=0;
			if(left-1 >=1) {
				left--;
				incr++;
			}
			if(right+1<=totalNumberOfPages) {
				right++;
				incr++;
			}
			if (incr==0)
				break calc_span;
			else
				i+=incr;
		}
		int[] pagerBoundaries = {left,right,totalNumberOfPages};
		return pagerBoundaries; 
	}
	
}

