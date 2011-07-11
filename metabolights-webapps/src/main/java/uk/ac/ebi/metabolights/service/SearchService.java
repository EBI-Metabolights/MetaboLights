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
	HashMap<Integer, List<LuceneSearchResult>> search(String queryText) throws Exception;
	String getIndexDirectory();

}
