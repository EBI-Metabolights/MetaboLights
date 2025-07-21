package uk.ac.ebi.metabolights.service;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.ebi.metabolights.model.LiveTraining;
import uk.ac.ebi.metabolights.model.OdTraining;
import uk.ac.ebi.metabolights.webservice.client.WsClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TrainingService{

    private static Logger logger = LoggerFactory.getLogger(TrainingService.class);

    public List<OdTraining> getOnDemandTraning() {
        List<OdTraining> odList = new ArrayList<>();
        try{
            String url = "https://www.ebi.ac.uk/ebisearch/ws/rest/ebiweb_training_online?format=json&query=domain_source:ebiweb_training_online&start=0&size=2&fields=title,subtitle,description,type,url&sort=title&facets=resource_training_page:MetaboLights";
            String odResponse = WsClient.excuteRequest(url, null, "GET");
            //logger.info("odResponse: "+ odResponse);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(odResponse);
            Integer hitcount = rootNode.get("hitCount").asInt();
            logger.info("Od Traning hitcount: "+ hitcount);
            if (hitcount > 0){
                ArrayNode arrayNode = (ArrayNode)rootNode.get("entries");
                for(int i = 0; i < arrayNode.size(); i++) {
                    OdTraining odTraining = new OdTraining();
                    JsonNode arrayElement = arrayNode.get(i);
                    ArrayNode titleNode =(ArrayNode) arrayElement.at("/fields/title");
                    String title = titleNode.get(0).asText();
                    odTraining.setTitle(title);
                    ArrayNode subtitleNode = (ArrayNode) arrayElement.at("/fields/subtitle");
                    String subtitle= subtitleNode.get(0).asText();
                    if(!subtitle.equals("null")){
                        odTraining.setSubTitle(subtitle);
                    }else{
                         odTraining.setSubTitle("");
                    }
                    ArrayNode descriptionNode = (ArrayNode) arrayElement.at("/fields/description");
                    String description = descriptionNode.get(0).asText();
                    if (description.length() > 350){
                        description = description.substring(0, 350) + "...";
                    }
                    odTraining.setDescription(description);
                    ArrayNode typeNode = (ArrayNode) arrayElement.at("/fields/type");
                    String type = typeNode.get(0).asText();
                    odTraining.setType(type);
                    ArrayNode linkNode = (ArrayNode) arrayElement.at("/fields/url");
                    String link = linkNode.get(0).asText();
                    odTraining.setLink(link);
                    odList.add(odTraining);
                }
            }

        }catch(Exception e){
            logger.error("Exception while fetching Ondemand traning from EBI Search : "+e.getMessage());
        }
        return odList;
    }

     public List<LiveTraining> getLiveTraning() {
        List<LiveTraining> liveList = new ArrayList<>();
        try{
            String url = "https://www.ebi.ac.uk/ebisearch/ws/rest/ebiweb_training_events?format=json&query=domain_source:ebiweb_training_events%20AND%20timeframe:upcoming&start=0&size=2&fieldurl=true&fields=title,description,start_date,end_date,date_time_clean,resource_training_page,type,training_type,url,venue,materials,status,timeframe,resource_training_page,course_image&facetcount=50&sort=start_date&facets=resource_training_page:MetaboLights";
            String liveResponse = WsClient.excuteRequest(url, null, "GET");
            //logger.info("liveResponse: "+ liveResponse);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(liveResponse);
            Integer hitcount = rootNode.get("hitCount").asInt();
            logger.info("Live Training hitcount: "+ hitcount);
            if (hitcount > 0){
                ArrayNode arrayNode = (ArrayNode)rootNode.get("entries");
                for(int i = 0; i < arrayNode.size(); i++) {
                    LiveTraining liveTraining = new LiveTraining();
                    JsonNode arrayElement = arrayNode.get(i);
                    ArrayNode titleNode =(ArrayNode) arrayElement.at("/fields/title");
                    String title = titleNode.get(0).asText();
                    liveTraining.setTitle(title);
                    ArrayNode descriptionNode = (ArrayNode) arrayElement.at("/fields/description");
                    String description = descriptionNode.get(0).asText();
                    if (description.length() > 350){
                        description = description.substring(0, 350) + "...";
                    }
                    liveTraining.setDescription(description);
                    ArrayNode dateNode = (ArrayNode) arrayElement.at("/fields/date_time_clean");
                    String date= dateNode.get(0).asText();
                    liveTraining.setDate(date);
                    ArrayNode typeNode = (ArrayNode) arrayElement.at("/fields/type");
                    String type = typeNode.get(0).asText();
                    liveTraining.setType(type);
                    ArrayNode linkNode = (ArrayNode) arrayElement.at("/fields/url");
                    String link = linkNode.get(0).asText();
                    liveTraining.setLink(link);
                    ArrayNode venueNode = (ArrayNode) arrayElement.at("/fields/venue");
                    String venue = venueNode.get(0).asText();
                    if(!venue.equals("null")){
                        liveTraining.setVenue(venue);
                    }else{
                         liveTraining.setVenue("Online");
                    }
                    ArrayNode statusNode = (ArrayNode) arrayElement.at("/fields/status");
                    String status = statusNode.get(0).asText();
                    liveTraining.setStatus(status);
                    liveList.add(liveTraining);
                }
            }

        }catch(Exception e){
            logger.error("Exception while fetching Live training from EBI Search : "+e.getMessage());
        }
        return liveList;
    }


    public static void main(String args[]) {
        try{
            TrainingService trainingService = new TrainingService();
            trainingService.getOnDemandTraning();
        }catch(Exception e){
             System.out.println("exception "+e);
        }
        
    }
}