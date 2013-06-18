package uk.ac.ebi.metabolights.referencelayer.importer;

import org.apache.log4j.Logger;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.*;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.DatabaseDAO;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.MetaboLightsCompoundDAO;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.SpeciesDAO;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.domain.*;
import uk.ac.ebi.ws.ols.QueryService;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PathwayImporter {

    private Logger LOGGER = Logger.getLogger(PathwayImporter.class);

    private MetaboLightsCompoundDAO mcd;
    private DatabaseDAO dbd;
    private Database pamela;
    private SpeciesDAO spd;

    private OntologyLookUpService ols = new OntologyLookUpService();

    // Instantiate with a connection object
    public PathwayImporter(Connection connection) throws IOException {
        this.mcd = new MetaboLightsCompoundDAO(connection);
        this.dbd = new DatabaseDAO(connection);
        this.spd = new SpeciesDAO(connection);

    }


    public void importPathwaysFromFolder(File folder){

        LOGGER.info("Importing pathways (*.png) from folder: " + folder.getAbsolutePath());

        FilenameFilter filter = new FilenameFilter(){

            @Override
            public boolean accept(File file, String s) {
                return s.toLowerCase().endsWith(".png");
            }
        };

        // Instantiate pamela database
        try {
            pamela = dbd.findByDatabaseName("PAMELA");

        } catch (DAOException e) {

            LOGGER.error("PAMELA \"database\" record not found, cannot proceed");
            return;
        }


        // Get the files in the folder
        for (File pathwayFile: folder.listFiles(filter)){

            // Log file
            LOGGER.info("Importing " + pathwayFile.getName());
            importPathway(pathwayFile);

        }

    }

    private void importPathway(File pathwayFile){

        String fileName = pathwayFile.getName();

        // Clean first part of the name: Only will work for pamela
        fileName = fileName.replace("TestPamelaLayout", "");


        String[] values = fileName.split("_");

        String acc = chebiID2MetaboLightsID(values[0]);
        String taxon = SpeciesUpdater.ONTOLOGY + ":" + values[1];

        Species sp = null;


        try {
            sp = spd.findBySpeciesTaxon(taxon);
        } catch (DAOException e) {
            LOGGER.error("Can't get species by taxon:" + e.getMessage());
        }

        // If species is null...
        if (sp==null){
            LOGGER.warn("No species for taxon:" + taxon + ", creating an improper one.");

            sp = new Species();
            sp.setSpecies(taxon);
            sp.setTaxon(taxon);
            sp.setDescription("Created by Importer, needs to be curated");
        }


        try {

            // Get the compound...
            MetaboLightsCompound mc = mcd.findByCompoundAccession(acc);


            if (mc == null){
                LOGGER.warn("MetaboLights doesn't have a compound for " + acc);
            } else{

                // Create the pathway
                Pathway pathway = new Pathway(fileName,pamela,pathwayFile, sp);

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


    private String chebiID2MetaboLightsID(String chebiID){
        return (chebiID.replaceFirst("CHEBI:", "MTBLC"));
    }
}

