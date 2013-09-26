/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 25/09/13 15:59
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

import org.junit.Test;
import uk.ac.ebi.metabolights.utils.mztab.MzTabWriter;
import uk.ac.ebi.pride.jmztab.MzTabParsingException;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 10/04/2013
 * Time: 12:30
 */
public class MzTabWriterTest {

    MzTabWriter mzTabWriter = new MzTabWriter();

    private static String MAFfileLocation = MzTabWriterTest.class.getClassLoader().getResource("." + File.separator + "MTBLS1").getPath();
    String MAFfileName = "m_live_mtbl1_rms_metabolite profiling_NMR spectroscopy_v2_maf.tsv";
    String MzTabFileName = "m_live_mtbl1_rms_metabolite profiling_NMR spectroscopy_v2_maf.mztab";
    String fullMAFfileLocation = MAFfileLocation + File.separator + MAFfileName;
    String fullMzTabFileLocation = MAFfileLocation + File.separator + MzTabFileName;

    @Test
    public void testReadMAF(){

        File file = mzTabWriter.readMAF(fullMAFfileLocation);
        assert file != null;

    }

    @Test
    public void testWriteMzTab() throws MzTabParsingException {

        File mafFile = mzTabWriter.readMAF(fullMAFfileLocation);
        assert mafFile != null;

        File mzTabFile = new File(fullMzTabFileLocation);
        assert mzTabFile != null;

        mzTabWriter.convertMAFToMzTab(mafFile.toString(), MAFfileLocation+mzTabFile.getName(), "MTBLS1");

    }

    @Test
    public void testMainWithAllParams(){
        mzTabWriter.main(new String[]{fullMAFfileLocation, fullMzTabFileLocation, "MTBLS1"});
    }


}
