package uk.ac.ebi.metabolights.referencelayer;

import org.apache.tiles.context.MapEntry;
import uk.ac.ebi.metabolights.utils.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tejasvi
 * Date: 07/03/13
 * Time: 11:45
 * To change this template use File | Settings | File Templates.
 */
public class RefLayerFilter {

    private String freeText = new String("");
    private LinkedHashMap<String, FacetStatus> technologyFacet = new LinkedHashMap<String, FacetStatus>();
    private LinkedHashMap<String, FacetStatus> organismFacet = new LinkedHashMap<String, FacetStatus>();
    private Integer currentPage = 1;
    private String defaultFreeText = "id:MTBL*";
    private Integer MTBLNumOfResults = 0;
    private Integer totalPages = 0;

    public Integer getLastPageEntries(){
        Integer entries = 0;
        Double entriesD = (MTBLNumOfResults.doubleValue()/10);
        String[] splitArray = entriesD.toString().split("\\.");
        entries = Integer.parseInt(splitArray[1]);
        return entries;
    }

    public Integer getMTBLNumOfResults() {
        return MTBLNumOfResults;
    }

    public void setMTBLNumOfResults(Integer MTBLNumOfResults) {
        this.MTBLNumOfResults = MTBLNumOfResults;
    }

    public Integer getTotalNumOfPages() {
        if(MTBLNumOfResults != 0){
            //Double MTBLNumOfResultsD = MTBLNumOfResults.doubleValue();
            Double totalPagesD = (Math.ceil(MTBLNumOfResults.doubleValue()/10));
            totalPages = totalPagesD.intValue();
        }
        return totalPages;
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

    public RefLayerFilter(String freeText, String[] organisms, String[] technologies, String currentPage){
        setCurrentPage(currentPage);
        setFreeText(freeText);
        convertArrayToHash(organisms, organismFacet);
        convertArrayToHash(technologies, organismFacet);

    }

    private void convertArrayToHash(String[] facetArray, LinkedHashMap<String, FacetStatus> facetHash){
        if(facetArray != null){ //added this change, if organisms facet is selected then tech is empty and vise versa.
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
            return "("+freeText+")";
        }
    }

    public String getFacetsQuery(){
        return StringUtils.join(getFacetQuery("organism", organismFacet), getFacetQuery("technology_type", technologyFacet), " AND ", "(", ")");
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

    private void updateFacet(String[] facetKeys, LinkedHashMap<String, FacetStatus> facetLinkedHash){
        for(String key: facetKeys){
            if(!(key.equals("")) || (key.equals(null))){ //added this additional check as some keys, returned from EBI results, were empty.
                if(!(facetLinkedHash.containsKey(key))){
                    facetLinkedHash.put(key, FacetStatus.unchecked);
                } else if(facetLinkedHash.get(key) == FacetStatus.dimmed){
                    facetLinkedHash.put(key, FacetStatus.unchecked);
                }
            }
        }
    }

    public void checkFacets(String[] organism, String[] technology){
        convertArrayToHash(organism, organismFacet);
        convertArrayToHash(technology, technologyFacet);

    }

    public void resetFacets(){
        resetFacet(organismFacet);
        resetFacet(technologyFacet);
    }

    private void resetFacet(LinkedHashMap<String,FacetStatus> facet){
        for(String key: facet.keySet()){
            facet.put(key, FacetStatus.dimmed);
        }
    }
    public RefLayerFilter clone(){

        RefLayerFilter clone =  new RefLayerFilter(freeText,null,null,currentPage.toString());

        clone.organismFacet = (LinkedHashMap<String,FacetStatus>)organismFacet.clone();
        clone.technologyFacet = (LinkedHashMap<String,FacetStatus>)technologyFacet.clone();

        return clone;
    }
}
