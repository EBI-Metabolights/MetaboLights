package uk.ac.ebi.metabolights.metabolightsuploader;

import java.util.HashMap;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.isatools.isatab.manager.SimpleManager;

import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.metabolights.checklists.CheckList;
import uk.ac.ebi.metabolights.checklists.SubmissionProcessCheckListSeed;



public class IsaTabUploader {
	
	//Logger
	private static final Logger logger = LoggerFactory.getLogger(IsaTabUploader.class);
	
	//Use IsaTabReplacerId
	private IsaTabIdReplacer itir = new IsaTabIdReplacer();
	private String owner = "";
	private VisibilityStatus status = VisibilityStatus.PUBLIC;
	private String configPath;
	private CheckList cl;
	
	public IsaTabUploader(){
	}
	public IsaTabUploader(String isatabfile, String unzipfolder, String owner){
		//Set properties
		itir.setIsaTabArchive(isatabfile);
		itir.setUnzipFolder(unzipfolder);
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
	public void setIsaTabFile(String isatabfile){itir.setIsaTabArchive(isatabfile);}
	public String getIsaTabFile(){return itir.getIsaTabArchive();}
	
	//UnzipFolder property
	public void setUnzipFolder(String unzipfolder){itir.setUnzipFolder(unzipfolder);}
	public String getUnzipFolder(){return itir.getUnzipFolder();}
	
	//Owner property
	public void setOwner(String owner){this.owner = owner;}
	public String getOwner(){return owner;}
	
	//Status property
	public void setStatus(VisibilityStatus status){this.status = status;}
	public VisibilityStatus getStatus(){return status;}
	
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
	 * Upload an experiment (Isa Tab zip file) into BII.
	 * @throws Exception 
	 */
	public HashMap<String,String> Upload() throws Exception{
		
		logger.info("Uploading IsaTabFile --> " + getIsaTabFile());
		
		//Replace the id
		itir.Execute();
		
		//Create a simple manager with the config path
		SimpleManager sm = new SimpleManager(configPath);
		
		//Load the isatab file
		sm.loadISATab(getUnzipFolder(), owner,status);
		
		//Update CheckList
		//TODO...this should be passed to SimpleManager and get a more detailed and precise info.
		updateCheckList(SubmissionProcessCheckListSeed.FILEPERSISTANCE, "The file has been succesfully persisted into our database.");
		updateCheckList(SubmissionProcessCheckListSeed.SETPERMISSIONS, "The file has been assigned to " + owner + " and the visibility has been set to " + status);
		
		
		//Return the new accession numbers
		return  itir.getIds();
		
	}
}