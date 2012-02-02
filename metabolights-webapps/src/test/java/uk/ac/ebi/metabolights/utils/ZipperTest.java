package uk.ac.ebi.metabolights.utils;

import org.apache.commons.compress.archivers.ArchiveException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ZipperTest {

	
	@Test
	public void testZip() {
		final String outFolder = "./src/test/resources/zipper/unziptest/in/";
		final String inFolder = "./src/test/resources/zipper/ziptest/in/";
		final String zipFile = "./src/test/resources/zipper/unziptest/in.zip";
		
		String outMap;
		String inMap;
		
		//Get the map of the folder
		inMap = getFolderMap(inFolder);
		System.out.println(inMap);
		
		try{
		
			//Test the zip command
			Zipper.zip( inFolder, zipFile);
			
			//Test the unzip command
			Zipper.unzip(zipFile);
		
		}catch (IOException ioe){
			fail(ioe.getMessage());
		} catch (ArchiveException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //Check the output files and folders are correct
		outMap = getFolderMap(outFolder);
		
		assertEquals(inMap, outMap);
		
	}
	private String getFolderMap(String folderS){
		
		String map = "";
		
		//Get the folder object
		File folder = new File(folderS);
		
		//Return the folder map
		return getFolderMap (folder, map);
		
	}
	private String getFolderMap(File folderF, String map){
		
		//Get the list of files in 
		File[] fileList = folderF.listFiles();
		
		for(int i=0; i < fileList.length; i++)
		{
			
			//If the file or folder is not hidden
			if (!fileList[i].isHidden()){

				//Write in the map
				map = map + fileList[i].getName() + "\n";
							
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
