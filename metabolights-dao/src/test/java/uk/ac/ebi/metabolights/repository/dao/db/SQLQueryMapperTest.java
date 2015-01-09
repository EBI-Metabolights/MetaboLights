/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Dec-23
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

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.model.Study;

public class SQLQueryMapperTest extends DBTestCaseBase {

	@Test(expected = DAOException.class)
	public void testWrongContructor() throws DAOException {

		SQLQueryMapper<String> sqlQueryMapper = new SQLQueryMapper<String>("query", new String[]{"asas", "sdsdd"}, String.class);
	}

	@Test
	public void testRightContructor() throws DAOException {
		SQLQueryMapper<String> sqlQueryMapper = new SQLQueryMapper<String>("query" ,new String[]{"toString", "length"},String.class);
	}

	@Test
	public void testBasicQueryMapperRun() throws Exception {

		IDatabaseConnection connection = getConnection();

		SQLQueryMapper<Study> studyQueryMapper = new SQLQueryMapper<>("SELECT * FROM studies WHERE acc = ?", new String[]{"getStudyIdentifier"}, Study.class );

		Study study = new Study();
		study.setStudyIdentifier("NONE");
		studyQueryMapper.map(connection.getConnection(),study);

	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(SQLQueryMapperTest.class.getClassLoader().getResourceAsStream("SQLQueryMapper.xml"));
		//return new XmlDataSet(SQLQueryMapperTest.class.getClassLoader().getResourceAsStream("SQLQueryMapper.xml"));
	}

	@Override
	protected void setUpDatabaseConfig(DatabaseConfig config) {
		//config.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, true);
		//config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
	}
}