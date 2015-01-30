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

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOTest;
import uk.ac.ebi.metabolights.repository.model.Field;
import uk.ac.ebi.metabolights.repository.model.Study;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StudyDAOTest extends DAOTest {


	private StudyDAO studyDAO;

	@Before
	public void initialiseTests() {


		studyDAO = new StudyDAO(configRoot,publicStudiesLocation,privateStudiesLocation);

	}

    @Test
    public void testGetStudy() throws Exception {


        Study study = studyDAO.getStudy("MTBLS1", true);
        assertEquals("MTBLS1 loaded?", study.getStudyIdentifier(),"MTBLS1");

		// Check organism is populated
		assertEquals("MTBLS1 organism size",1, study.getOrganism().size());
		assertEquals("MTBLS1 organism test","Homo sapiens (Human)", study.getOrganism().iterator().next().getOrganismName());
		assertEquals("MTBLS1 organism part test","urine", study.getOrganism().iterator().next().getOrganismPart());

		// Number of fields are correct
		assertEquals("MTBLS1 assay fields count", 31, study.getAssays().get(0).getAssayTable().getFields().values().size());

		Field unitField = study.getAssays().get(0).getAssayTable().getFields().get("9~unit");
		assertNotNull("Unit field for temperature returned", unitField);

		unitField = study.getAssays().get(0).getAssayTable().getFields().get("18~unit");
		assertNotNull("Unit field for tesla returned", unitField);


		Field organism = study.getSampleTable().getFields().get("1~characteristics[organism]");
		assertNotNull("Organism field count test", organism);


		assertEquals("Organism field type test", "Characteristics", organism.getFieldType());
		assertEquals("Organism field clean header test", "Organism", organism.getCleanHeader());
		assertEquals("Organism field description test", "Organism description", organism.getDescription());




		// Check metabolites assignment information is present
		assertNotNull("Check existence of metabolites object", study.getAssays().get(0).getMetaboliteAssignment());
		assertNotNull("Check existence of metabolite filename is filled", study.getAssays().get(0).getMetaboliteAssignment().getMetaboliteAssignmentFileName());
		assertNotNull("Check existence of metabolite lines", study.getAssays().get(0).getMetaboliteAssignment().getMetaboliteAssignmentLines());
		// 214 lines +  single pipes in 4 of them + 1 duuble pipe in 1 line = 220.
		assertEquals("Check MTBSL1 metabolites lines number", 220,study.getAssays().get(0).getMetaboliteAssignment().getMetaboliteAssignmentLines().size());


        study = studyDAO.getStudy("MTBLS2", false);
        assertEquals("MTBLS2 loaded?", study.getStudyIdentifier(),"MTBLS2");
		assertEquals("MTBLS2 organism size",1, study.getOrganism().size());
		assertEquals("MTBLS2 organism test","Arabidopsis thaliana (thale cress)", study.getOrganism().iterator().next().getOrganismName());
		assertEquals("MTBLS2 organism part test","rosette leaf", study.getOrganism().iterator().next().getOrganismPart());


        study = studyDAO.getStudy("MTBLS3", true);
        assertEquals("MTBLS3 loaded?", study.getStudyIdentifier(),"MTBLS3");
		assertEquals("MTBLS3 organism size",2, study.getOrganism().size());


        study = studyDAO.getStudy("MTBLS4", false);
        assertEquals("MTBLS4 loaded?", study.getStudyIdentifier(),"MTBLS4");
		assertEquals("MTBLS4 organism size",1, study.getOrganism().size());
		assertEquals("MTBLS4 organism test","Homo sapiens (Human)", study.getOrganism().iterator().next().getOrganismName());
		assertEquals("MTBLS4 organism part test","blood plasma", study.getOrganism().iterator().next().getOrganismPart());

    }

	@Test
	public void testTechnologyNormalization(){

		Study study = studyDAO.getStudy("MTBLS114", false);

		// Check technology is clean of ontologies for a ontologized value
		assertEquals("MTBLS114 technology is 'clean'","NMR spectroscopy", study.getAssays().get(0).getTechnology());


		study = studyDAO.getStudy("MTBLS1", false);

		// Check technology is still clean , not ontologized value.
		assertEquals("MTBLS1 technology is 'clean'","NMR spectroscopy", study.getAssays().get(0).getTechnology());


	}

}
