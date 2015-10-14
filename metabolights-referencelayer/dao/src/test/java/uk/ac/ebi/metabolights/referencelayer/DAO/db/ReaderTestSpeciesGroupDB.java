/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 20/06/13 14:18
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
import uk.ac.ebi.metabolights.referencelayer.model.SpeciesGroup;

import java.sql.Connection;

public class ReaderTestSpeciesGroupDB extends TestCase{

	protected static final Logger LOGGER = LoggerFactory.getLogger(ReaderTestSpeciesGroupDB.class);

	private Connection con;
	protected SpeciesGroupDAO spgd;


	static String[] expected = new String[]{null,"SpeciesGroupTestUpdated"};


	@Override
	@BeforeClass
	protected void setUp() throws Exception {


		DatabaseInstance dbi = DatabaseInstance.getInstance("metabolightsDEV");
		con = dbi.getConnection();
		spgd = new SpeciesGroupDAO(con);

	}
	 @Override
	 @AfterClass
	protected void tearDown() throws Exception {
		if (spgd != null) spgd.close();
		if (con != null) con.close();

	}


	/*
	 */
	public void testSavingASpeciesGroup() throws Exception {

		SpeciesGroup spg = new SpeciesGroup();
		String[] newspg = new String[]{null,"New spg",};
		spg.setName(newspg[1]);

		spgd.save(spg);
		assertSpeciesGroup(spg, newspg);

		// at least check id is not 0
		assertTrue("Checking SpeciesGroup id is not 0", spg.getId() != 0 );

		// Update the id (it for now the expected id.
		expected[0] = Long.toString(spg.getId());
		spg.setName(expected[1]);

		spgd.save(spg);
		assertSpeciesGroup(spg, expected);


	}

    public static SpeciesGroup newRandomSpeciesGroup() {
        SpeciesGroup spg = new SpeciesGroup();
        spg.setName("Random spg" + System.currentTimeMillis() );
        return spg;

    }

	public void testFindASpeciesGroupById() throws Exception {

		SpeciesGroup spg = spgd.getById(Long.parseLong(expected[0]));
		assertSpeciesGroup(spg, expected);

        String change = "something different";

        // Test SpeciesGroupIdentityMap usage
        spg.setName(change);

        spg = spgd.getById(Long.parseLong(expected[0]));

        assertEquals("Testing identity map usage", change, spg.getName());


	}

    public void testFindASpeciesGroupByName() throws Exception {

        SpeciesGroup spg = spgd.getByName(expected[1]);

        assertSpeciesGroup(spg, expected);

    }


    public void testDeleteASpeciesGroup() throws Exception {

		SpeciesGroup spg = spgd.getById(Long.parseLong(expected[0]));

		spgd.delete(spg);

		spg = spgd.getById(Long.parseLong(expected[0]));

		assertTrue("Deleted SpeciesGroup must not be found" , spg == null);
	}


	private void assertSpeciesGroup(SpeciesGroup spg, String[] expectedvalues){

		assertNotNull(spg);
		// If the id is not null
		if (expectedvalues[0] != null) 	assertEquals("Checking " + expectedvalues[1] + " id" , Long.parseLong(expectedvalues[0]) , spg.getId().longValue());
		assertEquals("Checking SpeciesGroup name" , expectedvalues[1] , spg.getName());


	}
}