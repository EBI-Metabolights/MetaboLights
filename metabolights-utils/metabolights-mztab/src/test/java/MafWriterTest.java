/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 03/10/13 15:15
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

import org.junit.Test;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.utils.mztab.ConfigurationReader;
import uk.ac.ebi.metabolights.utils.mztab.CreateMetaboliteAssignment;
import uk.ac.ebi.metabolights.utils.mztab.MzTabFileWriter;
import uk.ac.ebi.pride.jmztab.MzTabParsingException;

import java.io.File;
import java.io.IOException;

public class MafWriterTest {

    CreateMetaboliteAssignment createMetaboliteAssignment = new CreateMetaboliteAssignment();

    private static String studyAccession = "MTBLS1";
    //private static String studyAccession = "MTBLS2";
    //private static String studyAccession = "MTBLS3";

    private static String MAFfileName = "m_" + studyAccession + "_NEW_maf.tsv";
    private static String MzTabFileName = studyAccession + ".mztab";
    private static String MAFfileLocation = MafWriterTest.class.getClassLoader().getResource("." + File.separator + studyAccession).getPath();

    String fullMAFfileLocation = MAFfileLocation + File.separator + MAFfileName;
    String fullMzTabFileLocation = MAFfileLocation + File.separator + MzTabFileName;

    ConfigurationReader configurationReader = new ConfigurationReader();
    MzTabFileWriter mzTabFileWriter = new MzTabFileWriter();

    @Test
    public void testCreateMetaboliteAssignment(){
        try {
            MetaboliteAssignment metaboliteAssignment = createMetaboliteAssignment.createMetaboliteAssignment(fullMzTabFileLocation, fullMAFfileLocation, studyAccession);
            assert metaboliteAssignment != null;

        } catch (MzTabParsingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetColumnHeader(){
        String headers = mzTabFileWriter.getColumnHeaderNames(configurationReader.MS);
        assert headers != null;
    }

    @Test
    public void testWriteMafHeaders(){

        try {
            mzTabFileWriter.writeMAF(fullMAFfileLocation,mzTabFileWriter.getColumnHeaderNames(configurationReader.MS));
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(fullMAFfileLocation);
        assert file.exists();    //Did we create the file?

    }

}
