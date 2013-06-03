package uk.ac.ebi.metabolights.model.queue;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
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
    private Boolean isPublic;
	
	private static Logger logger = Logger.getLogger(SubmissionItem.class);
	
	static final String FILE_NAME_SEP = "~";
	static final String FILE_NAME_FOR_PRD_UPDATES = "PRDupdatedate.zip";
	static final String FILE_NAME_FOR_DELETIONS = "DELETE.zip";
    //static final String FILE_NAME_FOR_PUBLIC_TO_PRIVATE = "PTPStudy.zip";
	
	// Format the date to a canonical format (YYYYMMDD)
	static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	
	public SubmissionItem(MultipartFile file, String user, Date publicReleaseDate, String accession, boolean isPublic){
		this.fileToQueue = file;
		this.userId =user;
		this.publicReleaseDate = publicReleaseDate;
		this.accession = accession ==null?"":accession;
        this.isPublic = isPublic;
		
		// If there is no file could be a Public release date update or a deletion...
		if (file == null)
		{
			//If date is null...then is a deletion
			if (publicReleaseDate == null)
			{
				this.originalFileName = FILE_NAME_FOR_DELETIONS;
			} else {
				this.originalFileName = FILE_NAME_FOR_PRD_UPDATES;
			}
		}else{
			this.originalFileName = file.getOriginalFilename();
		}
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
        
        // For new files or updates we should have a file, but not for updating public release date
        if (fileToQueue != null)
        {
        	logger.info("The new file is created, now we transfer the file from memory to the filesystem");
        	fileToQueue.transferTo(newFile);
        }else{
        	newFile.createNewFile();
        }
        
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
 	 * USERNAME~[ACCESSION]~PUBLICRELEASEDATE~ORIGINALFILENAME(with any ~ replaced)
 	 * 
	 * For UPDATES the accession part must be present: 
	 * conesa@ebi.ac.uk~MTBLS1~20120624~myresubmissionarchive.zip
	 * 
	 * For NEW submissions accession is not present
	 * conesa@ebi.ac.uk~~20120516~mynewsubmissionarchive.zip
	 * 
	 * For updating "Public Release Date" (No file is provided).
	 * conesa@ebi.ac.uk~MTBLS1~20120516~PRDupdatedate.zip
	 */
    String ComposeFileName(){

        String date = publicReleaseDate==null?"":sdf.format(publicReleaseDate);
        
		String fileName = userId + FILE_NAME_SEP + accession + FILE_NAME_SEP + date + FILE_NAME_SEP + this.originalFileName;  
		
		return fileName;

	}
	/*
	 * De-Compose File name
	 * 
	 * de compose file an already submitted file ans get userid, date,...
 	 * For the queue system the name must have this format:
 	 * USERNAME~[ACCESSION]~PUBLICRELEASEDATE~ORIGINALFILENAME(with any ~ replaced)
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
		publicReleaseDate = properties[2].equalsIgnoreCase("")?null:sdf.parse(properties[2]);
		originalFileName = properties[3];
	}
	
	public void moveFileTo(String destinationFolder, Boolean isMoveToErrorFolder) throws IOException{

        File destination =new File(destinationFolder + fileQueued.getName());

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp = sdf.format(cal.getTime());

        if (isMoveToErrorFolder) {
            destination = new File(destinationFolder + "TS_" +timeStamp + "~" + fileQueued.getName());
        }

		FileUtils.moveFile(fileQueued, destination);
		
		fileQueued = destination;
		
	}

	public VisibilityStatus getStatus(){
		
		// If there is no public release date is because it is a deletion..return private.
		if (publicReleaseDate == null) return VisibilityStatus.PRIVATE;
		
		return (getPublicReleaseDate().before(new Date())?VisibilityStatus.PUBLIC:VisibilityStatus.PRIVATE);
	}

	@Override
	public String toString(){
		
		return (String.format("UserID: %s\nAccession: %s\nRelease date: %s\nOriginal file name: %s\n", userId, accession==null?"NEW":accession,publicReleaseDate,originalFileName));
		
	}
}
