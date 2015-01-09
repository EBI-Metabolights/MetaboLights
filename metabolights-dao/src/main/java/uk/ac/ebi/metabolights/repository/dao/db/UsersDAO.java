/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Dec-22
 * Modified by:   conesa
 *
 *
 * Copyright 2014 EMBL-European Bioinformatics Institute.
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

import uk.ac.ebi.metabolights.repository.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: conesa
 * Date: 22/12/14
 * Time: 10:34
 */
public class UsersDAO extends DAO<User> {


	private enum QueryKeys{
		FINDBYID,
		INSERT,
		UPDATE,
		DELETE

	}

	@Override
	protected Object getEntityId(User entity) {
		return entity.getUserId();
	}

	@Override
	protected void insert(User newUser) throws  DAOException {

		SQLQueryMapper<User> insertQuery = getQuery(QueryKeys.INSERT.ordinal());

		PreparedStatement insertStm = insertQuery.map(getConnection(), newUser);

		try {
			insertStm.executeUpdate();

			ResultSet keys = insertStm.getGeneratedKeys();

			// There shoudl be only one
			while (keys.next()) {
				newUser.setUserId(keys.getLong(1));  //There should be only one
				logger.debug( "new user inserted: user " +newUser.getUserId());
			}
			keys.close();

		} catch (SQLException e) {
			throw new DAOException("Can't insert user" , e);
		}


	}

	@Override
	protected void update(User entity) {

	}

	@Override
	public User findById(Integer id) throws DAOException {

		SQLQueryMapper<User> findByQuery = getQuery(QueryKeys.FINDBYID.ordinal());

		// Create a dummy user to host the
		PreparedStatement findByStm = findByQuery.map(getConnection(), id);

		try {
			findByStm.execute();

			ResultSet rs = findByStm.getResultSet();

		} catch (SQLException e) {
			throw new DAOException("Can't find user by Id " + id , e);
		}




		return null;
	}

	@Override
	public void delete(User userToDelete) throws  DAOException {

		SQLQueryMapper<User> deleteQuery = getQuery(QueryKeys.DELETE.ordinal());

		PreparedStatement deleteStm = deleteQuery.map(getConnection(), userToDelete);

		try {
			deleteStm.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Can't delete an user: UserId " + userToDelete.getUserId() , e);
		}

	}

	@Override
	protected void loadQueries() throws DAOException {

		// Add users queries

		// Delete query
		addQuery(
				QueryKeys.DELETE.ordinal(),
				"DELETE FROM users where id = ?",
				"getUserId",
				User.class
		);

		// Save query
		addQuery(
				QueryKeys.INSERT.ordinal(),
//				"INSERT INTO users(" +
//						"\"ID\", \"ADDRESS\", \"EMAIL\", \"JOINDATE\", \"PASSWORD\", \"ROLE\", \"USERNAME\"," +
//						"\"AFFILIATION\", \"FIRSTNAME\", \"LASTNAME\", \"STATUS\", \"AFFILIATIONURL\", \"APITOKEN\") " +
//				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				"INSERT INTO users(" +
						"password, username) " +
						"VALUES (?, ?)",

				"getUserName;getDbPassword" ,
				User.class
		);


		// Find by id
		addQuery(
				QueryKeys.FINDBYID.ordinal(),
				"SELECT * FROM users where id = ?",
				"intValue",
				Integer.class
		);

	}
}
