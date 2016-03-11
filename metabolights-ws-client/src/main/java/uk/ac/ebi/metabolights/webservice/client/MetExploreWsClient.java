package uk.ac.ebi.metabolights.webservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by venkata on 10/03/2016.
 */
public class MetExploreWsClient extends WsClient{

    private String metExploreWsUrl = "";

    private static final String GET = "GET";
    private static final String POST = "POST";


    private static final Logger logger = LoggerFactory.getLogger(WikipathwaysWsClient.class);

    public MetExploreWsClient(){}

    public MetExploreWsClient(String metExploreWsUrl) {
        this.metExploreWsUrl = metExploreWsUrl;
    }

    public String getMetExploreWsUrl() {
        return metExploreWsUrl;
    }

    public String getPathwayMappings(String studyId){

        String metExploreResponse = excuteRequest(getMetExplorePathwayMappingsUrl(studyId), null, this.GET);

        return metExploreResponse;

    }

    private String getMetExplorePathwayMappingsUrl(String studyId){

        String metExplorePathwayMappingsUrl = "http://metexplore.toulouse.inra.fr:8080/metExploreWebService/mapping/launchaspathways/inchi/1363?name=EBI_Phenomanal_Test&wsurl=http://wwwdev.ebi.ac.uk/metabolights/webservice/study/"+studyId+"/getMetabolitesInchi";

        return metExplorePathwayMappingsUrl;

    }

}
