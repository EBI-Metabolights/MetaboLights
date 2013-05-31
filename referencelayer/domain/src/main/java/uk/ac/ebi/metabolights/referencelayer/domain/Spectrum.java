package uk.ac.ebi.metabolights.referencelayer.domain;

import org.apache.commons.lang.builder.EqualsBuilder;

import java.io.File;

/**
 * 
 * @author conesa
 *
 * MetaboLights compound to Spectrum linking object
 */
public class Spectrum {

	private long id;
    private File pathToJsonSpectrum;
    private String name;
    private SpectrumType spectrumType;

    public enum SpectrumType {
        NMR,
        MS
    }

    public Spectrum(long id, String pathToSpectrum, String name, SpectrumType spectrumType){
        construct(id,pathToSpectrum, name, spectrumType);
    }
    public Spectrum(String pathToSpectrum, String name, SpectrumType spectrumType){
        construct(0,pathToSpectrum, name, spectrumType);
    }

    private void construct(long id, String pathToSpectrum, String name, SpectrumType spectrumType){
        this.id = id;
        this.pathToJsonSpectrum = new File(pathToSpectrum);
        this.name = name;
        this.spectrumType = spectrumType;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getPathToJsonSpectrum() {
        return pathToJsonSpectrum;
    }

    public void setPathToJsonSpectrum(File pathToJsonSpectrum) {
        this.pathToJsonSpectrum = pathToJsonSpectrum;
    }

    public SpectrumType getSpectrumType() {
        return spectrumType;
    }

    public void setSpectrumType(SpectrumType spectrumType) {
        this.spectrumType = spectrumType;
    }

    @Override
    public boolean equals(Object obj){

        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Spectrum))
            return false;

        Spectrum spectrum = (Spectrum)obj;

        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                        append(this.pathToJsonSpectrum, spectrum.pathToJsonSpectrum).
                        append(this.name, spectrum.name).
                        append(this.spectrumType, spectrum.spectrumType).
                        isEquals();

    }
}
