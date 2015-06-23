/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 20/06/13 14:18
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.model;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 *
 * @author conesa
 *
 * Species reference table
 */
public class Attribute extends Identifier implements Comparable<Attribute>{

    private String value;
    private AttributeDefinition attributeDefinition;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AttributeDefinition getAttributeDefinition() {
        return attributeDefinition;
    }

    public void setAttributeDefinition(AttributeDefinition attributeDefinition) {
        this.attributeDefinition = attributeDefinition;
    }

    @Override
    public boolean equals(Object obj){

        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Attribute))
            return false;

        Attribute attribute = (Attribute)obj;

        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(value, attribute.value).
                append(attributeDefinition, attribute.attributeDefinition).
                isEquals();

    }

    @Override
    public int compareTo(Attribute attribute) {
        int defComp = attributeDefinition.compareTo(attribute.attributeDefinition);

        return defComp == 0? value.compareTo(attribute.value): defComp;
    }
}