package uk.ac.ebi.metabolights.metabolightsuploader;

import java.io.IOException;

import javax.naming.ConfigurationException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.isatools.isatab.manager.SimpleManager;



public class IsaTabUploader {
	
	//Logger
	private static final Logger logger = LoggerFactory.getLogger(IsaTabUploader.class);
	
	//Use IsaTabReplacerId
	private IsaTabIdReplacer itir = new IsaTabIdReplacer();
	private String owner = "";
	
	public IsaTabUploader(){
	}
	public IsaTabUploader(String isatabfile, String unzipfolder, String owner){
		//Set properties
		itir.setIsaTabArchive(isatabfile);
		itir.setUnzipFolder(unzipfolder);
		this.owner = owner;
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
	
//	/**
//	 * Upload an isa tab file using members for the file, unzip folder and owner. It also assign the owner to the file
//	 * @throws ConfigurationException
//	 * @throws IsaTabIdReplacerException
//	 * @throws IOException
//	 */
//	public void UploadWithCommandLine() throws ConfigurationException, IsaTabIdReplacerException, IOException{
//
//		//Replace the id
//		itir.Execute();
//
//		//Avoid System Exit. This is done at the end of PersistenceShellCommand.main 
//		forbidSystemExitCall();
//		
//		try{
//			//Invoke persistence command
//			//how to get feedback, connect to the log. or pass the log file as parameter.
//			PersistenceShellCommand.main(new String[]{itir.getUnzipFolder()});
//			
//		}catch(ExitTrappedException e){
//			
//		}finally{
//			enableSystemExitCall();
//		}
//			
//	
//		//Assign the user to the experiment
//		assignOwners(getOwner(), itir.getAccessionNumberList());
//			
//	}
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
		//TODO: When uploading an id that already exists, it does not throw any exception, it only log it!!!
		sm.loadISAtab(getUnzipFolder(), owner);
		
		//Return the new accession numbers
		return itir.getAccessionNumberList();
		
	}
//	/**
//	 * Assign all the owners to all the studies, to unassign use a - sign prefixing the user
//	 * @param owners: String with owners separated by commas.[+|-]login1,login2,... if +|- is specified the users will
//     *             be added to / removed from the study, otherwise this will be the exact list of study's users
//	 * @param studies: String with studies separated by spaces
//	 */
//	public void assignOwners (String owners, String studies){
//		//We are looking for something like this:
//		//--study-owners BII-S-1=-zakmck,rose --study-owners BII-S-2=+zakmck,rose --public BII-S-1,BII-S-2
//	
//		String[] studiesList;
//		String ownerscommandline = "";
//		String publiccommandline;
//		String[] args;
//		
//		//Convert strings into arrays
//		studiesList = studies.split(" ");
//		
//		//init the public param
//		publiccommandline = " --public ";
//		
//		//For each study
//		for (int i=0;i<studiesList.length;i++){
//			
//			//Start a study owner param
//			ownerscommandline = ownerscommandline
//								+ " --study-owners " 
//								+ studiesList[i] + "=" + owners;
//			
//			//Set the public status...
//			//This might be a parameter but for now lets go straightforward
//			publiccommandline = publiccommandline + studiesList[i] + ",";
//			
//		}
//
//		
//		//Truncate the last comma of the public command line
//		publiccommandline = StringUtils.truncate(publiccommandline);
//		
//		//Create args array joining both command lines
//		args = (ownerscommandline + publiccommandline).split(" ");
//		
//
//		//Avoid System Exit. This is done at the end of PermModShellCommand.main 
//		forbidSystemExitCall();
//
//		try{
//			//Invoke the permission manager command line
//			PermModShellCommand.main(args);
//
//		}catch(ExitTrappedException e){
//			
//		}finally{
//			enableSystemExitCall();
//		}
//
//	}
//
//	private static class ExitTrappedException extends SecurityException {}
//
//	private static void forbidSystemExitCall() {
//	  final SecurityManager securityManager = new SecurityManager() {
//	    public void checkPermission( Permission permission ) {
//	      if(permission.getName().indexOf("exitVM")==0) {
//	        throw new ExitTrappedException() ;
//	      }
//	    }
//	  } ;
//	  System.setSecurityManager( securityManager ) ;
//	}
//
//	private static void enableSystemExitCall() {
//	  System.setSecurityManager( null ) ;
//	}
}
	



