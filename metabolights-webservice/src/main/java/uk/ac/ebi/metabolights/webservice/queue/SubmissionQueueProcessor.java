/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 5/19/14 2:29 PM
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

//import org.isatools.isatab.gui_invokers.GUIInvokerResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import uk.ac.ebi.metabolights.repository.dao.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.LiteEntity;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.search.service.SearchService;
import uk.ac.ebi.metabolights.webservice.controllers.IndexController;
import uk.ac.ebi.metabolights.webservice.services.AppContext;
import uk.ac.ebi.metabolights.webservice.utils.FileUtil;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;

import java.io.File;

import static java.lang.Thread.sleep;
import static uk.ac.ebi.metabolights.webservice.queue.SubmissionItem.SubissionType;

/*
 * Process a single item in the queue (upload a zip file to the database and move it to the correspondent folder)
 */

@Transactional
public class SubmissionQueueProcessor {

	private static Logger logger = LoggerFactory.getLogger(SubmissionQueueProcessor.class);
    private static String zipOnDemandLocation = PropertiesUtil.getProperty("ondemand");
	private SearchService<Object,LiteEntity> searchService = IndexController.searchService;

	private StudyDAO studyDAO;
	private SubmissionItem si;

	public SubmissionQueueProcessor(SubmissionItem itemToSubmit) throws DAOException {

		studyDAO = DAOFactory.getInstance().getStudyDAO();
		this.si = itemToSubmit;

	}

	public void start() throws Exception{

		try{

			// Check if the process can start
			if (!CanProcessStart()) throw new Exception("Processing (Submission) can't start. See logs for more detailed information.");

			//Check if we process folder is empty
			if (!isProcessFolderEmpty())
			{
				// there is something being processed..we need to wait.
				logger.info("Submission process can't start: Process Folder (" + SubmissionQueue.getProcessFolder() + ") is not empty");
				return;
			}

			// Move it to the process folder
			si.moveFileTo(SubmissionQueue.getProcessFolder(), false);

			// If it's a new study
			if (si.getSubmissionType() == SubissionType.CREATE){
				// Start the upload
				Study newStudy  = create();

				// Inform the user and team.
				AppContext.getEmailService().sendQueuedStudySubmitted(newStudy.getUsers().iterator().next().getEmail(),si.getOriginalFileName() , newStudy.getStudyPublicReleaseDate(), newStudy.getStudyIdentifier());
			}

			// If the file name is empty...the user hasn't provided a file, therefore, only wants to change the public release date.
			else if (si.getSubmissionType()== SubissionType.CHANGE_PUBLIC_RELEASE_DATE){

				updatePublicReleaseDate();
				AppContext.getEmailService().sendQueuedPublicReleaseDateUpdated(si.getUserToken(), si.getAccession(), si.getPublicReleaseDate());

			}
			// If the file name is empty...the user hasn't provided a file, therefore, only wants to change the public release date.
			else if (si.getSubmissionType()==SubissionType.DELETE){

				//Call deletion...
				deleteStudy();

				AppContext.getEmailService().sendStudyDeleted(si.getUserToken(), si.getAccession());

			}
			// It's then an update
			else if (si.getSubmissionType()==SubissionType.UPDATE)
			{

				// Update study
				updateStudy();

				AppContext.getEmailService().sendQueuedStudyUpdated(si.getUserToken(), si.getAccession(), si.getPublicReleaseDate());


			} else {

				logger.warn("Don't know what to do with this submitted item: " + si);
			}

			// Clean the process folder anyway
			cleanProcessFolder();

		}catch(Exception e){

			// There was an error in the submission process...
			si.moveFileTo(SubmissionQueue.getErrorFolder(), true);

            // Clean the process folder anyway
            cleanProcessFolder();

			AppContext.getEmailService().sendSubmissionError(si.getUserToken(), si.getOriginalFileName(), e);
		}


	}

