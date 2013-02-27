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

    private @Value ("#{ebiServiceURL}") String url;


    private EBISearchService ebiSearchService;

    String MTBLDomainName = "metabolights";

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
            @RequestParam(required = false, value = "query") String userQuery,
            @RequestParam(required = false, value = "organisms") String[] organismsSelected, // Parameters from the jsp file relating to the organism filter
            @RequestParam(required = false, value = "technology") String[] technologiesSelected, // Parameters from the jsp file relating to the technology filter
            @RequestParam(required = false, value = "PageNumber") String PageSelected) { //Parameters from the jsp for Page number

        //Create an object for reference layer filter.
        RefLayerSearchFilter rflf = new RefLayerSearchFilter();

        Integer currentPage = getCurrentPage(PageSelected);

        initiateFacetHash(rflf);

        try {
            ebiSearchService = new EBISearchService_Service(new URL(url)).getEBISearchServiceHttpPort(); //Get service for data from EB-EYE
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("refLayerSearch"); //must match the definition attribute in tiles.xml

        getSearchResults(rflf, mav, MTBLDomainName, userQuery, organismsSelected, technologiesSelected, currentPage);

        mav.addObject("query", userQuery);
        mav.addObject("MTBLDomain", MTBLDomainName);
        mav.addObject("currentPage", currentPage);

        return mav;
    }

    private Integer getCurrentPage(String pageSelected) {
        Integer currentPage = 0;

        if(pageSelected == null){
            currentPage = 1; //Loading the page for the first time, set it to 1.
        } else {
            currentPage = Integer.parseInt(pageSelected); //else getting it from the jsp
        }
        return currentPage;
    }

    private void initiateFacetHash(RefLayerSearchFilter rflf) {

        rflf.setOrgHash(new LinkedHashMap<String, String>());
        rflf.setTechHash(new LinkedHashMap<String, String>());

        rflf.setOrgNoDim(new LinkedHashMap<String, String>());
        rflf.setTechNoDim(new LinkedHashMap<String, String>());

        rflf.setTechCheckedItemsHash(new LinkedHashMap<String, String>());
        rflf.setOrgCheckedItemsHash(new LinkedHashMap<String, String>());
    }

    public void getSearchResults(RefLayerSearchFilter rflf, ModelAndView mav, String MTBLDomainName, String userQuery, String[] organismsSelected, String[] technologiesSelected, Integer currentPage) {


        //Create a method to create and modify query
        modifyQuery(mav, rflf, userQuery, organismsSelected, technologiesSelected);

        //Get number of results for the query
        int MTBLNumOfResults = getMTBLNumberOfResults(MTBLDomainName, rflf);

        //Get the entries for domain, has method for creating filter
        getEntriesForMetabolights(userQuery, MTBLDomainName, MTBLNumOfResults, rflf, mav, currentPage);
    }

    private RefLayerSearchFilter modifyQuery(ModelAndView mav, RefLayerSearchFilter rflf, String userQuery, String[] organismsSelected, String[] technologiesSelected) {

        rflf.setOrgValue("false");
        rflf.setTechValue("false");

        if(userQuery == null){
            userQuery = "";
        }

        if(userQuery.equals("")){
            rflf.setModQuery("id:MTBL*");
        } else {
            rflf.setModQuery("("+userQuery+")"); //Modifying the query from the search box from the ref layer page
        }

        if(organismsSelected != null){

            rflf.setOrgCheckedItems(organismsSelected);
            rflf.setModifyQueryToInclOrgs(new StringBuffer());

            int orgChkdLen = rflf.getOrgCheckedItems().length;
            for(int oci=0; oci<orgChkdLen; oci++){
                String valTrue = "true";
                rflf.getOrgCheckedItemsHash().put(rflf.getOrgCheckedItems()[oci], valTrue);
                if(oci == 0){
                    rflf.getModifyQueryToInclOrgs().append("(organism:"+"\""+rflf.getOrgCheckedItems()[oci]+"\""+")"); //appending organisms after they are checked in the ref layer page
                } else {
                    rflf.getModifyQueryToInclOrgs().append(" OR "+"(organism:"+"\""+rflf.getOrgCheckedItems()[oci]+"\""+")"); //appending organisms with 'or' after they are checked in the ref layer page
                }
            }

            rflf.setOrgQuery(rflf.getModQuery() + " AND ("+rflf.getModifyQueryToInclOrgs()+")"); //modifying query to include organisms
        }

        if(technologiesSelected != null){

            rflf.setTechCheckedItems(technologiesSelected);
            rflf.setModifyQueryToInclTech(new StringBuffer());

            int techChkdLen = rflf.getTechCheckedItems().length;

            for(int tci=0; tci<techChkdLen; tci++){
                String valTrue = "true";
                rflf.getTechCheckedItemsHash().put(rflf.getTechCheckedItems()[tci], valTrue);

                if(tci == 0){
                    rflf.getModifyQueryToInclTech().append("(technology_type:"+"\""+rflf.getTechCheckedItems()[tci]+"\""+")"); //appending tech after they are checked in the ref layer page
                } else {
                    rflf.getModifyQueryToInclTech().append(" OR "+"(technology_type:"+"\""+rflf.getTechCheckedItems()[tci]+"\""+")"); //appending tech with 'or' after they are checked in the ref layer page
                }
            }

            if(rflf.getModifyQueryToInclOrgs() != null){
                rflf.setTechQuery(rflf.getModQuery() + " AND ("+rflf.getModifyQueryToInclOrgs()+") AND ("+rflf.getModifyQueryToInclTech()+")"); //modifying query to include organisms and tech
            } else {
                rflf.setTechQuery(rflf.getModQuery() +" AND ("+rflf.getModifyQueryToInclTech()+")"); //modifying query to include tech only
            }
        }

        mav.addObject("orgClear", rflf.isOrgClear());
        mav.addObject("techClear", rflf.isTechClear());

        return rflf;
    }

    private RefLayerSearchFilter getEntriesForMetabolights(String query, String MTBLDomainName, int MTBLNumOfResults, RefLayerSearchFilter rflf, ModelAndView mav, Integer currentPage) {

        ArrayOfString listOfMTBLFields = ebiSearchService.listFields(MTBLDomainName);
        listOfMTBLFields.getString().add("CHEBI");
        listOfMTBLFields.getString().add("METABOLIGHTS");

        if (MTBLNumOfResults != 0) {

            ArrayOfString listOfMTBLResults = null;  //Get the results after user checks a filter

            if((rflf.getTechQuery()) != null){
                listOfMTBLResults = ebiSearchService.getAllResultsIds(MTBLDomainName, rflf.getTechQuery());
            } else if((rflf.getOrgQuery()) != null){
                listOfMTBLResults = ebiSearchService.getAllResultsIds(MTBLDomainName, rflf.getOrgQuery());
            } else {
                listOfMTBLResults = ebiSearchService.getAllResultsIds(MTBLDomainName, rflf.getModQuery());
            }

            //Making the filters similar to Repository
                rflf.setListOfMTBLEntries(ebiSearchService.getEntries(MTBLDomainName, listOfMTBLResults, listOfMTBLFields));
                rflf.setListOfMTBLEntriesLen(rflf.getListOfMTBLEntries().getArrayOfString().size());

                for(int i=0; i<rflf.getListOfMTBLEntriesLen(); i++){

                    rflf.setMTBLEntries(rflf.getListOfMTBLEntries().getArrayOfString().get(i));

                    if(rflf.getMTBLEntries().getString().get(5) != null) rflf.setOrgType(rflf.getMTBLEntries().getString().get(5)); //gets single or multiple organism(s) depending on studies
                    if(rflf.getMTBLEntries().getString().get(6) != null) rflf.setTechType(rflf.getMTBLEntries().getString().get(6)); // gets single or multiple technology_type(s) depending on studies.

                    String[] orgHghSplit = null;
                    if(rflf.getOrgType() != null){
                        orgHghSplit = rflf.getOrgType().split("\\n");
                        for(int os=0; os<orgHghSplit.length; os++){
                            if(!rflf.getOrgNoDim().containsKey(orgHghSplit[os])){
                                rflf.getOrgNoDim().put(orgHghSplit[os], "highlight");
                            }
                        }
                    }

                    String[] techHghSplit = null;
                    if(rflf.getTechType() != null){
                        techHghSplit = rflf.getTechType().split("\\n");
                        for(int ts=0; ts<techHghSplit.length; ts++){
                            if(!rflf.getTechNoDim().containsKey(techHghSplit[ts])){
                                rflf.getTechNoDim().put(techHghSplit[ts], "highlight");
                            }
                        }
                    }
                }

            // Declare a collection to store all the entries found
            Collection<MetabolightsCompound> mcs = new ArrayList <MetabolightsCompound>();
            Collection<RefLayerSearchFilter> rflfs = new ArrayList <RefLayerSearchFilter>();

            int numOfMTBLEntries = rflf.getListOfMTBLEntriesLen();
            int entriesFrom = 0;
            int toEntries = 0;

            Float modNumOfMTBLEntries = (float) numOfMTBLEntries; // total number of results.
            Float fractionOfEntries = (modNumOfMTBLEntries/10); //Total number of results divided by 10 to get the from and to after some calculations below.
            String[] splitNumOfMTBLEntries = fractionOfEntries.toString().split("\\."); // split the above newLen with '.'
            String firstElementAsStr = splitNumOfMTBLEntries[0]; //Taking the first value
            String lastElementAsStr = splitNumOfMTBLEntries[1]; //Taking the second value

            Integer firstElementOfSplit = Integer.parseInt(firstElementAsStr); //Converting from String to Integer.
            Integer lastElementOfSplit = Integer.parseInt(lastElementAsStr);

            if(lastElementOfSplit != 0){
                firstElementOfSplit = firstElementOfSplit + 1; // increasing firstElementOfSplit by 1 to compare if the firstElementOfSplit equals page number.
            }

            entriesFrom = ((currentPage*10)-10);

            if(currentPage.equals(firstElementOfSplit)){
                toEntries = numOfMTBLEntries; // assigns total length to 'toEntries' if Page Number variable is equal to firstElementOfSplit, Eg: 226 == 226. This is if result is not multiple of 10.
            } else {
                toEntries = (currentPage*10); // if results are 10 or multiples of 10.
            }


            // Map the column Names with the indexes
            mapColumns(listOfMTBLFields);

            for(int z=entriesFrom; z<toEntries; z++){

                // Get the ebiEye entry
                List<String> ebiEyeEntry = rflf.getListOfMTBLEntries().getArrayOfString().get(z).getString();

                // Instantiate a new entry...
                MetabolightsCompound mc = ebieyeEntry2Metabolite(ebiEyeEntry);

                mcs.add(mc);
            }

            //Code for the facets, below code is not redundant.
                ArrayOfString AllResultsForFacets = null;  //Get all results for filters

                AllResultsForFacets = ebiSearchService.getAllResultsIds(MTBLDomainName, rflf.getModQuery());

                rflf.setListOfMTBLEntriesForFacets(ebiSearchService.getEntries(MTBLDomainName, AllResultsForFacets, listOfMTBLFields));
                rflf.setListOfMTBLEntriesForFacetsLen(rflf.getListOfMTBLEntriesForFacets().getArrayOfString().size());

                for(int i=0; i<rflf.getListOfMTBLEntriesForFacetsLen(); i++){

                    rflf.setMTBLFacetEntries(rflf.getListOfMTBLEntriesForFacets().getArrayOfString().get(i));
                    if(rflf.getMTBLFacetEntries().getString().get(5) != null) {
                        rflf.setFacetOrgType(rflf.getMTBLFacetEntries().getString().get(5)); //gets single or multiple organism(s) depending on studies
                    }
                    if(rflf.getMTBLFacetEntries().getString().get(6) != null) {
                        rflf.setFacetTechType(rflf.getMTBLFacetEntries().getString().get(6)); // gets single or multiple technology_type(s) depending on studies.
                    }

                    //Setup filters, pass technology and organism arrays as POJO in rflf.
                    facetsSetup(rflf);
                }


            rflfs.add(rflf);
            mav.addObject("RefLayer", rflfs);
            mav.addObject("technologyList", rflf.getTechHash());
            mav.addObject("techLen", rflf.getTechHash().size());
            mav.addObject("orgLen", rflf.getOrgHash().size());
            mav.addObject("query", query);
            mav.addObject("entries", mcs);
            mav.addObject("queryResults", MTBLNumOfResults);
        }
        return rflf;
    }

    @SuppressWarnings("unchecked")
    //Setting up the filter
    private RefLayerSearchFilter facetsSetup(RefLayerSearchFilter rflf) {

        //Technology Filter
        if(rflf.getFacetTechType() != null){
            rflf.setTechSplit(rflf.getFacetTechType().split("\\n")); //split the technologies with \n
            rflf.setTechSplitLen(rflf.getTechSplit().length); //set the number of technologies, in this case 2.

            for(int t=0; t<rflf.getTechSplitLen(); t++){
                if(!rflf.getTechHash().containsKey(rflf.getTechSplit()[t])){
                    if(rflf.getTechSplit()[t] != null){
                        rflf.getTechHash().put(rflf.getTechSplit()[t], rflf.getTechValue()); //resetting the for all value to false
                    }
                }
            }

            if(rflf.getTechNoDim() != null){
                Iterator<String> techUnqIter = rflf.getTechNoDim().keySet().iterator();
                while(techUnqIter.hasNext()){
                    String techUnqTmp = techUnqIter.next(); //contains single highlighted item.
                    String techSetValue = "highlight";
                    rflf.getTechHash().remove(techUnqTmp);
                    rflf.getTechHash().put(techUnqTmp, techSetValue);
                }
            }

            if(rflf.getTechCheckedItems() != null){
                rflf.setTechCheckedItemsSet(rflf.getTechCheckedItemsHash().keySet()); //setting LinkedHashMap with tech checked items
                Iterator<String> techIter = rflf.getTechCheckedItemsSet().iterator();
                while(techIter.hasNext()){
                    String techTmpKey = (String) techIter.next(); //contains a single checked item (key)
                    String techValueTrue = "true";
                    rflf.getTechHash().remove(techTmpKey);
                    rflf.getTechHash().put(techTmpKey, techValueTrue);
                }
            }
        }

        //Organism filter
        if(rflf.getFacetOrgType() != null){
            rflf.setOrgSplit(rflf.getFacetOrgType().split("\\n")); //split the organisms with \n
            rflf.setOrgSplitLen(rflf.getOrgSplit().length); //set the number of organisms. The length can vary according to study. 1-4.

            for(int o=0; o<rflf.getOrgSplitLen(); o++){
                //getFacetOrgType()[o] - single organism
                if(!rflf.getOrgHash().containsKey(rflf.getOrgSplit()[o])){
                    if(rflf.getOrgSplit()[o] != null){
                         rflf.getOrgHash().put(rflf.getOrgSplit()[o], rflf.getOrgValue()); //makes value false for all the items in the list, resetting the value to false
                    }
                }
            }

            if(rflf.getOrgNoDim() != null){
                Iterator<String> orgUnqIter = rflf.getOrgNoDim().keySet().iterator();
                while(orgUnqIter.hasNext()){
                    String orgUnqTmp = orgUnqIter.next(); //contains single highlighted item.
                    String orgSetValue = "highlight";
                    rflf.getOrgHash().remove(orgUnqTmp);
                    rflf.getOrgHash().put(orgUnqTmp, orgSetValue);
                }
            }

            if(rflf.getOrgCheckedItems() != null){
                rflf.setOrgCheckedItemsSet(rflf.getOrgCheckedItemsHash().keySet()); //setting linkedHashSet with organism checked items
                Iterator<String> orgIter = rflf.getOrgCheckedItemsSet().iterator();
                while(orgIter.hasNext()){
                    String orgTmpKey = (String) orgIter.next(); //contains a single checked item (key)
                    String orgValueTrue = "true";
                    rflf.getOrgHash().remove(orgTmpKey);
                    rflf.getOrgHash().put(orgTmpKey, orgValueTrue);
                }
            }
        }
        return rflf;
    }

    //Getting total number of results in Metabolights domain.
    private int getMTBLNumberOfResults(String MTBLDomainName, RefLayerSearchFilter rfFilter) {

        int MTBLNumberOfResults = 0;

        if ((MTBLDomainName != null)) {
            if((rfFilter.getTechQuery()) != null){
                MTBLNumberOfResults = ebiSearchService.getNumberOfResults(MTBLDomainName, rfFilter.getTechQuery());
            } else if((rfFilter.getOrgQuery()) != null){
                MTBLNumberOfResults = ebiSearchService.getNumberOfResults(MTBLDomainName, rfFilter.getOrgQuery());
            } else {
                MTBLNumberOfResults = ebiSearchService.getNumberOfResults(MTBLDomainName, rfFilter.getModQuery());
            }
        }
        return MTBLNumberOfResults;
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