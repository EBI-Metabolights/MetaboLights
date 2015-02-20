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
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.search.service.SearchQuery;
import uk.ac.ebi.metabolights.search.service.SearchResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: conesa
 * Date: 30/08/2013
 * Time: 12:01
 */
public class MetabolightsWsClient {

    private static final Logger logger = LoggerFactory.getLogger(MetabolightsWsClient.class);

    private static final String ANONYMOUS = "JAVA_WS_client_Anonymous";
    private static final String DEAFULT_TOKEN_HEADER = "USER_TOKEN";
    private String metabolightsWsUrl = "http://www.ebi.ac.uk/metabolights/webservice/";

    private String tokenHeaderName = DEAFULT_TOKEN_HEADER;
    private String userToken = ANONYMOUS;

    public MetabolightsWsClient(String metabolightsWsUrl) {
        this.metabolightsWsUrl = metabolightsWsUrl;
    }

    public MetabolightsWsClient() {
    }

    ;

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

    private String makeGetRequest(String path) {

        logger.debug("Making get " + path + " request to webservice");

        try {

            // Get a GET connection
            HttpURLConnection conn = getHttpURLConnection(path, "GET");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String message = org.apache.commons.io.IOUtils.toString(br);

            conn.disconnect();

            return message;

        } catch (MalformedURLException e) {

            e.printStackTrace();


        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;

    }

    private String makePostRequest(String path, String json) {

        logger.debug("Making get " + path + " request to webservice");

        try {

            // Get a post connection
            HttpURLConnection conn = getHttpURLConnection(path, "POST");

            conn.setRequestProperty("content-type", "application/json");
            conn.setDoOutput(true);

            // Send JSON content
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());

            out.write(json);
            out.close();
//            OutputStream os = conn.getOutputStream();
//            os.write(json.getBytes());
//            os.flush();


            // Read response
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Post request failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String message = org.apache.commons.io.IOUtils.toString(br);

            conn.disconnect();

            return message;

        } catch (MalformedURLException e) {

            e.printStackTrace();


        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;

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

        logger.info("Study " + studyIdentifier + " requested to the MetaboLights WS client");

        String path = getStudyPath(studyIdentifier);

        // Make the request
        String response = makeGetRequest(path);

        return deserializeJSONString(response, Study.class);

    }

    private String getStudyPath(String studyIdentifier) {
        return "study/" + studyIdentifier;
    }

    public RestResponse<MetaboliteAssignment> getMetabolites(String studyIdentifier, int assayNumber) {

        String path = getStudyPath(studyIdentifier) + "/assay/" + assayNumber + "/maf";

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

    public RestResponse<SearchResult> search() {

        logger.info("Empty search requested to the MetaboLights WS client");

        // Make the request
        String response = makeGetRequest("search");

        return deserializeJSONString(response, SearchResult.class);


    }

    public RestResponse<SearchResult> search(SearchQuery query) {

        logger.info("Search requested to the MetaboLights WS client");

        String json = serializeObject(query);

        // Make the request
        String response = makePostRequest("search", json);

        return deserializeJSONString(response, SearchResult.class);


    }

}
