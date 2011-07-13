package uk.ac.ebi.metabolights.controller;

import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import uk.ac.ebi.bioinvindex.model.term.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.bioinvindex.model.AssayResult;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.metabolights.service.StudyService;

/**
 * Controller for entry (=study) details.
 * 
 */
@Controller
public class EntryController extends AbstractController {

	private static Logger logger = Logger.getLogger(EntryController.class);

	@Autowired
	private StudyService studyService;

	//(value = "/entry/{metabolightsId}")
	@RequestMapping(value = "/{metabolightsId}") 
	public ModelAndView showEntry(@PathVariable("metabolightsId") String mtblId, Map<String, Object> map) {
		logger.info("requested entry " + mtblId);
		Study study = studyService.getBiiStudy(mtblId);
		
		Collection<String> organismNames = new TreeSet<String>();
		for (AssayResult assRes : study.getAssayResults()) {
			for (PropertyValue<?> pv : assRes.getCascadedPropertyValues()) {
				if (pv.getType().getValue().equals("organism")) {
					organismNames.add(pv.getType().getValue()+":"+pv.getValue());
					logger.debug("adding "+pv.getValue());
				}
			}
		}
		
		ModelAndView mav = new ModelAndView("entry"); ;
		mav.addObject("study", study);
		mav.addObject("organismNames", organismNames);

		return mav;
	}


}
