/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-16
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

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.StableId;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.StudyData;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.UserData;

import javax.sql.DataSource;


public class HibernateUtil {

	private static Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
	private static HibernateUtil instance;

	private static SessionFactory factory;

	public static void initialize(Configuration configuration) {

		logger.info("Initializing HibernateSession util.");

		// Call the private initializer
		initializePrivate(configuration);

	}


	public static void initialize(String JNDIDataSource) {

		logger.info("Initializing HibernateSession util form JNDI name representing a data source: " + JNDIDataSource);

		Configuration configuration = new Configuration();
		configuration.setProperty("hibernate.connection.datasource", JNDIDataSource);
		initializePrivate(configuration);


	}

	public static void initialize(DataSource dataSource) {

		logger.info("Initializing HibernateSession util form a DataSource instance." );


		Configuration configuration = new Configuration();

		// this is how to configure hibernate datasource
		configuration.getProperties().put(Environment.DATASOURCE, dataSource);

		configuration.setProperty("hibernate.current_session_context_class", "thread");
		initializePrivate(configuration);

	}

	private static void initializePrivate(Configuration configuration) {

		// Add classes to be handle by hibernate
		addEntities(configuration);

		// Get the service registry
		ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

		// Get the session factory
		factory = configuration.buildSessionFactory(sr);


	}


	private static SessionFactory getSessionFactory(){
		return factory;
	}


	private static HibernateUtil getInstance() {
		return instance;
	}

	// Add entities to be handle by hibernate
	private static void addEntities(Configuration configuration) {


		configuration.setProperty("hibernate.current_session_context_class", "thread");
		// Add Entities here
		configuration.addAnnotatedClass(StudyData.class);
		configuration.addAnnotatedClass(UserData.class);
		configuration.addAnnotatedClass(StableId.class);

	}

	public static SessionWrapper getSession(){
		return new SessionWrapper(factory);
	}

}
