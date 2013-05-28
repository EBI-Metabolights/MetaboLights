package uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.model;

@SuppressWarnings("UnusedDeclaration")
public interface PeakRaw {

    Double getX();
    void setX(Double x);

    Double getY();
    void setY(Double y);

    Double getAnnotation();
    void setAnnotation(String annotation);

}
