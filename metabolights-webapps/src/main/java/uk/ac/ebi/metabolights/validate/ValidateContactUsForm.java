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
