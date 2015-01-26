/*
* EBI MetaboLights - http://www.ebi.ac.uk/metabolights
* Cheminformatics and Metabolism group
*
* European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
*
* Last modified: 2015-Jan-21
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

package uk.ac.ebi.metabolights.repository.dao.hibernate;

import org.junit.Assert;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.StudyData;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.User;

import java.util.List;

public class StudyDAOTest extends HibernateTest{


	private static final String ACC = "ACC";

	@Test
	public void testConstructor() throws Exception {

		StudyDAO studyDAO = new StudyDAO();

		Assert.assertEquals("Class match test", StudyData.class, studyDAO.getDataModelClass());

	}

	@Test
	public void testCRUDStudyDAO() throws DAOException {

		StudyDAO studyDAO = new StudyDAO();

		Study newStudy = new Study();

		newStudy.setStudyIdentifier(ACC + System.currentTimeMillis());
		Assert.assertNotNull("Obfuscation code has a default value", newStudy.getObfuscationCode());


		// Add one user
		User newUser = UserDAOTest.getUser();

		// Add the user to the study
		newStudy.getUsers().add(newUser);
		studyDAO.save(newStudy);

		Assert.assertNotNull("Study id it's been populated" ,newStudy.getId());

		// Test study user association
		Assert.assertNotNull("User id it's been populated" ,newUser.getUserId());


		// Test find
		Study savedStudy =  studyDAO.findById(newStudy.getId());
		Assert.assertEquals("Test accession", newStudy.getStudyIdentifier(), savedStudy.getStudyIdentifier());
		Assert.assertEquals("Test user count", newStudy.getUsers().size(), savedStudy.getUsers().size());


		// Test some user properties
		User savedUser = savedStudy.getUsers().iterator().next();
		Assert.assertEquals("Test saved user values", newUser.getUserName(), savedUser.getUserName());



		// Test update
		savedStudy.setStudyIdentifier(ACC + System.currentTimeMillis());
		studyDAO.save(savedStudy);

		savedStudy = (Study) studyDAO.findById(newStudy.getId());
		Assert.assertNotSame("Test obfuscation code", newStudy.getObfuscationCode(), savedStudy.getObfuscationCode());

		// Now delete the user
		//studyDAO.delete(savedStudy);


	}
	@Test
	public void testFindAll() throws DAOException {

		StudyDAO studyDAO = new StudyDAO();

		List<Study> studies = studyDAO.findAll();

		logger.info(studies.size() + " studies found.");


	}
}
