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

package uk.ac.ebi.metabolights.repository.security;

import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.hibernate.UserDAO;
import uk.ac.ebi.metabolights.repository.model.User;

/**
 * User: conesa
 * Date: 27/01/15
 * Time: 10:27
 */
public class SecurityService {

	private static final String masterToken = java.util.UUID.randomUUID().toString();
	private static UserDAO userDAO = new UserDAO();
	private static StudyDAO studyDAO = new StudyDAO();

	public static void userAccessingStudy(String studyIdentifier, String userToken) throws DAOException {

		// If the study is public
		if (studyDAO.isStudyPublic(studyIdentifier)) {
			return;
		}

		checkUserAccess(userToken, "User with token " + userToken + " is not authorised to access or study " + studyIdentifier + " does not exist.", studyIdentifier);

	}

	public static User userDeletingStudy(String studyIdentifier, String userToken) throws DAOException {

		// If the study is public
		if (studyDAO.isStudyPublic(studyIdentifier)) {
			throwSecurityException("Public studies can't be deleted and " + studyIdentifier + " is Public." );
		}

		// Get the user
		return  checkUserAccess(userToken, "User with token " + userToken + " is not authorised to delete the study " + studyIdentifier + ". Only ACTIVE curators and/or owners can do it.", studyIdentifier);


	}

	private static User checkUserAccess(String userToken, String exceptionMessage, String studyIdentifier) throws DAOException {

		// If userToken is null.
		if (userToken == null) {
			throwSecurityException("Anonymous user (null) are not authorised to perform this action. ");
		}

		User user = userDAO.findByToken(userToken);

		// If not null
		if (user!=null) {

			// Add status check: if active
			if (user.getStatus().equals(User.UserStatus.ACTIVE)) {

				// If it's a curator
				if (user.isCurator()) {
					return user;
				}

				// If no studyIdentifier passed
				if (studyIdentifier == null) {
					return user;

				} else if (user.doesUserOwnsTheStudy(studyIdentifier)) {
					return user;
				}

			}

		}

		throwSecurityException(exceptionMessage);

		return null;

	}

	private static User checkUserAccess(String userToken, String exceptionMessage) throws DAOException {
		return checkUserAccess(userToken, exceptionMessage, null);
	}

	private static void throwSecurityException(String message) throws DAOException {

		// Throw a security exception
		throw new DAOException(message);
	}


	public static User userCreatingStudy(String userToken) throws DAOException {

		return checkUserAccess(userToken, "User with token " + userToken + " is not authorised to create a study.");


	}

	public static User userUpdatingStudy(String studyIdentifier, String userToken) throws DAOException {

		// If the study is public
		if (studyDAO.isStudyPublic(studyIdentifier)) {
			throwSecurityException("Public studies can't be updated and " + studyIdentifier + " is Public." );
		}


		return checkUserAccess(userToken, "User with token " + userToken + " is not authorised to update the study " + studyIdentifier, studyIdentifier);
	}
}
