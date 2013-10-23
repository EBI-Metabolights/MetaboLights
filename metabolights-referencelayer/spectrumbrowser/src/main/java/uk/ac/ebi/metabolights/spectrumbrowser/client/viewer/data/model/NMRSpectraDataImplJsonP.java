/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 10/06/13 14:39
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayNumber;

/**
 * User: conesa
 * Date: 23/05/2013
 * Time: 12:10
 */
public class NMRSpectraDataImplJsonP extends JavaScriptObject {

    protected NMRSpectraDataImplJsonP(){}


    public final native String getxLabel()/*-{
     return this.xLabel;
     }-*/;


    public final native void setxLabel(String xLabel) /*-{
        this.xLabel = xLabel;
    }-*/;


    public final native float getxMax() /*-{
        return xMax;
    }-*/;


    public final native void setxMax(float xMax) /*-{
        this.xMax = xMax;
    }-*/;


    public final native float getxMin() /*-{
        return xMin;
    }-*/;


    public final native void setxMin(float xMin) /*-{
        this.xMin = xMin;
    }-*/;


    public final native String getyLabel() /*-{
        return yLabel;
    }-*/;


    public final native void setyLabel(String yLabel) /*-{
        this.yLabel = yLabel;
    }-*/;


    public final native float getyMax() /*-{
        return yMax;
    }-*/;


    public final native void setyMax(float yMax) /*-{
        this.yMax = yMax;
    }-*/;


    public final native float getyMin() /*-{
        return yMin;
    }-*/;


    public final native void setyMin(float yMin) /*-{
        this.yMin = yMin;
    }-*/;

    public final native JsArrayNumber getData() /*-{
        return this.data;
    }-*/;

//
//    public void setData(Double[] peaks) /*-{
//        this.peaks = peaks;
//    }-*/;
}
