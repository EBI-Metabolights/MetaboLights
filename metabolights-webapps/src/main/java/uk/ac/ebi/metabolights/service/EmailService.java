/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/16/14 4:39 PM
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

package uk.ac.ebi.metabolights.service;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import uk.ac.ebi.metabolights.form.ContactValidation;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Uses a central Spring interface for sending emails (the MailSender interface).
 * A simple value object encapsulating the properties of a simple mail such as from and to (plus many others) is the SimpleMailMessage class.
 * @author markr
 */
@Service
public class EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private @Value("#{EBIHost}") String prodURL;
    private @Value("#{curationEmailAddress}") String curationEmailAddress;
	private @Value("#{BccPivotalEmailAddress}") String pivotalAutomatedsubmittoqueueEmailAddress;

    //Have to qualify these as the test Spring servlet is defining the same beans
    @Qualifier("mailSender")
    @Autowired
	private JavaMailSender mailSender; // configured in servlet XML

	@Qualifier("velocityEngine")
	@Autowired
	private VelocityEngine velocityEngine; // configured in servlet XML


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

	public void setMailSender(JavaMailSender mailSender) {
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
		//mailSender.send(msg);
		sendHTMLEmail(msg);
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
		//mailSender.send(msg);
		sendHTMLEmail(msg);
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
		//mailSender.send(msg);
		sendHTMLEmail(msg);
	}

	/**
	 * Sends an email to the user to mention that the requested account has become active.
	 * @param user detail of the new account requested
	 */
	public void sendAccountHasbeenActivated (MetabolightsUser user) {
		SimpleMailMessage msg = new SimpleMailMessage(this.accountApprovedTemplate);
		String body = null;
        try {
            String fileLocation = EmailService.class.getClassLoader().getResource("").getPath()+ File.separator+"email_template"+File.separator;
            body = PropertyLookup.getEmailMessage(fileLocation+"accountApprovedTemplate.txt",user.getUserName(), user.getFirstName());
        } catch (IOException e) {
            body = PropertyLookup.getMessage("msg.accountActive.old",user.getUserName());
        }
        msg.setTo(user.getEmail());
		msg.setText(body);
		//mailSender.send(msg);
		sendHTMLEmail(msg);
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
				"Affiliation: "+ usr.getAffiliation() +" "+ usr.getAffiliationUrl() + "\n\n" +
				"Message: " + usr.getMessage() + "\n";
		msg.setText(body);
		//mailSender.send(msg);
		sendHTMLEmail(msg);
	}

    /**
     * Sends an email to the submitter and the curators that a study will go public
     * @param study
     */
    public void sendStudyGoingPublicNotification(LiteStudy study){
        SimpleMailMessage msg = new SimpleMailMessage(this.studySoonLiveTemplate);
        String body = PropertyLookup.getMessage("msg.studySoonLive", study.getStudyIdentifier(), new SimpleDateFormat("dd-MM-yyyy").format(study.getStudyPublicReleaseDate()));
        String[] emailTo = getRecipientsForStudy(study);
        msg.setTo(emailTo);
        msg.setText(body);
        //mailSender.send(msg);
		sendHTMLEmail(msg);

    }

	private String[] getRecipientsForStudy(LiteStudy study) {

		if (study == null){
			return new String[]{curationEmailAddress};
		} else {

			int recipientsNumber = study.getUsers().size() + 1;

			String[] recipients = new String[recipientsNumber];

			for (int i = 0; i < study.getUsers().size(); i++) {

				String email = study.getUsers().get(i).getEmail();
				recipients[i] = email;
			}

			// Add the curators emailaddress
			recipients[recipientsNumber-1] = curationEmailAddress;
			return recipients;
		}
	}


	private void sendHTMLEmail(SimpleMailMessage msg){
		sendHTMLEmail(msg.getFrom(),msg.getTo(), msg.getBcc() == null ? new String[0] : msg.getBcc(),msg.getSubject(),msg.getText(),"");
	}

	public void sendSimpleEmail (String from, String[] to, String subject, String body) {
        //mailSender.send(msg);
		sendHTMLEmail(getBasicMailMessage(from, to, subject, body));
	}

	public void sendSimpleEmailWithBcc (String from, String[] to, String[] bcc, String subject, String body) {

		SimpleMailMessage msg = getBasicMailMessage(from, to, subject, body);
		msg.setBcc(bcc);
		//mailSender.send(msg);
		sendHTMLEmail(msg);
	}

	private SimpleMailMessage getBasicMailMessage(String from, String[] to, String subject, String body){

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(from);
		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(body);
		return msg;
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
		String[] bcc = {pivotalAutomatedsubmittoqueueEmailAddress};
		String subject = PropertyLookup.getMessage("mail.submittedStudy.subject", ID);
		//String body = PropertyLookup.getMessage("mail.submittedStudy.body", new String[]{fileName,  ID, publicReleaseDate.toString(), prodURL});
		String text = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, "email_template/studySubmissionConfirmationEmail.vm", "UTF-8", new HashMap());
		String body = MessageFormat.format(text,  new String[]{fileName,  ID, publicReleaseDate.toString(), prodURL});
    	sendSimpleEmailWithBcc(from, to, bcc, subject, body);

	}

