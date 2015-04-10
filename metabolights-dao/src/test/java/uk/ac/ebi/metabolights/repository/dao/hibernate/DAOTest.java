/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-19
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

import junit.framework.TestCase;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;

import java.net.URL;
import java.util.Properties;

public abstract class DAOTest  extends TestCase{

	private static final String ISA_CONF_LOCATION = "configRoot";
	private static final String PUBLIC_STUDIES_LOCATION = "studies/public/";
	private static final String PRIVATE_STUDIES_LOCATION = "studies/private/";
	protected String configRoot;
	protected String publicStudiesLocation;
	protected String privateStudiesLocation;

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Before
	public void setUp() throws Exception {

		Configuration configuration = new Configuration();

		// Get property file
		Properties hibernateProperties = new Properties();
		hibernateProperties.load(DAOTest.class.getResourceAsStream("/hibernate.hidden.properties"));

		configuration.setProperties(hibernateProperties);

		initFolders();

		DAOFactory.initialize(configRoot,publicStudiesLocation,privateStudiesLocation,configuration);

		cleanDB();


	}

	private void initFolders() {

		URL configRootUrl = DAOTest.class.getClassLoader().getResource(ISA_CONF_LOCATION);
		URL publicStudiesLocationUrl = DAOTest.class.getClassLoader().getResource(PUBLIC_STUDIES_LOCATION) ;
		URL privateStudiesLocationUrl = DAOTest.class.getClassLoader().getResource(PRIVATE_STUDIES_LOCATION) ;

		configRoot = configRootUrl.getFile();
		publicStudiesLocation = publicStudiesLocationUrl.getFile();
		privateStudiesLocation = privateStudiesLocationUrl.getFile();


	}

	protected void cleanDB(){

		SessionWrapper session = HibernateUtil.getSession();
		session.needSession();
		session.createQuery("delete from StudyData").executeUpdate();
		session.createQuery("delete from UserData").executeUpdate();
		session.createQuery("delete from StableId").executeUpdate();
		session.noNeedSession();
	}

}