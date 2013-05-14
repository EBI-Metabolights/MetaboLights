package uk.ac.ebi.metabolights.referencelayer.spectra.viewer.model;

import java.util.ArrayList;

/**
 * User: conesa
 * Date: 09/05/2013
 * Time: 14:29
 */
public class JsonSpectra {

    private long spectrumId;
    private double mzStart;
    private double mzStop;
    private ArrayList<JsonSpectraPeak> peaks = new ArrayList<JsonSpectraPeak>();

    public long getSpectrumId() {
        return spectrumId;
    }

    public void setSpectrumId(long spectrumId) {
        this.spectrumId = spectrumId;
    }

    public double getMzStart() {
        return mzStart;
    }

    public void setMzStart(double mzStart) {
        this.mzStart = mzStart;
    }

    public double getMzStop() {
        return mzStop;
    }

    public void setMzStop(double mzStop) {
        this.mzStop = mzStop;
    }

    public ArrayList<JsonSpectraPeak> getPeaks() {
        return peaks;
    }

    public void setPeaks(ArrayList<JsonSpectraPeak> peaks) {
        this.peaks = peaks;
    }
}