//	/*
//	 * Email to send when a study is been successfully updated ...
//	 */
//	public void sendQueuedStudyUpdated(String userEmail,String ID, Date publicReleaseDate){
//		String from = PropertyLookup.getMessage("mail.noreplyaddress");
//		String[] to = {userEmail, curationEmailAddress};
//		String subject = PropertyLookup.getMessage("mail.updateStudy.subject", ID);
//		String body = PropertyLookup.getMessage("mail.updateStudy.body", new String[]{  ID, publicReleaseDate.toString(), prodURL});
//
//		sendSimpleEmail(from, to, subject, body);
//
//	}

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

//	/*
//	 * Email to send when the submission process fails...
//	 */
//	public void sendSubmissionError(String userEmail, String fileName, Exception error) throws Exception {
//		String from = curationEmailAddress;
//		String[] to = {userEmail, curationEmailAddress};
//		String subject = PropertyLookup.getMessage("mail.errorInStudy.subject", fileName );
//        String hostName = java.net.InetAddress.getLocalHost().getHostName();
//        String body;
//
//        if(error instanceof IsaTabException){
//            IsaTabException ie = (IsaTabException) error;
//            String[] errorChunks = null;
//            String errorMessage = null;
//            if (ie.geTechnicalInfo().contains("\tat "))
//                errorChunks = ie.geTechnicalInfo().split("\tat ");
//
//            if (errorChunks != null)
//                errorMessage = errorChunks[0];
//
//            if (errorMessage != null)
//                errorMessage = errorMessage.replace("/nfs/public/rw/homes/tc_cm01/metabolights","MetaboLightsHomeFolder");
//
//            body = PropertyLookup.getMessage("mail.errorInStudy.body", new String[]{fileName, error.getMessage(), hostName, errorMessage});
//        } else {
//            body = PropertyLookup.getMessage("mail.errorInStudy.body", new String[]{fileName, error.getMessage(), hostName, error.getMessage()});
//        }
//		//sendSimpleEmail(from, to, subject, body);
//		sendHTMLEmail(from, to, subject, body, exceptionToString(error));
//
//	}

	private void sendHTMLEmail(final String from, final String[] to, final String[] bcc, final String subject,  final String body,  final String technicalInfo) {

		final String HTMLbody = body.replace("\n", "<BR/>");
		final String HTMLtechnicalInfo = technicalInfo.replace("\n", "<BR/>");

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {


				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(to);
				message.setFrom(from); // could be parameterized...
				if(bcc.length !=0){
					message.setBcc(bcc);
				}
				message.setSubject(subject);
				Map model = new HashMap();
				model.put("body", HTMLbody);
				model.put("technicalInfo", HTMLtechnicalInfo);
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "email_template/htmlemail.vm", model);


				message.setText(text, true);
			}
		};

		try {

			this.mailSender.send(preparator);
		} catch (Exception e) {

			logger.error("Could not send email: \n Subject: \n {}\n\n Body:\n{}\n\nTechnical info:\n{}",subject,body,technicalInfo ,e);
		}
	}

	private String exceptionToString(Exception error){

		String result = error.getClass().getCanonicalName() + "\n";

		for (StackTraceElement stackTraceElement: error.getStackTrace()){

			result = result + stackTraceElement.toString() + "\n";
		}

		return result;
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
