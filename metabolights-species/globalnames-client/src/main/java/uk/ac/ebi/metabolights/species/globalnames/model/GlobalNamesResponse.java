package uk.ac.ebi.metabolights.species.globalnames.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * User: conesa
 * Date: 28/11/2013
 * Time: 17:02
 */
@JsonIgnoreProperties("parameters")
public class GlobalNamesResponse {


	String id;
	String url;
	@JsonProperty("data_sources")
	List<Data_sources> data_sources = new ArrayList<Data_sources>();
	@JsonProperty("data")
	List<Data> data = new ArrayList<Data>();
	String status;
	String message;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Data_sources> getData_sources() {
		return data_sources;
	}

	public void setData_sources(List<Data_sources> data_sources) {
		this.data_sources = data_sources;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
