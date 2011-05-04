package uk.ac.ebi.metabolights.metabolightsuploader;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.naming.ConfigurationException;

import org.junit.Test;

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
	//Do not test this as command line tools is not recommended to use@Test
	public void testUploadCommandLine() throws ConfigurationException, IsaTabIdReplacerException, IOException {
		final String ISA_ARCHIVE = "src/test/resources/inputfiles/BII-I-1.zip";
		final String UNZIP_FOLDER = "src/test/resources/outputfiles/testUpload/";
		
		//Instantiate with parameters
		//TODO assuming conesa is a user in the database already. Ideally test should create the user if it doesn't exist.
		IsaTabUploader itu = new IsaTabUploader(ISA_ARCHIVE, UNZIP_FOLDER, "conesa");
		
		//Load file...
		itu.UploadWithCommandLine();
		
		//If there is no error we assume every thing went well
		assertTrue("testUpload successful", true);
		
		
	}
	@Test
	public void testUpload() throws ConfigurationException, IsaTabIdReplacerException, IOException {
		final String ISA_ARCHIVE = "src/test/resources/inputfiles/BII-I-1.zip";
		final String UNZIP_FOLDER = "src/test/resources/outputfiles/testUpload/";
		
		//Instantiate with parameters
		//TODO assuming conesa is a user in the database already. Ideally test should create the user if it doesn't exist.
		IsaTabUploader itu = new IsaTabUploader(ISA_ARCHIVE, UNZIP_FOLDER, "conesa");
		
		//Load file...
		itu.Upload();
		
		//If there is no error we assume every thing went well
		assertTrue("testUpload successful", true);
		
		
	}

}
