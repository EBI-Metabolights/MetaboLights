package uk.ac.ebi.metabolights.model.queue;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
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
	
	private Date queuedDate;
	private Date publicReleaseDate;
	private String userId;
	private String accession;
	private String originalFileName;
	private MultipartFile fileToQueue;
	private File fileQueued;
	
	private static Logger logger = Logger.getLogger(SubmissionItem.class);
	
	static final String FILE_NAME_SEP = "~";
	// Format the date to a canonical format (YYYYMMDD)
	static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	
	public SubmissionItem(MultipartFile file, MetabolightsUser user, Date publicReleaseDate, String accession){
		this.fileToQueue = file;
		this.userId =user.getUserName().toString();
		this.publicReleaseDate = publicReleaseDate;
		this.accession = accession ==null?"":accession;
		this.originalFileName = file.getOriginalFilename();
	}
	public SubmissionItem (File fileQueued) throws ParseException{
		
		this.fileQueued = fileQueued;
		
		DecomposeFileName(fileQueued.getName());
		
	}
	
	public String getUserId() {
		return userId;
	}
	public String getAccession() {
		return accession;
	}
	public File getFileQueued() {
		return fileQueued;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public Date getPublicReleaseDate(){
		return publicReleaseDate;
	}
	public void submitToQueue() throws IllegalStateException, IOException{
		
		String fullPathFileName = ComposeFullPathFileName();
		
		//Write the file in the file system, in the queue folder
        logger.info("Write the file in the file system "+ fullPathFileName);
        File newFile = new File(fullPathFileName);
        logger.info("The new file is created, now we transfer the file from memory to the filesystem");
        fileToQueue.transferTo(newFile);
        fileQueued = newFile;
		
		
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

        String date = sdf.format(publicReleaseDate);
		
		fileName = userId + FILE_NAME_SEP + accession + FILE_NAME_SEP + date + FILE_NAME_SEP + this.originalFileName;  
		
		return fileName;
				
		
	}
	/*
	 * De-Compose File name
	 * 
	 * de compose file an already submitted file ans get userid, date,...
 	 * For the queue system the name must have this format:
 	 * USERID~[ACCESSION]~PUBLICRELEASEDATE~ORIGINALFILENAME(with any ~ replaced)
 	 * 
	 * For UPDATES the accession part must be present: 
	 * conesa@ebi.ac.uk~MTBLS1~20120624~myresubmissionarchive.zip
	 * 
	 * For NEW submissions accession is not present
	 * conesa@ebi.ac.uk~~20120516~mynewsubmissionarchive.zip
	 */
	private void DecomposeFileName(String fileName) throws ParseException{

		
		// Split the name using the file name separator
		String[] properties = fileName.split(FILE_NAME_SEP);
		
		// First item should be the user
		userId = properties[0];
		accession = properties[1];
		publicReleaseDate = sdf.parse(properties[2]);
		originalFileName = properties[3];
		
		
		
	}
	
	public void moveFileTo(String destinationFolder) throws IOException{
		
		
		File destination =new File(destinationFolder + fileQueued.getName());
		
		FileUtils.moveFile(fileQueued, destination);
		
		fileQueued = destination;
		
	}
	@Override
	public String toString(){
		
		return (String.format("UserID: %s\nAccession: %s\nRelease date: %s\nOriginal file name: %s\n", userId, accession==null?"NEW":accession,publicReleaseDate,originalFileName));
		
	}
	

}
