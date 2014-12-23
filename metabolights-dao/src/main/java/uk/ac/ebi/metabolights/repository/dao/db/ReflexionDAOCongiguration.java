/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Dec-22
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * User: conesa
 * Date: 22/12/14
 * Time: 15:48
 */
public class ReflexionDAOCongiguration {


	private String table;
	private Map<String,ReflexionDAOTuple> jdbcToJavaMap = new HashMap<>();
	private static Logger logger = LoggerFactory.getLogger(ReflexionDAOCongiguration.class);

	// Calculated values
	private String select = "";
	private String fields = "";


	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Map<String, ReflexionDAOTuple> getJdbcToJavaMap() {
		return jdbcToJavaMap;
	}

	public void setJdbcToJavaMap(Map<String, ReflexionDAOTuple> jdbcToJavaMap) {
		this.jdbcToJavaMap = jdbcToJavaMap;
	}

	private String getSELECT(){
		fields = getFIELDS();
		select = "SELECT " +
				fields +
				" FROM " + table;

		return select;
	}

	private String getFIELDS() {

		String fields = "";

		for (ReflexionDAOTuple field: jdbcToJavaMap.values()){

			fields = fields + field.getDatabaseColumnName() + ",";
		}

		// Remove the last comma.
		if (fields.length() >0) fields.substring(0, fields.length()-1);

		return fields;
	}
}
