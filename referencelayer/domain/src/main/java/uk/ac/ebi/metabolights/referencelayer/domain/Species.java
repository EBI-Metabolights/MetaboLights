package uk.ac.ebi.metabolights.referencelayer.domain;

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



}
