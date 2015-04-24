/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 5/16/14 11:13 AM
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

package uk.ac.ebi.metabolights.webservice.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.metabolights.webservice.services.AppContext;

import java.util.List;
import java.util.TimerTask;

@Transactional
public class SubmissionQueueScan extends TimerTask{
	private static Logger logger = LoggerFactory.getLogger(SubmissionQueueScan.class);

	@Override
	public void run() {


		SubmissionItem si = null;

		// Check if there is any item in the queue...
		try {

			// Load the queue
			List<SubmissionItem> queue = SubmissionQueue.getQueue();

			// If there is any Submission...
			if (!queue.isEmpty()){

				// Get the first Item (Should be sorted by submission time)
				si = queue.get(0);

				// Process it
				SubmissionQueueProcessor sp = new SubmissionQueueProcessor(si);

				// Check first if the process can start, otherwise we will get an exception if can't start
				if (sp.canProcessStart()){

					sp.start();

				}
			}

		} catch (Exception e) {

			String message;

			if (e instanceof MailException){
				message = "There was an error sending an email after a submission. Email might be wrong. User hasn't got any notification but submission might have been successful.\n";
			} else {

				message = "There was an error while retrieving queued items" + this.getClass() + "\nMethod: run()\n\nERROR:\n" + e.getMessage() + "\n";
			}

			message = message + si;

			message = message + "\n" + AppContext.getEmailService().userTokenToText(si.getUserToken());

			if (AppContext.getApplicationContext() != null) {
				// Cannot load the queue
				AppContext.getEmailService().sendSimpleEmail("ERROR while running the queue", message);
			} else {
				logger.warn(message);
			}

		}

	}

}
