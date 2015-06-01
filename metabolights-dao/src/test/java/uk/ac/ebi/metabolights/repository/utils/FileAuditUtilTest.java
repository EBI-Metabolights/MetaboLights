package uk.ac.ebi.metabolights.repository.utils;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.model.Backup;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class FileAuditUtilTest extends TestCase {

	public static final String A_FOOO = "a_fooo";
	public static final String S_FOOO = "s_fooo";
	private File auditedFolder;
	private File i_file;
	private File a_file;
	private File s_file;
	private File m_file;
	private File ifile;
	private File u_file;
	private File testFolder;
	private File aFolder;

	@Before
	public void setUp() throws IOException {

		// Create a temporary directory
		testFolder= new File(FileUtils.getTempDirectory(), "FileAuditTest");
		testFolder.mkdir();
		FileUtils.cleanDirectory(testFolder);

		// Create some files
		i_file = createAFile("i_fooo");
		a_file = createAFile(A_FOOO);
		s_file = createAFile(S_FOOO);
		m_file = createAFile("m_fooo");
		ifile = createAFile("ifooo");
		u_file = createAFile("u_fooo");

		aFolder = createAFolder("aFolder");
		s_file = createAFile(S_FOOO, aFolder);
		createAFile(A_FOOO, aFolder);


		auditedFolder  = new File(testFolder,"important_data");
		auditedFolder.mkdir();


	}

	private File createAFile(String file) throws IOException {
		return createAFile(file, testFolder);
	}

	public File createAFile(String fileName, File folder) throws IOException {
		File newFile = new File(folder,fileName);
		newFile.createNewFile();
		return newFile;
	}

	public File createAFolder(String folderName) throws IOException {
		File newFolder = new File(testFolder, folderName);
		newFolder.mkdir();
		return newFolder;
	}

	@Test
	public void testGetAuditFolder(){

		File auditFolder = FileAuditUtil.getAuditFolder(auditedFolder);

		assertEquals("Audit folder test", getAuditFolderName(),auditFolder.getAbsolutePath());
		assertTrue("Audit folder exists", auditFolder.exists());

	}

	private String getAuditFolderName(){

		// Compose the expected audit folder path
		return new File(auditedFolder, FileAuditUtil.AUDIT_FOLDER_NAME).getAbsolutePath();
	}

	@Test
	public void testGetBackUpFolder() {

		File backUpFolder = FileAuditUtil.getBackUpFolder(auditedFolder);

		assertTrue("backup folder under audit folder", backUpFolder.getAbsolutePath().startsWith(getAuditFolderName()));
		assertFalse("backUp folder exists, and it shouldn't", backUpFolder.exists());


	}

	@Test
	public void testMoveFileToAuditedFolder() throws IOException, InterruptedException {

		File backUpFolder = FileAuditUtil.getBackUpFolder(auditedFolder);

		FileAuditUtil.moveFileToAuditedFolder(i_file,auditedFolder,backUpFolder, false);
		FileAuditUtil.moveFileToAuditedFolder(m_file,auditedFolder,backUpFolder, false);

		/**
		Status:

	 	auditedFolder/	i_file
		 				m_file
		 				audit/

		*/

		// New file moved + audit folder
		assertEquals("new audited file moved", 3, auditedFolder.list().length);
		// Nothing there since audited folder was empty
		assertEquals("backup folder was created and it shouldn't since there wasn't anything to backup.", false, backUpFolder.exists());

		// Create it again since it has been moved
		i_file.createNewFile();
		FileAuditUtil.moveFileToAuditedFolder(i_file,auditedFolder,backUpFolder, false);

		/**
		 Expected Status:

		 auditedFolder/	i_file
		 				m_file
		 				audit/[backupfolder]/i_file

		 */

		// New file moved + audit folder
		assertEquals("new audited file moved", 3, auditedFolder.list().length);
		// Back up should occur
		assertEquals("existing auditable file moved, backup happens", 1, backUpFolder.list().length);

		// Test a not audited file
		FileAuditUtil.moveFileToAuditedFolder(ifile,auditedFolder,backUpFolder, false);

		/**
		 Expected Status:

		 auditedFolder/	i_file
		 				m_file
		 				ifile
		 				audit/[backupfolder]/	i_file

		 */

		// New file moved
		assertEquals("new not audited file moved", 4, auditedFolder.list().length);

		// Nothing there since audited folder was empty
		assertEquals("new not audited not backed up", 1, backUpFolder.list().length);


		// Create it again since it has been moved
		ifile.createNewFile();
		FileAuditUtil.moveFileToAuditedFolder(ifile,auditedFolder,backUpFolder, false);

		/**
		 Expected Status:

		 auditedFolder/	i_file
		 				m_file
		 				ifile
						audit/[backupfolder]/i_file

		 */


		// Back up shouldn't occur
		assertEquals("existing not audited file replaced, backup does not happens", 1, backUpFolder.list().length);


		// Test one more auditable file
		FileAuditUtil.moveFileToAuditedFolder(a_file,auditedFolder,backUpFolder, false);

		/**
		 Expected Status:

		 auditedFolder/	i_file
		 				m_file
		 				ifile
						a_file
						audit/[backupfolder]/	i_file

		 */

		// New file moved
		assertEquals("new audited a_file moved", 5, auditedFolder.list().length);

		// Nothing there since audited folder was empty
		assertEquals("new a_auditable file moved, but nothing to backup", 1, backUpFolder.list().length);


		// Create it again since it has been moved
		a_file.createNewFile();
		FileAuditUtil.moveFileToAuditedFolder(a_file,auditedFolder,backUpFolder, false);

		/**
		 Expected Status:

		 auditedFolder/	i_file
		 				m_file
 						ifile
		 				a_file
						audit/[backupfolder]/	i_file
		 										a_file
		 */

		// Should have same number of files
		assertEquals("new audited a_file moved", 5, auditedFolder.list().length);

		// Back up should occur
		assertEquals("existing_auditable file moved, backup happens", 2, backUpFolder.list().length);


		Collection<Backup> backups = FileAuditUtil.getBackupsCollection(auditedFolder);

		assertEquals("FileAuditUtil does not populate the backup collection properly", 1, backups.size());

		// Sleep 1 second to get a new backup folder
		Thread.sleep(1000);

		a_file.createNewFile();
		FileAuditUtil.moveFileToAuditedFolder(a_file,auditedFolder);

		/**
		 Expected Status:

		 auditedFolder/	i_file
		 				ifile
		 				a_file
		 				m_file
		 				audit/	[backupfolder]/		i_file
		 											a_file
		 						[backupfolder2]/	m_file

		 */


		backups = FileAuditUtil.getBackupsCollection(auditedFolder);
		assertEquals("FileAuditUtil does not populate the backup collection properly", 2, backups.size());

		// Should have same number of files
		assertEquals("new audited a_file moved", 5, auditedFolder.list().length);

		// Sleep 1 second to get a new backup folder
		Thread.sleep(1000);

		File newBackupFolder = FileAuditUtil.moveFolderContentToAuditFolder(aFolder, auditedFolder);

		/**
		 Expected Status:

		 auditedFolder/	i_file
		 				m_file
		 				ifile
		 				a_file
		 				s_file
		 				audit/	[backupfolder]/		i_file
		 											a_file
		 						[backupfolder2]/	a_file
		 						[backupfolder3]/	i_file
		 											m_file
		 											a_file
		 */

		// Should have same number of files
		assertEquals("Audited folder has wrong file count after moving a folder", 6, auditedFolder.list().length);

		backups = FileAuditUtil.getBackupsCollection(auditedFolder);

		assertEquals("FileAuditUtil does not populate the backup collection properly", 3, backups.size());

		// Back up should occur
		assertEquals("No all auditable files have been backed up after moving a folder", 3, newBackupFolder.list().length);



	}

	@Test
	public void testIdFileAudited() throws Exception {

		assertTrue("i_file should be audited",FileAuditUtil.isFileAudited(i_file));
		assertTrue("s_file should be audited",FileAuditUtil.isFileAudited(s_file));
		assertTrue("a_file should be audited",FileAuditUtil.isFileAudited(a_file));
		assertTrue("m_file should be audited",FileAuditUtil.isFileAudited(m_file));
		assertFalse("u_file should not be audited", FileAuditUtil.isFileAudited(u_file));
		assertFalse("ifile should not be audited", FileAuditUtil.isFileAudited(ifile));
	}
}