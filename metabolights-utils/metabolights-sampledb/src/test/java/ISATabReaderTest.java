/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 6/10/14 11:57 AM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.isatools.isacreator.model.Contact;
import org.isatools.isacreator.model.Investigation;
import org.isatools.isacreator.model.Study;
import org.junit.Test;
import uk.ac.ebi.arrayexpress2.sampletab.parser.SampleTabParser;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.utils.sampletab.ISATabReader;

import java.util.List;
import java.util.Map;

import static de.regnis.q.sequence.core.QSequenceAssert.assertNotNull;

public class ISATabReaderTest {

    private SampleTabParser parser = new SampleTabParser();
    private ISATabReader isaTabReader = new ISATabReader();
    Investigation investigation;
    public static String studyAcc = "MTBLS1";
    private static String isatabDirectory = ISATabReaderTest.class.getClassLoader().getResource("./"+studyAcc).getPath();
    //private static String configDirectory = ISATabReaderTest.class.getClassLoader().getResource(".").getPath();
    private static String configDirectory = "/nfs/public/rw/homes/tc_cm01/metabolights/dev/isatab/configurations";


    private Investigation getInvestigation() throws IsaTabException {
        investigation = isaTabReader.getInvestigation(configDirectory, isatabDirectory);
        return investigation;
    }


    @Test
    public void testGetContactsFromInvestigation() throws IsaTabException {

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
