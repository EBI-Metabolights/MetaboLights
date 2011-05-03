package uk.ac.ebi.metabolights.metabolightsuploader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.naming.ConfigurationException;

import org.junit.Before;
import org.junit.Test;

import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabIdReplacer;
import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabIdReplacerException;
import uk.ac.ebi.metabolights.utils.FileUtil;
import uk.ac.ebi.metabolights.utils.StringUtils;
import uk.ac.ebi.metabolights.utils.Zipper;

public class IsaTabIdReplacerTest {

	//Folders
	final String FOLDER_TEST_IN = "./src/test/resources/inputfiles/";
	final String FOLDER_TEST_OUT = "./src/test/resources/outputfiles/";
	
	//Files for validation
	final String FILE_NOEXISTS_TXT = "1234567890.txt";
	final String FILE_NOEXISTS_ZIP = "1234567890.zip";
	final String FILE_EXISTS_ZIP = FOLDER_TEST_IN + "fake.zip";
	final String FILE_EXISTS_TXT = FOLDER_TEST_IN + "fake.txt";

	//Constants for IsaTab1 test
	final String FOLDER_ISATAB1 = FOLDER_TEST_IN + "isatab1/";
	final String FILE_ISATAB1 = FOLDER_TEST_IN + "isatab1.zip";
	
	final String FOLDER_ISATAB1_OUT = FOLDER_TEST_OUT + "isatab1/";
	
	//Constants for IsaTabBII1 test
	final String FOLDER_ISATABBII1 = FOLDER_TEST_IN + "BII-I-1/";
	final String FILE_ISATABBII1 = FOLDER_TEST_IN + "BII-I-1.zip";
	
	final String FOLDER_ISATABBII1_OUT = FOLDER_TEST_OUT + "BII-I-1/";
	

	
	@Before
	public void prepareInputFiles(){
		
		//Zip all files needed to the test.
		Zipper.zip(FOLDER_ISATAB1, FILE_ISATAB1);
		Zipper.zip(FOLDER_ISATABBII1, FILE_ISATABBII1);
		
		//Delete (empty recursively) the output folder
		File out = new File (FOLDER_TEST_OUT);
		FileUtil.deleteDir(out);
		
		//Create it again, this time empty	
		out.mkdir();
		
	}
	@Test
	public void testMembers(){
		//Values for the constructor
		final String EXPECTED_ARCHIVE_C = "foo";
		final String EXPECTED_UNZIP_FOLDER_C ="moo";
		
		//Values to test the setters
		final String EXPECTED_ARCHIVE = "loo";
		final String EXPECTED_UNZIP_FOLDER ="coo";
		
		
		//Creation
		IsaTabIdReplacer istr = new IsaTabIdReplacer(EXPECTED_ARCHIVE_C,EXPECTED_UNZIP_FOLDER_C);
		
		//Test Members after constructor
		assertEquals(EXPECTED_ARCHIVE_C,istr.getIsaTabArchive());
		assertEquals(EXPECTED_UNZIP_FOLDER_C, istr.getUnzipFolder());
		
		//Test setters
		//Test IsaTabArchive member
		istr.setIsaTabArchive(EXPECTED_ARCHIVE);
		assertEquals(EXPECTED_ARCHIVE, istr.getIsaTabArchive());
		
		//Test unzipfolder member
		istr.setUnzipFolder(EXPECTED_UNZIP_FOLDER);
		assertEquals(EXPECTED_UNZIP_FOLDER, istr.getUnzipFolder());
		
	}

