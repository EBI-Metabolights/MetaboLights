package uk.ac.ebi.metabolights.controller;


import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.metabolights.form.EmailAddress;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.EmailService;
import uk.ac.ebi.metabolights.service.UserService;
import uk.ac.ebi.metabolights.validate.ValidatorMetabolightsUser;

/**
 * Controller for login and related actions.  
 * @author markr
 *
 */
@Controller
public class LoginController extends AbstractController{

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;

	
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

 
	@RequestMapping(value = "/forgotPassword")
   	public ModelAndView passWordReset() {
    	ModelAndView mav = new ModelAndView("forgotPassword");
    	mav.addObject(new EmailAddress());
    	return mav;
    }

    /**
     * Handles a user request who forgot the password.<br> 
     * @param email user form
     * @param result binding result
     * @return where to navigate 
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ModelAndView resetPassWord(@Valid EmailAddress email, BindingResult result, Model model) {
    	ModelAndView mav = new ModelAndView("forgotPassword");
    	mav.addObject(email); 
        if (result.hasErrors()) {
            return mav;
        }
        //Find the user associated with the password
        MetabolightsUser mtblUser = userService.lookupByEmail(email.getEmailAddress());
		if (mtblUser == null) {
			mav.addObject("message", PropertyLookup.getMessage("msg.unknownEmail"));
			return mav;
		}
		else {
			// Get some new password
			String tempPassword = getTemporaryPassword ();
			mtblUser.setDbPassword(tempPassword);
			
			// Update database user details
			userService.update(mtblUser);

			// Send new password to user, tell him to update the temp password
			emailService.sendResetPassword(email.getEmailAddress(), tempPassword, mtblUser.getUserName());

			//TODO = redirect, work out url?x=y
			return new ModelAndView("index", "message", PropertyLookup.getMessage("msg.pwsent",email.getEmailAddress()));
		}
    }


    private String getTemporaryPassword () {
    	String tempPw="";
    	do {
	    	String foo = UUID.randomUUID().toString();
	    	foo=foo.replaceAll("\\-", "");
	    	foo=foo.substring(0,6);
	    	tempPw+=foo;
    	} while (tempPw.length()<ValidatorMetabolightsUser.MIN_PASSWORD_LEN);
    	return tempPw;
	}
     	
}

