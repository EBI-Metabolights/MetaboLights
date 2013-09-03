package uk.ac.ebi.metabolights.repository.dao.filesystem;

import org.junit.Test;
import uk.ac.ebi.metabolights.repository.model.Study;

/**
 * Created with IntelliJ IDEA.
 * User: tejasvi
 * Date: 02/09/13
 * Time: 14:38
 * To change this template use File | Settings | File Templates.
 */
public class StudyDAOTest {

    final String ISA_CONF_LOCATION = "C:\\Users\\tejasvi\\workspace\\MetaboLightsIDEA\\metabolights-utils\\metabolights-sampledb\\src\\test\\resources\\MetaboLightsConfig20121211";
    final String PUBLIC_STUDIES_LOCATION = "C:\\MTBL\\Pablo\\ftp\\public\\";


    @Test
    public void testGetStudy() throws Exception {



        StudyDAO studyDAO = new StudyDAO(ISA_CONF_LOCATION,PUBLIC_STUDIES_LOCATION,"");

        Study study = studyDAO.getStudy("MTBLS1");

    }
}
