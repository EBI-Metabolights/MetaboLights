/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 12/5/13 10:41 AM
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

import junit.framework.TestCase;
import org.isatools.isacreator.spreadsheet.model.TableReferenceObject;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.utils.mztab.*;

import java.io.File;

public class MzTabReaderTest extends TestCase {

    MzTabReader mzTabReader = new MzTabReader();
    CreateMetaboliteAssignment createMetaboliteAssignment = new CreateMetaboliteAssignment();

    ConvertToMzTab convertToMzTab = new ConvertToMzTab();
    MzTabFileWriter mzTabFileWriter = new MzTabFileWriter();
    ConfigurationReader configurationReader = new ConfigurationReader();

    //private static String studyAccession = "MTBLS1";
    String studyAccession = "MTBLS2";
    //private static String studyAccession = "MTBLS3";

    String[] studyAccessionList = new String[]{"MTBLS1", "MTBLS2", "MTBLS3"};

    String studyDirectory = "", configDirectory = "";

    @Before
    public void runBeforeEveryTest() {
        studyDirectory = MzTabReaderTest.class.getClassLoader().getResource(".").getPath();
        configDirectory = "/nfs/public/rw/homes/tc_cm01/metabolights/dev/isatab/configurations";
    }

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
    public void testGetColumnHeader(){
        String headers = mzTabFileWriter.getColumnHeaderNames(configurationReader.MS);
        assert headers != null;
    }


    @Test
    public void testWriteMzTab() throws IsaTabException, DAOException {

        File studyDir = new File(studyDirectory);
        assert studyDir.isDirectory();

        File configDir = new File(configDirectory);
        assert configDir.isDirectory();

        for (String acc: studyAccessionList){
            convertToMzTab.convertMAFToMzTab(studyDirectory, acc, configDirectory);
        }

        //convertToMzTab.convertMAFToMzTab(studyDirectory, studyAccession, configDirectory);

    }

    @Test
    public void testMainWithAllParams(){
        ConvertToMzTab.main(new String[]{studyDirectory, configDirectory, studyAccession});
    }


}
