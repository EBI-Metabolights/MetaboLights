/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 17/05/13 09:38
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: conesa
 * Date: 25/04/2013
 * Time: 15:15
 */
public class CrossReferenceTest {

    @Test
    public void testEquals() throws Exception {

        Database db1 = new Database();
        db1.setName("db1");

        Database db2 = new Database();
        db2.setName("db2");


        CrossReference cr1 = new CrossReference();
        cr1.setAccession("hello");
        cr1.setDb(db1);

        CrossReference cr2 = new CrossReference();
        cr2.setAccession(cr1.getAccession());
        cr2.setDb(cr1.getDb());


        assertTrue("Equals expected to be true", cr1.equals(cr2));
        assertTrue("Equals to itself expected to be true", cr2.equals(cr2));

        cr2.setAccession("bye");
        assertFalse("Equals expected to be false", cr1.equals(cr2));

        cr2.setDb(db2);
        assertFalse("Equals expected to be false", cr1.equals(cr2));

        cr2.setAccession(cr1.getAccession());
        assertFalse("Equals expected to be false", cr1.equals(cr2));


    }
}
