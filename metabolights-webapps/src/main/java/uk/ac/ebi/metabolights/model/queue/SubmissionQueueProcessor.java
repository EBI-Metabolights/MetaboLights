package uk.ac.ebi.metabolights.model.queue;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.isatools.tablib.utils.logging.TabLoggingEventWrapper;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.metabolights.controller.SubmissionController;
import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabUploader;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.utils.FileUtil;
import uk.ac.ebi.metabolights.utils.PropertiesUtil;
import uk.ac.ebi.metabolights.utils.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Process a single item in the queue (upload a zip file to the database and move it to the correspondent folder)
 */

@Transactional
public class SubmissionQueueProcessor {

	private static Logger logger = Logger.getLogger(SubmissionQueueProcessor.class);
	private static String publicFtpLocation = PropertiesUtil.getProperty("publicFtpStageLocation");
	private static String privateFtpLocation = PropertiesUtil.getProperty("privateFtpStageLocation");
	
	
	private IsaTabUploader itu = new IsaTabUploader();
	private SubmissionItem si;
	private HashMap<String,String> IDs; 
	
	public SubmissionQueueProcessor(SubmissionItem ItemToSubmit) {
		
		this.si = ItemToSubmit;

	}
	
	public void start() throws Exception{
		
		try{
		
			// Check if the process can start
			if (!CanProcessStart()) throw new Exception("Proccessing (Submission) can't start. See logs for more detailed information.");
			
			
			//Check if we process folder is empty
			if (!isProcessFolderEmpty())
			{
				// there is something being processed..we need to wait.
				logger.info("Submission process can't start: Process Folder (" + SubmissionQueue.getProcessFolder() + ") is not empty");
				return;
			}
		
			// Move it to the process folder
			si.moveFileTo(SubmissionQueue.getProcessFolder());
			
			// If it's a new study
			if (si.getAccession().isEmpty())
			{
				// Start the upload
				IDs = uploadToBii();
				
				// Inform the user and team.
				AppContext.getEmailService().sendQueuedStudySubmitted(si.getUserId(),si.getOriginalFileName() , si.getPublicReleaseDate(), IDs.values().iterator().next());
			
				
			}
			// If the file name is empty...the user hasn't provided a file, therefore, only wants to change the public release date.
			else if (si.getOriginalFileName().equals(SubmissionItem.FILE_NAME_FOR_PRD_UPDATES))
			{
				
				updatePublicReleaseDate();
				AppContext.getEmailService().sendQueuedPublicReleaseDateUpdated(si.getUserId(), si.getAccession(), si.getPublicReleaseDate());
				
			}// It's then an update
			else
			{
				
				// Update study
				updateStudy();
				
				AppContext.getEmailService().sendQueuedStudyUpdated(si.getUserId(), si.getAccession()	,si.getPublicReleaseDate());
				
				
			}
			

		}catch(Exception e){
			
			// There was an error in the submission process...
			si.moveFileTo(SubmissionQueue.getErrorFolder());
			AppContext.getEmailService().sendSubmissionError(si.getUserId(), si.getOriginalFileName(), e.getMessage());
			
			
		} finally{
		
			// Clean the process folder anyway
			cleanProcessFolder();
		
		}
		
		
	}
	
	public HashMap<String,String> getIDs(){
		return IDs;
	}
	private void cleanProcessFolder(){
		FileUtil.deleteDir(new File(SubmissionQueue.getProcessFolder()));
	}
	
