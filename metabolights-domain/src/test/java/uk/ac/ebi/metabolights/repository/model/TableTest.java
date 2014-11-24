/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Nov-05
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

package uk.ac.ebi.metabolights.repository.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TableTest {

	@Test
	public void testIterator() throws Exception {


		Table table = getTable(20,15);

		int rowCount = 1;
		int col;


		for(Row row :table){

			col = 1;

			Assert.assertEquals("Row index in row", rowCount-1,row.getRowIndex());

			for (Cell cell:row){

				Assert.assertEquals("Cell value test",coordinates2Values(rowCount,col),cell.getValue());

				col++;

			}

			rowCount++;

		}

	}

	static List<List<String>> generateData(int rows, int columns){

		List<List<String>> data = new ArrayList<List<String>>();
		for (int rowCount = 1; rowCount<= rows; rowCount++){

			List<String > row = new ArrayList<String>();

			for (int colCount = 1; colCount<= columns; colCount++){

				String cellValue = coordinates2Values(rowCount, colCount);
				row.add(cellValue);
			}

			data.add(row);


		}

		return data;
	}

	static LinkedHashMap<String, Field> generateFieldsMap (int numberOfFields){

		String[] headers = new String[numberOfFields];

		for (int fieldCount = 0; fieldCount<numberOfFields;fieldCount++){

			String header = "Field:" + fieldCount;

			headers[fieldCount]= header;
		}

		return generateFieldsMap(headers);

	}


	static LinkedHashMap<String, Field> generateFieldsMap (String[] headers){

		LinkedHashMap<String,Field> fieldMap =  new LinkedHashMap();

		for (int fieldCount = 0; fieldCount<headers.length;fieldCount++){

			Field field = new Field(headers[fieldCount], fieldCount,"todo");
			fieldMap.put(fieldCount + "~" + field.getHeader(), field);
		}

		return fieldMap;

	}

	private static String coordinates2Values(int rowCount, int colCount) {

		return "R:" + rowCount + ":C" + colCount;

	}

	@Test
	public void testConstructor() throws Exception {

		Table table = getTable(2,4);

		Assert.assertEquals("Data size test", 2, table.getData().size());

		Assert.assertEquals("Fields size test", 4, table.getFields().size());


	}

	@Test
	public void testDuplicatedHeaders() throws Exception {

		Table table = getTable(2,new String[] {"Unit", "Unit"});

		Assert.assertEquals("Data size test", 2, table.getData().size());

		Assert.assertEquals("Fields size test", 2, table.getFields().size());



		for (Field field:table.getFields().values()){

			Assert.assertEquals("Teast field iteration ", "Unit", field.getHeader());

		}




	}

	public static Table getTable(int rows, int columns) {
		List<List<String>> data = generateData(rows,columns);
		LinkedHashMap<String, Field> fieldMap = generateFieldsMap(columns);

		return new Table(data,fieldMap);
	}

	public static Table getTable(int rows, String[] columns) {
		List<List<String>> data = generateData(rows,columns.length);
		LinkedHashMap<String, Field> fieldMap = generateFieldsMap(columns);

		return new Table(data,fieldMap);
	}

}