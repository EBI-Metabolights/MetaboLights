/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 10/10/13 15:24
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

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

    private static String isatabDirectory = SampleTabExporterTest.class.getClassLoader().getResource("./MTBLS31").getPath();
    //private static String configDirectory = SampleTabExporterTest.class.getClassLoader().getResource(".").getPath();
    private static String configDirectory = "/nfs/public/rw/homes/tc_cm01/metabolights/dev/isatab/configurations";
    private static String sampleTabFile = isatabDirectory + "/sampleTab.tsv";

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
        sampleTabExporter.exportSampleFile(configDirectory, isatabDirectory, sampleTabFile);
    }


}
