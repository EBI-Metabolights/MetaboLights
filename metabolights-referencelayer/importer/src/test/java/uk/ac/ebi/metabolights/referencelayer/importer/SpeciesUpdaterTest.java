/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 19/06/13 08:34
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.importer;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.ac.ebi.biobabel.util.db.DatabaseInstance;

import java.io.File;
import java.net.URL;
import java.sql.Connection;

/**
 * User: conesa
 * Date: 18/06/2013
 * Time: 12:18
 */
public class SpeciesUpdaterTest {
    protected static final Logger LOGGER = Logger.getLogger(SpeciesUpdaterTest.class);

    private static Connection con;

    @BeforeClass
    public static void setUp() throws Exception {

        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();

        DatabaseInstance dbi = DatabaseInstance.getInstance("metabolightsDEV"); //OracleDatabaseInstance.getInstance("metabolightsDEV");
        //DatabaseInstance dbi = DatabaseInstance.getInstance("metabolightsMYSQL");
        con = dbi.getConnection();


    }
    @AfterClass
    public static void tearDown() throws Exception {
        if (con != null) con.close();

    }


    @Test
    public void UpdateSpecies() throws Exception {


        SpeciesUpdater speciesUpdater = new SpeciesUpdater(con);


        speciesUpdater.UpdateSpeciesInformation();

    }
}
