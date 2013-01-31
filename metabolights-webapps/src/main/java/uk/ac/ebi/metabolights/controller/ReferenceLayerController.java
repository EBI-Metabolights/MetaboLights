package uk.ac.ebi.metabolights.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.ebisearchservice.ArrayOfString;
import uk.ac.ebi.ebisearchservice.EBISearchService;
import uk.ac.ebi.ebisearchservice.EBISearchService_Service;
import uk.ac.ebi.metabolights.referencelayer.MetabolightsCompound;
import uk.ac.ebi.metabolights.referencelayer.RefLayerSearchFilter;
import uk.ac.ebi.metabolights.service.AppContext;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Controller for login and related actions.
 * @author Tejasvi
 */
@Controller
public class ReferenceLayerController extends AbstractController {

//	String url = "http://ash-3:8080/ebisearch/service.ebi?wsdl";
	private @Value ("#{ebiServiceURL}") String url;


	private EBISearchService ebiSearchService;

	String ChDomain = "chebi";
	String MTBLDomain = "metabolights";

    public enum ColumnMap{
    	description,
        id,
        iupac,
        name,
        organism,
        technology_type("technology type"),
        CHEBI,
        METABOLIGHTS;
        ColumnMap(String altName){this.altName = altName;}
        ColumnMap(){}
        private int index = -1;
        private String altName;
        public int getIndex(){return index;}
        public void setIndex(int index){this.index = index;}

