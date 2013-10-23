/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 08/07/13 08:56
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
 * Date: 17/06/2013
 * Time: 17:13
 */
public class PathwayImporterTest {

    protected static final Logger LOGGER = Logger.getLogger(PathwayImporterTest.class);

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
    public void testImportPathwaysFromFolder() throws Exception {


        PathwayImporter rli = new PathwayImporter(con);


        //URL url = ImporterTests.class.getClassLoader().getResource("pathways");

        URL url = new URL("file:/nfs/public/rw/homes/tc_cm01/reference/");

        if (url == null) {
            // error - missing folder
        } else {
            File pathwaysFolder = new File(url.getFile());

            rli.importPathwaysFromFolder(pathwaysFolder);

        }


    }
}
