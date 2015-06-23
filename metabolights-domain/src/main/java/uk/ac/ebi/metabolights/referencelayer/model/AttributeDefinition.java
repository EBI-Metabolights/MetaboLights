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
 * Attribute definition for generic Attributes (it doesn't hod any values, just name and description)
 */
public class AttributeDefinition extends Identifier implements Comparable<AttributeDefinition>{

	private String name;
    private String description;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj){

        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof AttributeDefinition))
            return false;

        AttributeDefinition ad1 = (AttributeDefinition)obj;

        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                        append(this.name, ad1.name).
                isEquals();

    }

    @Override
    public int compareTo(AttributeDefinition attributeDefinition) {
        return name.compareTo(attributeDefinition.name);
    }
}
