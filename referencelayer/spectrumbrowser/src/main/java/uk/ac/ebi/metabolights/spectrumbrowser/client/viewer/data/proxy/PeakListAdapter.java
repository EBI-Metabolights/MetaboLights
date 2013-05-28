package uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.proxy;


import uk.ac.ebi.biowidgets.spectrum.data.Peak;
import uk.ac.ebi.biowidgets.spectrum.data.PeakList;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.model.PeakListRaw;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.model.PeakRaw;

import java.util.LinkedList;
import java.util.List;

public class PeakListAdapter implements PeakList {
    private List<Peak> peaks;
    private PeakListRaw peakList;

    public PeakListAdapter(PeakListRaw peakList) {
        this.peakList = peakList;
        this.peaks = new LinkedList<Peak>();
        for (PeakRaw peak : this.peakList.getPeaks()) {
            peaks.add(new PeakAdapter(peak));
        }
    }

    @Override
    public Double getMzStart() {
        return this.peakList.getMzStart();
    }

    @Override
    public Double getMzStop() {
        return this.peakList.getMzStop();
    }

    @Override
    public List<Peak> getPeaks() {
        return this.peaks;
    }

    @Override
    public long getSpectrumId() {
        return this.peakList.getSpectrumId();
    }
}

