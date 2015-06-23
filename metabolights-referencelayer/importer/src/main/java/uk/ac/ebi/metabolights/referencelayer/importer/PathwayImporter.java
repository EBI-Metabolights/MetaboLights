/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 12/3/13 4:02 PM
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

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.AttributeDefinitionDAO;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.DatabaseDAO;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.MetaboLightsCompoundDAO;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.SpeciesDAO;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;

public class PathwayImporter {

    private Logger LOGGER = LoggerFactory.getLogger(PathwayImporter.class);

    private static final String DEFAULT_PATHWAYS_TXT_NAME = "pathway.txt";
    private File pathwaysTxt = new File(DEFAULT_PATHWAYS_TXT_NAME);
    private File importFolder = new File(".");
    private File compoundsFolder;


    // DAO objects
    private MetaboLightsCompoundDAO mcd;
    private DatabaseDAO dbd;
    private Database pamela;
    private SpeciesDAO spd;
    private AttributeDefinitionDAO add;

    private OntologyLookUpService ols = new OntologyLookUpService();
    private AttributeDefinition pamelaPathwayIdAttributeDefinition;
    private AttributeDefinition keggPathwayURLAttributeDefinition;
    private AttributeDefinition bioCYCURLAttributeDefinition;

    // Instantiate with a connection object
    public PathwayImporter(Connection connection) throws IOException {

        initConnections(connection);

    }

    // Instantiate with a connection object
    public PathwayImporter(Connection connection, File importFolder) throws IOException {

        initConnections(connection);

        setupFilesAndFolders(importFolder);

    }

    public PathwayImporter(Connection connection, File importFolder, File compoundsFolder) throws IOException {

        initConnections(connection);

        setupFilesAndFolders(importFolder,compoundsFolder);

    }
    private void initConnections(Connection connection) throws IOException {

        this.mcd = new MetaboLightsCompoundDAO(connection);
        this.dbd = new DatabaseDAO(connection);
        this.spd = new SpeciesDAO(connection);
        this.add = new AttributeDefinitionDAO(connection);

    }


    private void setupFilesAndFolders(File importFolder, File compoundsFolder, File pathwaysTxt){

        this.importFolder = importFolder;
        this.compoundsFolder = importFolder;
        this.pathwaysTxt = pathwaysTxt;

    }

    private void setupFilesAndFolders(File importFolder){

       setupFilesAndFolders(importFolder,importFolder);

    }

    private void setupFilesAndFolders(File importFolder, File compoundsFolder){

        setupFilesAndFolders(importFolder,compoundsFolder, new File (importFolder.getAbsolutePath() + "/" + DEFAULT_PATHWAYS_TXT_NAME));

    }


    public void importPathwaysFromFolder(File folder){


        setupFilesAndFolders(folder);

        importPathways();

    }

    private void importPathways() {


        LOGGER.info("Importing pathways (*.png) from folder: " + importFolder.getAbsolutePath());
        LOGGER.info("Reading pathways info from " + pathwaysTxt.getAbsolutePath());
        LOGGER.info("Moving pathway files to " + compoundsFolder.getAbsolutePath());


        // Instantiate pamela database
        try {
            pamela = dbd.findByDatabaseName("PAMELA");

        } catch (DAOException e) {

            LOGGER.error("PAMELA \"database\" record not found, cannot proceed");
            return;
        }


        // Read the pathways info file...

        // Open and go through the file
        try {
            //Use a buffered reader
            BufferedReader reader = new BufferedReader(new FileReader(pathwaysTxt));
            String line = "", text = "";

            //Go through the file
            while((line = reader.readLine()) != null)
            {

                // Get the Chebi id ==> First element in a line separated by Tabulators
                String[] values = line.split("\t");

                String chebiId = values[0];
                String taxon = values[1];
                String index = values[2];
                String pathwayName = values[3];
                String pathwayACC = values[4];
                String pamelaPathwayACC = values[5];

                importPathway(chebiId,taxon,index,pathwayName,pathwayACC,pamelaPathwayACC);


            }

            //Close the reader
            reader.close();

        } catch (Exception e) {
            LOGGER.error("Can't import pathways.",e);
            return;
        }

    }

