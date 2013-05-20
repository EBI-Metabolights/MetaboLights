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

//    String MAFfileLocation = "/Users/kenneth/Desktop/ISAcreatorMetaboLights_1.6/isatab files/MTBLS1_V2/";
//    String MAFfileName = "m_live_mtbl1_rms_metabolite profiling_NMR spectroscopy_v2_maf.tsv";
//    String MzTabFileName = "m_live_mtbl1_rms_metabolite profiling_NMR spectroscopy_v2_maf.mztab";

    String MAFfileLocation = "/Users/kenneth/Desktop/ISAcreatorMetaboLights_1.6/isatab files/MTBLS2_V2/";
    String MAFfileName = "m_mtbl2_metabolite profiling_mass spectrometry_v2_maf.tsv";
    String MzTabFileName = "m_mtbl2_metabolite profiling_mass spectrometry_v2_maf.mztab";

    @Test
    public void testReadMAF(){

        File file = mzTabWriter.readMAF(MAFfileLocation+MAFfileName);
        assert file != null;

    }

    @Test
    public void testWriteMzTab() throws MzTabParsingException {

        File mafFile = mzTabWriter.readMAF(MAFfileLocation+MAFfileName);
        assert mafFile != null;

        File mzTabFile = new File(MAFfileLocation+MzTabFileName);
        assert mzTabFile != null;

        mzTabWriter.convertMAFToMZTab(mafFile, MAFfileLocation+mzTabFile.getName());


    }

    @Test
    public void testMainWithAllParams(){
        mzTabWriter.main(new String[]{MAFfileLocation+MAFfileName, MAFfileLocation+MzTabFileName});
    }

}
