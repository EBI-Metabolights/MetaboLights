/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Mar-31
 * Modified by:   kenneth
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/28/13 6:04 PM
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
	String edit_distance;

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

	public String getEdit_distance() {
		return edit_distance;
	}

	public void setEdit_distance(String edit_distance) {
		this.edit_distance = edit_distance;
	}
}
