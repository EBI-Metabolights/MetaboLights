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
import uk.ac.ebi.biowidgets.spectrum.data.PeakList;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.model.Discretizer;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.model.NMRSpectraData;


import java.util.LinkedList;
import java.util.List;

public class NMRPeakListAdapter implements PeakList {
    private List<Peak> peaks;
    private NMRSpectraData peakList;

    public NMRPeakListAdapter(NMRSpectraData nmrSpectraData) {
        this.peakList = nmrSpectraData;
        this.peaks = new LinkedList<Peak>();


        // Instantiate the discretizer
        Discretizer disc = new Discretizer(nmrSpectraData.getxMin(), nmrSpectraData.getxMax(), nmrSpectraData.getData().length);

        for (int i = 0; i < peakList.getData().length;i++) {

            // Create a new peak adapter
            peaks.add(new NMRPeakAdapter(disc.valueAtPoint(i),peakList.getData()[i]));

        }

    }

    @Override
    public Double getMzStart() {
        return Double.valueOf(this.peakList.getxMax());
    }

    @Override
    public Double getMzStop() {
        return Double.valueOf(this.peakList.getxMin());
    }

    @Override
    public List<Peak> getPeaks() {
        return this.peaks;
    }

    @Override
    public long getSpectrumId() {
        return 1;
    }
}

