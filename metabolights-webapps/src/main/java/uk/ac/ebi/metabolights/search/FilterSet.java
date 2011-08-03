package uk.ac.ebi.metabolights.search;

import java.util.HashMap;

import uk.ac.ebi.bioinvindex.search.hibernatesearch.StudyBrowseField;

public class FilterSet {
	
	private  HashMap<String, FilterItem> filterItems = new  HashMap<String, FilterItem>();
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
	public HashMap<String, FilterItem> getFilterItems(){
		return filterItems;
	}
	
}
