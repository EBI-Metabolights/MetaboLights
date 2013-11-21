/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 17/05/13 09:38
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package ac.ebi.metabolights.referencelayer.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: conesa
 * Date: 25/04/2013
 * Time: 15:21
 */
public class MetSpeciesTest {
    @Test
    public void testEquals() throws Exception {

        CrossReference cr1 = new CrossReference();
        cr1.setAccession("acc1");

        Species sp1 = new Species();
        sp1.setSpecies("sp1");



        MetSpecies msp1 = new MetSpecies(sp1,cr1);
        assertTrue("Equals to itself expected to be true", msp1.equals(msp1));



        CrossReference cr2 = new CrossReference();
        cr1.setAccession("acc2");

        Species sp2 = new Species();
        sp1.setSpecies("sp2");


        MetSpecies msp2 = new MetSpecies(sp2,cr2);

        assertFalse("Equals expected to be false", msp1.equals(msp2));

        msp2 = new MetSpecies(sp1,cr1);
        assertTrue("Equals expected to be true", msp1.equals(msp2));


        msp2 = new MetSpecies(null,null);
        assertFalse("Equals expected to be false", msp1.equals(msp2));

        msp1 = new MetSpecies(null,null);
        assertTrue("Equals expected to be true", msp1.equals(msp2));


    }
}
