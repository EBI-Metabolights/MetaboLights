/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/06/13 16:08
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.spectra.viewer.model;

import com.google.gson.annotations.SerializedName;

/**
 * User: conesa
 * Date: 09/05/2013
 * Time: 14:33
 */
public class JsonSpectraPeak {

    @SerializedName("mz")
    private double mz;
    @SerializedName("intensity")
    private double intensity;

    public JsonSpectraPeak(double mz, double intensity) {
        this.mz = mz;
        this.intensity = intensity;
    }
}
