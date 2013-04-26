package uk.ac.ebi.metabolights.referencelayer.domain;

import org.apache.commons.lang.builder.EqualsBuilder;

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

    @Override
    public boolean equals(Object obj){

        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof MetSpecies))
            return false;

        MetSpecies metSpecies = (MetSpecies)obj;

        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                        append(this.database, metSpecies.database).
                        append(this.species, metSpecies.species).
                        isEquals();

    }
}
