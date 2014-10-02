/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/7/14 5:29 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.referencelayer.importer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ebi.biobabel.util.db.DatabaseInstance;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.SpeciesDAO;
import uk.ac.ebi.metabolights.referencelayer.domain.Species;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * User: conesa
 * Date: 18/06/2013
 * Time: 12:18
 */
public class SpeciesUpdaterTest {
    protected static final Logger LOGGER = Logger.getLogger(SpeciesUpdaterTest.class);
	private static final String WORMS_TEST_ID = "WORMS:145379";
	private static final String NEWT_HUMAN = "NEWT:9606";

	private static Connection con;

    @BeforeClass
    public static void setUp() throws Exception {

        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();

        DatabaseInstance dbi = DatabaseInstance.getInstance("metabolightsPROD");
		//DatabaseInstance dbi = DatabaseInstance.getInstance("metabolightsDEV");

        con = dbi.getConnection();


    }
    @AfterClass
    public static void tearDown() throws Exception {
        if (con != null) con.close();

    }


    @Test
    public void UpdateSpecies() throws Exception {


        SpeciesUpdater speciesUpdater = new SpeciesUpdater(con);

		speciesUpdater.setUpdateOptions(SpeciesUpdater.UpdateOptions.GROUP_USE_GLOBAL_NAMES);


        speciesUpdater.UpdateSpeciesInformation();

    }


	@Test
	public void UpdateSpecieFromName() throws Exception {


		SpeciesUpdater speciesUpdater = new SpeciesUpdater(con);

		SpeciesDAO spd = new SpeciesDAO(con);

		Species sp = spd.findBySpeciesTaxon(NEWT_HUMAN);

		// If HUman is not there (just in case)...
		if (sp != null) {

			sp.setTaxon(null);
			sp.setSpeciesMember(null);
		} else {
			sp = new Species();
			sp.setSpecies("Homo sapiens (Human)");
		}

		speciesUpdater.UpdateSpeciesInformation(sp);

		// We should have the taxon information and and species member id....
		assertEquals("Taxon should be populated" , NEWT_HUMAN, sp.getTaxon());

		assertTrue("Species Member id should be populated." , sp.getSpeciesMember()!= null);

	}

	@Test
	public void UpdateSpeciFromTaxon() throws Exception {


		SpeciesUpdater speciesUpdater = new SpeciesUpdater(con);

		SpeciesDAO spd = new SpeciesDAO(con);

		Species sp = spd.findBySpeciesTaxon(NEWT_HUMAN);

		// If HUman is not there (just in case)...
		if (sp != null) {

			sp.setSpecies(null);
			sp.setSpeciesMember(null);
		} else {
			sp = new Species();
			sp.setTaxon(NEWT_HUMAN);
		}

		speciesUpdater.UpdateSpeciesInformation(sp);

		// We should have the taxon information and and species member id....
		assertNotNull("Name should be populated", sp.getSpecies());

		assertTrue("Species Member id should be populated." , sp.getSpeciesMember()!=null);

	}

	@Test
	public void UpdateWORMSSpecie() throws Exception {


		SpeciesUpdater speciesUpdater = new SpeciesUpdater(con);
		speciesUpdater.setUpdateOptions(SpeciesUpdater.UpdateOptions.GROUPS);

		SpeciesDAO spd = new SpeciesDAO(con);

		Species sp = spd.findBySpeciesTaxon(WORMS_TEST_ID);

		// If Human is not there (just in case)...
		if (sp != null) {

			sp.setSpeciesMember(null);
		} else {
			sp = new Species();
			sp.setSpecies("Aaptos ciliata");
			sp.setTaxon(WORMS_TEST_ID);
		}

		speciesUpdater.UpdateSpeciesInformation(sp);

		assertTrue("Species Member id should be populated." , sp.getSpeciesMember()!=null);

	}
}
