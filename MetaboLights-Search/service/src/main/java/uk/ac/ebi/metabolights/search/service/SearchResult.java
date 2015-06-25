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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.model.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * User: conesa
 * Date: 02/12/14
 * Time: 09:59
 */
public class SearchResult<I extends Entity> {
	private SearchQuery query;
	private List<I> results = new LinkedList<I>();
	private List<String> reportLines = new ArrayList<String>();

	static Logger logger = LoggerFactory.getLogger(SearchResult.class);
	public SearchQuery getQuery() {
		return query;
	}

	public void setQuery(SearchQuery query) {
		this.query = query;
	}

	public Collection<I> getResults() {
		return results;
	}
	public void report(String reportLine) {
		logger.info(reportLine);
		reportLines.add(reportLine);
	}

	public List<String> getReportLines() {
		return reportLines;
	}
}
