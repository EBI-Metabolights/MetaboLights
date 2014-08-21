/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 10/24/13 9:16 AM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.referencelayer.importer;

import org.apache.log4j.Logger;
import sun.security.jgss.GSSUtil;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.ChebiWebServiceFault_Exception;
import uk.ac.ebi.chebi.webapps.chebiWS.model.LiteEntity;
import uk.ac.ebi.chebi.webapps.chebiWS.model.LiteEntityList;
import uk.ac.ebi.chebi.webapps.chebiWS.model.RelationshipType;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.rhea.ws.client.RheaResourceClient;
import uk.ac.ebi.rhea.ws.client.RheasResourceClient;
import uk.ac.ebi.rhea.ws.response.sbml1.Reaction;
import uk.ac.ebi.rhea.ws.response.search.RheaReaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
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
    private ChebiWebServiceClient chebiWS = new ChebiWebServiceClient();


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

                    String chebiId = values[0];

                    // Try first the initial chebiId
                    Collection<String> chebiIdRelatives = new ArrayList<String>();
                    chebiIdRelatives.add(chebiId);

                    int reactionsFound;

                    // Try to get the reactions for the initial chebiID
                    reactionsFound = checkExistanceInRhea(chebiId, values[1], chebiIdRelatives);

                    // if nothing found....
                    if (reactionsFound == 0) {

                        // ... try tautomers
                        chebiIdRelatives = getChebiIdsRelatives(chebiId, RelationshipType.IS_TAUTOMER_OF);

                        // Try to get the reactions for the tautomers
                        reactionsFound = checkExistanceInRhea(chebiId, values[1], chebiIdRelatives);

                        // if nothing found....
                        if (reactionsFound == 0) {

                            // ... try children
                            chebiIdRelatives = getChebiIdsRelatives(chebiId, RelationshipType.IS_A);

                            // Try to get the reactions for the children
                            reactionsFound = checkExistanceInRhea(chebiId, values[1], chebiIdRelatives);


                            // if nothing found....
                            if (reactionsFound == 0) {

                                // ... try tautomers of the children
                                chebiIdRelatives = getChebiIdsRelatives(chebiIdRelatives, RelationshipType.IS_TAUTOMER_OF);

                                // Try to get the reactions for the tautomers
                                reactionsFound = checkExistanceInRhea(chebiId, values[1], chebiIdRelatives);

                                LOGGER.info("\t" + chebiId  + "\t" + values[1] + "\t" + reactionsFound + "\t reactions found\t" + chebiIdRelatives.size() + "\tchildren's tautomers: " + chebiIdRelatives.toString() );

                            } else {
                                LOGGER.info("\t" + chebiId  + "\t" + values[1] + "\t" + reactionsFound + "\t reactions found\t" + chebiIdRelatives.size() + "\tchildren: " + chebiIdRelatives.toString() );
                            }

                        } else {
                            LOGGER.info("\t" + chebiId  + "\t" + values[1] + "\t" + reactionsFound + "\t reactions found\t" + chebiIdRelatives.size() + "\ttautomers: " + chebiIdRelatives.toString() );
                        }

                    } else {
                        LOGGER.info("\t" + chebiId  + "\t" + values[1] + "\t" + reactionsFound + "\t reactions found\tdirect" );
                    }

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

    private Collection<String> getChebiIdsRelatives(String chebiId, RelationshipType relType) throws ChebiWebServiceFault_Exception {


        ArrayList<String> relatives = new ArrayList<String>();

        // Get all the children of that chebi id
        LiteEntityList children = chebiWS.getAllOntologyChildrenInPath(chebiId, relType, true);

        for (LiteEntity le: children.getListElement()){
            relatives.add(le.getChebiId());
        }

        return relatives;

    }

    private Collection<String> getChebiIdsRelatives(Collection<String> chebiIdList, RelationshipType relType) throws ChebiWebServiceFault_Exception {


        ArrayList<String> joinedRelatives = new ArrayList<String>();

        // For each chebiID in the lis
        for (String chebiId: chebiIdList){

            Collection<String> relatives = getChebiIdsRelatives(chebiId,relType);

            joinedRelatives.addAll(relatives);
        }

        return joinedRelatives;

    }

    private int checkExistanceInRhea(String chebiId, String chebiName, Collection<String> chebiIdRelatives){

        int total = 0;

        for (String relative: chebiIdRelatives){
            List<RheaReaction> reactions = wsRheaClient.search(relative);

            if (reactions != null) {
                total = total + reactions.size();
            }
        }

//        LOGGER.info("\t" + chebiId  + "\t" + chebiName + "\t" + total + "\t reactions found\t" + chebiIdRelatives.size() + "\trelatives: " + chebiIdRelatives.toString() );

        return total;


    }

}
