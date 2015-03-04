/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-28
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

package uk.ac.ebi.metabolights.repository.dao;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.HibernateUtil;
import uk.ac.ebi.metabolights.repository.dao.hibernate.UserDAO;

import javax.sql.DataSource;

/**
 * User: conesa
 * Date: 28/01/15
 * Time: 10:55
 */
public class DAOFactory {

	private static DAOFactory instance;
	private static String isaTabRootConfigurationFolder;
	private static String publicFolder;
	private static String privateFolder;
	private static final Logger logger = LoggerFactory.getLogger(DAOFactory.class);

	public static DAOFactory initialize (String isaTabRootConfigurationFolder, String publicFolder, String privateFolder, Configuration configuration) throws DAOException {

		initializeFields(isaTabRootConfigurationFolder, publicFolder, privateFolder);

		HibernateUtil.initialize(configuration);

		return getInstance();

	}

	public static DAOFactory initialize (String isaTabRootConfigurationFolder, String publicFolder, String privateFolder, String JNDIDataSource) throws DAOException {

		initializeFields(isaTabRootConfigurationFolder, publicFolder, privateFolder);

		HibernateUtil.initialize(JNDIDataSource);

		return getInstance();

	}

	public static DAOFactory initializeWithDataSource(String isaTabRootConfigurationFolder, String publicFolder, String privateFolder, DataSource dataSource) throws DAOException {

		initializeFields(isaTabRootConfigurationFolder, publicFolder, privateFolder);

		HibernateUtil.initialize(dataSource);

		return getInstance();

	}

	private static void initializeFields(String isaTabRootConfigurationFolder, String publicFolder, String privateFolder) {
		if (isInitialized()) {
			logger.warn("DAOFactory is already initialized..this shouldn't happen unless you;ve found a good use case for it. Be carefull!");
		}

		DAOFactory.isaTabRootConfigurationFolder = isaTabRootConfigurationFolder;
		DAOFactory.publicFolder = publicFolder;
		DAOFactory.privateFolder = privateFolder;
	}


	public static DAOFactory getInstance() throws DAOException {

		if (!isInitialized()) {
			throw new DAOException("Configure first the DAOFactory before requesting an instance");
		}

		if (instance == null) {
			instance = new DAOFactory();

		}

		return instance;
	}
	private DAOFactory(){}

	private static boolean isInitialized(){
		return isaTabRootConfigurationFolder != null;
	}
	public static String getIsaTabRootConfigurationFolder() {
		return isaTabRootConfigurationFolder;
	}

	public static String getPublicFolder() {
		return publicFolder;
	}

	public static String getPrivateFolder() {
		return privateFolder;
	}

	public StudyDAO getStudyDAO(){
		StudyDAO newStudyDAO = new StudyDAO(isaTabRootConfigurationFolder,publicFolder,privateFolder);
		return newStudyDAO;
	}
	public UserDAO getUserDAO() {
		return new UserDAO();
	}
}
