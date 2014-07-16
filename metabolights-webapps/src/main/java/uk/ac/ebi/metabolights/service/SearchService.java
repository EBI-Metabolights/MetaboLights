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
