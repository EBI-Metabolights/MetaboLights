package uk.ac.ebi.metabolights.referencelayer.spectra.viewer.model;

import com.google.gson.annotations.SerializedName;

/**
 * User: conesa
 * Date: 09/05/2013
 * Time: 14:33
 */
public class JsonSpectraPeak {

    @SerializedName("x")
    private double mz;
    @SerializedName("y")
    private double intensity;
    public JsonSpectraPeak(double mz, double intensity){
        this.mz = mz;
        this.intensity = intensity;
    }
}
