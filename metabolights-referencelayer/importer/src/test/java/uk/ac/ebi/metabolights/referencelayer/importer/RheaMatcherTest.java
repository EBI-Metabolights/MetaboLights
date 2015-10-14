/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 10/24/13 9:15 AM
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

import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

/**
 * User: conesa
 * Date: 24/04/2013
 * Time: 11:43
 */
public class RheaMatcherTest extends TestCase {

    protected static final Logger LOGGER = LoggerFactory.getLogger(RheaMatcherTest.class);




    @Override
    @BeforeClass
    protected void setUp() throws Exception {


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
