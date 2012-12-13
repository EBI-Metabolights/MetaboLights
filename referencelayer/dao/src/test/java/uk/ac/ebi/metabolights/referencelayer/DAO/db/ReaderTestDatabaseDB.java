package uk.ac.ebi.metabolights.referencelayer.DAO.db;

import java.sql.Connection;

import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import uk.ac.ebi.biobabel.util.db.DatabaseInstance;
import uk.ac.ebi.metabolights.referencelayer.domain.Database;

public class ReaderTestDatabaseDB extends TestCase{
	
	protected static final Logger LOGGER = Logger.getLogger(ReaderTestDatabaseDB.class);
	
	private Connection con;
	protected DatabaseDAO dbd;
	
	
	static String[] expected = new String[]{null,"DatabaseTestUpdated"};
	
	
	@Override
	@BeforeClass
	protected void setUp() throws Exception {
	
		// Set up a simple configuration that logs on the console.
	    BasicConfigurator.configure();
	    
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

	public void testFindADatabaseById() throws Exception {
		
		Database db = dbd.findByDatabaseId(Long.parseLong(expected[0]));
		assertDatabase(db, expected);
	}	
	
	public void testDeleteADatabase() throws Exception {
		
		Database db = dbd.findByDatabaseId(Long.parseLong(expected[0]));
		
		dbd.delete(db);
		
		db = dbd.findByDatabaseId(Long.parseLong(expected[0]));
		
		assertTrue("Deleted compound must not be found" , db == null);
	}	

	
	private void assertDatabase(Database db, String[] expectedvalues){
		
		assertNotNull(db);
		// If the id is not null
		if (expectedvalues[0] != null) 	assertEquals("Checking " + expectedvalues[1] + " id" , Long.parseLong(expectedvalues[0]) , db.getId());
		assertEquals("Checking database name" , expectedvalues[1] , db.getName());
		
		
	}
	
	
}
