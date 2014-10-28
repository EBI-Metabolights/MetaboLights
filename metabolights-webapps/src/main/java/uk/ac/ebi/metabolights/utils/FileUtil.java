/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/11/14 5:12 PM
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class FileUtil {

	private static Logger logger = LoggerFactory.getLogger (FileUtil.class);
	
	public static void replace (String fileToSearchIn, String textToSearch, String textToReplace) throws IOException{
		String text;
		
		//Get the file into a string
		text = file2String(fileToSearchIn);
		
		//Replace the content
		text = StringUtils.replace(text, textToSearch, textToReplace);
		
		//Save the file
		String2File(text, fileToSearchIn);
		
		
	}
	/**
	 * Returns a string with the contents of a file.
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static String file2String (String fileToUse) throws IOException{
		
		//Instantiate a file object
		File file = new File (fileToUse);
		
		//Use a buffered reader
		BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "";
        String text = "";
        
        //Go through the file
        while((line = reader.readLine()) != null)
        {
            //Add the final carriage return and line feed
        	text += line + "\r\n";
        }
        
        //Close the reader
        reader.close();
        
        //Return the String
        return text;
	}
	/**
	 * Takes the String passed and saves it in a file 
	 * @param text: Text to save
	 * @param fileToSave: File to save (create) with "text" inside.
	 * @throws IOException 
	 */
	public static void String2File (String text, String fileToSave) throws IOException{

		//instantiate a FileWriter
        FileWriter writer = new FileWriter(fileToSave);
        
        //Write the text
        writer.write(text);
        
        //Close the writer
        writer.close();
	}
	/**
	 * Deletes all files and subdirectories under dir.
	 * 
	 * @param dir to delete
	 * @return Returns true if all deletions were successful.
	 * If a deletion fails, the method stops attempting to delete and returns false.
	 */
	public static boolean deleteDir(File dir) {
	    if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }

	    // The directory is now empty so delete it
	    return dir.delete();
	}

	public static boolean fileExists(String path){
		File file = new File(path);

		return file.exists();
	}

	public static boolean fileExists(String path, boolean throwException) throws FileNotFoundException{

		return fileExists(new File(path), throwException);
		
	}

	public static boolean filesExists(File[] files, boolean throwException) throws FileNotFoundException{

		//Check existence of the files or folders
		for (File file:files)
		{
			if (fileExists(file, true))
			{
				return false;
			};
		}

		return true;

	}

	public static boolean fileExists(File file, boolean throwException) throws FileNotFoundException{

		boolean result = file.exists();

		if (throwException && !result){

			throw new FileNotFoundException ("Path (" + file.getAbsolutePath() + ") not found.");
		}

		return result;

	}


}
