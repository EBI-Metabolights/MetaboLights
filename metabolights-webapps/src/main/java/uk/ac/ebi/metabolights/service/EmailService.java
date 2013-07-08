package uk.ac.ebi.metabolights.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import uk.ac.ebi.metabolights.form.ContactValidation;
import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Uses a central Spring interface for sending emails (the MailSender interface).
 * A simple value object encapsulating the properties of a simple mail such as from and to (plus many others) is the SimpleMailMessage class.
 * @author markr
 */
@Service
public class EmailService {

    private @Value("#{EBIHost}") String prodURL;
    private @Value("#{curationEmailAddress}") String curationEmailAddress;

    //Have to qualify these as the test Spring servlet is defining the same beans
    @Qualifier("mailSender")
    @Autowired
	private MailSender mailSender; // configured in servlet XML

    @Qualifier("reminderTemplate")
    @Autowired
	private SimpleMailMessage reminderTemplate; // template for password reminder, configured in servlet XML

    @Qualifier("verifyNewAccountTemplate")
    @Autowired
	private SimpleMailMessage verifyNewAccountTemplate; // template for confirmation of an account request

    @Qualifier("activateAccountTemplate")
    @Autowired
	private SimpleMailMessage activateAccountTemplate; // template for password reminder, configured in servlet XML

    @Qualifier("accountApprovedTemplate")
    @Autowired
	private SimpleMailMessage accountApprovedTemplate; // template for notification that account is active

    @Qualifier("contactUsTemplate")
    @Autowired
	private SimpleMailMessage contactUsTemplate; // template for general website requests

    @Qualifier("studySoonLiveTemplate")
    @Autowired
    private SimpleMailMessage studySoonLiveTemplate; // template to notify the submitter of studies going live

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
     * Sends an email to the submitter and the curators that a study will go public
     * @param submitterEmail
     * @param publicDate
     * @param acc
     */
    public void sendStudyGoingPublicNotification(String submitterEmail, Date publicDate, String acc){
        SimpleMailMessage msg = new SimpleMailMessage(this.studySoonLiveTemplate);
        String body = PropertyLookup.getMessage("msg.studySoonLive", acc, new SimpleDateFormat("dd-MM-yyyy").format(publicDate));
        String[] emailTo = new String[] {submitterEmail,curationEmailAddress};
        msg.setTo(emailTo);
        msg.setText(body);
        mailSender.send(msg);

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
		String[] to  = {curationEmailAddress};
		sendSimpleEmail( to ,subject, body);
	}

	/*
	 * Email to send when a study its being queued...
	 */
	public void sendQueuedStudyEmail(String userEmail, String fileName, String fileSize, Date publicReleaseDate, String hostName, String studyToUpdate){
		String from = PropertyLookup.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, curationEmailAddress};
		String subject = PropertyLookup.getMessage("mail.queuedStudy.subject", fileName);
		String body = PropertyLookup.getMessage("mail.queuedStudy.body", new String[]{userEmail, fileName, fileSize, publicReleaseDate.toString(), hostName, (studyToUpdate==null?"NEW STUDY":"UPDATING " + studyToUpdate)});

		sendSimpleEmail(from, to, subject, body);

	}

	/*
	 * Email to send when an Public Release Date update is being requested...
	 */
	public void sendQueuedPRLUpdate( String userEmail, Date publicReleaseDate, String hostName, String studyToUpdate){
		String from = PropertyLookup.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, curationEmailAddress};
		String subject = PropertyLookup.getMessage("mail.queuedPRDUpdate.subject", studyToUpdate );
		String body = PropertyLookup.getMessage("mail.queuedPRDUpdate.body", new String[]{userEmail, studyToUpdate,publicReleaseDate.toString(), hostName});

		sendSimpleEmail(from, to, subject, body);

	}

	/*
	 * Email to send when an Public Release Date update is being requested...
	 */
	public void sendQueuedDeletion( String userEmail, String hostName, String studyToDelete){
		String from = PropertyLookup.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, curationEmailAddress};
		String subject = PropertyLookup.getMessage("mail.queuedDeletion.subject", studyToDelete );
		String body = PropertyLookup.getMessage("mail.queuedDeletion.body", new String[]{userEmail, studyToDelete, hostName});

		sendSimpleEmail(from, to, subject, body);

	}

	/*
	 * Email to send when a study is being successfully submitted...
	 */
	public void sendQueuedStudySubmitted(String userEmail, String fileName, Date publicReleaseDate, String ID){
		String from = PropertyLookup.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, curationEmailAddress};
		String subject = PropertyLookup.getMessage("mail.submittedStudy.subject", ID);
		String body = PropertyLookup.getMessage("mail.submittedStudy.body", new String[]{fileName,  ID, publicReleaseDate.toString(), prodURL});

		sendSimpleEmail(from, to, subject, body);

	}

	/*
	 * Email to send when a study is been successfully updated ...
	 */
	public void sendQueuedStudyUpdated(String userEmail,String ID, Date publicReleaseDate){
		String from = PropertyLookup.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, curationEmailAddress};
		String subject = PropertyLookup.getMessage("mail.updateStudy.subject", ID);
		String body = PropertyLookup.getMessage("mail.updateStudy.body", new String[]{  ID, publicReleaseDate.toString(), prodURL});

		sendSimpleEmail(from, to, subject, body);

	}

	/*
	 * Email to send when a study's public release date is been successfully updated ...
	 */
	public void sendQueuedPublicReleaseDateUpdated(String userEmail,String ID, Date publicReleaseDate){
		String from = PropertyLookup.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, curationEmailAddress};
		String subject = PropertyLookup.getMessage("mail.publicReleaseDate.subject", ID);
		String body = PropertyLookup.getMessage("mail.publicReleaseDate.body", new String[]{  ID, publicReleaseDate.toString(), prodURL});

		sendSimpleEmail(from, to, subject, body);

	}

	public void sendStudyDeleted(String userEmail,String ID ){
		String from = PropertyLookup.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, curationEmailAddress};
		String subject = PropertyLookup.getMessage("mail.studyDeleted.subject", ID);
		String body = PropertyLookup.getMessage("mail.studyDeleted.body", ID);

		sendSimpleEmail(from, to, subject, body);

	}
	/*
	 * Email to send when the submission process fails...
	 */
	public void sendSubmissionError(String userEmail, String fileName, Exception error ) throws Exception {
		String from = curationEmailAddress;
		String[] to = {userEmail, curationEmailAddress};
		String subject = PropertyLookup.getMessage("mail.errorInStudy.subject", fileName );
        String hostName = java.net.InetAddress.getLocalHost().getHostName();
        String empty = "Generic error", body;

        if(error instanceof IsaTabException){
            IsaTabException ie = (IsaTabException) error;
            String[] errorChunks = ie.geTechnicalInfo().split("\tat ");
            body = PropertyLookup.getMessage("mail.errorInStudy.body", new String[]{fileName, error.getMessage(), hostName, errorChunks[0]});
        } else {
            body = PropertyLookup.getMessage("mail.errorInStudy.body", new String[]{fileName, error.getMessage(), hostName, empty});
        }
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
