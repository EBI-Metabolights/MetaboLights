package uk.ac.ebi.metabolights.metabolightsuploader;

import org.isatools.isatab.gui_invokers.GUIInvokerResult;
import org.isatools.isatab.manager.SimpleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.metabolights.checklists.CheckList;
import uk.ac.ebi.metabolights.checklists.SubmissionProcessCheckListSeed;
import uk.ac.ebi.metabolights.utils.FileUtil;
import uk.ac.ebi.metabolights.utils.Zipper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//@Service
public class IsaTabUploader {
	
	//Logger
	private static final Logger logger = LoggerFactory.getLogger(IsaTabUploader.class);
	
	//@Autowired
	//public IsaTabIdReplacer itir;
	private IsaTabIdReplacer itir = new IsaTabIdReplacer();

    public IsaTabIdReplacer getItir() {
        if (itir == null)
            itir = new IsaTabIdReplacer();
        return itir;
    }

	private String owner = "";
	private VisibilityStatus status = VisibilityStatus.PUBLIC;
	private CheckList cl;
	private String isaTabArchive;
	private String unzipFolder;
	private String copyToPublicFolder;
	private String copyToPrivateFolder;
	
	private SimpleManager sm = new SimpleManager();
	
	public IsaTabUploader(){
	}

	public IsaTabUploader(String isatabfile, String unzipfolder, String owner, String copyToPublicFolder, String copyToPrivateFolder){
		//Set properties
		this.isaTabArchive = isatabfile;
		this.unzipFolder= unzipfolder;
		this.copyToPublicFolder = copyToPublicFolder;
		this.copyToPrivateFolder = copyToPrivateFolder;
		this.owner = owner;
	}

	public IsaTabUploader(String isatabfile, String unzipfolder, String owner, String copyToPublicFolder, String copyToPrivateFolder, VisibilityStatus status){
		this(isatabfile,unzipfolder,owner, copyToPublicFolder, copyToPrivateFolder);
		this.status = status;
	}

	public IsaTabUploader(String isatabfile, String unzipfolder, String owner, String copyToPublicFolder, String copyToPrivateFolder, VisibilityStatus status, String configDBPath, String publicDate, String submissionDate){
		this(isatabfile,unzipfolder, owner, copyToPublicFolder, copyToPrivateFolder, status);
		this.sm.setDBConfigPath(configDBPath);
		itir.setPublicDate(publicDate);
		itir.setSubmissionDate(submissionDate);
	}

	// IsaTabFile property
	public void setIsaTabFile(String isatabfile){this.isaTabArchive =isatabfile;}
	public String getIsaTabFile(){return this.isaTabArchive;}
	
	// UnzipFolder property
	public void setUnzipFolder(String unzipfolder){	this.unzipFolder =unzipfolder;	}
	public String getUnzipFolder(){return this.unzipFolder;}
	
	// CopyTo Public folder property
	public void setCopyToPublicFolder(String copyToFolder){ this.copyToPublicFolder =copyToFolder;}
	public String getCopyToPublicFolder(){return this.copyToPublicFolder;}
	
	// CopyTo Private folder property
	public void setCopyToPrivateFolder(String copyToFolder){ this.copyToPrivateFolder =copyToFolder;}
	public String getCopyToPrivateFolder(){return this.copyToPrivateFolder;}

	// Owner property
	public void setOwner(String owner){this.owner = owner;}
	public String getOwner(){return owner;}
	
	// Status property
	public void setStatus(VisibilityStatus status){this.status = status;}
	public VisibilityStatus getStatus(){return status;}
	
	// Config path property
	public void setDBConfigPath(String newConfigPath){sm.setDBConfigPath(newConfigPath);}
	public String getDBConfigPath(){return sm.getDBConfigPath();}

	// Submission Date Property
	public void setSubmissionDate(String submissionDate){itir.setSubmissionDate(submissionDate);}
	public String getSubmissionDate(){return itir.getSubmissionDate();}
	
	// Public Date Property
	public void setPublicDate(String publicDate){itir.setPublicDate(publicDate);}
	public String getPublicDate(){return itir.getPublicDate();}

	// Simple manager property
	public SimpleManager getSimpleManager(){
		return sm;
	}
	
