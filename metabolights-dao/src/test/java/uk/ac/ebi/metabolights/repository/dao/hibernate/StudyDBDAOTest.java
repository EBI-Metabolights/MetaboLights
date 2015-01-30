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
import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.security.SecurityService;

import java.util.List;

public class StudyDBDAOTest extends DAOTest {


	private static final String ACC = "ACC";

	@Test
	public void testConstructor() throws Exception {

		StudyDAO studyDBDAO = new StudyDAO();

		Assert.assertEquals("Class match test", StudyData.class, studyDBDAO.getDataModelClass());

	}

	@Test
	public void testCRUDStudyDAO() throws DAOException {

		StudyDAO studyDBDAO = new StudyDAO();

		Study newStudy = new Study();

		newStudy.setStudyIdentifier(ACC + System.currentTimeMillis());
		Assert.assertNotNull("Obfuscation code has a default value", newStudy.getObfuscationCode());

		// Add one user
		User newUser = UserDAOTest.getUser();

		// Add the user to the study
		newStudy.getUsers().add(newUser);
		studyDBDAO.save(newStudy);

		Assert.assertNotNull("Study id it's been populated" ,newStudy.getId());

		// Test study user association
		Assert.assertNotNull("User id it's been populated" ,newUser.getUserId());


		// Test find
		Study savedStudy =  studyDBDAO.findById(newStudy.getId());
		Assert.assertEquals("Test accession", newStudy.getStudyIdentifier(), savedStudy.getStudyIdentifier());
		Assert.assertEquals("Test user count", newStudy.getUsers().size(), savedStudy.getUsers().size());

		savedStudy =  studyDBDAO.findByAccession(newStudy.getStudyIdentifier());
		Assert.assertEquals("Test accession", newStudy.getStudyIdentifier(), savedStudy.getStudyIdentifier());
		Assert.assertEquals("Test user count", newStudy.getUsers().size(), savedStudy.getUsers().size());

		savedStudy =  studyDBDAO.findByObfuscationCode(newStudy.getObfuscationCode());
		Assert.assertEquals("Test accession", newStudy.getStudyIdentifier(), savedStudy.getStudyIdentifier());
		Assert.assertEquals("Test user count", newStudy.getUsers().size(), savedStudy.getUsers().size());



		// Try isStudyPublic
		Assert.assertEquals("Test isStudyPublic",false, studyDBDAO.isStudyPublic(newStudy.getStudyIdentifier()));

		// Try Security service
		SecurityService.userAccessingStudy(savedStudy.getStudyIdentifier(),newUser.getApiToken());

		// Add a some users
		UserDAO userDAO = new UserDAO();
		User curator = UserDAOTest.getUser();
		curator.setRole(AppRole.ROLE_SUPER_USER);
		userDAO.save(curator);

		// Create another user, not owner of the study.
		User notOwner = UserDAOTest.getUser();
		userDAO.save(notOwner);

		// Test curator can access the study
		SecurityService.userAccessingStudy(savedStudy.getStudyIdentifier(),curator.getApiToken());

		// Test another user can't
		try{

			SecurityService.userAccessingStudy(savedStudy.getStudyIdentifier(),notOwner.getApiToken());

			throw new AssertionError("a not owner user should trigger an exception when checking user access to a study");

		} catch (SecurityException e){
			logger.info("Expected exception: user, not an owner should throw an exception");
		}


		// Test a non existing user
		try{

			SecurityService.userAccessingStudy(savedStudy.getStudyIdentifier(),"foofoo");

			throw new AssertionError("a non existing user should trigger an exception when checking user access to a study");

		} catch (SecurityException e){
			logger.info("Expected exception: user, non existing user should throw an exception");
		}




		// Test some user properties
		User savedUser = savedStudy.getUsers().iterator().next();
		Assert.assertEquals("Test saved user values", newUser.getUserName(), savedUser.getUserName());

		// Test update
		savedStudy.setStudyIdentifier(ACC + System.currentTimeMillis());
		studyDBDAO.save(savedStudy);

		savedStudy = (Study) studyDBDAO.findById(newStudy.getId());
		Assert.assertNotSame("Test obfuscation code", newStudy.getObfuscationCode(), savedStudy.getObfuscationCode());

		// Now delete the user
		//studyDAO.delete(savedStudy);


	}
	@Test
	public void testFindAll() throws DAOException {

		StudyDAO studyDBDAO = new StudyDAO();

		List<Study> studies = studyDBDAO.findAll();

		logger.info(studies.size() + " studies found.");


	}
}
