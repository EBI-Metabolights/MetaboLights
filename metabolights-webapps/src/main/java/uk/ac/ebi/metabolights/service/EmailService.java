package uk.ac.ebi.metabolights.service;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.mchange.v2.codegen.bean.Property;

import uk.ac.ebi.metabolights.form.ContactValidation;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.utils.PropertiesUtil;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

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
	private SimpleMailMessage verifyNewAccountTemplate; // template for confirmation of an account request

	@Autowired
	private SimpleMailMessage activateAccountTemplate; // template for password reminder, configured in servlet XML

	@Autowired
	private SimpleMailMessage accountApprovedTemplate; // template for notification that account is active
	
	@Autowired
	private SimpleMailMessage contactUsTemplate; // template for general website requests

    @Autowired
    private SimpleMailMessage studyPublicTemplate; // template to notify users when studies go public
    
  
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setReminderTemplate(SimpleMailMessage templateMessage) {
		this.reminderTemplate = templateMessage;
	}

	/**
	 * Sends an email with a new (reset) password for the user to login with.
	 * @param emailAddress
	 * @param resetPassword
	 * @param userName
	 */
	public void sendResetPassword (String emailAddress, String resetPassword, String userName) {
		SimpleMailMessage msg = new SimpleMailMessage(this.reminderTemplate);
		String body = PropertyLookup.getMessage("msg.passwordResetNotification", userName, resetPassword);
		msg.setTo(emailAddress);
		msg.setText(body);
		this.mailSender.send(msg);
	}

	/**
	 * Sends an email to a user to confirm the creation of a new account.
	 * @param emailAddress
	 * @param confirmationURL
	 */
	public void sendConfirmNewAccountRequest (String emailAddress, String confirmationURL) {
		SimpleMailMessage msg = new SimpleMailMessage(this.verifyNewAccountTemplate);
		String body = PropertyLookup.getMessage("msg.confirmAccountRequest", confirmationURL);
		msg.setTo(emailAddress);
		msg.setText(body);
		this.mailSender.send(msg);
	}

	/**
	 * Sends an email to MetaboLights admin for notification of a new account request.
	 * Admin then needs to authorize this by clicking a private URL in the email. 
	 * 
	 * @param usr detail of the new account requested
	 */
	public void sendNewAccountAlert (MetabolightsUser usr, String url) {
		SimpleMailMessage msg = new SimpleMailMessage(this.activateAccountTemplate);
		String body = PropertyLookup.getMessage("msg.accountRequest",
				usr.getFirstName(),usr.getLastName(),usr.getUserName(),usr.getUserId()+"",
				usr.getEmail(),usr.getAffiliation(),usr.getAffiliationUrl(),CountryService.lookupCountry(usr.getAddress()),
				url);
		msg.setText(body);
		this.mailSender.send(msg);
	}

	/**
	 * Sends an email to the user to mention that the requested account has become active. 
	 * @param user detail of the new account requested
	 */
	public void sendAccountHasbeenActivated (MetabolightsUser user) {
		SimpleMailMessage msg = new SimpleMailMessage(this.accountApprovedTemplate);
		String body = PropertyLookup.getMessage("msg.accountActive",user.getUserName());
		msg.setTo(user.getEmail());
		msg.setText(body);
		this.mailSender.send(msg);
	}

	
	/**
	 * Sends an email to MetaboLights, general request.
	 * 
	 * @param usr details for the email message
	 */
	public void sendContactUsAlert (ContactValidation usr) {
		SimpleMailMessage msg = new SimpleMailMessage(this.contactUsTemplate);
		String body = PropertyLookup.getMessage("msg.emailContactUsTo");
		msg.setFrom(usr.getEmailAddress());
		body = body + "\n\n" +
				"From: " + usr.getFirstName() +" "+ usr.getLastName() + "\n\n" +
				"Email: " + usr.getEmailAddress() + "\n\n" +
				"Affiliaction: "+ usr.getAffiliation() +" "+ usr.getAffiliationUrl() + "\n\n" +
				"Message: " + usr.getMessage() + "\n";
		msg.setText(body);
		this.mailSender.send(msg);
	}


    /**
     * Sends an email to a user to the submitter that a study is about to go public.
     * @param emailAddress
     */
    public void sendStudyToGoPublicMessage (String emailAddress, String studyAcc, String studyDate, boolean madePublic) {

        SimpleMailMessage msg = new SimpleMailMessage(this.studyPublicTemplate);
        String body = PropertyLookup.getMessage("msg.studytogopublic", studyAcc, studyDate);

        if (madePublic)  //We just made your study public
            body = PropertyLookup.getMessage("msg.studynowpublic", studyAcc);

        msg.setTo(emailAddress);
        msg.setText(body);
        this.mailSender.send(msg);
    }
   
	public void sendSimpleEmail (String from, String[] to, String subject, String body) {
		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setFrom(from);
		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(body);
		
		this.mailSender.send(msg);
	}
	public void sendSimpleEmail ( String[] to, String subject, String body) {
		String from = PropertyLookup.getMessage("mail.noreplyaddress");
		sendSimpleEmail(from , to ,subject, body);
	}
	
	public void sendSimpleEmail (String subject, String body) {
		String[] to  = {PropertiesUtil.getProperty("mtblDevEmailAddress")};
		sendSimpleEmail( to ,subject, body);
	}

	/*
	 * Email to send when a study its being queued...
	 */
	public void sendQueuedStudyEmail(String userEmail, String fileName, String fileSize, Date publicReleaseDate, String hostName, String studyToUpdate){
		String from = PropertyLookup.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, PropertiesUtil.getProperty("mtblDevEmailAddress")};
		String subject = PropertyLookup.getMessage("mail.queuedStudy.subject", fileName);
		String body = PropertyLookup.getMessage("mail.queuedStudy.body", new String[]{userEmail, fileName, fileSize, publicReleaseDate.toString(), hostName, (studyToUpdate==null?"NEW STUDY":"UPDATING " + studyToUpdate)});
		
		sendSimpleEmail(from, to, subject, body);
		
	}
	
	/*
	 * Email to send when an Public Release Date update is being requested...
	 */
	public void sendQueuedPRLUpdate( String userEmail, Date publicReleaseDate, String hostName, String studyToUpdate){
		String from = PropertyLookup.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, PropertiesUtil.getProperty("mtblDevEmailAddress")};
		String subject = PropertyLookup.getMessage("mail.queuedPRDUpdate.subject", studyToUpdate );
		String body = PropertyLookup.getMessage("mail.queuedPRDUpdate.body", new String[]{userEmail, studyToUpdate,publicReleaseDate.toString(), hostName});
		
		sendSimpleEmail(from, to, subject, body);
		
	}
	
	
	/*
	 * Email to send when a study is being successfully submitted...
	 */
	public void sendQueuedStudySubmitted(String userEmail, String fileName, Date publicReleaseDate, String ID){
		String from = PropertyLookup.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, PropertiesUtil.getProperty("mtblDevEmailAddress")};
		String subject = PropertyLookup.getMessage("mail.submittedStudy.subject", ID);
		String body = PropertyLookup.getMessage("mail.submittedStudy.body", new String[]{fileName,  ID, publicReleaseDate.toString()});
		
		sendSimpleEmail(from, to, subject, body);
		
	}
	
	/*
	 * Email to send when a study is been successfully updated ...
	 */
	public void sendQueuedStudyUpdated(String userEmail,String ID, Date publicReleaseDate){
		String from = PropertyLookup.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, PropertiesUtil.getProperty("mtblDevEmailAddress")};
		String subject = PropertyLookup.getMessage("mail.updateStudy.subject", ID);
		String body = PropertyLookup.getMessage("mail.updateStudy.body", new String[]{  ID, publicReleaseDate.toString()});
		
		sendSimpleEmail(from, to, subject, body);
		
	}
	
	/*
	 * Email to send when a study's public release date is been successfully updated ...
	 */
	public void sendQueuedPublicReleaseDateUpdated(String userEmail,String ID, Date publicReleaseDate){
		String from = PropertyLookup.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, PropertiesUtil.getProperty("mtblDevEmailAddress")};
		String subject = PropertyLookup.getMessage("mail.publicReleaseDate.subject", ID);
		String body = PropertyLookup.getMessage("mail.publicReleaseDate.body", new String[]{  ID, publicReleaseDate.toString()});
		
		sendSimpleEmail(from, to, subject, body);
		
	}
	
	/*
	 * Email to send when the submission process fails...
	 */
	public void sendSubmissionError(String userEmail, String fileName, String error ){
		String from = PropertiesUtil.getProperty("mtblDevEmailAddress");
		String[] to = {userEmail, PropertiesUtil.getProperty("mtblDevEmailAddress")};
		String subject = PropertyLookup.getMessage("mail.errorInStudy.subject", fileName );
		String body = PropertyLookup.getMessage("mail.errorInStudy.body", new String[]{fileName, error});
		
		sendSimpleEmail(from, to, subject, body);
		
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
			emailAddr.validate(); //Validate the email address, standard Java
			
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
			TextUtils.textHasContent( tokens[1] ) &&
			tokens[1].contains("."); //Must have a somewhere.domain
	}

	


}
