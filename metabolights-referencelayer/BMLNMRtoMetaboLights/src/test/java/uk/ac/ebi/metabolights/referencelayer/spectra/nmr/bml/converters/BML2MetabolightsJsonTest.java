/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 17/05/13 09:38
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.spectra.nmr.bml.converters;

import com.google.common.io.Files;

import java.io.File;
import java.net.URL;
import static org.junit.Assert.*;

/**
 * User: conesa
 * Date: 09/05/2013
 * Time: 16:30
 */
public class BML2MetabolightsJsonTest {
    @org.junit.Test
    public void testRun() throws Exception {

        URL url = BML2MetabolightsJsonTest.class.getClassLoader().getResource("BML_Analysis_1.xml");
        if (url == null) {
            // error - missing BML test file
            System.out.println("error - missing BML test file: BML_Analysis_1.xml");

        } else {


            // Get the bml file
            File bmlFile = new File(url.getFile());

            // If there is a previous output file...delete it
            File outPut = new File (bmlFile.getAbsolutePath() + ".json");
            if (outPut.exists()) outPut.delete();


            bmlFile.setWritable(false);

            int numFiles =  bmlFile.getParentFile().list().length;


            BML2MetabolightsJson bml2MetabolightsJson = new BML2MetabolightsJson(bmlFile);

            bml2MetabolightsJson.run();

            File parent = bmlFile.getParentFile();

            // Test if the output file exists...
            assertEquals("Is there a new file?",  numFiles+1, parent.list().length );


        }

    }


    @org.junit.Test
    public void testRun2() throws Exception {

        URL url = BML2MetabolightsJsonTest.class.getClassLoader().getResource("BML_Analysis_1.xml");
        if (url == null) {
            // error - missing BML test file
            System.out.println("error - missing BML test file: BML_Analysis_1.xml");

        } else {

            // Get the bml file
            File bmlFile = new File(url.getFile());

            bmlFile.setWritable(false);

            File tmpDir = Files.createTempDir();

            BML2MetabolightsJson bml2MetabolightsJson = new BML2MetabolightsJson(bmlFile, tmpDir);

            bml2MetabolightsJson.run();


            // Test if the output file exists...
            assertEquals("Is there a json file in the temp folder?", 1, tmpDir.listFiles().length );



        }


    }


    @org.junit.Test
    public void testRunReadable() throws Exception {

        URL url = BML2MetabolightsJsonTest.class.getClassLoader().getResource("BML_Analysis_1.xml");
        if (url == null) {
            // error - missing BML test file
            System.out.println("error - missing BML test file: BML_Analysis_1.xml");

        } else {

            // Get the bml file
            File bmlFile = new File(url.getFile());

            bmlFile.setWritable(true);

            File tmpDir = Files.createTempDir();


            BML2MetabolightsJson bml2MetabolightsJson = new BML2MetabolightsJson(bmlFile, tmpDir);

            bml2MetabolightsJson.run();

            // Test if the output file exists...
            assertEquals("We don't expect any jason file?",  0,  tmpDir.list().length) ;

        }
    }
}
