/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 17/09/13 12:23
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.repository.model;

import java.util.Collection;

public class Assay {

    private String measurement;
    private String technology;
    private String platform;
    private String fileName;
    private Collection<AssayLine> assayLines;
    private MetaboliteAssignment metaboliteAssignment; //this also contains a file name MAF

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Collection<AssayLine> getAssayLines() {
        return assayLines;
    }

    public void setAssayLines(Collection<AssayLine> assayLines) {
        this.assayLines = assayLines;
    }

    public MetaboliteAssignment getMetaboliteAssignment() {
        if (metaboliteAssignment == null)
            metaboliteAssignment = new MetaboliteAssignment();

        return metaboliteAssignment;
    }

    public void setMetaboliteAssignment(MetaboliteAssignment metaboliteAssignment) {
        this.metaboliteAssignment = metaboliteAssignment;
    }
}
