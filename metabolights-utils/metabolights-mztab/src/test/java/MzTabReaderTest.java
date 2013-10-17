/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 17/10/13 10:03
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

import org.isatools.isacreator.spreadsheet.model.TableReferenceObject;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.utils.mztab.*;
import uk.ac.ebi.pride.jmztab.MzTabParsingException;
import uk.ac.ebi.pride.jmztab.model.SmallMolecule;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class MzTabReaderTest {

    MzTabReader mzTabReader = new MzTabReader();
    CreateMetaboliteAssignment createMetaboliteAssignment = new CreateMetaboliteAssignment();
    ConvertToMzTab convertToMzTab = new ConvertToMzTab();
    MzTabFileWriter mzTabFileWriter = new MzTabFileWriter();
    ConfigurationReader configurationReader = new ConfigurationReader();

    private static String studyAccession = "MTBLS1";
    //private static String studyAccession = "MTBLS2";
    //private static String studyAccession = "MTBLS3";

    String MAFfileName = "m_test_"+studyAccession+"_maf.tsv";
    String MzTabFileName = studyAccession + ".mztab";
    private static String MAFfileLocation = MzTabReaderTest.class.getClassLoader().getResource("." + File.separator + studyAccession).getPath();

    String fullMAFfileLocation = MAFfileLocation + File.separator + MAFfileName;
    String fullMzTabFileLocation = MAFfileLocation + File.separator + MzTabFileName;

    @Test
    public void getMSTableReference(){
        TableReferenceObject tableReferenceObject = configurationReader.getMSConfig();
        assert tableReferenceObject != null;
    }

    @Test
    public void getNMRTableReference(){
        TableReferenceObject tableReferenceObject = configurationReader.getNMRConfig();
        assert tableReferenceObject != null;
    }

    @Test
    public void testWriteMzTab() throws MzTabParsingException {

        File mafFile = mzTabReader.readMAF(fullMAFfileLocation);
        assert mafFile != null;

        File mzTabFile = new File(fullMzTabFileLocation);
        assert mzTabFile != null;

        convertToMzTab.convertMAFToMzTab(mafFile.toString(), MAFfileLocation+mzTabFile.getName(), studyAccession);

    }

    @Test
    public void testMainWithAllParams(){
        ConvertToMzTab.main(new String[]{fullMAFfileLocation, fullMzTabFileLocation, studyAccession});
        File file = new File(fullMzTabFileLocation);
        assert file.exists();    //Did we create the file
    }

    @Test
    public void testGetSmallMoleculesInMzTabFike(){
        Collection<SmallMolecule> smallMolecules = mzTabReader.getSmallMolecules(fullMzTabFileLocation);
        for (SmallMolecule molecule : smallMolecules){
            assert molecule.getUnitId().equals(studyAccession);
        }

    }



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

    @Test
    public void testReadMAF(){
        File file = mzTabReader.readMAF(fullMAFfileLocation);
        assert file.exists();
    }

}
