package uk.ac.ebi.metabolights.referencelayer.spectra.viewer.model;

/**
 * User: conesa
 * Date: 09/05/2013
 * Time: 14:33
 */
public class JsonSpectraPeak {
    private double mz;
    private double intensity;
    public JsonSpectraPeak(double mz, double intensity){
        this.mz = mz;
        this.intensity = intensity;
    }
}
