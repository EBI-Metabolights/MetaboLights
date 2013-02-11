package uk.ac.ebi.metabolights.referencelayer;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Set;

import uk.ac.ebi.ebisearchservice.ArrayOfArrayOfString;
import uk.ac.ebi.ebisearchservice.ArrayOfString;

/**
 * Related with Reference Layer filter
 * @author Tejasvi
 *
 */
public class RefLayerSearchFilter {
	
	private String query;
	private String ModQuery;
	private String orgQuery;
	private String techQuery;
	private String studiesQuery;
	private String orgElement;
	private String techElement;
	private String[] orgType;
	private String[] techType;
    private String FacetOrgType;
    private String FacetTechType;

	private Boolean orgValue;
	private Boolean techValue;
	
	private String[] organisms;
	private String[] technology1;
	private String[] orgSplit;
	private String[] techSplit;
	private String[] orgCheckedItems;
	private String[] techCheckedItems;
	private String[] orgNumOfItems;
	private String[] techNumOfItems;
    private String[] orgTypes;
    private String[] techTypes;

	private int orgLen;
	private int techLen;
	private int techSplitLen;
	private int orgSplitLen;
	private int techFlag = 0;
	private int orgFlag = 0;
	private int MTBLCArrayOfEntriesLen;
    private int MTBLFacetArrayOfEntriesLen;
	private int orgCount;
	private int techCount;
	
	private LinkedHashMap<String, Boolean> techHash;
	private LinkedHashMap<String, Boolean> orgHash;
	private LinkedHashMap<String, Boolean> orgCheckedItemsHash;
	private LinkedHashMap<String, Boolean> techCheckedItemsHash;
	
	private Enumeration techEnum;
	private Enumeration orgEnum;
	private Set<String> techCheckedItemsSet;
	private Set<String> orgCheckedItemsSet;
	
	
	private ArrayOfArrayOfString MTBLCArrayOfEntries;
    private ArrayOfArrayOfString MTBLFacetsArrayOfEntries;
	private ArrayOfString MTBLCEntries;
    private ArrayOfString MTBLFacetEntries;
	private ArrayOfString MTBLCResults;
	private ArrayOfString MTBLSResults;
	
	private StringBuffer orgSB;
	private StringBuffer techSB;
	
	private boolean techClear;
	private boolean orgClear;


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

    public int getMTBLFacetArrayOfEntriesLen() {
        return MTBLFacetArrayOfEntriesLen;
    }

    public void setMTBLFacetArrayOfEntriesLen(int MTBLFacetArrayOfEntriesLen) {
        this.MTBLFacetArrayOfEntriesLen = MTBLFacetArrayOfEntriesLen;
    }

    public ArrayOfArrayOfString getMTBLFacetsArrayOfEntries() {
        return MTBLFacetsArrayOfEntries;
    }

    public void setMTBLFacetsArrayOfEntries(ArrayOfArrayOfString MTBLFacetsArrayOfEntries) {
        this.MTBLFacetsArrayOfEntries = MTBLFacetsArrayOfEntries;
    }

    public ArrayOfString getMTBLFacetEntries() {
        return MTBLFacetEntries;
    }

    public void setMTBLFacetEntries(ArrayOfString MTBLFacetEntries) {
        this.MTBLFacetEntries = MTBLFacetEntries;
    }

    public ArrayOfString getMTBLCResults() {
		return MTBLCResults;
	}

	public void setMTBLCResults(ArrayOfString mTBLCResults) {
		MTBLCResults = mTBLCResults;
	}

	public ArrayOfString getMTBLSResults() {
		return MTBLSResults;
	}

	public void setMTBLSResults(ArrayOfString mTBLSResults) {
		MTBLSResults = mTBLSResults;
	}

	public int getMTBLCArrayOfEntriesLen() {
		return MTBLCArrayOfEntriesLen;
	}

	public void setMTBLCArrayOfEntriesLen(int mTBLCArrayOfEntriesLen) {
		MTBLCArrayOfEntriesLen = mTBLCArrayOfEntriesLen;
	}

	public ArrayOfString getMTBLCEntries() {
		return MTBLCEntries;
	}

	public void setMTBLCEntries(ArrayOfString mTBLCEntries) {
		MTBLCEntries = mTBLCEntries;
	}

	public ArrayOfArrayOfString getMTBLCArrayOfEntries() {
		return MTBLCArrayOfEntries;
	}

	public void setMTBLCArrayOfEntries(ArrayOfArrayOfString mTBLCArrayOfEntries) {
		MTBLCArrayOfEntries = mTBLCArrayOfEntries;
	}

	public String getStudiesQuery() {
		return studiesQuery;
	}

