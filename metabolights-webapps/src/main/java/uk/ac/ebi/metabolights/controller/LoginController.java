package uk.ac.ebi.metabolights.controller;


import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.metabolights.form.EmailReminder;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.EmailService;
import uk.ac.ebi.metabolights.service.UserService;

/**
 * Controller for user authentication actions.  
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

 
	@RequestMapping(value = "/passwordReminder")
   	public ModelAndView passWordReminder() {
    	ModelAndView mav = new ModelAndView("reminder");
    	mav.addObject(new EmailReminder());
    	return mav;
    }

    /**
     * Handles a user request who forgot the password.<br> 
     * @param emailReminder user form
     * @param result binding result
     * @return where to navigate 
     */
    @RequestMapping(value = "/sendPasswordReminder", method = RequestMethod.POST)
    public ModelAndView sendPassWordReminder(@Valid EmailReminder emailReminder, BindingResult result, Model model) {
    	ModelAndView mav = new ModelAndView("reminder");
    	mav.addObject(emailReminder); 
        if (result.hasErrors()) {
            return mav;
        }
        //Find the user associated with the password
        MetabolightsUser mtblUser = userService.lookupByEmail(emailReminder.getEmailAddress());
		if (mtblUser == null) {
			mav.addObject("message", PropertyLookup.getMessage("msg.unknownEmail"));
			return mav;
		}
		else {
			//TODO

	    	/*
	    	// create number -> string (min length)
	    	Random r = new Random();
	    	String token = "";
	    	while (token.length()<40) {
	    		
	        	token+=Long.toString(Math.abs(r.nextLong()), 20);
			}
	     	System.out.println(token+" "+token.length());
	    	
	     	//store in spring bean (singleton) MAP
	     	//send url with that token and username and a timeout
	     	//wait for reply
	     	// request comes in
	     	// clean old shit in map
	     	// find the url .. not cleaned up?
	     	// none found -> inv URL bye
	     	// found -> get username, load up bean, log user in remove from MAP
	     	// forward to let user change details, check same (re-type password) update in database (hibornate)
	     	// or user can ignore that 
	     	// update 
	     	*/

			emailService.sendPasswordReminder(emailReminder.getEmailAddress(), "TODO");
			return new ModelAndView("index", "message", PropertyLookup.getMessage("msg.pwsent",emailReminder.getEmailAddress()));
		}
    }

    
	@RequestMapping(value = "/newAccount")
   	public ModelAndView newAccount() {
    	ModelAndView mav = new ModelAndView("createAccount");
    	mav.addObject(new MetabolightsUser());
    	return mav;
    }
	
    /**
     * Handles creation of a new user account.<br> 
     * @param emailReminder user form
     * @param result binding result
     * @return where to navigate 
     */
    @RequestMapping(value = "/createNewAccount", method = RequestMethod.POST)
    public ModelAndView createNewAccount(@Valid MetabolightsUser metabolightsUser, BindingResult result, Model model) {

    	//TODO ajax lookup of username.... for now, test it here. catch any db exceptions really. UQ, weird chars etc
    	
    	if (result.hasErrors()) {
        	ModelAndView mav = new ModelAndView("createAccount");
        	mav.addObject(metabolightsUser);
        	return mav;
        }
        
    	//stick in database
        System.out.println("database transaction");
        
        //send an email
        System.out.println("send email ");
        
        //TODO - make 'temp' account?
        //TODO - UK username catch (should be Ajaxed)
        
        //set message

        //redirect to index
        
        return new ModelAndView("redirect:index"); // TODO!
    }
     	
}

