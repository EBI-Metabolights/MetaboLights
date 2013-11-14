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
		Taxon animals = TaxonConverter.stringToTaxon("NEWT:33208");

		grouper.getTaxonGroups().add(animals);

		Taxon human = TaxonConverter.stringToTaxon("NEWT:9606");

		result = grouper.getGroupFromTaxon(human);

		assertEquals("Group for NEWT:9606 should be Animals NEWT:33208", animals.getId() , result.getId());


		// Lets try now with a green plants...it should return the root node
		Taxon greenPlants = TaxonConverter.stringToTaxon("NEWT:33090");

		result =  grouper.getGroupFromTaxon(greenPlants);

		assertEquals("Group for green plants should be root NEWT:1", "NEWT:1" , result.getId());


		// Add now the green plants as a group...
		grouper.getTaxonGroups().add(greenPlants);

		// Lets try now with a green plants...it should return itself
		result = grouper.getGroupFromTaxon(greenPlants);

		assertEquals("Group for green plants should be itself NEWT:33090", greenPlants.getId() , result.getId());


		// Try a plant...Arabidopsis thaliana (thale cress)
		result = grouper.getGroupFromTaxon(TaxonConverter.stringToTaxon("NEWT:3702"));

		assertEquals("Group for arabisopsis should be Green plants", greenPlants.getId() , result.getId());

		// Human should still return animals
		result = grouper.getGroupFromTaxon(human);

		assertEquals("Group for NEWT:9606 should be Animals NEWT:33208", animals.getId() , result.getId());







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
