/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 6/10/14 11:57 AM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.controller;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.authenticate.IsaTabAuthenticationProvider;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.EmailService;
import uk.ac.ebi.metabolights.service.TextUtils;
import uk.ac.ebi.metabolights.service.UserService;
import uk.ac.ebi.metabolights.validate.ValidatorMetabolightsUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Controller for user account handling (new account, update account).<br>
 * New accounts need to go through an approval process:
 * <UL>
 * <LI>User fills in a 'create account' form, this gets validated for all required fields</LI>
 * <LI>If valid, an account with status NEW is created</LI>
 * <LI>The user is sent an email to confirm, by clicking a unique URL</LI>
 * <LI>When confirmed, the account gets status USER_VERIFIED</LI>
 * <LI>MTBL admin is notified by an email sent to the admin-list which includes a private link to make the user ACTIVE</LI>
 * <LI>When the private link is clicked, the user becomes ACTIVE and is notified that his account is now usable</LI>
 * </UL>
 * @author markr
 *
 */
@Controller
public class UserAccountController extends AbstractController{

	private static Logger logger = LoggerFactory.getLogger(UserAccountController.class);
	private @Value("#{OrcidLinkServiceURL}") String orcidLinkServiceURL;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	/* Ensures the ValidatorMetabolightsUser is invoked to validate the input (@Valid further down) */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ValidatorMetabolightsUser());
	}

	/**
	 * Forward to the account creation page, with a blank MetabolightsUser.
	 */
	@RequestMapping(value = "/newAccount")
   	public ModelAndView newAccount() {
    	//ModelAndView mav = new ModelAndView("createAccount");
		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("createAccount");
    	mav.addObject(new MetabolightsUser());
    	mav.addObject("orcidLinkUrl",orcidLinkServiceURL);
    	return mav;
    }

	private @Value("#{urlConfirmNewAccount}") String confirmNewAccountUrl;


    /**
     * Handles creation of a new user account.<br>
     * Verify form input, check for clashes in database, store the
     * new account (status 'NEW') and let the user verify the request.
     *
     * @param metabolightsUser user details
     * @param result Binding result
     * @param model
     * @return where to navigate next
     */
	@RequestMapping(value = "/createNewAccount", method = RequestMethod.POST)
    public ModelAndView createNewAccount(@Valid MetabolightsUser metabolightsUser, BindingResult result, Model model, HttpServletRequest request) {

    	// The isatab schema works with a USERNAME. For Metabolights, we set the email address to be the user name,
    	// it's easier for people to remember that
    	metabolightsUser.setUserName(metabolightsUser.getEmail());

    	boolean duplicateEmailAddress=false;

    	if (TextUtils.textHasContent(metabolightsUser.getEmail()) && result.getFieldError("email")==null &&
    			emailExists (metabolightsUser.getEmail())) {
    		duplicateEmailAddress=true;
    	}

    	if (result.hasErrors()|| duplicateEmailAddress) {

    		//ModelAndView mav = new ModelAndView("createAccount");
    		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("createAccount");
        	mav.addObject(metabolightsUser);
			mav.addObject("orcidLinkUrl",orcidLinkServiceURL);
        	if (duplicateEmailAddress) {
        		mav.addObject("duplicateEmailAddress", PropertyLookup.getMessage("Duplicate.metabolightsUser.email"));
        	}
        	return mav;
        }


    	Long newUserId=null;

    	try {
			//Store the user information in the database, status NEW means still inactive (to be authorized).
			metabolightsUser.setStatus(MetabolightsUser.UserStatus.NEW); // make account non usable yet
			metabolightsUser.setDbPassword(IsaTabAuthenticationProvider.encode(metabolightsUser.getDbPassword()));
            metabolightsUser.setApiToken(UUID.randomUUID().toString());
			newUserId = userService.insert(metabolightsUser);

			//Send user a verification email
			String uniqueURLParameter=numericSequence(metabolightsUser.getDbPassword());
			String confirmationURL=confirmNewAccountUrl+"?usr="+ URLEncoder.encode(metabolightsUser.getUserName(),"UTF-8")+"&key="+uniqueURLParameter;
			emailService.sendConfirmNewAccountRequest(metabolightsUser.getEmail(),confirmationURL);

    	} catch (Exception ex ) {
			ex.printStackTrace();
			//On exception, user gets deleted again
			if (newUserId!=null)
				userService.delete(metabolightsUser);
			throw new RuntimeException(ex);
		}

    	//Let the user know what will happen next
    	//String emailShort=metabolightsUser.getEmail().substring(0,metabolightsUser.getEmail().indexOf('@'));

		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("user", metabolightsUser);
		httpSession.setAttribute("country",metabolightsUser.getListOfAllCountries().get(metabolightsUser.getAddress()));

    	return new ModelAndView("redirect:accountRequested");

    	//return new ModelAndView("redirect:accountRequested="+emailShort);

	}

    /**
     * Look email up in database.
     * @param newEmail
     * @return true if userName exists
     */
    private boolean emailExists (String newEmail) {
    	MetabolightsUser mtblUser = userService.lookupByEmail(newEmail);
		if (mtblUser != null) {
			return true;
		}
    	return false;
    }

	/**
	 * Converts a String to a numeric hash, to be used to create a safe confirmation URL without naughty characters.
	 * @param str input String
	 * @return a string with a lot of numbers, like "11348507875108577367104781199057111886965741201228210010966546961"
	 */
	private String numericSequence(String str) {
		StringBuffer numericHash=new StringBuffer();
		for (int i=0; i<str.length();i++) {
			int nmbr=(int)str.charAt(i);
			numericHash.append(nmbr);
		}
		return numericHash.toString();
	}


	/**
	 * For redirection after a user has filled in a new account form.
	 * @param request user
	 * @return ModelAndView
	 */
	@RequestMapping(value={"/accountRequested"})
	public ModelAndView accountRequested(HttpServletRequest request) {
		//ModelAndView mav = new ModelAndView("index"); // default action for this request, unless the session has candy in it.
		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("index"); // default action for this request, unless the session has candy in it.
    	MetabolightsUser newUser = (MetabolightsUser) request.getSession().getAttribute("user");

		if (newUser!=null){
			//mav = new ModelAndView("accountRequested");
			mav = AppContext.getMAVFactory().getFrontierMav("accountRequested");
			mav.addObject("user", newUser);
			request.getSession().removeAttribute("user");
		}

    	return mav;
	}


	private @Value("#{urlActivateNewAccount}") String activateNewAccountUrl;

	/**
	 * User has verified new account request by clicking a link sent by email. Update the status
	 * of the database row accordingly. Notify admin to activate thie user and provide them a link to do so.
	 *
	 * @param userName
	 * @param key
	 */
	@RequestMapping(value={"/confirmAccountRequest"}, method = RequestMethod.GET)
	public ModelAndView confirmAccountRequested(@RequestParam("usr") String userName, @RequestParam("key") String key) {
		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("index");
		MetabolightsUser user = userService.lookupByUserName(userName);
		if (user!=null && user.getStatus().equals(MetabolightsUser.UserStatus.NEW)
				&& numericSequence(user.getDbPassword()).equals(key)   ) {
			//Set user status to Verified
			user.setStatus(MetabolightsUser.UserStatus.VERIFIED);
			userService.update(user);
			mav.addObject("message", PropertyLookup.getMessage("msg.verifiedAccount")+" "+userName+".");

			//Notify mtbl admin
	    	String uniqueURLParameter=numericSequence(user.getDbPassword());
	    	String activationURL=activateNewAccountUrl+"?usrId="+user.getUserId() +"&key="+uniqueURLParameter;
			emailService.sendNewAccountAlert(user,activationURL);
		}
		return mav;
	}

	/**
	 * MTBL admin has verified the user can become active. Update the user status to Active, and
	 * let the user know by email.
	 * @param usrId
	 * @param key
	 */
	@RequestMapping(value={"/activateAccount__NotifyUser"}, method = RequestMethod.GET)
	public ModelAndView activateAccount(@RequestParam("usrId") long usrId, @RequestParam("key") String key) {

		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("index");

		MetabolightsUser user = userService.lookupById(usrId);
		if (user!=null && user.getStatus().equals(MetabolightsUser.UserStatus.VERIFIED)
				&& numericSequence(user.getDbPassword()).equals(key)   ) {
			user.setStatus(MetabolightsUser.UserStatus.ACTIVE);
			userService.update(user);
			mav.addObject("message", PropertyLookup.getMessage("msg.activatedAccount")+" "+user.getUserName()+".");
			emailService.sendAccountHasbeenActivated(user);
		}
		return mav;
	}

    /**
     * Look up current user details by using the Id stored in the principal.
     * @return Spring model and view
     */
	@RequestMapping(value = "/myAccount")
   	public ModelAndView myAccount() {

		ModelAndView mav = null;

		MetabolightsUser principal = ((MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
		if (principal==null) {
        	mav = AppContext.getMAVFactory().getFrontierMav("index"); // probably logged out
     	} else{
     		mav = getModelAndViewForUser(principal.getUserId());
     	}


    	return mav;
    }

    /**
     * Look up user details for the id requested as parameter.
     * @return Spring model and view
     */
	@RequestMapping(value = "/userAccount")
   	public ModelAndView userAccount(@RequestParam("usrId") long usrId) {

    	return getModelAndViewForUser(usrId);

    }
	/* Returns a modelANDView for the userID passed as parameter.*/
	private ModelAndView getModelAndViewForUser(long userId){

		ModelAndView mav = null;

		MetabolightsUser metabolightsUser = userService.lookupById(userId);

		if (metabolightsUser==null) {
        	mav = AppContext.getMAVFactory().getFrontierMav("index"); // Not found
        	mav.addObject("messsage", "User ID " + userId + " not found.");
        	return mav;
     	}

		mav = AppContext.getMAVFactory().getFrontierMav("updateAccount");
 		metabolightsUser.setUserVerifyDbPassword(metabolightsUser.getDbPassword());
    	mav.addObject(metabolightsUser);
		mav.addObject("orcidLinkUrl",orcidLinkServiceURL);
		logger.info("showing account details for "+metabolightsUser.getUserName()+" ID="+metabolightsUser.getUserId());
    	return mav;

	}





	/**
	 * Updates the user details in the database.
	 * @param metabolightsUser
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
    public ModelAndView updateAccount(@Valid MetabolightsUser metabolightsUser, BindingResult result, Model model, HttpServletRequest request) {

    	if (result.hasErrors()) {
        	ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("updateAccount");
        	mav.addObject(metabolightsUser);
			mav.addObject("orcidLinkUrl",orcidLinkServiceURL);
        	return mav;
        }

    	//Did the user enter a new password? If so, encode it..
    	MetabolightsUser userCheck = userService.lookupByUserName(metabolightsUser.getUserName());
    	if (!userCheck.getDbPassword().equals(metabolightsUser.getDbPassword())) {
    		logger.info("pasword was changed ... was "+userCheck.getDbPassword()+" is now "+metabolightsUser.getDbPassword());
        	metabolightsUser.setDbPassword(IsaTabAuthenticationProvider.encode(metabolightsUser.getDbPassword()));
            metabolightsUser.setApiToken(UUID.randomUUID().toString());
    	}

    	//Update the user information in the database
    	userService.update(metabolightsUser);

		HttpSession session = request.getSession();
		String pagename = (String) session.getAttribute("currentpage");
			// Get the current user...
			MetabolightsUser currentUser = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

			// If the user matches the account just been edited (now curators can edit accounts).
			if (currentUser.getUserId().equals(userCheck.getUserId())){
				//Update the current security context with new attributes
				try {
					BeanUtils.copyProperties(currentUser, metabolightsUser);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//Redirect to account confirmation page
				HttpSession httpSession = request.getSession();
				httpSession.setAttribute("user", metabolightsUser);
//				return new ModelAndView("redirect:update-success");
				if(pagename == null){
					return new ModelAndView("redirect:update-success");
				}
				return new ModelAndView("redirect:" + pagename);

				// Is a curator....go back to user list
			} else {
				return new ModelAndView("redirect:users");
			}
	}

	/**
	 * After succesful update.
	 */
	@RequestMapping({"/update-success"})
	public ModelAndView updateDone(HttpServletRequest request) {
	    //return new ModelAndView("index", "message", PropertyLookup.getMessage("msg.updatedAccount"));

		// default action for this request, unless the session has candy in it.
		ModelAndView mav = new ModelAndView ("redirect:index?message="+ PropertyLookup.getMessage("msg.updatedAccount"));
    	MetabolightsUser newUser = (MetabolightsUser) request.getSession().getAttribute("user");

		if (newUser!=null){
			mav = AppContext.getMAVFactory().getFrontierMav("accountRequested");
			mav.addObject("user", newUser);
			mav.addObject("updated","updated");  //Just to enable us to display a different text when requesting and updating the account
			mav.addObject("country",newUser.getListOfAllCountries().get(newUser.getAddress()));
			request.getSession().removeAttribute("user");
		}

    	return mav;
    }


}
