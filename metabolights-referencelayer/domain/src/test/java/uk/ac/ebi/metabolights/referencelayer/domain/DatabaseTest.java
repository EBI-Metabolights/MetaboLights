/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 10/1/14 2:42 PM
 * Modified by:   kenneth
 *
 * Copyright 2014 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * Y
 * ou may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.referencelayer.domain;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * User: conesa
 * Date: 25/04/2013
 * Time: 14:46
 */
public class DatabaseTest {
    @Test
    public void testEquals() throws Exception {

        Database db1 = new Database();
        db1.setName("hello");

        Database db2 = new Database();
        db2.setName(db1.getName());


        assertTrue("Equals expected to be true", db1.equals(db2));
        assertTrue("Equals to itself expected to be true", db2.equals(db2));

        db2.setName("bye");

        assertFalse("Equals expected to be false", db1.equals(db2));




    }
}
