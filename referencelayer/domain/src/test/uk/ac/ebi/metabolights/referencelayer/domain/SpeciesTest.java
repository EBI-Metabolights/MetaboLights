package uk.ac.ebi.metabolights.referencelayer.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

        sp2.setSpecies("bye");

        assertFalse("Equals expected to be false", sp1.equals(sp2));

    }
}
