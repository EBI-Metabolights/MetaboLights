package uk.ac.ebi.metabolights.repository.model;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: tejasvi
 * Date: 02/09/13
 * Time: 13:54
 * To change this template use File | Settings | File Templates.
 */
public class Assay {

    private String measurement;
    private String technology;
    private String platform;
    private String fileName;

    private Collection<AssayLine> asssayLines;

    public Collection<AssayLine> getAsssayLines() {
        return asssayLines;
    }

    public void setAsssayLines(Collection<AssayLine> asssayLines) {
        this.asssayLines = asssayLines;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

}
