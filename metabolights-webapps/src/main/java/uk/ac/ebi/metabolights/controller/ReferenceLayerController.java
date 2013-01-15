package uk.ac.ebi.metabolights.controller;

import java.net.MalformedURLException;


import java.net.URL;
import java.util.*;


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

/**
 * Controller for login and related actions.
 * @author Tejasvi
 */
@Controller
public class ReferenceLayerController extends AbstractController {

	String url = "http://ash-3:8080/ebisearch/service.ebi?wsdl";


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


	@RequestMapping({ "/RefLayerSearch" })
	public ModelAndView searchAndDisplay(
			@RequestParam(required = false, value = "query") String query, 
			@RequestParam(required = false, value = "organisms") String[] organisms, // Parameters from the jsp file relating to the organism filter
			@RequestParam(required = false, value = "technology") String[] technology, // Parameters from the jsp file relating to the technology filter
			@RequestParam(required = false, value = "PageNumber") String PageNumber) { //paramenters are for Page number

		//Create an object for reference layer filter.
		RefLayerSearchFilter rflf = new RefLayerSearchFilter();

		//System.out.println("NumOfPages: "+NumOfPages);

		Integer PageNumber1 = null;

		if(PageNumber == null){
			PageNumber1 = 1; //Loading the page for the first time, setting it as 1.
		} else {
			PageNumber1 = Integer.parseInt(PageNumber); //else getting it from the jsp
		}

		Integer indexSize = 10;  //setting the index size to be used in the ebiservices
		Integer beginIndex = ((PageNumber1 * indexSize) - indexSize);// (PageNumber1-1) * indexSize // setting the beginning of the index.


		Hashtable<String, Boolean> techHash = new Hashtable<String, Boolean>();
		Hashtable<String, Boolean> orgHash = new Hashtable<String, Boolean>();

		Hashtable<String, Boolean> orgCheckedItemsHash = new Hashtable<String, Boolean>(); //Hash for checked items in organism filter
		Hashtable<String, Boolean> techCheckedItemsHash = new Hashtable<String, Boolean>(); //Hash for checked items in tech filter

		rflf.setOrgHash(orgHash);
		rflf.setTechHash(techHash);

		rflf.setTechCheckedItemsHash(techCheckedItemsHash);
		rflf.setOrgCheckedItemsHash(orgCheckedItemsHash);

		try {
			ebiSearchService = new EBISearchService_Service(new URL(url)).getEBISearchServiceHttpPort(); //Get service for data from EB-EYE
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("RefLayerSearch"); //must match the definition attribute in tiles.xml

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
		MTBLnumOfResults = getDomainNumOfResults(MTBLDomain, query, rflf, MTBLnumOfResults);

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
		} else {
			rflf.setModQuery("("+query+") AND (id:MTBLC*)"); //Modifying the query from the search box from the ref layer page
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
				//System.out.println(rflf.getModQuery() + " AND ("+rflf.getOrgSB()+") AND ("+techSB+")");
			} else {
				rflf.setTechQuery(rflf.getModQuery() +" AND ("+techSB+")"); //modifying query to include tech only
				//System.out.println(rflf.getModQuery() +" AND ("+techSB+")");
			}
		}
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

			ArrayOfString MTBLResults = null;

			if((rflf.getTechQuery()) != null){
				MTBLResults = ebiSearchService.getAllResultsIds(MTBLDomain, rflf.getTechQuery());
			} else if((rflf.getOrgQuery()) != null){
				MTBLResults = ebiSearchService.getAllResultsIds(MTBLDomain, rflf.getOrgQuery());
			} else {
				MTBLResults = ebiSearchService.getAllResultsIds(MTBLDomain, rflf.getModQuery());
			}

			rflf.setMTBLArrayOfEntries(ebiSearchService.getEntries(MTBLDomain, MTBLResults, MTBLFields));
			rflf.setMTBLArrayOfEntriesLen(rflf.getMTBLArrayOfEntries().getArrayOfString().size());
			
//			for(int g=0; g<rflf.getMTBLArrayOfEntriesLen(); g++){
//				ArrayOfString aos = rflf.getMTBLArrayOfEntries().getArrayOfString().get(g);
////				System.out.println(aos);
//				int size1 = aos.getString().size();
//				for(int h=0; h<size1; h++){
//					System.out.println(aos.getString().get(h));
//				}
//				System.out.println("--------------------------");
//			}

