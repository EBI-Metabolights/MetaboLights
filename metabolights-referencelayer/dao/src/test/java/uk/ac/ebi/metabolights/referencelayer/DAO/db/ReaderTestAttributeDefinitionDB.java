/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 08/07/13 08:56
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.DAO.db;

import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.biobabel.util.db.DatabaseInstance;
import uk.ac.ebi.metabolights.referencelayer.model.AttributeDefinition;

import java.sql.Connection;

public class ReaderTestAttributeDefinitionDB extends TestCase{

	protected static final Logger LOGGER = LoggerFactory.getLogger(ReaderTestAttributeDefinitionDB.class);

	private Connection con;
	protected AttributeDefinitionDAO add;


	static String[] expected = new String[]{null,"AttributeDefinitionTest updated", "Ad description updated"};


	@Override
	@BeforeClass
	protected void setUp() throws Exception {

		// Set up a simple configuration that logs on the console.
	    //BasicConfigurator.configure();

		DatabaseInstance dbi = DatabaseInstance.getInstance("metabolightsDEV");
		con = dbi.getConnection();
		add = new AttributeDefinitionDAO(con);

	}
	 @Override
	 @AfterClass
	protected void tearDown() throws Exception {
		if (add != null) add.close();
		if (con != null) con.close();

	}


	/*
	 */
	public void testSavingAnAttributeDefinition() throws Exception {

		AttributeDefinition ad = new AttributeDefinition();
		String[] newAd = new String[]{null,"New Ad","New Ad description"};
		ad.setName(newAd[1]);
        ad.setDescription(newAd[2]);

		add.save(ad);
		assertAttributeDefinition(ad, newAd);

		// at least check id is not 0
		assertTrue("Checking attributeDefinition id is not 0", ad.getId() != 0 );

		// Update the id (it for now the expected id.
		expected[0] = Long.toString(ad.getId());
		ad.setName(expected[1]);
        ad.setDescription(expected[2]);

		add.save(ad);
		assertAttributeDefinition(ad, expected);


	}

    public static AttributeDefinition newRandomAttributeDefinition() {
        AttributeDefinition ad = new AttributeDefinition();
        ad.setName("Random Ad");
        ad.setDescription("Random Ad Description");
        return ad;

    }

	public void testFindAnAttributeDefinitionById() throws Exception {

		AttributeDefinition ad = add.findByAttributeDefinitionId(Long.parseLong(expected[0]));
		assertAttributeDefinition(ad, expected);

        String change = "something different";

        // Test IdentityMap usage
        ad.setName(change);

        ad = add.findByAttributeDefinitionId(Long.parseLong(expected[0]));

        assertEquals("Testing identity map usage", change, ad.getName());


	}

    public void testFindAnAttributeDefinitionByName() throws Exception {

        AttributeDefinition ad = add.findByAttributeDefinitionName(expected[1]);
        assertAttributeDefinition(ad, expected);

    }

    public void testDeleteAnAttributeDefinition() throws Exception {

		AttributeDefinition ad = add.findByAttributeDefinitionId(Long.parseLong(expected[0]));

		add.delete(ad);

		ad = add.findByAttributeDefinitionId(Long.parseLong(expected[0]));

		assertTrue("Deleted attributeDefinition must not be found", ad == null);
	}


	private void assertAttributeDefinition(AttributeDefinition ad, String[] expectedvalues){

		assertNotNull(ad);
		// If the id is not null
		if (expectedvalues[0] != null) 	assertEquals("Checking " + expectedvalues[1] + " id", Long.parseLong(expectedvalues[0]), ad.getId());
		assertEquals("Checking attibuteDfinition name" , expectedvalues[1] , ad.getName());
        assertEquals("Checking attibuteDfinition description" , expectedvalues[2] , ad.getDescription());


	}


}