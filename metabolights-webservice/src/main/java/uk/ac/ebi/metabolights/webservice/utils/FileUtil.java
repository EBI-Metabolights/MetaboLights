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

package uk.ac.ebi.metabolights.webservice.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.webservice.services.PropertyLookUpService;
import static java.nio.file.StandardCopyOption.*;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;


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
	 * @param fileToUse
	 * @return
	 * @throws java.io.IOException
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
	 * @throws java.io.IOException
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
	 * Deletes all fileNames and subdirectories under dir.
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

		//Check existence of the fileNames or folders
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

	// Get the file, stream to browser
	// Stream a file to the browser
	public static void streamFile(File file, HttpServletResponse response){

		// get the extension
		String extension = org.springframework.util.StringUtils.getFilenameExtension(file.getName());

		// let the browser know the type of file
		String contenType = "application/" + extension;

		streamFile(file,response,contenType);


	}
	public static void streamFile(File file, HttpServletResponse response, String contentType ){

		try {

			// get your file as InputStream
			InputStream is = new FileInputStream(file);

			// let the browser know the type of file
			response.setContentType(contentType);

			// Specify the file name
			response.setHeader( "Content-Disposition", "filename=" + file.getName()   );

			// copy it to response's OutputStream
			IOUtils.copy(is, response.getOutputStream());

		} catch (FileNotFoundException e) {
			logger.info("Can't stream file "+ file.getAbsolutePath() + "!, File not found.");
			throw new RuntimeException(PropertyLookUpService.getMessage("Entry.fileMissing"));
		} catch (IOException ex) {
			logger.info("Error writing file to output stream. Filename was '"+ file.getAbsolutePath() + "'");
			throw new RuntimeException(PropertyLookUpService.getMessage("Entry.fileMissing"));
		}
	}


	/**
	 * Delete a single file
	 *
	 * @param fileName to be deleted
	 * @return success/fail status
	 * @author jrmacias
	 * @date 20151012
	 */
	public static boolean deleteFile(String fileName) {

		boolean result = false;

		try{
			// try to delete the file
			result = Files.deleteIfExists(FileSystems.getDefault().getPath(fileName));
			logger.info("File {} have {} been deleted.",fileName, result?"successfully":"not");
		}catch (IOException ex){
			logger.error("Error deleting file: {}", ex.getMessage());
		}
		return result;
	}

	/**
	 * Delete a list of files
	 *
	 * @param fileNames, a list of file names to be deleted
	 * @return a string with a list of filenames and whether they were deleted ot not
	 * @author jrmacias
	 * @date 20151012
	 */
	public static String deleteFiles(List<String> fileNames){

		StringBuffer result = new StringBuffer();

		for (String fileName : fileNames) {
			result.append(new File(fileName).getName()).append(", ").append("file was ")
					.append(deleteFile(fileName) ? "":"NOT ").append("deleted.").append("|");
		}
		return result.toString();
	}


	private static @Value("#{privateFTPDirectory}") String privateFTPRoot;
	private static @Value("#{privateFTPUser}") String privateFTPUser;
	private static @Value("#{privateFTPGroup}") String privateFTPGroup;
	/**
	 * Create a private FTP folder for uploading big study files
	 *
	 * @param folder
	 * @return a String containing created folder
	 * @author jrmacias
	 * @date 20151102
	 */
	@PostConstruct
	public static Path createFTPFolder(String folder) throws IOException {

		// TODO delete this
		if(privateFTPRoot == null) privateFTPRoot = "/Users/jrmacias/Projects/Deploy-local/ebi/ftp/private/mtblight/private";
		if(privateFTPUser == null) privateFTPUser = "jrmacias";
		if(privateFTPGroup == null) privateFTPGroup = "staff";

		// create the folder
		File ftpFolder = new File(privateFTPRoot + File.separator + folder);
		Path folderPath = ftpFolder.toPath();
		ftpFolder.mkdir();

		// set folder owner, group and access permissions
		// 'chmod 770'
		String ownerName = privateFTPUser;
		String groupName = privateFTPGroup;
		UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
		Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();

		// owner permissions
		perms.add(PosixFilePermission.OWNER_READ);
		perms.add(PosixFilePermission.OWNER_WRITE);
		perms.add(PosixFilePermission.OWNER_EXECUTE);
		// group permissions
		perms.add(PosixFilePermission.GROUP_READ);
		perms.add(PosixFilePermission.GROUP_WRITE);
		perms.add(PosixFilePermission.GROUP_EXECUTE);

		// get the actual user and group from System
		UserPrincipal owner = lookupService.lookupPrincipalByName(ownerName);
		GroupPrincipal group = lookupService.lookupPrincipalByGroupName(groupName);

		// apply changes
		Files.getFileAttributeView(folderPath, PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS).setOwner(owner);
		Files.getFileAttributeView(folderPath, PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS).setGroup(group);
		Files.getFileAttributeView(folderPath, PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS).setPermissions(perms);

		return folderPath;
	}

	/**
	 * Move a single file from a private FTP folder to MetaboLights
	 *
	 * @param fileName
	 * @param ftpFolder
	 * @param studyFolder
     * @return
	 * @author jrmacias
	 * @date 20151104
     */
	private static boolean moveFileFromFTP(String fileName, String ftpFolder, String studyFolder) {
		boolean result = false;

		// TODO delete this
		if(privateFTPRoot == null) privateFTPRoot = "/Users/jrmacias/Projects/Deploy-local/ebi/ftp/private/mtblight/private";

		// move the file
		Path filePath = Paths.get(
				privateFTPRoot +
				File.separator + ftpFolder +
				File.separator + fileName);
		Path studyPath  = Paths.get(studyFolder +
				File.separator + fileName);

		try {
			Files.move(filePath, studyPath, ATOMIC_MOVE);
			result = true;
		} catch (IOException ex) {
			logger.error("Error: can't move the file. {}", ex.getMessage());
		}

		return result;
	}

	/**
	 * Move a list of files from a private FTP folder to MetaboLights
	 *
	 * @param fileNames
	 * @param ftpFolder
	 * @param studyFolder
	 * @return a String with a list of files + status (moved / not moved)
	 * @author jrmacias
	 * @date 20151104
     */
	public static String moveFilesFromPrivateFTP(List<String> fileNames, String ftpFolder, String studyFolder) {

		StringBuffer result = new StringBuffer();

		for (String fileName : fileNames) {
			result.append(new File(fileName).getName()).append(", ").append("file was ").
					append(moveFileFromFTP(fileName, ftpFolder, studyFolder) ? "":"NOT ").append("moved.").append("|");
		}
		return result.toString();
	}


}
