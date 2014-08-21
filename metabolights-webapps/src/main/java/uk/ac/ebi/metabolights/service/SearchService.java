/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 7/14/14 4:45 PM
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

package uk.ac.ebi.metabolights.service;

import java.util.HashMap;
import java.util.List;

import uk.ac.ebi.metabolights.search.LuceneSearchResult;

/**
 * Services searching the Lucene text index created on the database content.
 *  
 * @author markr
 *
 */
public interface SearchService {

	/**
	 * Perform a Lucene search with the provided text query. 
	 * @param queryText input query
	 * @return Number of hist for the search and the list of results
	 */
	HashMap<Integer, List<LuceneSearchResult>> search(String text) throws Exception;
	String getIndexDirectory();
	int[] getPagingRange (double pageSize, double totalHits, int currentPage);
	LuceneSearchResult getStudy(String study);
	LuceneSearchResult getStudyByObfuscationCode(String obfuscationCode);


}
