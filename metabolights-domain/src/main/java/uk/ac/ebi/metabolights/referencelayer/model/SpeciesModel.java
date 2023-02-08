package uk.ac.ebi.metabolights.referencelayer.model;

import org.apache.commons.lang.builder.EqualsBuilder;

public class SpeciesModel extends EntityModel {

    private String species;
    private String description;
    private String taxon;
    private SpeciesMembersModel speciesMember;


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

    public SpeciesMembersModel getSpeciesMember() {
        return speciesMember;
    }

    public void setSpeciesMember(SpeciesMembersModel speciesMember) {
        this.speciesMember = speciesMember;
    }

    @Override
    public boolean equals(Object obj){

        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof SpeciesModel))
            return false;

        SpeciesModel sp1 = (SpeciesModel)obj;

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
