package uk.ac.ebi.metabolights.search;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import uk.ac.ebi.bioinvindex.search.hibernatesearch.StudyBrowseField;
import uk.ac.ebi.metabolights.search.LuceneSearchResult.Assay;

/**
 * Manages, store, parse and does everything relted with the filter
 * @author conesa
 *
 */
public class Filter extends HashMap<String,FilterSet>{
	
	private static final long serialVersionUID = -8290724660711400374L;

	//List with filter items, each one will be a group of checkboxes
    private FilterSet organisms = new FilterSet("organisms", StudyBrowseField.ORGANISM); 
    private FilterSet technology = new FilterSet("technology",StudyBrowseField.ASSAY_INFO);
    private String freeTextQuery = "";

    // the currently displayed page number, paging through results 
    private int pageNumber=1;
    // the number of entries on a displayed page
    private final int pageSize=10;    
    
	public Filter(HttpServletRequest request){
	
		//Mount the structure
		mountFilterStructure();
	
	    //Fill the filter hash with the parameters
	    parseRequest(request);
	    
	}
	
	public String getFreeTextQuery(){
		return freeTextQuery;
	}

	public int getPageNumber(){
		return pageNumber;
	}

	public int getPageSize(){
		return pageSize;
	}

	
	private void mountFilterStructure(){
		
		//Add all the list of filters to the hash...
		this.put(organisms.getName(), organisms);
		this.put(technology.getName(), technology);
	}

	
	/**request can come with 2 parameters:
	 * query: freetext query
	 * form with all the possible filters
	 * @param request
	 * @return
	 */
	private void parseRequest(HttpServletRequest request){
			
		// Get an enumeration of all parameters
		Enumeration paramenum = request.getParameterNames(); 
		//Loop through the enumeration
		while (paramenum.hasMoreElements()) { 
			// Get the name of the request parameter 
			String name = (String)paramenum.nextElement(); 
			
			//If the parameter is the freetext one
			if (name.equals("freeTextQuery")) {
				//There should be only one...
				freeTextQuery = request.getParameter(name);
				continue;
			}

			//Which page are we on
			if (name.equals("pageNumber")) {
				pageNumber = Integer.valueOf(request.getParameter(name));
				continue;
			}

			
			//Get the Corresponding filterSet
			FilterSet fs = this.get(name);
		
			// Get the value of the request parameter 
			//String value = request.getParameter(name); 

			//If exists...
			if (fs != null){

				// If the request parameter can appear more than once in the query string, get all values 
				String[] values = request.getParameterValues(name); 

				for (int i=0; i<values.length; i++) { 
					
					/* do something */
					System.out.println("name: " + name + ", value: " + values[i]);
					
					//Add a filter item to the corresponding filterset
					FilterItem fi = new FilterItem(values[i],fs.getName());
					fi.setIsChecked(true);
					fs.put(fi.getValue(), fi);
				}
			}
		}	
	}
	/**
	 * Composes the query to be run into lucene search engine using the loaded parameters.
	 * @return
	 */
	public String getLuceneQuery(){
		
		String luceneQuery = freeTextQuery.isEmpty()? "*" :  "*" + value2Lucene(freeTextQuery) + "*";
		
				
		//Go through the Filters set
		for (Entry<String,FilterSet> entry: this.entrySet()){
			
			//Get the filter set organisms, technologies,... 
			FilterSet fs = entry.getValue();
			
			//Get the lucene index field name
			String field = fs.getField().getName();
			
			String luceneQueryBlock="";
			
			//For each filter item we shall make an OR filter
			for (Entry<String,FilterItem> entry1: fs.entrySet()){
				
				//Get the filter item.
				FilterItem fi = entry1.getValue();
			
				//Join terms with an OR
				luceneQueryBlock = joinFilterTerms(luceneQueryBlock, field + ":*" + value2Lucene(fi.getValue()) + "*", "OR") ;
				
			}
			
			//Join Blocks with an and
			luceneQuery = joinFilterTerms(luceneQuery, luceneQueryBlock, "AND");
			
		}
						
		return luceneQuery;
		
	}
	private String joinFilterTerms (String term1, String term2, String op){
				
		if(term1.isEmpty()) {return term2;}
		if(term2.isEmpty()) {return term1;}
		
		return "(" + term1 + " " + op + " " + term2 + ")";
		
	}
	
	private String value2Lucene(String value){
		return value.replace(" ", "\\ ").replace("(", "\\(").replace(")","\\)");
	}
	/**
	 * Load all the possible unique filter items to be offer them in the page.
	 * @param resultSet
	 */
	public void loadFilter(List<LuceneSearchResult> resultSet){
		
		//Get the iterator
		Iterator iter = resultSet.iterator();
		
		//While there are results...
		while (iter.hasNext()){ //Is there a better Lucene way of doing this?  Getting unique values from the index?
			
			//Get the result (one lucene index document)
			LuceneSearchResult result = (LuceneSearchResult) iter.next();
			
			//Add unique entries to the list
			
			//If there is any organism
			if (result.getOrganism()!=null){
				
				//If we haven't stored the item yet...
				if(!organisms.containsKey(result.getOrganism())){
					organisms.put( result.getOrganism(), new FilterItem(result.getOrganism(), organisms.getName()));
				}
		
				//Increase the count of organisms
				organisms.get(result.getOrganism()).addToNumber(1);
			}
			
			//Get the list of technologies..
			Iterator <Assay> assIter = result.getAssays().iterator();
			while (assIter.hasNext()){
				Assay assay = (Assay) assIter.next();
				if (!technology.containsKey(assay.getTechnology())){
					technology.put(assay.getTechnology(), new FilterItem ( assay.getTechnology() , technology.getName()));
				}

				//Increase the count of technologies
				technology.get(assay.getTechnology()).addToNumber(assay.getCount());
				
			}
		}
	}
}
