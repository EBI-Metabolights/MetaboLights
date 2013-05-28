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
