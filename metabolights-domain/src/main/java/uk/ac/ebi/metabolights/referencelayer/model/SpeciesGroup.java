/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 13/11/13 13:18
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.model;

import uk.ac.ebi.metabolights.repository.model.Entity;

import java.util.Collection;

public class SpeciesGroup extends Entity {

    private String name;
    private Collection<SpeciesMembers> speciesMembersList;
	private Collection<SpeciesGroup> children;
	private Collection<Species> specieses;
	private long parentId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<SpeciesMembers> getSpeciesMembersList() {
        return speciesMembersList;
    }

    public void setSpeciesMembersList(Collection<SpeciesMembers> speciesMembersList) {
        this.speciesMembersList = speciesMembersList;
    }

	public Collection<SpeciesGroup> getChildren() {
		return children;
	}

	public void setChildren(Collection<SpeciesGroup> children) {
		this.children = children;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public Collection<Species> getSpecieses() {
		return specieses;
	}

	public void setSpecieses(Collection<Species> specieses) {
		this.specieses = specieses;
	}
}
