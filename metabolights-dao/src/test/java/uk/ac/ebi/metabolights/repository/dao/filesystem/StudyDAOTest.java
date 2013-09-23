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
import uk.ac.ebi.metabolights.repository.model.Study;

import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: tejasvi
 * Date: 02/09/13
 * Time: 14:38
 * To change this template use File | Settings | File Templates.
 */
public class StudyDAOTest {

    final String ISA_CONF_LOCATION = "configRoot";

    final String PUBLIC_STUDIES_LOCATION = "studies/public/";
    final String PRIVATE_STUDIES_LOCATION = "studies/private/";

    @Test
    public void testGetStudy() throws Exception {

        URL configRootUrl = StudyDAOTest.class.getClassLoader().getResource(ISA_CONF_LOCATION);
        URL publicStudiesLocationUrl = StudyDAOTest.class.getClassLoader().getResource(PUBLIC_STUDIES_LOCATION) ;
        URL privateStudiesLocationUrl = StudyDAOTest.class.getClassLoader().getResource(PRIVATE_STUDIES_LOCATION) ;

        String configRoot = configRootUrl.getFile();
        String publicStudiesLocation = publicStudiesLocationUrl.getFile();
        String privateStudiesLocation = privateStudiesLocationUrl.getFile();

        StudyDAO studyDAO = new StudyDAO(configRoot,publicStudiesLocation,privateStudiesLocation);

        Study study = studyDAO.getStudy("MTBLS1");
        assertEquals("MTBLS1 loaded?", study.getStudyIdentifier(),"MTBLS1");

        study = studyDAO.getStudy("MTBLS2");
        assertEquals("MTBLS2 loaded?", study.getStudyIdentifier(),"MTBLS2");

        study = studyDAO.getStudy("MTBLS3");
        assertEquals("MTBLS3 loaded?", study.getStudyIdentifier(),"MTBLS3");


        study = studyDAO.getStudy("MTBLS4");
        assertEquals("MTBLS4 loaded?", study.getStudyIdentifier(),"MTBLS4");

    }

}
