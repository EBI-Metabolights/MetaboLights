package uk.ac.ebi.metabolights.referencelayer.domain;

/**
 * 
 * @author conesa
 *
 * MetaboLights compound to Species linking object
 */
public class MetSpecies {

	private long id;
    private Species species;
    private Database database;

    public MetSpecies(long id, Species species, Database database){
        construct(id,species,database);
    }
    public MetSpecies(Species species, Database database){
        construct(0,species,database);
    }

    private void construct(long id, Species species, Database database){
        this.id = id;
        this.species = species;
        this.database= database;

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Species getSpecies() {
        return species;
    }

    public Database getDatabase() {
        return database;
    }
}
