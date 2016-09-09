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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by kalai on 06/09/2016.
 */
@Controller
@RequestMapping("genericcompoundsearch")
public class CTSSearch implements Serializable, Cloneable, Callable<Collection<CompoundSearchResult>> {

    public static final String COMPOUND_NAME_MAPPING = "/name";


    //inchi-key to synonyms: http://cts.fiehnlab.ucdavis.edu/service/synonyms/WQZGKKKJIJFFOK-GASJEMHNSA-N

    //name to inchi-key: http://cts.fiehnlab.ucdavis.edu/service/convert/Chemical%20Name/InChIKey/glucose

    //inchi-key to all-info: http://cts.fiehnlab.ucdavis.edu/service/compound/LFQSCWFLJHTTHZ-UHFFFAOYSA-N
    //http://cts.fiehnlab.ucdavis.edu/service/compound/QNAYBMKLOCPYGJ-REOHCLBHSA-N

    //inchi-key to chemspider: http://cts.fiehnlab.ucdavis.edu/service/convert/InChIKey/Chemspider/QNAYBMKLOCPYGJ-REOHCLBHSA-N
    //inchi-key to chebi: http://cts.fiehnlab.ucdavis.edu/service/convert/InChIKey/ChEBI/QNAYBMKLOCPYGJ-REOHCLBHSA-N
    //inchi-key to pubchem: http://cts.fiehnlab.ucdavis.edu/service/convert/InChIKey/PubChem%20CID/QNAYBMKLOCPYGJ-REOHCLBHSA-N

    //get synonyms from inchikey: http://cts.fiehnlab.ucdavis.edu/service/synonyms/WQZGKKKJIJFFOK-GASJEMHNSA-N


    @RequestMapping(value = COMPOUND_NAME_MAPPING + "/{compoundName}")
    @ResponseBody
    public RestResponse<List<CompoundSearchResult>> getCompoundByName(@PathVariable("compoundName") final String compoundName) {
        RestResponse<List<CompoundSearchResult>> response = new RestResponse();
        List<CompoundSearchResult> searchHits = getSearchHitsForName(compoundName);
        response.setContent(searchHits);
        return response;
    }

    private List<CompoundSearchResult> getSearchHitsForName(String compoundName) {
        List<CompoundSearchResult> searchResult = new ArrayList<>();
        try {
            String inchikey = getInChIKeyFrom(compoundName);
            CompoundSearchResult compoundSearchResult = getFullCompoundUsing(inchikey);
            searchResult.add(compoundSearchResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return searchResult;
    }

    public String getInChIKeyFrom(String compoundName) throws JSONException {
        String getURL = "http://cts.fiehnlab.ucdavis.edu/service/convert/Chemical%20Name/InChIKey/" + compoundName;
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
        return fill(new JSONObject(response));
    }

    private CompoundSearchResult fill(JSONObject searchResult) throws JSONException {
        CompoundSearchResult compoundSearchResult = new CompoundSearchResult(SearchResource.CTS);
        if (empty(searchResult)) return compoundSearchResult;
        compoundSearchResult.setFormula(getFormula(searchResult));
        compoundSearchResult.setInchi(getInChICode(searchResult));
        List<String> chebiIDs = extractExternalIDs("ChEBI", searchResult);
        if (!chebiIDs.isEmpty()) {
            compoundSearchResult.setChebiId(chebiIDs.get(0));
        }
        return compoundSearchResult;
    }

    private boolean empty(JSONObject searchResult) throws JSONException {
        JSONArray result = searchResult.getJSONArray("result");
        return result.length() == 0;
    }

    private String getInChICode(JSONObject searchResult) throws JSONException {
        String inchiCode = (String) searchResult.get("inchicode");
        return inchiCode;
    }

    private String getFormula(JSONObject searchResult) throws JSONException {
        String formula = (String) searchResult.get("formula");
        return formula;
    }

    private List<String> extractExternalIDs(String fromDataResource, JSONObject searchResult) throws JSONException {
        JSONArray externalIDs = searchResult.getJSONArray("externalIds");
        List<String> externalResourceIDs = new ArrayList<>();
        for (int i = 0; i < externalIDs.length(); i++) {
            JSONObject dataSource = (JSONObject) externalIDs.get(i);
            String sourceName = (String) dataSource.get("name");
            if (sourceName.equalsIgnoreCase(fromDataResource)) {
                String sourceID = (String) dataSource.get("value");
                externalResourceIDs.add(sourceID);
            }
        }
        return externalResourceIDs;
    }

    public List<String> getSynonymsFrom(String inchiKey) {
        String getURL = " http://cts.fiehnlab.ucdavis.edu/service/synonyms/" + inchiKey;
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


    @Override
    public Collection<CompoundSearchResult> call() throws Exception {
        return null;
    }

}
