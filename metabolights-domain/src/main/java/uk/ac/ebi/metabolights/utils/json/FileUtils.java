package uk.ac.ebi.metabolights.utils.json;

import uk.ac.ebi.metabolights.repository.model.MLLProject;
import uk.ac.ebi.metabolights.repository.model.MLLWorkSpace;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by venkata on 30/01/2017.
 */
public class FileUtils {

    /**
     * Takes the String passed and saves it in a file
     * @param fileToSave : File to save (create) with "text" inside.
     * @throws java.io.IOException
     */
    public static void workspace2File(MLLWorkSpace mllWorkSpace, String fileToSave) throws IOException {

        //instantiate a FileWriter
        FileWriter writer = new FileWriter(fileToSave);

        //Write the text
        writer.write(mllWorkSpace.getAsJSON());

        //Close the writer
        writer.close();
    }

    public static Boolean checkFileExists(String fileName){
        if (fileName == null || fileName.isEmpty())
            return false;        // No filename given

        File f = new File(fileName);

        if(f.exists())
            return true;        // Found the file

        return false;           // Did not find the file

    }


    /**
     * Takes the String passed and saves it in a file
     * @param fileToSave : File to save (create) with "text" inside.
     * @throws java.io.IOException
     */
    public static void project2File(MLLProject mllProject, String fileToSave) throws IOException {

        //instantiate a FileWriter
        FileWriter writer = new FileWriter(fileToSave);

        //Write the text
        writer.write(mllProject.getAsJSON());

        //Close the writer
        writer.close();
    }

    public static Path createFolder(String newFolderPath) throws IOException {

        // create the folder
        File newFolder = new File(newFolderPath);
        Path folderPath = newFolder.toPath();
        if (!newFolder.mkdir()) throw new IOException();

        // set folder owner, group and access permissions
        // 'chmod 770'
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
        // apply changes
        Files.getFileAttributeView(folderPath, PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS).setPermissions(perms);

        return folderPath;
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

    static String filePrefix = ".DELETEME-";

    /**
     * Delete a list of files from the private upload folder, upon user request
     *
     * @param fileNames
     * @param projectFolder
     * @return
     * @author jrmacias
     * @date 20151204
     */
    public static String deleteFilesFromProject(List<String> fileNames, String projectFolder) {
        StringBuffer result = new StringBuffer();

        for (String fileName : fileNames) {
            result.append(new File(fileName).getName()).append(", ").append("file was ")
                    .append(deleteFileFromProject(fileName,projectFolder) ? "":"NOT ").append("deleted.").append("|");
        }

        return result.toString();
    }

    /**
     * Delete a file from the private upload folder, upon user request
     * Although, actually we are no longer moving the file,
     * just re-naming it to be deleted later by a bash script
     *
     * @param fileName
     * @param projectFolder
     * @return
     * @author jrmacias
     * @date 20151204
     */
    private static boolean deleteFileFromProject(String fileName, String projectFolder) {

        boolean result = false;

        Path filePath = Paths.get( "" + File.separator +
                projectFolder + File.separator +
                fileName);

        // we are no longer moving the file...
        // ...just re-naming it to be deleted later by a bash script
        filePath.toFile().renameTo(new File( "" + File.separator +
                projectFolder + File.separator +
                filePrefix + fileName));
        result = true;

        return result;
    }

}