			// Declare a collection to store all the entries found
			Collection<MetabolightsCompound> mcs = new ArrayList <MetabolightsCompound>();
			Collection<RefLayerSearchFilter> rflfs = new ArrayList <RefLayerSearchFilter>();

			//Initiating all required variables
//			String ChebiName = null;
//			String MTBLStudies = null;
//			String[] SplitChebiName = null;
//			String[] iupacSplit = null;
//			String queryIUPACName = null;
//			String[] MTBLSplit = null;

			int length = rflf.getMTBLArrayOfEntriesLen();
			int from = 0;
			int to = 0;
			
			Float modLen = (float) rflf.getMTBLArrayOfEntriesLen(); // total number of results.
			Float newLen = (modLen/10); //Total number of results divided by 10 to get the from and to after some calculations below.
			String[] lenSplit = newLen.toString().split("\\."); // spliting the above newLen with '.'
			String bef = lenSplit[0]; //Taking the first value
			String aft = lenSplit[1]; //Taking the second value
			
			Integer befInt = Integer.parseInt(bef); //Converting from String to Integer.
			Integer aftInt = Integer.parseInt(aft);
			
			if(aftInt != 0){
				befInt = befInt + 1; // increasing befInt by 1 to compare if the befInt equals page number. 
			}
			
			from = ((PageNumber1*10)-10);
			
			if(PageNumber1.equals(befInt)){
				to = length; // assigns total length to 'to' if PageNumber1 variable is equal to befInt, Eg: 226 == 226. This is if result is not multiple of 10.
			} else {
				to = (PageNumber1*10); // if results are 10 or multiples of 10.
			}


            // Map the column Names with the indexes
            mapColumns(MTBLFields);


			for(int z=from; z<to; z++){

                // Get the ebiEye entry
                List<String> ebiEyeEntry = rflf.getMTBLArrayOfEntries().getArrayOfString().get(z).getString();

				// Instantiate a new entry...
				MetabolightsCompound mc = ebieyeEntry2Metabolite(ebiEyeEntry);


// REFACTORED THIS AND MOVED IT INTO A METHOD
//				if(rflf.getMTBLArrayOfEntries().getArrayOfString().get(z).getString().get(7) != null){
//					ChebiName = rflf.getMTBLArrayOfEntries().getArrayOfString().get(z).getString().get(7); // Chebi name for the compound in Metabolights
//					SplitChebiName = ChebiName.split(":");
//					mc.setChebiURL(SplitChebiName[1]);
//					mc.setChebiId(rflf.getMTBLArrayOfEntries().getArrayOfString().get(z).getString().get(7)); // Chebi name for the compound in Metabolights
//				}
//
//				if(rflf.getMTBLArrayOfEntries().getArrayOfString().get(z).getString().get(8) != null){
//					MTBLStudies = rflf.getMTBLArrayOfEntries().getArrayOfString().get(z).getString().get(8); // Studies for that compound
//					MTBLSplit = MTBLStudies.split("\\s");
//					mc.setMTBLStudies(MTBLSplit);
//				}
//
//				if(rflf.getMTBLArrayOfEntries().getArrayOfString().get(z).getString().get(3) != null){
//					queryIUPACName = rflf.getMTBLArrayOfEntries().getArrayOfString().get(z).getString().get(3);
//					iupacSplit = queryIUPACName.split("\\n");
//					mc.setIupac(iupacSplit);
//				}
//
//				// Fill its properties...
//
//				mc.setAccession(rflf.getMTBLArrayOfEntries().getArrayOfString().get(z).getString().get(2)); // Id of the compound in Metabolights
//				mc.setName(rflf.getMTBLArrayOfEntries().getArrayOfString().get(z).getString().get(4)); //Name of the compound
//
//				//				mc.setTechnologyType(techType); // gets technology_type, which can be NMR Spectroscopy or MS.
//
//				// Store the metabolite entry in the collection
				mcs.add(mc);
			}
			//}

