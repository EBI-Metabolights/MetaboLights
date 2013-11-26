package uk.ac.ebi.metabolights.species.core.tools;

import org.junit.Test;
import uk.ac.ebi.metabolights.species.model.Taxon;
import static org.junit.Assert.*;

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



		// Try human
		Taxon human = TaxonConverter.stringToTaxon("NEWT:9606");

		result = grouper.getGroupFromTaxon(human);

		assertEquals("Group for NEWT:9606 should be Animals NEWT:33208", animals.getId() , result.getId());


		// Try human (NCBI version)
		human = TaxonConverter.stringToTaxon("NCBI:9606");

		result = grouper.getGroupFromTaxon(human);

		assertEquals("Group for NCBI:9606 should be Animals NEWT:33208", animals.getId() , result.getId());




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
	public void testGetGroupFromTaxon2() throws Exception {

		Grouper grouper = new Grouper();

		IParentSearcher woRMSSearcher = new WoRMSPArentSearcher();
		grouper.getParentSearchers().add(woRMSSearcher);

		Taxon result = grouper.getGroupFromTaxon(TaxonConverter.stringToTaxon("NEWT:1"));

		assertNotNull("Group for NEWT root element should be itelf", result);

		// Set a group for animals... (WoRMS and NCBI)
		Taxon animalsNEWT = TaxonConverter.stringToTaxon("NEWT:33208");
		Taxon animalsWoRMS = TaxonConverter.stringToTaxon("WORMS:2");

		grouper.getTaxonGroups().add(animalsNEWT);
		grouper.getTaxonGroups().add(animalsWoRMS);

		// Try human
		Taxon humanNEWT = TaxonConverter.stringToTaxon("NEWT:9606");
		Taxon pontinus = new Taxon(WoRMSPArentSearcher.WoRMS_PREFIX + ":" + 127240, "Pontinus kuhlii (Bowdich, 1825)", "","");

		result = grouper.getGroupFromTaxon(humanNEWT);

		assertEquals("Group for NEWT:9606 should be Animals NEWT:33208", animalsNEWT.getId() , result.getId());


		result = grouper.getGroupFromTaxon(pontinus);

		assertEquals("Group for WORMS:127240 should be Animals WORMS:2", animalsWoRMS.getId() , result.getId());


	}


}
