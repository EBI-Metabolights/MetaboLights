package uk.ac.ebi.metabolights.metabolightsuploader;

import java.io.IOException;

import javax.naming.ConfigurationException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.isatools.isatab.manager.SimpleManager;

import uk.ac.ebi.bioinvindex.model.VisibilityStatus;



public class IsaTabUploader {
	
	//Logger
	private static final Logger logger = LoggerFactory.getLogger(IsaTabUploader.class);
	
	//Use IsaTabReplacerId
	private IsaTabIdReplacer itir = new IsaTabIdReplacer();
	private String owner = "";
	private VisibilityStatus status = VisibilityStatus.PUBLIC;
	
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
	
	public void setIsaTabFile(String isatabfile){
		itir.setIsaTabArchive(isatabfile);
	}
	public String getIsaTabFile(){
		return itir.getIsaTabArchive();
	}
	public void setUnzipFolder(String unzipfolder){
		itir.setUnzipFolder(unzipfolder);
	}
	public String getUnzipFolder(){
		return itir.getUnzipFolder();
	}
	public void setOwner(String owner){
		this.owner = owner;
	}
	public String getOwner(){
		return owner;
	}
	public void setStatus(VisibilityStatus status){
		this.status = status;
	}
	public VisibilityStatus getStatus(){
		return status;
	}
	/**
	 * Upload an experiment (Isa Tab zip file) into BII.
	 * @throws ConfigurationException
	 * @throws IsaTabIdReplacerException
	 * @throws IOException
	 * returns String with the new accession numbers assigned (only Study Ids).
	 */
	public String Upload() throws ConfigurationException, IsaTabIdReplacerException, IOException{
		
		logger.info("Uploading IsaTabFile --> " + getIsaTabFile());
		
		//Replace the id
		itir.Execute();
		
		//Create a simple manager
		SimpleManager sm = new SimpleManager();
		
		//Load the isatab file
		//TODO: When uploading an id that already exists, it does not throw any exception, it only logs it!!!
		sm.loadISATab(getUnzipFolder(), owner,status);
		
		//Return the new accession numbers
		return itir.getAccessionNumberList();
		
	}
}
	



