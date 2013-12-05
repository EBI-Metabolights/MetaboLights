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
