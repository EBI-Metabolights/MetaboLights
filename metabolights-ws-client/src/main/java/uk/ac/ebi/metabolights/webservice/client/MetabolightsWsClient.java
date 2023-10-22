/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Apr-22
 * Modified by:   kenneth
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 6/11/14 10:03 AM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.webservice.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.referencelayer.model.Compound;
import uk.ac.ebi.metabolights.referencelayer.model.MetaboLightsCompound;
import uk.ac.ebi.metabolights.referencelayer.model.MetaboLightsCompoundModel;
import uk.ac.ebi.metabolights.referencelayer.model.MetaboliteAssignmentModel;
import uk.ac.ebi.metabolights.referencelayer.model.StudyModel;
import uk.ac.ebi.metabolights.repository.model.Entity;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.search.service.Booster;
import uk.ac.ebi.metabolights.search.service.Facet;
import uk.ac.ebi.metabolights.search.service.FacetLine;
import uk.ac.ebi.metabolights.search.service.SearchQuery;
import uk.ac.ebi.metabolights.search.service.SearchResult;
import uk.ac.ebi.metabolights.search.service.SearchUser;
import uk.ac.ebi.metabolights.webservice.client.models.ArrayListOfStrings;
import uk.ac.ebi.metabolights.webservice.client.models.CitationsList;
import uk.ac.ebi.metabolights.webservice.client.models.MixedSearchResult;
import uk.ac.ebi.metabolights.webservice.client.models.ReactionsList;

import javax.naming.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * User: conesa
 * Date: 30/08/2013
 * Time: 12:01
 */
public class MetabolightsWsClient {
    public static final String OBJECT_TYPE = "ObjectType";
    public static final String ASSAYS_TECHNOLOGY = "assays.technology";
    public static final String SPECIES_DATA = "compound.hasSpecies";
    public static final String PATHWAY_DATA = "compound.hasPathways";
    public static final String REACTION_DATA = "compound.hasReactions";
    public static final String NMR_DATA = "compound.hasNMR";
    public static final String MS_DATA = "compound.hasMS";
    public static final String STUDY_STATUS = "studyStatus";
    public static final String ORGANISM_ORGANISM_NAME = "organism.organismName";
    public static final String ORGANISM_ORGANISM_PART = "organism.organismPart";
    public static final String FACTORS_NAME = "factors.name";
    public static final String DESCRIPTORS_DESCRIPTION = "descriptors.description";
    public static final String VALIDATIONS_STATUS = "validations.status";
    public static final String VALIDATIONS_ENTRIES_STATUS = "validations.entries.statusExt";
    public static final String SEARCH_USER_STUDIES = "searchUserStudies";

    private static final Logger logger = LoggerFactory.getLogger(MetabolightsWsClient.class);

    private static final String ANONYMOUS = "JAVA_WS_client_Anonymous";
    private static final String DEAFULT_TOKEN_HEADER = "user_token";
    public static final String OBFUSCATIONCODE_PATH = "obfuscationcode/";
    private static final String INDEXING_PATH = "index/";
    public static final String SECURITY_PATH = "security/";
    public static final String SEC_STUDIES = "studies/";
    private static final String COMPOUND_PATH = "compounds/";



    private String metabolightsJavaWsUrl = null;
    private String metabolightsPythonWsUrl = null;
    private static final String STUDY_PATH = "study/";

    private static final String STUDY_PATH_WS = "public/study/";

    private static final String STUDY_LIST = "studies";

    private String tokenHeaderName = DEAFULT_TOKEN_HEADER;
    private String userToken = ANONYMOUS;

    public static MetabolightsWsClient getInstance() {
        return new MetabolightsWsClient();
    }
    public static MetabolightsWsClient getInstance(String metabolightsJavaWsUrl) {
        return new MetabolightsWsClient(metabolightsJavaWsUrl);
    }

