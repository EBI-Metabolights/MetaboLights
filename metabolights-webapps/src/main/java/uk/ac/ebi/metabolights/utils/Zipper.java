package uk.ac.ebi.metabolights.utils;
/*
Create Zip File From Directory recursively using ZipOutputStream Example
This Java example shows how create zip file from directory recursively 
using Java ZipOutputStream class. This program also shows how to add
directory and sub-directories to zip file.
*/

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


public class Zipper {


public static void zip(String thisFileOrDir, String toThisFile) throws IOException {
	
	try
	{
		
		//Check existence of the file or folder
		FileUtil.fileExists(thisFileOrDir, true);
		
		//create object of FileOutputStream
		FileOutputStream fout = new FileOutputStream(toThisFile);

		//create object of ZipOutputStream from FileOutputStream
		ZipOutputStream zout = new ZipOutputStream(fout);

		//create File object from source directory
		File fileSource = new File(thisFileOrDir);

		addDirectory(zout, fileSource, "");

		//close the ZipOutputStream
		zout.close();

		System.out.println("Zip file has been created!");

	}
	catch(IOException ioe)
	{
		System.out.println("IOException :" + ioe);
		throw ioe;
	}
}

private static void addDirectory(ZipOutputStream zout, File fileSource, String innerFolder) throws IOException {

	//If the directory is hidden...it is zipping the svn folders..
	if (fileSource.isHidden()){ 
		System.out.println("Skiping hidden folder " + fileSource.getName());
		return;
	}
	
	
	//get sub-folder/files list
	File[] files = fileSource.listFiles();

	System.out.println("Adding directory " + fileSource.getName());

	for(int i=0; i < files.length; i++)
	{
		//if the file is directory, call the function recursively
		if(files[i].isDirectory())
		{
			addDirectory(zout, files[i], innerFolder + files[i].getName() + "/");
			continue;
		}

		/*
		 * we are here means, its file and not directory, so
		 * add it to the zip file
		 */

		try
		{
			System.out.println("Adding file " + files[i].getName());

			//create byte buffer
			byte[] buffer = new byte[1024];

			//create object of FileInputStream
			FileInputStream fin = new FileInputStream(files[i]);

			zout.putNextEntry(new ZipEntry( innerFolder + files[i].getName()));

			/*
			 * After creating entry in the zip file, actually 
			 * write the file.
			 */
			int length;

			while((length = fin.read(buffer)) > 0)
			{
			   zout.write(buffer, 0, length);
			}

			/*
			 * After writing the file to ZipOutputStream, use
			 * 
			 * void closeEntry() method of ZipOutputStream class to 
			 * close the current entry and position the stream to 
			 * write the next entry.
			 */

			 zout.closeEntry();

			 //close the InputStream
			 fin.close();

		}
		catch(IOException ioe)
		{
			System.out.println("IOException :" + ioe);
			throw ioe;
		}
	}

}

public static void unzip (String strZipFile) throws IOException, ArchiveException {

	/*
	 * STEP 1 : Create directory with the name of the zip file
	 * 
	 * For e.g. if we are going to extract c:/demo.zip create c:/demo 
	 * directory where we can extract all the zip entries
	 * 
	 */
	
	//Check existence
	FileUtil.fileExists(strZipFile, true);
	
	String zipPath = StringUtils.truncate(strZipFile, 4);
	File temp = new File(zipPath);
	temp.mkdir();
	System.out.println(zipPath + " created");
	
	//Call unzip with the path
	unzip2(strZipFile, zipPath);
	
}

    public static void unzip2(String strZipFile, String folder) throws IOException, ArchiveException{

        //Check if the file exists
        FileUtil.fileExists(strZipFile, true);

        final InputStream is = new FileInputStream(strZipFile);
        ArchiveInputStream in = new ArchiveStreamFactory().createArchiveInputStream("zip", is);

        ZipArchiveEntry entry = null;
        OutputStream out = null;


        while ((entry = (ZipArchiveEntry) in.getNextEntry()) != null) {

            //create directories if required.
            File zipPath = new File(folder);
            File destinationFilePath = new File(zipPath, entry.getName());
            destinationFilePath.getParentFile().mkdirs();

            //if the entry is directory, leave it. Otherwise extract it.
            if(entry.isDirectory())
            {
                continue;
            }
            else {
                out = new FileOutputStream(new File(folder, entry.getName()));
                IOUtils.copy(in, out);
                out.close();
            }

        }

        in.close();

    }


public static void unzip(String strZipFile, String folder) throws IOException {
	 
	try
	{

		//Check existence
		FileUtil.fileExists(strZipFile, true);
		
		File fSourceZip = new File(strZipFile);
		File zipPath = new File(folder);
		/*
		 * STEP 2 : Extract entries while creating required
		 * sub-directories
		 * 
		 */
		ZipFile zipFile = new ZipFile(fSourceZip);
		Enumeration e = zipFile.entries();

		while(e.hasMoreElements())
		{
			ZipEntry entry = (ZipEntry)e.nextElement();
			File destinationFilePath = new File(zipPath,entry.getName());

			//create directories if required.
			destinationFilePath.getParentFile().mkdirs();

			//if the entry is directory, leave it. Otherwise extract it.
			if(entry.isDirectory())
			{
				continue;
			}
			else
			{
				System.out.println("Extracting " + destinationFilePath);

				/*
				 * Get the InputStream for current entry
				 * of the zip file using
				 * 
				 * InputStream getInputStream(Entry entry) method.
				 */
				BufferedInputStream bis = new BufferedInputStream(zipFile
														.getInputStream(entry));

				int b;
				byte buffer[] = new byte[1024];

				/*
				 * read the current entry from the zip file, extract it
				 * and write the extracted file.
				 */
				FileOutputStream fos = new FileOutputStream(destinationFilePath);
				BufferedOutputStream bos = new BufferedOutputStream(fos,
								1024);

				while ((b = bis.read(buffer, 0, 1024)) != -1) {
						bos.write(buffer, 0, b);
				}

				//flush the output stream and close it.
				bos.flush();
				bos.close();

				//close the input stream.
				bis.close();
			}
		}
	}
	catch(IOException ioe)
	{
		System.out.println("IOError :" + ioe);
		throw ioe;
	}

}

}