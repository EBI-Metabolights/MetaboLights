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

package uk.ac.ebi.metabolights.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import uk.ac.ebi.metabolights.utils.StringUtils;
public class StringUtilsTest {

	@Test
	public void testTruncate() {
	
		final String TEXT = "0123456789009876543210";
		final String EXPECTED_LEFT_3 = "3456789009876543210";
		final String EXPECTED_RIGHT_5 = "01234567890098765";
		final String EXPECTED_RIGHT_1 = "012345678900987654321";
		
		String result;
		
		//Truncate left 3
		result = StringUtils.truncate(TEXT, 3, true);
		assertEquals(EXPECTED_LEFT_3, result);
		
		//Truncate right 5
		result = StringUtils.truncate(TEXT, 5, false);
		assertEquals(EXPECTED_RIGHT_5, result);
		
		//Truncate simple (one character from the right)
		result = StringUtils.truncate(TEXT);
		assertEquals(EXPECTED_RIGHT_1, result);
		
		//Truncate 5 (has to be from the right)
		result = StringUtils.truncate(TEXT, 5);
		assertEquals(EXPECTED_RIGHT_5, result);
		
		
				

	}
	@Test
	public void testTruncateByChar(){
		final String TEXT = "012.3456789876.543210";
		final String EXPECTED_LEFT_KEEPLEFT = "012";
		final String EXPECTED_LEFT_KEEPRIGHT = "3456789876.543210";
		final String EXPECTED_RIGHT_KEEPLEFT = "012.3456789876";
		final String EXPECTED_RIGHT_KEEPRIGHT = "543210";
		final String find = ".";
		
		String result;
		
		//Find from LEFT, KEEP LEFT
		result = StringUtils.truncate(TEXT,find,true, false);
		assertEquals(EXPECTED_LEFT_KEEPLEFT, result);
		
		//Find from LEFT, KEEP RIGHT
		result = StringUtils.truncate(TEXT,find,true, true);
		assertEquals(EXPECTED_LEFT_KEEPRIGHT, result);
		
		//Find from RIGHT, KEEP LEFT
		result = StringUtils.truncate(TEXT,find,false, false);
		assertEquals(EXPECTED_RIGHT_KEEPLEFT, result);
		
		//Find from RIGHT, KEEP RIGHT
		result = StringUtils.truncate(TEXT,find,false, true);
		assertEquals(EXPECTED_RIGHT_KEEPRIGHT, result);
		
	}
	@Test
	public void testTruncateEmptyText(){
		String result;
		
		//Test truncation right of empty string
		result = StringUtils.truncate("");
		assertEquals("", result);
		
		//Test truncation left
		result = StringUtils.truncate("",2,true);
		assertEquals("", result);

		//Test also truncate the whole string
		result = StringUtils.truncate("A");
		assertEquals("", result);
		
		//Test truncation of the whole string from the Left
		result = StringUtils.truncate("ABC", 3, true);
		assertEquals("", result);
		
	}
	@Test 
	public void testTruncateStringNotFound(){
		String actual;
		
		//Test to find a string that is not present
		actual = StringUtils.truncate("1234567890", "a", true, false);
		assertEquals("", actual);
		
		
	}
	@Test
	public void testReplaceSimple(){
		final String FIND = "tomato";
		final String REPLACE = "aligator";
		final String BEFORE = "See you later ";
		final String AFTER = " for a while crocodrile";
		String text = BEFORE + FIND + AFTER;
		String expected = BEFORE + REPLACE + AFTER;
		
		//Call replace
		text = StringUtils.replace(text, FIND, REPLACE);
		
		//Test the result
		assertEquals(expected, text);
		
	}
	@Test
	public void testReplaceRegExp(){
		final String FIND = "\\((\\w+)\\)";
		final String REPLACE = "<$1>";
		String text = "a (b c) d (ef) g";
		String expected = "a (b c) d <ef> g";
		
		//Call replace
		text = StringUtils.replace(text, FIND, REPLACE);
		
		//Test the result
		assertEquals(expected, text);
		
	}
	
	












}
