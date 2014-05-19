package uk.ac.ebi.metabolights.service;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * Provides an interface to the Lucene index.<br>
 * Singleton, Spring managed service. 
 */
@Service
public class IndexProviderServiceImpl implements IndexProviderService {

	private static Logger logger = Logger.getLogger(IndexProviderServiceImpl.class);
	private IndexSearcher indexSearcher;
	private IndexReader indexReader;
	private @Value("#{luceneIndexDirectoryShort}bii/") String luceneIndexDirectory;

	public IndexSearcher getSearcher() {
		logger.debug("Singleton hash code is "+this.hashCode());
	
		//Refresh index
		setUp();
		
		return indexSearcher;
		
	}

	
	public IndexReader getReader() {
		logger.debug("Singleton hash code is "+this.hashCode());
	
		//Refresh index if out of date.
		setUp();
				
		return indexReader;
	}
	
	public String getIndexDirectory ()
	{
		return luceneIndexDirectory; 
	}


	/**
	 * Sets up the Lucene index reader and searcher.
	 * Could do this in the constructor, but that Value for the directory 
	 * is not available then, so take care.
	 *  
	 */
	private synchronized void setUp() {
		
		boolean setUpNeeded = false;
		
		try {

			// If indexReader is null
			if (indexReader == null){
				setUpNeeded = true;
			// Or is not up to date.
			}else if (!indexReader.isCurrent()){
				setUpNeeded = true;
			}
		
			if (setUpNeeded) {
				logger.info("Using index directory "+getIndexDirectory());
					FSDirectory directory= FSDirectory.getDirectory(getIndexDirectory());
					
					//Do we need a lock ? Must read the manual.
					//directory.setLockFactory(new SimpleFSLockFactory());
					
					indexSearcher =new IndexSearcher(directory );
					indexReader = IndexReader.open(directory, true);
			}
		} catch (Exception e) {
				e.printStackTrace();
				logger.error(TextUtils.getErrorStackAsHTML(e));
				logger.error("MAJOR ERROR - could not instantiate Lucene index");
		}
	}
}
