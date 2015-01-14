/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Dec-23
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * User: conesa
 * Date: 23/12/14
 * Time: 09:12
 * Creates, prepares and populate a PreparedStatement using entity methods.
 */
public class SQLQueryMapper<E> {
	private static Logger logger = LoggerFactory.getLogger(SQLQueryMapper.class);

	private String query;
	private Class<E> aClass;
	private List<Method> getters = new ArrayList<>();
	private PreparedStatement preparedStatement;

	public SQLQueryMapper (String query, String[] gettersNames, Class<E> aClass) throws DAOException {
		this.query=query;
		this.aClass = aClass;
		stringGettersToMethods(gettersNames);
	}
	private void stringGettersToMethods(String[] gettersNames) throws DAOException {

		// For each method name
		for (String getterName:gettersNames) {

			// Get the methos from the entity by name
			Method method;

			try {
				method = aClass.getMethod(getterName);

			} catch (NoSuchMethodException e) {
				throw new DAOException("Getter passed to the SQLMapper (" + getterName + ") not found in the class " + aClass.getName(), e);
			}

			getters.add(method);
		}

	}
	// Maps Entity to the statement and returns it
	public PreparedStatement map (Connection connection, Object dataHolder) throws DAOException {

		logger.debug("Running a prepared statement: " + query );

		PreparedStatement stm = getPrepareSQLStatement(connection);

		try {
			stm.clearParameters();
		} catch (SQLException e) {
			throw new DAOException("Can't clear statement parameters", e);
		}

		// Fills the statement with the query and the parameters, taken from the entity.
		// Go through the methods

		for (int index = 0; index <getters.size();index++){

			Method method = getters.get(index);

			// Index in stamenents starts with 1
			fillStatementParameter(stm,index+1,method,dataHolder);
		}

		return stm;

	}
	private void fillStatementParameter(PreparedStatement stm, int index, Method method, Object entity) throws DAOException {

		// Get the return type
		Type returnType = method.getReturnType();

		try {

			// Value returned by method
			Object value = method.invoke(entity);

			if (returnType.equals(String.class)) {

				stm.setString(index, (String) value);


			} else if (returnType.equals(Integer.TYPE)) {

				stm.setInt(index, (Integer) value);

			} else if (returnType.equals(Long.class)) {

				stm.setLong(index, (Long) value);

			} else if (returnType.equals(Date.class)) {

				Date date = (Date) value;
				stm.setTimestamp(index, new java.sql.Timestamp(date.getTime()));
			// If it's an enum use the ordinal
			}else if (value.getClass().isEnum()){

				Method ordinalMethod = value.getClass().getMethod("ordinal");
				Integer ordinal = (Integer) ordinalMethod.invoke(value);

				stm.setInt(index,ordinal);

			} else {
				logger.warn("return type (" + returnType.toString() + ") of method " + method.getName() + " not understood. Consider adding this type to the fillStatementParameter method. Trying default approach: toString");
				stm.setString(index, value.toString());
			}
		} catch (Exception e){
			throw new DAOException("Can't fill statement parameter (index " + index +") invoking method " + method.getName() + ". Statement: " + query, e );
		}
	}
	private PreparedStatement getPrepareSQLStatement(Connection connection) throws DAOException {

		// If we don't have a prepared statement
		if (preparedStatement == null){

			// Prepared statement
			try {
				preparedStatement = prepareStatement(connection,query);
			} catch (SQLException e) {
				throw new DAOException(e);
			}

		}

		return preparedStatement;
	}
	// Prepares the statement, for Insert statements we need a different configuration, at least for Postgresql.
	private static PreparedStatement prepareStatement(Connection connection, String query) throws SQLException {

		// If its and insert query
		if (query.toLowerCase().startsWith("insert")) {
			return connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		} else {
			return connection.prepareStatement(query);
		}

	}
}
