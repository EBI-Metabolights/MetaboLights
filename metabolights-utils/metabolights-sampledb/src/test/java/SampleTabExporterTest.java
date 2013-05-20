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

    private static String isatabDirectory = "/Users/kenneth/dev/ISAtab/source/ISAcreator-1.7/isatab files/MTBLS1_V2";
    private static String configDirectory = "/Users/kenneth/dev/metabolights/metabolights-webapps/src/main/resources/isa_configurator"; //Current config
    private static String sampleTabFile = "/Users/kenneth/dev/ISAtab/source/ISAcreator-1.7/isatab files/MTBLS1_V2/sampleTab.txt";

    ISATabReader isaTabReader = new ISATabReader();
    SampleTabExporter sampleTabExporter = new SampleTabExporter();
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
