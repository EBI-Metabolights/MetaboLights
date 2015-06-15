/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Dec-02
 * Modified by:   conesa
 *
 *
 * Copyright 2014 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.search.service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * User: conesa
 * Date: 02/12/14
 * Time: 09:20
 */
public class SearchQuery {
	private String text;
	private LinkedList<Facet> facets = new LinkedList<Facet>();
	private ArrayList<Booster> boosters = new ArrayList();
	private Pagination pagination = new Pagination();
	@JsonIgnore
	private SearchUser user;

	public SearchQuery(){}
	public SearchQuery(String text){
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Collection<Facet> getFacets() {
		return facets;
	}

	public void setFacets(LinkedList<Facet> facets) {
		this.facets = facets;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public SearchUser getUser() {
		return user;
	}

	public void setUser(SearchUser user) {
		this.user = user;
	}

	public ArrayList<Booster> getBoosters() {
		return boosters;
	}

	public String toString(){
		String result = text;

		if (user != null) {
			result = result + ";" + user.toString();
		}

		return result;
	}

}
