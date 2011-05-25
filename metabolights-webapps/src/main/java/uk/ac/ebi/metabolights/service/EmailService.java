package uk.ac.ebi.metabolights.service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;

/**
 * Uses a central Spring interface for sending emails (the MailSender interface). 
 * A simple value object encapsulating the properties of a simple mail such as from and to (plus many others) is the SimpleMailMessage class.
 * @author markr
 */
@Service
public class EmailService {

	@Autowired
	private MailSender mailSender; // configured in servlet XML
	@Autowired
	private SimpleMailMessage reminderTemplate; // template for password reminder, configured in servlet XML

	@Autowired
	private SimpleMailMessage newAccountTemplate; // template for password reminder, configured in servlet XML

	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setReminderTemplate(SimpleMailMessage templateMessage) {
		this.reminderTemplate = templateMessage;
	}

	public void sendResetPassword (String emailAddress, String resetPassword, String userName) {
		SimpleMailMessage msg = new SimpleMailMessage(this.reminderTemplate);
		String body = PropertyLookup.getMessage("msg.passwordResetNotification", userName, resetPassword);
		msg.setTo(emailAddress);
		msg.setText(body);
		this.mailSender.send(msg);
	}

	public void sendNewAccountAlert (MetabolightsUser usr) {
		SimpleMailMessage msg = new SimpleMailMessage(this.newAccountTemplate);
		String body = PropertyLookup.getMessage("msg.accountRequest",
				usr.getFirstName(),usr.getLastName(),usr.getUserName(),usr.getUserId()+"",
				usr.getEmail(),usr.getAffiliation(),CountryService.lookupCountry(usr.getAddress()));
		msg.setText(body);
		this.mailSender.send(msg);
	}


	/**
	 * For use by a Sping Validator, to check if an email looks likely.
	 * @param aEmailAddress
	 */
	public static boolean isValidEmailAddress(String aEmailAddress){
		if (aEmailAddress == null) return false;
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(aEmailAddress);
			if ( ! hasNameAndDomain(aEmailAddress) ) {
				result = false;
			}
		}
		catch (AddressException ex){
			result = false;
		}
		return result;
	}

	/**
	 * Checks for name and domain in email address.
	 * @param aEmailAddress
	 */
	private static boolean hasNameAndDomain(String aEmailAddress){
		String[] tokens = aEmailAddress.split("@");
		return 
		tokens.length == 2 &&
		TextUtils.textHasContent( tokens[0] ) && 
		TextUtils.textHasContent( tokens[1] ) ;
	}



}
