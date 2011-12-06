package uk.ac.ebi.metabolights.search;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.search.LuceneSearchResult.Assay;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Map.Entry;

/**
 * Manages, store, parse and does everything related with the filter
 * @author conesa
 *
 */
public class Filter {
	
	public static final String STATUS_PRIVATE = "PRIVATE";
	public static final String STATUS_PUBLIC = "PUBLIC";

	private static final String FREE_TEXT_QUERY = "freeTextQuery";

	private static final long serialVersionUID = -8290724660711400374L;

	// Hash of FilterSets
	Map<String,FilterSet> fss = new LinkedHashMap<String,FilterSet>();
	
	// List with filter items, each one will be a group of checkboxes
    private FilterSet organisms = new FilterSet("organisms", "organism","",""); 
    private FilterSet technology = new FilterSet("technology","assay_info","*|","|*");
    private FilterSet status = new FilterSet("status","status","","");
    private FilterSet mystudies = new FilterSet("mystudies", "user","username:", "|*");
    private String freeTextQuery = "";
    
    // the currently displayed page number, paging through results 
    private int pageNumber=1;
    // the number of entries on a displayed page
    private final int pageSize=10;
    // the initial hits (first time filter items are loaded)
    private int initialHits=0;
    // the current hits (with filters applied)
    private int currentHits=0;
    
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
	public void setFreeTextQuery(String freeTextQuery){
		this.freeTextQuery = freeTextQuery; 
	}

	public int getPageNumber(){
		return pageNumber;
	}

	public int getPageSize(){
		return pageSize;
	}
	
	public int getInitialHits(){
		return initialHits;
	}
	
	public int getCurrentHits(){
		return currentHits;
	}
	
	private void mountFilterStructure(){
		
		//Add all the list of filters to the hash...
		fss.put(status.getName(),status);
		fss.put(organisms.getName(), organisms);
		fss.put(technology.getName(), technology);
		fss.put(mystudies.getName(), mystudies);
		
	}

	private void addStatusFilterSet(){

		//Add fixed items to status filter set (PUBLIC AND PRIVATE)
		FilterItem fiPublic = new FilterItem("Public studies" ,status.getName());
		fiPublic.setValue(STATUS_PUBLIC);
		status.put(fiPublic.getValue(), fiPublic);
				
		FilterItem fiPrivate = new FilterItem("Private studies", status.getName());
		fiPrivate.setValue(STATUS_PRIVATE);
		status.put(fiPrivate.getValue(), fiPrivate);
	
		// Disable/enable this filter Set: If there is any user legged in, this filter should be enabled.
		status.setIsEnabled(!getUserName().isEmpty());
		
	}
	
	private void checkMyStudiesFilterSet(){
		
		//If the user id logged on
		String user = getUserName();
		
		if (user.isEmpty()){
			mystudies.setIsEnabled(false);
			
		} else {
			//Lets disable also here
			mystudies.setIsEnabled(false);
			FilterItem fiMyStudies = new FilterItem("My studies",mystudies.getName());
			fiMyStudies.setValue(user);
			mystudies.put(user, fiMyStudies);
		}
		
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
		
		//Reset page number
		pageNumber = 1;
		
		//Load status filters
		addStatusFilterSet();
		
		//Load myStudies filter
		checkMyStudiesFilterSet();
		
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
			FilterSet fs = fss.get(name);
		
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
					FilterItem fi = fs.getFilterItems().get(values[i]);
					fi.setIsChecked(true);
				}
			}
		}	
	}
	private void checkIfFilterLoadIsNeeeded(HttpServletRequest request){
		
				
		//Get the free text (if any...)
		String freeTextQueryFromRequest = request.getParameter(FREE_TEXT_QUERY);
		
		//If text from the request equals the previous one...
		if (freeTextQueryFromRequest == null){
			isFilterLoadNeeded = true;
		}else if(freeTextQueryFromRequest.equals(freeTextQuery)){
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
		for (FilterSet fs:fss.values()){
			for (FilterItem fi: fs.getFilterItems().values()){
				fi.reset();
			}
		}
	}

	private void clearAllFilterItems(){
		for (FilterSet fs:fss.values()){
			fs.getFilterItems().clear();
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
		for (Entry<String,FilterSet> entry: fss.entrySet()){
			
			//Get the filter set organisms, technologies,... 
			FilterSet fs = entry.getValue();
			
			//Get the lucene index field name
			String field = fs.getField();
			
			String luceneQueryBlock="";
			
			//For each filter item we shall make an OR filter
			for (Entry<String,FilterItem> entry1: fs.getFilterItems().entrySet()){
				
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
        if (!getStatusFilter().isEmpty())
		    luceneQuery = joinFilterTerms(getStatusFilter(), luceneQuery, "AND");
		
		return luceneQuery;
		
	}
	
	private String getStatusFilter(){

        if (getUser().isCurator()) //If you are a curator, display everything, so no filtering on status or username
            return "status:P*";

        //To start, all public studies can be retrieved
        String statusFilter = "status:" + STATUS_PUBLIC;

        //If the user is not empty
        if (!getUserName().isEmpty()){
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
		value = value.replace(":","\\:").replace(" ", "\\ ").replace("(", "\\(").replace(")","\\)");
			
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
				if(!organisms.getFilterItems().containsKey(result.getOrganism())){
					organisms.getFilterItems().put( result.getOrganism(), new FilterItem(result.getOrganism(), organisms.getName()));
				}
		
				//Increase the count of organisms
				organisms.getFilterItems().get(result.getOrganism()).addToNumber(1);
			}
			
			//Get the list of technologies..
			Iterator <Assay> assIter = result.getAssays().iterator();
			while (assIter.hasNext()){
				Assay assay = (Assay) assIter.next();
				if (!technology.getFilterItems().containsKey(assay.getTechnology())){
					technology.put(assay.getTechnology(), new FilterItem ( assay.getTechnology() , technology.getName()));
				}

				//Increase the count of technologies
				technology.getFilterItems().get(assay.getTechnology()).addToNumber(assay.getCount());
				
			}
			
			//STATUS count
			if (result.getIsPublic()){
				status.getFilterItems().get(STATUS_PUBLIC).addToNumber(1);
			}else{
				status.getFilterItems().get(STATUS_PRIVATE).addToNumber(1);
			}
			
		}
		
		//If it's the first load (no filter item checked and no free text search)
		if (isFilterLoadNeeded && freeTextQuery.isEmpty()){
			initialHits = resultSet.size();
		}
		
		//Get the current hits (always)
		currentHits = resultSet.size();
		
	}

    private MetabolightsUser getUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MetabolightsUser user = new MetabolightsUser();

        if (!auth.equals(new String("anonymousUser")) && auth.getPrincipal() instanceof MetabolightsUser )
		     user = (MetabolightsUser) (auth.getPrincipal());

        return user;

    }

	private String getUserName(){
		String userName = "";
		
		//If there is any user...
		if ( getUser().getUserName() != null)
			userName = getUser().getUserName();
		
		return userName;
	}


    private String getUserName2(){

		String userName = "";

		//If there is any user...
		if ( SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof MetabolightsUser){

			//Get the user
			MetabolightsUser user = (MetabolightsUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

			userName =user.getUserName();
		}

		return userName;
	}



	public Map<String,FilterSet> getFss(){
		return fss;
	}
}
