/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 26/04/13 13:52
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
 * Source of any entry (CHEBI, METABOLIGHTS,....)
 */
public class Database extends Identifier {

	private String name;

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

    @Override
    public boolean equals(Object obj){

        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Database))
            return false;

        Database db1 = (Database)obj;

        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                        append(this.name, db1.name).
                isEquals();

    }

}
