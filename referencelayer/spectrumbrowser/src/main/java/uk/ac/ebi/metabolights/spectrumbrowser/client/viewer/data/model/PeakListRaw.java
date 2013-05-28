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
