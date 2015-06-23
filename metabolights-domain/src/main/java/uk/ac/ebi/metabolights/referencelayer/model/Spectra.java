/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 10/06/13 14:39
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.model;

import org.apache.commons.lang.builder.EqualsBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author conesa
 *
 * MetaboLights compound to Spectra linking object
 */
public class Spectra extends Identifier{

    private File pathToJsonSpectra;
    private String name;
    private SpectraType spectraType;
    private Collection<Attribute> attributes = new ArrayList<Attribute>();

    public enum SpectraType {
        NMR,
        MS
    }

    public Spectra(long id, String pathToSpectra, String name, SpectraType spectraType){
        construct(id,pathToSpectra, name, spectraType);
    }
    public Spectra(String pathToSpectra, String name, SpectraType spectraType){
        construct(0,pathToSpectra, name, spectraType);
    }

    private void construct(long id, String pathToSpectra, String name, SpectraType spectraType){
        this.setId(id);
        this.pathToJsonSpectra = new File(pathToSpectra);
        this.name = name;
        this.spectraType = spectraType;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getPathToJsonSpectra() {
        return pathToJsonSpectra;
    }

    public void setPathToJsonSpectra(File pathToJsonSpectra) {
        this.pathToJsonSpectra = pathToJsonSpectra;
    }

    public SpectraType getSpectraType() {
        return spectraType;
    }

    public void setSpectraType(SpectraType spectraType) {
        this.spectraType = spectraType;
    }

    public Collection<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Collection<Attribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object obj){

        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Spectra))
            return false;

        Spectra spectra = (Spectra)obj;

        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                        append(this.pathToJsonSpectra, spectra.pathToJsonSpectra).
                        append(this.name, spectra.name).
                        append(this.spectraType, spectra.spectraType).
                        isEquals();

    }
}
