package uk.ac.ebi.metabolights.model.queue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.utils.PropertiesUtil;
/*
 * Submission Item
 * 
 * Represent a item that has to be submitted to the submission queue
 * Mainly it has to have the file, the user, the accession (only for re submissions) and the public release date
 */
public class SubmissionItem {
	
	private Date publicReleaseDate;
	private MetabolightsUser user;
	private String accession;
	private MultipartFile file;
	
	private static Logger logger = Logger.getLogger(SubmissionItem.class);
	
	final String FILE_NAME_SEP = "~";
	
	public SubmissionItem(MultipartFile file, MetabolightsUser user, Date publicReleaseDate, String accession){
		this.file = file;
		this.user =user;
		this.publicReleaseDate = publicReleaseDate;
		this.accession = accession ==null?"":accession;
	}
	public void submitToQueue() throws IllegalStateException, IOException{
		
		String fullPathFileName = ComposeFullPathFileName();
		
		//Write the file in the file system, in the queue folder
        logger.info("Write the file in the file system "+ fullPathFileName);
        File newFile = new File(fullPathFileName);
        logger.info("The new file is created, now we transfer the file from memory to the filesystem");
        file.transferTo(newFile);
		
		
	}
	private String ComposeFullPathFileName(){
		
		String destinationFileName;
		
		destinationFileName = SubmissionQueue.getQueueFolder() + ComposeFileName();
		
		return destinationFileName;
		
	}
	
	/*
	 * Compose File name
	 * 
	 * Set up file name and unzip folder
 	 * For the queue system the name must have this format:
 	 * USERID~[ACCESSION]~PUBLICRELEASEDATE~ORIGINALFILENAME(with any ~ replaced)
 	 * 
	 * For UPDATES the accession part must be present: 
	 * 163~MTBLS1~20120624~myresubmissionarchive.zip
	 * 
	 * For NEW submissions accession is not present
	 * 163~~20120516~mynewsubmissionarchive.zip
	 */
	private String ComposeFileName(){

		String fileName;
        
		// Format the date to a canonical format (YYYYMMDD)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(publicReleaseDate);
		
		fileName = user.getUserId() + FILE_NAME_SEP + accession + FILE_NAME_SEP + date + FILE_NAME_SEP + file.getOriginalFilename();  
		
		return fileName;
				
		
	}

}
