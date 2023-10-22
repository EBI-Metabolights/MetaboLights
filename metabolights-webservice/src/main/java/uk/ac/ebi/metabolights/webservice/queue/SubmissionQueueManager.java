/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/24/12 12:40 PM
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
import org.springframework.beans.factory.annotation.Autowired;
import uk.ac.ebi.metabolights.webservice.services.AppContext;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;

import java.util.Timer;

/*
 * Manages the queue and triggers the submission process
 */

public class SubmissionQueueManager {

	private static Logger log = LoggerFactory.getLogger(SubmissionQueueManager.class);
	private static Timer tm;

	private String queueRunner;
	public SubmissionQueueManager(){

		queueRunner = PropertiesUtil.getProperty("queueRunner");

		// By default, only start the queue if its the designated machine.
		if (!AppContext.getHostName().equals(queueRunner)) {
			log.info("Queue not running in this machine. Hostname ({}) do not matches queue runner ({})", AppContext.getHostName(), queueRunner);
			return;
		}


		if (! getIsRunning()){
			
			start();
		}
	}
	public void start(){

		// If timer is running
		if (tm !=null) return;
		
		tm = new Timer("Submission queue thread");
		
		// interval between scans..
		int minutes = 1;
		
		log.info("Submission queue is starting. Will scan directory every " + minutes + " minute/s");

		// Period of time after each execution in milliseconds.
		long period = 1000 * 60 * minutes;
		
		// tm.schedule(new SubmissionQueueScan(), 0, period);
			
		
	}
	public void stop(){
		
		if(tm == null) return;
		
		log.info("Submission queue is stopping\t\ttm.cancel();\n.");
		tm = null;
		
	}
	public boolean getIsRunning(){
		return (tm!=null);
	}


}
