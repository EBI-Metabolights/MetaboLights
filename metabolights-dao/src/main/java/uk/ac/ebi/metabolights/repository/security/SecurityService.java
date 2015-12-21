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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.hibernate.UserDAO;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
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

	private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);


	public static void userAccessingStudy(String studyIdentifier, String userToken) throws DAOException {

		// If the study is public
		if (studyDAO.isStudyPublic(studyIdentifier)) {
			return;
		}

		checkUserAccess(userToken, "access " + studyIdentifier, studyIdentifier);

	}

	public static User userDeletingStudy(String studyIdentifier, String userToken) throws DAOException {

//		// If the study is public
//		if (studyDAO.isStudyPublic(studyIdentifier)) {
//			throwSecurityException("Public studies can't be deleted and " + studyIdentifier + " is Public." );
//		}
//
//		// Get the user
//		return  checkUserAccess(userToken, "User with token " + userToken + " is not authorised to delete the study " + studyIdentifier + ". Only ACTIVE curators and/or owners can do it.", studyIdentifier);

		User user = tokenToUser(userToken,"delete " + studyIdentifier);

		// if user is a curator
		if (user.isCurator()){
			return  user;
		}

		throwSecurityException(user.getFullName() + " is not authorised to delete. Only curators can do it. ");

		return null;

	}

	public static User userOverridingStudyValidations(String studyIdentifier, String userToken) throws DAOException {

		User user = tokenToUser(userToken,"Override Validations " + studyIdentifier);

		// if user is a curator
		if (user.isCurator()){
			return  user;
		}

		throwSecurityException(user.getFullName() + " is not authorised to override validations. Only curators can do it. ");

		return null;

	}



	/**
	 * Lookup for an user by its token. If token null or token doesn't match, a security exception is thrown
	 * @param userToken
	 * @param action
	 * @return
	 * @throws DAOException
	 */
	private static User tokenToUser(String userToken, String action) throws DAOException {

		// If userToken is null.
		if (userToken == null) {
			throwSecurityException("Anonymous users (null) are not authorised to " + action);
		}

		User user = userDAO.findByToken(userToken);

		// If null
		if (user == null) {
			throwSecurityException("The user token provided (" + userToken + ") does not correspond to any of our users in our system, you are not authorised to " + action);
		}

		return user;
	}

	private static User checkUserAccess(String userToken, String action, String studyIdentifier) throws DAOException {

		User user = tokenToUser(userToken, action);

		// If not null
		if (user!=null) {

			// Add status check: if active
			if (user.getStatus().equals(User.UserStatus.ACTIVE)) {

				// If it's a curator
				if (user.isCurator()) {
					return user;
				}

				// If no studyIdentifier passed (invoked without study for new submissions)
				if (studyIdentifier == null) {
					return user;

				} else if (user.doesUserOwnsTheStudy(studyIdentifier)) {
					return user;
				}

			}

		}

		throwSecurityException( user.getFullName() + " (" + user.getUserId()+ "), you are not authorised to " + action);

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

		return checkUserAccess(userToken, "create a study");


	}

	public static User userUpdatingStudy(String studyIdentifier, String userToken) throws DAOException {
		return userUpdatingStudy( studyIdentifier,  userToken, null);
	}
	public static User userUpdatingStudy(String studyIdentifier, String userToken, LiteStudy.StudyStatus newStatus) throws DAOException {

		// This will deal with ownership (curator or owner can access)
		User user = checkUserAccess(userToken, "update the study " + studyIdentifier, studyIdentifier);

		LiteStudy study = getStudyFromUser(user, studyIdentifier);

		// but to update we need to check more stuff
		// If user is a curator...can always update, even when it is public.
		if (user.isCurator()) {

			return user;


		} else if (study.getStudyStatus() == LiteStudy.StudyStatus.SUBMITTED) {

			// If status is passed, user changing the status
			if (newStatus != null) {

				// User only can request a change to ONCURATION
				if (newStatus == LiteStudy.StudyStatus.INCURATION) {
					return user;
				}

				// User updating the study through files...so, nos status change involved and study is private
			} else {

				return user;
			}
		}

		// Else...not a curator and study not SUBMITTED.
		throwSecurityException(studyIdentifier + " study can't be updated when the status is " + study.getStudyStatus().name());

		return null;
	}

	private static LiteStudy getStudyFromUser(User user, String studyIdentifier) {
		for (LiteStudy study : user.getStudies()) {
			if (study.getStudyIdentifier().equals(studyIdentifier)){
				return study;
			}
		}

		// This should never happen, unless user is a curator.
		return null;

	}
}
