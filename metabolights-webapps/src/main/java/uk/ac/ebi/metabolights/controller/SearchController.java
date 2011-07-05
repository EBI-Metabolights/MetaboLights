package uk.ac.ebi.metabolights.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	
	private @Value("#{appProperties.urlBiiPrefixSearch}") String urlBiiPrefixSearch;
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/search", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView luceneSearch (HttpServletRequest request) {

	    List<LuceneSearchResult> resultSet = new ArrayList<LuceneSearchResult>();

	    List<String> organisms = new ArrayList<String>(); 
	    List<String> technology = new ArrayList<String>();
	    
	    //HashMap<String, Set> filters = new HashMap();
	    
	    //Get the user free text search
	    String query = request.getParameter("query")!=null? request.getParameter("query"): "";   
	    
		try {
			logger.info("searching for "+query);
			resultSet = searchService.search(query);
			logger.debug("Found #results = "+resultSet.size());
			
			Iterator iter = resultSet.iterator();
			while (iter.hasNext()){ //Is there a better Lucene way of doing this?  Getting unique values from the index?
				LuceneSearchResult result = (LuceneSearchResult) iter.next();
				
				if (result.getOrganism()!=null && !organisms.contains(result.getOrganism())) //Add unique entries to the list
					organisms.add(result.getOrganism());

				
				//Get the list of technologies..
				Iterator <Assay> assIter = result.getAssays().iterator();
				while (assIter.hasNext()){
					Assay assay = (Assay) assIter.next();
					if (!technology.contains(assay.getTechnology()))
						technology.add(assay.getTechnology()); 
				}
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	ModelAndView mav = new ModelAndView("searchResult");
    	mav.addObject("searchResults", resultSet);
    	mav.addObject("userQuery", query);
    	if (!query.isEmpty())
    		mav.addObject("userQueryClean", query.replaceAll("\\*", "")); //TODO, % as well
    	if (organisms.size()>1)
    		mav.addObject("organisms", organisms);
    	if (technology.size()>1)
    		mav.addObject("technology", technology);
    	
    	
    	return mav;
	}
	

}

