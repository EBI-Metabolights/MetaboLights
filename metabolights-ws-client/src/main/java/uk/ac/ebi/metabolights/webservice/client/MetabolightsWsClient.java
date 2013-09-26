/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 26/09/13 15:17
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.webservice.client;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.Study;

import java.io.BufferedReader;
import java.io.File;
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

    private String metabolightsWsUrl = "http://www.ebi.ac.uk/metabolights/webservice/";

    public MetabolightsWsClient(String metabolightsWsUrl){
        this.metabolightsWsUrl = metabolightsWsUrl;
    }

    public MetabolightsWsClient(){};

    private String makeGetRequest(String path) {

        try {

            URL url = new URL(metabolightsWsUrl + path);
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

    public Study getStudy(String studyIdentifier){

        String path = "study/" + studyIdentifier;

        // Make the request
        String response = makeGetRequest(path);

        return parseJason(response, Study.class);

    }

    public MetaboliteAssignment getMetabolites(String mafPath){

        String path = "maf/" + mafPath.replaceAll(File.separator,"__");

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
