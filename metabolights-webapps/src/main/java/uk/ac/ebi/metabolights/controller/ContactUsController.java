/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 8/5/13 5:20 PM
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static Logger logger = LoggerFactory.getLogger(ContactUsController.class);
	
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

    @RequestMapping(value = "/acknowledgements")
    public ModelAndView acknowledgements() {
        return AppContext.getMAVFactory().getFrontierMav("acknowledgements");
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
