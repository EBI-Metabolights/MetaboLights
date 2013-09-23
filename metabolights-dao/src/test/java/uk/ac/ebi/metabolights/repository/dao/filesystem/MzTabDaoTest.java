/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 23/09/13 14:01
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.repository.dao.filesystem;

import org.junit.Test;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;

import java.net.URL;

import static de.regnis.q.sequence.core.QSequenceAssert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: kenneth
 * Date: 23/09/2013
 * Time: 13:43
 * To change this template use File | Settings | File Templates.
 */
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
        String testFile = publicStudiesLocationUrl.getPath() + "MTBLS1/m_live_mtbl1_rms_metabolite profiling_NMR spectroscopy_v2_maf.tsv";

        MzTabDAO mzTabDAO = new MzTabDAO();
        MetaboliteAssignment metaboliteAssignment = mzTabDAO.mapMetaboliteAssignmentFile(testFile);

        assertNotNull(metaboliteAssignment.getMetaboliteAssignmentLines());

    }
}
