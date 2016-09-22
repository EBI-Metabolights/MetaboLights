package uk.ac.ebi.metabolights.webservice.searchplugin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by kalai on 06/09/2016.
 */
public class CTSSearch implements Serializable, Cloneable, Callable<Collection<CompoundSearchResult>> {

    //inchi-key to synonyms: http://cts.fiehnlab.ucdavis.edu/service/synonyms/WQZGKKKJIJFFOK-GASJEMHNSA-N

    //name to inchi-key: http://cts.fiehnlab.ucdavis.edu/service/convert/Chemical%20Name/InChIKey/glucose

    //inchi-key to all-info: http://cts.fiehnlab.ucdavis.edu/service/compound/LFQSCWFLJHTTHZ-UHFFFAOYSA-N
    //http://cts.fiehnlab.ucdavis.edu/service/compound/QNAYBMKLOCPYGJ-REOHCLBHSA-N

    //inchi-key to chemspider: http://cts.fiehnlab.ucdavis.edu/service/convert/InChIKey/Chemspider/QNAYBMKLOCPYGJ-REOHCLBHSA-N
    //inchi-key to chebi: http://cts.fiehnlab.ucdavis.edu/service/convert/InChIKey/ChEBI/QNAYBMKLOCPYGJ-REOHCLBHSA-N
    //inchi-key to pubchem: http://cts.fiehnlab.ucdavis.edu/service/convert/InChIKey/PubChem%20CID/QNAYBMKLOCPYGJ-REOHCLBHSA-N

    //get synonyms from inchikey: http://cts.fiehnlab.ucdavis.edu/service/synonyms/WQZGKKKJIJFFOK-GASJEMHNSA-N

