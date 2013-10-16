/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 11/10/13 12:01
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

import org.junit.Test;
import uk.ac.ebi.metabolights.utils.mztab.MzTabReader;
import uk.ac.ebi.pride.jmztab.MzTabFile;
import uk.ac.ebi.pride.jmztab.model.SmallMolecule;

import java.io.File;
import java.util.Collection;

public class MzTabReaderTest {

    MzTabReader mzTabReader = new MzTabReader();

    private static String studyAccession = "MTBLS1";
    //private static String studyAccession = "MTBLS2";
    //private static String studyAccession = "MTBLS3";

    String MAFfileName = "m_test_"+studyAccession+"_maf.tsv";
    String MzTabFileName = studyAccession + ".mztab";
    private static String MAFfileLocation = MzTabReaderTest.class.getClassLoader().getResource("." + File.separator + studyAccession).getPath();

    String fullMAFfileLocation = MAFfileLocation + File.separator + MAFfileName;
    String fullMzTabFileLocation = MAFfileLocation + File.separator + MzTabFileName;


    @Test
    public void testReadMAF(){
        File file = mzTabReader.readMAF(fullMAFfileLocation);
        assert file.exists();
    }

    @Test
    public void testReadMzTabFile(){
        MzTabFile mzTabFile = mzTabReader.readMzTab(fullMzTabFileLocation);
        assert mzTabFile != null;
    }

    @Test
    public void testGetSmallMoleculesInMzTabFike(){
        Collection<SmallMolecule> smallMolecules = mzTabReader.getSmallMolecules(fullMzTabFileLocation);
        for (SmallMolecule molecule : smallMolecules){
            assert molecule.getUnitId().equals(studyAccession);
        }

    }

}
