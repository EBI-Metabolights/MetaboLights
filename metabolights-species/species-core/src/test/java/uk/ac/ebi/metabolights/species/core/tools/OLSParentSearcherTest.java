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
