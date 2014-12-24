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

import java.util.HashMap;
import java.util.Map;

/**
 * User: conesa
 * Date: 22/12/14
 * Time: 10:33
 */
public abstract class DAO <E>{

	private Map<Integer,SQLQueryMapper<E>> sqlQueries = new HashMap<>();
	public abstract void save (E entity);
	public abstract E findById (Integer id);
	public abstract void delete(Integer id);
	protected abstract void loadQueries() throws DAOException;

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
}
