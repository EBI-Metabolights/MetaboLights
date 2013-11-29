package uk.ac.ebi.metabolights.species.globalnames.model;

import java.util.Collection;

/**
 * User: conesa
 * Date: 28/11/2013
 * Time: 17:10
 */
public class Data {

	String supplied_name_string;
	Collection<Result> results;

	public String getSupplied_name_string() {
		return supplied_name_string;
	}

	public void setSupplied_name_string(String supplied_name_string) {
		this.supplied_name_string = supplied_name_string;
	}

	public Collection<Result> getResults() {
		return results;
	}

	public void setResults(Collection<Result> results) {
		this.results = results;
	}

}
