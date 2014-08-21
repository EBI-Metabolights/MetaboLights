/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/9/13 10:46 AM
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

package uk.ac.ebi.metabolights.search;

import java.text.Collator;
import java.util.Locale;
import java.util.TreeMap;


public class FilterSet {

	private  TreeMap<String, FilterItem> filterItems = new  TreeMap<String, FilterItem>(Collator.getInstance(Locale.ENGLISH));
	private String name;
	private String field;
	private String prefix="";
	private String suffix="";
	private boolean isEnabled = true;
	public FilterSet(String name, String field, String prefix, String suffix){
		this.name = name;
		this.field = field;
		this.prefix = prefix;
		this.suffix = suffix;
	}
	public FilterSet(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public String getField(){
		return field;
	}
	public String getPrefix(){
		return prefix;
	}
	public String getSuffix(){
		return suffix;
	}
	public boolean getIsEnabled(){
		return isEnabled;
	}

	public void setIsEnabled(boolean enabled){
		this.isEnabled = enabled;
	}
	public void put (String key, FilterItem fi){
		 filterItems.put(key, fi);
	}
	public TreeMap<String, FilterItem> getFilterItems(){
		return filterItems;
	}

}
