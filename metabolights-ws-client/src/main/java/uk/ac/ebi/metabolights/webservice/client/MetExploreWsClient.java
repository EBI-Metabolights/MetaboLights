package uk.ac.ebi.metabolights.webservice.client;

/**
 * Created by venkata on 10/03/2016.
 */
public class MetExploreWsClient extends WsClient{

    private String metExploreWsUrl = "";

    private static final String GET = "GET";
    public MetExploreWsClient(){}

    public MetExploreWsClient(String metExploreWsUrl) {
        this.metExploreWsUrl = metExploreWsUrl;
    }

    public String getMetExploreWsUrl() {
        return metExploreWsUrl;
    }

    public String getPathwayMappings(String studyId){

        String metExploreResponse = excuteRequest(getMetExplorePathwayMappingsUrl(studyId), null, GET);

        return metExploreResponse;

    }

    private String getMetExplorePathwayMappingsUrl(String studyId){

        String metExplorePathwayMappingsUrl = "https://metexplore.toulouse.inra.fr/metExploreWebService/mapping/launchaspathways/inchi/1363?name=EBI_Phenomanal_Test&wsurl=http://wwwdev.ebi.ac.uk/metabolights/webservice/study/"+studyId+"/getMetabolitesInchi";

        return metExplorePathwayMappingsUrl;

    }

}
