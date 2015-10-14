package uk.ac.ebi.metabolights.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;

/**
 * User: conesa
 * Date: 26/05/15
 * Time: 14:02
 */
public class Backup {

	private static final Logger logger = LoggerFactory.getLogger(Backup.class);
	private Date backupTimeStamp;
	private String backupId;
	private File folder;

	public Backup(File folder, Date backupTimeStamp){
		this.folder = folder;
		this.backupTimeStamp = backupTimeStamp;
		this.backupId = folder.getName();
	}
	public Backup(){
	}

	public Date getBackupTimeStamp() {
		return backupTimeStamp;
	}

	public void setBackupTimeStamp(Date backupTimeStamp) {
		this.backupTimeStamp = backupTimeStamp;
	}
	@JsonIgnore
	public File getFolder() {
		return folder;
	}

	public void setFolder(File folder) {
		this.folder = folder;
	}

	public String getFolderPath() {
		return folder.getAbsolutePath();
	}

	public void setFolderPath(String folder) {
		this.folder = new File(folder);
	}

	public String getBackupId() {
		return backupId;
	}

	public void setBackupId(String backupId) {
		this.backupId = backupId;
	}
}