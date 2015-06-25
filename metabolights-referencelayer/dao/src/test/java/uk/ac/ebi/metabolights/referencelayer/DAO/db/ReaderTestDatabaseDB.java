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
import uk.ac.ebi.metabolights.referencelayer.model.Database;

import java.sql.Connection;

public class ReaderTestDatabaseDB extends TestCase{

	protected static final Logger LOGGER = LoggerFactory.getLogger(ReaderTestDatabaseDB.class);

	private Connection con;
	protected DatabaseDAO dbd;


	static String[] expected = new String[]{null,"DatabaseTestUpdated"};


	@Override
	@BeforeClass
	protected void setUp() throws Exception {

//		// Set up a simple configuration that logs on the console.
//	    BasicConfigurator.configure();

		DatabaseInstance dbi = DatabaseInstance.getInstance("metabolightsDEV");
		con = dbi.getConnection();
		dbd = new DatabaseDAO(con);

	}
	 @Override
	 @AfterClass
	protected void tearDown() throws Exception {
		if (dbd != null) dbd.close();
		if (con != null) con.close();

	}


	/*
	 */
	public void testSavingADatabase() throws Exception {

		Database db = new Database();
		String[] newDb = new String[]{null,"New Db",};
		db.setName(newDb[1]);

		dbd.save(db);
		assertDatabase(db ,newDb);

		// at least check id is not 0
		assertTrue("Checking database id is not 0", db.getId() != 0 );

		// Update the id (it for now the expected id.
		expected[0] = Long.toString(db.getId());
		db.setName(expected[1]);

		dbd.save(db);
		assertDatabase(db, expected);


	}

    public static Database newRandomDatabase() {
        Database db = new Database();
        db.setName("Random DB" + System.currentTimeMillis() );
        return db;

    }

	public void testFindADatabaseById() throws Exception {

		Database db = dbd.findByDatabaseId(Long.parseLong(expected[0]));
		assertDatabase(db, expected);

        String change = "something different";

        // Test DatabaseIdentityMap usage
        db.setName(change);

        db = dbd.findByDatabaseId(Long.parseLong(expected[0]));

        assertEquals("Testing identity map usage", change, db.getName());


	}

    public void testFindADatabaseByName() throws Exception {

        Database db = dbd.findByDatabaseName(expected[1]);

        assertDatabase(db, expected);

    }


    public void testDeleteADatabase() throws Exception {

		Database db = dbd.findByDatabaseId(Long.parseLong(expected[0]));

		dbd.delete(db);

		db = dbd.findByDatabaseId(Long.parseLong(expected[0]));

		assertTrue("Deleted database must not be found" , db == null);
	}


	private void assertDatabase(Database db, String[] expectedvalues){

		assertNotNull(db);
		// If the id is not null
		if (expectedvalues[0] != null) 	assertEquals("Checking " + expectedvalues[1] + " id" , Long.parseLong(expectedvalues[0]) , db.getId().longValue());
		assertEquals("Checking database name" , expectedvalues[1] , db.getName());


	}


}