        public String columnName(){
            if (altName != null){
                return altName;
            }

            return this.name();
        }
        
    }

    
	@RequestMapping({ "/refLayerSearch" })
	public ModelAndView searchAndDisplay(
			@RequestParam(required = false, value = "query") String query, 
			@RequestParam(required = false, value = "organisms") String[] organisms, // Parameters from the jsp file relating to the organism filter
			@RequestParam(required = false, value = "technology") String[] technology, // Parameters from the jsp file relating to the technology filter
			@RequestParam(required = false, value = "PageNumber") String PageNumber) { //paramenters are for Page number

		//Create an object for reference layer filter.
		RefLayerSearchFilter rflf = new RefLayerSearchFilter();

//		System.out.println("Technology: "+technology);

		Integer PageNumber1 = null;

		if(PageNumber == null){
			PageNumber1 = 1; //Loading the page for the first time, setting it as 1.
		} else {
			PageNumber1 = Integer.parseInt(PageNumber); //else getting it from the jsp
		}


		LinkedHashMap<String, Boolean> techHash = new LinkedHashMap<String, Boolean>();
		LinkedHashMap<String, Boolean> orgHash = new LinkedHashMap<String, Boolean>();

		LinkedHashMap<String, Boolean> orgCheckedItemsHash = new LinkedHashMap<String, Boolean>(); //Hash for checked items in organism filter
		LinkedHashMap<String, Boolean> techCheckedItemsHash = new LinkedHashMap<String, Boolean>(); //Hash for checked items in tech filter

		rflf.setOrgHash(orgHash);
		rflf.setTechHash(techHash);

		rflf.setTechCheckedItemsHash(techCheckedItemsHash);
		rflf.setOrgCheckedItemsHash(orgCheckedItemsHash);

		try {
			ebiSearchService = new EBISearchService_Service(new URL(url)).getEBISearchServiceHttpPort(); //Get service for data from EB-EYE
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		
		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("refLayerSearch"); //must match the definition attribute in tiles.xml

		mav.addObject("query", query);
		mav.addObject("Chdomain", ChDomain);
		mav.addObject("MTBLDomain", MTBLDomain);
		mav.addObject("currrentPage", PageNumber1);

		getSearchResults(rflf, mav, ChDomain, MTBLDomain, query, organisms, technology, PageNumber1);

		return mav;
	}

	public void getSearchResults(RefLayerSearchFilter rflf, ModelAndView mav, String ChDomain, String MTBLDomain, String query, String[] organisms, String[] technology, Integer PageNumber1) {

		int MTBLnumOfResults = 0;

		//Create a method to create a query
		createQuery(mav, rflf, query, organisms, technology);

		//Get number of results for the query
		MTBLnumOfResults = getDomainNumOfResults(MTBLDomain, query, rflf, MTBLnumOfResults, mav);

		//Get the entries for domain, has method for creating filter
		getDomainEntries(query, MTBLDomain, MTBLnumOfResults, rflf, mav, organisms, technology, PageNumber1);
	}

	private RefLayerSearchFilter createQuery(ModelAndView mav, RefLayerSearchFilter rflf, String query, String[] organisms, String[] technology) {

		rflf.setOrgValue(false);
		rflf.setTechValue(false);

		if(query == null){
			query = "";
		}

		if(query.equals("")){
			rflf.setModQuery("id:MTBLC*");
			rflf.setStudiesModQuery("id:MTBLS*");
		} else {
			rflf.setModQuery("("+query+") AND (id:MTBLC*)"); //Modifying the query from the search box from the ref layer page
			rflf.setStudiesModQuery("("+query+") AND (id:MTBLS*)");
		}

		if(organisms != null){

			StringBuffer orgSB = new StringBuffer();

			rflf.setOrgCheckedItems(organisms);
			rflf.setOrgSB(orgSB);

			int orgChkdLen = rflf.getOrgCheckedItems().length;
			for(int oci=0; oci<orgChkdLen; oci++){
				Boolean ValTrue = true;
				rflf.getOrgCheckedItemsHash().put(rflf.getOrgCheckedItems()[oci], ValTrue);
				if(oci == 0){
					rflf.getOrgSB().append("(organism:"+"\""+rflf.getOrgCheckedItems()[oci]+"\""+")"); //appending organisms after they are checked in the ref layer page
				} else {
					rflf.getOrgSB().append(" OR "+"(organism:"+"\""+rflf.getOrgCheckedItems()[oci]+"\""+")"); //appending organisms with 'or' after they are checked in the ref layer page
				}
			}

			rflf.setOrgQuery(rflf.getModQuery() + " AND ("+orgSB+")"); //modifying query to include organisms
			rflf.setStudiesOrgQuery(rflf.getStudiesModQuery() + " AND ("+orgSB+")");
		} else {
			rflf.setOrgClear(true);
		}

		if(technology != null){

			StringBuffer techSB = new StringBuffer();
			rflf.setTechCheckedItems(technology);
			rflf.setTechSB(techSB);

			int techChkdLen = rflf.getTechCheckedItems().length;

			for(int tcl=0; tcl<techChkdLen; tcl++){
				Boolean ValTrue = true;
				rflf.getTechCheckedItemsHash().put(rflf.getTechCheckedItems()[tcl], ValTrue);

				if(tcl == 0){
					rflf.getTechSB().append("(technology_type:"+"\""+rflf.getTechCheckedItems()[tcl]+"\""+")"); //appending tech after they are checked in the ref layer page
				} else {
					rflf.getTechSB().append(" OR "+"(technology_type:"+"\""+rflf.getTechCheckedItems()[tcl]+"\""+")"); //appending tech with 'or' after they are checked in the ref layer page
				}
			}

			if(rflf.getOrgSB() != null){
				rflf.setTechQuery(rflf.getModQuery() + " AND ("+rflf.getOrgSB()+") AND ("+techSB+")"); //modifying query to include organisms and tech
				rflf.setStudiesTechQuery(rflf.getStudiesModQuery() + " AND ("+rflf.getOrgSB()+") AND ("+techSB+")");
				//System.out.println(rflf.getModQuery() + " AND ("+rflf.getOrgSB()+") AND ("+techSB+")");
			} else {
				rflf.setTechQuery(rflf.getModQuery() +" AND ("+techSB+")"); //modifying query to include tech only
				rflf.setStudiesTechQuery(rflf.getStudiesModQuery() +" AND ("+techSB+")");
				//System.out.println(rflf.getModQuery() +" AND ("+techSB+")");
			}
		} else {
			rflf.setTechClear(true);
		}
		
		mav.addObject("orgClear", rflf.isOrgClear());
		mav.addObject("techClear", rflf.isTechClear());
		
		return rflf;
	}

	private RefLayerSearchFilter getDomainEntries(String query, String MTBLDomain, int MTBLnumOfResults, RefLayerSearchFilter rflf, ModelAndView mav, String[] organisms, String[] technology, Integer PageNumber1) {

		ArrayOfString MTBLFields = ebiSearchService.listFields(MTBLDomain);
//		System.out.println(MTBLFieldLen);
		MTBLFields.getString().add("CHEBI");
		MTBLFields.getString().add("METABOLIGHTS");
//		int MTBLFieldLen = MTBLFields.getString().size();
//		for(int d=0; d<MTBLFieldLen; d++){
//			System.out.println(MTBLFields.getString().get(d));
//		}

		if (MTBLnumOfResults != 0) {
			
			rflf.setMTBLCResults(null);
			rflf.setMTBLSResults(null);
			
			getMTBLCResults(rflf, MTBLDomain, ebiSearchService, MTBLFields);
			getMTBLSResults(rflf, MTBLDomain, ebiSearchService, MTBLFields);

			// Declare a collection to store all the entries found
			Collection<MetabolightsCompound> mcCompsCol = new ArrayList <MetabolightsCompound>();
			Collection<MetabolightsCompound> mcStudiesCol = new ArrayList <MetabolightsCompound>();
			Collection<RefLayerSearchFilter> rflfs = new ArrayList <RefLayerSearchFilter>();

			int MTBLCLength = rflf.getMTBLCArrayOfEntriesLen();
			int MTBLSLength = rflf.getMTBLSArrayOfEntriesLen();
			
			//Instance variables for studies, it helps in calculating number of pages and remainder results on last page
			Float modMTBLSLen = (float) rflf.getMTBLSArrayOfEntriesLen(); //17 as of now.
			Float newMTBLSLen = (modMTBLSLen/5); //3.4 if total is 17
			Float newMTBLSRemainder = (modMTBLSLen % 5); //2.0 if total is 17
			String[] MTBLSLenSplit = newMTBLSLen.toString().split("\\.");
			String[] MTBLSRemainderSplit = newMTBLSRemainder.toString().split("\\.");
			String MTBLSNumOfPages =  MTBLSLenSplit[0]; //3 total number of pages, if total is 17
//			String MTBLSMoreRes = MTBLSLenSplit[1]; //4 in this case, this helps to determine the page number.
			String MTBLSRemLastPage = MTBLSRemainderSplit[0]; //2 remainder results on last page, if total is 17, 0 if reminider is multiple of 5
			
			Integer MTBLSResPagesInt = Integer.parseInt(MTBLSNumOfPages); //number of pages excluding the results on last page, if results on last page >0
			Integer MTBLSRemEntriesInt = Integer.parseInt(MTBLSRemLastPage); //results remaining on last page.
			Integer MTBLSFrom = 0;
			Integer MTBLSTo = 0;
			
			//Instance variables for Compounds, it helps in calculating number of pages and remainder results on last page
			Float modMTBLCLen = (float) rflf.getMTBLCArrayOfEntriesLen(); // total number of results. This includes compounds and studies.
			Float newLen = (modMTBLCLen/10); //Total number of results divided by 10 to get the from and to after some calculations below.
			String[] lenSplit = newLen.toString().split("\\."); // spliting the above newLen with '.'
			String bef = lenSplit[0]; //Taking the first value
			String aft = lenSplit[1]; //Taking the second value
			
			Integer MTBLCNumOfPages = Integer.parseInt(bef); //Converting from String to Integer. MTBLC number of pages
			Integer MTBLCRemEntries = Integer.parseInt(aft);//Remaining entries on last page
			Integer MTBLCFrom = 0;
			Integer MTBLCTo = 0;
			
			if(MTBLSRemEntriesInt != 0){
				MTBLSResPagesInt = MTBLSResPagesInt + 1; // increasing MTBLSResPagesInt by 1 if last page has entries.
			}
			if(MTBLCRemEntries != 0){
				MTBLCNumOfPages = MTBLCNumOfPages + 1; // increasing MTBLCNumOfPages by 1 if last page has entries.
			}
			
			
			//'from' code.
			if(PageNumber1 == 1){
				MTBLCFrom = 0;
				MTBLSFrom = 0;
			} else if((PageNumber1 <= MTBLSResPagesInt) && (PageNumber1 != 1)){
				MTBLCFrom = ((PageNumber1 * 5)-5);
				MTBLSFrom = ((PageNumber1 * 5)-5);
			} else if(PageNumber1 == (MTBLSResPagesInt+1)){
				if(MTBLSRemEntriesInt == 0){
					MTBLCFrom = ((PageNumber1 * 5)-5);
				} else {
					MTBLCFrom = ((PageNumber1 * 5) - MTBLSRemEntriesInt);
				}
			} else {
					MTBLCFrom = (((PageNumber1 * 10) - 10) - MTBLSLength);
			}
			
			//'to' code.
			if(PageNumber1 == 1){
				if(MTBLSLength >= 5){
					MTBLCTo = 5;
					MTBLSTo = 5;
				} else {
					if(MTBLCLength != 0){
						MTBLCTo = 5+(5-MTBLSRemEntriesInt);
					}
					if(MTBLSLength != 0){
						MTBLSTo = 5-(5 -MTBLSRemEntriesInt);
					}
				}
			} else if((PageNumber1 < MTBLSResPagesInt) && (PageNumber1 != 1)){ //if page number is less than number of pages studies occupies.
				MTBLCTo = (PageNumber1 * 5);
				MTBLSTo = (PageNumber1 * 5);
				
			} else if(PageNumber1 == ((MTBLSResPagesInt-1)+(MTBLCNumOfPages-1))){
				MTBLCTo = MTBLCLength;
				MTBLSTo = MTBLSLength;
				
			} else if ((PageNumber1 == MTBLCNumOfPages)){
				MTBLCTo = MTBLCLength;
				
			} else if ((PageNumber1 == MTBLSResPagesInt)){
				MTBLSTo = MTBLSLength;
				
			} else if((PageNumber1 == MTBLSResPagesInt) && (PageNumber1 != 1)){ //If the page number is eq to number of pages studies occupies.
				if(MTBLSRemEntriesInt != 0){ // checking if there are studies on last page
					MTBLCTo = ((PageNumber1*5) + (5-MTBLSRemEntriesInt)); //setting MTBLCTo depending on studies on last page
				} else {
					if(MTBLSLength != 0){ //checking if there are studies
						MTBLCTo = (PageNumber1*5); //setting it to multiple 5.
					} else {
						MTBLCTo = (PageNumber1*10); // else setting it to multiple of 10.
					}
				}
				
				if(MTBLSRemEntriesInt != 0){
					MTBLSTo = ((PageNumber1*5) - (5-MTBLSRemEntriesInt));
				} else {
					if(MTBLSLength != 0){
						MTBLSTo = (PageNumber1*5);
					} else {
						MTBLSTo = (PageNumber1*10);
					}
				}
			}  else {
				MTBLCTo = (MTBLCFrom + 10);
			}
			

            // Map the column Names with the indexes
            mapColumns(MTBLFields);
            
            // Store the metabolite entry in the collection
            getCompoundsEntry(rflf, MTBLCFrom, MTBLCTo, mcCompsCol, technology, organisms, mav);
            
            // Store the studies entry in the collection
            getStudiesEntry(rflf, MTBLSFrom, MTBLSTo, mcStudiesCol, technology, organisms, mav);
			
			rflfs.add(rflf);
			mav.addObject("RefLayer", rflfs);
			mav.addObject("technologyList", rflf.getTechHash());
			mav.addObject("organismList", rflf.getOrgHash());
			mav.addObject("query", query);
			mav.addObject("entriesForComps", mcCompsCol);
			mav.addObject("entriesForStudies", mcStudiesCol);
			mav.addObject("MTBLCResults", rflf.getMTBLCResults().getString());
			mav.addObject("MTBLSResults", rflf.getMTBLSResults().getString());
			mav.addObject("queryResults", MTBLnumOfResults);
			mav.addObject("orgHashLen", rflf.getOrgHash().size());
		}
		return rflf;
	}

	private RefLayerSearchFilter getStudiesEntry(RefLayerSearchFilter rflf, Integer MTBLSFrom,
			Integer MTBLSTo, Collection<MetabolightsCompound> mcStudiesCol,
			String[] technology, String[] organisms, ModelAndView mav) {
		
		if(MTBLSTo != 0){
    		for(int s=MTBLSFrom; s<MTBLSTo; s++){
    				
   				 // Get the ebiEye entry for studies
   				List<String> ebiEyeEntryForStudies = rflf.getMTBLSArrayOfEntries().getArrayOfString().get(s).getString();

   				// Instantiate a new entry...
   				MetabolightsCompound mcStudies = ebieyeEntry2Metabolite(ebiEyeEntryForStudies);

   				mcStudiesCol.add(mcStudies);
   			}
   			
   			for(int j=0; j<rflf.getMTBLSArrayOfEntriesLen(); j++){
   				rflf.setMTBLSEntries(rflf.getMTBLSArrayOfEntries().getArrayOfString().get(j));
   				rflf.setTechnology1(technology);
   				rflf.setOrganisms(organisms);
   				rflf.setOrgType(rflf.getMTBLSEntries().getString().get(5));
   				rflf.setTechType(rflf.getMTBLSEntries().getString().get(6));
   				
   				//Setup filters, pass technology and organism arrays as POJO in rflf.
   				refLayerFilterSetup(rflf, mav);
   			}
        }
		return rflf;
	}

	private RefLayerSearchFilter getCompoundsEntry(RefLayerSearchFilter rflf, Integer MTBLCFrom, Integer MTBLCTo, Collection<MetabolightsCompound> mcCompsCol, String[] technology, String[] organisms, ModelAndView mav) {
				
        if(MTBLCTo != 0){
			for(int c=MTBLCFrom; c<MTBLCTo; c++){

                // Get the ebiEye entry for compounds
                List<String> ebiEyeEntryForCompounds = rflf.getMTBLCArrayOfEntries().getArrayOfString().get(c).getString();

				// Instantiate a new entry...
				MetabolightsCompound mcComp = ebieyeEntry2Metabolite(ebiEyeEntryForCompounds);

				mcCompsCol.add(mcComp);
			}

			for(int i=0; i<rflf.getMTBLCArrayOfEntriesLen(); i++){

				rflf.setMTBLCEntries(rflf.getMTBLCArrayOfEntries().getArrayOfString().get(i));
				//String queryID = MTBLEntries.getString().get(2); // Id of the compound in Metabolights
				rflf.setTechnology1(technology);
				rflf.setOrganisms(organisms);
				rflf.setOrgType(rflf.getMTBLCEntries().getString().get(5)); //gets single or multiple organism(s) depending on studies
				rflf.setTechType(rflf.getMTBLCEntries().getString().get(6)); // gets single or multiple technology_type(s) depending on studies.

				//Setup filters, pass technology and organism arrays as POJO in rflf.
				refLayerFilterSetup(rflf, mav);
			}
        }
		return rflf;
		
	}

	private RefLayerSearchFilter getMTBLSResults(RefLayerSearchFilter rflf, String MTBLDomain, EBISearchService ebiSearchService, ArrayOfString MTBLFields) {
		if((rflf.getStudiesTechQuery()) != null){
			rflf.setMTBLSResults (ebiSearchService.getAllResultsIds(MTBLDomain, rflf.getStudiesTechQuery()));
		} else if((rflf.getStudiesOrgQuery()) != null){
			rflf.setMTBLSResults (ebiSearchService.getAllResultsIds(MTBLDomain, rflf.getStudiesOrgQuery()));
		} else {
			rflf.setMTBLSResults (ebiSearchService.getAllResultsIds(MTBLDomain, rflf.getStudiesModQuery()));
		}
		
		rflf.setMTBLSArrayOfEntries(ebiSearchService.getEntries(MTBLDomain, rflf.getMTBLSResults(), MTBLFields));
		rflf.setMTBLSArrayOfEntriesLen(rflf.getMTBLSArrayOfEntries().getArrayOfString().size());
		return rflf;
	}

	private RefLayerSearchFilter getMTBLCResults(RefLayerSearchFilter rflf, String MTBLDomain, EBISearchService ebiSearchService, ArrayOfString MTBLFields) {
		if((rflf.getTechQuery()) != null){
			rflf.setMTBLCResults (ebiSearchService.getAllResultsIds(MTBLDomain, rflf.getTechQuery()));
		} else if((rflf.getOrgQuery()) != null){
			rflf.setMTBLCResults (ebiSearchService.getAllResultsIds(MTBLDomain, rflf.getOrgQuery()));
		} else {
			rflf.setMTBLCResults (ebiSearchService.getAllResultsIds(MTBLDomain, rflf.getModQuery()));
		}
		
		rflf.setMTBLCArrayOfEntries(ebiSearchService.getEntries(MTBLDomain, rflf.getMTBLCResults(), MTBLFields));
		rflf.setMTBLCArrayOfEntriesLen(rflf.getMTBLCArrayOfEntries().getArrayOfString().size());
		return rflf;
		
	}

	@SuppressWarnings("unchecked")
	private RefLayerSearchFilter refLayerFilterSetup(RefLayerSearchFilter rflf, ModelAndView mav) {

		if(rflf.getTechType() != null){
			rflf.setTechSplit(rflf.getTechType().split("\\n")); //split the technologies with \n
			rflf.setTechSplitLen(rflf.getTechSplit().length); //set the number of technologies, in this case 2.
			
			for(int t=0; t<rflf.getTechSplitLen(); t++){
				if(!rflf.getTechHash().containsKey(rflf.getTechSplit()[t])){
					if(rflf.getTechType() != null){
						rflf.getTechHash().put(rflf.getTechSplit()[t], rflf.getTechValue()); //resetting the for all value to false
					}
				}
			}
			
			if(rflf.getTechCheckedItems() != null){
				rflf.setTechCheckedItemsSet(rflf.getTechCheckedItemsHash().keySet()); //setting LinkedHashMap with tech checked items
				int techChkdItemsLen = rflf.getTechCheckedItems().length;
				Iterator<String> techIter = rflf.getTechCheckedItemsSet().iterator();
				while(techIter.hasNext()){
//					System.out.println(techChkdItemsLen + " - Entered while for techIter.hasnext");
					String techTmpKey = (String) techIter.next(); //contains a single checked item (key)
					for(int it=0; it<techChkdItemsLen; it++){
						Boolean techValueTrue = true;
						rflf.getTechHash().remove(techTmpKey); 
						rflf.getTechHash().put(techTmpKey, techValueTrue);
					}
				}
			}
		}

		if(rflf.getOrgType() != null){
			rflf.setOrgSplit(rflf.getOrgType().split("\\n")); //split the organisms with \n
			rflf.setOrgSplitLen(rflf.getOrgSplit().length); //set the number of organisms. The length can vary according to study. 1-4.
			
			
			for(int o=0; o<rflf.getOrgSplitLen(); o++){
				//getOrgSplit()[o] will contain single organism
				if(!rflf.getOrgHash().containsKey(rflf.getOrgSplit()[o])){
					if(rflf.getOrgType() != null){
						rflf.getOrgHash().put(rflf.getOrgSplit()[o], rflf.getOrgValue()); //makes value false for all the items in the list, resetting the value to false
					}
				}
			}
			if(rflf.getOrgCheckedItems() != null){
				rflf.setOrgCheckedItemsSet(rflf.getOrgCheckedItemsHash().keySet()); //setting linkedHashSet with organism checked items
				int orgChkdItemsLen = rflf.getOrgCheckedItems().length;
				Iterator<String> orgIter = rflf.getOrgCheckedItemsSet().iterator();
				while(orgIter.hasNext()){
//					System.out.println(orgChkdItemsLen + " - Entered while for orgIter.hasnext");
					String orgTmpKey = (String) orgIter.next(); //contains a single checked item (key)
					for(int o=0; o<orgChkdItemsLen; o++){
						Boolean orgValueTrue = true;
						rflf.getOrgHash().remove(orgTmpKey);
						rflf.getOrgHash().put(orgTmpKey, orgValueTrue);
					}
				}
			}
		}
		return rflf;
	}

	private int getDomainNumOfResults(String MTBLDomain1, String query, RefLayerSearchFilter rfFilter, int MTBLnumOfResults1, ModelAndView mav) {
		
		int MTBLCNumOfResults = 0;
		int MTBLSNumOfResults = 0;
		
		if ((MTBLDomain1 != null)) {
			if((rfFilter.getTechQuery()) != null){
				MTBLCNumOfResults = ebiSearchService.getNumberOfResults(MTBLDomain, rfFilter.getTechQuery());
			} else if((rfFilter.getOrgQuery()) != null){
				MTBLCNumOfResults = ebiSearchService.getNumberOfResults(MTBLDomain, rfFilter.getOrgQuery());
			} else {
				MTBLCNumOfResults = ebiSearchService.getNumberOfResults(MTBLDomain, rfFilter.getModQuery());
			}
			
			if((rfFilter.getStudiesTechQuery()) != null){
				MTBLSNumOfResults = ebiSearchService.getNumberOfResults(MTBLDomain, rfFilter.getStudiesTechQuery());
			} else if((rfFilter.getStudiesOrgQuery()) != null){
				MTBLSNumOfResults = ebiSearchService.getNumberOfResults(MTBLDomain, rfFilter.getStudiesOrgQuery());
			} else {
				MTBLSNumOfResults = ebiSearchService.getNumberOfResults(MTBLDomain, rfFilter.getStudiesModQuery());
			}
		}
		
		MTBLnumOfResults1 = MTBLCNumOfResults+MTBLSNumOfResults;
//		System.out.println(MTBLnumOfResults1);
		
		mav.addObject("allResultsLen", MTBLnumOfResults1);
		
		return MTBLnumOfResults1;
	}


    // Map the filed names of the ebieye entry with position index
    private void mapColumns(ArrayOfString fields){

        // If not already mapped (only needs to be done once)...index will have -1
        if (ColumnMap.METABOLIGHTS.getIndex() == -1){


            for (int i=0;i < fields.getString().size(); i++){

                String fieldName = fields.getString().get(i);

                // Loop through the enum (not very optimal but the enum is quite short)
                for (ColumnMap cm : ColumnMap.values()){

                    if (cm.columnName().equals(fieldName)){
                        cm.setIndex(i);
                        continue;
                    }
                }
            }
        }
    }
    // Get the value of an ebieye entry by its name
    private String getValueFromEbieyeEntry(ColumnMap columnName, List<String> ebieyeEntry){

        // If index is -1 the field is not there
        if (columnName.getIndex() == -1 ) return "";

        // If the ebeeye entry do not have that value....
        if (ebieyeEntry.size() < columnName.getIndex()){
            return "";
        }

        // Get the value
        String value = ebieyeEntry.get(columnName.getIndex());

        // If it's null
        return (value == null? "": value);


    }
    private MetabolightsCompound ebieyeEntry2Metabolite(List<String> ebieyeEntry){

        // Instantiate a new Metabolite compound ...
        MetabolightsCompound mc = new MetabolightsCompound();

        String value;

        // Get the chebiId
        value = getValueFromEbieyeEntry(ColumnMap.CHEBI, ebieyeEntry);
        mc.setChebiId(value);
        if (!value.equals("")) mc.setChebiURL(value.split(":")[1]);
        
        //Get the description
        value = getValueFromEbieyeEntry(ColumnMap.description, ebieyeEntry);
        if(!value.equals("")) mc.setDescription(value);

        // Get the studies
        value = getValueFromEbieyeEntry(ColumnMap.METABOLIGHTS, ebieyeEntry);
        if (!value.equals("")) mc.setMTBLStudies(value.split("\\s"));

        // Get the iupac names
        value = getValueFromEbieyeEntry(ColumnMap.iupac, ebieyeEntry);
        if (!value.equals("")) mc.setIupac(value.split("\\n"));

        // Get the ACCESION
        value = getValueFromEbieyeEntry(ColumnMap.id, ebieyeEntry);
        mc.setAccession(value);

        // Get the name
        value = getValueFromEbieyeEntry(ColumnMap.name, ebieyeEntry);
        mc.setName(value);
        return mc;

    }
}