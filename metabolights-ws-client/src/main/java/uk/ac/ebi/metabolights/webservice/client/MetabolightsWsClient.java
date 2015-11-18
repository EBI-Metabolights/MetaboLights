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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.referencelayer.model.Compound;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.search.service.SearchQuery;
import uk.ac.ebi.metabolights.search.service.SearchResult;
import uk.ac.ebi.metabolights.webservice.client.models.ArrayListOfStrings;
import uk.ac.ebi.metabolights.webservice.client.models.CitationsList;
import uk.ac.ebi.metabolights.webservice.client.models.MixedSearchResult;
import uk.ac.ebi.metabolights.webservice.client.models.ReactionsList;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: conesa
 * Date: 30/08/2013
 * Time: 12:01
 */
public class MetabolightsWsClient {

    private static final Logger logger = LoggerFactory.getLogger(MetabolightsWsClient.class);

    private static final String ANONYMOUS = "JAVA_WS_client_Anonymous";
    private static final String DEAFULT_TOKEN_HEADER = "user_token";
    public static final String OBFUSCATIONCODE_PATH = "obfuscationcode/";
    private static final String INDEXING_PATH = "index/";
    public static final String REINDEX_PATH = "all";
    public static final String SECURITY_PATH = "security/";
    public static final String SEC_STUDIES = "studies/";
    private static final String COMPOUND_PATH = "compounds/";
    public static final String STUDIES = "studies";
    public static final String COMPOUNDS = "compounds";
    private static final String QUEUE_PATH = "queue/";

    private String metabolightsWsUrl = "http://www.ebi.ac.uk/metabolights/webservice/";
    private static final String STUDY_PATH = "study/";

    private String tokenHeaderName = DEAFULT_TOKEN_HEADER;
    private String userToken = ANONYMOUS;

    public MetabolightsWsClient(String metabolightsWsUrl) {
        this.metabolightsWsUrl = metabolightsWsUrl;
    }

    public MetabolightsWsClient() {
    }


    public String getMetabolightsWsUrl() {
        return metabolightsWsUrl;
    }

    public void setMetabolightsWsUrl(String metabolightsWsUrl) {
        this.metabolightsWsUrl = metabolightsWsUrl;
    }

    public String getTokenHeaderName() {
        return tokenHeaderName;
    }

