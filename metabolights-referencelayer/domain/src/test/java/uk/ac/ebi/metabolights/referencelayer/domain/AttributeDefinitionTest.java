/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 10/06/13 14:39
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
 * Time: 14:46
 */
public class AttributeDefinitionTest {
    @Test
    public void testEquals() throws Exception {

        AttributeDefinition ad1 = new AttributeDefinition();
        ad1.setName("hello");
        ad1.setDescription("description");

        AttributeDefinition ad2 = new AttributeDefinition();
        ad2.setName(ad1.getName());
        ad2.setDescription("something else");


        assertTrue("Equals expected to be true", ad1.equals(ad2));
        assertTrue("Equals to itself expected to be true", ad2.equals(ad2));

        ad2.setName("bye");

        assertFalse("Equals expected to be false", ad1.equals(ad2));




    }
}
