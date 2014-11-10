/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Nov-04
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * User: conesa
 * Date: 04/11/14
 * Time: 14:35
 */
public class Table implements Iterable<Row>{

	private  List<List<String>> data;
	private LinkedHashMap<String,Field> fields;

	public Table(){};

	public Table (List<List<String>> data, LinkedHashMap<String, Field> fields){
		this.data = data;
		this.fields= fields;
	}

	@Override
	@JsonIgnore
	public Iterator<Row> iterator() {
		return new Rows(this);
	}

	@JsonIgnore
	public Iterator<Row> getIterator(){return iterator();}
	public LinkedHashMap<String, Field> getFields() {
		return fields;
	}

	public List<List<String>> getData() {
		return data;
	}

	public void setData(List<List<String>> data) {
		this.data = data;
	}

	public void setFields(LinkedHashMap<String, Field> fields) {
		this.fields = fields;
	}
}
