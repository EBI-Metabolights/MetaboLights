package uk.ac.ebi.metabolights.repository.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.model.Backup;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: conesa
 * Date: 15/04/15
 * Time: 14:11
 */
public class FileAuditUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileAuditUtil.class);
	public static final String AUDIT_FOLDER_NAME = "audit";
	public static final String AUDIT_SUBFOLDER_NAME_PATTERN = "yyyyMMddHHmmss";
	private static DateFormat df = new SimpleDateFormat(AUDIT_SUBFOLDER_NAME_PATTERN);
	public static final String BACKUP_FILE_NAME_PATTERN = "[isma]_.*";

	public static File getBackUpFolder(File folderToBackUp) {


		File auditFolder = getAuditFolder(folderToBackUp);

		// Get the date today using Calendar object.
		Date today = Calendar.getInstance().getTime();

		// Using DateFormat format method we can create a string
		// representation of a date with the defined format.
		String backupDestination  = df.format(today);

		File backUpFolder = new File(auditFolder,backupDestination);

		//backUpFolder.mkdir();
		return  backUpFolder;

	}

	public static File getAuditFolder(File folderToBackUp) {

		File auditFolder = new File (folderToBackUp, AUDIT_FOLDER_NAME);

		if (!auditFolder.exists()) {
			auditFolder.mkdir();
		}

		return auditFolder;

	}
	public static void moveFileToAuditedFolder(File fileToMove, File auditedFolder) throws IOException {

		File backUpFolder = getBackUpFolder(auditedFolder);

		moveFileToAuditedFolder(fileToMove,auditedFolder,backUpFolder, false);
	}

	public static void moveFileToAuditedFolder(File fileToMove, File auditedFolder, File backUpFolder, boolean copy) throws IOException {

		// Compose the file to back up
		File fileToSave = new File (auditedFolder,fileToMove.getName());

		backUpFile(fileToSave,backUpFolder);

		fileToSave.delete();

		if (copy) {

			FileUtils.copyFileToDirectory(fileToMove,auditedFolder);

		} else {

			// Now move the new file
			FileUtils.moveFileToDirectory(fileToMove, auditedFolder, true);

		}

	}

	public static void moveFolderContentToAuditFolder(File folderWithFilesToMove, File auditedFolder , boolean copy) throws IOException {

		File backUpFolder = getBackUpFolder(auditedFolder);

		moveFolderContentToAuditFolder(folderWithFilesToMove,auditedFolder, backUpFolder, copy);

	}

	public static void moveFolderContentToAuditFolder(File folderWithFilesToMove, File auditedFolder ) throws IOException {


		moveFolderContentToAuditFolder(folderWithFilesToMove,auditedFolder, false);

	}

	public static void copyFolderContentToAuditFolder(File folderWithFilesToMove, File auditedFolder ) throws IOException {


		moveFolderContentToAuditFolder(folderWithFilesToMove,auditedFolder, true);

	}


	public static void moveFolderContentToAuditFolder(File folderWithFilesToMove, File auditedFolder, File backUpFolder, boolean copy) throws IOException {

		// If it's a file....let's be flexible and treat it as a single file move
		if (!folderWithFilesToMove.isDirectory()) {

			moveFileToAuditedFolder(folderWithFilesToMove, auditedFolder,backUpFolder, copy);

		} else {

			// For each file/folder under the folderwith content
			for (File file : folderWithFilesToMove.listFiles()) {
				moveFileToAuditedFolder(file, auditedFolder,backUpFolder, copy);
			}

		}

	}

	public static void moveFolderContentToAuditFolder(File folderWithFilesToMove, File auditedFolder, File backUpFolder) throws IOException {
		moveFolderContentToAuditFolder(folderWithFilesToMove,auditedFolder,backUpFolder,false);
	}

	public static void copyFolderContentToAuditFolder(File folderWithFilesToMove, File auditedFolder, File backUpFolder) throws IOException {
		moveFolderContentToAuditFolder(folderWithFilesToMove,auditedFolder,backUpFolder,true);
	}

	public static void backUpFile(File fileToBackUp) throws IOException {

		File backUpFolder = getBackUpFolder(fileToBackUp.getParentFile());
		backUpFile(fileToBackUp,backUpFolder);
	}
	public static void backUpFile(File fileToBackUp, File backUpFolder) throws IOException {

		// If the pattern is found
		if (isFileAudited(fileToBackUp)) {

			if (fileToBackUp.exists()) {

				logger.info("backing up {}", fileToBackUp.getAbsolutePath());
				FileUtils.moveFileToDirectory(fileToBackUp, backUpFolder, true);
			}

		}

	}

	public static boolean isFileAudited(File fileToMove) {
		// Get the files to back up
		Pattern p  = Pattern.compile(BACKUP_FILE_NAME_PATTERN);
		Matcher m = p.matcher(fileToMove.getName());

		boolean result =m.matches();

		return result;
	}

	public static Collection<Backup> getBackupsCollection(File backedUpFolder){

		File auditFolder = getAuditFolder(backedUpFolder);

		LinkedList<Backup> backups = new LinkedList<>();

		if (auditFolder.exists()) {



			for (File file : auditFolder.listFiles()) {
				Date timeStamp = backupFolderName2Date(file.getName());
				Backup backup = new Backup(file, timeStamp);
				backups.add(backup);
			}

		}

		return backups;
	}

	private static Date backupFolderName2Date(String backupFolderName) {

		try {
			return df.parse(backupFolderName);
		} catch (ParseException e) {
			logger.warn("Can't parse backup folder name into date!");
		}

		return null;

	}
}