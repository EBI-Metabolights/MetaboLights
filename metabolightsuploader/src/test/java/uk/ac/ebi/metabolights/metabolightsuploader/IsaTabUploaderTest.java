package uk.ac.ebi.metabolights.metabolightsuploader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.naming.ConfigurationException;

import junit.framework.Assert;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
import uk.ac.ebi.bioinvindex.model.security.User;
import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabUploader;
import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabIdReplacerException;

public class IsaTabUploaderTest {
	@Test
	public void testSetters() {
		IsaTabUploader itu = new IsaTabUploader();

		itu.setIsaTabFile("foo");
		assertEquals("foo", itu.getIsaTabFile());

		itu.setUnzipFolder("unzip");
		assertEquals("unzip", itu.getUnzipFolder());

		itu.setOwner("owner");
		assertEquals("owner", itu.getOwner());


	}

	@Test
	public void testConstructor(){
		IsaTabUploader itu = new IsaTabUploader("foo","moo", "loo");

		//Test getters.
		assertEquals("foo", itu.getIsaTabFile());
		assertEquals("moo",itu.getUnzipFolder());
		assertEquals("loo",itu.getOwner());

	}
	@Test
	public void testValidationOfValidISAFile(){

		//This file passes the validation 
		//TODO(watch up, others shipped with ISA Creator like BII-S-1 also passes it!!!)...study this behavior
		final String ISA_ARCHIVE_FOLDER = "src/test/resources/inputfiles/ISACREATOR1.4_archive_FAST_valid/";

		IsaTabUploader itu = new IsaTabUploader();
		try {
			itu.validate(ISA_ARCHIVE_FOLDER);
		}catch (Exception e){
			fail ("validate method raised and error: " + e.getMessage());
		}
	}
	@Test
	public void testValidationIncorrect(){

		IsaTabUploader itu = new IsaTabUploader();
		try {
			itu.validate("src/test/resources/inputfiles/foo/");
			fail ("validate did not raised and error and it should do it with a wrong ISA TAB file");
		}catch (Exception e){
			//Expected...
		}
	}
	@Test
	public void testValidationIncorrectWithRealFile(){

		IsaTabUploader itu = new IsaTabUploader();
		try {
			//This should not validate
			itu.validate("src/test/resources/inputfiles/ISACREATOR1.4_archive_FAST_invalid/");
			fail ("validate did not raised and error and it should do it with a wrong ISA TAB file");
		}catch (Exception e){
			//Expected...
		}
	}

	/**
	 * Tests loading an Isatab set of files into the database schema.
	 * You need to have a number of files and settings to make this work:
	 * <ul>
	 * <li>config/hibernate.properties is used for the target database details. Fill in test database details and
	 * mind the setting for hibernate.hbm2ddl.auto. This properties file also defines the Lucene index location. </li>
	 * <li>src/test/resources/am_hibernate.cfg.xml is redundantly used as well for the database details, like hibernate.properties</li>
	 * <li>config/data_locations.xml is used for the output of the uploaded file set</li>
	 * </ul>
	 * 
	 *  questions:
	 *   -there is an unzip folder. should that not be a UNIQUE folder.. to prevent concurrency problems. is that done in the webshite? 
	 *   -should the unzip folder content not be removed afterwards ... ?!
	 *   -what with data_locations.xml? why not just keep the zip file. or are these different files.. what's for download?
	 *   -should we perhaps dig through directories? how does the zip file look like, can it have multiple directories...? 
	 */
	@Test
	public void testUpload() {

		
		//Hibernate init
		SessionFactory fac = new AnnotationConfiguration().configure().buildSessionFactory();
    	Session session = fac.openSession();
		session.beginTransaction();

		//Find a username
		String userName="UNSET";
		Query q = session.createQuery("FROM User");
		List<User> users = (List<User>) q.list();
		if(users.size()!=0) {
			userName = users.get(0).getUserName();
		}
		
		//Upload
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		final String ISA_ARCHIVE = path+"inputfiles/zuker.zip";

		//Clean out unzip folder first, otherwise we get unpredictable results
		final String UNZIP_FOLDER =path+"outputfiles/";
		File folder = new File(UNZIP_FOLDER);
		File[] listOfFiles = folder.listFiles();
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	    	  listOfFiles[i].delete();
	      } else if (listOfFiles[i].isDirectory()) {
	    	  deleteDirectory(listOfFiles[i]);
	      }
	    }
	    
		IsaTabUploader itu = new IsaTabUploader(ISA_ARCHIVE, UNZIP_FOLDER, "MrJunit", VisibilityStatus.PUBLIC,"config/"); 
		HashMap<String,String> ids=null;

		try{
			//Load file...
			ids = itu.Upload();
			
			assertEquals("Study id's assigned ", 1,ids.size());

			String iD="UNSET";
			for (String key : ids.keySet() ) {
				assertEquals("Original ID ", "I00649",key);
				iD=ids.get(key);
			}

			//Now test database for upload results using the newly assigned accession
			q = session.createQuery("FROM Study WHERE acc = :acc");
			q.setParameter("acc",iD);
			
  		    Study study = (Study) q.uniqueResult();
			Assert.assertEquals("Title", "Zuker rat urine NMR study", study.getTitle());
			Assert.assertEquals("Description", "Zuker rat urine study by NMR", study.getDescription());
			for (User owner : study.getUsers()) {
				Assert.assertEquals("Owner", userName, owner);
			}

		}catch (Exception e){
			e.printStackTrace();
			fail("testupload threw an exception: " + e.getMessage());
		}
	}
	
	/**
	 * Used for removal of unzip folder content
	 */
	private boolean deleteDirectory(File path) {
		if( path.exists() ) {
			File[] files = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					deleteDirectory(files[i]);
				}
				else {
					files[i].delete();
				}
			}
		}
		return( path.delete() );
	}


	@Test
	public void testReindex() {
		IsaTabUploader itu = new IsaTabUploader();
		//Set the config path
		itu.setConfigPath("config/");

		try{
			//Reindex the database...
			itu.reindex();
		}catch(Exception e){

			fail("Reindex test failed: " + e.getMessage());

		}
	}
}
