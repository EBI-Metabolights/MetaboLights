/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 02/10/13 14:17
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

import org.junit.Test;
import uk.ac.ebi.metabolights.utils.mztab.MzTabConverter;
import uk.ac.ebi.metabolights.utils.mztab.MzTabReader;
import uk.ac.ebi.pride.jmztab.MzTabParsingException;

import java.io.File;

public class MzTabWriterTest {

    MzTabConverter mzTabConverter = new MzTabConverter();
    MzTabReader mzTabReader = new MzTabReader();

    //MTBLS1
    //private static String studyAccession = "MTBLS1";
    //String MAFfileName = "m_live_mtbl1_rms_metabolite profiling_NMR spectroscopy_v2_maf.tsv";

    //MTBLS2
    //private static String studyAccession = "MTBLS2";
    //String MAFfileName = "a_mtbl2_metabolite profiling_mass spectrometry_maf.csv";

    //MTBLS3
    private static String studyAccession = "MTBLS3";
    String MAFfileName = "m_live_mtbl3_metabolite profiling_mass spectrometry_v2_maf.tsv";


    String MzTabFileName = studyAccession + ".mztab";
    private static String MAFfileLocation = MzTabWriterTest.class.getClassLoader().getResource("." + File.separator + studyAccession).getPath();

    String fullMAFfileLocation = MAFfileLocation + File.separator + MAFfileName;
    String fullMzTabFileLocation = MAFfileLocation + File.separator + MzTabFileName;

    @Test
    public void testWriteMzTab() throws MzTabParsingException {

        File mafFile = mzTabReader.readMAF(fullMAFfileLocation);
        assert mafFile != null;

        File mzTabFile = new File(fullMzTabFileLocation);
        assert mzTabFile != null;

        mzTabConverter.convertMAFToMzTab(mafFile.toString(), MAFfileLocation+mzTabFile.getName(), studyAccession);

    }

    @Test
    public void testMainWithAllParams(){
        mzTabConverter.main(new String[]{fullMAFfileLocation, fullMzTabFileLocation, studyAccession});
    }

}
