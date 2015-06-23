/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 13/11/13 13:20
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.model;

import java.util.List;

public class SpeciesMembers extends Identifier {

    private SpeciesGroup speciesGroup;
    private long parentMemberId;
    private String taxon;
    private String taxonDesc;
    private List<Species> speciesList;

	public SpeciesGroup getSpeciesGroup() {
		return speciesGroup;
	}

	public void setSpeciesGroup(SpeciesGroup speciesGroup) {
		this.speciesGroup = speciesGroup;
	}

	public long getParentMemberId() {
        return parentMemberId;
    }

    public void setParentMemberId(long parentMemberId) {
        this.parentMemberId = parentMemberId;
    }

    public String getTaxon() {
        return taxon;
    }

    public void setTaxon(String taxon) {
        this.taxon = taxon;
    }

    public String getTaxonDesc() {
        return taxonDesc;
    }

    public void setTaxonDesc(String taxonDesc) {
        this.taxonDesc = taxonDesc;
    }

    public List<Species> getSpeciesList() {
        return speciesList;
    }

    public void setSpeciesList(List<Species> speciesList) {
        this.speciesList = speciesList;
    }

}
