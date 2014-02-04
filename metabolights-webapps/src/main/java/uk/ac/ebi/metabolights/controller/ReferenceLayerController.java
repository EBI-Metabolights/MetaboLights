/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 04/02/14 14:03
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.ebisearchservice.ArrayOfArrayOfString;
import uk.ac.ebi.ebisearchservice.ArrayOfString;
import uk.ac.ebi.ebisearchservice.EBISearchService;
import uk.ac.ebi.ebisearchservice.EBISearchService_Service;
import uk.ac.ebi.metabolights.authenticate.IsaTabAuthentication;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.referencelayer.EBeyeSearchCompound;
import uk.ac.ebi.metabolights.referencelayer.RefLayerFilter;
import uk.ac.ebi.metabolights.search.LuceneSearchResult;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.SearchService;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Controller for login and related actions.
 * @author Tejasvi
 */
@Controller
public class ReferenceLayerController extends AbstractController {

    @Autowired
    private SearchService searchService;

    private @Value ("#{ebiServiceURL}") String url;
    private final String REFLAYERSESSION = "RefLayer";
    RefLayerFilter rffl;
    RefLayerFilter cacheRffl;

    private static EBISearchService ebiSearchService;
    private static final String MTBLDomainName = "metabolights";
    private static ArrayOfString listOfMTBLFields;
    private static ModelAndView mav;
    private static Logger logger = Logger.getLogger(IsaTabAuthentication.class);

    public enum UserAction {
            clickedOnPage,
            freeTextOrExample,
            checkedFacet,
            browseCached,
            firstTimeBrowse;
    }

    private UserAction ua;
    ArrayOfArrayOfString listOfMTBLEntries;

    public enum ColumnMap{
        description,
        id,
        iupac,
        name,
        organism,
        technology_type,
        CHEBI,
        METABOLIGHTS,
        study_design,
        submitter,
        study_factor,
        last_modification_date,
        has_literature,
        has_ms,
        has_nmr,
        has_pathways,
        has_reactions,
        has_species,
        study_status;

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

    private void initEBISearchService() throws Exception {

        if(ebiSearchService == null) try {
            ebiSearchService = new EBISearchService_Service(new URL(url)).getEBISearchServiceHttpPort();
        } catch (Exception e) {
            logger.info("initEBISearchService method error - "+ e);
            throw e;
        }

        listOfMTBLFields = ebiSearchService.listFields(MTBLDomainName);

        listOfMTBLFields.getString().add("CHEBI");
        listOfMTBLFields.getString().add("METABOLIGHTS");
        mapColumns();
        rffl.setMTBLNumOfResults(ebiSearchService.getNumberOfResults(MTBLDomainName, rffl.getEBIQuery()));
    }

