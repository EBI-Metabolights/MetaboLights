/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-22
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

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * User: conesa
 * Date: 22/01/15
 * Time: 10:44
 */
public class Filter {

	private static final Logger logger = LoggerFactory.getLogger(Filter.class);

	Map<String,Object> fieldValuePairs = new HashMap<String,Object>();

	public Filter(Object[] fieldValuePairsArray){

		 arrayObjectsToFieldValueMap(fieldValuePairsArray);

	}

	public Filter() {

	}

	public Map<String, Object> getFieldValuePairs() {
		return fieldValuePairs;
	}

	public void  arrayObjectsToFieldValueMap (Object[] fieldValuePairsArray){

		boolean isKey=true;

		String key=null;

		for(int n =0;n<fieldValuePairsArray.length;n++){


			if (isKey){

				key = (String) fieldValuePairsArray[n];

			} else {
				fieldValuePairs.put(key,fieldValuePairsArray[n]);
			}


			isKey = !isKey;
		}
	}

	public void fillQuery(Query query) throws DAOException {

		for (Map.Entry<String, Object> pair: fieldValuePairs.entrySet()) {

			fillParameter(query,pair.getKey(),pair.getValue());
		}

	}
	private void fillParameter(Query query, String key, Object value) throws DAOException {

		Class claz = value.getClass();

		if (claz.equals(String.class)) {
			query.setString(key, (String) value);

		}else if (claz.equals(Long.class)){
				query.setLong(key, (Long) value);
		}else if (claz.equals(Integer.class)){
			query.setInteger(key, (Integer) value);

		} else {
			throw new DAOException ("Can't fill query parameter, Class " + claz.getName()+ " not implemented");
		}
	}

}
