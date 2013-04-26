package uk.ac.ebi.metabolights.referencelayer.importer;

import org.apache.log4j.Logger;
import sun.security.jgss.GSSUtil;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.rhea.ws.client.RheaResourceClient;
import uk.ac.ebi.rhea.ws.client.RheasResourceClient;
import uk.ac.ebi.rhea.ws.response.sbml1.Reaction;
import uk.ac.ebi.rhea.ws.response.search.RheaReaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.List;

/**
 * User: conesa
 * Date: 23/04/2013
 * Time: 17:21
 */
public class RheaMatcher {

    private Logger LOGGER = Logger.getLogger(RheaMatcher.class);

    private RheasResourceClient wsRheaClient;


    private void initializeRheaClient(){
        if (wsRheaClient == null){
            wsRheaClient = new RheasResourceClient();
        }

    }
    public void matchMetabolitesWithRhea(File chebiTSV){

        LOGGER.info("Importing metabolites from chebi. TSV: " + chebiTSV.getAbsoluteFile());

        // Try to get the list of metabolites from chebi TSV...
        // SAMPLE:
        // ChEBI ID	ChEBI ASCII Name	Text Score	Tanimoto Similarity Score	Entry Status
        // CHEBI:36062	3,4-dihydroxybenzoic acid	7.22
        // CHEBI:1189	2-methoxyestrone	7.11
        // CHEBI:1387	3,4-dihydroxyphenylethyleneglycol	7.11
        // CHEBI:3648	chlorpromazine <i>N</i>-oxide	7.11

        Long linesRead =new Long(0);
        Long imported = new Long(0);


        // Open and go through the file
        try {
            //Use a buffered reader
            BufferedReader reader = new BufferedReader(new FileReader(chebiTSV));
            String line = "", text = "";

            // Initialize the rhea webservice
            initializeRheaClient();

            //Go through the file
            while((line = reader.readLine()) != null)
            {

                // If its not the first line...skip it as it is the header definition line....
                if (linesRead !=0){
                    // Get the Chebi id ==> First element in a line separated by Tabulators
                    String[] values = line.split("\t");

                    checkExistanceInRhea(values[0], values[1]);

                }

                linesRead++;

            }

            //Close the reader
            reader.close();


        } catch (Exception e) {
            LOGGER.error("Can't match metabolites with Rhea.",e);
            return;
        }

    }

    private void checkExistanceInRhea(String chebiId, String chebiName){


        List<RheaReaction> reactions = wsRheaClient.search(chebiId);

        // If no reactions found
        if ((reactions == null) || reactions.size() == 0) {
            LOGGER.info("\t" + chebiId  + " - " + chebiName + "\t0\t reactions found");
        } else {
            LOGGER.info("\t" + chebiId  + " - " + chebiName + "\t" +reactions.size() + "\t reactions found");
        }




    }

}
