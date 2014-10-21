/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/29/13 1:11 PM
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

package uk.ac.ebi.metabolights.species.globalnames.client;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;

/**
 * User: conesa
 * Date: 28/11/2013
 * Time: 11:58
 */
public class NameResolversParams {

	private static Logger logger = LoggerFactory.getLogger(NameResolversParams.class);
	public static final String NAMES_PARAM_NAME = "names";
	public static final String DATA_SOURCE_PARAM_NAME = "data_source_ids";

	// List of sources: <a href:"http://resolver.globalnames.org/data_sources"/>

	private ArrayList<String> names = new ArrayList<String>();
	private GlobalNamesSources dataSource;

	public NameResolversParams(String name){
		this.names.add(name);
	}

	public NameResolversParams(String name, GlobalNamesSources dataSource){
		this.names.add(name);
		this.dataSource = dataSource;
	}

	public ArrayList<String> getNames() {
		return names;
	}

	public void setNames(ArrayList<String> names) {
		this.names = names;
	}
	/* Behaves as if it were a single string parameter */
	public void setName (String name){

		names.clear();
		names.add(name);
	}
	public String getName(){
		if (names.size() >0) {
			return names.get(0);
		} else {
			return null;
		}
	}

	public GlobalNamesSources getDataSource() {
		return dataSource;
	}

	public void setDataSource(GlobalNamesSources dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	public String toString(){

		String result;

		String namesParam = namesParamToString();

		String dataSourceParam = dataSource==null?"": DATA_SOURCE_PARAM_NAME +  "=" + dataSource.getDataSourceId();

		result = joinParams(namesParam, dataSourceParam);

		try {
			result = URIUtil.encodeQuery(result);
		} catch (URIException e) {
			logger.error("NameResolversParams can't be encoded for the get Request. Value: " + result , e );
		}

		return result;



	}

	private String namesParamToString(){

		String result = "";

		if (names.size()>0){


			for (String name: names){
				if (name !=null && !name.isEmpty()){
					result = result + name + "\t";
				}
			}
		}

		if (result.length()>0)  result = result.substring(0,result.length()-1);

		if (!result.isEmpty()){
			result = NAMES_PARAM_NAME + "=" + result;
		}

		return result;
	}

	private String joinParams(String param1, String param2){

		if (param1 == null) param1 ="";
		if (param2 == null) param2 ="";

		if (param1.equals("") ) return param2;
		if (param2.equals("")) return param1;

		return param1 + "&" + param2;

	}

}
