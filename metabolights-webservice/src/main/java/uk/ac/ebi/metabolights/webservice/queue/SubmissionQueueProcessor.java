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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import uk.ac.ebi.metabolights.repository.dao.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.search.service.imp.es.ElasticSearchService;
//import uk.ac.ebi.metabolights.webservice.client.Metabolights---WsClient;
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

	private ElasticSearchService searchService = SubmissionQueueManager.searchService;

	private StudyDAO studyDAO = null;
	private SubmissionItem si;

	public SubmissionQueueProcessor(SubmissionItem itemToSubmit) throws DAOException {

		this.studyDAO = DAOFactory.getInstance().getStudyDAO();
		this.si = itemToSubmit;

	}

	public void start() throws Exception {

		try {

			// Check if the process can start
			if (!canProcessStart())
				throw new Exception("Processing (Submission) can't start. See logs for more detailed information.");

			//Check if we process folder is empty
			if (!isProcessFolderEmpty()) {
				// there is something being processed..we need to wait.
				logger.info("Submission process can't start: Process Folder (" + SubmissionQueue.getProcessFolder() + ") is not empty");
				return;
			}

					// Move it to the process folders
			si.moveFileTo(SubmissionQueue.getProcessFolder(), false);

			// If it's a new study
			if (si.getSubmissionType() == SubissionType.CREATE) {
				// Start the upload
				Study newStudy = create();

				// Inform the user and team.
				if (si.getOriginalFileName().contains("LABS_")){

					 si.getOriginalFileName().replace("LABS_", "").replace(".zip", "");
					si.getUserToken();

					AppContext.getEmailService().sendQueuedStudySubmitted(newStudy, si.getOriginalFileName());

//                    Metabolights---WsClient mwsClient  = new Metabolights---WsClient();
//                    mwsClient.mapStudyToLabsProject(newStudy.getStudyIdentifier(), labsProjectId, userID, PropertiesUtil.getProperty("EBIHost"));

				} else {
					AppContext.getEmailService().sendQueuedStudySubmitted(newStudy, si.getOriginalFileName());
				}

			}
			// It's then an update
			else if (si.getSubmissionType() == SubissionType.UPDATE) {
				// Update study
				Study updatedStudy = update();
				AppContext.getEmailService().sendQueuedStudyUpdated(updatedStudy);
			} else {
				logger.warn("Don't know what to do with this submitted item: " + si);
			}

		// If the user email is wrong or we cou;d get this error....inform curators
		} catch (MailException e) {
			// Throw it up....it should been caught in the caller
			throw  e;

		} catch (Exception e){

			// There was an error in the submission process...
			si.moveFileTo(SubmissionQueue.getErrorFolder(), true);
			AppContext.getEmailService().sendSubmissionError(si.getUserToken(), si.getOriginalFileName(), e);

		} finally {

			// Clean the process folder anyway
			cleanProcessFolder();

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

	public boolean canProcessStart(){

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
	 * Re-submit process:
	 no need to allocate a new MTBLS id during this process
	 - Check that the logged in user owns the chosen study
	 - Check that the study is SUBMITTED (? what do we think ?).  This could stop submitters from "nullifying" a public study.

	 - Update new zipfile with Public date from the resubmission form?
	 - Update Public DB with public release date
	 - Update files (audit must be active)
	 - Index study
	 - Remove zipfile in on demand forder if exists.
	 - Display a success or error page to the submitter.  Email metabolights-help and submitter with results
	 * @return
	 * @throws Exception
	 */


	public Study update() throws Exception{


		// Unzip the file...
		si.unzip();

		// Validate it before anything?

		// Persist in the database
		StudyDAO studyDAO = DAOFactory.getInstance().getStudyDAO();

		// Add the study.
		Study newStudy = studyDAO.update(si.getUnzippedFolder(), si.getAccession(),si.getPublicReleaseDate(), si.getUserToken());

		//Index the study
		searchService.index(newStudy);

		// Delete the original zip file...
		si.getFileQueued().delete();

		// Delete the temporary zipfile if exist
		deleteZippedFile(si.getAccession());

		//Return the new accession number.
		return newStudy;

	}

    private void deleteZippedFile(String study){

		if (zipOnDemandLocation == null) {
			logger.warn("ondemand folder is not set. Can't delete possible existing zipped files!!. Study: {}" ,study );
			return;
		}

        File zippedStudy = new File (zipOnDemandLocation + study + ".zip");

        // If it exists...delete it.
        if (zippedStudy.exists()) zippedStudy.delete();

	}
}
