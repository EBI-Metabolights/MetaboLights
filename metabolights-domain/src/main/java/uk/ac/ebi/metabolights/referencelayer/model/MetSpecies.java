/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 17/05/13 09:38
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import uk.ac.ebi.metabolights.repository.model.Entity;

/**
 *
 * @author conesa
 *
 * MetaboLights compound to Species linking object
 */
public class MetSpecies extends Entity {

    private Species species;
    private CrossReference crossReference;

    public MetSpecies(long id, Species species, CrossReference crossReference){
        construct(id,species,crossReference);
    }
    public MetSpecies(Species species, CrossReference crossReference){
        construct(0,species,crossReference);
    }

    private void construct(long id, Species species, CrossReference crossReference){
        this.setId(id);
        this.species = species;
        this.crossReference= crossReference;

    }

    public Species getSpecies() {
        return species;
    }

    public CrossReference getCrossReference() {
        return crossReference;
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
                        append(this.crossReference, metSpecies.crossReference).
                        append(this.species, metSpecies.species).
                        isEquals();

    }
}