    public void setTokenHeaderName(String tokenHeaderName) {
        this.tokenHeaderName = tokenHeaderName;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    private String makeRequest(String path, String method) {

       return makeRequestSendingData(path,null,method);

    }
    private String makeRequestSendingData(String path, Object dataToSend, String method) {

        logger.debug("Making a {} request to {}", method,path);

        try {

            // Get a post connection
            HttpURLConnection conn = getHttpURLConnection(path, method);

            conn.setRequestProperty("content-type", "application/json");
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
    }


    private String makePostRequest(String path, Object data) {
        return makeRequestSendingData(path, data, "POST");
    }

    private String makePutRequest(String path, Object data) {
        return makeRequestSendingData(path, data, "PUT");
    }

    private String makeDeleteRequest(String path) {return makeRequestSendingData(path, null,"DELETE");
    }
    private String makeDeleteRequest(String path, Object data) {return makeRequestSendingData(path, data,"DELETE");
    }

    private String makeGetRequest(String path) {
        return makeRequest(path, "GET");
    }

    private HttpURLConnection getHttpURLConnection(String path, String method) throws IOException {


        URL url = new URL(metabolightsWsUrl + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty(tokenHeaderName, userToken);

        return conn;
    }

    public RestResponse<Study> getStudy(String studyIdentifier) {

        logger.debug("Study " + studyIdentifier + " requested to the MetaboLights WS client");

        String path = getStudyPath(studyIdentifier);

        // Make the request
        return getStudyRestResponse(path);

    }

    public RestResponse<Study> getStudybyObfuscationCode(String obfuscationCode) {

        logger.info("Study by obfuscation code " + obfuscationCode + " requested to the MetaboLights WS client");

        String path = getObfuscationPath(obfuscationCode);

        return getStudyRestResponse(path);

    }

    private RestResponse<Study> getStudyRestResponse(String path) {
        // Make the request
        String response = makeGetRequest(path);

        return deserializeJSONString(response, Study.class);
    }

    private RestResponse<Compound> getCompoundRestResponse(String path) {
        // Make the request

        String response = makeGetRequest(path);

        return deserializeJSONString(response, Compound.class);
    }

    private String getObfuscationPath(String obfuscationCode) {
        return STUDY_PATH + OBFUSCATIONCODE_PATH + obfuscationCode;
    }


    public RestResponse<String[]> getAllStudyAcc() {

        logger.debug("Getting all public study identifiers from the MetaboLights WS client");

        // Make the request
        String response = makeGetRequest(STUDY_PATH + "list");

        return deserializeJSONString(response, String[].class);

    }

    public RestResponse<String[]> getAllCompoundsAcc() {

        logger.debug("Getting all public compound identifiers from the MetaboLights WS client");

        // Make the request
        String response = makeGetRequest(COMPOUND_PATH + "list");

        return deserializeJSONString(response, String[].class);

    }


    private String getStudyPath(String studyIdentifier) {
        return STUDY_PATH + studyIdentifier;
    }

    private String getIndexingPath(String action) {
        return INDEXING_PATH + action;
    }

    private String getQueuePath(String action) {
        return QUEUE_PATH + action;
    }
    public RestResponse<MetaboliteAssignment> getMetabolites(String studyIdentifier, int assayNumber) {

        String path = getStudyPath(studyIdentifier) + "/assay/" + assayNumber + "/maf";

        // Make the request
        String response = makeGetRequest(path);

        return deserializeJSONString(response, MetaboliteAssignment.class);

    }

    public RestResponse<MetaboliteAssignment> getMetabolitesByObfuscationCode(String obfuscationCode, int assayNumber) {

        logger.info("Metabolites by obfuscation code " + obfuscationCode + " requested to the MetaboLights WS client");

        String path = getObfuscationPath(obfuscationCode) + "/assay/" + assayNumber + "/maf";

        // Make the request
        String response = makeGetRequest(path);

        return deserializeJSONString(response, MetaboliteAssignment.class);

    }


    private <T> RestResponse<T> deserializeJSONString(String response, Class<T> valueType) {

        logger.debug("Parsing json response into MetaboLights model: " + response);

        // Parse response (json) into Study entity...

        // Add guava serialization for multimaps (Table.Fields is a multimap now).
        ObjectMapper mapper = new ObjectMapper();

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        try {

            JavaType type = mapper.getTypeFactory().constructParametricType(RestResponse.class, valueType);

            return mapper.readValue(response, type);

        } catch (IOException e) {

            logger.error("Can't parse ws response (json) back into " + valueType.getName() + ": " + e.getMessage());
            logger.debug("Response is: " + response);

        }

        return null;
    }

    private String serializeObject(Object objectToSerialize) {

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

    public RestResponse<? extends MixedSearchResult> search() {

        logger.debug("Empty search requested to the MetaboLights WS client");

        // Make the request
        String response = makeGetRequest("search");

        MixedSearchResult foo = new MixedSearchResult();

        return deserializeJSONString(response, foo.getClass());


    }

    public RestResponse<? extends MixedSearchResult> search(SearchQuery query) {

        logger.debug("Search requested to the MetaboLights WS client");

        String json = serializeObject(query);

        // Make the request
        String response = makePostRequest("search", json);

        MixedSearchResult foo = new MixedSearchResult();

        return deserializeJSONString(response, foo.getClass());


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

    public RestResponse<String> deleteStudy(String studyIdentifier){

        String response = makeDeleteRequest(getStudyPath(studyIdentifier));

        return deserializeJSONString(response, String.class);

    }

    /**
     * Deletes a series of files selected from the Study Files tab in a study.
     *
     * @param studyId
     * @param obfuscationCode, the user credentials
     * @param selectedFiles, the list of files to be deleted
     * @return
     * @author: jrmacias
     * @date: 20151012
     */
    public RestResponse<String> deleteFilesFromStudy(String studyId,
                                                     String obfuscationCode,
                                                     List<String> selectedFiles){

        String response = makePostRequest(STUDY_PATH + studyId + "/deleteFiles",selectedFiles);
        logger.info("Deleting files from study {} by user request.", studyId);

        return deserializeJSONString(response, String.class);
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

// It doesn't work since the user is anonymous and elastic search module always apply a security filter returning only owned private studies or Public ones.
// If the study is private this will not work.
//    public LiteStudy searchStudybyObfuscationCode(String obfuscationCode){
//
//        RestResponse<StudySearchResult> response = (RestResponse<StudySearchResult>) searchStudyWithResponse(obfuscationCode, "obfuscationCode");
//
//
//        if (response.getContent().getResults().size() == 0) {
//            return null;
//        } else {
//            return response.getContent().getResults().iterator().next();
//        }
//
//    }


    public RestResponse<Compound> getCompound(String compoundIdentifier) {

        logger.debug("Compound " + compoundIdentifier + " requested to the MetaboLights WS client");
        System.out.println("Compound " + compoundIdentifier + " requested to the MetaboLights WS client");

        String path = getCompoundPath(compoundIdentifier);

        // Make the request
        return getCompoundRestResponse(path);

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

    /**
     * Reset index
     */
    public RestResponse<String> resetIndex(){

        String path = getIndexingPath("reset");

        // Make the request
        String responseS = makeGetRequest(path);

        return deserializeJSONString(responseS, String.class);

    }

    /**
     * Indexing all
     */

    public RestResponse<String> reindex() {

        logger.debug("Indexing all studies and compounds requested to MetaboLights WS client.");


        // Make the request
        String response = makeGetRequest(getIndexingPath(REINDEX_PATH));

        return deserializeJSONString(response, String.class);
    }



    public RestResponse<ArrayListOfStrings> deleteIndex() {

        String response = makeDeleteRequest(getIndexingPath(""));

        return deserializeJSONString(response, ArrayListOfStrings.class);
    }

    public RestResponse<ArrayListOfStrings> deleteIndexedEntries(List<String> ids) {

        String response = makePostRequest(getIndexingPath("delete"), ids);

        return deserializeJSONString(response, ArrayListOfStrings.class);
    }

    public RestResponse<ArrayListOfStrings> deleteStudiesIndex() {

        String response = makeDeleteRequest(getIndexingPath(STUDIES));

        return deserializeJSONString(response, ArrayListOfStrings.class);
    }
    public RestResponse<ArrayListOfStrings> deleteCompoundsIndex() {

        String response = makeDeleteRequest(getIndexingPath(COMPOUNDS));

        return deserializeJSONString(response, ArrayListOfStrings.class);
    }

    public RestResponse<ArrayListOfStrings> indexStudies(List<String> studyIds) {

        logger.debug("Indexing all studies requested to MetaboLights WS client.");

        // Make the request
        String response = makePostRequest(getIndexingPath(STUDIES), studyIds);

        return deserializeJSONString(response, ArrayListOfStrings.class);
    }

    public RestResponse<ArrayListOfStrings> indexAllStudies(){

        return indexStudies(null);
    }

    public RestResponse<ArrayListOfStrings> indexCompounds(List<String> compoundIds) {

        logger.debug("Indexing all compounds requested to MetaboLights WS client.");

        // Make the request
        String response = makePostRequest(getIndexingPath(COMPOUNDS), compoundIds);

        return deserializeJSONString(response, ArrayListOfStrings.class);
    }

    public RestResponse<ArrayListOfStrings> indexAllCompounds(){

        return indexCompounds(null);
    }

    public RestResponse<ArrayListOfStrings> indexStudy(String studyIdentifier) {

        List<String> studyIdList = new ArrayList<>();
        studyIdList.add(studyIdentifier);
        return indexStudies(studyIdList);


    }

    public RestResponse<ArrayListOfStrings> indexCompound(String compound) {

        List<String> compoundList = new ArrayList<>();
        compoundList.add(compound);
        return indexCompounds(compoundList);
    }


    /**
     *
     *
     * QUEUE MANAGEMENT OPERATIONS
     *
     *
     */


    /**
     * Queue status
     */
    public RestResponse<Boolean> getQueueStatus(){

        String path = getQueuePath("status");

        // Make the request
        String responseS = makeGetRequest(path);

        return deserializeJSONString(responseS, Boolean.class);

    }

    /**
     * Toggle queue
     */
    public RestResponse<Boolean> toggleQueue(){

        String path = getQueuePath("toggle");

        // Make the request
        String responseS = makeGetRequest(path);

        return deserializeJSONString(responseS, Boolean.class);

    }

    /**
     * Create a private FTP folder for a Study, so the user can upload big files using ftp.
     *
     * @param studyId
     * @return
     * @author: jrmacias
     * @date: 20151105
     */
    public RestResponse<String> requestFtpFolder(String studyId){

        String jsonData = serializeObject("Request FTP Folder");

        String response = makePostRequest(STUDY_PATH + studyId +
                "/files/requestFtpFolder", jsonData);

        return deserializeJSONString(response, String.class);
    }

    /**
     * Move files from private FTP folder for a Study.
     *
     * @param studyId
     * @return
     * @author: jrmacias
     * @date: 20151105
     */
    public RestResponse<String> moveFilesfromFtpFolder(String studyId, List<String> selFiles){

        String jsonData = serializeObject(selFiles);

        String response = makePostRequest(STUDY_PATH + studyId +
                "/files/moveFilesfromFtpFolder", jsonData);

        return deserializeJSONString(response, String.class);
    }

    /**
     * Get a list of files from private FTP folder for a Study
     *
     * @param studyId
     * @return
     * @author: jrmacias
     * @date: 20151110
     */
    public RestResponse<File[]> getPrivateFtpFileList(String studyId) {

        String response = makeGetRequest(STUDY_PATH + studyId +
                "/files/privateFtpFolder/list");

        return deserializeJSONString(response, File[].class);
    }

    /**
     * Check if a Study has a private FTP folder
     *
     * @param studyId
     * @return
     * @author: jrmacias
     * @date: 20151112
     */
    public boolean hasPrivateFtpFolder(String studyId) {

        boolean response = deserializeJSONString(makeGetRequest(STUDY_PATH + studyId +
                "/files/privateFtpFolder"), Boolean.class).getContent();

        return response;
    }

    /**
     * Delete a list of files from the private FTP folder of the study
     *
     * @param studyId
     * @param selectedFiles
     * @return
     * @author: jrmacias
     * @date: 20151112
     */
    public RestResponse<String> deletePrivateFtpFiles(String studyId, List<String> selectedFiles) {

        logger.info("Deleting files from study {} private FTP, by user request.", studyId);

        String response = makePostRequest(STUDY_PATH + studyId +
                "/files/deleteFilesfromFtpFolder", selectedFiles);

        return deserializeJSONString(response, String.class);
    }
}
