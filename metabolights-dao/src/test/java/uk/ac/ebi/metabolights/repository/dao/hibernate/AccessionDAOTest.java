package uk.ac.ebi.metabolights.repository.dao.hibernate;


import uk.ac.ebi.metabolights.repository.dao.DAOFactory;

import static org.junit.Assert.*;

public class AccessionDAOTest extends DAOTest {

	public void testGetStableId() throws Exception {

		AccessionDAO accessionDAO = new AccessionDAO();

		String defaultPrefix = DAOFactory.getDefaultPrefix();

		// Should be the first one using the default value
		String stableID = accessionDAO.getStableId();

		assertNotNull("Stable id is not null", stableID);
		assertTrue("Using default prefix", stableID.startsWith(DAOFactory.getDefaultPrefix()));

		// Request another one
		String newStableID = accessionDAO.getStableId();
		assertFalse("Next stable id is different", stableID.equals(newStableID ));
		assertTrue("Using default prefix", newStableID.startsWith(DAOFactory.getDefaultPrefix()));

		// Set a new prefix
		String newPrefix = "NEWPREFIX";
		DAOFactory.setDefaultPrefix(newPrefix);

		// Request another one
		stableID = accessionDAO.getStableId();
		assertTrue("Using new prefix?", stableID.startsWith(newPrefix));

		newStableID = accessionDAO.getStableId(defaultPrefix);
		assertTrue("Using default prefix", newStableID.startsWith(defaultPrefix));
		assertTrue("Ends in 3", newStableID.endsWith("3"));



	}
}