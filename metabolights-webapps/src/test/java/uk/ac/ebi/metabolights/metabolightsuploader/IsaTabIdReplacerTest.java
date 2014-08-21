/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/5/12 10:35 AM
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

package uk.ac.ebi.metabolights.metabolightsuploader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
//import java.io.IOException;

//import javax.naming.ConfigurationException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.commons.io.FileUtils;

import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabIdReplacer;
import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabIdReplacerException;
import uk.ac.ebi.metabolights.utils.FileUtil;
//import uk.ac.ebi.metabolights.utils.StringUtils;
import uk.ac.ebi.metabolights.utils.Zipper;

public class IsaTabIdReplacerTest {

	// Folders
	static final String FOLDER_TEST_IN = "./src/test/resources/inputfiles/";
	static final String FOLDER_TEST_OUT = "./src/test/resources/outputfiles/";
	
	// Files for validation
	static final String FILE_NOEXISTS_TXT = "1234567890.txt";
	static final String FILE_NOEXISTS_ZIP = "1234567890.zip";
	static final String FILE_EXISTS_ZIP = FOLDER_TEST_IN + "fake.zip";
	static final String FILE_EXISTS_TXT = FOLDER_TEST_IN + "fake.txt";

	// Constants for IsaTab1 test
	static final String FOLDER_ISATAB1 = FOLDER_TEST_IN + "isatab1/";
	static final String FILE_ISATAB1 = FOLDER_TEST_IN + "isatab1.zip";
	
	static final String FOLDER_ISATAB1_OUT = FOLDER_TEST_OUT + "isatab1/";
	
	// Constants for IsaTabBII1 test
	static final String FOLDER_ISATABBII1 = FOLDER_TEST_IN + "BII-I-1/";
	static final String FILE_ISATABBII1 = FOLDER_TEST_IN + "BII-I-1.zip";

	static final String FOLDER_ISATABBII1_OUT = FOLDER_TEST_OUT + "BII-I-1/";

	
	// Constants for RS_T2DM_GSK test
	static final String FOLDER_ISATAB_RS_T2DM_GSK = FOLDER_TEST_IN + "RS_T2DM_GSK/";
	static final String FILE_ISATAB_RS_T2DM_GSK = FOLDER_TEST_IN + "RS_T2DM_GSK.zip";
	
	static final String FOLDER_ISATAB_RS_T2DM_GSK_OUT = FOLDER_TEST_OUT + "RS_T2DM_GSK/";

	
	// Constants for fake test
	static final String FOLDER_FAKE = FOLDER_TEST_IN + "fake/";
	static final String FILE_FAKE = FOLDER_TEST_IN + "fake.zip";
	
	static final String FOLDER_FAKE_OUT = FOLDER_TEST_OUT + "fake/";
	
	// Constants for field replacement
	static final String FOLDER_REPLACEMENT = FOLDER_TEST_IN + "FieldReplacement/";
	
	static final String FOLDER_REPLACEMENT_OUT = FOLDER_TEST_OUT + "FieldReplacement/";
	
			
	// First time, BII-I-1 zip is not found for IsaTabUploaderTest (why?) I would like to invoke
	// this method before any test start.
	@BeforeClass
	public static void prepareInputFiles() throws IOException{
		
		try{
			// Zip all files needed to the test.
			Zipper.zip(FOLDER_ISATAB1, FILE_ISATAB1);
			Zipper.zip(FOLDER_ISATABBII1, FILE_ISATABBII1);
			Zipper.zip(FOLDER_FAKE, FILE_FAKE);
			Zipper.zip(FOLDER_ISATAB_RS_T2DM_GSK, FILE_ISATAB_RS_T2DM_GSK);
			
			
		}catch (IOException ioe){
			fail("Zipper.zip threw an exception: " + ioe.getMessage());
		}
			
		// Delete (empty recursively) the output folder
		File out = new File (FOLDER_TEST_OUT);
		FileUtil.deleteDir(out);
		// Create it again, this time empty	
		out.mkdir();
		
		// Copy all folders in input files into the output folder...
		FileUtils.copyDirectory(new File(FOLDER_ISATAB1), new File(FOLDER_ISATAB1_OUT));
		FileUtils.copyDirectory(new File(FOLDER_ISATABBII1), new File(FOLDER_ISATABBII1_OUT));
		FileUtils.copyDirectory(new File(FOLDER_FAKE), new File(FOLDER_FAKE_OUT));
		FileUtils.copyDirectory(new File(FOLDER_ISATAB_RS_T2DM_GSK), new File (FOLDER_ISATAB_RS_T2DM_GSK_OUT));
		FileUtils.copyDirectory(new File(FOLDER_REPLACEMENT), new File (FOLDER_REPLACEMENT_OUT));
		
		
	}
	@Test
	public void testMembers(){
		// Values for the constructor
		final String EXPECTED_ARCHIVE_C = "foo";
		
		// Values to test the setters
		final String EXPECTED_ARCHIVE = "loo";
		
		// Creation
		IsaTabIdReplacer istr = new IsaTabIdReplacer(EXPECTED_ARCHIVE_C);
		
		// Test Members after constructor
		assertEquals(EXPECTED_ARCHIVE_C,istr.getIsaTabFolder());
		
		// Test setters
		// Test IsaTabArchive member
		istr.setIsaTabFolder(EXPECTED_ARCHIVE);
		assertEquals(EXPECTED_ARCHIVE, istr.getIsaTabFolder());
		
		// Test methods...
		assertEquals("This is a field", istr.getFieldInLine("This is a field\t\"this is the value\""));
		assertEquals(null, istr.getFieldInLine("notabline"));
		
		assertEquals("this is the value", istr.getValueInLine("This is a field\t\"this is the value\""));
		assertEquals(null, istr.getValueInLine("notabline"));
		
		
		
	}
	