	private void cleanProcessFolder(){
        int times =0;
        final int  MAX_TIMES = 3;

        while ( !FileUtil.deleteDir(new File(SubmissionQueue.getProcessFolder())) && (times < MAX_TIMES)){
            times++;
            try {
                sleep(times *1000);
            } catch (InterruptedException e) {
                logger.debug("For some reason the thread can't go to sleep: " + e.getMessage());
            }
        };
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

        // Trying to fix file system problem, when, for some reason, the folder is locked and can't be deleted.
        // When this happens only the folder remains but it's empty...

        for (File file : processFolder.listFiles()){
            if (file.isDirectory()){

                // loop through the files
                for (File insidefile: file.listFiles()){

                    // If file starts with ".nfs"
                    if (insidefile.getName().startsWith(".nfs")){

                        logger.info(".nfs file inside folder, trying to delete it: " + file.getAbsolutePath() );
                        insidefile.delete();
                    }
                }

                // Try to delete the folder now
                file.delete();

            } else {
                return false;
            }


        }

        // Check now if processfolder is empty
        if (processFolder.listFiles().length==0){
            return true;
        } else {
            return false;
        }

	}
	/**
	 * Upload the IsaTabFile (zip) into BII database replacing the id with our own accession numbers.
	 * @return
	 * @throws Exception
	 */
	private Study create() throws Exception {


		// Unzip the file...
		si.unzip();

		// Persist in the database
		StudyDAO studyDAO = DAOFactory.getInstance().getStudyDAO();

		// Add the study.
		Study newStudy = studyDAO.add(si.getUnzippedFolder(), si.getPublicReleaseDate(), si.getUserToken());

		//Index the study
		searchService.index(newStudy);

		// Delete the original zip file...
		si.getFileQueued().delete();

		//Return the new accession number.
		return newStudy;

	}
	/**
	 * Delete a study from the system (both: Database and file system).
	 * @throws Exception
	 */
	private void deleteStudy() throws Exception {

//	    // Get the IsaTabUploader (and unloader) configured
//		studyDAO = getIsaTabUploader();
//
//		// Unload it from the database, data locations and index.
//	    studyDAO.unloadISATabFile(si.getAccession());
//
//	    // Remove files from our staging folder
//	    File studyFolder = new File(studyDAO.getCurrentStudyFilePath(si.getAccession()));
//
//	    FileUtils.deleteDirectory(studyFolder);


	}
//	/**
//	 * Returns a default configured uploader. After it you may probably need to set:
//	 * UnzipFolder
//	 * publicDate : Format expected --> dd-MMM-yyyy
//	 * @return
//	 * @throws Exception
//	 */
//	private IsaTabUploader getIsaTabUploader() throws Exception{
//
//		// Set common properties
//		studyDAO.setOwner(si.getUserId());
//		studyDAO.setCopyToPrivateFolder(privateFtpStageLocation);
//		studyDAO.setCopyToPublicFolder(publicFtpStageLocation);
//		studyDAO.setStatus(si.getStatus());
//
//		// Get the path for the config folder (where the hibernate properties for the import layer are).
//        String configPath = PropertiesUtil.getProperty("isatabuploaderconfig"); //SubmissionController.class.getClassLoader().getResource("").getPath();
//        studyDAO.setDBConfigPath(configPath);
//
//		// Get today's date.
//		Calendar currentDate = Calendar.getInstance();
//		SimpleDateFormat isaTabFormatter = new SimpleDateFormat("yyyy-MM-dd"); // New ISAtab format (1.4)
//		String submissionDate = isaTabFormatter.format(currentDate.getTime());
//		studyDAO.setSubmissionDate(submissionDate);
//
//		// For deletion we don't have a public release date
//		if (si.getPublicReleaseDate() != null)
//		{
//			studyDAO.setPublicDate(isaTabFormatter.format(si.getPublicReleaseDate()));
//		}
//
//		// Set properties related with the file itself...
//		// Calculate the unzip folder (remove the extension + .)
//		String unzipFolder = StringUtils.truncate(si.getFileQueued().getAbsolutePath(), 4);
//
//		studyDAO.setUnzipFolder(unzipFolder);
//
//		studyDAO.setIsaTabFile(si.getFileQueued().getAbsolutePath());
//
//		return studyDAO;
//
//	}
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
	 * @return
	 * @throws Exception
	 */