    private void importPathway(String chebiId, String taxon, String index, String pathwayName, String pathwayACC, String pamelaPathwayACC){


        // Compose the pathway file name:
        String fileName = chebiId.replace(":", "_") + "_" + taxon + "_" + index + ".png";

        File pathwayFile = new File (importFolder.getAbsolutePath() + "/" + fileName);

        if (!pathwayFile.exists()) {
            LOGGER.error("Can't find pathway file " + pathwayFile.getAbsolutePath());
            return;
        }

        // Compose the metabolite accession:
        String acc = chebiId.replace("CHEBI:", "MTBLC");


        // We need to copy the file to the compound folder + ACC: compoundFolder/MTBLC1234
        File copyTo = new File(compoundsFolder.getAbsolutePath() + "/" + acc);
        if (!copyTo.exists()) {
            copyTo.mkdirs();
        }

        File finalPathwayFile = new File(copyTo.getAbsolutePath() + "/" + fileName);

        // copy the file to the compound folder
        try {

            FileUtils.copyFile(pathwayFile,finalPathwayFile );

            LOGGER.info("Pathway file moved to " + finalPathwayFile.getAbsolutePath());

        } catch (IOException e) {
            LOGGER.error("Can't copy pathway file " + pathwayFile.getAbsolutePath() + " to " + finalPathwayFile.getAbsolutePath(), e);
            return;
        }


        // Get the species
        Species sp = getSpecies(taxon);


        try {

            // Get the compound...
            MetaboLightsCompound mc = mcd.findByCompoundAccession(acc);


            if (mc == null){
                LOGGER.warn("MetaboLights doesn't have a compound for " + acc);
            } else{

                // Create the pathway
                Pathway pathway = new Pathway(pathwayName,pamela,finalPathwayFile, sp);

                // Add attributes...

                // PAMELA ID
                Attribute att = new Attribute();
                att.setValue(pamelaPathwayACC);
                att.setAttributeDefinition(getPamelaPathwayIdAttributeDefinition());
                pathway.getAttributes().add(att);


                // pathway ID
                att = new Attribute();

                if (pathwayACC.startsWith("rn")){

                    att.setValue("http://www.genome.jp/kegg-bin/show_pathway?" + pathwayACC);
                    att.setAttributeDefinition(getKeggPathwayURLAttributeDefinition());
                } else {

                    att.setValue("http://biocyc.org/META/NEW-IMAGE?type=PATHWAY&object=" + pathwayACC);
                    att.setAttributeDefinition(getBioCYCURLAttributeDefinition());

                }
                pathway.getAttributes().add(att);


                // If the pathway is not there...
                if (!mc.getMetPathways().contains(pathway)){
                    mc.getMetPathways().add(pathway);

                }

                mc.setHasPathways(true);

                // Save the compound...
                mcd.save(mc);

            }


        } catch (DAOException e) {
            LOGGER.error(e.getMessage());
        }


    }

    private AttributeDefinition getPamelaPathwayIdAttributeDefinition(){

        if (pamelaPathwayIdAttributeDefinition == null) {
            pamelaPathwayIdAttributeDefinition = getAttributeDefinition("PAMELA Pathway ID", "PAMELA pathway identifier");

        }

        return pamelaPathwayIdAttributeDefinition;
    }

    private AttributeDefinition getKeggPathwayURLAttributeDefinition(){

        if (keggPathwayURLAttributeDefinition == null) {
            keggPathwayURLAttributeDefinition = getAttributeDefinition("KEGG Pathway URL", "URL that takes you to a KEGG pathway page");

        }

        return keggPathwayURLAttributeDefinition;

    }

