/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/12/13 5:44 PM
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

package uk.ac.ebi.metabolights.species.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * User: conesa
 * Date: 12/11/2013
 * Time: 17:32
 */
public class TaxonomyTest {


	@Test
	public void testIsThisYourTaxon() throws Exception {

		Taxonomy taxonomy = new Taxonomy("desc", "ID", "VERSION");


		assertEquals("Default taxonomy pattern test", "ID:\\d+", taxonomy.getMatchingPattern());


		// Test taxon recogonition...
		Taxon ok = new Taxon("ID:1","","","");

		assertTrue("Taxon " + ok.getId() + " should be recognized" , taxonomy.isThisYourTaxon(ok));

		ok.setId("ID:43534523542324234");
		assertTrue("Taxon " + ok.getId() + " should be recognized" , taxonomy.isThisYourTaxon(ok));

		Taxon wrong = new Taxon("ID1","","","");

		assertFalse("Taxon " + wrong.getId() + " shouldn't be recognized" , taxonomy.isThisYourTaxon(wrong));

		wrong.setId("sadhasID");
		assertFalse("Taxon " + wrong.getId() + " shouldn't be recognized" , taxonomy.isThisYourTaxon(wrong));

		wrong.setId("ID:");
		assertFalse("Taxon " + wrong.getId() + " shouldn't be recognized" , taxonomy.isThisYourTaxon(wrong));

		wrong.setId("ACID:1234");
		assertFalse("Taxon " + wrong.getId() + " shouldn't be recognized" , taxonomy.isThisYourTaxon(wrong));

	}
}
