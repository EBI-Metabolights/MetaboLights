package uk.ac.ebi.metabolights.search;

import java.util.HashMap;

import uk.ac.ebi.bioinvindex.search.hibernatesearch.StudyBrowseField;

public class FilterSet extends HashMap<String, FilterItem>{
	private String name;
	private StudyBrowseField field;
	public FilterSet(String name, StudyBrowseField field){
		this.name = name;
		this.field = field;
	}
	public FilterSet(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public StudyBrowseField getField(){
		return field;
	}
}
