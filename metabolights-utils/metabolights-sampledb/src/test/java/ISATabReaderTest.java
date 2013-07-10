import org.isatools.isacreator.io.importisa.ISAtabFilesImporter;
import org.isatools.isacreator.model.Contact;
import org.isatools.isacreator.model.Investigation;
import org.isatools.isacreator.model.Study;
import org.junit.Test;
import uk.ac.ebi.arrayexpress2.sampletab.parser.SampleTabParser;
import uk.ac.ebi.metabolights.utils.sampletab.ISATabReader;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static de.regnis.q.sequence.core.QSequenceAssert.assertNotNull;
import static de.regnis.q.sequence.core.QSequenceAssert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 24/04/2013
 * Time: 10:09
 */
public class ISATabReaderTest {

    private SampleTabParser parser = new SampleTabParser();
    private ISATabReader isaTabReader = new ISATabReader();
    Investigation investigation;
    private static String isatabDirectory = ISATabReaderTest.class.getClassLoader().getResource("./MTBLS1").getPath();
    private static String configDirectory = ISATabReaderTest.class.getClassLoader().getResource("./MetaboLightsConfig20121211").getPath();

    private Investigation getInvestigation(){
        investigation = isaTabReader.getInvestigation(configDirectory, isatabDirectory);
        return investigation;
    }

    @Test
    public void testCheckISAtabFiles(){
        Boolean isaFound = isaTabReader.validateISAtabFiles(configDirectory, isatabDirectory);
        assertTrue(isaFound);
    }

    @Test
    public void testISAtabFilesImporter(){
        ISAtabFilesImporter isatabFilesImporter;
        isatabFilesImporter = isaTabReader.getIsatabFilesImporter(configDirectory);
        assertNotNull(isatabFilesImporter);
    }

    @Test
    public void testGetInvestigation(){
        Boolean isaFound = isaTabReader.validateISAtabFiles(configDirectory, isatabDirectory);

        if (isaFound) {
            Investigation investigation = isaTabReader.getInvestigation();
            assertNotNull(investigation);
        }
    }

    @Test
    public void testGetContactsFromInvestigation(){

        Map<String, Study> studyMap = getInvestigation().getStudies();
        assertNotNull(studyMap);

        for (Map.Entry<String, Study> entry : studyMap.entrySet())
        {
            Study study = entry.getValue();
            assertNotNull(study);

            List<Contact> contactsList = study.getContacts();
            assertNotNull(contactsList);

            if (!contactsList.isEmpty()){
                Contact contact = contactsList.get(0);
                assertNotNull(contact);
            }
        }



    }


}
