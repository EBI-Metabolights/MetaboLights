package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.form.ContactValidation;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.EmailService;
import uk.ac.ebi.metabolights.validate.ValidateContactUsForm;

import javax.validation.Valid;

@Controller
public class ContactUsController extends AbstractController{
	
	private static Logger logger = Logger.getLogger(ContactUsController.class);
	
	@Autowired
	private EmailService emailService;
	
	/* Ensures the ValidatorMetabolightsUser is invoked to validate the input (@Valid further down) */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ValidateContactUsForm());
	}

    @RequestMapping(value = "/contact")
    public ModelAndView contact() {
    	//return new ModelAndView("contact","contactValidation", new ContactValidation());  
    	return AppContext.getMAVFactory().getFrontierMav("contact","contactValidation", new ContactValidation());
    	
    }
	
	 /**
     * Sends an email to MetaboLights, for general questions.<br> 
     * @param contactValidation user form
     * @param result binding result
     */
    @RequestMapping(value = "/contactUsAlert", method = RequestMethod.POST)
    public ModelAndView contactUs(@Valid ContactValidation contactValidation, BindingResult result) {

        if (result.hasErrors()) {
            //return new ModelAndView("contact","contactValidation", contactValidation);
        	return AppContext.getMAVFactory().getFrontierMav("contact","contactValidation", contactValidation);
        }
        
        logger.info("Sending 'Contact Us' email from " + contactValidation.getEmailAddress());
        
		// Send new the email
		emailService.sendContactUsAlert(contactValidation);
		//return new ModelAndView("index", "message", PropertyLookup.getMessage("msg.emailSent",contactValidation.getEmailAddress()));
		return new ModelAndView ("redirect:index?message="+ PropertyLookup.getMessage("msg.emailSent",contactValidation.getEmailAddress()));

    }

}
