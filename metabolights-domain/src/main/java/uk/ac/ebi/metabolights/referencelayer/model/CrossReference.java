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
 * Species reference table
 */
public class CrossReference extends Entity {


    private String accession;
    private Database db;

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public Database getDb() {
        return db;
    }

    public void setDb(Database db) {
        this.db = db;
    }

    @Override
    public boolean equals(Object obj){

        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof CrossReference))
            return false;

        CrossReference xr = (CrossReference)obj;

        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(accession, xr.accession).
                append(db, xr.db).
                isEquals();

    }
}