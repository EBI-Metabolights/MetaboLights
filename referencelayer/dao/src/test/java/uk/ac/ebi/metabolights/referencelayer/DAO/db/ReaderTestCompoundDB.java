package uk.ac.ebi.metabolights.referencelayer.DAO.db;

import java.sql.Connection;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import uk.ac.ebi.biobabel.util.db.DatabaseInstance;
import uk.ac.ebi.biobabel.util.db.OracleDatabaseInstance;
import uk.ac.ebi.metabolights.referencelayer.domain.MetaboLightsCompound;

public class ReaderTestCompoundDB extends TestCase{
	
	protected static final Logger LOGGER = Logger.getLogger(ReaderTestCompoundDB.class);
	
	private Connection con;
	protected MetaboLightsCompoundDAO mcd;
	
	
	static String[] expected = new String[]{null,"ACC1U","Updated compound", "Updated compound description", "Inchi value updated", "ChebiId updated", "iupac names updated", "formula updated"};
	
	
	@Override
	@BeforeClass
	protected void setUp() throws Exception {
	
		// Set up a simple configuration that logs on the console.
	    BasicConfigurator.configure();
	    
		//DatabaseInstance dbi = DatabaseInstance.getInstance("metabolightsMYSQL");
        DatabaseInstance dbi = DatabaseInstance.getInstance("metabolightsDEV");
		con = dbi.getConnection();
		mcd = new MetaboLightsCompoundDAO(con);
					
		
		//LOGGER.info("Connected to metabolightsDEV");
		
	}
	 @Override
	 @AfterClass
	protected void tearDown() throws Exception {
		if (mcd != null) mcd.close();
		if (con != null) con.close();
		
	}

	 
	/*
	 */
	public void testSavingACompound() throws Exception {
		
		MetaboLightsCompound mc = new MetaboLightsCompound();
		String[] newCompound = new String[]{null,"ACC1","New compound", "New compound description", "New inchi value", "New chebiId", "New iupac name", "New formula"};
		mc.setAccession(newCompound[1]);
		mc.setName(newCompound[2]);
		mc.setDescription(newCompound[3]);
		mc.setInchi(newCompound[4]);
		mc.setChebiId(newCompound[5]);
        mc.setIupacNames(newCompound[6]);
        mc.setFormula(newCompound[7]);
		
		
		mcd.save(mc);
		assertMetabolite(mc ,newCompound);
		
		// at least check id is not 0
		assertTrue("Checking compound id is not 0", mc.getId() != 0 );
		
		// Update the id (it for now the expected id.
		expected[0] = Long.toString(mc.getId());
		mc.setAccession(expected[1]);
		mc.setName(expected[2]);
		mc.setDescription(expected[3]);
		mc.setInchi(expected[4]);
		mc.setChebiId(expected[5]);
        mc.setIupacNames(expected[6]);
        mc.setFormula(expected[7]);
		
		
		mcd.save(mc);
		assertMetabolite(mc, expected);
		
		
	}

	public void testFindACompoundByAccession() throws Exception {
		
		MetaboLightsCompound mc = mcd.findByCompoundAccession(expected[1]);
		assertMetabolite(mc, expected);
		
	}
	
	public void testFindACompoundByName() throws Exception {
		
		MetaboLightsCompound mc = mcd.findByCompoundName(expected[2]);
		assertMetabolite(mc, expected);
	}
	
	public void testFindACompoundById() throws Exception {
		
		MetaboLightsCompound mc = mcd.findByCompoundId(Long.parseLong(expected[0]));
		assertMetabolite(mc, expected);
	}

    public void testGetAllCompounds() throws Exception{

        // Get all the compounds
        Set<MetaboLightsCompound> mcs = mcd.getAllCompounds();

        // There must be at least one
        assertEquals("testing getAllCompounds, at least there must be one", true, mcs.size()>0);

    }

    public  void testExistCompound() throws Exception{

        boolean exists = mcd.doesCompoundExists(Long.parseLong(expected[0]));

        assertTrue("Compound existence", exists);

        // This one shouldn't exist
        exists = mcd.doesCompoundExists(new Long(-23));

        assertTrue("Compound in-existence", !exists);
    }

    public void testDeleteACompound() throws Exception {
		
		MetaboLightsCompound mc = mcd.findByCompoundId(Long.parseLong(expected[0]));
		
		mcd.delete(mc);
		
		mc = mcd.findByCompoundId(Long.parseLong(expected[0]));
		
		assertTrue("Deleted compound must not be found" , mc == null);
	}


	private void assertMetabolite(MetaboLightsCompound mc, String[] expectedvalues){
		
		assertNotNull(mc);
		// If the id is not null
		if (expectedvalues[0] != null) 	assertEquals("Checking " + expectedvalues[1] + " id" , Long.parseLong(expectedvalues[0]) , mc.getId());
		assertEquals("Checking " + expectedvalues[1] + " accesion" , expectedvalues[1] , mc.getAccession());
		assertEquals("Checking " + expectedvalues[1] + " name" , expectedvalues[2] , mc.getName());
		assertEquals("Checking " + expectedvalues[1] + " description" , expectedvalues[3] , mc.getDescription());
		assertEquals("Checking " + expectedvalues[1] + " inchi" , expectedvalues[4] , mc.getInchi());
		assertEquals("Checking " + expectedvalues[1] + " chebiId" , expectedvalues[5] , mc.getChebiId());
        assertEquals("Checking " + expectedvalues[1] + " iupac names" , expectedvalues[6] , mc.getIupacNames());
        assertEquals("Checking " + expectedvalues[1] + " formula" , expectedvalues[7] , mc.getFormula());
		
		
	}
	
	
}
