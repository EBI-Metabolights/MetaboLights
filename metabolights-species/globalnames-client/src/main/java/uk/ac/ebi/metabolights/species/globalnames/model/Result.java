package uk.ac.ebi.metabolights.species.globalnames.model;

/**
 * User: conesa
 * Date: 28/11/2013
 * Time: 17:12
 */
public class Result {
	int data_source_id;
	String data_source_title;
	String gni_uuid;
	String name_string;
	String canonical_form;
	String classification_path;
	String classification_path_ranks;
	String classification_path_ids;
	String taxon_id;
	String current_taxon_id;
	String current_name_string;
	String local_id;
	String global_id;
	String url;
	int match_type;
	String prescore;
	float score;

	public int getData_source_id() {
		return data_source_id;
	}

	public void setData_source_id(int data_source_id) {
		this.data_source_id = data_source_id;
	}

	public String getData_source_title() {
		return data_source_title;
	}

	public void setData_source_title(String data_source_title) {
		this.data_source_title = data_source_title;
	}

	public String getGni_uuid() {
		return gni_uuid;
	}

	public void setGni_uuid(String gni_uuid) {
		this.gni_uuid = gni_uuid;
	}

	public String getName_string() {
		return name_string;
	}

	public void setName_string(String name_string) {
		this.name_string = name_string;
	}

	public String getCanonical_form() {
		return canonical_form;
	}

	public void setCanonical_form(String canonical_form) {
		this.canonical_form = canonical_form;
	}

	public String getClassification_path() {
		return classification_path;
	}

	public void setClassification_path(String classification_path) {
		this.classification_path = classification_path;
	}

	public String getClassification_path_ranks() {
		return classification_path_ranks;
	}

	public void setClassification_path_ranks(String classification_path_ranks) {
		this.classification_path_ranks = classification_path_ranks;
	}

	public String getClassification_path_ids() {
		return classification_path_ids;
	}

	public void setClassification_path_ids(String classification_path_ids) {
		this.classification_path_ids = classification_path_ids;
	}

	public String getTaxon_id() {
		return taxon_id;
	}

	public void setTaxon_id(String taxon_id) {
		this.taxon_id = taxon_id;
	}

	public String getLocal_id() {
		return local_id;
	}

	public void setLocal_id(String local_id) {
		this.local_id = local_id;
	}

	public String getGlobal_id() {
		return global_id;
	}

	public void setGlobal_id(String global_id) {
		this.global_id = global_id;
	}

	public String getCurrent_taxon_id() {
		return current_taxon_id;
	}

	public void setCurrent_taxon_id(String current_taxon_id) {
		this.current_taxon_id = current_taxon_id;
	}

	public String getCurrent_name_string() {
		return current_name_string;
	}

	public void setCurrent_name_string(String current_name_string) {
		this.current_name_string = current_name_string;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getMatch_type() {
		return match_type;
	}

	public void setMatch_type(int match_type) {
		this.match_type = match_type;
	}

	public String getPrescore() {
		return prescore;
	}

	public void setPrescore(String prescore) {
		this.prescore = prescore;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
}
