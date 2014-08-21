/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 7/22/14 3:23 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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