	public void updateStudy() throws Exception{


//		// Define the back up path of the existing file
//		File backup = new File(SubmissionQueue.getBackUpFolder() + si.getAccession());
//		boolean needRestore = false;
//
//		try {
//
//			// Get the uploader configured...
//			IsaTabUploader studyDAO = getIsaTabUploader();
//
//			// Check that the new zip file has the same studyID (this will unzip the file)
//			Map<String,String> zipValues = studyDAO.getStudyFields(si.getFileQueued(), new String[]{"Study Identifier"});
//
//			String newStudyId = zipValues.get("Study Identifier");
//
//            //TODO, add a check for both the submitted studyid and the MTBLS id
//			//If Ids do not match...
//			if (!si.getAccession().equals(newStudyId)){
//
//				// Get study XRefs...
//				List<MetaboLightsStudyXRef> studyXrefs = AppContext.getAccessionService().getStudyXRefs(si.getAccession());
//
//				boolean found = false;
//
//				for (MetaboLightsStudyXRef studyXRef:studyXrefs){
//					if (studyXRef.getSubmittedId().equals(newStudyId)){
//						found = true;
//					}
//				}
//
//				if (!found) {
//					throw new Exception(PropertyLookUpService.getMessage("msg.validation.studyIdDoNotMatch", newStudyId, si.getAccession()));
//				}
//
//			}
//
//			// Check there is a previous back up
//			if (backup.exists()){
//				throw new Exception(PropertyLookUpService.getMessage("msg.validation.backupFileExists", si.getAccession()));
//			}
//
//			//Validate the new file
//			try{
//				// It has to be a directory...the call to getStudyFile has unzipped the file to unzip folder. We will use it.
//				studyDAO.validate(studyDAO.getUnzipFolder());
//
//			} catch (Exception e){
//
//				List<TabLoggingEventWrapper> isaTabLog = studyDAO.getSimpleManager().getErrors();
//				throw new Exception(PropertyLookUpService.getMessage("msg.validation.invalid") + "\n\nERROR:" + e.getMessage() + "\n\n" + isaTabLog.toString() );
//
//			}
//
//			// Make the backup...
//			File currentFile = new File(studyDAO.getCurrentStudyFilePath(si.getAccession()));
//			// NOTE: This is reseting the status based on the place the study it's been found.(Which makes it fail when the study is private and need to be public.
//			studyDAO.setStatus(si.getStatus());
//
//			// Now it's unzipped...not a file anymore
//			//FileUtils.copyFile(currentFile, backup);
//			FileUtils.moveDirectory(currentFile, backup);
//
//			// Unload the study, this will remove the file too.
//			logger.info("Deleting previous study " + si.getAccession());
//			studyDAO.unloadISATabFile(si.getAccession());
//
//			// From this point restoring the backup must be done in case of an exception
//			needRestore = true;
//
//			// upload the new study with the new date
//			// NOTE: this will unzip again the file (done previously in the getStudyFields
//			// To avoid unziping it twice (specially for large files) we can set the isaTabFile property
//			// to the unzipped folder and it should work...
//			studyDAO.setIsaTabFile(studyDAO.getUnzipFolder());
//
//			// To test the restore backup
//			//if (needRestore){throw new Exception("fake exception");}
//
//			logger.info("Uploading new study");
//
//			studyDAO.UploadWithoutIdReplacement(si.getAccession());
//
//			// Remove the backup
//			needRestore = false;
//
//
//			FileUtils.deleteDirectory(backup);
//            deleteZippedFile(si.getAccession());
//
//
//		} catch (Exception e){
//
//			// If there is a need of restoring the backup
//			if (needRestore){
//
//
//				// TODO  Restore process...
////				// Calculate the previous status
////				VisibilityStatus oldStatus = params.study.getIsPublic()?VisibilityStatus.PUBLIC: VisibilityStatus.PRIVATE;
////
////
//////				// Error:
//////				Caused by: java.lang.InterruptedException: sleep interrupted
//////				at java.lang.Thread.sleep(Native Method)
//////				at org.isatools.isatab.commandline.AbstractImportLayerShellCommand.createDataLocationManager(AbstractImportLayerShellCommand.java:402)
////
////				// Get the uploader configured
////				si.getFileQueued()
////
////				IsaTabUploader studyDAO = getIsaTabUploader(backup.getAbsolutePath(), oldStatus, null);
////
////				// Upload the old study
////				studyDAO.UploadWithoutIdReplacement(si.getAccession());
////
////				// Delete the backup
////				backup.delete();
//
//				// TODO: Send email. Return a different response...
//				throw new Exception("There was an error while updating the study. We have restored the previous experiment. " + e.getMessage());
//
//			}else{
//				throw e;
//			}
//		}

	}

    private void deleteZippedFile(String study){

        File zippedStudy = new File (zipOnDemandLocation + study + ".zip");

        // If it exists...delete it.
        if (zippedStudy.exists()) zippedStudy.delete();
    }

