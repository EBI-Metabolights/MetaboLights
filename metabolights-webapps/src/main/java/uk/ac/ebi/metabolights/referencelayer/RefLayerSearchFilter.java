package uk.ac.ebi.metabolights.referencelayer;

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
	
	private String ModQuery;
	private String orgQuery;
	private String techQuery;
	private String orgType;
	private String techType;
    private String FacetOrgType;
    private String FacetTechType;

	private String orgValue;
	private String techValue;
	
	private String[] orgSplit;
	private String[] techSplit;
	private String[] orgCheckedItems;
	private String[] techCheckedItems;

	private int techSplitLen;
	private int orgSplitLen;
	private int MTBLCArrayOfEntriesLen;
    private int MTBLFacetArrayOfEntriesLen;
	
	private LinkedHashMap<String, String> techHash;
	private LinkedHashMap<String, String> orgHash;
	private LinkedHashMap<String, String> orgCheckedItemsHash;
	private LinkedHashMap<String, String> techCheckedItemsHash;
    private LinkedHashMap<String, String> orgNoDim;
    private LinkedHashMap<String, String> techNoDim;

	private Set<String> techCheckedItemsSet;
	private Set<String> orgCheckedItemsSet;
	
	
	private ArrayOfArrayOfString MTBLCArrayOfEntries;
    private ArrayOfArrayOfString MTBLFacetsArrayOfEntries;
	private ArrayOfString MTBLCEntries;
    private ArrayOfString MTBLFacetEntries;
	
	private StringBuffer orgSB;
	private StringBuffer techSB;
	
	private boolean techClear;
	private boolean orgClear;

    private Set<String> orgSet;
    private Set<String> techSet;



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

	public RefLayerSearchFilter(){
		
	}
}
