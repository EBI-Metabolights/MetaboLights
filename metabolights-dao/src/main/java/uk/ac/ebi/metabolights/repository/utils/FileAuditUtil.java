package uk.ac.ebi.metabolights.repository.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	public static final String BACKUP_FILE_NAME_PATTERN = "[isma]_.*";

	public static File getBackUpFolder(File folderToBackUp) {


		File auditFolder = getAuditFolder(folderToBackUp);

		DateFormat df = new SimpleDateFormat(AUDIT_SUBFOLDER_NAME_PATTERN);

		// Get the date today using Calendar object.
		Date today = Calendar.getInstance().getTime();

		// Using DateFormat format method we can create a string
		// representation of a date with the defined format.
		String backupDestination  = df.format(today);

		File backUpFolder = new File(auditFolder,backupDestination);

		backUpFolder.mkdir();
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

		moveFileToAuditedFolder(fileToMove,auditedFolder,backUpFolder);
	}

	public static void moveFileToAuditedFolder(File fileToMove, File auditedFolder, File backUpFolder) throws IOException {

		// Compose the file to back up
		File fileToSave = new File (auditedFolder,fileToMove.getName());

		backUpFile(fileToSave,backUpFolder);

		fileToSave.delete();
		// Now move the new file
		FileUtils.moveFileToDirectory(fileToMove, auditedFolder, true);

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

}