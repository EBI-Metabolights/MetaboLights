/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 18/09/13 10:33
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.repository.model;

/**
 * Created with IntelliJ IDEA.
 * User: tejasvi
 * Date: 03/09/13
 * Time: 09:15
 * To change this template use File | Settings | File Templates.
 */
public class AssayLine {
    private String sampleName;
    //private String

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

}
