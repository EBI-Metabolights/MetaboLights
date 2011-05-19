package uk.ac.ebi.metabolights.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Email reminder (corresponding to a web form) as requested by the user.<br>
 * Note that validation messages in messagesXXX.properties are depending on the
 * attribute names here, so keep that in mind if you are to change a attribute name.<br>
 * Example of such a property:<br>
 * NotEmpty.emailReminder.emailAddress=Email address must not be empty.
 * 
 * @author markr
 * 
 */
public class EmailReminder {

	public EmailReminder(){	}

	@NotNull
	@Email
    @NotEmpty
  //@Size(min=5,max =255)  
	private String emailAddress;

	public void setEmailAddress(String email) {
		this.emailAddress = email;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

}