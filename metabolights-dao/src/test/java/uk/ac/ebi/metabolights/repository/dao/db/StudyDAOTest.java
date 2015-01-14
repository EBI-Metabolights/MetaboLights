/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-08
 * Modified by:   conesa
 *
 *
 * Copyright 2015 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.repository.dao.db;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.model.Study;

public class StudyDAOTest extends DBTestCaseBase{

	String[] expected_new = ("MTBLS1_OC_" + Study.StudyStatus.PENDING.ordinal()).split("_");
	String[] expected_updated = ("MTBLS1U_OCU_" + Study.StudyStatus.APPROVED.ordinal()).split("_");
	private enum EXPECTED {
		ACC,OBFUSCATIONCODE,STATUS
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(SQLQueryMapperTest.class.getClassLoader().getResourceAsStream("StudiesDAODataTest.xml"));
	}

	@Test
	public void testCRUD() throws DAOException {

		Study newStudy = fillStudyFromExpected(expected_new, null);
		
		StudyDBDAO studyDAO  = DAOFactory.getStudyDAO();

		// Save the new study
		studyDAO.save(newStudy);

		// Test id it's been populated
		Assert.assertNotNull("Study id shoud be populated", newStudy.getId());

		// Test find now.
		Study findByStudy = studyDAO.findById(newStudy.getId());

		// Test id it's been populated
		assertStudy(findByStudy, expected_new);

		// Test update...
		fillStudyFromExpected(expected_updated, findByStudy);

		// Save changes
		studyDAO.save(findByStudy);

		findByStudy = studyDAO.findById(findByStudy.getId());

		assertStudy(findByStudy, expected_updated);


		findByStudy = studyDAO.findByAcc(expected_updated[EXPECTED.ACC.ordinal()]);

		assertStudy(findByStudy, expected_updated);


		// Delete the study
		studyDAO.delete(findByStudy);

		// try to get it
		findByStudy = studyDAO.findById(findByStudy.getId());

		Assert.assertNull(findByStudy);

	}

	private Study fillStudyFromExpected(String[] values, Study study) {

		// Create a new study
		if (study ==null) study = new Study();

		//study.setStudyIdentifier(values[EXPECTED.USER_NAME.ordinal()]);
		study.setStudyIdentifier(values[EXPECTED.ACC.ordinal()]);
		study.setObfuscationCode(values[EXPECTED.OBFUSCATIONCODE.ordinal()]);

		int ordinal = Integer.parseInt(values[EXPECTED.STATUS.ordinal()]);
		study.setStudyStatus(Study.StudyStatus.values()[ordinal]);

		return study;
	}


	private void assertStudy(Study study, String[] expected){

		// Test id it's been populated
		Assert.assertEquals("Accesion test", expected[EXPECTED.ACC.ordinal()], study.getStudyIdentifier());
		Assert.assertEquals("Obfuscationcode test", expected[EXPECTED.OBFUSCATIONCODE.ordinal()], study.getObfuscationCode());
		Assert.assertEquals("status test", Integer.parseInt(expected[EXPECTED.STATUS.ordinal()]), study.getStudyStatus().ordinal());

	}

}