    public static MetabolightsWsClient getInstanceForExporter(String metabolightsWsUrl) {
        return new MetabolightsWsClient(metabolightsWsUrl, "");
    }

    private MetabolightsWsClient() {
        this(null);
    }

    private MetabolightsWsClient(String metabolightsWsUrl,String exporter) {
        this.metabolightsJavaWsUrl = metabolightsWsUrl;
        this.metabolightsPythonWsUrl = metabolightsWsUrl;
    }
    private MetabolightsWsClient(String metabolightsJavaWsUrl) {
        this.initializeEnvironmentVariables();
    }

    private void initializeEnvironmentVariables() {
        Context initCtx;
        try {
            initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            this.metabolightsJavaWsUrl = (String)envCtx.lookup("metabolightsJavaWsInternalUrl");
            this.metabolightsPythonWsUrl = (String)envCtx.lookup("metabolightsPythonWsInternalUrl");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    private String makeRequest(String path, String method) {
       return makeRequestSendingData(path,null,method, this.metabolightsJavaWsUrl, null);
    }

    private String makeRequestSendingData(String path, Object dataToSend, String method, String host, String userToken) {

        logger.debug("Making a {} request to {}", method, path);

        try {

            // Get a post connection
            HttpURLConnection conn = getHttpURLConnection(host, path, method, userToken);
            if(dataToSend != null){
                conn.setRequestProperty("content-type", "application/json");
            }
            conn.setDoOutput(true);

            if (dataToSend != null) {

                String json = null;

                // If it's not a String
                if (!(dataToSend instanceof String)) {

                    json = serializeObject(dataToSend);
                } else {
                    json = (String) dataToSend;
                }

                // Send JSON content
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());

                out.write(json);
                out.close();

            }

            // Read response
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("MetaboLights Java WS client: " + conn.getURL().toString() + "(" + method + ") request failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String message = org.apache.commons.io.IOUtils.toString(br);

            conn.disconnect();

            return message;

        } catch (MalformedURLException e) {

            RestResponse response = new RestResponse();
            response.setMessage("Malformed url: " + path);
            response.setErr(e);
            logger.error(response.getMessage(), e);

            return serializeObject(response);


        } catch (IOException e) {

            RestResponse response = new RestResponse();
            response.setMessage("IO exception while trying to reach " + path);
            response.setErr(e);
            logger.error(response.getMessage(), e);

            return serializeObject(response);

        }
        catch (Exception e){
            System.out.println("Exception occured - "+e);
            RestResponse response = new RestResponse();
            response.setMessage("IO exception while trying to reach " + path);
            response.setErr(e);
            logger.error(response.getMessage(), e);

            return serializeObject(response);
        }
    }

    public String makePythoDeleteRequest(String path, Object data) {
        return makePythonDeleteRequest(path, data, null);
    }
    public String makePythonDeleteRequest(String path, Object data, String userToken) {
        return makeRequestSendingData(path, data, "DELETE", this.metabolightsPythonWsUrl, userToken);
    }

    public String makePostRequest(String path, Object data) {
        return makeRequestSendingData(path, data, "POST", this.metabolightsJavaWsUrl, null);
    }
    public String makePythonPostRequest(String path, Object data) {
        return makePythonPostRequest(path, data, null);
    }
    public String makePythonPostRequest(String path, Object data, String userToken) {
        return makeRequestSendingData(path, data, "POST", this.metabolightsPythonWsUrl, userToken);
    }
    private String makePutRequest(String path, Object data) {
        return makeRequestSendingData(path, data, "PUT", this.metabolightsJavaWsUrl, null);
    }
    public String makePythonPutRequest(String path, Object data) {
        return makePythonPutRequest(path, data, null);
    }
    public String makePythonPutRequest(String path, Object data, String userToken) {
        return makeRequestSendingData(path, data, "PUT", this.metabolightsJavaWsUrl, userToken);
    }
    private String makeGetRequest(String path) {
        return makeRequest(path, "GET");
    }

    public String makePythonGetRequest(String path) {
        return makePythonGetRequest(path, null);
    }

    public String makePythonGetRequest(String path, String userToken) {
        return makeRequestSendingData(path,  null,  "GET", this.metabolightsPythonWsUrl, userToken);
    }
    private HttpURLConnection getHttpURLConnection(String mtblsWsUrl, String path, String method, String userToken) throws IOException {
        URL url;
        String base = mtblsWsUrl;
        if (base == null)
           base = metabolightsJavaWsUrl;
        if (base.endsWith("/"))
            base = base.replaceAll(".$", "");
        if (path.startsWith("/"))
            path = path.substring(1);

        url = new URL(base + "/" + path);

        // System.out.println( " !!metabolightsWsURI :- " +url);
        logger.debug( " !!metabolightsWsURI :- {}", url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Accept", "application/json");
        if (userToken != null)
            conn.setRequestProperty(tokenHeaderName, userToken);
        else
            conn.setRequestProperty(tokenHeaderName, this.userToken);

        return conn;
    }

    public RestResponse<Study> getStudy(String studyIdentifier) {

        logger.debug("Study " + studyIdentifier + " requested to the MetaboLights WS client");

        String path = getStudyPathJavaWS(studyIdentifier);

        // Make the request
        return getStudyRestResponse(path);

    }

    public RestResponse<StudyModel> getStudyModel(String studyIdentifier) {

        logger.debug("Study " + studyIdentifier + " requested to the MetaboLights WS client");
        String path = getStudyPathWS(studyIdentifier);

        // Make the request
        return getStudyModelRestResponse(path);

    }

    public RestResponse<Study> getStudybyObfuscationCode(String obfuscationCode) {

        logger.info("Study by obfuscation code " + obfuscationCode + " requested to the MetaboLights WS client");

        String path = getObfuscationPath(obfuscationCode);

        return getStudyRestResponse(path);

    }


    private RestResponse<Study> getStudyRestResponse(String path) {
        // Make the request
        String response = makeGetRequest(path);
        RestResponse<Study> resS = deserializeJSONString(response, Study.class);
        return resS;
    }

    private RestResponse<StudyModel> getStudyModelRestResponse(String path) {
        // Make the request
        String response = makeGetRequest(path);
        RestResponse<StudyModel> resS = deserializeJSONString(response, StudyModel.class);
        return resS;
    }

    private RestResponse<Compound> getCompoundRestResponse(String path) {
        // Make the request

        String response = makeGetRequest(path);

        return deserializeJSONString(response, Compound.class);
    }

    private RestResponse<MetaboLightsCompoundModel> getCompoundModelRestResponse(String path) {
        // Make the request

        String response = makeGetRequest(path);

        return deserializeJSONString(response, MetaboLightsCompoundModel.class);
    }

    private String getObfuscationPath(String obfuscationCode) {
        return STUDY_PATH + OBFUSCATIONCODE_PATH + obfuscationCode;
    }


    public RestResponse<String[]> getAllStudyAcc() {

        logger.debug("Getting all public study identifiers from the MetaboLights Python WS client");
        // Make the request
        
        String response = makePythonGetRequest(STUDY_LIST);

        return deserializeJSONString(response, String[].class);

    }

    public RestResponse<String[]> getAllCompoundsAcc() {

        logger.debug("Getting all public compound identifiers from the MetaboLights Python WS client");
        System.out.println(" getAllCompoundsAcc : " + COMPOUND_PATH + "list");
        // Make the request
        String response = makePythonGetRequest(COMPOUND_PATH + "list");

        return deserializeJSONString(response, String[].class);

    }
    private void addFacet(String facetName, SearchQuery query) {

        query.getFacets().add(new Facet(facetName));
    }

    private SearchQuery getEmptyQuery() {

        SearchQuery emptyQuery = new SearchQuery();

        addFacet(OBJECT_TYPE, emptyQuery);
        addFacet(ASSAYS_TECHNOLOGY, emptyQuery);
        addFacet(STUDY_STATUS, emptyQuery);
        addFacet(ORGANISM_ORGANISM_NAME, emptyQuery);
        addFacet(ORGANISM_ORGANISM_PART, emptyQuery);
        addFacet(SPECIES_DATA, emptyQuery);
        addFacet(PATHWAY_DATA, emptyQuery);
        addFacet(REACTION_DATA, emptyQuery);
        addFacet(NMR_DATA, emptyQuery);
        addFacet(MS_DATA, emptyQuery);

//		<c:if test="${not empty usersFullName}">
//				,"lines":[{"value":"${usersFullName}","checked":true}]
//			</c:if>
//
//        commented out temporarily
//        addFacet(FACTORS_NAME, emptyQuery);
//        addFacet(DESCRIPTORS_DESCRIPTION, emptyQuery);
        // addFacet(VALIDATIONS_STATUS, emptyQuery);
        // addFacet(VALIDATIONS_ENTRIES_STATUS, emptyQuery);

        emptyQuery.getPagination().setPage(1);
        emptyQuery.getPagination().setPageSize(10);

        Booster titleBooster = new Booster();
        titleBooster.setBoost(1);
        titleBooster.setFieldName("title");

        titleBooster = new Booster();
        titleBooster.setBoost(1);
        titleBooster.setFieldName("name");

        titleBooster = new Booster();
        titleBooster.setBoost(2);
        titleBooster.setFieldName("_id");


        emptyQuery.getBoosters().add(titleBooster);
//
//		<c:if test="${not empty freeTextQuery}">
//				emptyQuery.text = "${freeTextQuery}";
//		</c:if>

        return emptyQuery;


    }

    private String getStudyPath(String studyIdentifier) {
        return STUDY_PATH + studyIdentifier;
    }

    private String getStudyPathWS(String studyIdentifier) {
        StringBuilder str = new StringBuilder(STUDY_LIST);
        str.append("/").append(STUDY_PATH_WS).append(studyIdentifier);
        return str.toString();
    }

    private String getStudyPathJavaWS(String studyIdentifier) {
        StringBuilder str = new StringBuilder();
        str.append(STUDY_PATH).append(studyIdentifier);
        return str.toString();
    }

    private String getIndexingPath(String action) {
        return INDEXING_PATH + action;
    }

    public String getMetabolitesJSON(String studyIdentifier, int assayNumber){

        StringBuilder str = new StringBuilder(STUDY_LIST);
        str.append("/").append(STUDY_PATH_WS).append(studyIdentifier).append("/");
        str.append("assay/").append(assayNumber-1).append("/maf");

        String path =  str.toString();
        // Make the request
        String response = makeRequestSendingData(path, null, "GET", this.metabolightsJavaWsUrl, null);

        return response;
    }

    public RestResponse<MetaboliteAssignment> getMetabolites(String studyIdentifier, int assayNumber) {
        String response = getMetabolitesJSON(studyIdentifier,assayNumber);
        return deserializeJSONString(response, MetaboliteAssignment.class);

    }

    public RestResponse<MetaboliteAssignmentModel> getMetabolitesSimple(String studyIdentifier, int assayNumber) {
        String response = getMetabolitesJSON(studyIdentifier,assayNumber);
        return deserializeJSONString(response, MetaboliteAssignmentModel.class);

    }

    public RestResponse<MetaboliteAssignment> getMetabolitesByObfuscationCode(String obfuscationCode, int assayNumber) {

        logger.info("Metabolites by obfuscation code " + obfuscationCode + " requested to the MetaboLights WS client");

        String path = getObfuscationPath(obfuscationCode) + "/assay/" + assayNumber + "/maf";

        // Make the request
        String response = makeGetRequest(path);

        return deserializeJSONString(response, MetaboliteAssignment.class);

    }


    public static <T> RestResponse<T> deserializeJSONString(String response, Class<T> valueType) {

        logger.debug("Parsing json response into MetaboLights model: " + response);

        // Parse response (json) into Study entity...

        // Add guava serialization for multimaps (Table.Fields is a multimap now).
        ObjectMapper mapper = new ObjectMapper();

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        try {

            JavaType type = mapper.getTypeFactory().constructParametricType(RestResponse.class, valueType);

            return mapper.readValue(response, type);

        } catch (IOException e) {
            System.out.println("Can't parse ws response (json) back into " +  valueType.getName() + ": " + e.getMessage());
            logger.error("Can't parse ws response (json) back into " + valueType.getName() + ": " + e.getMessage());
            logger.debug("Response is: " + response);

        }

        return null;
    }

    public static String serializeObject(Object objectToSerialize) {

        logger.debug("Serializing object to a Json string:" + objectToSerialize.getClass());

        // Get the mapper
        ObjectMapper mapper = new ObjectMapper();


        try {

            return mapper.writeValueAsString(objectToSerialize);

        } catch (IOException e) {

            logger.error("Can't serialize " + objectToSerialize.getClass());

        }

        return null;
    }

    // public RestResponse<? extends MixedSearchResult> search2() {

    //     logger.info("Empty search requested to the MetaboLights WS client");

    //     // Make the request
    //     String response = makeGetRequest("search");

    //     MixedSearchResult foo = new MixedSearchResult();

    //     return deserializeJSONString(response, foo.getClass());


    // }


    public RestResponse<? extends MixedSearchResult> search() {

        logger.debug("Empty search requested to the MetaboLights WS client");

        // Make the request
        String response = makePythonPostRequest("/es-index/search", "");

        return deserialize(response,  getEmptyQuery());
        // MixedSearchResult foo = new MixedSearchResult();

        // return deserializeJSONString(response, foo.getClass());


    }

    // public RestResponse<? extends MixedSearchResult> search2(SearchQuery query) {

    //     logger.debug("Search requested to the MetaboLights WS client");

    //     String json = serializeObject(query);

    //     // Make the request
    //     // String response = makePythonPostRequest("/es-index/search", json);
    //     String response = makePostRequest("search", json);

    //     MixedSearchResult foo = new MixedSearchResult();

    //     return deserializeJSONString(response, foo.getClass());


    // }

    public RestResponse<? extends MixedSearchResult> search(SearchQuery query) {

        logger.debug("Search requested to the MetaboLights WS client");

        String json = serializeObject(query);

        // Make the request
        String response = makePythonPostRequest("/es-index/search", json);
        return deserialize(response, query);
        // MixedSearchResult foo = new MixedSearchResult();

        // return deserializeJSONString(response, foo.getClass());

    }

    private RestResponse<MixedSearchResult> deserialize(String input, SearchQuery query){
        JSONParser parser = new JSONParser();
        ObjectMapper mapper= new ObjectMapper();
        mapper.setTimeZone(Calendar.getInstance().getTimeZone());
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        RestResponse<MixedSearchResult> response = new RestResponse<MixedSearchResult>();
        response.setContent(new MixedSearchResult());
        response.setErr(null);
        response.getContent().setQuery(query);
        
        try {
            JSONObject result = (JSONObject) parser.parse(input);
            response.setMessage(null);
            
            if(result != null && result.containsKey("content")){
                JSONObject content = (JSONObject) result.get("content");
                
                if( content != null && content.containsKey("results")){
                    SearchQuery sq = new SearchQuery();
                    JSONObject queryJson = (JSONObject) content.get("query");
                    mapper.readerForUpdating(sq).readValue(queryJson.toJSONString());
                    
                    // Map<String, Map<String, FacetLine>> facetLines = new HashMap<String, Map<String, FacetLine>> ();
                    // for (Facet facet : sq.getFacets()) {
                    //     Map<String, FacetLine> lineMap = new HashMap<String, FacetLine>();
                    //     facetLines.put(facet.getName(), lineMap);

                    //     for (FacetLine line : facet.getLines()){
                    //         lineMap.put(line.getValue(), line);
                    //     }
                    // }
                                        
                    // for (Facet facet_item : response.getContent().getQuery().getFacets()) {
                    //     for (FacetLine line : facet_item.getLines()){
                    //         if(line.isChecked()){
                    //             if (facetLines.containsValue(facet_item.getName())){
                    //                 if (facetLines.get(facet_item.getName()).containsKey(line.getValue())){
                    //                     FacetLine item = facetLines.get(facet_item.getName()).get(line.getValue());
                    //                     item.setChecked(true);
                    //                 }
                    //             }
                    //         }
                    //     }
                    // }
                    response.getContent().setQuery(sq);
                    // response.getContent().getQuery().getPagination().setItemsCount(sq.getPagination().getItemsCount());
                    
                    Collection<Entity> resultList = response.getContent().getResults();
                    JSONArray results = (JSONArray) content.get("results");
                    
                    for (Object item : results){
                        JSONObject entity = (JSONObject) item;
                        if (entity.containsKey("ObjectType")){
                            String type = (String) entity.get("ObjectType");
                            if (type.equalsIgnoreCase("study")){
                                LiteStudy study = new LiteStudy();
                                mapper.readerForUpdating(study).readValue(entity.toJSONString());
                                resultList.add(study);
                            } else if (type.equalsIgnoreCase("compound")){
                                MetaboLightsCompound compound = new MetaboLightsCompound();
                                String data = entity.toJSONString();
                                try {
                                    compound =  mapper.readerForUpdating(compound).readValue(data);
                                    resultList.add(compound);
                                } catch(Exception ex){
                                    System.out.println("Error for compound: " + entity.get("name"));
                                }


                            } else {
                                System.out.println("Object type is not defined: " + type);
                            }
                        } else {
                            System.out.println("Object type is not defined");
                        }
                    }
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return response;
        
    }

    public RestResponse<String> updatePublicReleaseDate(Date newPublicReleaseDate, String studyIdentifier) {

        logger.debug("Public release date ({}) update of {} requested to MetaboLights WS client.", newPublicReleaseDate, studyIdentifier);

        String json = serializeObject(newPublicReleaseDate);

        // Make the request
        String response = makePutRequest(getStudyPath(studyIdentifier) + "/publicreleasedate" , json);

        return deserializeJSONString(response, String.class);

    }

    public RestResponse<String> updateStatus(LiteStudy.StudyStatus newStatus, String studyIdentifier) {

        logger.debug("Status ({}) update of {} requested to MetaboLights WS client.", newStatus, studyIdentifier);

        String json = serializeObject(newStatus);

        // Make the request
        String response = makePutRequest(getStudyPath(studyIdentifier) + "/status" , json);

        return deserializeJSONString(response, String.class);

    }

    public RestResponse<? extends SearchResult> searchStudyWithResponse(String studyIdentifier) {

        return  searchStudyWithResponse(studyIdentifier, "_id");

    }

    public RestResponse<? extends SearchResult> searchStudyWithResponse(String studyIdentifier, String field) {

        logger.debug("Requesting a single study ({}) to the search engine.", studyIdentifier);

        // Create the search query
        SearchQuery searchQuery = new SearchQuery();

        // NOTE: Elastic search explicit language!! breaking search interface.
        // Adding the non escaping char to avoid escaping :
        searchQuery.setText("'" +field + ":" + studyIdentifier);

        return  search(searchQuery);


    }

    public LiteStudy searchStudy(String studyIdentifier){

        RestResponse<MixedSearchResult> response = (RestResponse<MixedSearchResult>) searchStudyWithResponse(studyIdentifier);

        if (response.getContent().getResults().size() == 0) {
            return null;
        } else {
            return (LiteStudy) response.getContent().getResults().iterator().next();
        }
    }

    public RestResponse<String> restore(String studyIdentifier, String backupIdentifier) {

        logger.debug("Restoring {} for {} requested to MetaboLights WS client.", backupIdentifier, studyIdentifier);

        // Make the request
        String response = makePutRequest(getStudyPath(studyIdentifier) + "/restore", backupIdentifier);

        return deserializeJSONString(response, String.class);

    }

    public boolean canViewStudy(String studyIdentifier) {
        logger.debug("Requesting canViewStudy ({}) to the webservice.", studyIdentifier);

        // Make the request
        String response = makeGetRequest(SECURITY_PATH + SEC_STUDIES + studyIdentifier + "/view");

        RestResponse<Boolean> restResponse = deserializeJSONString(response, Boolean.class);

        return restResponse.getContent();

    }

    public boolean canViewStudyByObfuscationCode(String obfuscationCode) {


        logger.debug("Requesting canViewStudy ({}) to the webservice by obfuscation code.", obfuscationCode);

        // Make the request
        //studies/obfuscationcode/{obfuscationcode}/view
        String response = makeGetRequest(SECURITY_PATH + SEC_STUDIES + "obfuscationcode/" + obfuscationCode + "/view");

        RestResponse<Boolean> restResponse = deserializeJSONString(response, Boolean.class);

        return restResponse.getContent();

    }

    public RestResponse<Compound> getCompound(String compoundIdentifier) {

        logger.debug("Compound " + compoundIdentifier + " requested to the MetaboLights WS client");
        System.out.println("Compound " + compoundIdentifier + " requested to the MetaboLights WS client");

        String path = getCompoundPath(compoundIdentifier);

        // Make the request
        return getCompoundRestResponse(path);

    }

    public RestResponse<MetaboLightsCompoundModel> getCompoundModel(String compoundIdentifier) {

        logger.debug("Compound " + compoundIdentifier + " requested to the MetaboLights WS client");
        System.out.println("Compound " + compoundIdentifier + " requested to the MetaboLights WS client");

        String path = getCompoundPath(compoundIdentifier);

        // Make the request
        return getCompoundModelRestResponse(path);

    }

    public RestResponse<ReactionsList> getCompoundReactions(String compoundIdentifier) {

        logger.debug("Reactions for compound " + compoundIdentifier + " requested to the MetaboLights WS client");

        String path = getCompoundPath(compoundIdentifier);

        path = path + "/reactions";


        // Make the request
        String response = makeGetRequest(path);

        ReactionsList reactions = new ReactionsList();

        return (RestResponse<ReactionsList>) deserializeJSONString(response, reactions.getClass());

    }


    private String getCompoundPath(String compoundIdentifier) {
        return  COMPOUND_PATH + compoundIdentifier;
    }


    public RestResponse<CitationsList> getCompoundCitations(String compoundIdentifier) {

        logger.debug("Reactions for compound " + compoundIdentifier + " requested to the MetaboLights WS client");

        String path = getCompoundPath(compoundIdentifier);

        path = path + "/citations";


        // Make the request
        String response = makeGetRequest(path);

        CitationsList citations = new CitationsList();

        return (RestResponse<CitationsList>) deserializeJSONString(response, citations.getClass());


    }

    /**
     *
     *
     * INDEXING MANAGEMENT OPERATIONS
     *
     *
     */


    /**
     * Index status
     */
    public RestResponse<ArrayListOfStrings> getIndexStatus(){

        String path = getIndexingPath("status");

        // Make the request
        String responseS = makeGetRequest(path);

        return deserializeJSONString(responseS, ArrayListOfStrings.class);

    }



}
