package uk.ac.ebi.metabolights.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.metabolights.authenticate.IsaTabAuthenticationProvider;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.CountryService;
import uk.ac.ebi.metabolights.service.EmailService;
import uk.ac.ebi.metabolights.service.TextUtils;
import uk.ac.ebi.metabolights.service.UserService;
import uk.ac.ebi.metabolights.validate.ValidatorMetabolightsUser;

/**
 * Controller for user account handling (new account, update account).  
 * @author markr
 *
 */
@Controller
public class UserAccountController extends AbstractController{

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	/* Ensures the ValidatorMetabolightsUser is invoked to validate the input (@Valid further down) */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ValidatorMetabolightsUser());
	}
	
	@RequestMapping(value = "/newAccount")
   	public ModelAndView newAccount() {
    	ModelAndView mav = new ModelAndView("createAccount");
    	mav.addObject(new MetabolightsUser());
    	return mav;
    }
	
	private @Value("#{appProperties.mtblAdminEmailAddress}") String mtblAdminEmailAddress;

    /**
     * Handles creation of a new user account.<br>
     * TODO Ajax username lookup.
     *  
     * @param MetabolightsUser user details
     * @param result Binding result
     * @param model 
     * @return where to navigate next 
     */
	@RequestMapping(value = "/createNewAccount", method = RequestMethod.POST)
    public ModelAndView createNewAccount(@Valid MetabolightsUser metabolightsUser, BindingResult result, Model model) {

    	boolean duplicateUser=false;
    	if (TextUtils.textHasContent(metabolightsUser.getUserName()) && result.getFieldError("userName")==null && 
    			userNameExists (metabolightsUser.getUserName())) {
    		duplicateUser=true;
    	}
    	
    	if (result.hasErrors()|| duplicateUser) {
        	ModelAndView mav = new ModelAndView("createAccount");
        	mav.addObject(metabolightsUser);
        	if (duplicateUser) {
        		// .. It should be possible to set the message through the object error but I didn't manage
        		//ObjectError duplicateUserError = new ObjectError("metabolightsUser", new String() {".."});
        		mav.addObject("dupUserMessage", PropertyLookup.getMessage("Duplicate.metabolightsUser.userName"));
        	}
       		return mav;
        }

    	//Store the user information in the database, status NEW means still inactive (to be authorized).
    	metabolightsUser.setStatus(MetabolightsUser.userStatus.NEW.toString()); // make account non usable yet
    	metabolightsUser.setUserName(metabolightsUser.getUserName().toLowerCase());
    	metabolightsUser.setDbPassword(IsaTabAuthenticationProvider.encode(metabolightsUser.getDbPassword()));

    	Long newId = userService.insert(metabolightsUser);
    	    	
        //Send an email to MTBL admin to review the user account request
		emailService.sendNewAccountAlert(metabolightsUser);

		//Let user know what will happen next
    	return new ModelAndView("redirect:accountRequested="+metabolightsUser.getUserName());

	}

	@RequestMapping(value={"/accountRequested={userName}"})
	public ModelAndView acountRequested(@PathVariable("userName") String userName, Map<String, Object> map) {
    	ModelAndView mav = new ModelAndView("accountRequested");
    	mav.addObject("userName", userName);
    	return mav;
	}

	
    /**
     * Look userName up in database. 
     * @param newUserName
     * @return true if userName exists
     */
    private boolean userNameExists (String newUserName) {
        System.out.println("db look up for user");
    	MetabolightsUser mtblUser = userService.lookupByUserName(newUserName);
		if (mtblUser != null) {
			return true;
		}
    	return false;
    }
}
