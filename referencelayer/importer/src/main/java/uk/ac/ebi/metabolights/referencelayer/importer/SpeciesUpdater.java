package uk.ac.ebi.metabolights.referencelayer.importer;


import org.apache.log4j.Logger;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.SpeciesDAO;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.domain.Species;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;


/**
 * User: conesa
 * Date: 18/06/2013
 * Time: 11:19
 */
public class SpeciesUpdater {

    public static final String ONTOLOGY = "NEWT";
    Logger LOGGER = Logger.getLogger(SpeciesUpdater.class);
    OntologyLookUpService ols = new OntologyLookUpService();
    SpeciesDAO speciesDAO;

    public SpeciesUpdater(Connection connection){

        try {
            speciesDAO = new SpeciesDAO(connection);

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    public void UpdateSpeciesInformation(){

        // Get all the species from MetaboLights...
        try {
            Set<Species> speciesList = speciesDAO.findAll();


            // For each species..
            for (Species sp : speciesList){

                // Update species info from ols...
                UpdateSpecieInformation(sp);

            }


        } catch (DAOException e) {
            LOGGER.error("Can't update species information");
        }



    }
    public void UpdateSpecieInformation(long speciesId){

        try {
            Species sp = speciesDAO.findBySpeciesId(speciesId);

            if (sp != null){

                UpdateSpecieInformation(sp);

            } else {
                LOGGER.error("Can't find species by id " + speciesId);
            }

        } catch (DAOException e) {
            LOGGER.error("Error trying to find species by id " + speciesId + ":\n" + e.getMessage());
        }


    }
    private void UpdateSpecieInformation(Species sp)  {


        //IF WE ALREADY HAVE THE TAXON WE DO NOT UPDATE ANYTHING...
        if (sp.getTaxon() == null) LOGGER.info ("Specie " + sp.getSpecies() + "(ID:" +sp.getId() + ") has taxon, we will not update it.");

        // Look up for the species information in OLS.
        try {

            Map<String,String> terms = ols.getTermsByName(sp.getSpecies(), ONTOLOGY);

            // If there's anything
            if (terms.size() !=0){

                Map.Entry entry = terms.entrySet().iterator().next();

                String taxon = ONTOLOGY + ":" + entry.getKey();
                sp.setTaxon(taxon);

                speciesDAO.save(sp);

                LOGGER.debug ("Specie " + sp.getSpecies() + "(ID:" +sp.getId() + ") taxon updated with " + taxon);

            } else {
                LOGGER.info ("Nothing found for " + sp.getSpecies() + " in " + ONTOLOGY + ".");
            }


        } catch (ServiceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