			for(int i=0; i<rflf.getMTBLArrayOfEntriesLen(); i++){

				rflf.setMTBLEntries(rflf.getMTBLArrayOfEntries().getArrayOfString().get(i));
				//				String queryID = MTBLEntries.getString().get(2); // Id of the compound in Metabolights
				rflf.setTechnology1(technology);
				rflf.setOrganisms(organisms);
				rflf.setOrgType(rflf.getMTBLEntries().getString().get(5)); //gets single or multiple organism(s) depending on studies
				rflf.setTechType(rflf.getMTBLEntries().getString().get(6)); // gets single or multiple technology_type(s) depending on studies.

				//Setup filters, pass technology and organism arrays as POJO in rflf.
				refLayerFilterSetup(rflf, mav);
			}

			rflfs.add(rflf);
			mav.addObject("RefLayer", rflfs);
			mav.addObject("organismList", rflf.getOrgHash());
			mav.addObject("technologyList", rflf.getTechHash());
			mav.addObject("query", query);
			mav.addObject("entries", mcs);
			mav.addObject("MTBLResults", MTBLResults.getString());
			mav.addObject("queryResults", MTBLnumOfResults);
		}
		return rflf;
	}



	private RefLayerSearchFilter refLayerFilterSetup(RefLayerSearchFilter rflf, ModelAndView mav) {

		if(rflf.getTechType() != null){
			rflf.setTechSplit(rflf.getTechType().split("\\n")); //split the technologies with \n
			rflf.setTechSplitLen(rflf.getTechSplit().length); //set the number of technologies, in this case 2.
			
			for(int t=0; t<rflf.getTechSplitLen(); t++){
				if(!rflf.getTechHash().contains(rflf.getTechSplit()[t])){
					if(rflf.getTechType() != null){
						rflf.getTechHash().put(rflf.getTechSplit()[t], rflf.getTechValue()); //resetting the for all value to false
					}
				}
			}
			if(rflf.getTechCheckedItems() != null){
				rflf.setTechCheckedItemsEnum(rflf.getTechCheckedItemsHash().keys()); //setting enum with tech checked items
				int techChkdItemsLen = rflf.getTechCheckedItems().length;
				while(rflf.getTechCheckedItemsEnum().hasMoreElements()){ 
					String techTmpKey = (String) rflf.getTechCheckedItemsEnum().nextElement(); //contains a single checked item (key)
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
				if(!rflf.getOrgHash().contains(rflf.getOrgSplit()[o])){
					if(rflf.getOrgType() != null){
						rflf.getOrgHash().put(rflf.getOrgSplit()[o], rflf.getOrgValue()); //makes value false for all the items in the list, resetting the value to false
					}
				}
			}
			if(rflf.getOrgCheckedItems() != null){
				rflf.setOrgCheckedItemsEnum(rflf.getOrgCheckedItemsHash().keys()); //setting enum with organism checked items
				int orgChkdItemsLen = rflf.getOrgCheckedItems().length;
				while(rflf.getOrgCheckedItemsEnum().hasMoreElements()){
					String orgTmpKey = (String) rflf.getOrgCheckedItemsEnum().nextElement(); //contains a single checked item (key)
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

	private int getDomainNumOfResults(String MTBLDomain1, String query, RefLayerSearchFilter rfFilter, int MTBLnumOfResults1) {

		if ((MTBLDomain1 != null)) {
			if((rfFilter.getTechQuery()) != null){
				MTBLnumOfResults1 = ebiSearchService.getNumberOfResults(MTBLDomain, rfFilter.getTechQuery());
			} else if((rfFilter.getOrgQuery()) != null){
				MTBLnumOfResults1 = ebiSearchService.getNumberOfResults(MTBLDomain, rfFilter.getOrgQuery());
			} else {
				MTBLnumOfResults1 = ebiSearchService.getNumberOfResults(MTBLDomain, rfFilter.getModQuery());
			}
		}
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