    public List<CompoundSearchResult> getSearchHitsForName(String compoundName) {
        List<CompoundSearchResult> searchResult = new ArrayList<>();
        try {
            String inchikey = getInChIKeyFrom(compoundName);
            if (!inchikey.isEmpty()) {
                CompoundSearchResult compoundSearchResult = getFullCompoundUsing(inchikey);
                if (!compoundSearchResult.isComplete()) {
                    //no smiles because, no matching chebi id is found
                    compoundSearchResult.setName(compoundName);
                    compoundSearchResult.setSmiles(getSMILESFromCactusFor(inchikey));
                }
                searchResult.add(compoundSearchResult);
                return searchResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchResult;
    }

    public String getInChIKeyFrom(String compoundName) throws Exception {
        String getURL = "http://cts.fiehnlab.ucdavis.edu/service/convert/Chemical%20Name/InChIKey/" + GenericCompoundWSClients.encoded(compoundName);
        String response = GenericCompoundWSClients.executeRequest(getURL, "GET", "");
        JSONArray result = new JSONArray(response);
        String inchiKey = "";
        if (result.length() != 0) {
            JSONObject result1 = (JSONObject) result.get(0);
            JSONArray result3 = result1.getJSONArray("result");
            if (result3.length() != 0) {
                inchiKey = (String) result3.get(0);
                return inchiKey;
            }
        }
        return inchiKey;
    }

    public CompoundSearchResult getFullCompoundUsing(String inchiKey) throws JSONException {
        String getURL = "http://cts.fiehnlab.ucdavis.edu/service/compound/" + inchiKey;
        String response = GenericCompoundWSClients.executeRequest(getURL, "GET", "");
        return fill(response);
    }

    private CompoundSearchResult fill(String response) throws JSONException {
        CompoundSearchResult compoundSearchResult = new CompoundSearchResult(SearchResource.CTS);
        if (isSuccess(response)) {
            JSONObject searchResult = new JSONObject(response);
            List<String> databaseIds = new ArrayList<>();

            if (extractExternalIDs("ChEBI", searchResult, databaseIds)) {
                String chebiID = databaseIds.get(0);
                if (!chebiID.isEmpty()) {
                    new ChebiSearch().fillWithChebiCompleteEntity(chebiID, compoundSearchResult);
                }
            } else if (extractExternalIDs("KEGG", searchResult, databaseIds)) {
                String keggID = databaseIds.get(0);
                if (!keggID.isEmpty()) {
                    compoundSearchResult.setDatabaseId("KEGG:" + keggID);
                    fillMinimumInfoFromCTS(compoundSearchResult, searchResult);
                }
            } else if (extractExternalIDs("Human Metabolome Database", searchResult, databaseIds)) {
                String hmdbID = databaseIds.get(0);
                if (!hmdbID.isEmpty()) {
                    compoundSearchResult.setDatabaseId(hmdbID);
                    fillMinimumInfoFromCTS(compoundSearchResult, searchResult);
                }
            } else if (extractExternalIDs("ChemSpider", searchResult, databaseIds)) {
                String chemSpiderID = databaseIds.get(0);
                if (!chemSpiderID.isEmpty()) {
                    compoundSearchResult.setDatabaseId("ChemSpider:" + chemSpiderID);
                    fillMinimumInfoFromCTS(compoundSearchResult, searchResult);
                }
            } else if (extractExternalIDs("Pubchem CID", searchResult, databaseIds)) {
                String pubChemID = databaseIds.get(0);
                if (!pubChemID.isEmpty()) {
                    compoundSearchResult.setDatabaseId("PubchemCID:" + pubChemID);
                    fillMinimumInfoFromCTS(compoundSearchResult, searchResult);
                }
            } else if (extractExternalIDs("Pubchem SID", searchResult, databaseIds)) {
                String pubChemID = databaseIds.get(0);
                if (!pubChemID.isEmpty()) {
                    compoundSearchResult.setDatabaseId("PubchemSID:" + pubChemID);
                    fillMinimumInfoFromCTS(compoundSearchResult, searchResult);
                }
            } else {
                fillMinimumInfoFromCTS(compoundSearchResult, searchResult);
            }
        }
        return compoundSearchResult;
    }

    private void fillMinimumInfoFromCTS(CompoundSearchResult compoundSearchResult, JSONObject searchResult) throws JSONException {
        compoundSearchResult.setFormula(getFormula(searchResult));
        compoundSearchResult.setInchi(getInChICode(searchResult));
    }

    private boolean isSuccess(String response) {
        if (response != null) {
            try {
                JSONObject object = new JSONObject(response);
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private String getInChICode(JSONObject searchResult) throws JSONException {
        String inchiCode = (String) searchResult.get("inchicode");
        return inchiCode;
    }

    private String getFormula(JSONObject searchResult) throws JSONException {
        String formula = (String) searchResult.get("formula");
        return formula;
    }

    private boolean extractExternalIDs(String fromDataResource, JSONObject searchResult, List<String> databaseIds) throws JSONException {
        JSONArray externalIDs = searchResult.getJSONArray("externalIds");
        for (int i = 0; i < externalIDs.length(); i++) {
            JSONObject dataSource = (JSONObject) externalIDs.get(i);
            String sourceName = (String) dataSource.get("name");
            if (sourceName.equalsIgnoreCase(fromDataResource)) {
                String sourceID = (String) dataSource.get("value");
                databaseIds.add(sourceID);
            }
        }
        return databaseIds.size() > 0;
    }

    public List<String> getSynonymsFor(String inchiKey) {
        String getURL = " http://cts.fiehnlab.ucdavis.edu/service/synonyms/" + inchiKey;
        String response = GenericCompoundWSClients.executeRequest(getURL, "GET", "");
        //TODO extract result from json
        return null;
    }

    public List<String> getSynonymsFromCactusFor(String inchiKey) {
        String getURL = " https://cactus.nci.nih.gov/chemical/structure/" + inchiKey + "/names";
        String response = GenericCompoundWSClients.executeRequest(getURL, "GET", "");
        //TODO extract result from json
        return null;
    }


    public List<String> extractChebiIds(JSONObject fullCompoundData) {
        List<String> chebiIDs = new ArrayList<>();
        //TODO extract result from json
        return chebiIDs;
    }

    public List<String> extractChemspiderIds(JSONObject fullCompoundData) {
        List<String> chemspiderIDs = new ArrayList<>();
        //TODO extract result from json
        return chemspiderIDs;
    }


    public List<String> extractPubchemIds(JSONObject fullCompoundData) {
        List<String> pubchemIDs = new ArrayList<>();
        //TODO extract result from json
        return pubchemIDs;
    }

    public String getSMILESFromCactusFor(String inchiKey) {
        String getURL = "https://cactus.nci.nih.gov/chemical/structure/" + inchiKey + "/smiles";
        String response = GenericCompoundWSClients.executeRequest(getURL, "GET", "");
        return parseSmilesFrom(response);
    }

    private String parseSmilesFrom(String response) {
        if (response == null) return null;
        String lines[] = response.split("\\r?\\n");
        return lines[0];
    }


    @Override
    public Collection<CompoundSearchResult> call() throws Exception {
        return null;
    }


}
