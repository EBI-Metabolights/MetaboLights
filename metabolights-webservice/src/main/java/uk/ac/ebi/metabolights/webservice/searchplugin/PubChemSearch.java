package uk.ac.ebi.metabolights.webservice.searchplugin;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;


/**
 * Created by kalai on 04/08/2016.
 */
public class PubChemSearch implements Serializable, Cloneable, Callable<Collection<CompoundSearchResult>> {

    private static final Logger logger = LoggerFactory.getLogger(PubChemSearch.class);

    private String pubchemUrl = GenericCompoundWSClients.getPubChemWSURL();
    private List<CompoundSearchResult> compoundSearchResults = new ArrayList<CompoundSearchResult>();
    private SearchTermCategory searchTermCategory;
    private String searchTerm;

    public PubChemSearch() {
    }

    public PubChemSearch(String searchterm, SearchTermCategory searchTermCategory) {
        this.searchTerm = searchterm;
        this.searchTermCategory = searchTermCategory;
    }

    //    to get cid by name

    //https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/name/hexose/cids/json

    //http://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/cid/206/property/MolecularFormula,CanonicalSMILES,inchi,molecularformula,iupacname/json

    //  https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/cid/206/xrefs/registryID/json

    public List<CompoundSearchResult> searchAndFillByName(String name) throws UnsupportedEncodingException {
        String searchURL = pubchemUrl + "name/" + GenericCompoundWSClients.encoded(name) + "/cids/json";
        List<String> pubchemCIDs = getAllMatchingCIDs(searchURL, "GET", "");
        if (pubchemCIDs.isEmpty()) return this.compoundSearchResults;
        fetchAndFillFullInfo(pubchemCIDs);
        return this.compoundSearchResults;
    }


//    private String getAnyMatchingCID(String searchURL, String method, String postBody) {
//        String pubchemResponse = excuteRequest(searchURL, "GET", "");
//        if (pubchemResponse == null) return "";
//        Integer pubchemCID;
//        try {
//            JSONObject myObject = new JSONObject(pubchemResponse);
//            JSONObject myObject2 = myObject.getJSONObject("IdentifierList");
//            JSONArray array1 = (JSONArray) myObject2.get("CID");
//            pubchemCID = (Integer) array1.get(0);
//            return pubchemCID.toString();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    private List<String> getAllMatchingCIDs(String searchURL, String method, String postBody) {
        String pubchemResponse = GenericCompoundWSClients.executeRequest(searchURL, method, postBody);
        if (pubchemResponse == null) return new ArrayList<>();
        List<String> pubchemCIDs = new ArrayList<>();
        try {
            JSONObject myObject = new JSONObject(pubchemResponse);
            JSONObject myObject2 = myObject.getJSONObject("IdentifierList");
            JSONArray array1 = (JSONArray) myObject2.get("CID");
            for (int i = 0; i < array1.length(); i++) {
                Integer id = (Integer) array1.get(i);
                pubchemCIDs.add(id.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pubchemCIDs;
    }

    private CompoundSearchResult fetchAndFillFullInfo(String pubchemID) {
        CompoundSearchResult compoundSearchResult = new CompoundSearchResult(SearchResource.PUBCHEM);
        JSONObject result = getMetaData(pubchemID);
        if (result != null) {
            fillFullInfo(compoundSearchResult, result);
            String chebiID = getChebiID(pubchemID);
            if (!chebiID.isEmpty()) {
                compoundSearchResult.setChebiId(chebiID);
            }
        }
        return compoundSearchResult;
    }

    private void fetchAndFillFullInfo(List<String> pubchemCIDs) {
        for (String pubchemID : pubchemCIDs) {
            this.compoundSearchResults.add(fetchAndFillFullInfo(pubchemID));
        }
    }

    private JSONObject getMetaData(String pubchemID) {
        String searchURL = pubchemUrl + "cid/" + pubchemID + "/property/MolecularFormula,CanonicalSMILES,inchi,molecularformula,iupacname/json";
        String pubchemResponse = GenericCompoundWSClients.executeRequest(searchURL, "GET", "");
        if (pubchemResponse == null) return null;
        try {
            JSONObject result = new JSONObject(pubchemResponse);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getChebiID(String pubchemID) {
        String searchURL = pubchemUrl + "cid/" + pubchemID + "/xrefs/registryID/json";
        String pubchemResponse = GenericCompoundWSClients.executeRequest(searchURL, "GET", "");
        if (pubchemResponse == null) return "";
        try {
            JSONObject result = new JSONObject(pubchemResponse);
            JSONObject myObject1 = result.getJSONObject("InformationList");
            JSONArray array1 = (JSONArray) myObject1.get("Information");
            JSONObject myObject2 = (JSONObject) array1.get(0);
            JSONArray array2 = (JSONArray) myObject2.get("RegistryID");
            return extractChebiID(array2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String extractChebiID(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            try {
                String value = array.getString(i);
                if (value.contains("CHEBI:")) {
                    return value;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private void fillFullInfo(CompoundSearchResult compoundSearchResult, JSONObject searchResult) {
        try {
            JSONObject myObject1 = searchResult.getJSONObject("PropertyTable");
            JSONArray array1 = (JSONArray) myObject1.get("Properties");
            JSONObject myObject2 = (JSONObject) array1.get(0);
            compoundSearchResult.setFormula((String) myObject2.get("MolecularFormula"));
            compoundSearchResult.setSmiles((String) myObject2.get("CanonicalSMILES"));
            compoundSearchResult.setName((String) myObject2.get("IUPACName"));
            compoundSearchResult.setInchi((String) myObject2.get("InChI"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }





    public List<CompoundSearchResult> searchAndFillByInChI(String inchi) {
        String searchURL = pubchemUrl + "inchi/cids/JSON";
        List<String> pubchemCIDs = getAllMatchingCIDs(searchURL, "POST", inchi);
        if (pubchemCIDs.isEmpty()) return this.compoundSearchResults;
        fetchAndFillFullInfo(pubchemCIDs);
        return this.compoundSearchResults;
    }

    public List<CompoundSearchResult> searchAndFillBySMILES(String smiles) {
        String searchURL = pubchemUrl + "smiles/" + smiles + "/cids/json";
        List<String> pubchemCIDs = sendAppropriateRequest(searchURL, smiles);
        if (pubchemCIDs.isEmpty()) return this.compoundSearchResults;
        fetchAndFillFullInfo(pubchemCIDs);
        return this.compoundSearchResults;
    }

    private List<String> sendAppropriateRequest(String searchURL, String content) {
        if (!content.contains("/")) {
            return getAllMatchingCIDs(searchURL, "GET", "");
        } else {
            String newSearchURL = pubchemUrl + "smiles/cids/json";
            return getAllMatchingCIDs(newSearchURL, "POST", content);
        }
    }

    @Override
    public Collection<CompoundSearchResult> call() throws Exception {
        if (this.searchTermCategory.equals(SearchTermCategory.INCHI)) {
            searchAndFillByInChI(this.searchTerm);
        } else if (this.searchTermCategory.equals(SearchTermCategory.NAME)) {
            searchAndFillByName(this.searchTerm);
        } else if (this.searchTermCategory.equals(SearchTermCategory.SMILES)) {
            searchAndFillBySMILES(this.searchTerm);
        }
        return this.compoundSearchResults;
    }

}
