/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/21/13 5:12 PM
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
 * Date: 13/11/2013
 * Time: 11:27
 */
public class OLSParentSearcherTest {

	@Test
	public void testGetOLSParent() throws Exception {

		OLSParentSearcher olsParentSearcher = new OLSParentSearcher();

		Taxon result = olsParentSearcher.getParentFromTaxon(TaxonConverter.stringToTaxon("NEWT:1"));

		assertNull("Parent of NEWT root element should be null", result);

		result = olsParentSearcher.getParentFromTaxon(TaxonConverter.stringToTaxon("NEWT:9606"));

		// Parent of Homo sapiens should be homo
		assertEquals("Parent of Homo sapiens should be homo:", "NEWT:9605",result.getId());


		// Test it takes care of NCBI taxons
		result = olsParentSearcher.getParentFromTaxon(TaxonConverter.stringToTaxon("NCBI:9606"));

		// Parent of Homo sapiens should be homo

		assertEquals("Parent of Homo sapiens should be homo:", "NEWT:9605",result.getId());


	}

}
