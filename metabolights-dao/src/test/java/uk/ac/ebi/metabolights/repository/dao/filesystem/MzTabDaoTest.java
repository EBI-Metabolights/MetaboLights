/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 23/09/13 15:16
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
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
