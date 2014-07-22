package uk.ac.ebi.metabolights.webapp;

public class MetaboLightsUtilities {
	public static String getLink(String id){
		String PRIORITYIDPATTERNS = "^CHEBI:[0-9]+$~^HMDB[0-9]+$~^LM[A-Z]{2}[0-9]+$~^C[0-9]{5}$~MTBLC[0-9]+$";
		String ACCESSION_URLS =  "http://www.ebi.ac.uk/chebi/searchId.do?chebiId=" +
					  "~http://www.hmdb.ca/metabolites/" +
					  "~http://www.lipidmaps.org/data/LMSDRecord.php?LMID=" +
					  "~http://www.genome.jp/dbget-bin/www_bget?cpd:" +
					  "~ ";
		
    	/**
    	 * URL Samples for ID:
    	 * CHEBI:		http://www.ebi.ac.uk/chebi/searchId.do?chebiId=CHEBI:15365			(Whole value)
    	 * HMDB:		http://www.hmdb.ca/metabolites/HMDB03459							(Whole value)
    	 * LIPIDMAPS:	http://www.lipidmaps.org/data/LMSDRecord.php?LMID=LMFA01010001		(Whole Value)
    	 * KEGG:		http://www.genome.jp/dbget-bin/www_bget?cpd:C01401					(Whole Value)
		 * MTBLC:		MTBLC12345															(relative)
    	 */
		
		
		
    	// If value is not null
    	if (id != null){
    		
    		// EXCEPTIONS ----------
    		// PubChem
    		if (id.startsWith("CID")){
    			return "http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid=" + id.replace("CID", "");
    		}
    		
    		
    		String[] remotePriorityPatterns = PRIORITYIDPATTERNS.split("~");
    		String[] remoteAccessionURL = ACCESSION_URLS.split("~");
    		
    		for (int i =0; i <remotePriorityPatterns.length; i++){
    			
    			// Get the pattern
    			String pattern = remotePriorityPatterns[i];
    			
    			// If the value matches the pattern...
    			if (id.matches(pattern)){
    				
    				// Get the url
    				String url = remoteAccessionURL[i];
    				
    				// If the url is not empty...
    				if (!url.isEmpty()){
    					
    					// Append the id at the end...
    					url = url + id;
    					
    					// Return the link..
    					return url;
    					
    					
    				}
    			}
    		}
    	}
    	
    	return "";
	} // End method

}
