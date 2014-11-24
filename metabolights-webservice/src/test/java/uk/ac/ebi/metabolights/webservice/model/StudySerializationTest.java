/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Nov-17
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

package uk.ac.ebi.metabolights.webservice.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import junit.framework.Assert;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.model.Field;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.Table;

import java.io.IOException;

public class StudySerializationTest {

	private Study getTestStudy(){
		Study test = new Study();

		// Add title
		test.setTitle("title");

		test.setSampleTable(getTestTable());

		return test;
	}

	private Table getTestTable() {

		Table testTable = new Table();
		// Add a field
		Field field = new Field("Field1",0,"fieldtype");
		testTable.getFields().put(field.getHeader(), field);

		field = new Field("Field2",1,"fieldtype");
		testTable.getFields().put(field.getHeader(), field);

		return testTable;

	}


	@Test
	public void testSerialization() throws JsonProcessingException {


		Study study = getTestStudy();

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new GuavaModule());

		String studyString = mapper.writeValueAsString(study);


		Assert.assertTrue("Test serialization of a title", studyString.contains("\"title\":\"title\""));


		// Test serialization of fields
		Assert.assertTrue("Test serialization of a fields", studyString.contains("\"header\":\"Field1\""));


	}

	@Test
	public void testTableSerialization() throws IOException {


		Table table = getTestTable();

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new GuavaModule());

		String tableString = mapper.writeValueAsString(table);

		// Test serialization of fields
		Assert.assertTrue("Test serialization of a fields", tableString.contains("\"header\":\"Field1\""));

		Assert.assertTrue("Test serialization of a fields 2", tableString.contains("\"header\":\"Field2\""));


		//Test deserialisation
		Table newTable = mapper.readValue(tableString,Table.class);


		Assert.assertEquals("Test number of fields", table.getFields().size(), newTable.getFields().size());

		Field field1 = table.getFields().get("Field1");
		Field newField1 = newTable.getFields().get("Field1");
		Assert.assertEquals("Test field 1", field1.getHeader(), newField1.getHeader());

	}

}