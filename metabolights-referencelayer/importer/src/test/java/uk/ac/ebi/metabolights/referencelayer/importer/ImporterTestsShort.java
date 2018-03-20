/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Dec-19
 * Modified by:   kenneth
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */


package uk.ac.ebi.metabolights.referencelayer.importer;

import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.referencelayer.PostgresSqlLoader;

import java.sql.Connection;

public class ImporterTestsShort extends TestCase{

	protected static final Logger LOGGER = LoggerFactory.getLogger(ImporterTestsShort.class);

	private ConnectionProvider connectionProvider;
	private PostgresSqlLoader postgresSqlLoader;

	@Override
	@BeforeClass
	protected void setUp() throws Exception {

		connectionProvider = new ConnectionProvider() {
			@Override
			public Connection getConnection() {
			   PostgresSqlLoader postgresSqlLoader = new PostgresSqlLoader();
               return postgresSqlLoader.getPostgresConnection("metabolightsPROD");
			}
		};

	}

	@Override
	@AfterClass
	protected void tearDown() throws Exception {
		if (connectionProvider != null) connectionProvider.getConnection().close();
	}


	public void testImport() throws Exception {

        ReferenceLayerImporter rli = new ReferenceLayerImporter(connectionProvider);
		String chebiId = rli.getChebiIDRoot();
		rli.setChebiIDRoot(chebiId);
        rli.importMetabolitesFromChebi();
	}


}
