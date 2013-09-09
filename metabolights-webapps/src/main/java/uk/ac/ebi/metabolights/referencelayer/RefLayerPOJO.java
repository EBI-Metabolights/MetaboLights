/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 06/09/13 21:26
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer;

import uk.ac.ebi.ebisearchservice.ArrayOfArrayOfString;
import uk.ac.ebi.ebisearchservice.ArrayOfString;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Related with Reference Layer filter
 * @author Tejasvi
 *
 */
public class RefLayerPOJO {

	private String ModQuery;
	private String orgQuery;
	private String techQuery;
	private String orgType;
	private String techType;
    private String FacetOrgType;
    private String FacetTechType;
    private String FacetStudyStatus;

	private String orgValue;
	private String techValue;
    private String statusValue;

	private String[] orgSplit;
	private String[] techSplit;
    private String[] statusSplit;
	private String[] orgCheckedItems;
	private String[] techCheckedItems;
    private String[] statusCheckedItems;

	private int techSplitLen;
	private int orgSplitLen;
    private int statusSplitLen;
	private int listOfMTBLEntriesLen;
    private int ListOfMTBLEntriesForFacetsLen;

	private LinkedHashMap<String, String> techHash;
	private LinkedHashMap<String, String> orgHash;
    private LinkedHashMap<String, String> statusHash;
	private LinkedHashMap<String, String> orgCheckedItemsHash;
	private LinkedHashMap<String, String> techCheckedItemsHash;
    private LinkedHashMap<String, String> statusCheckedItemsHash;
    private LinkedHashMap<String, String> orgNoDim;
    private LinkedHashMap<String, String> techNoDim;
    private LinkedHashMap<String, String> statusNoDim;

	private Set<String> techCheckedItemsSet;
	private Set<String> orgCheckedItemsSet;
    private Set<String> statusCheckedItemsSet;

	private ArrayOfArrayOfString listOfMTBLEntries;
    private ArrayOfArrayOfString ListOfMTBLEntriesForFacets;
	private ArrayOfString MTBLEntries;
    private ArrayOfString MTBLFacetEntries;

	private StringBuffer modifyQueryToInclOrgs;
	private StringBuffer modifyQueryToInclTech;
    private StringBuffer modifyQueryToInclStatus;

	private boolean techClear;
	private boolean orgClear;
    private boolean statusClear;

    private Set<String> orgSet;
    private Set<String> techSet;
    private Set<String> statusSet;

    public int getListOfMTBLEntriesLen() {
        return listOfMTBLEntriesLen;
    }

    public void setListOfMTBLEntriesLen(int listOfMTBLEntriesLen) {
        this.listOfMTBLEntriesLen = listOfMTBLEntriesLen;
    }

    public ArrayOfArrayOfString getListOfMTBLEntries() {
        return listOfMTBLEntries;
    }

    public void setListOfMTBLEntries(ArrayOfArrayOfString listOfMTBLEntries) {
        this.listOfMTBLEntries = listOfMTBLEntries;
    }

    public StringBuffer getModifyQueryToInclOrgs() {
        return modifyQueryToInclOrgs;
    }

    public void setModifyQueryToInclOrgs(StringBuffer modifyQueryToInclOrgs) {
        this.modifyQueryToInclOrgs = modifyQueryToInclOrgs;
    }

    public StringBuffer getModifyQueryToInclTech() {
        return modifyQueryToInclTech;
    }

    public void setModifyQueryToInclTech(StringBuffer modifyQueryToInclTech) {
        this.modifyQueryToInclTech = modifyQueryToInclTech;
    }

    public StringBuffer getModifyQueryToInclStatus() {
        return modifyQueryToInclStatus;
    }

    public void setModifyQueryToInclStatus(StringBuffer modifyQueryToInclStatus) {
        this.modifyQueryToInclStatus = modifyQueryToInclStatus;
    }

    public LinkedHashMap<String, String> getOrgNoDim() {
        return orgNoDim;
    }

    public void setOrgNoDim(LinkedHashMap<String, String> orgNoDim) {
        this.orgNoDim = orgNoDim;
    }

