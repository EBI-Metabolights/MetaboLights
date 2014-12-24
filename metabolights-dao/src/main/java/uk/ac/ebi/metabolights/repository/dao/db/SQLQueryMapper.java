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
	// Runs the query
	public void run (Connection connection, E entity) throws SQLException, DAOException {

		logger.debug("Running a prepared statement: " + query );

		PreparedStatement stm = getPrepareSQLStatement(connection);

		stm.clearParameters();

		// Fills the statement with the query and the parameters, taken from the entity.
		// Go through the methods

		for (int index = 0; index <getters.size();index++){

			Method method = getters.get(index);

			// Index in stamenents starts with 1
			fillStatementParameter(stm,index+1,method,entity);
		}

	}
	private void fillStatementParameter(PreparedStatement stm, int index, Method method, E entity) throws DAOException {

		// Get the return type
		Type returnType = method.getReturnType();

		try {

			// Value returned by method
			Object value = method.invoke(entity);

			if (returnType.equals(Integer.TYPE)) {

				stm.setInt(index, (Integer) value);

			} else if (returnType.equals(String.class)) {

				stm.setString(index, (String) value);

			}
		} catch (Exception e){
			throw new DAOException("Can't fill statement parameter (index " + index +") invoking method " + method.getName() + ". Statement: " + query, e );
		}
	}
	private PreparedStatement getPrepareSQLStatement(Connection connection) throws SQLException {

		// If we don't have a prepared statement
		if (preparedStatement == null){

			// Prepared statement
			preparedStatement = connection.prepareStatement(query);

		}

		return preparedStatement;
	}
}