	/*
    Update the studies release date and status
	 */
    @Transactional
	public void updatePublicReleaseDate() throws Exception {


//	    // Create the uploader
//	    IsaTabUploader studyDAO = new IsaTabUploader();
//
//	    // Change the status
//	    try {
//
//	        // ************************
//	        // Get the study from Database first...
//	        // ************************
//	        // Get the study object
//	        Study biiStudy = AppContext.getStudyService().getBiiStudy(si.getAccession(),false, true);
//
//	        // Set the new Public Release Date
//	        //biiStudy.setReleaseDate(si.getPublicReleaseDate());
//	        Calendar cal = Calendar.getInstance();
//	        cal.setTime(si.getPublicReleaseDate());
//	        //cal.add(Calendar.DATE, 1);
//	        biiStudy.setReleaseDate(cal.getTime());
//
//            VisibilityStatus oldStatus = biiStudy.getStatus();
//	        biiStudy.setStatus(si.getStatus());
//            VisibilityStatus newStatus = biiStudy.getStatus();
//
//	        logger.info("Updating public release date in study (database)");
//
//            // Save it
//            AppContext.getStudyService().update(biiStudy); // update the database
//
//            if (oldStatus == VisibilityStatus.PRIVATE && newStatus == VisibilityStatus.PUBLIC ){
//                studyDAO.setCopyToPrivateFolder(privateFtpStageLocation);
//                studyDAO.setCopyToPublicFolder(publicFtpStageLocation);
//                //filesMovedPrivToPub = true;
//            } else if(oldStatus == VisibilityStatus.PUBLIC && newStatus == VisibilityStatus.PRIVATE){
//                studyDAO.setCopyToPrivateFolder(publicFtpLocation);
//                studyDAO.setCopyToPublicFolder(privateFtpStageLocation);
//                //filesMovedPubToPriv = true;
//                logger.info("Setting the copy folders for copying from public FTP to private staging.");
//            }  else if(oldStatus == VisibilityStatus.PRIVATE && newStatus == VisibilityStatus.PRIVATE) {
//                studyDAO.setCopyToPrivateFolder(privateFtpStageLocation);
//                studyDAO.setCopyToPublicFolder(publicFtpStageLocation);
//                PrivToPriv = true;
//                logger.info("Changing only public release date in a Private study");
//            }
//
//            //Since now we are storing the studies unzipped....we check if the folder exists
//            File studyFolder = new File (studyDAO.getStudyFilePath(si.getAccession(), VisibilityStatus.PUBLIC));
//
//            // If not in public folder...
//            if (!studyFolder.exists()){
//
//                // Try it in the private
//                studyFolder = new File (studyDAO.getStudyFilePath(si.getAccession(), VisibilityStatus.PRIVATE));
//
//                // Check if it exists
//                if (!studyFolder.exists()){
//
//                    // Throw an exception
//                    throw new FileNotFoundException (PropertyLookUpService.getMessage("msg.makestudypublic.nofilefound", si.getAccession()));
//
//                }
//            }
//
//	        // ************************
//	        // Index it...
//	        // ************************
//	        //Get the path for the config folder (where the hibernate properties for the import layer are).
//	        String configPath = PropertiesUtil.getProperty("isatabuploaderconfig") ; //SubmissionQueueProcessor.class.getClassLoader().getResource("").getPath();      //TODO, change to use JNDI parameters
//
//	        // Set the config folder, and the ftp folders
//	        studyDAO.setDBConfigPath(configPath);
//
//
//
//	        // If the new status is public...
//	        if (si.getStatus() == VisibilityStatus.PUBLIC){
//
//	            // Move the file from the private
//	            studyDAO.moveFile(si.getAccession(), VisibilityStatus.PRIVATE);  //Need to pass the old status, not the new public status
//
//	            // Change the permissions
//
//	        } else if(si.getStatus() == VisibilityStatus.PRIVATE && PrivToPriv == false){ // If the new status is private...
//
//                // Move the file from the public
//                //studyDAO.moveFile(si.getAccession(), VisibilityStatus.PRIVATE);
//                studyDAO.copyFilesFromPubToPriv(si.getAccession(), VisibilityStatus.PRIVATE);
//            }
//
//            studyDAO.setUnzipFolder(studyFolder.getAbsolutePath());
//
//            // ************************
//            // Change the investigation file
//            // ************************
//            // Create the replacement Hash
//
//            HashMap<String,String> replacementHash = new HashMap<String,String>();
//
//            // Add the Public release date field with the new value
//            replacementHash.put("Study Public Release Date", new SimpleDateFormat("yyyy-MM-dd").format(si.getPublicReleaseDate()));
//
//            logger.info("Replacing Study Public Release Date in zip file. with " + si.getPublicReleaseDate());
//
//            // Call the replacement method...
//            studyDAO.changeStudyFields(si.getAccession(), replacementHash);
//
//            // reindex the study...
//            studyDAO.reindexStudies(si.getAccession());
//
//            // Delete the zipped file
//            deleteZippedFile(si.getAccession());
//
//	    } catch (Exception e) {
//	    	e.printStackTrace();
//	        throw new Exception("updatePublicReleaseDate error: " + e.getMessage());
//	    }
	}
}
