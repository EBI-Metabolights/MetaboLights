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

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.Study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: conesa
 * Date: 30/08/2013
 * Time: 12:01
 */
public class MetabolightsWsClient {

	private static final String ANONYMOUS = "JAVA_WS_client_Anonymous";
	private static final String DEAFULT_TOKEN_HEADER = "USER_TOKEN";
	private String metabolightsWsUrl = "http://www.ebi.ac.uk/metabolights/webservice/";

	private String tokenHeaderName = DEAFULT_TOKEN_HEADER;
	private String userToken = ANONYMOUS;

    public MetabolightsWsClient(String metabolightsWsUrl){
        this.metabolightsWsUrl = metabolightsWsUrl;
    }

    public MetabolightsWsClient(){};

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

        try {

            URL url = new URL(metabolightsWsUrl + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty(tokenHeaderName, userToken);

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

    public Study getStudy(String studyIdentifier){

        String path = getStudyPath(studyIdentifier);

        // Make the request
        String response = makeGetRequest(path);

        return parseJason(response, Study.class);

    }

	private String getStudyPath(String studyIdentifier) {
		return "study/" + studyIdentifier;
	}

	public MetaboliteAssignment getMetabolites(String studyIdentifier, int assayNumber){

        String path = getStudyPath(studyIdentifier) + "/assay/" + assayNumber + "/maf";

        // Make the request
        String response = makeGetRequest(path);

        return parseJason(response, MetaboliteAssignment.class);

    }


    private <T> T parseJason(String response, Class<T> valueType ){

        // Parse response (json) into Study Model...
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false); //TODO, why the hell does it fail on the new Study.studyLocation!  I give up, Ken

        try {
            return mapper.readValue(response, valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
