package uk.ac.ebi.metabolights.webservice.searchplugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kalai on 03/08/2016.
 */
public class GenericCompoundWSClients {
    private static Logger logger = LoggerFactory.getLogger(GenericCompoundWSClients.class);
    private static final String chebiWSUrl = "http://www.ebi.ac.uk/webservices/chebi/2.0/webservice?wsdl";
    private static final String pubchemWSUrl = "https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/";
    private static final String chemSpiderWSUrl = "TODO";

    private static ChebiWebServiceClient chebiWS;
    // todo pubchem client
    // todo chemspider client


    public static ChebiWebServiceClient getChebiWS() {
        if (chebiWS == null)
            try {
                logger.info("Starting a new instance of the ChEBI ChebiWebServiceClient");
                chebiWS = new ChebiWebServiceClient(new URL(chebiWSUrl), new QName("http://www.ebi.ac.uk/webservices/chebi", "ChebiWebServiceService"));
            } catch (MalformedURLException e) {
                logger.error("Error instanciating a new ChebiWebServiceClient " + e.getMessage());
            }
        return chebiWS;
    }

    public static String getPubChemWSURL(){
         return pubchemWSUrl;
    }


}
