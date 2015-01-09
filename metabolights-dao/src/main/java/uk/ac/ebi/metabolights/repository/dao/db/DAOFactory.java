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

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * User: conesa
 * Date: 22/12/14
 * Time: 10:21
 */
public final class DAOFactory {

	// DAO map
	private static Map<String,DAO> daos = new HashMap<String, DAO>();

	private static ConnectionProvider localConnectionProvider;
	private static Connection connection;

	private static <T extends DAO> T getDAO(Class<T> type) throws DAOException {

		String keyToDAO = type.getSimpleName();
		DAO dao;

		// If we got the DAO already.
		if (daos.containsKey(keyToDAO)){
			dao = daos.get(keyToDAO);

		// DAO does not exists.
		} else {

			// Instantiate the new DAO type
			try {
				dao = type.newInstance();
			} catch (Exception e) {
				throw new DAOException("Can't instantiate DAO requested:" + type.getName());
			}

		}

		return type.cast(dao);


	}

	public static UsersDAO getUsersDAO() throws DAOException {

		return getDAO(UsersDAO.class);
	}
	public static Connection getConnection(){

		if (connection == null){
			connection = localConnectionProvider.getConnection();
		}

		return connection;


	}
	public static void setConnectionProvider(ConnectionProvider connectionProvider){
		localConnectionProvider = connectionProvider;
	}



}
