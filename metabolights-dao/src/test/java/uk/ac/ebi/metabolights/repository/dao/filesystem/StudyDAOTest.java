/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 10/21/13 2:35 PM
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
import uk.ac.ebi.metabolights.repository.model.Study;

import java.net.URL;

import static org.junit.Assert.assertEquals;

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

        Study study = studyDAO.getStudy("MTBLS1", true);
        assertEquals("MTBLS1 loaded?", study.getStudyIdentifier(),"MTBLS1");

        study = studyDAO.getStudy("MTBLS2", false);                               //TODO, not standard column order per the config, so have to fix the code
        assertEquals("MTBLS2 loaded?", study.getStudyIdentifier(),"MTBLS2");

        study = studyDAO.getStudy("MTBLS3", true);
        assertEquals("MTBLS3 loaded?", study.getStudyIdentifier(),"MTBLS3");


        study = studyDAO.getStudy("MTBLS4", false);
        assertEquals("MTBLS4 loaded?", study.getStudyIdentifier(),"MTBLS4");

    }

}
