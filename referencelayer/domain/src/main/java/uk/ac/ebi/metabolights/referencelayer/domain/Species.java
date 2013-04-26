package uk.ac.ebi.metabolights.referencelayer.domain;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author conesa
 *
 * Species reference table
 */
public class Species {

	private long id;
    private String species;
    private String description;
    private String taxon;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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



}
