package uk.ac.ebi.metabolights.metabolightsuploader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.naming.ConfigurationException;

import org.junit.Test;

import uk.ac.ebi.bioinvindex.model.VisibilityStatus;
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
	
	
	
	@Test
	public void testUpload() {
		final String ISA_ARCHIVE = "src/test/resources/inputfiles/BII-I-1.zip";
		final String UNZIP_FOLDER = "src/test/resources/outputfiles/testUpload/";
		
		//Instantiate with parameters
		//TODO assuming conesa is a user in the database already. Ideally test should create the user if it doesn't exist.
		IsaTabUploader itu = new IsaTabUploader(ISA_ARCHIVE, UNZIP_FOLDER, "conesa", VisibilityStatus.PUBLIC,"config/"); 
		
		try{
			//Load file...
			itu.Upload();
		
			//If there is no error we assume every thing went well
			assertTrue("testUpload successful", true);
			
		}catch (Exception e){
			fail("testupload threw an exception: " + e.getMessage());
		}
	}

}
