/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 10/06/13 14:39
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: conesa
 * Date: 25/04/2013
 * Time: 15:15
 */
public class AttributeTest {

    @Test
    public void testEquals() throws Exception {

        AttributeDefinition ad1 = new AttributeDefinition();
        ad1.setName("ad1");

        AttributeDefinition ad2 = new AttributeDefinition();
        ad2.setName("ad2");


        Attribute attr1 = new Attribute();
        attr1.setValue("hello");
        attr1.setAttributeDefinition(ad1);

        Attribute att2 = new Attribute();
        att2.setValue(attr1.getValue());
        att2.setAttributeDefinition(attr1.getAttributeDefinition());


        assertTrue("Equals expected to be true", attr1.equals(att2));
        assertTrue("Equals to itself expected to be true", att2.equals(att2));

        att2.setValue("bye");
        assertFalse("Equals expected to be false", attr1.equals(att2));

        att2.setAttributeDefinition(ad2);
        assertFalse("Equals expected to be false", attr1.equals(att2));

        att2.setValue(attr1.getValue());
        assertFalse("Equals expected to be false", attr1.equals(att2));


    }
}
