/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-14
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

import uk.ac.ebi.metabolights.repository.model.Study;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * User: conesa
 * Date: 22/12/14
 * Time: 10:34
 */
public class StudyDBDAO extends DAO<Study>{


	private enum QueryKeys{
		FINDBYID,
		INSERT,
		UPDATE,
		DELETE,
		FINDBYACC,

	}

	@Override
	protected Object getEntityId(Study entity) {
		return entity.getId();
	}

	@Override
	protected void insert(Study newStudy) throws  DAOException {

		SQLQueryMapper<Study> insertQuery = getQuery(QueryKeys.INSERT.ordinal());

		PreparedStatement insertStm = insertQuery.map(getConnection(), newStudy);

		try {
			insertStm.executeUpdate();

			ResultSet keys = insertStm.getGeneratedKeys();

			// There shoudl be only one
			while (keys.next()) {
				newStudy.setId(keys.getLong(1));  //There should be only one
				logger.debug( "new study inserted: study " + newStudy.getId());
			}
			keys.close();

		} catch (SQLException e) {
			throw new DAOException("Can't insert study" , e);
		}


	}

	@Override
	protected void update(Study existingStudy) throws DAOException {

		SQLQueryMapper<Study> updateQuery = getQuery(QueryKeys.UPDATE.ordinal());

		PreparedStatement insertStm = updateQuery.map(getConnection(), existingStudy);

		try {
			insertStm.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException("Can't update the  study" , e);
		}

	}

	@Override
	public Study findById(Long id) throws DAOException {

		return findBy(QueryKeys.FINDBYID,id);
	}

	public Study findBy(QueryKeys findByQueryKey, Object filter) throws DAOException {

		SQLQueryMapper<Study> findByQuery = getQuery(findByQueryKey.ordinal());


		PreparedStatement findByStm = findByQuery.map(getConnection(), filter);

		try {
			findByStm.execute();

			ResultSet rs = findByStm.getResultSet();

			return fillEntity(rs);

		} catch (SQLException e) {
			throw new DAOException("Can't find study with query " + findByQueryKey.name() + " by " + filter , e);
		}

	}

	public Study findByAcc(String accesion) throws DAOException {

		return findBy(QueryKeys.FINDBYACC,accesion);

	}
	@Override
	protected void fillReflexionMap() throws NoSuchMethodException {

		reflexionMap.put ("id",Study.class.getMethod("setId", Long.class));
		reflexionMap.put ("acc",Study.class.getMethod("setStudyIdentifier", String.class));
		reflexionMap.put ("obfuscationcode",Study.class.getMethod("setObfuscationCode", String.class));
		reflexionMap.put ("status",Study.class.getMethod("setStudyStatus", Study.StudyStatus.class));

	}

	@Override
	protected void fillClass() {
		this.aClass = Study.class;
	}

	@Override
	public void delete(Study studyToDelete) throws  DAOException {

		SQLQueryMapper<Study> deleteQuery = getQuery(QueryKeys.DELETE.ordinal());

		PreparedStatement deleteStm = deleteQuery.map(getConnection(), studyToDelete);

		try {
			deleteStm.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Can't delete a study: StudyId " + studyToDelete.getId() , e);
		}

	}

	@Override
	protected void loadQueries() throws DAOException {

		// Add study queries

		// Delete by Id query
		addQuery(
				QueryKeys.DELETE.ordinal(),
				"DELETE FROM studies where id = ?;",
				"getId",
				Study.class
		);

		// Insert query
		addQuery(
				QueryKeys.INSERT.ordinal(),
				"INSERT INTO studies" +
						"(acc, obfuscationcode, status) " +
						"VALUES (?, ?, ?);",

				"getStudyIdentifier;getObfuscationCode; " ,
				Study.class
		);


		// Update query
		addQuery(
				QueryKeys.UPDATE.ordinal(),
				"UPDATE studies SET " +
						"acc=?, obfuscationcode=?, status=? " +
						"WHERE id = ?;",

				"getStudyIdentifier;getObfuscationCode;getStudyStatus;getId" ,
				Study.class
		);


		// Find by id
		addQuery(
				QueryKeys.FINDBYID.ordinal(),
				"SELECT * FROM studies where id = ?;",
				"intValue",
				Long.class
		);

		// Find by acc
		addQuery(
				QueryKeys.FINDBYACC.ordinal(),
				"SELECT * FROM studies where acc = ?;",
				"toString",
				String.class
		);


	}
}
