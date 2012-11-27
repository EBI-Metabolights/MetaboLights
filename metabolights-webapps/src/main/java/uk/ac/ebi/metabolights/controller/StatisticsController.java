package uk.ac.ebi.metabolights.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.metabolights.service.AppContext;

/**
 * Controller for login and related actions.  
 * @author Pablo
 *
 */
@Controller
public class StatisticsController extends AbstractController{


	
	@RequestMapping({"/statistics","/stats"})
	public ModelAndView showStatistics() {
	    
		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav ("statistics"); //name of jsp page must be same as this
		return mav;    
    }
}