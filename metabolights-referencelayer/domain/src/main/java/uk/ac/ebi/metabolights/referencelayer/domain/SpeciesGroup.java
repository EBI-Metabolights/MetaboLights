/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 13/11/13 13:18
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.domain;

import java.util.List;

public class SpeciesGroup {

    private long id;
    private String name;
    private List<SpeciesMembers> speciesMembersList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SpeciesMembers> getSpeciesMembersList() {
        return speciesMembersList;
    }

    public void setSpeciesMembersList(List<SpeciesMembers> speciesMembersList) {
        this.speciesMembersList = speciesMembersList;
    }
}
