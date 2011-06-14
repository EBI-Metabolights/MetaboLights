package uk.ac.ebi.metabolights.service;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;

/**
 * Provides an interface to the Lucene index.<br>
 */
public interface IndexProviderService {

	public IndexSearcher getSearcher();
	public IndexReader getReader();
	public String getIndexDirectory ();
}

