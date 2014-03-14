/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/06/13 11:49
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.model;

@SuppressWarnings("UnusedDeclaration")
public interface PeakRaw {

    Double getMz();

    void setMz(Double mz);

    Double getIntensity();

    void setIntensity(Double intensity);

    Double getAnnotation();

    void setAnnotation(String annotation);
}
