package uk.ac.ebi.metabolights.referencelayer.domain;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: conesa
 * Date: 25/04/2013
 * Time: 15:21
 */
public class SpectrumTest {
    @Test
    public void testEquals() throws Exception {



        Spectrum spectrum = new Spectrum("file", "name", Spectrum.SpectrumType.MS);
        assertTrue("Equals to itself expected to be true", spectrum.equals(spectrum));

        Spectrum spectrum2 = new Spectrum("file", spectrum.getName(), spectrum.getSpectrumType());
        assertTrue("Equals to other instance same values expected to be true", spectrum.equals(spectrum2));

        spectrum2.setName("name2");
        assertFalse("Equals expected to be false", spectrum.equals(spectrum2));

        spectrum2.setPathToJsonSpectrum(new File("file2"));
        assertFalse("Equals expected to be false", spectrum.equals(spectrum2));

        spectrum2.setName(spectrum.getName());
        assertFalse("Equals expected to be false", spectrum.equals(spectrum2));

        spectrum2.setSpectrumType(Spectrum.SpectrumType.NMR);
        assertFalse("Equals expected to be false", spectrum.equals(spectrum2));

        spectrum2.setPathToJsonSpectrum(spectrum.getPathToJsonSpectrum());
        assertFalse("Equals expected to be false", spectrum.equals(spectrum2));






    }
}
