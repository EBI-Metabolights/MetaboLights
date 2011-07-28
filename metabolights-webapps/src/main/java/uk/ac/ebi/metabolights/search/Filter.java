package uk.ac.ebi.metabolights.search;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.springframework.security.core.context.SecurityContextHolder;

import uk.ac.ebi.bioinvindex.search.hibernatesearch.StudyBrowseField;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.search.LuceneSearchResult.Assay;

/**
 * Manages, store, parse and does everything relted with the filter
 * @author conesa
 *
 */
public class Filter extends HashMap<String,FilterSet>{
	
	private static final String FREE_TEXT_QUERY = "freeTextQuery";

	private static final long serialVersionUID = -8290724660711400374L;

	// List with filter items, each one will be a group of checkboxes
    private FilterSet organisms = new FilterSet("organisms", StudyBrowseField.ORGANISM,"",""); 
    private FilterSet technology = new FilterSet("technology",StudyBrowseField.ASSAY_INFO,"*|","|*");
    private String freeTextQuery = "";

    // the currently displayed page number, paging through results 
    private int pageNumber=1;
    // the number of entries on a displayed page
    private final int pageSize=10;
    // Store if it is necessary to load the filter based on the results...
    private boolean isFilterLoadNeeded;
    
	public Filter (HttpServletRequest request){
		
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
	public void parseRequest(HttpServletRequest request){
			
		//Check if the filter sets must be reloaded
		checkIfFilterLoadIsNeeeded(request);
	
		//Uncheck all items
		uncheckAllFilterItems();
		
		
		// Get an enumeration of all parameters
		Enumeration paramenum = request.getParameterNames(); 
		//Loop through the enumeration
		while (paramenum.hasMoreElements()) { 
			// Get the name of the request parameter 
			String name = (String)paramenum.nextElement(); 
	
			//Which page are we on
			if (name.equals("pageNumber")) {
				pageNumber = Integer.valueOf(request.getParameter(name));
				continue;
			}

			//Get the Corresponding filterSet
			FilterSet fs = this.get(name);
		
			//If exists...
			if (fs != null){

				// If the request parameter can appear more than once in the query string, get all values 
				String[] values = request.getParameterValues(name); 

				for (int i=0; i<values.length; i++) { 
					
					//No longer needed: we are using session object to store the this class
//					//Add a filter item to the corresponding filterset
//					FilterItem fi = new FilterItem(values[i],fs.getName());
//					fi.setIsChecked(true);
//					fs.put(fi.getValue(), fi);
					
					//We need to update the status (checked/unchecked)
					FilterItem fi = fs.get(values[i]);
					fi.setIsChecked(true);
				}
			}
		}	
	}
	private void checkIfFilterLoadIsNeeeded(HttpServletRequest request){
		
		//Get the free text (if any...)
		String freeTextQueryFromRequest = request.getParameter(FREE_TEXT_QUERY);

		//If text from the request equals the previous one...
		if (freeTextQueryFromRequest.equals(freeTextQuery)){
			isFilterLoadNeeded = false;
			
		}else{

			//Update free text query with the new one
			freeTextQuery = freeTextQueryFromRequest;
			
			isFilterLoadNeeded = true;
		}
		
		//if needed...
		if (isFilterLoadNeeded) {
			//...clear the filter items....later they must be populated based on the resultset
			clearAllFilterItems();
		}
		
		
		
	}
	
	private void uncheckAllFilterItems(){
		for (FilterSet fs:this.values()){
			for (FilterItem fi: fs.values()){
				fi.reset();
			}
		}
	}
	private void clearAllFilterItems(){
		for (FilterSet fs:this.values()){
			fs.clear();
		}	
	}
	/**
	 * Composes the query to be run into lucene search engine using the loaded parameters.
	 * @return
	 */
	public String getLuceneQuery(){
		
		//Start with the freeTextQuery
		String luceneQuery = freeTextQuery.isEmpty()? "" :  value2Lucene("*" + freeTextQuery + "*") ;
		
				
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
				
				//If filter item id checked
				if (fi.getIsChecked()){
					//Join terms with an OR
					luceneQueryBlock = joinFilterTerms(luceneQueryBlock, field + ":" + value2Lucene(fs.getPrefix() + fi.getValue() + fs.getSuffix()) , "OR") ;
				}
				
			}
			
			//Join Blocks with an and
			luceneQuery = joinFilterTerms(luceneQuery, luceneQueryBlock, "AND");
			
		}
						
		//Add the filter for private studies...
		luceneQuery = joinFilterTerms(getStatusFilter(), luceneQuery, "AND");
		
		return luceneQuery;
		
	}
	
	private String getStatusFilter(){
		
		//To start, all public studies can be retrieved
		String statusFilter = "status:PUBLIC";
		
		//If the user is not empty
		if (!this.getUserName().isEmpty()){
			//Add an or clause for the private ones the user owns
			statusFilter = joinFilterTerms(statusFilter, "user:username\\:" + this.getUserName() + "*", "OR");
		}
		
		//Return the filter
		return statusFilter;
	}
	private String joinFilterTerms (String term1, String term2, String op){
				
		if(term1.isEmpty()) {return term2;}
		if(term2.isEmpty()) {return term1;}
		
		return "(" + term1 + " " + op + " " + term2 + ")";
		
	}
	
	private String value2Lucene(String value){
		
		//Replace special characters...
		value = value.replace(" ", "\\ ").replace("(", "\\(").replace(")","\\)");
			
		return value;
	}
	public boolean getIsFilterLoadNeeded(){
		return isFilterLoadNeeded;
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
	private String getUserName(){
		
		String userName = "";
		
		//If there is any user...
		if ( SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof MetabolightsUser){
		
			//Get the user
			MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		
			userName =user.getUserName();
		}
		
		return userName;
	}
}
