package uk.ac.ebi.metabolights.controller;

import java.net.MalformedURLException;


import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;


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
		MTBLFields.getString().add("CHEBI");
		MTBLFields.getString().add("METABOLIGHTS");

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

			// Declare a collection to store all the entries found
			Collection<MetabolightsCompound> mcs = new ArrayList <MetabolightsCompound>();
			Collection<RefLayerSearchFilter> rflfs = new ArrayList <RefLayerSearchFilter>();

			String ChebiName = null;
			String MTBLStudies = null;
			String[] SplitChebiName = null;
			String[] iupacSplit = null;
			String queryIUPACName = null;
			String[] MTBLSplit = null;

			int length = rflf.getMTBLArrayOfEntriesLen();
			int from = 0;
			int to = 0;
			
			Float modLen = (float) rflf.getMTBLArrayOfEntriesLen();
			Float newLen = (modLen/10);
			String[] lenSplit = newLen.toString().split("\\.");
			String bef = lenSplit[0];
			String aft = lenSplit[1];
			
			Integer befInt = Integer.parseInt(bef);
			Integer aftInt = Integer.parseInt(aft);
			
			if(aftInt != 0){
				befInt = befInt + 1;
			}
			
			from = ((PageNumber1*10)-10);

			if(length < 10){
				to = length;
			} else if(PageNumber1 == befInt){
				to = from + aftInt;
			} else {
				to = (PageNumber1*10);
			}

			for(int z=from; z<to; z++){
				ChebiName = rflf.getMTBLArrayOfEntries().getArrayOfString().get(z).getString().get(7); // Chebi name for the compound in Metabolights
				MTBLStudies = rflf.getMTBLArrayOfEntries().getArrayOfString().get(z).getString().get(8); // Studies for that compound

				if(queryIUPACName == null){
					queryIUPACName = "null";
				}

				SplitChebiName = ChebiName.split(":");

				iupacSplit = queryIUPACName.split("\\n");

				MTBLSplit = MTBLStudies.split("\\s");

				// Instantiate a new entry...
				MetabolightsCompound mc = new MetabolightsCompound();

				// Fill its properties...
				mc.setChebiURL(SplitChebiName[1]);
				mc.setAccession(rflf.getMTBLArrayOfEntries().getArrayOfString().get(z).getString().get(2)); // Id of the compound in Metabolights
				mc.setName(rflf.getMTBLArrayOfEntries().getArrayOfString().get(z).getString().get(4)); //Name of the compound
				mc.setIupac(iupacSplit);
				//				mc.setTechnologyType(techType); // gets technology_type, which can be NMR Sectroscopy or MS.
				mc.setChebiId(rflf.getMTBLArrayOfEntries().getArrayOfString().get(z).getString().get(7)); // Chebi name for the compound in Metabolights
				mc.setMTBLStudies(MTBLSplit);

				// Store the metabolite entry in the collection
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

		rflf.setTechSplit(rflf.getTechType().split("\\n")); //split the technologies with \n
		rflf.setTechSplitLen(rflf.getTechSplit().length); //set the number of technologies, in this case 2.		

		rflf.setOrgSplit(rflf.getOrgType().split("\\n")); //split the organisms with \n
		rflf.setOrgSplitLen(rflf.getOrgSplit().length); //set the number of organisms. The length can vary according to study. 1-4.

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
}