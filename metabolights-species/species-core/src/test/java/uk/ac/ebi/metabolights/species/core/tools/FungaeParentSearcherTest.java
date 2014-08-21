/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 12/5/13 10:32 AM
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

package uk.ac.ebi.metabolights.species.core.tools;

import org.junit.Test;
import uk.ac.ebi.metabolights.species.model.Taxon;
import static org.junit.Assert.*;

/**
 * User: conesa
 * Date: 04/12/2013
 * Time: 15:04
 */
public class FungaeParentSearcherTest {
	@Test
	public void testGetParentFromTaxon() throws Exception {

		FungaeParentSearcher ps = new FungaeParentSearcher();

		Taxon taxon = new Taxon ("MB:asdf","","","");

		Taxon parent = ps.getParentFromTaxon(taxon);

		assertEquals("Test if Fungae parent searcher returns the correct parent", ps.FUNGAE_KINGDOM, parent.getId());



	}

	@Test
	public void testIsThisTaxonYours() throws Exception {

		FungaeParentSearcher ps = new FungaeParentSearcher();

		Taxon taxon = new Taxon ("MB:asdf","","","");

		assertTrue("Test if Fungae parent searcher accepts MB taxons",ps.isThisTaxonYours(taxon));

		taxon.setId("IF:asdf");

		assertTrue("Test if Fungae parent searcher accepts IF taxons", ps.isThisTaxonYours(taxon));

		taxon.setId("NEWT:asdf");

		assertFalse("Test if Fungae parent searcher rejects NEWT taxons", ps.isThisTaxonYours(taxon));




	}
}
