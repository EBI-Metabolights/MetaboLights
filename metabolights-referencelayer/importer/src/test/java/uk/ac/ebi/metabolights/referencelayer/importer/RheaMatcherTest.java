/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 26/04/13 14:21
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.importer;

import junit.framework.TestCase;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import java.io.File;
import java.net.URL;

/**
 * User: conesa
 * Date: 24/04/2013
 * Time: 11:43
 */
public class RheaMatcherTest extends TestCase {

    protected static final Logger LOGGER = Logger.getLogger(RheaMatcherTest.class);




    @Override
    @BeforeClass
    protected void setUp() throws Exception {

        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();

    }


    public void testMatchMetabolitesWithRhea() throws Exception {


        RheaMatcher rm = new RheaMatcher();


        URL url = RheaMatcherTest.class.getClassLoader().getResource("ChEBI_Results_Metabolites.tsv");
        if (url == null) {
            // error - missing folder
        } else {
            File chebiTSV = new File(url.getFile());

            rm.matchMetabolitesWithRhea(chebiTSV);

        }


    }
}