    public LinkedHashMap<String, String> getTechNoDim() {
        return techNoDim;
    }

    public void setTechNoDim(LinkedHashMap<String, String> techNoDim) {
        this.techNoDim = techNoDim;
    }

    public LinkedHashMap<String, String> getStatusNoDim() {
        return statusNoDim;
    }

    public void setStatusNoDim(LinkedHashMap<String, String> statusNoDim) {
        this.statusNoDim = statusNoDim;
    }

    public Set<String> getOrgSet() {
        return orgSet;
    }

    public void setOrgSet(Set<String> orgSet) {
        this.orgSet = orgSet;
    }

    public Set<String> getTechSet() {
        return techSet;
    }

    public void setTechSet(Set<String> techSet) {
        this.techSet = techSet;
    }

    public Set<String> getStatusSet() {
        return statusSet;
    }

    public void setStatusSet(Set<String> statusSet) {
        this.statusSet = statusSet;
    }

    public String getFacetOrgType() {
        return FacetOrgType;
    }

    public void setFacetOrgType(String facetOrgType) {
        FacetOrgType = facetOrgType;
    }

    public String getFacetTechType() {
        return FacetTechType;
    }

    public void setFacetTechType(String facetTechType) {
        FacetTechType = facetTechType;
    }

    public String getFacetStudyStatus() {
        return FacetStudyStatus;
    }

    public void setFacetStudyStatus(String facetStudyStatus) {
        FacetStudyStatus = facetStudyStatus;
    }

    public int getListOfMTBLEntriesForFacetsLen() {
        return ListOfMTBLEntriesForFacetsLen;
    }

    public void setListOfMTBLEntriesForFacetsLen(int listOfMTBLEntriesForFacetsLen) {
        ListOfMTBLEntriesForFacetsLen = listOfMTBLEntriesForFacetsLen;
    }

    public ArrayOfArrayOfString getListOfMTBLEntriesForFacets() {
        return ListOfMTBLEntriesForFacets;
    }

    public void setListOfMTBLEntriesForFacets(ArrayOfArrayOfString listOfMTBLEntriesForFacets) {
        ListOfMTBLEntriesForFacets = listOfMTBLEntriesForFacets;
    }

    public ArrayOfString getMTBLFacetEntries() {
        return MTBLFacetEntries;
    }

    public void setMTBLFacetEntries(ArrayOfString MTBLFacetEntries) {
        this.MTBLFacetEntries = MTBLFacetEntries;
    }

    public ArrayOfString getMTBLEntries() {
        return MTBLEntries;
    }

    public void setMTBLEntries(ArrayOfString MTBLEntries) {
        this.MTBLEntries = MTBLEntries;
    }

    public Set<String> getTechCheckedItemsSet() {
		return techCheckedItemsSet;
	}

	public void setTechCheckedItemsSet(Set<String> techCheckedItemsSet) {
		this.techCheckedItemsSet = techCheckedItemsSet;
	}

	public Set<String> getOrgCheckedItemsSet() {
		return orgCheckedItemsSet;
	}

	public void setOrgCheckedItemsSet(Set<String> orgCheckedItemsSet) {
		this.orgCheckedItemsSet = orgCheckedItemsSet;
	}

    public Set<String> getStatusCheckedItemsSet() {
        return statusCheckedItemsSet;
    }

    public void setStatusCheckedItemsSet(Set<String> statusCheckedItemsSet) {
        this.statusCheckedItemsSet = statusCheckedItemsSet;
    }

    public LinkedHashMap<String, String> getTechHash() {
        return techHash;
    }

    public void setTechHash(LinkedHashMap<String, String> techHash) {
        this.techHash = techHash;
    }

    public LinkedHashMap<String, String> getOrgHash() {
        return orgHash;
    }

    public void setOrgHash(LinkedHashMap<String, String> orgHash) {
        this.orgHash = orgHash;
    }

    public LinkedHashMap<String, String> getStatusHash() {
        return statusHash;
    }

    public void setStatusHash(LinkedHashMap<String, String> statusHash) {
        this.statusHash = statusHash;
    }

