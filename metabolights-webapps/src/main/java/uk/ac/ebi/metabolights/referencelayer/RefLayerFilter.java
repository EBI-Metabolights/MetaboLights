/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 09/09/13 11:55
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer;

import uk.ac.ebi.metabolights.utils.StringUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: tejasvi
 * Date: 07/03/13
 * Time: 11:45
 */
public class RefLayerFilter {

    private String freeText = new String("");
    private LinkedHashMap<String, FacetStatus> technologyFacet = new LinkedHashMap<String, FacetStatus>();
    private LinkedHashMap<String, FacetStatus> organismFacet = new LinkedHashMap<String, FacetStatus>();
    private LinkedHashMap<String, FacetStatus> statusFacet = new LinkedHashMap<String, FacetStatus>();    //Private (1) or Public (0) studies
    private Integer currentPage = 1;
    private String defaultFreeText = "id:MTBL*";
    private Integer MTBLNumOfResults = 0;
    private Integer totalPages = 0;
    private Integer resultsPerPage = 10;

    public Integer getLastPageEntries(){
        return (MTBLNumOfResults % resultsPerPage);
    }

    public Integer getMTBLNumOfResults() {
        return MTBLNumOfResults;
    }

    public void setMTBLNumOfResults(Integer MTBLNumOfResults) {
        this.MTBLNumOfResults = MTBLNumOfResults;
    }

    public Integer getTotalNumOfPages() {
        if(MTBLNumOfResults != 0){
            Double totalPagesD = (Math.ceil(MTBLNumOfResults.doubleValue()/resultsPerPage));
            totalPages = totalPagesD.intValue();
        }
        return totalPages;
    }


    public void sortFacets() {
        organismFacet = sortFacet(organismFacet);
        technologyFacet = sortFacet(technologyFacet);
        statusFacet = sortFacet(statusFacet);
    }

    private LinkedHashMap<String, FacetStatus> sortFacet(LinkedHashMap<String, FacetStatus> Facet) {
        List<String> facetKeys = new LinkedList<String>(Facet.keySet());
        Collections.sort(facetKeys);

        LinkedHashMap<String, FacetStatus> sortedHash = new LinkedHashMap<String, FacetStatus>();
        for(String key: facetKeys){
            sortedHash.put(key, Facet.get(key));
        }

        return sortedHash;
    }


    public enum FacetStatus {
        checked,
        unchecked,
        dimmed
    }
    public String getFreeText() {
        return freeText;
    }

    public String getDefaultFreeText(){
        return defaultFreeText;
    }

    public void setFreeText(String freeText) {
        if(freeText == null){
            this.freeText = "";
        } else {
            this.freeText = freeText;
        }
    }

    public LinkedHashMap<String, FacetStatus> getTechnologyFacet() {
        return technologyFacet;
    }

    public LinkedHashMap<String, FacetStatus> getOrganismFacet() {
        return organismFacet;
    }

    public LinkedHashMap<String, FacetStatus> getStatusFacet() {
        return statusFacet;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    private void setCurrentPage(String pageSelected) {

        if(pageSelected == null){
            currentPage = 1; //Loading the page for the first time, set it to 1.
        } else {
            currentPage = Integer.parseInt(pageSelected); //else getting it from the jsp
        }
    }

    public RefLayerFilter(String freeText, String[] organisms, String[] technologies, String[] studyStatus, String currentPage){
        setCurrentPage(currentPage);
        setFreeText(freeText);
        convertArrayToHash(organisms, organismFacet);
        convertArrayToHash(technologies, technologyFacet);
        convertArrayToHash(studyStatus, statusFacet);

    }

    private void convertArrayToHash(String[] facetArray, LinkedHashMap<String, FacetStatus> facetHash){
        if(facetArray != null){
            for(String facetName:facetArray){
                facetHash.put(facetName, FacetStatus.checked);
            }
        }
    }

    public String getEBIQuery(){
        String finalQuery = getFacetsQuery();
        finalQuery = StringUtils.join(finalQuery, getEBIFreeTextQuery(), " AND ", "(", ")");
        return finalQuery;
    }

    private String getEBIFreeTextQuery(){
        if(freeText.equals("")){
            return "("+defaultFreeText+")";
        } else {
            return "("+freeText+"*)";
        }
    }

    public String getFacetsQuery(){
        String queryStr = null;
        queryStr = StringUtils.join(getFacetQuery("organism", organismFacet), getFacetQuery("technology_type", technologyFacet), " AND ", "(", ")");

        if (!queryStr.isEmpty())
            queryStr = queryStr + " AND ";

        queryStr = queryStr + getFacetQuery("study_status", statusFacet);


        //return StringUtils.join(getFacetQuery("organism", organismFacet), getFacetQuery("technology_type", technologyFacet), " AND ", "(", ")");
        return queryStr;
    }

    private String getFacetQuery(String EBIFieldName, LinkedHashMap<String, FacetStatus> facetHash){
        String facetQuery = "";
        for(String facetKey:facetHash.keySet()){
            FacetStatus facetStatus = facetHash.get(facetKey);
            if(facetStatus == FacetStatus.checked){
                facetQuery = facetQuery + "(" + EBIFieldName + ":\"" + facetKey + "\") OR ";
            }
        }

        facetQuery = StringUtils.truncate(facetQuery, 4);
        return facetQuery;
    }

    public void updateOrganismFacet(String[] organismList){
        updateFacet(organismList, organismFacet);
    }

    public void updateTechnologyFacet(String[] technologyList){
        updateFacet(technologyList, technologyFacet);
    }

    public void updateStatusFacet(String[] statusList){
        updateFacet(statusList, statusFacet);
    }

    private void updateFacet(String[] facetKeys, LinkedHashMap<String, FacetStatus> facetLinkedHash){
        for(String key: facetKeys){
            if(!(key.equals("")) || (key == null)){
                if(!(facetLinkedHash.containsKey(key))){
                    facetLinkedHash.put(key, FacetStatus.unchecked);
                } else if(facetLinkedHash.get(key) == FacetStatus.dimmed){
                    facetLinkedHash.put(key, FacetStatus.unchecked);
                }
            }
        }
    }

    public void checkFacets(String[] organism, String[] technology, String[] studyStatus){
        convertArrayToHash(organism, organismFacet);
        convertArrayToHash(technology, technologyFacet);
        convertArrayToHash(studyStatus, statusFacet);

    }

    public void uncheckFacets() {
        setAllFacetStatus(organismFacet, FacetStatus.unchecked);
        setAllFacetStatus(technologyFacet, FacetStatus.unchecked);
        setAllFacetStatus(statusFacet, FacetStatus.unchecked);

    }

    public void resetFacets(){
        setAllFacetStatus(organismFacet, FacetStatus.dimmed);
        setAllFacetStatus(technologyFacet, FacetStatus.dimmed);
        setAllFacetStatus(statusFacet, FacetStatus.dimmed);
    }

    private void setAllFacetStatus(LinkedHashMap<String,FacetStatus> facet, FacetStatus value){
        for(String key: facet.keySet()){
            facet.put(key, value);
        }
    }
    public RefLayerFilter clone(){

        RefLayerFilter clone =  new RefLayerFilter(freeText,null,null,null,currentPage.toString());

        clone.organismFacet = (LinkedHashMap<String,FacetStatus>)organismFacet.clone();
        clone.technologyFacet = (LinkedHashMap<String,FacetStatus>)technologyFacet.clone();
        clone.statusFacet = (LinkedHashMap<String,FacetStatus>)statusFacet.clone();

        return clone;
    }
}
