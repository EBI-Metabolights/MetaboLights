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
		s_file = createAFile("s_fooo");
		m_file = createAFile("m_fooo");
		ifile = createAFile("ifooo");
		u_file = createAFile("u_fooo");

		aFolder = createAFolder("aFolder");
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
		assertTrue("backUp folder exists", backUpFolder.exists());

	}

	@Test
	public void testMoveFileToAuditedFolder() throws IOException, InterruptedException {

		File backUpFolder = FileAuditUtil.getBackUpFolder(auditedFolder);

		FileAuditUtil.moveFileToAuditedFolder(i_file,auditedFolder,backUpFolder, false);

		// Nothing there since audited folder was empty
		assertEquals("backup folder was created and it shouldn't since there wasn't anything to backup.", false, backUpFolder.exists());

		// Create it again since it has been moved
		i_file.createNewFile();
		FileAuditUtil.moveFileToAuditedFolder(i_file,auditedFolder,backUpFolder, false);

		// Back up should occur
		assertEquals("existing auditable file moved, backup happens", 1, backUpFolder.list().length);

		// Test a not audited file
		FileAuditUtil.moveFileToAuditedFolder(ifile,auditedFolder,backUpFolder, false);

		// Nothing there since audited folder was empty
		assertEquals("new not audited file moved, nothing to backup", 1, backUpFolder.list().length);


		// Create it again since it has been moved
		ifile.createNewFile();
		FileAuditUtil.moveFileToAuditedFolder(ifile,auditedFolder,backUpFolder, false);

		// Back up shouldn't occur
		assertEquals("existing not audited file replaced, backup does not happens", 1, backUpFolder.list().length);


		// Test one more auditable file
		FileAuditUtil.moveFileToAuditedFolder(a_file,auditedFolder,backUpFolder, false);

		// Nothing there since audited folder was empty
		assertEquals("new a_auditable file moved, but nothing to backup", 1, backUpFolder.list().length);

		// Create it again since it has been moved
		a_file.createNewFile();
		FileAuditUtil.moveFileToAuditedFolder(a_file,auditedFolder,backUpFolder, false);

		// Back up should occur
		assertEquals("existing a_auditable file moved, backup happens", 2, backUpFolder.list().length);

		Collection<Backup> backups = FileAuditUtil.getBackupsCollection(auditedFolder);

		assertEquals("FileAuditUtil does not populate the backup collection properly", 1, backups.size());

		// Create it again since it has been moved
		a_file.createNewFile();

		// Sleep 1 second to get a new backup folder
		Thread.sleep(1000);

		FileAuditUtil.moveFileToAuditedFolder(a_file,auditedFolder);

		backups = FileAuditUtil.getBackupsCollection(auditedFolder);

		assertEquals("FileAuditUtil does not populate the backup collection properly", 2, backups.size());


		// Create it again since it has been moved
		a_file.createNewFile();

		// Sleep 1 second to get a new backup folder
		Thread.sleep(1000);

		FileAuditUtil.moveFolderContentToAuditFolder(aFolder, auditedFolder);

		backups = FileAuditUtil.getBackupsCollection(auditedFolder);

		assertEquals("FileAuditUtil does not populate the backup collection properly", 3, backups.size());


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