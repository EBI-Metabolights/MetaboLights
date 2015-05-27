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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Assert;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.model.*;
import uk.ac.ebi.metabolights.repository.utils.IsaTab2MetaboLightsConverter;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;

public class StudySerializationTest {

	private Study getTestStudy(){
		Study test = new Study();

		// Add title
		test.setTitle("title");

		test.setSampleTable(getTestTable());
		// Has to be without timestamp
		test.setStudyPublicReleaseDate(IsaTab2MetaboLightsConverter.isaTabDate2Date("2015-07-01"));

		User user = new User();
		test.getUsers().add(user);

		Backup newBackup = new Backup(new File("."), new Date());

		LinkedList<Backup> backups = new LinkedList<>();
		backups.add(newBackup);
		test.setBackups(backups);


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
	public void testSerialization() throws IOException {


		Study study = getTestStudy();

		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		String studyString = mapper.writeValueAsString(study);

		Assert.assertTrue("Test serialization of a title", studyString.contains("\"title\":\"title\""));

		// Test serialization of fields
		Assert.assertTrue("Test serialization of a fields", studyString.contains("\"header\":\"Field1\""));

		// Test serialization of users
		Assert.assertTrue("Test serialization of a users", studyString.contains("\"users\":[{"));

		// Test serialization of users, listofallstatus not serialized
		Assert.assertFalse("listofstatus is being serialized!", studyString.contains("listOfAllStatus"));


		// Test deserialisation now
		Study deserializedStudy = mapper.readValue(studyString,Study.class);

		Assert.assertTrue("Public release date is not properly kept during serialization and parsing.", DateUtils.isSameDay(study.getStudyPublicReleaseDate(),deserializedStudy.getStudyPublicReleaseDate()));

	}

	@Test
	public void testTableSerialization() throws IOException {


		Table table = getTestTable();

		ObjectMapper mapper = new ObjectMapper();

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