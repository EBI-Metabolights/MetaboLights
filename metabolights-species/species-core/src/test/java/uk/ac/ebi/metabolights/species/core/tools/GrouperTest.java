package uk.ac.ebi.metabolights.species.core.tools;

import org.junit.Test;
import uk.ac.ebi.metabolights.species.model.Taxon;
import static org.junit.Assert.*;

import java.util.HashMap;

/**
 * User: conesa
 * Date: 13/11/2013
 * Time: 11:27
 */
public class GrouperTest {
	@Test
	public void testGetGroupFromTaxon() throws Exception {

		Grouper grouper = new Grouper();

		Taxon result = grouper.getGroupFromTaxon(TaxonConverter.stringToTaxon("NEWT:1"));

		assertNotNull("Group for NEWT root element should be itelf", result);


		// Set a group for animals...
		grouper.setTaxonGroups(TaxonConverter.stringToTaxonList("NEWT:33208"));
		result = grouper.getGroupFromTaxon(TaxonConverter.stringToTaxon("NEWT:9606"));

		assertEquals("Group for NEWT:9606 should be Animals NEWT:33208", "NEWT:33208" , result.getId());




	}

	@Test
	public void testGetOLSParent() throws Exception {

		Grouper grouper = new Grouper();

		Taxon result = grouper.getOLSParent(TaxonConverter.stringToTaxon("NEWT:1"));

		assertNull("Parent of NEWT root element should be null", result);


		result = grouper.getOLSParent(TaxonConverter.stringToTaxon("NEWT:9606"));

		// Parent of Homo sapiens should be homo

		assertEquals("Parent of Homo sapiens should be homo:", "NEWT:9605",result.getId());



	}

}
