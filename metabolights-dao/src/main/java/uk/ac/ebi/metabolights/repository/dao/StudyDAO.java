/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-27
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

package uk.ac.ebi.metabolights.repository.dao;

import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.security.SecurityService;

import java.io.File;
import java.util.List;

/**
 * User: conesa
 * Date: 27/01/15
 * Time: 08:33
 */
public class StudyDAO {

	private uk.ac.ebi.metabolights.repository.dao.hibernate.StudyDAO dbDAO = new uk.ac.ebi.metabolights.repository.dao.hibernate.StudyDAO();
	private uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO fsDAO;



	public StudyDAO(String isaTabRootConfigurationFolder, String publicFolder, String privateFolder){

		fsDAO = new uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO(isaTabRootConfigurationFolder, publicFolder, privateFolder);
		dbDAO = new uk.ac.ebi.metabolights.repository.dao.hibernate.StudyDAO();

	}

	public Study getStudy(String accession, String userToken, boolean includeMetabolites) throws DAOException {

		SecurityService.userAccessingStudy(accession, userToken);

		Study study = dbDAO.findByAccession(accession);

		return getStudyFromFileSystem(study,includeMetabolites);
	}


	public Study getStudy(String accession) throws DAOException {

		return getStudy(accession,"",false);
	}

	public Study getStudy(String accession, String userToken) throws DAOException {
		return getStudy(accession,userToken,false);
	}

	public Study getStudyByObfuscationCode(String obfuscationCode, boolean includeMetabolites) throws DAOException {

		Study study = dbDAO.findByObfuscationCode(obfuscationCode);

		return getStudyFromFileSystem(study, includeMetabolites);
	}


	private Study getStudyFromFileSystem(Study study, boolean includeMetabolites) throws DAOException {

		fsDAO.fillStudy(includeMetabolites, study);

		return study;

	}

	public Study getStudyByObfuscationCode(String obfuscationCode) throws DAOException {
		return getStudyByObfuscationCode(obfuscationCode,false);
	}

	public List<String> getList(String userToken) throws DAOException {
		return dbDAO.getStudyListForUser(userToken);
	}

	public void addStudy(File studyFolder, String userToken) {

		// We should move here all the itir stuff, done now in the webservice.

	}
}



