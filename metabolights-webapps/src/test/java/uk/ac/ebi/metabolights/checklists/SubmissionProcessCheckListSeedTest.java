package uk.ac.ebi.metabolights.checklists;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.ebi.metabolights.checklists.SubmissionProcessCheckListSeed;

public class SubmissionProcessCheckListSeedTest {

	@Test
	public void testGetKey() {
		assertEquals("1", SubmissionProcessCheckListSeed.FILEUPLOAD.getKey());
		
	}

	@Test
	public void testGetTitle() {
		assertEquals("File upload", SubmissionProcessCheckListSeed.FILEUPLOAD.getTitle());
		
	}

}
