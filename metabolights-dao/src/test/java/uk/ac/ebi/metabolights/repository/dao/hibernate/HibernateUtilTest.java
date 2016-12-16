/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Feb-09
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

package uk.ac.ebi.metabolights.repository.dao.hibernate;

import org.junit.Before;
import org.junit.Test;
import org.postgresql.ds.PGPoolingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HibernateUtilTest {

	private static final Logger logger = LoggerFactory.getLogger(HibernateUtilTest.class);

	private static final String JAVA = "java:";
	private static final String JAVA_COMP = JAVA+"/comp";
	private static final String JAVA_COMP_ENV = JAVA_COMP +"/env";
	private static final String JAVA_COMP_ENV_JDBC = JAVA_COMP_ENV+ "/jdbc";
	private static final String JNDI_TEST_DATA_SOURCE = "/testDataSource";
	private static final String JNDI_TEST_DATA_SOURCE_FULL = JAVA_COMP_ENV_JDBC + JNDI_TEST_DATA_SOURCE;

	@Before
	public void setUpClass() throws Exception {


		// Get data from the hibernate.properties test file
		Properties hibernateProperties = loadPropertyFile("/hibernate.hidden.properties");

		// If pointing to oracle:
		if (hibernateProperties.get("hibernate.connection.driver_class").equals("org.postgresql.Driver")) {
			addPostrePoolToJNDI(hibernateProperties);
		}
		/**else if (hibernateProperties.get("hibernate.connection.driver_class").equals("oracle.jdbc.driver.OracleDriver")) {
			addOraclePoolToJNDI(hibernateProperties);
		}**/
		else {
			logger.warn("Can't create a pool in JNDI for that driver: " + hibernateProperties.get("hibernate.connection.driver_class"));

		}

	}

	private void addPostrePoolToJNDI(Properties hibernateProperties) throws NamingException, SQLException {

        // Create initial context
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES,
                "org.apache.naming");
        InitialContext ic = new InitialContext();

        ic.createSubcontext("java:");
        ic.createSubcontext(JAVA_COMP);
        ic.createSubcontext(JAVA_COMP_ENV);
        ic.createSubcontext(JAVA_COMP_ENV_JDBC);

        // Construct DataSource
        PGPoolingDataSource ds = new PGPoolingDataSource();
        ds.setUrl(hibernateProperties.getProperty("hibernate.connection.url"));
        ds.setUser(hibernateProperties.getProperty("hibernate.connection.username"));
        ds.setPassword(hibernateProperties.getProperty("hibernate.connection.password"));

        // Test connectivity
        Connection conn = ds.getConnection();
        conn.close();

        ic.bind(JNDI_TEST_DATA_SOURCE_FULL, ds);

	}

	/**private void addOraclePoolToJNDI(Properties hibernateProperties) throws NamingException, SQLException {

		// Create initial context
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.naming.java.javaURLContextFactory");
		System.setProperty(Context.URL_PKG_PREFIXES,
				"org.apache.naming");
		InitialContext ic = new InitialContext();

		ic.createSubcontext("java:");
		ic.createSubcontext(JAVA_COMP);
		ic.createSubcontext(JAVA_COMP_ENV);
		ic.createSubcontext(JAVA_COMP_ENV_JDBC);

		// Construct DataSource
		OracleConnectionPoolDataSource ds = new OracleConnectionPoolDataSource();
		ds.setURL(hibernateProperties.getProperty("hibernate.connection.url"));
		ds.setUser(hibernateProperties.getProperty("hibernate.connection.username"));
		ds.setPassword(hibernateProperties.getProperty("hibernate.connection.password"));

		// Test connectivity
		PooledConnection pc_1 = ds.getPooledConnection();
		Connection conn_1 = pc_1.getConnection();
		conn_1.close();
		pc_1.close();

		ic.bind(JNDI_TEST_DATA_SOURCE_FULL, ds);



	}      **/

	private Properties loadPropertyFile(String fileName) throws IOException {

		Properties prop = new Properties();
		InputStream in = getClass().getResourceAsStream(fileName);
		prop.load(in);
		in.close();

		return prop;
	}

	@Test
	public void testInitialisationFromJDNDI() {



		HibernateUtil.initialize(JNDI_TEST_DATA_SOURCE_FULL);





	}

}