	// IsaTab Replacer
	public IsaTabIdReplacer getIsaTabIdReplacer(){return itir;}
	
	// CheckList property
	public void setCheckList(CheckList newCl){
		cl= newCl;
		itir.setCheckList(newCl);
	}
	
	private void updateCheckList (SubmissionProcessCheckListSeed spcls, String newNotes){
		
		//If we have a check list
		if (cl != null){
			cl.CheckItem(spcls.getKey(), newNotes);
		}
	}

	/**
	 * Unzips ISATab file if it is a zip file, otherwise it will do nothing
	 * @throws IOException 
	 */
	private void Unzip() throws IOException{
		File isatab = new File (this.isaTabArchive);
		
		// If the file is a folder
		if (isatab.isDirectory()) {
			logger.info( this.isaTabArchive + " is a Folder, no unzip proccess is performed.");
			
			// Set unzipfolder
			this.unzipFolder = this.isaTabArchive;
			//Update CheckList
			updateCheckList(SubmissionProcessCheckListSeed.FILEUNZIP, "File is a folder. Unzip not done.");
			
		} else {
			
			// Remove any previous content of the unzip folder.
			File uf = new File (this.unzipFolder);
			FileUtil.deleteDir(uf);
			// Create it again, this time it will be empty..
			uf.mkdir();
			
			logger.info("unziping " + this.isaTabArchive + " to " + this.unzipFolder);
            try {
                Zipper.unzip2(this.isaTabArchive,this.unzipFolder);
                //Zipper.unzip(this.isaTabArchive,this.unzipFolder);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Update CheckList
			updateCheckList(SubmissionProcessCheckListSeed.FILEUNZIP, "File successfully unzipped.");

		}
		
	}
	
	
	/**
	 * Upload an experiment (Isa Tab zip file) into BII.
	 * @throws Exception 
	 */
	public HashMap<String,String> Upload() throws Exception{
		
		logger.info("Uploading IsaTabFile --> " + getIsaTabFile());
		
		//Unzip the file...
		Unzip();
		
		//Validate the file
		GUIInvokerResult result = sm.validateISAtab(this.unzipFolder);
		
		// If not SUCCESS...
		if (result != GUIInvokerResult.SUCCESS) throw new IsaTabException("We could not successfully validate the study archive.",sm.getLastLog()) ;
				
		//Update CheckList
		//TODO...this should be passed to SimpleManager and get a more detailed and precise info.
		updateCheckList(SubmissionProcessCheckListSeed.CONTENTVALIDATION, "The file has been successfully validated using our configuration.");
		
		//Sync unzipfolder with IsaTabIdReplacer
		itir.setIsaTabFolder(this.unzipFolder);
		
		//Replace the id
		itir.Execute();
		
		//Load the isatab file
		result = sm.loadISAtab(this.unzipFolder, owner, status, false);
		
		// If not SUCCESS...
		if (result != GUIInvokerResult.SUCCESS) throw new IsaTabException("File persistance process has failed.",sm.getLastLog()) ;
				
		//Update CheckList
		//TODO...this should be passed to SimpleManager and get a more detailed and precise info.
		updateCheckList(SubmissionProcessCheckListSeed.FILEPERSISTENCE, "The file has been successfully stored in our database.");
		updateCheckList(SubmissionProcessCheckListSeed.SETPERMISSIONS, "The file has been assigned to " + owner + " and the visibility has been set to " + status);

		//Reindex all the studies...
		reindexStudies(itir.getIds().values());
		
		// Zip the folder using the Study id as a name. (this make sense only it there is only one study, to be done soon).
		Zip();
		
		//Return the new accession numbers
		return  itir.getIds();
		
	}
	
	/**
	 * Upload and study without replacing the ids. 
	 * @param studyToUpdate: Study that is being uploaded, necessary for calculating the zip destination
	 * @throws Exception
	 */
	public void UploadWithoutIdReplacement(String studyToUpdate) throws Exception{
		itir.setStudyIdToUse(studyToUpdate);
		Upload();
		itir.setStudyIdToUse(null);
	}
	/**
	 * Zip the folder using the Study id as a name. (this make sense only it there is only one study, to be done soon).
	 * @throws IOException 
	 */
	private void Zip() throws IOException{
		
		String study;
		
		// Get the Study Id
		Collection<String> studyCol = itir.getIds().values();
		
		// Get the next element (It will return the only one there must be).
		study = studyCol.iterator().next();

		// Get the destination
		String destinationS = getStudyFilePath(study, this.status);

		Zip(destinationS);
		
		
	}
	/**
	 * Zip the folder to the specified destination and change the status
	 * @param destinationS
	 * @throws IOException
	 */
	private void Zip(String destinationS) throws IOException{
		
		// Get the destination
		File destination = new File(destinationS);
		
		//If there is already a file....
		if (destination.exists()){
			logger.info("Replacing " + destination.getAbsolutePath());
			destination.delete();
		}
		
		// Zip the folder.
		Zipper.zip(this.unzipFolder, destination.getAbsolutePath());
		
		//If public
		if (this.status == VisibilityStatus.PUBLIC){

			//Set the status of the file
			changeFilePermissions(destination.getAbsolutePath(), this.status);

		}
		
	}
	
	/**
	 * The way of making the file public is by letting the "other" read and execute the file.
	 * @param filePath
	 */
	public void changeFilePermissions(String filePath, VisibilityStatus newStatus){
		
		// Get the file
		File file = new File(filePath);

		// Remove permissions to everybody...
		// file.setExecutable(false, false);
		// file.setReadable(false, false);

		//If private, only the owner will be granted
		Boolean onlyOwner = (newStatus == VisibilityStatus.PRIVATE);
	
		//Set them for (owner or all)...
		file.setExecutable(true, onlyOwner);
		file.setReadable(true, onlyOwner);
		
	}
	public void PublishStudy(String study) throws Exception{
		changeStudyStatus(VisibilityStatus.PUBLIC, null, study);
	}
	/**
	 * Replaces values in IsaTab zip file based on the hash (Field - value)
	 * @param study
	 * @param replacementHash: Key should be an IsaTab field, Value any value to use in the replacement.
	 * @throws Exception 
	 */
	public void changeStudyFields(String study, HashMap<String,String> replacementHash) throws Exception{
		
		// Get the file, where ever it is...
		isaTabArchive = getCurrentStudyFilePath(study);
		
		// Un zip it to the upload folder (be sure it is clean)
		Unzip();
		
		// Replace the fields
		getItir().setIsaTabFolder(unzipFolder);     //TODO, Null pointer
		getItir().replaceFields(replacementHash);
		
		//Zip it again to the specified folder
		Zip(isaTabArchive);

	}
	
	public Map<String,String> getStudyFields(String study, String[] fields) throws Exception{
		
		// Get the file, where ever it is...
		File isaTabArchive = new File(getCurrentStudyFilePath(study));
		
		return getStudyFields(isaTabArchive, fields);
		
	}
	
	public Map<String,String> getStudyFields(File isaTabArchive, String[] fields) throws Exception{
		
		// Get the file, where ever it is...
		this.isaTabArchive = isaTabArchive.getAbsolutePath();
		
		// Un zip it to the upload folder (be sure it is clean)
		Unzip();
		
		// Get the fields
		itir.setIsaTabFolder(unzipFolder);
		return itir.getFields(fields);
		
	}
	
	
	private void changeStudyStatus(VisibilityStatus newStatus, String owner, String study) throws Exception{
		
		//Change study status...
		sm.changeStudyPermissions(newStatus, owner,new String[] {study});
		
		// Reindex...
		reindexStudies(study);
		
		// Move the file
		moveFile (study, getOtherStatus(newStatus));
		
		//change the file permissions
		changeFilePermissions(getStudyFilePath(study, newStatus), newStatus);
		
	}
	/**
	 * Will move the file from the public to the private or viceversa 
	 * @param study
	 * @param currentStatus
	 */
	public void moveFile(String study, VisibilityStatus currentStatus){
		
		// Get the current file location
		File studyFile = new File(getStudyFilePath(study, currentStatus));
		
		//Move it to the other location
		studyFile.renameTo(new File (getStudyFilePath(study,getOtherStatus(currentStatus))));
		
	}
	public VisibilityStatus getOtherStatus(VisibilityStatus status){
		return (status == VisibilityStatus.PRIVATE)? VisibilityStatus.PUBLIC: VisibilityStatus.PRIVATE;
	}

	public String getCurrentStudyFilePath(String study) throws FileNotFoundException{
		
		// As we don't whether the study is public or private, try to guess it...
		// Try the public...
		File fileStudyPublic = new File (getStudyFilePath(study, VisibilityStatus.PUBLIC));
		
		// If exists...
		if (fileStudyPublic.exists()){
			this.status = VisibilityStatus.PUBLIC;
			return fileStudyPublic.getAbsolutePath();
		} else {
			
			// Try the private...
			File fileStudyPrivate = new File (getStudyFilePath(study, VisibilityStatus.PRIVATE));
			
			// If exists...
			if (fileStudyPrivate.exists()){
				this.status = VisibilityStatus.PRIVATE;
				return fileStudyPrivate.getAbsolutePath();
			
			}else{
			
				//There isn't anywhere...
				throw new FileNotFoundException("Study file for " + study + "not found neither in public nor private folder.");
			
			}

		}
		
		
	}
	public String getStudyFilePath(String study, VisibilityStatus status) {
		
		String folder = (status == VisibilityStatus.PRIVATE)? copyToPrivateFolder: copyToPublicFolder;
		return folder + study + ".zip";
	}
	public void validate(String isatabFile) throws IsaTabException{
		GUIInvokerResult result;
		
		
		logger.info("Validating " + isatabFile);
		result = sm.validateISAtab(isatabFile);
		
		// If not SUCCESS...
		if (result != GUIInvokerResult.SUCCESS) throw new IsaTabException("Validation process while uploading failed",sm.getLastLog()) ;
		
		
	}

	public void reindex() throws IsaTabException{
		
		GUIInvokerResult result;
		
		logger.info("Reindexing the whole database");
		result = sm.reindexDatabase();
		
		// If not SUCCESS...
		if (result != GUIInvokerResult.SUCCESS) throw new IsaTabException("Reindex of the whole database has failed",sm.getLastLog()) ;
		
	}

	/**
	 * 
	 * @param studiesList: String with accessions separated by "|" (pipes).
	 * @throws Exception
	 */
	
	public void reindexStudies(String studiesList) throws IsaTabException{
		
		GUIInvokerResult result;
		
		logger.info("Reindexing studies: " + studiesList);
		
		result = sm.reindexStudies(studiesList);
		
		// If not SUCCESS...
		if (result != GUIInvokerResult.SUCCESS) throw new IsaTabException("Reindex of " + studiesList + " has failed",sm.getLastLog()) ;
		
	}

	public void reindexStudies(Collection<String> studies) throws IsaTabException{
		String studiesS=""; 
		// Reindex all the studies...there must be only one
		for (String study: studies){
			studiesS = studiesS + study + "|";
		}
		
		reindexStudies(studiesS);
		
	}

	/**
	 * 
	 * @param studyList: String with accessions separated by "|" (pipes).
	 * @throws IsaTabException 
	 * @throws Exception
	 */
	public void unloadISATabFile(String studyList) throws IsaTabException{
		
		GUIInvokerResult result = sm.unLoadISAtab(studyList);
		
		if (result != GUIInvokerResult.SUCCESS){
			
			throw new IsaTabException("Unload of " + studyList + " hasn't succeded.", sm.getLastLog());
			
		}
		
		//Get the list
		// TODO: what if there is an error and some has been removed and others not...this step below should be done for each study inside Simple manager
		// or establish a communication channel (observer, events....) to know when a study has been deleted.
		// In case of only unloading one study this should be fine.
		Set<String> studies = sm.Study2Set(studyList);
	
		// Go through the list of studies
		for (String study:studies){
		
			// Get the file
			// TODO: Is it public or is it private.... try to remove from both locations ;-( 
			File studyfile = new File (getStudyFilePath(study, VisibilityStatus.PRIVATE));
			
			// Remove it if it is there...
			if (studyfile.exists()) { studyfile.delete();}
			
			// Remove it if it is there...
			studyfile = new File (getStudyFilePath(study, VisibilityStatus.PUBLIC));
			
			if (studyfile.exists()) { studyfile.delete();}
			
			
		}
		

	}
	
}