package uk.ac.ebi.metabolights.utils;

import static org.junit.Assert.*;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

public class ZipperTest {

	@Test
	public void testZip() {
		
		String inFolder = "./src/test/resources/zipper/ziptest/in/";
		String inMap;
		
		String zipFile = "./src/test/resources/zipper/unziptest/in.zip";
		
		String outFolder = "./src/test/resources/zipper/unziptest/in";
		String outMap;
		
		
		
		//Get the map of the folder
		inMap = getFolderMap(inFolder);
		System.out.println(inMap);
		
		
		//Test the zip command
		Zipper.zip( inFolder, zipFile);
		
		//Test the unzip command
		Zipper.unzip(zipFile);

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
			
			//Write in the map
			map = map + fileList[i].getName() + "\n";
						
			//If it is a directory
			if (fileList[i].isDirectory()){
				
				//Get the map under this folder
				map = getFolderMap(fileList[i], map);
				
			}
			
		}
		
		return map;
		
	}
	
	
}