    private AttributeDefinition getBioCYCURLAttributeDefinition(){

        if (bioCYCURLAttributeDefinition == null) {
            bioCYCURLAttributeDefinition = getAttributeDefinition("BIOCYC Pathway URL", "URL that takes you to a BIOCYC pathway page");

        }

        return bioCYCURLAttributeDefinition;
    }

    private AttributeDefinition getAttributeDefinition(String attributeDefinitionName, String attributeDefinitionDescription){

        AttributeDefinition ad;

        // Search for the attribute Definition by name
        try {
            ad = add.findByAttributeDefinitionName(attributeDefinitionName);
        } catch (DAOException e) {
            LOGGER.error("Can't find attribute definition" , e);
            return null;
        }

        // If attribute definition is not found
        if (ad == null){
            ad = new AttributeDefinition();
            ad.setName(attributeDefinitionName);
            ad.setDescription(attributeDefinitionDescription);
        }

        return ad;

    }

    private Species getSpecies(String taxon) {

        // Get the species
        taxon = SpeciesUpdater.NEWT_ONTOLOGY + ":" + taxon;

        Species sp = null;

        try {
            sp = spd.findBySpeciesTaxon(taxon);
        } catch (DAOException e) {
            LOGGER.error("Can't get species by taxon:" + e.getMessage());
        }

        // If species is null...
        if (sp==null){
            LOGGER.warn("No species for taxon:" + taxon + ", creating one.");

            String speciesName;
            try {
                speciesName = ols.getTermName(taxon, SpeciesUpdater.NEWT_ONTOLOGY);
            } catch (Exception e) {
                LOGGER.error ("Can't get Term name from ontology service for " + taxon);

                speciesName = taxon;


            }

            sp = new Species();
            sp.setSpecies(speciesName);
            sp.setTaxon(taxon);
            sp.setDescription("Created by Importer, needs to be curated");
        }
        return sp;

    }
//    private void importPathway(File pathwayFile){
//
//        String fileName = pathwayFile.getName();
//
//        String[] values = fileName.split("_");
//
//        String acc = "MTBLC" + values[1];
//        String taxon = SpeciesUpdater.NEWT_ONTOLOGY + ":" + values[2];
//
//        Species sp = null;
//
//
//        try {
//            sp = spd.findBySpeciesTaxon(taxon);
//        } catch (DAOException e) {
//            LOGGER.error("Can't get species by taxon:" + e.getMessage());
//        }
//
//        // If species is null...
//        if (sp==null){
//            LOGGER.warn("No species for taxon:" + taxon + ", creating one.");
//
//            String speciesName;
//            try {
//                speciesName = ols.getTermName(taxon, SpeciesUpdater.NEWT_ONTOLOGY);
//            } catch (Exception e) {
//                LOGGER.error ("Can't get Term name from ontology service for " + taxon);
//
//                speciesName = taxon;
//
//
//            }
//
//            sp = new Species();
//            sp.setSpecies(speciesName);
//            sp.setTaxon(taxon);
//            sp.setDescription("Created by Importer, needs to be curated");
//        }
//
//
//        try {
//
//            // Get the compound...
//            MetaboLightsCompound mc = mcd.findByCompoundAccession(acc);
//
//
//            if (mc == null){
//                LOGGER.warn("MetaboLights doesn't have a compound for " + acc);
//            } else{
//
//                // Create the pathway
//                Pathway pathway = new Pathway(fileName,pamela,pathwayFile, sp);
//
//                // If the pathway is not there...
//                if (!mc.getMetPathways().contains(pathway)){
//                    mc.getMetPathways().add(pathway);
//
//                }
//
//                mc.setHasPathways(true);
//
//                // Save the compound...
//                mcd.save(mc);
//
//            }
//
//
//        } catch (DAOException e) {
//            LOGGER.error(e.getMessage());
//        }
//
//
//    }

}

