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

import static org.junit.Assert.*;

/**
 * User: conesa
 * Date: 25/04/2013
 * Time: 15:15
 */
public class SpeciesTest {

    @Test
    public void testEquals() throws Exception {


        Species sp1 = new Species();
        sp1.setSpecies("hello");

        Species sp2 = new Species();
        sp2.setSpecies(sp1.getSpecies());


        assertTrue("Equals expected to be true", sp1.equals(sp2));
        assertTrue("Equals to itself expected to be true", sp2.equals(sp2));


        assertEquals("hasCode expected to be equal to hello hashcode", "hello".hashCode(), sp1.hashCode());
        assertEquals("hasCode expected to be equal", sp1.hashCode(), sp2.hashCode());

        sp2.setSpecies("bye");
        sp2.setId(3);

        assertFalse("Equals expected to be false", sp1.equals(sp2));
        assertEquals("hasCode expected to be equal to 3.hashCode", new Long(3).hashCode(), sp2.hashCode());
        assertNotSame("hasCode expected to be different", sp1.hashCode(), sp2.hashCode());





    }
}
