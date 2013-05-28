package uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.proxy;


import uk.ac.ebi.biowidgets.spectrum.data.Peak;
import uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.model.PeakRaw;

public class PeakAdapter implements Peak {

    PeakRaw peak;

    public PeakAdapter(PeakRaw peak) {
        this.peak = peak;
    }

    @Override
    public Double getAnnotation() {
        return this.peak.getAnnotation();
    }

    @Override
    public Double getIntensity() {
        return this.peak.getY();
    }

    @Override
    public Double getMz() {
        return this.peak.getX();
    }
}
