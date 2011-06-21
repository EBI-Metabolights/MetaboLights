package uk.ac.ebi.metabolights.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.metabolights.search.LuceneSearchResult;
import uk.ac.ebi.metabolights.service.SearchService;

/**
 * Forwards the request for an entry to the BII inv website
 * http://stackoverflow.com/questions/1536863/spring-mvc-get-the-part-url-of-the-current-request-relative-to-the-controller
 * 
 * @author markr
 * 
 */
@Controller
public class EntryController extends AbstractController {

	private static Logger logger = Logger.getLogger(EntryController.class);

	private List<LuceneSearchResult> luceneDocs = new ArrayList<LuceneSearchResult>();
	
	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/entry={metabolightsId}")
	public ModelAndView showEntry(@PathVariable("metabolightsId") String mtblId, Map<String, Object> map) {
		logger.info("requested entry " + mtblId);

		try {
			logger.info("Retrieving lucene entry for Study " + mtblId);
			luceneDocs = searchService.search(mtblId); // TODO, should be populated and unique				
		} catch (Exception e) {
			e.printStackTrace();
		}

		String biiUrl = "/entry=" + mtblId;
		ModelAndView mav = new ModelAndView("entry", "biiUrl", biiUrl);
		mav.addObject("document", luceneDocs.get(0));
		return mav;
	}

}
