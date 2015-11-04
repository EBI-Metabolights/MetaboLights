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

package uk.ac.ebi.metabolights.webservice.services;

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
import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.webservice.utils.TextUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.InetAddress;
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
	public static final String REVIEWER_URL_PREFIX = "reviewer";

	private @Value("#{EBIHost}") String prodURL;
    private @Value("#{curationEmailAddress}") String curationEmailAddress;

    //Have to qualify these as the test Spring servlet is defining the same beans
    @Qualifier("mailSender")
    @Autowired
	private JavaMailSender mailSender; // configured in servlet XML

	@Qualifier("velocityEngine")
	@Autowired
	private VelocityEngine velocityEngine; // configured in servlet XML


	@Qualifier("userDetailsService")
	@Autowired
	private UserService userService;


	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}


    /**
     * Sends an email to the submitter and the curators that a study will go public
     * @param study
     */
    public void sendStudyGoingPublicNotification(Study study){

		String from = PropertyLookUpService.getMessage("mail.noreplyaddress");
		String[] to = getRecipientsFromStudy(study);
		String subject = PropertyLookUpService.getMessage("mail.studySoonLive.subject", study.getStudyIdentifier());
        String body = PropertyLookUpService.getMessage("mail.studySoonLive.body", study.getStudyIdentifier(), new SimpleDateFormat("dd-MM-yyyy").format(study.getStudyPublicReleaseDate()));

		sendSimpleEmail(from, to, subject, body);

    }

	private void sendHTMLEmail(SimpleMailMessage msg){
		sendHTMLEmail(msg.getFrom(),msg.getTo(),msg.getSubject(),msg.getText(),"");
	}

	public void sendSimpleEmail (String from, String[] to, String subject, String body) {

		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setFrom(from);
		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(body);

		//mailSender.send(msg);
		sendHTMLEmail(msg);
	}

	public void sendSimpleEmail ( String[] to, String subject, String body) {
		String from = PropertyLookUpService.getMessage("mail.noreplyaddress");
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
		String from = PropertyLookUpService.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, curationEmailAddress};
		String subject = PropertyLookUpService.getMessage("mail.queuedStudy.subject", fileName);
		String body = PropertyLookUpService.getMessage("mail.queuedStudy.body", new String[]{userEmail, fileName, fileSize, publicReleaseDate.toString(), hostName, (studyToUpdate==null?"NEW STUDY":"UPDATING " + studyToUpdate)});

		sendSimpleEmail(from, to, subject, body);

	}

	/*
	 * Email to send when an Public Release Date update is being requested...
	 */
	public void sendQueuedDeletion( String userEmail, String hostName, String studyToDelete){
		String from = PropertyLookUpService.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, curationEmailAddress};
		String subject = PropertyLookUpService.getMessage("mail.queuedDeletion.subject", studyToDelete );
		String body = PropertyLookUpService.getMessage("mail.queuedDeletion.body", new String[]{userEmail, studyToDelete, hostName});

		sendSimpleEmail(from, to, subject, body);

	}

	/*
	 * Email to send when a study is being successfully submitted...
	 */
	public void sendQueuedStudySubmitted(Study newStudy, String fileName){
		String from = PropertyLookUpService.getMessage("mail.noreplyaddress");
		String[] to = getRecipientsFromStudy(newStudy);
		String subject = PropertyLookUpService.getMessage("mail.submittedStudy.subject", newStudy.getStudyIdentifier());
		String body = PropertyLookUpService.getMessage("mail.submittedStudy.body", new String[]{fileName,  newStudy.getStudyIdentifier(), newStudy.getStudyPublicReleaseDate().toString(), prodURL});

		sendSimpleEmail(from, to, subject, body);

	}

	/*
	 * Email to send when a study is been successfully updated ...
	 */
	public void sendQueuedStudyUpdated(Study updatedStudy){
		String from = PropertyLookUpService.getMessage("mail.noreplyaddress");
		String[] to = getRecipientsFromStudy(updatedStudy);
		String subject = PropertyLookUpService.getMessage("mail.updateStudy.subject", updatedStudy.getStudyIdentifier());
		String body = PropertyLookUpService.getMessage("mail.updateStudy.body", new String[]{  updatedStudy.getStudyIdentifier(), updatedStudy.getStudyPublicReleaseDate().toString(), prodURL});

		sendSimpleEmail(from, to, subject, body);

	}

	/*
 * Email to send when a study's public release date is been successfully updated ...
 */
	public void sendPublicReleaseDateUpdated(Study study){
		String from = PropertyLookUpService.getMessage("mail.noreplyaddress");
		String[] to = getRecipientsFromStudy(study);
		String subject = PropertyLookUpService.getMessage("mail.publicReleaseDate.subject", study.getStudyIdentifier());
		String body = PropertyLookUpService.getMessage("mail.publicReleaseDate.body", new String[]{  study.getStudyIdentifier(), study.getStudyPublicReleaseDate().toString(), prodURL});

		sendSimpleEmail(from, to, subject, body);

	}

	public void sendStatusChanged(Study study) {

		String from = PropertyLookUpService.getMessage("mail.noreplyaddress");
		String[] to = getRecipientsFromStudy(study);
		String subject = PropertyLookUpService.getMessage("mail.status.subject", study.getStudyIdentifier(), study.getStudyStatus().name());
		String body = getMessageForStatusChange(study);

		sendSimpleEmail(from, to, subject, body);

	}

	private String getMessageForStatusChange(Study study) {

		switch (study.getStudyStatus()) {
			case SUBMITTED:
				return PropertyLookUpService.getMessage("mail.status.body.private", study.getStudyStatus().name(), study.getStudyIdentifier(), LiteStudy.StudyStatus.INCURATION.name());
			case INCURATION:
				return PropertyLookUpService.getMessage("mail.status.body.incuration", study.getStudyStatus().name(), study.getStudyIdentifier());
			case INREVIEW:
				return PropertyLookUpService.getMessage("mail.status.body.inreview", study.getStudyStatus().name(), study.getStudyIdentifier(), prodURL + REVIEWER_URL_PREFIX + study.getObfuscationCode(), prodURL + study.getStudyIdentifier(), curationEmailAddress );
			case PUBLIC:
				return PropertyLookUpService.getMessage("mail.status.body.public", study.getStudyStatus().name(), study.getStudyIdentifier());
		}

		return "Status changes to " + study.getStudyStatus();
	}


	public void sendStudyDeleted(Study deletedStudy){
		String from = PropertyLookUpService.getMessage("mail.noreplyaddress");
		String[] to = getRecipientsFromStudy(deletedStudy);
		String subject = PropertyLookUpService.getMessage("mail.studyDeleted.subject", deletedStudy.getStudyIdentifier());
		String body = PropertyLookUpService.getMessage("mail.studyDeleted.body", deletedStudy.getStudyIdentifier());

		sendSimpleEmail(from, to, subject, body);

	}

	/*
	 * Email to send when the submission process fails...
	 */
	public void sendSubmissionError(String userToken, String fileName, Exception error) throws Exception {
		String from = curationEmailAddress;
		String[] to = getRecipientsFromToken(userToken);
		String subject = PropertyLookUpService.getMessage("mail.errorInStudy.subject", fileName );
        String hostName = InetAddress.getLocalHost().getHostName();
        String body = PropertyLookUpService.getMessage("mail.errorInStudy.body", new String[]{fileName, error.getMessage(), hostName, error.getMessage()});

		String technicalInfo = userTokenToText(userToken);

		technicalInfo = technicalInfo + "\n" + exceptionToString(error);

		sendHTMLEmail(from, to, subject, body, technicalInfo);

	}

	private String[] getRecipientsFromToken(String userToken) {

		String userEmail = userTokenToEmail(userToken);

		if (userEmail.equals("")){
			return new String[]{curationEmailAddress};
		} else {
			return new String[]{userEmail,curationEmailAddress};
		}
	}

	private String[] getRecipientsFromStudy(Study study) {

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

	public void sendHTMLEmail(final String from, final String[] to, final String subject,  final String body,  final String technicalInfo) {

		final String HTMLbody = body.replace("\n", "<BR/>");
		final String HTMLtechnicalInfo = technicalInfo.replace("\n", "<BR/>");

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {


				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(to);
				message.setFrom(from); // could be parameterized...
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

			logger.error("Couldn't sent email: \n Subject: \n {}\n\n Body:\n{}\n\nTechnical info:\n{}",subject,body,technicalInfo, e );
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
			TextUtils.textHasContent(tokens[0]) &&
			TextUtils.textHasContent( tokens[1] ) &&
			tokens[1].contains("."); //Must have a somewhere.domain
	}

	public String userTokenToEmail(String userToken){

		// Using the Spring user service. THis will return always a user
		// Check for anonymous
		User user = userService.lookupByToken(userToken);

		if (user.getRole() == AppRole.ANONYMOUS)
			return "";
		else
			return user.getEmail();

	}

	public String userTokenToText(String userToken) {

		User user = userService.lookupByToken(userToken);

		String message =
			"User's info:\n" +
					"Full name: " + user.getFullName() + "\n" +
					"Role: " + user.getRole().name() + "\n" +
					"Status: " + user.getStatus().name() + "\n" +
					"Affiliation: " + user.getAffiliation() + "\n" +
					"Join date: " + user.getJoinDate() + "\n";


		return message;
	}

	/**
	 * Email to be sent when a private FTP is created for the Study
	 *
	 * @param userEmail
	 * @param studyID
	 * @param folderName The private FTP folder created
	 * @author jrmacias
	 * @date 20151102
	 */
	public void sendCreatedFTPFolderEmail(String userEmail, String studyID , String folderName) {
		String from = PropertyLookUpService.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, curationEmailAddress};
		String subject = "Requested Study FTP folder.";

		StringBuilder body = new StringBuilder().append("We are happy to inform you that your FTP folder for study ")
				.append(studyID).
				append(" has been successfully created and is know ready for use. To access, please use your favorite FTP client with following path:").append('\n')
				.append(folderName);

		sendSimpleEmail(from, to, subject, body.toString());
	}
}
