/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 6/6/14 2:55 PM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

import org.isatools.isacreator.model.Contact;
import org.isatools.isacreator.model.Investigation;
import org.isatools.isacreator.model.Study;
import org.junit.Test;
import uk.ac.ebi.arrayexpress2.sampletab.parser.SampleTabParser;
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


    private Investigation getInvestigation(){
        investigation = isaTabReader.getInvestigation(configDirectory, isatabDirectory);
        return investigation;
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
