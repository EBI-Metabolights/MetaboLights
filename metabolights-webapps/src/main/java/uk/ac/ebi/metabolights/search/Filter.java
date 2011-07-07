package uk.ac.ebi.metabolights.search;

import java.util.Enumeration;
import java.util.HashMap;
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
	
    //List with filter items, each one will be a group of checkboxes
    private FilterSet organisms = new FilterSet("organisms", StudyBrowseField.ORGANISM); 
    private FilterSet technology = new FilterSet("technology",StudyBrowseField.ASSAY_INFO);
    
    private String query;
    
	public Filter(HttpServletRequest request){
	
		//Mount the structure
		mountFilterStructure();
		
		//Get the user free text search
	    query = request.getParameter("query")!=null? request.getParameter("query"): "";   
	
	    //Fill the filter hash with the parameters
	    parseRequest(request);
	    
	}

	private void mountFilterStructure(){
		
		//Add all the list of filters to the hash...
		this.put(organisms.getName(), organisms);
		this.put(technology.getName(), technology);
		
	}

	
	
	public String getQuery(){ return query;}
	
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
	public void loadFilter(List<LuceneSearchResult> resultSet){
		
		//Get the iterator
		Iterator iter = resultSet.iterator();
		
		//While there are results...
		while (iter.hasNext()){ //Is there a better Lucene way of doing this?  Getting unique values from the index?
			
			//Get the result (one lucene index document)
			LuceneSearchResult result = (LuceneSearchResult) iter.next();
			
			//If we haven't stored the item yet...
			if (result.getOrganism()!=null && !organisms.containsKey(result.getOrganism())) //Add unique entries to the list
			
				organisms.put( result.getOrganism(), new FilterItem(result.getOrganism(), organisms.getName()));

			
			
			//Get the list of technologies..
			Iterator <Assay> assIter = result.getAssays().iterator();
			while (assIter.hasNext()){
				Assay assay = (Assay) assIter.next();
				if (!technology.containsKey(assay.getTechnology()))
					technology.put(assay.getTechnology(), new FilterItem ( assay.getTechnology() , technology.getName())); 
			}
		}
	}
}
