package uk.ac.ebi.metabolights.webservice.queue;

import junit.framework.TestCase;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class SubmissionItemTest extends TestCase {

	public void testGetSubmissionType() throws Exception {


		// This is a new submission
		SubmissionItem si = new SubmissionItem(getSubmissionFile("email", "", "20150101", "newStudy.zip"));
		assertEquals("Submission type: CREATE", SubmissionItem.SubissionType.CREATE, si.getSubmissionType());

		// This is an update
		si = new SubmissionItem(getSubmissionFile("email", "acc", "20150101", "updatedStudy.zip"));
		assertEquals("Submission type: UPDATE", SubmissionItem.SubissionType.UPDATE, si.getSubmissionType());

		// This is a Public release date change
		si = new SubmissionItem(getSubmissionFile("email", "acc", "20150101", SubmissionItem.FILE_NAME_FOR_PRD_UPDATES));
		assertEquals("Submission type: PUBLIC RELEASE DATE CHANGE", SubmissionItem.SubissionType.CHANGE_PUBLIC_RELEASE_DATE, si.getSubmissionType());

		// This is a DELETION
		si = new SubmissionItem(getSubmissionFile("email", "acc", "20150101", SubmissionItem.FILE_NAME_FOR_DELETIONS));
		assertEquals("Submission type: CREATE", SubmissionItem.SubissionType.UPDATE, si.getSubmissionType());


	}

	private File getSubmissionFile(String email, String accession, String publicReleaseDate, String fileName) {

		return new File(email +SubmissionItem.FILE_NAME_SEP+ accession +SubmissionItem.FILE_NAME_SEP+ publicReleaseDate +SubmissionItem.FILE_NAME_SEP + fileName);
	}

	public void testPublicReleaseDateConversion() throws Exception {

		// Before british summer time.
		SubmissionItem si = new SubmissionItem(getSubmissionFile("email", "", "20150101", "newStudy.zip"));
		assertEquals("20150101 does not convert to same value Date", 1, getDayOfMonth(si.getPublicReleaseDate()));

		// After British summer time
		si = new SubmissionItem(getSubmissionFile("email", "", "20150701", "newStudy.zip"));
		assertEquals("20150701 does not convert to same value Date", 1, getDayOfMonth(si.getPublicReleaseDate()));

	}

	public static int getDayOfMonth(Date aDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(aDate);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
}