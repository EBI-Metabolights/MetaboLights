/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/26/13 4:34 PM
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
import static org.junit.Assert.*;
import uk.ac.ebi.metabolights.species.model.Taxon;

/**
 * User: conesa
 * Date: 26/11/2013
 * Time: 10:12
 */
public class WoRMSParentSearcherTest {
	@Test
	public void testGetParentFromTaxon() throws Exception {

		WoRMSPArentSearcher woRMSParentSearcher = new WoRMSPArentSearcher();

		Taxon pontinus = new Taxon(woRMSParentSearcher.WoRMS_PREFIX + ":" + 127240, "Pontinus kuhlii (Bowdich, 1825)", "","");

		Taxon parent = woRMSParentSearcher.getParentFromTaxon(pontinus);

		assertEquals("Testing parent id of Pontinus kuhlii", "126170" , parent.getRecordIdentifier());

	}

	@Test
	public void testIsThisTaxonYours() throws Exception {

		WoRMSPArentSearcher woRMSParentSearcher = new WoRMSPArentSearcher();

		Taxon orphan = new Taxon(woRMSParentSearcher.WoRMS_PREFIX + ":" + 127240, "Pontinus kuhlii (Bowdich, 1825)", "","");

		assertTrue("Worms taxon must be accepted", woRMSParentSearcher.isThisTaxonYours(orphan));

		orphan.setPrefix("hsdWORMS");

		assertFalse("weird taxon must NOT be accepted", woRMSParentSearcher.isThisTaxonYours(orphan));

		orphan.setPrefix("NCBI");

		assertFalse("NCBI taxon must NOT be accepted", woRMSParentSearcher.isThisTaxonYours(orphan));

	}

	@Test
	public void testGetParentFromTaxonRoot() throws Exception {

		WoRMSPArentSearcher woRMSParentSearcher = new WoRMSPArentSearcher();

		Taxon biota = new Taxon(woRMSParentSearcher.WoRMS_PREFIX + ":" + 1, "Biota", "","");

		Taxon parent = woRMSParentSearcher.getParentFromTaxon(biota);

		assertNull("Testing parent id for Biota is null", parent);

	}

}
