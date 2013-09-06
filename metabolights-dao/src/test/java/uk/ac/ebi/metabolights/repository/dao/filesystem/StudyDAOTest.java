package uk.ac.ebi.metabolights.repository.dao.filesystem;

import org.junit.Test;
import static org.junit.Assert.*;

import uk.ac.ebi.metabolights.repository.model.Study;


import java.net.URL;

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


    @Test
    public void testGetStudy() throws Exception {


        URL configRootUrl = StudyDAOTest.class.getClassLoader().getResource(ISA_CONF_LOCATION);

        URL publicStudiesLocationUrl = StudyDAOTest.class.getClassLoader().getResource(PUBLIC_STUDIES_LOCATION) ;


        String configRoot = configRootUrl.getFile();

        String publicStudiesLocation = publicStudiesLocationUrl.getFile();


        StudyDAO studyDAO = new StudyDAO(configRoot,publicStudiesLocation,"");

        Study study = studyDAO.getStudy("MTBLS1");


        assertEquals("MTBLS1 loaded?", study.getStudyIdentifier(),"MTBLS1");

        study = studyDAO.getStudy("MTBLS2");

        assertEquals("MTBLS2 loaded?", study.getStudyIdentifier(),"MTBLS2");


    }
}
