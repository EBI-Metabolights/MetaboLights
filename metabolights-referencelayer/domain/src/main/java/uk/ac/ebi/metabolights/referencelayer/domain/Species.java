/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 13/11/13 13:19
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.domain;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 *
 * @author conesa
 *
 * Species reference table
 */
public class Species extends Identifier {

    private String species;
    private String description;
    private String taxon;
    private SpeciesMembers speciesMember;


    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Taxon id: Ontology term for the specie
     * @return
     */

    public String getTaxon() {
        return taxon;
    }

    public void setTaxon(String taxon) {
        this.taxon = taxon;
    }

    public SpeciesMembers getSpeciesMember() {
        return speciesMember;
    }

    public void setSpeciesMember(SpeciesMembers speciesMember) {
        this.speciesMember = speciesMember;
    }

    @Override
    public boolean equals(Object obj){

        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Species))
            return false;

        Species sp1 = (Species)obj;

        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(species, sp1.species).
                isEquals();

    }

    @Override
    public int hashCode(){

        if (getId() != 0) {
            return new Long(getId()).hashCode();
        } else {
            return species.hashCode();
        }

    }


}
