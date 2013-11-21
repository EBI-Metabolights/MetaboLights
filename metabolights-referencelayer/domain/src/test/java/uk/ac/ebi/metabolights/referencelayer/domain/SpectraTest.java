/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/06/13 11:50
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package ac.ebi.metabolights.referencelayer.domain;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: conesa
 * Date: 25/04/2013
 * Time: 15:21
 */
public class SpectraTest {
    @Test
    public void testEquals() throws Exception {



        Spectra spectra = new Spectra("file", "name", Spectra.SpectraType.MS);
        assertTrue("Equals to itself expected to be true", spectra.equals(spectra));

        Spectra spectra2 = new Spectra("file", spectra.getName(), spectra.getSpectraType());
        assertTrue("Equals to other instance same values expected to be true", spectra.equals(spectra2));

        spectra2.setName("name2");
        assertFalse("Equals expected to be false", spectra.equals(spectra2));

        spectra2.setPathToJsonSpectra(new File("file2"));
        assertFalse("Equals expected to be false", spectra.equals(spectra2));

        spectra2.setName(spectra.getName());
        assertFalse("Equals expected to be false", spectra.equals(spectra2));

        spectra2.setSpectraType(Spectra.SpectraType.NMR);
        assertFalse("Equals expected to be false", spectra.equals(spectra2));

        spectra2.setPathToJsonSpectra(spectra.getPathToJsonSpectra());
        assertFalse("Equals expected to be false", spectra.equals(spectra2));






    }
}
