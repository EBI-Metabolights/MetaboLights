/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-08
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

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;

import java.sql.Connection;

/**
 * User: conesa
 * Date: 08/01/15
 * Time: 12:38
 */
public abstract class DBTestCaseBase extends DBTestCase{
 		protected ConnectionProvider connectionProvider;

		public  DBTestCaseBase(){
			super();
			System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,"org.postgresql.Driver");
			System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,"jdbc:postgresql://localhost/metabolights");
			System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,"reader");
			System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,"reader");

			connectionProvider = new ConnectionProviderBridge(this) ;

		}

		class ConnectionProviderBridge implements ConnectionProvider {

			private DBTestCaseBase dbTestCaseBase;

			public ConnectionProviderBridge(DBTestCaseBase dbTestCaseBase) {

				this.dbTestCaseBase = dbTestCaseBase;
			}
			@Override
			public Connection getConnection() {

				try {
					return dbTestCaseBase.getConnection().getConnection();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}

