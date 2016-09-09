package uk.ac.ebi.metabolights.webservice.searchplugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;

import javax.xml.namespace.QName;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

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

    public static String executeRequest(String requestURL, String method, String postBody) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            //String encodedUrl = URLEncoder.encode(requestURL, "UTF-8");

            URL url = new URL(requestURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);      // "GET"
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setRequestProperty( "charset", "utf-8");
            connection.setDoOutput(true);

            if (method.equalsIgnoreCase("post")) {
                if (!postBody.isEmpty()) {
                    OutputStream os = connection.getOutputStream();
                    os.write(postBody.getBytes());
                    os.flush();
                }
            }
            logger.info(requestURL + " " + method + " " + postBody);

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
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


}