	public void setStudiesQuery(String studiesQuery) {
		this.studiesQuery = studiesQuery;
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

	public LinkedHashMap<String, Boolean> getTechHash() {
		return techHash;
	}

	public void setTechHash(LinkedHashMap<String, Boolean> techHash) {
		this.techHash = techHash;
	}

	public LinkedHashMap<String, Boolean> getOrgHash() {
		return orgHash;
	}

	public void setOrgHash(LinkedHashMap<String, Boolean> orgHash) {
		this.orgHash = orgHash;
	}

	public LinkedHashMap<String, Boolean> getOrgCheckedItemsHash() {
		return orgCheckedItemsHash;
	}

	public void setOrgCheckedItemsHash(
			LinkedHashMap<String, Boolean> orgCheckedItemsHash) {
		this.orgCheckedItemsHash = orgCheckedItemsHash;
	}

	public LinkedHashMap<String, Boolean> getTechCheckedItemsHash() {
		return techCheckedItemsHash;
	}

	public void setTechCheckedItemsHash(
			LinkedHashMap<String, Boolean> techCheckedItemsHash) {
		this.techCheckedItemsHash = techCheckedItemsHash;
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

	public String[] getOrgNumOfItems() {
		return orgNumOfItems;
	}

	public void setOrgNumOfItems(String[] orgNumOfItems) {
		this.orgNumOfItems = orgNumOfItems;
	}

	public String[] getTechNumOfItems() {
		return techNumOfItems;
	}

	public void setTechNumOfItems(String[] techNumOfItems) {
		this.techNumOfItems = techNumOfItems;
	}

	public int getOrgCount() {
		return orgCount;
	}

	public void setOrgCount(int orgCount) {
		this.orgCount = orgCount;
	}

	public int getTechCount() {
		return techCount;
	}

	public void setTechCount(int techCount) {
		this.techCount = techCount;
	}

	public StringBuffer getOrgSB() {
		return orgSB;
	}

	public void setOrgSB(StringBuffer orgSB) {
		this.orgSB = orgSB;
	}

	public StringBuffer getTechSB() {
		return techSB;
	}

	public void setTechSB(StringBuffer techSB) {
		this.techSB = techSB;
	}

	public String[] getTechCheckedItems() {
		return techCheckedItems;
	}

	public void setTechCheckedItems(String[] techCheckedItems) {
		this.techCheckedItems = techCheckedItems;
	}

	public String[] getOrgCheckedItems() {
		return orgCheckedItems;
	}

	public void setOrgCheckedItems(String[] orgCheckedItems) {
		this.orgCheckedItems = orgCheckedItems;
	}

	public int getTechFlag() {
		return techFlag;
	}

	public void setTechFlag(int techFlag) {
		this.techFlag = techFlag;
	}

	public int getOrgFlag() {
		return orgFlag;
	}

	public void setOrgFlag(int orgFlag) {
		this.orgFlag = orgFlag;
	}

    public String[] getOrgType() {
        return orgType;
    }

    public void setOrgType(String[] orgType) {
        this.orgType = orgType;
    }

    public String[] getTechType() {
        return techType;
    }

    public void setTechType(String[] techType) {
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

	public Enumeration getTechEnum() {
		return techEnum;
	}

	public void setTechEnum(Enumeration techEnum) {
		this.techEnum = techEnum;
	}

	public Enumeration getOrgEnum() {
		return orgEnum;
	}

	public void setOrgEnum(Enumeration orgEnum) {
		this.orgEnum = orgEnum;
	}

	public int getOrgLen() {
		return orgLen;
	}

	public void setOrgLen(int orgLen) {
		this.orgLen = orgLen;
	}

	public int getTechLen() {
		return techLen;
	}

	public void setTechLen(int techLen) {
		this.techLen = techLen;
	}

	public Boolean getOrgValue() {
		return orgValue;
	}

	public void setOrgValue(Boolean orgValue) {
		this.orgValue = orgValue;
	}

	public Boolean getTechValue() {
		return techValue;
	}

	public void setTechValue(Boolean techValue) {
		this.techValue = techValue;
	}

	public String getOrgElement() {
		return orgElement;
	}

	public void setOrgElement(String orgElement) {
		this.orgElement = orgElement;
	}

	public String getTechElement() {
		return techElement;
	}

	public void setTechElement(String techElement) {
		this.techElement = techElement;
	}

	public String[] getOrganisms() {
		return organisms;
	}

	public void setOrganisms(String[] organisms) {
		this.organisms = organisms;
	}

	public String[] getTechnology1() {
		return technology1;
	}

	public void setTechnology1(String[] technology1) {
		this.technology1 = technology1;
	}

	public String getQuery() {
		return query;
	}

	public String getModQuery() {
		return ModQuery;
	}

	public void setModQuery(String modQuery) {
		ModQuery = modQuery;
	}

	public void setQuery(String query) {
		this.query = query;
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

	public RefLayerSearchFilter(){
		
	}
}
