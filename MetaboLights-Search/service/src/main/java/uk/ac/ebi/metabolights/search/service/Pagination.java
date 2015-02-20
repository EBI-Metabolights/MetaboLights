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

/**
 * User: conesa
 * Date: 02/12/14
 * Time: 10:10
 */
public class Pagination {
	private int page = 1;
	private int itemsCount=0;
	private int pageSize = 10;

	public Pagination(int itemsCount) {
		this.itemsCount = itemsCount;
	}
	public Pagination(){}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getItemsCount() {
		return itemsCount;
	}

	public void setItemsCount(int itemsCount) {
		this.itemsCount = itemsCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@JsonIgnore
	public int getPageCount(){
		return (int) Math.ceil(itemsCount/(double)pageSize);
	}
	@JsonIgnore
	public int getFirstPageItemNumber() {
		return ((page - 1) * pageSize) + 1;
	}
	@JsonIgnore
	public int getLastPageItemNumber() {
		if (page == getPageCount()){
			return itemsCount;
		} else {
			return getFirstPageItemNumber() + (pageSize - 1);
		}
	}
}
