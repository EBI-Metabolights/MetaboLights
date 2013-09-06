/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 06/09/13 20:27
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
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
