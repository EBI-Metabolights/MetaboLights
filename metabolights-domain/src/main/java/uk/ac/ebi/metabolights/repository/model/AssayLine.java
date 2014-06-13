/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 6/12/14 8:22 AM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.repository.model;

import java.util.Collection;

public class AssayLine {
    private String sampleName;
    private Collection<Factors> factors;
    private Collection<AssayFiles> files;

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public Collection<Factors> getFactors() {
        return factors;
    }

    public void setFactors(Collection<Factors> factors) {
        this.factors = factors;
    }

    public Collection<AssayFiles> getFiles() {
        return files;
    }

    public void setFiles(Collection<AssayFiles> files) {
        this.files = files;
    }
}
