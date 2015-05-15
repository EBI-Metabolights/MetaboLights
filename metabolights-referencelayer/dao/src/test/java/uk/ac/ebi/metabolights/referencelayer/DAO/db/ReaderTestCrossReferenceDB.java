/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 17/05/13 09:38
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
import uk.ac.ebi.metabolights.referencelayer.domain.CrossReference;
import uk.ac.ebi.metabolights.referencelayer.domain.Database;

import java.sql.Connection;

public class ReaderTestCrossReferenceDB extends TestCase{

	protected static final Logger LOGGER = LoggerFactory.getLogger(ReaderTestCrossReferenceDB.class);

	private Connection con;
	protected CrossReferenceDAO crd;


	static String[] expected = new String[]{null,"XRefACCUpdated"};


	@Override
	@BeforeClass
	protected void setUp() throws Exception {


		DatabaseInstance dbi = DatabaseInstance.getInstance("metabolightsDEV");
		con = dbi.getConnection();
		crd = new CrossReferenceDAO(con);

	}
	 @Override
	 @AfterClass
	protected void tearDown() throws Exception {
		if (crd != null) crd.close();
		if (con != null) con.close();

	}


	/*
	 */
	public void testSavingACrossReference() throws Exception {

        Database db = ReaderTestDatabaseDB.newRandomDatabase();

		CrossReference crossReference = new CrossReference();
        crossReference.setDb(db);

        String[] xref = new String[]{null,"new Xref"};
		crossReference.setAccession(xref[1]);

		crd.save(crossReference);
		assertCrossReference(crossReference, xref);

		// at least check id is not 0
		assertTrue("Checking database id is not 0", crossReference.getId() != 0 );

		// Update the id (it for now the expected id.
		expected[0] = Long.toString(crossReference.getId());
		crossReference.setAccession(expected[1]);

		crd.save(crossReference);
		assertCrossReference(crossReference, expected);


	}

    public static CrossReference newRandomCrossReference() {

        CrossReference cr = new CrossReference();
        cr.setAccession("Random ACC");
        cr.setDb(ReaderTestDatabaseDB.newRandomDatabase());

        return cr;

    }

	public void testFindACrossReferenceById() throws Exception {

		CrossReference cr = crd.findByCrossReferenceId(Long.parseLong(expected[0]));
		assertCrossReference(cr, expected);

        String change = "something different";

        // Test DatabaseIdentityMap usage
        cr.setAccession(change);

        cr = crd.findByCrossReferenceId(Long.parseLong(expected[0]));

        assertEquals("Testing identity map usage", change, cr.getAccession());


	}

	public void testDeleteACrossReference() throws Exception {

		CrossReference cr = crd.findByCrossReferenceId(Long.parseLong(expected[0]));

		crd.delete(cr);

        DatabaseDAO dbd = new DatabaseDAO(crd.con);
        dbd.delete(cr.getDb());

		cr = crd.findByCrossReferenceId(Long.parseLong(expected[0]));


		assertTrue("Deleted CrossReference must not be found", cr == null);



    }


	private void assertCrossReference(CrossReference cr, String[] expectedvalues){

		assertNotNull(cr);
		// If the id is not null
		if (expectedvalues[0] != null) 	assertEquals("Checking " + expectedvalues[1] + " id", Long.parseLong(expectedvalues[0]), cr.getId());
		assertEquals("Checking CrossRefererence accession" , expectedvalues[1] , cr.getAccession());

        // If the id is not null (compound is saved)...
        if (cr.getId() != 0){
            assertTrue("Is Database saved?", cr.getDb().getId() != 0);

        }

	}


}