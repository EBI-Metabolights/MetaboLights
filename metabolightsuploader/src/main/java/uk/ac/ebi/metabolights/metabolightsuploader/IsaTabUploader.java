package uk.ac.ebi.metabolights.metabolightsuploader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.isatools.isatab.manager.SimpleManager;

import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.metabolights.checklists.CheckList;
import uk.ac.ebi.metabolights.checklists.SubmissionProcessCheckListSeed;
import uk.ac.ebi.metabolights.utils.Zipper;



public class IsaTabUploader {
	
	//Logger
	private static final Logger logger = LoggerFactory.getLogger(IsaTabUploader.class);
	
	//Use IsaTabReplacerId
	private IsaTabIdReplacer itir = new IsaTabIdReplacer();
	private String owner = "";
	private VisibilityStatus status = VisibilityStatus.PUBLIC;
	private String configPath;
	private CheckList cl;
	private String isaTabArchive;
	private String unzipFolder;
	
	public IsaTabUploader(){
	}
	public IsaTabUploader(String isatabfile, String unzipfolder, String owner){
		//Set properties
		this.isaTabArchive = isatabfile;
		this.unzipFolder= unzipfolder;
		this.owner = owner;
	}
	public IsaTabUploader(String isatabfile, String unzipfolder, String owner, VisibilityStatus status){
		
		this(isatabfile,unzipfolder,owner);
		this.status = status;
		
	}
	public IsaTabUploader(String isatabfile, String unzipfolder, String owner, VisibilityStatus status, String configPath){
		this(isatabfile,unzipfolder, owner,status);
		this.configPath = configPath;
		
	}
	//IsaTabFile property
	public void setIsaTabFile(String isatabfile){this.isaTabArchive =isatabfile;}
	public String getIsaTabFile(){return this.isaTabArchive;}
	
	//UnzipFolder property
	public void setUnzipFolder(String unzipfolder){
		this.unzipFolder =unzipfolder;
	}
	public String getUnzipFolder(){return this.unzipFolder;}
	
	//Owner property
	public void setOwner(String owner){this.owner = owner;}
	public String getOwner(){return owner;}
	
	//Status property
	public void setStatus(VisibilityStatus status){this.status = status;}
	public VisibilityStatus getStatus(){return status;}
	
	//Config path property
	public void setConfigPath(String newConfigPath){this.configPath = newConfigPath;}
	public String getConfigPath(){return this.configPath;}

	//CheckList property
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
		
		//If the file is a folder
		if (isatab.isDirectory()) {
			logger.info( this.isaTabArchive + " is a Folder, no unzip proccess is performed.");
			
			//Set unzipfolder
			this.unzipFolder = this.isaTabArchive;
			//Update CheckList
			updateCheckList(SubmissionProcessCheckListSeed.FILEUNZIP, "File is a folder. Unzip not done.");
			
		}else{
			logger.info("unziping " + this.isaTabArchive + " to " + this.unzipFolder);
			Zipper.unzip(this.isaTabArchive,this.unzipFolder);

			//Update CheckList
			updateCheckList(SubmissionProcessCheckListSeed.FILEUNZIP, "File succesfully unzipped.");

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
		
		//Create a simple manager with the config path
		SimpleManager sm = new SimpleManager(configPath);
	
		//Validate the file
		sm.validateISAtab(this.unzipFolder);
		
		//Update CheckList
		//TODO...this should be passed to SimpleManager and get a more detailed and precise info.
		updateCheckList(SubmissionProcessCheckListSeed.CONTENTVALIDATION, "The file has been succesfully validated using our configuration.");
		
		//Sync unzipfolder with IsaTabIdReplacer
		itir.setIsaTabFolder(this.unzipFolder);
		
		//Replace the id
		itir.Execute();
			
		//Load the isatab file
		sm.loadISATab(this.unzipFolder, owner,status);
		
		//Update CheckList
		//TODO...this should be passed to SimpleManager and get a more detailed and precise info.
		updateCheckList(SubmissionProcessCheckListSeed.FILEPERSISTANCE, "The file has been succesfully persisted into our database.");
		updateCheckList(SubmissionProcessCheckListSeed.SETPERMISSIONS, "The file has been assigned to " + owner + " and the visibility has been set to " + status);
		
		
		//Return the new accession numbers
		return  itir.getIds();
		
	}
	public void validate(String isatabFile) throws Exception{
		SimpleManager sm = new SimpleManager();
		sm.validateISAtab(isatabFile);
		
	}
	public void reindex() throws Exception{
		SimpleManager sm = new SimpleManager(configPath);
		sm.reindexDatabase();
	}
}