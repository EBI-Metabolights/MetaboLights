/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 27/09/13 14:46
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.species.globalnames.client;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
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

	private static Logger logger = Logger.getLogger(GlobalNamesWSClient.class);
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
