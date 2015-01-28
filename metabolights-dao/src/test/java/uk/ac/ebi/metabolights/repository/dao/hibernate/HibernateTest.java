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

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public abstract class HibernateTest {

	protected Logger logger;
	@Before
	public void setUp() throws Exception {

		logger= LoggerFactory.getLogger(this.getClass());

		Configuration configuration = new Configuration();

		// Get property file
		Properties hibernateProperties = new Properties();
		//hibernateProperties.load(SessionUtilTest.class.getResourceAsStream("hibernate.properties"));

		configuration.setProperties(hibernateProperties);



		HibernateUtil.initialize(configuration);

		cleanDB();


	}
	protected void cleanDB(){

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.createQuery("delete from StudyData").executeUpdate();
		session.createQuery("delete from UserData").executeUpdate();
		session.close();
	}

}