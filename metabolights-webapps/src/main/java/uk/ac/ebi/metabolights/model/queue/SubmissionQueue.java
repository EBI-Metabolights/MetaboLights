package uk.ac.ebi.metabolights.model.queue;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import uk.ac.ebi.metabolights.utils.PropertiesUtil;

public class SubmissionQueue {
	
	
	private static Logger logger = Logger.getLogger(SubmissionQueue.class);

	private static String uploadDirectory = PropertiesUtil.getProperty("uploadDirectory");;
	private static String queueFolder;
	private static String processFolder;
	
	public SubmissionQueue(){
	}
	
	public static String getQueueFolder(){
		
		if (queueFolder == null){
			
			// Calculate the queue folder
			queueFolder = getFolder("queue/");
			
		}
		
		return queueFolder;
		
	}
	public static String getProcessFolder(){
		
		if (processFolder == null){
			
			processFolder= getFolder("process/");
			
		}
		
		return processFolder;
		
	}
	private static String getFolder(String folderName){

		// Concatenate the folder name to the upload folder
		String folder = uploadDirectory + folderName;
		
		// If it doesn't exist create it
		File dir=new File(folder);
		if (!dir.exists()) {
			boolean success = dir.mkdir();
			if (success) {
				logger.info(folderName + " dir " + folder + " created");
			}    
		}
		
		return folder;
		
		
	}
	
	public static void start(){
	
		
	}
	

}