    public LinkedHashMap<String, String> getOrgCheckedItemsHash() {
        return orgCheckedItemsHash;
    }

    public void setOrgCheckedItemsHash(LinkedHashMap<String, String> orgCheckedItemsHash) {
        this.orgCheckedItemsHash = orgCheckedItemsHash;
    }

    public LinkedHashMap<String, String> getTechCheckedItemsHash() {
        return techCheckedItemsHash;
    }

    public void setTechCheckedItemsHash(LinkedHashMap<String, String> techCheckedItemsHash) {
        this.techCheckedItemsHash = techCheckedItemsHash;
    }

    public LinkedHashMap<String, String> getStatusCheckedItemsHash() {
        return statusCheckedItemsHash;
    }

    public void setStatusCheckedItemsHash(LinkedHashMap<String, String> statusCheckedItemsHash) {
        this.statusCheckedItemsHash = statusCheckedItemsHash;
    }

    public boolean isTechClear() {
		return techClear;
	}

	public void setTechClear(boolean techClear) {
		this.techClear = techClear;
	}

	public boolean isOrgClear() {
		return orgClear;
	}

	public void setOrgClear(boolean orgClear) {
		this.orgClear = orgClear;
	}

    public boolean isStatusClear() {
        return statusClear;
    }

    public void setStatusClear(boolean statusClear) {
        this.statusClear = statusClear;
    }

    public String[] getTechCheckedItems() {
		return techCheckedItems;
	}

	public void setTechCheckedItems(String[] techCheckedItems) {
		this.techCheckedItems = techCheckedItems;
	}

    public String[] getStatusCheckedItems() {
        return statusCheckedItems;
    }

    public void setStatusCheckedItems(String[] statusCheckedItems) {
        this.statusCheckedItems = statusCheckedItems;
    }

    public String[] getOrgCheckedItems() {
		return orgCheckedItems;
	}

	public void setOrgCheckedItems(String[] orgCheckedItems) {
		this.orgCheckedItems = orgCheckedItems;
	}


    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getTechType() {
        return techType;
    }

    public void setTechType(String techType) {
        this.techType = techType;
    }

    public String[] getOrgSplit() {
		return orgSplit;
	}

	public void setOrgSplit(String[] orgSplit) {
		this.orgSplit = orgSplit;
	}

	public String[] getTechSplit() {
		return techSplit;
	}

	public void setTechSplit(String[] techSplit) {
		this.techSplit = techSplit;
	}

    public String[] getStatusSplit() {
        return statusSplit;
    }

    public void setStatusSplit(String[] statusSplit) {
        this.statusSplit = statusSplit;
    }

    public int getTechSplitLen() {
		return techSplitLen;
	}

	public void setTechSplitLen(int techSplitLen) {
		this.techSplitLen = techSplitLen;
	}

	public int getOrgSplitLen() {
		return orgSplitLen;
	}

	public void setOrgSplitLen(int orgSplitLen) {
		this.orgSplitLen = orgSplitLen;
	}

    public int getStatusSplitLen() {
        return statusSplitLen;
    }

    public void setStatusSplitLen(int statusSplitLen) {
        this.statusSplitLen = statusSplitLen;
    }

    public String getOrgValue() {
        return orgValue;
    }

    public void setOrgValue(String orgValue) {
        this.orgValue = orgValue;
    }

    public String getTechValue() {
        return techValue;
    }

    public void setTechValue(String techValue) {
        this.techValue = techValue;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public String getModQuery() {
		return ModQuery;
	}

	public void setModQuery(String modQuery) {
		ModQuery = modQuery;
	}

	public String getOrgQuery() {
		return orgQuery;
	}

	public void setOrgQuery(String orgQuery) {
		this.orgQuery = orgQuery;
	}

	public String getTechQuery() {
		return techQuery;
	}

	public void setTechQuery(String techQuery) {
		this.techQuery = techQuery;
	}

	public RefLayerPOJO(){

	}

    public RefLayerPOJO(String userQuery, String[] organisms, String[] technologies, String[] studyStatus, String currentPage){

    }
}
