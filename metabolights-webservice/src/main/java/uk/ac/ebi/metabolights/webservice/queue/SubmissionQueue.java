/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 8/29/13 3:12 PM
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
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SubmissionQueue {
	
	
	private static Logger logger = LoggerFactory.getLogger(SubmissionQueue.class);

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

				try {

					SubmissionItem si = new SubmissionItem(fileQueued);

					queue.add(si);
				} catch (Exception e){
					logger.warn("File in the queue not understood: {}. Error thrown: {}", fileQueued.getName(), e.getMessage());

				}


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
			
			if (si.getUserToken().equalsIgnoreCase(userId)){
				queueForUser.add(si);
			}
			
		}
		
		return queueForUser;
	}
	
}
