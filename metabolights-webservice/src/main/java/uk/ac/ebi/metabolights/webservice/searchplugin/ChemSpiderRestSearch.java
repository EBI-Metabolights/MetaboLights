/*
 * EBI MetaboLights - https://www.ebi.ac.uk/metabolights
 * Metabolomics team
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2018-Nov-28
 * Modified by:   kalai
 *
 * Copyright 2018 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.webservice.searchplugin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.webservice.utils.PropertiesUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public class ChemSpiderRestSearch implements Serializable, Cloneable, Callable<Collection<CompoundSearchResult>> {

    private static Logger logger = LoggerFactory.getLogger(ChemSpiderRestSearch.class);
    private final String ChemSpiderToken = PropertiesUtil.getProperty("chemspiderSecurityToken");
    private final String ChemSpiderEndpoint = "https://api.rsc.org/compounds/v1/";
    private List<CompoundSearchResult> compoundSearchResults = new ArrayList<CompoundSearchResult>();
    private String searchTerm;
    private SearchTermCategory searchTermCategory;
    private boolean searchComplete = false;

    public ChemSpiderRestSearch(SearchTermCategory searchTermCategory, String searchTerm) {
        this.searchTerm = searchTerm;
        this.searchTermCategory = searchTermCategory;
    }


    @Override
    public Collection<CompoundSearchResult> call() throws Exception {
        if (this.searchTermCategory.equals(SearchTermCategory.NAME)) {
            getCompoundSearchResults(doNameSearch());
        } else if (this.searchTermCategory.equals(SearchTermCategory.SMILES)) {
            getCompoundSearchResults(doSMILESSearch());
        } else if (this.searchTermCategory.equals(SearchTermCategory.INCHI)) {
            getCompoundSearchResults(doInChISearch());
        }
        return this.compoundSearchResults;
    }


    private String doNameSearch() {
        String response = "";
        try {
            JSONObject json = new JSONObject();
            json.put("name", searchTerm);
            json.put("orderBy", "");
            json.put("orderDirection", "");
            System.out.println(json.toString());
            response = GenericCompoundWSClients.executeRequest(this.ChemSpiderEndpoint + "filter/name", "POST", json.toString(), this.ChemSpiderToken);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    private String doInChISearch() {
        String request = "{ \"inchi\" : \"" + searchTerm + "\"}";
        String response = GenericCompoundWSClients.executeRequest(this.ChemSpiderEndpoint + "filter/inchi", "POST", request, this.ChemSpiderToken);
        return response;
    }

    private String doSMILESSearch() {
        String request = "{ \"smiles\" : \"" + searchTerm + "\"}";
        String response = GenericCompoundWSClients.executeRequest(this.ChemSpiderEndpoint + "filter/smiles", "POST", request, this.ChemSpiderToken);
        return response;
    }

    private void getCompoundSearchResults(String response) {
        if (response == null || response.isEmpty()) {
            return;
        }
        List<Integer> chemSpiderIDs = parseResponseAndGetChemSpiderIDs(response);
        if (chemSpiderIDs.size() > 0) {
            doBatchQueryAndExtractResults(chemSpiderIDs);
        }
    }

    private List<Integer> parseResponseAndGetChemSpiderIDs(String response) {
        if (response == null) {
            return new ArrayList<>();
        }
        List<Integer> chemSpiderIDs = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(response);
            String queryId = (String) object.get("queryId");
            while (!searchComplete) {
                checkForStatus(queryId);
            }
            if (searchComplete) {
                chemSpiderIDs = extractResults(queryId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return chemSpiderIDs;
    }

    private void checkForStatus(String queryId) {
        String response = GenericCompoundWSClients.executeRequest(this.ChemSpiderEndpoint + "filter/" + queryId + "/status", "GET", "", this.ChemSpiderToken);
        try {
            JSONObject object = new JSONObject(response);
            String status = (String) object.get("status");
            if (status.equals("Complete")) {
                searchComplete = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> extractResults(String queryId) {
        List<Integer> ids = new ArrayList<>();
        String response = GenericCompoundWSClients.executeRequest(this.ChemSpiderEndpoint + "filter/" + queryId + "/results", "GET", "", this.ChemSpiderToken);
        try {
            ChemSpiderIDResult result = new ObjectMapper().readValue(response, ChemSpiderIDResult.class);
            if (result != null) {
                if (result.getResults() != null && result.results.size() > 0) {
                    ids = result.getResults();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            new ArrayList<>();
        }
        return ids;
    }

    private void doBatchQueryAndExtractResults(List<Integer> chemSpiderIds) {
        ChemSpiderBatchRequest request = new ChemSpiderBatchRequest(chemSpiderIds);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String body = objectMapper.writeValueAsString(request);
            String response = GenericCompoundWSClients.executeRequest(this.ChemSpiderEndpoint + "records/batch", "POST", body, this.ChemSpiderToken);
            extractBatchResults(response);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    private void extractBatchResults(String response) {
        try {
            ChemSpiderBatchResult result = new ObjectMapper().readValue(response, ChemSpiderBatchResult.class);
            if (result != null) {
                if (result.getRecords() != null && result.getRecords().size() > 0) {
                    for (ChemSpiderRecord record : result.getRecords()) {
                        updateCompoundSearchResult(record);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCompoundSearchResult(ChemSpiderRecord record) {
        CompoundSearchResult compoundSearchResult = new CompoundSearchResult(SearchResource.CHEMSPIDER);
        compoundSearchResult.setDatabaseId(record.getId() != null && !record.getId().isEmpty() ? Utilities.appendNameSpaceAndConvert(Integer.parseInt(record.getId())) : "");
        compoundSearchResult.setFormula(record.getFormula() != null && !record.getFormula().isEmpty() ? Utilities.format(record.getFormula()) : "");
        compoundSearchResult.setInchi(record.getInchi() != null && !record.getInchi().isEmpty() ? record.getInchi() : "");
        compoundSearchResult.setSmiles(record.getSmiles() != null && !record.getSmiles().isEmpty() ? record.getSmiles() : "");
        compoundSearchResult.setName(record.getCommonName() != null && !record.getCommonName().isEmpty() ? record.getCommonName() : "");
        this.compoundSearchResults.add(compoundSearchResult);
    }

    static class ChemSpiderIDResult {
        private List<Integer> results;
        private boolean limitedToMaxAllowed;

        ChemSpiderIDResult(){};

        public List<Integer> getResults() {
            return results;
        }

        public boolean getLimitedToMaxAllowed() {
            return limitedToMaxAllowed;
        }
    }

    static class ChemSpiderRecord {
        private String id;
        private String smiles;
        private String formula;
        private String commonName;
        private String inchi;

        ChemSpiderRecord(){};

        public String getId() {
            return id;
        }

        public String getSmiles() {
            return smiles;
        }

        public String getFormula() {
            return formula;
        }

        public String getCommonName() {
            return commonName;
        }

        public String getInchi() {
            return inchi;
        }

    }

    static class ChemSpiderBatchRequest {
        static String[] keywords = {"SMILES", "Formula", "InChI", "CommonName"};

        private List<String> fields;
        private List<Integer> recordIds;

        ChemSpiderBatchRequest(){};

        ChemSpiderBatchRequest(List<Integer> recordIds) {
            this.recordIds = recordIds;
            this.fields = Arrays.asList(keywords);
        }

        public List<String> getFields() {
            return fields;
        }

        public List<Integer> getRecordIds() {
            return recordIds;
        }
    }

    static class ChemSpiderBatchResult {

        ChemSpiderBatchResult(){};

        List<ChemSpiderRecord> records;

        public List<ChemSpiderRecord> getRecords() {
            return records;
        }

    }

}