    private void mapColumns(){

        // If not already mapped (only needs to be done once)...index will have -1
        if (ColumnMap.METABOLIGHTS.getIndex() == -1){

            for (int i=0;i < listOfMTBLFields.getString().size(); i++){

                String fieldName = listOfMTBLFields.getString().get(i);

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

    @RequestMapping({ "/clearreflayercache" })
    public ModelAndView clearCache(){
        cacheRffl = null;
        ColumnMap.METABOLIGHTS.setIndex(-1);
        return printMessage("Cache cleared.", "The cache has been cleared.");
    }

    @RequestMapping({ "/reflayersearch", "/reference" })
    public ModelAndView searchAndDisplay(
        @RequestParam(required = false, value = "freeTextQuery") String userQuery,
        @RequestParam(required = false, value = "organisms") String[] organismsSelected,
        @RequestParam(required = false, value = "technology") String[] technologiesSelected,
        @RequestParam(required = false, value = "status") String[] studyStatusSelected,
        @RequestParam(required = false, value = "PageNumber") String PageSelected,
        @RequestParam(required = false, value = "userAction") String userAction,
        HttpServletRequest request) {

        if(userQuery == null){
            userQuery = "";
        }

        rffl = (RefLayerFilter) request.getSession().getAttribute(REFLAYERSESSION);
        mav = AppContext.getMAVFactory().getFrontierMav("reflayersearch");

        //if the user is logged in show status status filter
        String showStudyStatus = null;

        if (getUser() != null)
            showStudyStatus = "1";

        mapUserAction(userQuery, organismsSelected, technologiesSelected, studyStatusSelected, PageSelected, userAction);
        try{
            queryEBI();
        } catch (Exception e){
            return new ModelAndView("redirect:index?message="+ PropertyLookup.getMessage("msg.wsdl.error"));
        }

        if(rffl.getMTBLNumOfResults() != 0){
            getEntries();
        }

        updateFacets();
        sortFacets();
        request.getSession().setAttribute("RefLayer", rffl);


        if((rffl.getFreeText().equals("")) && (cacheRffl == null)){
            cacheRffl = rffl.clone();
        }

        mav.addObject("rffl", rffl);
        mav.addObject("showStudyStatus",showStudyStatus);
        return mav;
    }

    private void sortFacets() {
        rffl.sortFacets();
    }

    private MetabolightsUser getUser(){
        //Current user context, the value is "anonymousUser" the user is not logged in
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MetabolightsUser user = null;

        if (!auth.getPrincipal().equals("anonymousUser"))
            user = (MetabolightsUser) auth.getPrincipal();

        return  user;
    }


    private void getEntries() {

        MetabolightsUser user = getUser();

        Collection<EBeyeSearchCompound> mcs = new ArrayList <EBeyeSearchCompound>();
        Integer entriesFrom = 0;
        Integer toEntries = 0;

        if(rffl.getCurrentPage().equals(rffl.getTotalNumOfPages())){
            if(rffl.getLastPageEntries() == 0){
                toEntries = 9;
            } else {
                toEntries = rffl.getLastPageEntries();
            }
        } else {
            toEntries = 10;                     //from 9
        }

        for(int z=entriesFrom; z<toEntries; z++){

            // Get the ebiEye entry
            List<String> ebiEyeEntry = listOfMTBLEntries.getArrayOfString().get(z).getString();

            // Instantiate a new entry...
            EBeyeSearchCompound mc = ebieyeEntry2Metabolite(ebiEyeEntry, user);

            mcs.add(mc);
        }

        mav.addObject("entries", mcs);
    }


    private String parseDateToString(Date date){

        String dateStr = new SimpleDateFormat("dd-MMM-yyyy").format(date);
        return dateStr;
    }

    private String formatEBeyeDateString(String date){
        String ebEyeDateFormat = "yyyy MMM dd";
        String mlDateFormat = "dd-MMM-yyyy";
        SimpleDateFormat ebEyeFormatter, mlFormatter;

        try {

            ebEyeFormatter = new SimpleDateFormat(ebEyeDateFormat);
            mlFormatter = new SimpleDateFormat(mlDateFormat);

            Date ebEyeDate = ebEyeFormatter.parse(date);
            String sampleDateStr = mlFormatter.format(ebEyeDate);

            return sampleDateStr;

        } catch (ParseException e) {
            logger.error("Cannot parse date for " + date + " : " + e.getMessage());
            return null;
        }



    }



    /**
     * Maps the search results into a compound
     * @param ebieyeEntry
     * @param user
     * @return EBeyeSearchCompound with data
     */
    private EBeyeSearchCompound ebieyeEntry2Metabolite(List<String> ebieyeEntry, MetabolightsUser user) {

        Boolean loggedIn = false;

        if (user != null) //Logged in
            loggedIn = true;
        //TODO, If user is not logged in we should ignore all private studies, BUT this can cause pagination gaps

        // Instantiate a new Metabolite compound ...
        EBeyeSearchCompound mc = new EBeyeSearchCompound();
        String value;


        //
        // COMPOUNDS and STUDIES common values
        //

        //Get the description
        value = getValueFromEbieyeEntry(ColumnMap.description, ebieyeEntry);
        if(!value.equals("")) mc.setDescription(value);

        // Get the ACCESION
        value = getValueFromEbieyeEntry(ColumnMap.id, ebieyeEntry);
        mc.setAccession(value);
        String accessionNumber = value;

        // Get the name
        value = getValueFromEbieyeEntry(ColumnMap.name, ebieyeEntry);
        mc.setName(value);

        //Get Technology
        value = getValueFromEbieyeEntry(ColumnMap.technology_type, ebieyeEntry);
        if (!value.equals("")) mc.setTechnology_type(value.split("\\n"));

        //Get Organism
        value = getValueFromEbieyeEntry(ColumnMap.organism, ebieyeEntry);
        if (!value.equals("")) mc.setOrganism(value.split("\\n"));

        value = getValueFromEbieyeEntry(ColumnMap.study_design, ebieyeEntry);
        if (!value.equals("")) mc.setStudy_design(value.split("\\n"));

        value = getValueFromEbieyeEntry(ColumnMap.last_modification_date, ebieyeEntry);
        if (!value.equals("")) mc.setLast_modification_date(formatEBeyeDateString(value));

        value = getValueFromEbieyeEntry(ColumnMap.study_factor, ebieyeEntry);
        if (!value.equals("")) mc.setStudy_factor(value.split("\\n"));

        value = getValueFromEbieyeEntry(ColumnMap.submitter, ebieyeEntry);
        if (!value.equals("")) mc.setSubmitter(value);


        //Check if this is a private study
        value = getValueFromEbieyeEntry(ColumnMap.study_status, ebieyeEntry);
        if (!value.equals("")) mc.setStudyStatus(value);

        //TODO, we can add the organism and technology here to supplement the facets (for PRIVATE studies), but should only show *after* the user has logged in

        if (value.equals("1") && loggedIn){  // Private study and the user is logged in

            //Get the private study from the BII lucene index
            LuceneSearchResult study = searchService.getStudy(accessionNumber);

            //Add in the read private study data as this is not exported to the EB-eye index
            if (study != null){

                //Document doc = study.getDoc();

                //Get the first and last name + username of the submitter
                LuceneSearchResult.Submitter submitter = study.getSubmitter();

                if (user.isCurator() || user.getUserName().equals(submitter.getUserName())){   //Yup, you are a curator and/or the submitter
                    mc.setName(study.getTitle());
                    mc.setOrganism(study.getOrganisms());
                    mc.setTechnology_type(study.getTechnologies());
                    mc.setStudy_design(study.getStudyDesign());
                    mc.setLast_modification_date(parseDateToString(study.getReleaseDate()));
                    mc.setSubmitter(submitter.getFullName());
                    mc.setDescription(null); //Not in the index, and want to avoid the empty text in the jsp
                }

            }


        }


        //
        //COMPOUNDS ONLY
        //

        //Get the has_literature
        value = getValueFromEbieyeEntry(ColumnMap.has_literature, ebieyeEntry);
        mc.setHasLiterature(value);

        //get has_ms
        value = getValueFromEbieyeEntry(ColumnMap.has_ms, ebieyeEntry);
        mc.setHasMS(value);

        //get has_nmr
        value = getValueFromEbieyeEntry(ColumnMap.has_nmr, ebieyeEntry);
        mc.setHasNMR(value);

        //get has_pathways
        value = getValueFromEbieyeEntry(ColumnMap.has_pathways, ebieyeEntry);
        mc.setHasPathways(value);

        // get has_reactions
        value = getValueFromEbieyeEntry(ColumnMap.has_reactions, ebieyeEntry);
        mc.setHasReactions(value);

        // get has_species
        value = getValueFromEbieyeEntry(ColumnMap.has_species, ebieyeEntry);
        mc.setHasSpecies(value);

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

        return mc;
    }

    private void mapUserAction(String userQuery, String[] organismsSelected, String[] technologiesSelected, String[] studyStatusSelected, String pageSelected, String userAction) {

        if(userAction != null){
            if(userAction.equals("facetClicked")){
                ua = UserAction.checkedFacet;
                rffl.setCurrentPage(Integer.parseInt(pageSelected));
                rffl.resetFacets();
                rffl.checkFacets(organismsSelected, technologiesSelected, studyStatusSelected);
                if (rffl.getFacetsQuery().equals("")){
                    rffl.uncheckFacets();
                }
            } else if(userAction.equals("pageClicked")){
                ua = UserAction.clickedOnPage;
                rffl.setCurrentPage(Integer.parseInt(pageSelected));
            }
        } else if("".equals(userQuery) && (organismsSelected == null)){
            if (cacheRffl != null){
                ua = UserAction.browseCached;

                rffl = cacheRffl.clone(); //add here for changing the page e.g: .../reference?PageNumber=3
            } else{
                ua = UserAction.firstTimeBrowse;
                rffl = new RefLayerFilter(userQuery, organismsSelected, technologiesSelected, studyStatusSelected, pageSelected);
            }
        } else {
            ua = UserAction.freeTextOrExample;
            rffl = new RefLayerFilter(userQuery, organismsSelected, technologiesSelected, studyStatusSelected, pageSelected);
        }
    }

    private void queryEBI() throws Exception {

        try {
            initEBISearchService();
        } catch (Exception e){
            logger.info("queryEBI method error - "+ e);
            throw e;
        }

        String facetQuery = rffl.getFacetsQuery(); // Moved here as it was called twice

        if ((ua == UserAction.clickedOnPage) || ((ua == UserAction.browseCached) && (facetQuery.equals(""))) || (ua == UserAction.checkedFacet && (facetQuery.equals("")))){
            ArrayOfString listOfMTBLIds = ebiSearchService.getResultsIds(MTBLDomainName, rffl.getEBIQuery(), ((rffl.getCurrentPage()*10)-10), 10);
            listOfMTBLEntries = ebiSearchService.getEntries(MTBLDomainName, listOfMTBLIds, listOfMTBLFields);
        } else {
            ArrayOfString listOfMTBLIds = ebiSearchService.getAllResultsIds(MTBLDomainName, rffl.getEBIQuery());
            listOfMTBLEntries = ebiSearchService.getEntries(MTBLDomainName, listOfMTBLIds, listOfMTBLFields);
        }
    }

    private void updateFacets() {

        if(ua != UserAction.clickedOnPage){
            for(ArrayOfString entry: this.listOfMTBLEntries.getArrayOfString()){
                String organisms = getValueFromEbieyeEntry(ColumnMap.organism, entry.getString());
                String technologies = getValueFromEbieyeEntry(ColumnMap.technology_type, entry.getString());
                String studyStatus = getValueFromEbieyeEntry(ColumnMap.study_status, entry.getString());
                String[] organismsList = organisms.split("\\n");
                String[] technologiesList = technologies.split("\\n");
                String[] studyStatusList = studyStatus.split("\\n");
                rffl.updateOrganismFacet(organismsList);
                rffl.updateTechnologyFacet(technologiesList);
                rffl.updateStatusFacet(studyStatusList);
            }
        }
    }

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
}
