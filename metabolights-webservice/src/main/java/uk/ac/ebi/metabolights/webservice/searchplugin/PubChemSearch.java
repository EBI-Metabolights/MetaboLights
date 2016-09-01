package uk.ac.ebi.metabolights.webservice.searchplugin;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by kalai on 04/08/2016.
 */
public class PubChemSearch {

    private String pubchemUrl = GenericCompoundWSClients.getPubChemWSURL();

    //    to get cid by name

    //https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/name/hexose/cids/json

    //http://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/cid/206/property/MolecularFormula,CanonicalSMILES,inchi,molecularformula,iupacname/json

    //  https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/cid/206/xrefs/registryID/json

    public boolean searchAndFillByName(String name, CompoundSearchResult compoundSearchResult) {
        String searchURL = pubchemUrl + "name/" + name + "/cids/json";
        String pubchemCID = getAnyMatchingCID(searchURL, "GET", "");
        if (pubchemCID.isEmpty()) return false;
        fetchAndFillFullInfo(pubchemCID, compoundSearchResult);
        return compoundSearchResult.isComplete();
    }

    private String getAnyMatchingCID(String searchURL, String method, String postBody) {
        String pubchemResponse = excuteRequest(searchURL, "GET", "");
        if (pubchemResponse == null) return "";
        Integer pubchemCID;
        try {
            JSONObject myObject = new JSONObject(pubchemResponse);
            JSONObject myObject2 = myObject.getJSONObject("IdentifierList");
            JSONArray array1 = (JSONArray) myObject2.get("CID");
            pubchemCID = (Integer) array1.get(0);
            return pubchemCID.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void fetchAndFillFullInfo(String pubchemID, CompoundSearchResult compoundSearchResult) {
        JSONObject result = getMetaData(pubchemID);
        if (result != null) {
            fillFullInfo(compoundSearchResult, result);
            String chebiID = getChebiID(pubchemID);
            if (!chebiID.isEmpty()) {
                compoundSearchResult.setChebiId(chebiID);
            }
        }
    }

    private JSONObject getMetaData(String pubchemID) {
        String searchURL = pubchemUrl + "cid/" + pubchemID + "/property/MolecularFormula,CanonicalSMILES,inchi,molecularformula,iupacname/json";
        String pubchemResponse = excuteRequest(searchURL, "GET", "");
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
        String pubchemResponse = excuteRequest(searchURL, "GET", "");
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


    private String excuteRequest(String requestURL, String method, String postBody) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            //String encodedUrl = URLEncoder.encode(requestURL, "UTF-8");

            URL url = new URL(requestURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);      // "GET"
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setDoOutput(true);

            if (method.equalsIgnoreCase("post")) {
                if (!postBody.isEmpty()) {
                    OutputStream os = connection.getOutputStream();
                    os.write(postBody.getBytes());
                    os.flush();
                }
            }

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("MetaboLights Java WS client: " + connection.getURL().toString() + "(" + "GET" + ") request failed : HTTP error code : "
                        + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            String message = org.apache.commons.io.IOUtils.toString(br);

            connection.disconnect();

            return message;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public boolean searchAndFillByInChI(String inchi, CompoundSearchResult compoundSearchResult) {
        String searchURL = pubchemUrl + "inchi/cids/JSON";
        String pubchemCID = getAnyMatchingCID(searchURL, "POST", inchi);
        if (pubchemCID.isEmpty()) return false;
        fetchAndFillFullInfo(pubchemCID, compoundSearchResult);
        return compoundSearchResult.isComplete();
    }

    public boolean searchAndFillBySMILES(String smiles, CompoundSearchResult compoundSearchResult) {
        String searchURL = pubchemUrl + "smiles/" + smiles + "/cids/json";
        String pubchemCID = getAnyMatchingCID(searchURL, "GET", "");
        if (pubchemCID.isEmpty()) return false;
        fetchAndFillFullInfo(pubchemCID, compoundSearchResult);
        return compoundSearchResult.isComplete();
    }
}
