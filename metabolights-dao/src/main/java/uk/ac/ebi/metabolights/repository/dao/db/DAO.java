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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * User: conesa
 * Date: 22/12/14
 * Time: 10:33
 */
public abstract class DAO <E>{

	// Should not be  more than one logger instance per DAO instance:
	// See http://stackoverflow.com/a/11544957/3157958.
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private Map<Integer,SQLQueryMapper<E>> sqlQueries =new HashMap<>();
	// Reflexion map (Key: Field in the database, Value: Setter in the entity).
	protected Map<String, Method> reflexionMap = new HashMap<>();
	protected Class<E> aClass;


	public void save (E entity) throws DAOException {

		if (getEntityId(entity) == null){
			insert(entity);
		} else {
			update(entity);
		}
	};
	protected abstract Object getEntityId(E entity);
	protected abstract void insert(E entity) throws DAOException;
	protected abstract void update(E entity) throws DAOException;
	public abstract E findById (Long id) throws DAOException;
	// Fill a reflexxion map: map with database COLUMNNAME and setter pairs.
	protected abstract void fillReflexionMap() throws NoSuchMethodException;
	// Fill the aClass member with the Class of the Entitty to be able to instantiate a new one
	protected abstract void fillClass();
	public abstract void delete(E entity) throws DAOException;
	protected abstract void loadQueries() throws DAOException;

	// DAO initialization
	protected void initialise() throws NoSuchMethodException, DAOException {

		// Call load queries
		loadQueries();
		fillClass();
		fillReflexionMap();
	}
	/**
	 *
	 * @param key
	 * @param query
	 * @param getters: String with getters names of the entity, separated by ";"
	 * @param entityClass
	 * @throws DAOException
	 */
	protected void addQuery(int key, String query, String getters, Class entityClass) throws DAOException {

		// Generate and array from the getters
		String[] gettersA = getters.split(";");
		SQLQueryMapper<E> sqlQueryMapper = new SQLQueryMapper<E>(query, gettersA, entityClass);
		sqlQueries.put(key,sqlQueryMapper);
	}
	protected SQLQueryMapper<E> getQuery(int key){

		return sqlQueries.get(key);
	}

	protected List<E> fillEntities(ResultSet rs) throws DAOException {
		List<E> entities = new ArrayList<>();

		try {
			while (rs.next()){

				E entity = fillSingleEntity(rs);
				entities.add(entity);
			}
		} catch (SQLException e) {
			throw new DAOException("Can't loop through the resultset.",e);
		}
		return entities;



	}

	protected E fillEntity(ResultSet rs) throws DAOException {

		Collection<E> entities = fillEntities(rs);

		// If no entities have been found
		if (entities.size()==0) {
			return null;
		} else {
			// Get the first one..
			return entities.iterator().next();
		}
	}

	private E fillSingleEntity(ResultSet rs) throws DAOException {

		E newEntity = null;


		try {

			if (aClass == null){
				throw new NullPointerException("aClass variable is not set, Can't instantiate a new entity without it. Fill the aClass variable when implementing fillClass()");
			}

			newEntity = aClass.newInstance();

			// Loop through the list reflexionMap
			for (Map.Entry<String,Method> entry: reflexionMap.entrySet()){

				fillEntitySetter(newEntity,entry,rs);

			}

		} catch (Exception e) {
			throw new DAOException("Can't instantiate and fill the entity (" + aClass.getName() + ")",e);
		}

		return newEntity;

	}

	private void fillEntitySetter (E entity, Map.Entry<String,Method> reflectionEntry, ResultSet rs) throws DAOException {

		// Get the method
		Method setter = reflectionEntry.getValue();

		// Get the type of the 1st parameter, since it's a setter there should be the only one.
		Type[] types = setter.getParameterTypes();


		if (types.length == 0) {
			 throw new NullPointerException("Can't set the value for " + setter.getName() + " since it has no paramenters.");
		}

		// We'll get the first one.
		Type type = types[0];

		Object value = null;

		try {


			if (type.equals(String.class)) {

				value = rs.getString(reflectionEntry.getKey());

			} else if (type.equals(Integer.class)) {

				value = rs.getInt(reflectionEntry.getKey());

			} else if (type.equals(Long.class)) {

				value = rs.getLong(reflectionEntry.getKey());

			} else if (type.equals(Date.class)) {

				value = rs.getTimestamp(reflectionEntry.getKey());

			} else if (((Class) type).isEnum()) {

				Integer ordinal = rs.getInt(reflectionEntry.getKey());

				value = ((Class)type).getEnumConstants()[ordinal];

			} else {
				logger.warn("Type " + type.toString() + " unexpected. Can't fill entity method " + setter.getName() + " with value " + value);
			}

		} catch (SQLException e) {
			throw new DAOException("Can't get value from the resultset to invoke " + setter.getName() + ". Trying to get the value fron the table column: " + reflectionEntry.getKey(), e );

		}


		// Now that we have the value, we invoke the setter
		try {
			setter.invoke(entity,value);
		} catch (Exception e) {
			throw new DAOException("Can't set value " + value.toString() + " using the method " + setter.getName(), e );
		}

	}
	protected Connection getConnection(){
		return DAOFactory.getConnection();
	}
}
