package uk.ac.ebi.metabolights.model.queue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.ebi.metabolights.service.UserService;

import java.util.Timer;

/*
 * Manages the queue and triggers the submission process
 */

public class SubmissionQueueManager {
	private static Logger log = Logger.getLogger(SubmissionQueueManager.class);
	private static Timer tm;
 	
	
	private static UserService userService;

	
	public SubmissionQueueManager(){
		if (! getIsRunning()){
			
			start();
		}
	}
	public static void start(){
		
		
		// If timer is running
		if (tm !=null) return;
		
		
		tm = new Timer();
		
		// interval between scans..
		int minutes = 1;
		
		log.info("Submission queue is starting. Will scan directory every " + minutes + " minute/s");

		// Period of time after each execution in milliseconds.
		long period = 1000 * 60 * minutes;
		
		tm.schedule(new SubmissionQueueScan(), 0, period);
			
		
	}
	public static void stop(){
		
		if(tm == null) return;
		
		log.info("Submission queue is stoping.");
		tm.cancel();
		tm = null;
		
	}
	public static boolean getIsRunning(){
		return (tm!=null);
	}

	public void setUserService(UserService us) {
		userService = us;
	}
	public static UserService getUserService(){
		return userService;
	}
}
