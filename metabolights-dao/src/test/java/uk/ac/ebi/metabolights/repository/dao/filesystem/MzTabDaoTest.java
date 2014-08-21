/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 10/16/13 11:03 AM
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

package uk.ac.ebi.metabolights.repository.dao.filesystem;

import org.junit.Test;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;

import java.net.URL;

import static de.regnis.q.sequence.core.QSequenceAssert.assertNotNull;

public class MzTabDaoTest {

    final String ISA_CONF_LOCATION = "configRoot";

    final String PUBLIC_STUDIES_LOCATION = "studies/public/";
    final String PRIVATE_STUDIES_LOCATION = "studies/private/";


    @Test
    public void testGetMetaboliteAssigment() throws Exception {

        URL configRootUrl = StudyDAOTest.class.getClassLoader().getResource(ISA_CONF_LOCATION),
            publicStudiesLocationUrl = StudyDAOTest.class.getClassLoader().getResource(PUBLIC_STUDIES_LOCATION),
            privateStudiesLocationUrl = StudyDAOTest.class.getClassLoader().getResource(PRIVATE_STUDIES_LOCATION);

        String configRoot = configRootUrl.getFile();
        String publicStudiesLocation = publicStudiesLocationUrl.getFile();
        String privateStudiesLocation = privateStudiesLocationUrl.getFile();
        String mtbls1 = publicStudiesLocation + "MTBLS1/m_live_mtbl1_rms_metabolite profiling_NMR spectroscopy_v2_maf.tsv";
        String mtbls2 = publicStudiesLocation + "MTBLS2/a_mtbl2_metabolite profiling_mass spectrometry_maf.csv";
        String mtbls3 = privateStudiesLocation + "MTBLS3/m_live_mtbl3_metabolite profiling_mass spectrometry_v2_maf.tsv";
        String mtbls4 = privateStudiesLocation + "MTBLS4/m_live_mtbl5_metabolite profiling_mass spectrometry_v2_maf.tsv";

        MzTabDAO mzTabDAO = new MzTabDAO();
        MetaboliteAssignment metaboliteAssignment1 = mzTabDAO.mapMetaboliteAssignmentFile(mtbls1);
        assertNotNull(metaboliteAssignment1.getMetaboliteAssignmentLines());

        MetaboliteAssignment metaboliteAssignment2 = mzTabDAO.mapMetaboliteAssignmentFile(mtbls2);
        assertNotNull(metaboliteAssignment2.getMetaboliteAssignmentLines());

        MetaboliteAssignment metaboliteAssignment3 = mzTabDAO.mapMetaboliteAssignmentFile(mtbls3);
        assertNotNull(metaboliteAssignment3.getMetaboliteAssignmentLines());

        MetaboliteAssignment metaboliteAssignment4 = mzTabDAO.mapMetaboliteAssignmentFile(mtbls4);
        assertNotNull(metaboliteAssignment4.getMetaboliteAssignmentLines());

    }
}
