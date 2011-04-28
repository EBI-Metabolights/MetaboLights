package uk.ac.ebi.metabolights.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.metabolights.properties.PropertyLookup;

/**
 * Controller for user log in actions.  
 * @author markr
 *
 */
@Controller
public class LoginController extends AbstractController{

	@RequestMapping({"/login-success"})
	public ModelAndView loggedIn() {
	    return new ModelAndView("index", "message", PropertyLookup.getMessage("msg.loggedIn"));
    }

	@RequestMapping({"/loggedout"})
	public ModelAndView loggedOut() {
	    return new ModelAndView("index", "message", PropertyLookup.getMessage("msg.loggedOut"));
    }

	@RequestMapping(value={"/login", "/timeout"})
	public String resolveRequest (HttpServletRequest request) {
		return GenericController.lastPartOfUrl(request);
	}


}

