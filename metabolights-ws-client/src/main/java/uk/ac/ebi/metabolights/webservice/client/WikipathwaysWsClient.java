package uk.ac.ebi.metabolights.webservice.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by venkata on 09/11/2015.
 */
public class WikipathwaysWsClient {

    private String wikipathwaysWsUrl = "http://webservice.wikipathways.org";
    private String pathwaysByXrefUrl = "/findPathwaysByXref";

    private static final String GET = "GET";
    private static final String POST = "POST";


    private static final Logger logger = LoggerFactory.getLogger(WikipathwaysWsClient.class);

    public WikipathwaysWsClient(){
    }

    public WikipathwaysWsClient(String wikipathwaysWsClientUrl) {
        this.wikipathwaysWsUrl = wikipathwaysWsClientUrl;
    }

    public String getWikipathwaysWsClientUrl() {
        return wikipathwaysWsUrl;
    }

    public JsonNode findPathwaysByXref(String id, String code){
        String wikiPathwaysResponse = excuteRequest(getPathwaysUrlByXref(id,code), null, this.GET);
        //logger.info(wikiPathwaysResponse);

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(wikiPathwaysResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonNode result = rootNode.path("result");

        return result;
    }


    private String getPathwaysUrlByXref(String id, String code){
        return this.wikipathwaysWsUrl + this.pathwaysByXrefUrl + "?ids=" +  id + "&codes=" + code + "&format=json";
    }

    public Boolean hasWikiPathways(String id, String code){
        Boolean pathwaysExist = false;

        if(findPathwaysByXref(id,code).size() > 0){
            pathwaysExist = true;
        }
        return pathwaysExist;
    }

    public static String excuteRequest(String targetURL, String urlParameters, String method) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(method);

            if (urlParameters != null) {
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                connection.setRequestProperty("Content-Length",
                        Integer.toString(urlParameters.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");


            }

            connection.setDoOutput(true);


            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("MetaboLights Java WS client: " + connection.getURL().toString() + "(" + method + ") request failed : HTTP error code : "
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
            if(connection != null) {
                connection.disconnect();
            }
        }
    }

}
