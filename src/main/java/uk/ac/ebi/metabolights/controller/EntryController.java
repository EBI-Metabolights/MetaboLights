package uk.ac.ebi.metabolights.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.metabolights.properties.PropertyLookup;

/**
 * Forwards the request for an entry to the BII inv website
 * http://stackoverflow.com/questions/1536863/spring-mvc-get-the-part-url-of-the-current-request-relative-to-the-controller
 * @author markr
 *
 */
@Controller
public class EntryController {

	/*
	@RequestMapping(value="/entry/{metabolightsId}")
	public String showEntry(@PathVariable("metabolightsId") String mtblId, Map<String, Object> map) {
    	String biiUrlPrefix = PropertyLookup.getMessage("url.bii.prefix.study");
    	String redirect="redirect:"+biiUrlPrefix+"?studyId="+mtblId;
    	return redirect;
	}
	*/

	@RequestMapping(value="/entry={metabolightsId}")
	public ModelAndView  showEntry(@PathVariable("metabolightsId") String mtblId, Map<String, Object> map) {
    	String biiUrlPrefix = PropertyLookup.getMessage("url.bii.prefix.study");
    	String biiUrl=biiUrlPrefix+"?studyId="+mtblId;
		return new ModelAndView("entry", "biiUrl", biiUrl);
	}


} 
