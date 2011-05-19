package uk.ac.ebi.metabolights.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

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

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setReminderTemplate(SimpleMailMessage templateMessage) {
		this.reminderTemplate = templateMessage;
	}

	public void sendPasswordReminder (String emailAddress, String body) {
		SimpleMailMessage msg = new SimpleMailMessage(this.reminderTemplate);
		msg.setTo(emailAddress);
		msg.setText(body);
		this.mailSender.send(msg);
	}
}
