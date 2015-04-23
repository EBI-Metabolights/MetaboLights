/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 5/16/14 11:16 AM
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

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.metabolights.webservice.utils.Zipper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * Submission Item
 * 
 * Represent a item that has to be submitted to the submission queue
 * Mainly it has to have the file, the user, the accession (only for re submissions) and the public release date
 *
 * PATTERN:
 *
 * USER~[ACCESSION]~DATE~(FILENAME or ACTION)
 *
 *  NEW SUBMISSIONS (no accession):  me@misite.com~~20150101~NewStudy.zip
 *  UPDATES (with accession):  me@misite.com~MTBLS13~20150101~updatedStudy.zip
 *  PUBLIC RELEASE DATE CHANGE:  me@misite.com~M TBLS13~20150301~PRDupdate.zip
 *  DELETE STUDY:  me@misite.com~MTBLS13~~DELETE.zip
 */
public class SubmissionItem {
	
	private Date queuedDate;
	private Date publicReleaseDate;
	private String userId;
	private String accession;
	private String originalFileName;
	private MultipartFile fileToQueue;
	private File fileQueued;
	private File unzippedFolder;
    private Boolean isPublic;
	
	private static Logger logger = LoggerFactory.getLogger(SubmissionItem.class);
	
	static final String FILE_NAME_SEP = "~";
	static final String FILE_NAME_FOR_PRD_UPDATES = "PRDupdatedate.zip";
	static final String FILE_NAME_FOR_DELETIONS = "DELETE.zip";
    //static final String FILE_NAME_FOR_PUBLIC_TO_PRIVATE = "PTPStudy.zip";
	
	// Format the date to a canonical format (YYYYMMDD)
	static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");


	public File getUnzippedFolder() {
		return unzippedFolder;
	}

	public File unzip() throws IOException, ArchiveException {

		unzippedFolder = Zipper.unzip2(fileQueued.getAbsolutePath());

		return unzippedFolder;
	}


	public enum SubissionType{
		CREATE,
		UPDATE,
		DELETE,
		CHANGE_PUBLIC_RELEASE_DATE,
		CHANGE_STATUS

	}
	
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
	
	public String getUserToken() {
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

		if (!fileQueued.exists()) {
			logger.error("Can't move submitted file {} to {}. File does not exists.");
		}

        File destination =new File(destinationFolder + fileQueued.getName());

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp = sdf.format(cal.getTime());

        if (isMoveToErrorFolder) {
            destination = new File(destinationFolder + "TS_" +timeStamp + "~" + fileQueued.getName());
        }

		if (fileQueued.isDirectory()){
			FileUtils.moveDirectory(fileQueued,destination);
		} else {
			FileUtils.moveFile(fileQueued, destination);
		}

		
		fileQueued = destination;
		
	}

	public VisibilityStatus getStatus(){
		
		// If there is no public release date is because it is a deletion..return private.
		if (publicReleaseDate == null) return VisibilityStatus.PRIVATE;
		
		return (getPublicReleaseDate().before(new Date())?VisibilityStatus.PUBLIC:VisibilityStatus.PRIVATE);
	}

	public SubissionType getSubmissionType(){


		if (getAccession().isEmpty()) {
			return SubissionType.CREATE;
		} else if (getOriginalFileName().equals(SubmissionItem.FILE_NAME_FOR_PRD_UPDATES)){
			return SubissionType.CHANGE_PUBLIC_RELEASE_DATE;
		} else if (getOriginalFileName().equals(SubmissionItem.FILE_NAME_FOR_DELETIONS)){
			return SubissionType.DELETE;
		} else {
			return SubissionType.UPDATE;
		}
	}

	@Override
	public String toString(){
		
		return (String.format("Submission's info:\nType: %s\nUserToken: %s\nAccession: %s\nRelease date: %s\nOriginal file name: %s\n", getSubmissionType().name(),userId, accession==null?"Empty":accession,publicReleaseDate,originalFileName));
		
	}

}
