package uk.ac.ebi.metabolights.service;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.PerFieldAnalyzerWrapper;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.metabolights.search.LuceneSearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{
	
	private static Logger logger = Logger.getLogger(SearchServiceImpl.class);
	private final int topN = 10000; // some max value of results we want

	@Autowired
	private IndexProviderService indexProvider; 
		
	public HashMap<Integer, List<LuceneSearchResult>> search(String QueryText) throws IOException, ParseException {
		
		List<LuceneSearchResult> resultSet = new ArrayList<LuceneSearchResult>(); 
		Integer numDocs = 0;
		HashMap<Integer, List<LuceneSearchResult>> searchResultHash = new HashMap<Integer, List<LuceneSearchResult>>();
		
		//Instantiate the searcher
		IndexSearcher indexSearcher = indexProvider.getSearcher();
		
		//Get the text
		Query luceneQuery = buildQuery(QueryText);

		TopDocs results = indexSearcher.search(luceneQuery,topN);
		logger.info("The query is now:"+luceneQuery.toString());
		ScoreDoc[] hits = results.scoreDocs;
		numDocs = results.totalHits;  //Total number of documents found

		//int i=0;
		for (ScoreDoc hit : hits) {
			Document doc = indexSearcher.doc(hit.doc);
			LuceneSearchResult searchResult = new LuceneSearchResult(doc,hit.score);
			resultSet.add(searchResult);
			//logger.debug(searchResult);
			//i++;
		}

		searchResultHash.put(numDocs, resultSet);
		return searchResultHash;
	}

    public Query buildQuery(String freeText) throws ParseException {
    	 Analyzer analyzer = new KeywordAnalyzer();//StandardAnalyzer(Version.LUCENE_29);

        String[] productFields =
               { "title",
                 "description",              //TODO, is this in the index at all?
                 "design_value",
                 "objective",
                 "acc",
                 "organism",
                 "assay_info",
                 "characteristics",
                 "factors",
                 "contact",
                 "protocol",
                 "publication",
                 "investigation_acc",
                 "investigation_description",
                 "investigation_title",
                 "user",
                 "Metabolite"
               };

        PerFieldAnalyzerWrapper aWrapper =
                new PerFieldAnalyzerWrapper(analyzer);
        aWrapper.addAnalyzer("acc", new KeywordAnalyzer());
        aWrapper.addAnalyzer("protocol", new KeywordAnalyzer());
        aWrapper.addAnalyzer("status", new KeywordAnalyzer());
        aWrapper.addAnalyzer("investigation_acc", new KeywordAnalyzer());
        //aWrapper.addAnalyzer("investigation_description", new KeywordAnalyzer());
        //aWrapper.addAnalyzer("investigation_title", new KeywordAnalyzer());
        //aWrapper.addAnalyzer("Metabolite", new SimpleAnalyzer());


        QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_29, productFields, aWrapper);
        parser.setAllowLeadingWildcard(true);
        parser.setLowercaseExpandedTerms(false);     //TODO, if set to true, private data search does not work

        org.apache.lucene.search.Query luceneQuery = parser.parse(freeText);

        System.out.println("luceneQuery = " + luceneQuery);
        return luceneQuery;
    }
	
	
	public String getIndexDirectory () {
		return indexProvider.getIndexDirectory(); 
	}
	
	/**
	 * Calculates values for page links to page through search results, used for display in JSP.<br>
	 * Example: there are 304 results, we use a display page size of 10. So there are 31 pages to show in total.
	 * We keep track of the current page, and with this function define a page range around that.<br>
	 * If the user were to be on page 14, and we show a maximum of 10 other pages to go to, we'd want a result
	 * here of [9 10 11 12 13 14 15 16 17 18 19] as page-links to click. The is conform Google pagin style, you limit
	 * the span because if you have a large amount of pages, you don't show them all but only the ones close by. 
	 * 
	 * @param pageSize
	 * @param totalHits
	 * @param currentPage
	 * @return {l,r,t} with l being the left most page number of the pager, r the right, t total number of pages
	 */
	public int[] getPagingRange (double pageSize, double totalHits, int currentPage) {

		// how many links should be displayed max
		int maxPagerSpan=10;
		
		int totalNumberOfPages = (int)(Math.ceil(totalHits/pageSize));
		int left=currentPage, right=currentPage;

		calc_span:
		for (int i = 0; i < maxPagerSpan-1; ) {
			int incr=0;
			if(left-1 >=1) {
				left--;
				incr++;
			}
			if (right-left+1 ==maxPagerSpan )
				break calc_span;

			if(right+1<=totalNumberOfPages) {
				right++;
				incr++;
			}
			if (incr==0 )
				break calc_span;
			else
				i+=incr;
		}
		int[] pagerBoundaries = {left,right,totalNumberOfPages};
		return pagerBoundaries; 
	}

	@Override
	public LuceneSearchResult getStudy(String study) {

		//Search results
		HashMap<Integer, List<LuceneSearchResult>> searchResultHash = new HashMap<Integer, List<LuceneSearchResult>>(); // Number of documents and the result list found
				
		//Get the query...	
		String luceneQuery = "acc:"+ study;
		
		logger.info("Searching for "+ luceneQuery);
		
		//Get the search result...
		try {
			searchResultHash = search(luceneQuery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Cannot get the study requested:\n" + e.getMessage());
		}
		
		// Get the result (Study)
		// There must be only one
		LuceneSearchResult result = searchResultHash.values().iterator().next().get(0); 
		
		return result;
		
	}

}
