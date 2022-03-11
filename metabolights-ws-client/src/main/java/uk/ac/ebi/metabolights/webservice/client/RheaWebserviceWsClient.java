package uk.ac.ebi.metabolights.webservice.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by venkata on 09/11/2015.
 */
public class RheaWebserviceWsClient extends WsClient{

    private String rheaWsUrl = "https://www.rhea-db.org/rhea/";
    private String query = "?query=";
    private String columns = "&columns=rhea-id,equation,chebi-id";
    private String format = "&format=json";
    private String limit = "&limit=10";

    private static final String GET = "GET";
    private static final String POST = "POST";


    private static final Logger logger = LoggerFactory.getLogger(RheaWebserviceWsClient.class);

    public RheaWebserviceWsClient(){
    }

    public RheaWebserviceWsClient(String rheaWsUrl) {
        this.rheaWsUrl = rheaWsUrl;
    }

    public String getRheaWsUrl() {
        return rheaWsUrl;
    }

    public JsonNode getRheaReactions(String id){
        String rheaWsURL = getRheaAPIUrl(id);
        logger.info("RheaURL "+rheaWsURL);
        String rheaResponse = excuteRequest(rheaWsURL, null, this.GET);
        //logger.debug("Response "+rheaResponse);
        if(rheaResponse == null){
            rheaResponse = "{\"result\":[]}";
        }
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(rheaResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonNode results = rootNode.path("results");
        return results;
    }


    private String getRheaAPIUrl(String chebiID) {
        return new StringBuilder(this.rheaWsUrl).append(query).append(chebiID)
                .append(columns).append(format).append(limit).toString();
    }

    public Boolean hasReaction(String chebiID){
        Boolean reactionExist = false;
        if(getRheaReactions(chebiID).size() > 0){
            reactionExist = true;
        }
        return reactionExist;
    }

    public static void main(String args[]){
        //String URL = "https://www.rhea-db.org/rhea/?query=chebi:4139&columns=rhea-id,equation,chebi-id&format=json&limit=10";
        String ID = "chebi:41390";
        RheaWebserviceWsClient ws = new RheaWebserviceWsClient();
        Boolean hasRhea = ws.hasReaction(ID);
        System.out.println("has "+hasRhea);

    }

}
