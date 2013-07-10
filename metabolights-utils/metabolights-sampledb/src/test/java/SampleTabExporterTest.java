import org.isatools.isacreator.model.Investigation;
import org.junit.Test;
import uk.ac.ebi.metabolights.utils.sampletab.ISATabReader;
import uk.ac.ebi.metabolights.utils.sampletab.SampleTabExporter;

import static de.regnis.q.sequence.core.QSequenceAssert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 24/04/2013
 * Time: 10:08
 */
public class SampleTabExporterTest {

    private static String isatabDirectory = ISATabReaderTest.class.getClassLoader().getResource("./MTBLS1").getPath();
    private static String configDirectory = ISATabReaderTest.class.getClassLoader().getResource("./MetaboLightsConfig20121211").getPath();
    private static String sampleTabFile = isatabDirectory + "/sampleTab.tsv";

    ISATabReader isaTabReader = new ISATabReader();
    SampleTabExporter sampleTabExporter = new SampleTabExporter();
    OLSQuery ols = new OLSQuery();
    Investigation investigation;


    private Investigation getInvestigation(){
        investigation = isaTabReader.getInvestigation(configDirectory, isatabDirectory);
        return investigation;
    }

    @Test
    public void testGetInvestigation(){

        getInvestigation();
        assertNotNull(investigation);

    }

    @Test
    public void testWriteSampleTab(){
        sampleTabExporter.exportSampleFile(sampleTabFile, configDirectory, isatabDirectory);
    }




}
