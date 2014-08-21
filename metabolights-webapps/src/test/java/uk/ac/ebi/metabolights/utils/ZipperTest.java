/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/11/14 5:20 PM
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

import org.apache.commons.compress.archivers.ArchiveException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ZipperTest {

	
	@Test
	public void testZip() throws IOException {

		final String EXPECTED_ZIP_MAP = "TMP/test\n" +
										"TMP/test/a.txt\n" +
										"TMP/test/folder\n" +
										"TMP/test/folder/b.txt";



		String[] expectedMap = map2FileSystem(EXPECTED_ZIP_MAP);

		String folderToZip = expectedMap[0];


		String tmpDir = System.getProperty("java.io.tmpdir");
		File zipFile = new File(tmpDir + "zipTest.zip");
		File unzipFolder =new File(zipFile.getAbsolutePath().replace(".zip", ""));

		FileUtil.deleteDir(zipFile);
		FileUtil.deleteDir(unzipFolder);

		try{
		
			//Test the zip command
			Zipper.zip( folderToZip, zipFile.getAbsolutePath());
			
			//Test the unzip command
			Zipper.unzip(zipFile.getAbsolutePath());
		
		}catch (IOException ioe){
			fail(ioe.getMessage());
		} catch (ArchiveException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //Check the output files and folders are correct

		String[] outMap = getFolderMap(unzipFolder.getAbsolutePath());

		// Check lengths: root folder in expected is not expected in the outMap: therefore -1
		assertEquals("Check that lenght is the same" , expectedMap.length-1, outMap.length);
		
	}

	private String[] map2FileSystem(String zip_map) throws IOException {

		String tempFolder = System.getProperty("java.io.tmpdir");

		zip_map = zip_map.replaceAll("TMP/", tempFolder);

		String files[] = zip_map.split("\n");

		for (String path: files) {

			File file = new File(path);

			if (file.exists()) file.delete();

			if (path.contains("."))
			{
				file.createNewFile();

			} else {
				file.mkdir();
			}

		}

		return files;

	}

	private String[] getFolderMap(String folderS){
		
		String map = "";
		
		//Get the folder object
		File folder = new File(folderS);
		
		//Return the folder map
		String mapS = getFolderMap (folder, map);

		return mapS.split("\n");
		
	}
	private String getFolderMap(File folderF, String map){
		
		//Get the list of files in 
		File[] fileList = folderF.listFiles();
		
		for(int i=0; i < fileList.length; i++)
		{
			
			//If the file or folder is not hidden
			if (!fileList[i].isHidden()){

				//Write in the map
				map = map + fileList[i].getAbsolutePath() + "\n";
							
				//If it is a not hidden directory
				if (fileList[i].isDirectory()){
					
					//Get the map under this folder
					map = getFolderMap(fileList[i], map);
					
				}

			}
			
		}
		
		return map;
		
	}
	@Test
	public void testInvalidZipFile(){
	
		try{
			
			Zipper.unzip("wrongpath");
			
			//If it does not throws an exception, It is wrong
			fail("Zipper.unzip must throw an exception with a wrong file");
		}catch(IOException ioe){
			
			assertTrue("IOException expected", true);
		} catch (ArchiveException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
	@Test
	public void testInvalidFileToZip(){
	
		try{
			
			Zipper.zip("wrongpath","wrongpath");
			
			//If it does not throws an exception, It is wrong
			fail("Zipper.zip must throw an exception with a wrong file or folder");
			
		}catch(IOException ioe){
			
			assertTrue("IOException expected", true);
		}
		
	}
	@Test
	public void testISAUnzipCreatorArchive(){
		
		final String ISAArchiveName = "ISACREATOR1.4_archive_FAST.zip";
		String ISAArchive = "./src/test/resources/zipper/unziptest/" + ISAArchiveName;
		
		try {
			Zipper.unzip(ISAArchive);
		} catch (IOException ioe) {
			fail(ioe.getMessage());
		} catch (ArchiveException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
	
	
}
