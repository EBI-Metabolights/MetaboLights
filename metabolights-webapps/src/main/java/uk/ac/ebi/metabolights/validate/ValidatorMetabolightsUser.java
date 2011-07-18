package uk.ac.ebi.metabolights.validate;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.service.EmailService;

/**
 * Validator for validation of (form) submission of user account details.
 * The error codes refer to the properties file. 
 * @author markr
 */
public class ValidatorMetabolightsUser implements Validator {

	public final static int MIN_PASSWORD_LEN=6;
	//public final static int MIN_USERNAME_LEN=3;

	/**
	 * Validation for {@link uk.ac.ebi.metabolights.model.MetabolightsUser MetabolightsUser}, 
	 * looking at required fields, email being likely, minimum/maximum field lengths.
	 * 
	 */
	@Override
	public void validate(Object obj, Errors e) {
		
		MetabolightsUser user = (MetabolightsUser) obj;

		// Required fields
		// :( not so nice to hard code attribute names here as Strings.. ah, Joy of Spring.
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "firstName", "NotEmpty.metabolightsUser.firstName");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "lastName", "NotEmpty.metabolightsUser.lastName");
	    //ValidationUtils.rejectIfEmptyOrWhitespace(e, "userName", "NotEmpty.metabolightsUser.userName");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "dbPassword", "NotEmpty.metabolightsUser.dbPassword");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "userVerifyDbPassword", "NotEmpty.metabolightsUser.userVerifyDbPassword");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "email", "NotEmpty.metabolightsUser.email");

		// Check email valid pattern (TODO: better use regex)
		if (e.getFieldError("email")==null)
	    	if (!EmailService.isValidEmailAddress(user.getEmail()))
	    		e.rejectValue("email", "Email.metabolightsUser.email");

		if (e.getFieldError("dbPassword")==null) {
			// Check two provided passwords match (prevent typo)
			if (!(user.getDbPassword().equals(user.getUserVerifyDbPassword()))) 
				e.rejectValue("dbPassword", "Match.metabolightsUser.passwords");
			// Check minimum size password
			else if (user.getDbPassword().length()<MIN_PASSWORD_LEN) {
				e.rejectValue("dbPassword", "Size.metabolightsUser.password");
			}
		}
		//if (e.getFieldError("userName")==null) {
			// Check minimum size user name
		//	if (user.getUserName().length()<MIN_USERNAME_LEN) {
		//		e.rejectValue("userName", "Size.metabolightsUser.username");
		//	}
		//}
	}

	@Override
	public boolean supports(Class<?> c) {
		return MetabolightsUser.class.equals(c);
	}
}