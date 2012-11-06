package uk.ac.ebi.metabolights.referencelayer;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import uk.ac.ebi.ebisearchservice.ArrayOfArrayOfString;
import uk.ac.ebi.ebisearchservice.ArrayOfString;
import uk.ac.ebi.metabolights.search.FilterItem;
import uk.ac.ebi.metabolights.search.FilterSet;

/**
 * Related with Reference Layer filter
 * @author tejasvi
 *
 */
public class RefLayerSearchFilter {
	
	private String query;
	private String orgQuery;
	private String techQuery;
	private String ModQuery;	
	private String orgElement;
	private String techElement;
	private String orgType;
	private String techType;
	
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
	
	private int orgLen;
	private int techLen;
	private int techSplitLen;
	private int orgSplitLen;
	private int techFlag = 0;
	private int orgFlag = 0;
	private int MTBLArrayOfEntriesLen;
	private int orgCount;
	private int techCount;
	
	private Hashtable<String, Boolean> techHash;
	private Hashtable<String, Boolean> orgHash;
	private Hashtable<String, Boolean> orgCheckedItemsHash;
	private Hashtable<String, Boolean> techCheckedItemsHash;
	
	private Enumeration techEnum;
	private Enumeration orgEnum;
	private Enumeration techCheckedItemsEnum;
	private Enumeration orgCheckedItemsEnum;
	
	private ArrayOfArrayOfString MTBLArrayOfEntries;
	private ArrayOfString MTBLEntries;
	
	private StringBuffer orgSB;
	private StringBuffer techSB;
	

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

	public Enumeration getTechCheckedItemsEnum() {
		return techCheckedItemsEnum;
	}

	public void setTechCheckedItemsEnum(Enumeration techCheckedItemsEnum) {
		this.techCheckedItemsEnum = techCheckedItemsEnum;
	}

	public Enumeration getOrgCheckedItemsEnum() {
		return orgCheckedItemsEnum;
	}

	public void setOrgCheckedItemsEnum(Enumeration orgCheckedItemsEnum) {
		this.orgCheckedItemsEnum = orgCheckedItemsEnum;
	}

	public Hashtable<String, Boolean> getOrgCheckedItemsHash() {
		return orgCheckedItemsHash;
	}

	public void setOrgCheckedItemsHash(
			Hashtable<String, Boolean> orgCheckedItemsHash) {
		this.orgCheckedItemsHash = orgCheckedItemsHash;
	}

	public Hashtable<String, Boolean> getTechCheckedItemsHash() {
		return techCheckedItemsHash;
	}

	public void setTechCheckedItemsHash(
			Hashtable<String, Boolean> techCheckedItemsHash) {
		this.techCheckedItemsHash = techCheckedItemsHash;
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

	public int getMTBLArrayOfEntriesLen() {
		return MTBLArrayOfEntriesLen;
	}

	public void setMTBLArrayOfEntriesLen(int mTBLArrayOfEntriesLen) {
		MTBLArrayOfEntriesLen = mTBLArrayOfEntriesLen;
	}

	public ArrayOfArrayOfString getMTBLArrayOfEntries() {
		return MTBLArrayOfEntries;
	}

	public void setMTBLArrayOfEntries(ArrayOfArrayOfString mTBLArrayOfEntries) {
		MTBLArrayOfEntries = mTBLArrayOfEntries;
	}

	public ArrayOfString getMTBLEntries() {
		return MTBLEntries;
	}

	public void setMTBLEntries(ArrayOfString mTBLEntries) {
		MTBLEntries = mTBLEntries;
	}

	public Hashtable<String, Boolean> getTechHash() {
		return techHash;
	}

	public void setTechHash(Hashtable<String, Boolean> techHash) {
		this.techHash = techHash;
	}

	public Hashtable<String, Boolean> getOrgHash() {
		return orgHash;
	}

	public void setOrgHash(Hashtable<String, Boolean> orgHash) {
		this.orgHash = orgHash;
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
