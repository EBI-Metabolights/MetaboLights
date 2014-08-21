/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/5/12 10:35 AM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import junit.framework.Assert;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import uk.ac.ebi.bioinvindex.model.Study;

/**
 * A base class for database integration tests giving access to a singleton
 * <tt>SessionFactory</tt> configured programmatically.
 * Allows for a specific properties file to be used.
 * 
 * @author Lasse Koskela
 */
abstract class HibernateIntegrationTestCase {
	
	private final static String propertiesFile = "/test.db.properties";
	private static SessionFactory sf;

	
	protected static synchronized SessionFactory getSessionFactory() {
		if (sf == null) {
			try {
				Configuration cfg = createConfiguration();
				sf = cfg.buildSessionFactory();

			} catch (Throwable ex) {
				throw new RuntimeException(ex);
			}
		}
		return sf;
	}

	private static Configuration createConfiguration() throws IOException {
		Configuration cfg = new AnnotationConfiguration().configure();
		Properties testProperties = loadPropertiesFrom(propertiesFile);
		Enumeration keys = testProperties.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = testProperties.getProperty(key);
			cfg.setProperty(key, value);
		}
		return cfg;
	}

	private static Properties loadPropertiesFrom(String path)
			throws IOException {
		Class context = HibernateIntegrationTestCase.class;
		InputStream stream = context.getResourceAsStream(path);
		Assert.assertNotNull("Resource not found: " + path, stream);
		Properties props = new Properties();
		props.load(stream);
		return props;
	}
}