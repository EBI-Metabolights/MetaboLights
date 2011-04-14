package uk.ac.ebi.metabolights.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Forwards the request for an entry to the BII inv website
 * http://stackoverflow.com/questions/1536863/spring-mvc-get-the-part-url-of-the-current-request-relative-to-the-controller
 * @author markr
 *
 */
@Controller
public class EntryController {

	@RequestMapping(value="/entry/{metabolightsId}")
	public String showEntry(@PathVariable("metabolightsId") String mtblId, Map<String, Object> map) {
		return "redirect:http://172.22.69.119:8080/bioinvindex/study.seam?studyId="+mtblId;
	}
} 