	@Test
	public void testReplacementWithHashAndGetFields() throws Exception{
		
		// Set the replacement folder
		IsaTabIdReplacer istr = new IsaTabIdReplacer(FOLDER_REPLACEMENT_OUT);
		
		// Create the hash with the replacement matrix
		HashMap<String, String> replacementData = new HashMap<String,String>();
		
		// Create the replacement data
		replacementData.put("none", "nothing to do");
		replacementData.put("Study Public Release Date", "09/09/2011");
		
		
		// Invoke replacement method
		istr.replaceFields(replacementData);
		
		String output = FileUtil.file2String(FOLDER_REPLACEMENT_OUT + "i_Investigation.txt");
		
		// Look for the new text...
		assertTrue("Looking for replacement done in Study Public Release Date", output.indexOf("Study Public Release Date\t\"09/09/2011\"")!=-1);
		
		// Search for the fields
		Map<String,String> result = istr.getFields(new String[]{"Study Public Release Date", "Study Design Type"}); 
		
		// Test returned values
		assertEquals("Number of values found in file.", 2,result.size());
		assertEquals("Study Design must value test", "intervention design", result.get("Study Design Type"));
		assertEquals("Study Public Release Date value test", "09/09/2011", result.get("Study Public Release Date"));
		
		
		
		
		
		
		
		
	}

	@Test
	public void testValidationNoFileWrongExtension(){
		
		//Test wrong extension and no existence..
		IsaTabIdReplacer istr = new IsaTabIdReplacer(FILE_NOEXISTS_TXT);
		
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
	public void testValidationFileExistsNotAFolder(){
		
		//Test wrong extension and no existence..
		IsaTabIdReplacer istr = new IsaTabIdReplacer(FILE_EXISTS_ZIP);
		
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
	public void testValidationFileExistsAndIsAFolder(){
		
		//Test wrong extension and no existence..
		IsaTabIdReplacer istr = new IsaTabIdReplacer(FOLDER_ISATABBII1_OUT);
		
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
	 * Test that a fake folder, without a investigation file throws an error.
	 */
	public void testFakeSample(){
		
		//Configure IsaTabIdReplacer
		IsaTabIdReplacer itr = new IsaTabIdReplacer(FOLDER_FAKE);
		
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
//		String[] accessionList;
//		String inputfile;
//		String outputfile;
		
		// Configure IsaTabIdReplacer
		IsaTabIdReplacer itr = new IsaTabIdReplacer(FOLDER_ISATAB1_OUT);
		
		// Set the date properties
		itr.setPublicDate("2011-02-02");
		
		try {
			
			//Accession list should be empty
			assertEquals(0, itr.getIds().size());
			
			//Execute the replacement method
 			itr.Execute();
 			
 			//Get the ids
 			HashMap<String,String> ids = itr.getIds();
 			
 			//There must be 1 studies..
 			assertEquals(1, ids.size());
 			
 			//We can not test the content as the Investigation is replaced but not returned in the HashMap
 			
// 			//Now, with these id we can test the output files and check if the replacement is done correctly.
// 			inputfile = FileUtil.file2String(FOLDER_ISATAB1+"i_Investigation.txt");
// 			outputfile= FileUtil.file2String(FOLDER_ISATAB1_OUT+"i_Investigation.txt");
// 
// 		    //replace ids by accession numbers
// 			Iterator it = ids.entrySet().iterator();
// 		    while (it.hasNext()) {
// 		        Map.Entry pairs = (Map.Entry)it.next();
//
// 		        //Replace the old id with the new ones
// 	 			inputfile = StringUtils.replace(inputfile, pairs.getKey().toString(), pairs.getValue().toString());
// 		    
// 		    }
// 			
// 			//Test if now matches
// 			assertEquals(inputfile, outputfile);
 			
 			
		}catch (Exception e){
			fail("testIsaTab1Sample has thrown an exception." + e.getMessage());
		}



	}
	@Test
	public void testMain() {
		
		try {
			IsaTabIdReplacer.main(new String[] {FOLDER_ISATABBII1_OUT});
		} catch (Exception e) {
			fail("Error invoking main: " + e.getMessage());
		}
		
		
	}
}
