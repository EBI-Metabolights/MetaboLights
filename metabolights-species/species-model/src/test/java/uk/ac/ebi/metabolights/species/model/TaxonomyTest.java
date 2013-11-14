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
