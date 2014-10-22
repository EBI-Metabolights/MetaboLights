package uk.ac.ebi.metabolights.referencelayer.DAO.db;

import junit.framework.TestCase;
import org.apache.log4j.BasicConfigurator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.biobabel.util.db.DatabaseInstance;
import uk.ac.ebi.metabolights.referencelayer.domain.SpeciesGroup;
import uk.ac.ebi.metabolights.referencelayer.domain.SpeciesMembers;

import java.sql.Connection;
import java.util.Collection;

/**
 * User: conesa
 * Date: 15/11/2013
 * Time: 16:29
 */
public class SpeciesMembersDAOTest extends TestCase{

	protected static final Logger LOGGER = LoggerFactory.getLogger(SpeciesMembersDAOTest.class);

	private Connection con;
	protected SpeciesMembersDAO spmd;
	private static SpeciesGroup group;

	static String[] newSpeciesMember = new String[]{null,"new taxon","new taxon desc"};
	static String[] updatedSpeciesMemeber = new String[]{null,"taxon updated","taxon desc updated"};


	@Override
	@BeforeClass
	protected void setUp() throws Exception {

		// Set up a simple configuration that logs on the console.
		BasicConfigurator.configure();

		DatabaseInstance dbi = DatabaseInstance.getInstance("metabolightsDEV");
		con = dbi.getConnection();
		spmd = new SpeciesMembersDAO(con);


	}
	@Override
	@AfterClass
	protected void tearDown() throws Exception {
		if (spmd != null) spmd.close();
		if (con != null) con.close();

	}

	@Test
	public void testSave() throws Exception {

		SpeciesMembers spm = getNewSpeciesMember();

		spmd.save(spm);
		assertSpeciesMember(spm,newSpeciesMember);
		// at least check id is not 0
		assertTrue("Checking SpeciesMember id is not 0", spm.getId() != 0);


		// Update the id (it for now the expected id.
		updatedSpeciesMemeber[0] = Long.toString(spm.getId());
		spm.setTaxon(updatedSpeciesMemeber[1]);
		spm.setTaxonDesc(updatedSpeciesMemeber[2]);

		spmd.save(spm);

		assertSpeciesMember(spm, updatedSpeciesMemeber);

	}

	private void assertSpeciesMember(SpeciesMembers spm, String[] expectedvalues) {

		assertNotNull(spm);

		// If the id is not null
		if (expectedvalues[0] != null) 	assertEquals("Checking " + expectedvalues[1] + " id" , Long.parseLong(expectedvalues[0]) , spm.getId());
		assertEquals("Checking " + expectedvalues[1] + " Taxon" , expectedvalues[1] , spm.getTaxon());
		assertEquals("Checking " + expectedvalues[1] + " Taxon description" , expectedvalues[2] , spm.getTaxonDesc());

		// If the id is not null (compound is saved)...
		if (spm.getId() != 0){
			assertTrue("Is Species group saved?", spm.getSpeciesGroup().getId() != 0);

		}


	}

	private SpeciesMembers getNewSpeciesMember() {

		SpeciesMembers spm = new SpeciesMembers();

		spm.setTaxon(newSpeciesMember[1]);
		spm.setTaxonDesc(newSpeciesMember[2]);

		group = ReaderTestSpeciesGroupDB.newRandomSpeciesGroup();
		spm.setSpeciesGroup(group);

		return spm;
	}

	@Test
	public void testGetById() throws Exception {

		SpeciesMembers spm = spmd.getById(Long.parseLong(updatedSpeciesMemeber[0]));
		assertSpeciesMember(spm,updatedSpeciesMemeber);

		String change = "something different";

		spm.setTaxon(change);

		spmd.getById(Long.valueOf(updatedSpeciesMemeber[0]));

		assertEquals("Testing identity map usage", change, spm.getTaxon());

	}

	@Test
	public void testGetByTaxon() throws Exception {


		SpeciesMembers spm = spmd.getByTaxon(updatedSpeciesMemeber[1]);
		assertSpeciesMember(spm,updatedSpeciesMemeber);

	}

	@Test
	public void testGetAllBySpeciesGroup() throws Exception {


		Collection<SpeciesMembers> spm = spmd.getAllBySpeciesGroup(group.getId());

		assertEquals("GetByGroupId must return 1 item", 1, spm.size());

		assertSpeciesMember(spm.iterator().next(),updatedSpeciesMemeber);



	}

	@Test
	public void testGetAll() throws Exception {


		Collection<SpeciesMembers> spm = spmd.getAll();

		assertTrue("GetAll must return at least 1 item", spm.size()>0);


	}


	@Test
	public void testDelete() throws Exception {


		SpeciesMembers spm = spmd.getById(Long.parseLong(updatedSpeciesMemeber[0]));

		spmd.delete(spm);

		SpeciesGroupDAO spgd = new SpeciesGroupDAO(spmd.con);
		spgd.delete(spm.getSpeciesGroup());

		spm = spmd.getById(Long.parseLong(updatedSpeciesMemeber[0]));

		assertTrue("Deleted Species member must not be found", spm == null);


	}
}
