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

package uk.ac.ebi.metabolights.repository.accessionmanager;

import static org.junit.Assert.*;

import org.junit.Test;
import uk.ac.ebi.metabolights.model.StableId;

public class StableIdTest {

	final Integer ID=3;
	final String PREFIX="HOLA";
	
	@Test
	public void testId() {
		StableId stableId = new StableId();
		
		stableId.setSeq(ID);
		assertEquals(ID, stableId.getSeq());
		
	}

	@Test
	public void testPrefix() {
		StableId stableId = new StableId();
		
		stableId.setPrefix(PREFIX);
		assertEquals(PREFIX, stableId.getPrefix());
	}
}
