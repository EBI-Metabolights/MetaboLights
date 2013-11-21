/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 26/04/13 13:52
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package ac.ebi.metabolights.referencelayer.domain;


import org.junit.Test;

import static org.junit.Assert.*;
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
