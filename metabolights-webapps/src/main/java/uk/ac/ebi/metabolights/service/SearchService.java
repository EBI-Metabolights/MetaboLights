package uk.ac.ebi.metabolights.service;

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
	 * @return list of results
	 */
	List<LuceneSearchResult> search(String queryText) throws Exception;
	String getIndexDirectory();

}
