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

package uk.ac.ebi.metabolights.utils;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class FileUtilTest {

	//Files
	final String FILE_INI = "src/test/resources/inputfiles/fake.txt";
	final String FILE_OUT = "src/test/resources/outputfiles/fake.txt";

	//Texts...
	final String TEXT_INI = "This is used in FileUtil also\r\n";
	final String FIND = "i[s|n]";
	final String REPLACE = "xx";
	final String TEXT_EXPECTED = "Thxx xx used xx FileUtil also\r\n";

	@Test
	public void testFile2String() throws IOException {
		String text;
		
		//Invoke the method
		text = FileUtil.file2String(FILE_INI);
		
		assertEquals(TEXT_INI, text);
	}

	@Test
	public void testString2File() throws IOException {
		String text;

		//Invoke the method
		FileUtil.String2File(TEXT_INI, FILE_OUT);
		
		//Check if the file saved is correct
		text = FileUtil.file2String(FILE_OUT);
		
		assertEquals(TEXT_INI, text);
		
	}

	@Test
	public void testReplace() throws IOException {

		String text;
		
		//We will use the output of the string to file test
		FileUtil.replace(FILE_OUT, FIND, REPLACE);
		
		//Check if the file saved is correct
		text = FileUtil.file2String(FILE_OUT);
		
		assertEquals(TEXT_EXPECTED, text);
		
	}
	@Test
	public void testFileExistance(){
		
		assertTrue("Test file exists with an existance file", FileUtil.fileExists(FILE_INI));
		assertTrue("Test folder exists with an existance folder", FileUtil.fileExists("."));
		
		assertTrue ("Test file exists with a non existance file", !FileUtil.fileExists("fooo"));
	}
	@Test
	public void testFileExistanceThrowingException1(){

		try{
			assertTrue("Test file exists with an existance file", FileUtil.fileExists(FILE_INI,true));
		}catch(Exception e){
			fail ("FileUtil.fileExists throw an unexpected exception when the file exists");
		}

	}
	@Test
	public void testFileExistanceThrowingException2(){

		try{
			assertTrue("Test folder exists with an existance folder", FileUtil.fileExists(".",true));
		}catch(Exception e){
			fail ("FileUtil.fileExists throw an unexpected exception when the folder exists");
		}
			
	}
	@Test
	public void testFileExistanceThrowingException3(){

		try{
			FileUtil.fileExists(".",true);
		}catch(IOException ioe){
			assertTrue("Test folder exists with a non existance path throws the correct IOException",true);
		}
			
	}

}
