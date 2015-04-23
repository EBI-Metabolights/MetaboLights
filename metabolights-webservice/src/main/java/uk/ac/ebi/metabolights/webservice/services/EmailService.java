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
     * @param submitterEmail
     * @param publicDate
     * @param acc
     */
    public void sendStudyGoingPublicNotification(String submitterEmail, Date publicDate, String acc){


		String from = PropertyLookUpService.getMessage("mail.noreplyaddress");
		String[] to = new String[] {submitterEmail,curationEmailAddress};
		String subject = PropertyLookUpService.getMessage("mail.studySoonLive.subject", acc);
        String body = PropertyLookUpService.getMessage("msg.studySoonLive.body", acc, new SimpleDateFormat("dd-MM-yyyy").format(publicDate));

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
	public void sendQueuedPRLUpdate( String userEmail, Date publicReleaseDate, String hostName, String studyToUpdate){
		String from = PropertyLookUpService.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, curationEmailAddress};
		String subject = PropertyLookUpService.getMessage("mail.queuedPRDUpdate.subject", studyToUpdate );
		String body = PropertyLookUpService.getMessage("mail.queuedPRDUpdate.body", new String[]{userEmail, studyToUpdate,publicReleaseDate.toString(), hostName});

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
	public void sendQueuedStudySubmitted(String userEmail, String fileName, Date publicReleaseDate, String ID){
		String from = PropertyLookUpService.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, curationEmailAddress};
		String subject = PropertyLookUpService.getMessage("mail.submittedStudy.subject", ID);
		String body = PropertyLookUpService.getMessage("mail.submittedStudy.body", new String[]{fileName,  ID, publicReleaseDate.toString(), prodURL});

		sendSimpleEmail(from, to, subject, body);

	}

	/*
	 * Email to send when a study is been successfully updated ...
	 */
	public void sendQueuedStudyUpdated(String userEmail,String ID, Date publicReleaseDate){
		String from = PropertyLookUpService.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, curationEmailAddress};
		String subject = PropertyLookUpService.getMessage("mail.updateStudy.subject", ID);
		String body = PropertyLookUpService.getMessage("mail.updateStudy.body", new String[]{  ID, publicReleaseDate.toString(), prodURL});

		sendSimpleEmail(from, to, subject, body);

	}

	/*
	 * Email to send when a study's public release date is been successfully updated ...
	 */
	public void sendQueuedPublicReleaseDateUpdated(String userEmail,String ID, Date publicReleaseDate){
		String from = PropertyLookUpService.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, curationEmailAddress};
		String subject = PropertyLookUpService.getMessage("mail.publicReleaseDate.subject", ID);
		String body = PropertyLookUpService.getMessage("mail.publicReleaseDate.body", new String[]{  ID, publicReleaseDate.toString(), prodURL});

		sendSimpleEmail(from, to, subject, body);

	}

	public void sendStudyDeleted(String userEmail,String ID ){
		String from = PropertyLookUpService.getMessage("mail.noreplyaddress");
		String[] to = {userEmail, curationEmailAddress};
		String subject = PropertyLookUpService.getMessage("mail.studyDeleted.subject", ID);
		String body = PropertyLookUpService.getMessage("mail.studyDeleted.body", ID);

		sendSimpleEmail(from, to, subject, body);

	}

	/*
	 * Email to send when the submission process fails...
	 */
	public void sendSubmissionError(String userToken, String fileName, Exception error) throws Exception {
		String from = curationEmailAddress;
		String[] to = {userTokenToEmail(userToken), curationEmailAddress};
		String subject = PropertyLookUpService.getMessage("mail.errorInStudy.subject", fileName );
        String hostName = InetAddress.getLocalHost().getHostName();
        String body = PropertyLookUpService.getMessage("mail.errorInStudy.body", new String[]{fileName, error.getMessage(), hostName, error.getMessage()});

		String technicalInfo = userTokenToText(userToken);

		technicalInfo = technicalInfo + "\n" + exceptionToString(error);

		sendHTMLEmail(from, to, subject, body, technicalInfo);

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
		this.mailSender.send(preparator);
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

		User user = userService.lookupByToken(userToken);

		return user.getEmail();

	}

	public String userTokenToText(String userToken) {

		User user = userService.lookupByToken(userToken);

		String message =
				"User's info:\n"+
						"Full name: " + user.getFullName() + "\n" +
						"Status: " + user.getStatus().name() + "\n" +
						"Affiliation: " + user.getAffiliation() + "\n" +
						"Join date: " + user.getJoinDate() + "\n";

		return message;
	}


}
