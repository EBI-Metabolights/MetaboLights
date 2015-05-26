package uk.ac.ebi.metabolights.repository.model;

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
	private File folder;

	public Backup(File folder, Date backupTimeStamp){
		this.folder = folder;
		this.backupTimeStamp = backupTimeStamp;
	}

}