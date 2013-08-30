package uk.ac.ebi.metabolights.model.queue;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.metabolights.checklists.CheckList;
import uk.ac.ebi.metabolights.controller.SubmissionController;
import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabUploader;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.utils.StringUtils;

public class SubmissionQueue {
	
	
	private static Logger logger = Logger.getLogger(SubmissionQueue.class);

	private static String uploadDirectory = PropertiesUtil.getProperty("uploadDirectory");
	private static String queueFolder;
	private static String processFolder;
	private static String errorFolder;
	private static String backUpFolder;
	private static List<SubmissionItem> queue = new ArrayList<SubmissionItem>();
	

	
	public SubmissionQueue(){
	}
	
	public static String getQueueFolder(){
		
		// Calculate the queue folder
		queueFolder = getFolder("queue/");
		
		return queueFolder;
		
	}
	public static String getBackUpFolder(){
		
		// Calculate the queue folder
		backUpFolder = getFolder("backup/");
		
		return backUpFolder;
		
	}
	public static String getProcessFolder(){
			
		processFolder= getFolder("process/");
		
		return processFolder;
		
	}
	public static String getErrorFolder(){
		
		errorFolder= getFolder("queueerrors/");
		
		return errorFolder;
		
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
	
	private static void refreshQueue() throws ParseException{
		
		queue.clear();
		
		// List the queue folder
		File queueFolderF = new File(getQueueFolder());
		
		File[] files = queueFolderF.listFiles();

        if (files != null){
            // Sort it: older first
            Arrays.sort(files, new Comparator<File>(){
                public int compare(File f1, File f2)
                {
                    return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
                } });


            for (int i=0;i< files.length;i++){

                File fileQueued = files[i];

                SubmissionItem si = new SubmissionItem(fileQueued);

                queue.add(si);

            }
        }
	} 
	
	
	public static List<SubmissionItem> getQueue() throws ParseException{
		
		refreshQueue();
		
		return queue;
	}
	
	public static List<SubmissionItem> getQueuedForUserId(String userId) throws ParseException{
		
		List<SubmissionItem> queueForUser = new ArrayList<SubmissionItem>();
		
		// Refresh the queue
		refreshQueue();
		
		for (int i = 0; i<queue.size(); i++){
			SubmissionItem si = queue.get(i);
			
			if (si.getUserId().equalsIgnoreCase(userId)){
				queueForUser.add(si);
			}
			
		}
		
		return queueForUser;
	}
	
}
