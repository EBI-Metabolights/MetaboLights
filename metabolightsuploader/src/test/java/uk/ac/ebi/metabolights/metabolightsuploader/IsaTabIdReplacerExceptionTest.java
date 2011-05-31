package uk.ac.ebi.metabolights.metabolightsuploader;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import uk.ac.ebi.metabolights.metabolightsuploader.IsaTabIdReplacerException;

public class IsaTabIdReplacerExceptionTest {

	@Test
	public void testIsaTabIdReplacerException() {
		final String MSG = "This is the message";
		final String FILEPATH = "this is the file path";
		final String FILEFORMAT = "This is the file format";
		String [] MSGS = {FILEFORMAT,FILEPATH};
		
		//Test the message
		IsaTabIdReplacerException e = new IsaTabIdReplacerException(MSG, MSGS);
		assertEquals(MSG + StringUtils.join(MSGS), e.getMessage());
		
		//Test the file format
		assertEquals(FILEFORMAT, e.getFileFormat());
		
		//Test the path
		assertEquals(FILEPATH, e.getFilePath());
		
	}

}
