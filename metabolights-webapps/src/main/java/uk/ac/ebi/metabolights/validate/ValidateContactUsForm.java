/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/24/12 12:40 PM
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

package uk.ac.ebi.metabolights.validate;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import uk.ac.ebi.metabolights.form.ContactValidation;
import uk.ac.ebi.metabolights.service.EmailService;

public class ValidateContactUsForm implements Validator {

	/**
	 * Validation for {@link uk.ac.ebi.metabolights.form.ContactValidation ContactValidation}, 
	 * looking at required fields, email being likely, minimum/maximum field lengths.
	 */
	@Override
	public boolean supports(Class<?> c) {
		return ContactValidation.class.equals(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ContactValidation user = (ContactValidation) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.metabolightsUser.firstName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.metabolightsUser.lastName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "NotEmpty.metabolightsUser.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "NotEmpty.concatUsForm.message");
		
		if (errors.getFieldError("emailAddress")==null)
	    	if (!EmailService.isValidEmailAddress(user.getEmailAddress()))
	    		errors.rejectValue("emailAddress", "Email.metabolightsUser.email");
		
	}

}
