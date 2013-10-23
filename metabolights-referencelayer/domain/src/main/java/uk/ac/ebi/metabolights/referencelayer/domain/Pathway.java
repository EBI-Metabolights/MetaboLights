/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 20/06/13 14:18
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.domain;

import org.apache.commons.lang.builder.EqualsBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author conesa
 *
 * Pathway associated with a MetaboLightsCompound
 */
public class Pathway {

	private long id;
    private String name;
    private Database database;
    private File pathToPathwayFile;
    private Collection<Attribute> attributes = new ArrayList<Attribute>();
    private Species speciesAssociated;


    public Pathway(long id, String name, Database database, File pathToPathwayFile, Species speciesAssociated){
        construct(id,name, database, pathToPathwayFile, speciesAssociated);
    }
    public Pathway(String name, Database database, File pathToPathwayFile, Species speciesAssociated){
        construct(0,name, database, pathToPathwayFile, speciesAssociated);
    }

    private void construct(long id, String name, Database database, File pathToPathwayFile, Species speciesAssociated){
        this.id = id;
        this.name = name;
        this.database = database;
        this.pathToPathwayFile = pathToPathwayFile;
        this.speciesAssociated = speciesAssociated;

    }
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

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Collection<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Collection<Attribute> attributes) {
        this.attributes = attributes;
    }

    public File getPathToPathwayFile() {
        return pathToPathwayFile;
    }

    public void setPathToPathwayFile(File pathToPathwayFile) {
        this.pathToPathwayFile = pathToPathwayFile;
    }

    public Species getSpeciesAssociated() {
        return speciesAssociated;
    }

    public void setSpeciesAssociated(Species speciesAssociated) {
        this.speciesAssociated = speciesAssociated;
    }

    @Override
    public boolean equals(Object obj){

        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Pathway))
            return false;

        Pathway pathway = (Pathway)obj;

        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                        append(this.database, pathway.database).
                        append(this.name, pathway.name).
                        append(this.speciesAssociated, pathway.speciesAssociated).
                        isEquals();

    }
}
