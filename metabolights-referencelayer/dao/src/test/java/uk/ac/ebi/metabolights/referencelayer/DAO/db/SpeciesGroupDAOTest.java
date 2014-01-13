package uk.ac.ebi.metabolights.referencelayer.DAO.db;

import junit.framework.TestCase;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ebi.biobabel.util.db.DatabaseInstance;
import uk.ac.ebi.metabolights.referencelayer.domain.SpeciesGroup;

import java.sql.Connection;
import java.util.Collection;

/**
 * User: conesa
 * Date: 15/11/2013
 * Time: 16:29
 */
public class SpeciesGroupDAOTest extends TestCase{

	protected static final Logger LOGGER = Logger.getLogger(SpeciesGroupDAOTest.class);

	private Connection con;
	protected SpeciesGroupDAO spgd;



	@Override
	@BeforeClass
	protected void setUp() throws Exception {

		// Set up a simple configuration that logs on the console.
		BasicConfigurator.configure();

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

	@Test
	public void testGetAll() throws Exception {


		Collection<SpeciesGroup> spm = spgd.getAll();

		assertTrue("GetAll must return at least 1 item", spm.size()>0);


	}
}