	public boolean CanProcessStart(){
		
		int errors=0;
		
		//Check if we have a submission Item.
		if (si == null) {
			errors++;
			logger.info("Submission process can't start: No SubmissionItem assigned");
		}else{

			//Check if the submission file exists.
			if (!si.getFileQueued().exists()) {
				errors++;
				logger.info("Submission process can't start: File (" + si.getFileQueued().getAbsolutePath() + ") doesn't exist");
			}

		}
		
		return (errors==0);
		
	}
	public static boolean isProcessFolderEmpty(){
		File processFolder = new File (SubmissionQueue.getProcessFolder());
		
		return (processFolder.list().length ==0);
	}
	/**
	 * Upload the IsaTabFile (zip) into BII database replacing the id with our own accession numbers.
	 * @param isaTabFile
	 * @param status
	 * @return
	 * @throws Exception 
	 */
	private HashMap<String,String> uploadToBii () throws Exception{
		
	    
		// Upload the file to bii
		
		//IsaTabUploader itu = getIsaTabUploader(isaTabFile, status, publicDate);
        itu = getIsaTabUploader();
		
		// Upload the file
		return itu.Upload();
	}
	/**
	 * Returns a default configured uploader. After it you may probably need to set:
	 * UnzipFolder
	 * publicDate : Format expected --> dd-MMM-yyyy
	 * @return
	 * @throws Exception 
	 */
	private IsaTabUploader getIsaTabUploader() throws Exception{

		
		// Calculate the visibility based on the Public release date
		VisibilityStatus status =  si.getStatus();
		
		// Get the path for the config folder (where the hibernate properties for the import layer are).
        String configPath = SubmissionController.class.getClassLoader().getResource("").getPath();

		// Get today's date.
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat isaTabFormatter = new SimpleDateFormat("yyyy-MM-dd"); // New ISAtab format (1.4)
		String submissionDate = isaTabFormatter.format(currentDate.getTime());

		// Format public date to IsaTab format date
		String publicDateS = isaTabFormatter.format(si.getPublicReleaseDate());
		
		// Set common properties
		itu.setOwner(si.getUserId());
		itu.setCopyToPrivateFolder(privateFtpLocation);
		itu.setCopyToPublicFolder(publicFtpLocation);
		itu.setDBConfigPath(configPath);
		itu.setSubmissionDate(submissionDate);

		// Set properties related with the file itself...
		// Calculate the unzip folder (remove the extension + .)
		String unzipFolder = StringUtils.truncate(si.getFileQueued().getAbsolutePath(), 4);
		itu.setUnzipFolder(unzipFolder);
		itu.setIsaTabFile(si.getFileQueued().getAbsolutePath());
		itu.setPublicDate(publicDateS);
		itu.setStatus(status);
		
		return itu;
		
	}
	/**
	 * Re-submit process:
		Each step must successfully validate, if not then stop and display an error
		no need to allocate a new MTBLS id during this process
		OK - Check that the logged in user owns the chosen study
		OK - Check that the study is PRIVATE (? what do we think ?).  This could stop submitters from "nullifying" a public study.
		OK - Unzip the new zipfile and check that the study id is matching (MTBLS id)
		OK - Update new zipfile with Public date from the resubmission form
		OK - Unload the old study
		OK - IF SUCCESSFULLY UNLOADED =  DO NOT Remove old study zipfile
		OK - IF ERROR = Reupload the old zipfile, DO NOT Remove old study zipfile
		OK - Upload the new study (includes Lucene re-index)
		OK - IF ERROR = Reupload the old zipfile, DO NOT Remove old study zipfile
		OK - Copy the new zipfile to the correct folder (public or private locations)
		OK - Remove old study zipfile
		Display a success or error page to the submitter.  Email metabolights-help and submitter with results
	 * @param file
	 * @param study
	 * @param publicReleaseDateS
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	
	public void updateStudy() throws Exception{
		

		// Check we have a study id
		
		
		// Define the back up path of the existing file 
		File backup = new File(SubmissionQueue.getBackUpFolder() + si.getAccession() + ".zip");
		boolean needRestore = false;
		
		try {
	
			// Get the uploader configured...
			IsaTabUploader itu =getIsaTabUploader();
						
			// Check that the new zip file has the same studyID (this will unzip the file)
			Map<String,String> zipValues = itu.getStudyFields(si.getFileQueued(), new String[]{"Study Identifier"});
			
			String newStudyId = zipValues.get("Study Identifier");
			
			// If Ids do not match...
			if (!si.getAccession().equals(newStudyId)){
				
				throw new Exception(PropertyLookup.getMessage("msg.validation.studyIdDoNotMatch",newStudyId,si.getAccession()));
				
			}
			
			// Check there is a previous back up
			if (backup.exists()){
				throw new Exception(PropertyLookup.getMessage("msg.validation.backupFileExists", si.getAccession()));
			}
			
			//Validate the new file
			try{
				// It has to be a directory...the call to getStudyFile has unzipped the file to unzip folder. We will use it.
				itu.validate(itu.getUnzipFolder());
				
			}catch (Exception e){
				
				List<TabLoggingEventWrapper> isaTabLog = itu.getSimpleManager().getLastLog();
				throw new Exception(PropertyLookup.getMessage("msg.validation.invalid") + "\n\nERROR:" + e.getMessage() + "\n\n" + isaTabLog.toString() );
				
			}
			
			// Make the backup...
			File currentFile = new File(itu.getCurrentStudyFilePath(si.getAccession()));
			FileUtils.copyFile(currentFile, backup);
			
			// Unload the study, this will remove the file too.
			logger.info("Deleting previous study " + si.getAccession());
			itu.unloadISATabFile(si.getAccession());
			
			// From this point restoring the backup must be done in case of an exception
			needRestore = true;
			
			// upload the new study with the new date
			// NOTE: this will unzip again the file (done previously in the getStudyFields
			// To avoid unziping it twice (specially for large files) we can set the isaTabFile property
			// to the unzipped folder and it should work...
			itu.setIsaTabFile(itu.getUnzipFolder());
			
			// To test the restore backup
			//if (needRestore){throw new Exception("fake exception");}
			
			logger.info("Uploading new study"); 
			
			itu.UploadWithoutIdReplacement(si.getAccession());

			// Remove the backup
			needRestore = false;
			backup.delete();
		
			
		} catch (Exception e){
			
			// If there is a need of restoring the backup
			if (needRestore){
			 
				
				// TODO  Restore process...
//				// Calculate the previous status
//				VisibilityStatus oldStatus = params.study.getIsPublic()?VisibilityStatus.PUBLIC: VisibilityStatus.PRIVATE;
//
//				
////				// Error:
////				Caused by: java.lang.InterruptedException: sleep interrupted
////				at java.lang.Thread.sleep(Native Method)
////				at org.isatools.isatab.commandline.AbstractImportLayerShellCommand.createDataLocationManager(AbstractImportLayerShellCommand.java:402)
//				
//				// Get the uploader configured
//				si.getFileQueued()
//				
//				IsaTabUploader itu = getIsaTabUploader(backup.getAbsolutePath(), oldStatus, null);
//				
//				// Upload the old study
//				itu.UploadWithoutIdReplacement(si.getAccession());
//				
//				// Delete the backup
//				backup.delete();
				
				// TODO: Send email. Return a different response...
				throw new Exception("There was an error while updating the study. We have restored the previous experiment. " + e.getMessage());
				
			}else{
				throw e;
			}
		}
		
	}
	
	/*
    Update the studies release date and status
	 */
    @Transactional
	public void updatePublicReleaseDate() throws Exception {

	    // Add the user id to the unzip folder
	    //String unzipFolder = uploadDirectory + userId + "/" + study;
	
	    // Create the uploader
	    IsaTabUploader itu = new IsaTabUploader();
	
	    // Set properties for file copying...
	    itu.setCopyToPrivateFolder(privateFtpLocation);
	    itu.setCopyToPublicFolder(publicFtpLocation);
	
	    // Change the status
	    try {
	        
	    	//Check if the zip file exists before changing anything else
	        File zipFile = new File (itu.getStudyFilePath(si.getAccession(), VisibilityStatus.PUBLIC));
	
	        // If not in public folder...
	        if (!zipFile.exists()){
	
	            // Try it in the private
	            zipFile = new File (itu.getStudyFilePath(si.getAccession(), VisibilityStatus.PRIVATE));
	
	            // Check if it exists
	            if (!zipFile.exists()){
	
	                // Throw an exception
	                throw new FileNotFoundException (PropertyLookup.getMessage("msg.makestudypublic.nofilefound", si.getAccession()));
	
	            }
	        }
	
	
	        // ************************
	        // Update the database first...
	        // ************************
	        // Get the study object
	        Study biiStudy = AppContext.getStudyService().getBiiStudy(si.getAccession(),false, true);
	
	        // Set the new Public Release Date
	        //NOTE: We need to increase the date by 1 date, since, for some weird reasons, the update decrease the date always by one.
	        //biiStudy.setReleaseDate(si.getPublicReleaseDate());
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(si.getPublicReleaseDate());
	        cal.add(Calendar.DATE, 1);
	        biiStudy.setReleaseDate(cal.getTime());
	        biiStudy.setStatus(si.getStatus());
	
	        logger.info("Updating public release date in study (database)");
	        // Save it
	        AppContext.getStudyService().update(biiStudy);
	
	
	        // ************************
	        // Index it...
	        // ************************
	        //Get the path for the config folder (where the hibernate properties for the import layer are).
	        //String configPath = UpdateStudyController.class.getClassLoader().getResource("").getPath() + "biiconfig/";
	        String configPath = SubmissionQueueProcessor.class.getClassLoader().getResource("").getPath();
	
	        // Set the config folder, and the ftp folders
	        itu.setDBConfigPath(configPath);
	
	        // reindex the study...
	        itu.reindexStudies(si.getAccession());
	
	
	        // ************************
	        // Change the zip file
	        // ************************
	        // Set the unzip folder
	        itu.setUnzipFolder(StringUtils.truncate(si.getFileQueued().getAbsolutePath()));
	
	        // Create the replacement Hash
	        HashMap<String,String> replacementHash = new HashMap<String,String>();
	
	        // Add the Public release date field with the new value
	        replacementHash.put("Study Public Release Date", new SimpleDateFormat("yyyy-MM-dd").format(si.getPublicReleaseDate()));
	
	        logger.info("Replacing Study Public Release Date in zip file. with " + si.getPublicReleaseDate());
	        
	        // Call the replacement method...it will unzip, replace and zip it again
	        itu.changeStudyFields(si.getAccession(), replacementHash);
	
	        // If the new status is public...
	        if (si.getStatus() == VisibilityStatus.PUBLIC){
	
	            // Move the file from the private
	            itu.moveFile(si.getAccession(), VisibilityStatus.PRIVATE);  //Need to pass the old status, not the new public status
	
	            // Change the permissions
	            String studyPath = itu.getStudyFilePath(si.getAccession(), VisibilityStatus.PUBLIC);
	            itu.changeFilePermissions(studyPath, VisibilityStatus.PUBLIC);
	        }
	
	        
	
	
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	    } catch (IsaTabException e) {
	        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	    } catch (Exception e) {
	    	e.printStackTrace();
	        throw new Exception("updatePublicReleaseDate error: " + e.getMessage());
	    }
	
	
	}

}
