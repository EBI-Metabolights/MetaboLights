package uk.ac.ebi.metabolights.spectrumbrowser.client.viewer.data.model;

/**
 * User: conesa
 * Date: 23/05/2013
 * Time: 12:10
 */
public class NMRSpectraDataImpl implements NMRSpectraData {
    private Double[] peaks;
    private String xLabel;
    private String yLabel;
    private float xMax;
    private float xMin;
    private float yMax;
    private float yMin;


    public Double[] getData() {
        return peaks;
    }

    
    public void setData(Double[] peaks) {
        this.peaks = peaks;
    }

    
    public String getxLabel() {
        return this.xLabel;
    }

    
    public void setxLabel(String xLabel) {
        this.xLabel = xLabel;
    }

    
    public float getxMax() {
        return xMax;
    }

    
    public void setxMax(float xMax) {
        this.xMax = xMax;
    }

    
    public float getxMin() {
        return xMin;
    }

    
    public void setxMin(float xMin) {
        this.xMin = xMin;
    }

    
    public String getyLabel() {
        return yLabel;
    }

    
    public void setyLabel(String yLabel) {
        this.yLabel = yLabel;
    }

    
    public float getyMax() {
        return yMax;
    }

    
    public void setyMax(float yMax) {
        this.yMax = yMax;
    }

    
    public float getyMin() {
        return yMin;
    }

    
    public void setyMin(float yMin) {
        this.yMin = yMin;
    }
}
