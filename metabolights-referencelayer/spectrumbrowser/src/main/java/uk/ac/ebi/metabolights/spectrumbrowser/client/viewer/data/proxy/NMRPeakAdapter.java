/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/06/13 11:49
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.proxy;


import uk.ac.ebi.biowidgets.spectrum.data.Peak;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.model.PeakRaw;

public class NMRPeakAdapter implements Peak {

    Double x;
    Double intensity;

    public NMRPeakAdapter(Double x, Double intensity) {
        this.x = x;
        this.intensity = intensity;
    }

    @Override
    public Double getAnnotation() {
        return Double.valueOf(0);
    }

    @Override
    public Double getIntensity() {
        return Double.valueOf(this.intensity);
    }

    @Override
    public Double getMz() {
        return this.x;
    }
}
