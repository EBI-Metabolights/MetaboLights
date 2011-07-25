package uk.ac.ebi.metabolights.form;

import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/*
 * Class for the contact us form.  Validation and transport of different fields
 */

public class ContactValidation{
	
	public ContactValidation(){}
	
	@NotNull
    @NotEmpty
 	private String firstName;
	
	@NotNull
    @NotEmpty
 	private String lastName;

	@NotNull
	@Email
    @NotEmpty
 	private String emailAddress;
	
 	private String affiliation;
 	private String affiliationUrl;
	
	@NotNull
    @NotEmpty
    @Size(min=10, max=512)  
	private String message;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getAffiliationUrl() {
		return affiliationUrl;
	}

	public void setAffiliationUrl(String affiliationUrl) {
		this.affiliationUrl = affiliationUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
