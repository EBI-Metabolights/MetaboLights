/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/5/12 10:35 AM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.metabolightsuploader;

import static org.junit.Assert.*;

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
