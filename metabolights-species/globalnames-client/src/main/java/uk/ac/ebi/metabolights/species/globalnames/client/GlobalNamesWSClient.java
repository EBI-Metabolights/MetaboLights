/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/29/13 1:34 PM
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

package uk.ac.ebi.metabolights.species.globalnames.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.species.globalnames.model.GlobalNamesResponse;

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
public class GlobalNamesWSClient {

	private static Logger logger = LoggerFactory.getLogger(GlobalNamesWSClient.class);
    private String globalNamesWsUrl = "http://resolver.globalnames.org/name_resolvers.json";

    public GlobalNamesWSClient(String globalNamesWsUrl){
        this.globalNamesWsUrl = globalNamesWsUrl;
    }

    public GlobalNamesWSClient(){};

    private String makeGetRequest(NameResolversParams params) {

        try {

            URL url = new URL(globalNamesWsUrl + "?" + params);

			logger.info("Making get request to " + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

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

    public GlobalNamesResponse resolveName(String name){

		NameResolversParams params = new NameResolversParams(name);

		return  resolveName(params);
    }

	public GlobalNamesResponse resolveName(String name, GlobalNamesSources source){

		NameResolversParams params = new NameResolversParams(name, source);

		return resolveName(params);
	}

	public GlobalNamesResponse resolveName(NameResolversParams params){

		// Make the request
		String response = makeGetRequest(params);

		return parseJason(response, GlobalNamesResponse.class);

	}



	private <T> T parseJason(String response, Class<T> valueType ){

        // Parse response (json) into Study Model...
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(response, valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