	@Test
	public void testValidationNoFileWrongExtension(){
		
		//Test wrong extension and no existence..
		IsaTabIdReplacer istr = new IsaTabIdReplacer(FILE_NOEXISTS_TXT, "foo");
		
		//Validate
		try{
			
			//Invoke validation
			istr.validateIsaTabArchive();
			
		}catch (IsaTabIdReplacerException e) {
			
			//There must be 2 errors (path and format)
			assertTrue("There must be a file format error", e.getFileFormat() != null);
			assertTrue("There must be a file path error", e.getFilePath() != null);
			
		}

	}
	@Test
	public void testValidationNoFileRightExtension(){
		
		//Test wrong extension and no existence..
		IsaTabIdReplacer istr = new IsaTabIdReplacer(FILE_NOEXISTS_ZIP, "foo");
		
		//Validate
		try{
			
			//Invoke validation
			istr.validateIsaTabArchive();
			
		}catch (IsaTabIdReplacerException e) {
			
			//There must be 1 errors (path)
			assertTrue("There must NOT be a file format error", e.getFileFormat() == null);
			assertTrue("There must be a file path error", e.getFilePath() != null);
			
		}

	}
	@Test
	public void testValidationFileExistsWrongExtension(){
		
		//Test wrong extension and no existence..
		IsaTabIdReplacer istr = new IsaTabIdReplacer(FILE_EXISTS_TXT, "foo");
		
		//Validate
		try{
			
			//Invoke validation
			istr.validateIsaTabArchive();
			
		}catch (IsaTabIdReplacerException e) {
			
			//There must be 1 errors (path)
			assertTrue("There must be a file format error", e.getFileFormat() != null);
			assertTrue("There must NOT be a file path error", e.getFilePath() == null);
			
		}

	}
	@Test
	public void testValidationFileExistsRightExtension(){
		
		//Test wrong extension and no existence..
		IsaTabIdReplacer istr = new IsaTabIdReplacer(FILE_EXISTS_ZIP, "foo");
		
		//Validate
		try{
			
			//Invoke validation
			istr.validateIsaTabArchive();
			
		}catch (IsaTabIdReplacerException e) {
			
			//There must be 1 errors (path)
			assertTrue("There must NOT be a file format error", e.getFileFormat() == null);
			assertTrue("There must NOT be a file path error", e.getFilePath() == null);
			
		}

	}

	@Test
	/**
	 * Test that a a zip file, without a investigation file throws an error.
	 */
	public void testFakeSample(){
		
		//Configure IsaTabIdReplacer
		IsaTabIdReplacer itr = new IsaTabIdReplacer(FILE_EXISTS_ZIP, FOLDER_TEST_OUT);
		
		try {
			
			//Execute the replacement method
 			itr.Execute();
		
		}catch (FileNotFoundException e){
			//DO not do nothing, it is expected to receive this error.
			assertTrue("IsatTabReplacer has thrown the expected FileNotFoundException", true);			
		}catch (Exception e){
			fail("testIsaTab1Sample have thrown an exception." + e.getMessage());
		}
	}

	
	@Test
	public void testIsaTab1Sample(){
		
		//Array for the list of new accession numbers
		String[] accessionList;
		String inputfile;
		String outputfile;
		
		//Configure IsaTabIdReplacer
		IsaTabIdReplacer itr = new IsaTabIdReplacer(FILE_ISATAB1, FOLDER_ISATAB1_OUT);
		
		try {
			
			//Accession list should be empty
			assertEquals("", itr.getAccessionNumberList());
			
			//Execute the replacement method
 			itr.Execute();
 			
 			//Transform the accessionList into an array
 			accessionList = itr.getAccessionNumberList().split(" ");
 			
 			//There must be 2 studies..
 			assertEquals(2, accessionList.length);
 			
 			//Cannot be done this way when Investigation Identifier is active because getAccessionNumberList do not return the Investigation Identifiers
 			
// 			//Now, with these id we can test the output files and check if the replacement is done correctly.
// 			inputfile = FileUtil.file2String(FOLDER_ISATAB1+"i_Investigation.txt");
// 			outputfile= FileUtil.file2String(FOLDER_ISATAB1_OUT+"i_Investigation.txt");
// 			
// 			//Replace the old id with the new ones
// 			inputfile = StringUtils.replace(inputfile, "BII-S-1", accessionList[0].toString());
// 			inputfile = StringUtils.replace(inputfile, "BII-S-2", accessionList[1].toString());
// 			
// 			//Test if now matches
// 			assertEquals(inputfile, outputfile);
// 			
 			
		}catch (Exception e){
			fail("testIsaTab1Sample have thrown an exception." + e.getMessage());
		}



	}
	@Test
	public void testMain() {
		
		try {
			IsaTabIdReplacer.main(new String[] {FILE_ISATABBII1, FOLDER_ISATABBII1_OUT});
		} catch (Exception e) {
			fail("Error invoking main: " + e.getMessage());
		}
		
		
	}
}
