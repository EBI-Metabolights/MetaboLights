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

import uk.ac.ebi.metabolights.repository.model.AppRole;
import uk.ac.ebi.metabolights.repository.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
	protected void update(User existingUser) throws DAOException {

		SQLQueryMapper<User> updateQuery = getQuery(QueryKeys.UPDATE.ordinal());

		PreparedStatement insertStm = updateQuery.map(getConnection(), existingUser);

		try {
			insertStm.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException("Can't update the  user" , e);
		}




	}

	@Override
	public User findById(Long id) throws DAOException {

		SQLQueryMapper<User> findByQuery = getQuery(QueryKeys.FINDBYID.ordinal());

		PreparedStatement findByStm = findByQuery.map(getConnection(), id);

		try {
			findByStm.execute();

			ResultSet rs = findByStm.getResultSet();

			return fillEntity(rs);

		} catch (SQLException e) {
			throw new DAOException("Can't find user by Id " + id , e);
		}

	}

	@Override
	protected void fillReflexionMap() throws NoSuchMethodException {

		reflexionMap.put ("address",User.class.getMethod("setAddress", String.class));
		reflexionMap.put ("email",User.class.getMethod("setEmail", String.class));
		reflexionMap.put ("joindate",User.class.getMethod("setJoinDate", Date.class));
		reflexionMap.put ("password",User.class.getMethod("setDbPassword", String.class));
		reflexionMap.put ("role",User.class.getMethod("setRole", AppRole.class));
		reflexionMap.put ("username",User.class.getMethod("setUserName", new Class[]{String.class}));
		reflexionMap.put ("affiliation",User.class.getMethod("setAffiliation", String.class));
		reflexionMap.put ("firstname",User.class.getMethod("setFirstName", String.class));
		reflexionMap.put ("lastname",User.class.getMethod("setLastName", String.class));
		reflexionMap.put ("status",User.class.getMethod("setStatus", User.UserStatus.class));
		reflexionMap.put ("affiliationurl",User.class.getMethod("setAffiliationUrl", String.class));
		reflexionMap.put ("apitoken",User.class.getMethod("setApiToken", String.class));
		reflexionMap.put ("id",User.class.getMethod("setUserId", Long.class));
	}

	@Override
	protected void fillClass() {
		this.aClass = User.class;
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

		// Delete by Id query
		addQuery(
				QueryKeys.DELETE.ordinal(),
				"DELETE FROM users where id = ?;",
				"getUserId",
				User.class
		);

		// Insert query
		addQuery(
				QueryKeys.INSERT.ordinal(),
				"INSERT INTO users(" +
						"address, email, joindate, password, role, username, affiliation, firstname, lastname, status, affiliationurl, apitoken) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",

				"getAddress;getEmail;getJoinDate;getDbPassword;getRole;getUserName;getAffiliation;getFirstName;getLastName;getStatus;getAffiliationUrl;getApiToken" ,
				User.class
		);


		// Update query
		addQuery(
				QueryKeys.UPDATE.ordinal(),
				"UPDATE users SET " +
						"address=?, email=?, joindate=?, password=?, role=?, username=?, affiliation=?, firstname=?, lastname=?, status=?, affiliationurl=?, apitoken=? " +
						"WHERE id = ?;",

				"getAddress;getEmail;getJoinDate;getDbPassword;getRole;getUserName;getAffiliation;getFirstName;getLastName;getStatus;getAffiliationUrl;getApiToken;getUserId" ,
				User.class
		);


		// Find by id
		addQuery(
				QueryKeys.FINDBYID.ordinal(),
				"SELECT * FROM users where id = ?;",
				"intValue",
				Long.class
		);

	}
}
