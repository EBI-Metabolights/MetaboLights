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

import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public interface PeakListRaw {

    long getSpectrumId();
    void setSpectrumId(long spectrumId);

    List<PeakRaw> getPeaks();
    void setPeaks(List<PeakRaw> peaks);

    Double getMzStart();
    void setMzStart(Double mzStart);

    Double getMzStop();
    void setMzStop(Double mzStop);
}
