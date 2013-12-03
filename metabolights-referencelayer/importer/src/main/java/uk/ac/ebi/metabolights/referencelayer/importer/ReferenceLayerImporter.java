/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 20/06/13 14:18
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.referencelayer.importer;

import org.apache.log4j.Logger;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.*;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.CrossReferenceDAO;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.MetSpeciesDAO;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.MetaboLightsCompoundDAO;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.SpeciesDAO;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.domain.CrossReference;
import uk.ac.ebi.metabolights.referencelayer.domain.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.domain.MetaboLightsCompound;
import uk.ac.ebi.metabolights.referencelayer.domain.Species;
import uk.ac.ebi.rhea.ws.client.RheasResourceClient;
import uk.ac.ebi.rhea.ws.response.search.RheaReaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReferenceLayerImporter{

    private Logger LOGGER = Logger.getLogger(ReferenceLayerImporter.class);

    private MetaboLightsCompoundDAO mcd;
    private CrossReferenceDAO crd;
    private SpeciesDAO spd;
	private MetSpeciesDAO mspd;

    private RheasResourceClient wsRheaClient;

    private ChebiWebServiceClient chebiWS = new ChebiWebServiceClient();
   // private ChebiWebServiceClient chebiWS = new ChebiWebServiceClient(new URL("http://www.ebi.ac.uk/webservices/chebi/2.0/webservice?wsdl"),new QName("http://www.ebi.ac.uk/webservices/chebi",	"ChebiWebServiceService"));

    // Root chebi entity that holds all the compound to import, by default is "metabolite".
	private static final int CHEBI_DB_ID = 1;
    private String chebiIDRoot = "CHEBI:25212";
    private RelationshipType relationshipType = RelationshipType.HAS_ROLE;
	private boolean deleteExistingCHEBISpecies = false;

    // Instantiate with a connection object
    public ReferenceLayerImporter(Connection connection) throws IOException {
        this.mcd = new MetaboLightsCompoundDAO(connection);
        this.crd = new CrossReferenceDAO(connection);
        this.spd = new SpeciesDAO(connection);
		this.mspd = new MetSpeciesDAO(connection);

    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getChebiIDRoot() {
        return chebiIDRoot;
    }

    public void setChebiIDRoot(String chebiIDRoot) {
        this.chebiIDRoot = chebiIDRoot;
    }

	public boolean isDeleteExistingCHEBISpecies() {
		return deleteExistingCHEBISpecies;
	}

	public void setDeleteExistingCHEBISpecies(boolean deleteExistingCHEBISpecies) {
		this.deleteExistingCHEBISpecies = deleteExistingCHEBISpecies;
	}

	public void importMetabolitesFromChebi(){

        LOGGER.info("Importing metabolites from chebi. Root: " + chebiIDRoot + ", relationship type: " + relationshipType);
        LiteEntityList el = null;

        // Try to get the list of metabolites from chebi...
        try {
             el = chebiWS.getAllOntologyChildrenInPath(chebiIDRoot, relationshipType,true);
        } catch (ChebiWebServiceFault_Exception e) {
            LOGGER.error("Can't import Metabolites from chebi. Chebi WS can't be accessed");
            return;
        }

        LOGGER.info(el.getListElement().size() + " Chebi compounds returned.");

        Long imported = new Long(0);

        // Now try to save the metabolites
        try {

            // Now we should have a list of chebi ids...
            for (LiteEntity le: el.getListElement()){

                imported = imported + chebiID2MetaboLights(le.getChebiId());

            }

        } catch (DAOException e) {

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        LOGGER.info(imported + " new compounds imported.");

    }



    public void importMetabolitesFromChebiTSV(File chebiTSV){

        LOGGER.info("Importing metabolites from chebi. TSV: " + chebiTSV.getAbsoluteFile());

        // Try to get the list of metabolites from chebi TSV...
        // SAMPLE:
        // ChEBI ID	ChEBI ASCII Name	Text Score	Tanimoto Similarity Score	Entry Status
        // CHEBI:36062	3,4-dihydroxybenzoic acid	7.22
        // CHEBI:1189	2-methoxyestrone	7.11
        // CHEBI:1387	3,4-dihydroxyphenylethyleneglycol	7.11
        // CHEBI:3648	chlorpromazine <i>N</i>-oxide	7.11

        Long linesRead = new Long(0);
        Long imported = new Long(0);


        // Open and go through the file
        try {
            //Use a buffered reader
            BufferedReader reader = new BufferedReader(new FileReader(chebiTSV));
            String line = "", text = "";

            //Go through the file
            while((line = reader.readLine()) != null)
            {

                // If its not the first line...skip it as it is the header definition line....
                if (linesRead !=0){
                    // Get the Chebi id ==> First element in a line separated by Tabulators
                    String[] values = line.split("\t");

                    imported = imported + chebiID2MetaboLights(values[0]);

                }

                linesRead++;

            }

            //Close the reader
            reader.close();

        } catch (DAOException e) {

            LOGGER.error("Can't save a metabolite to the Database.",e);

        } catch (Exception e) {
            LOGGER.error("Can't import Metabolites from chebi TSV.",e);
            return;
        }

        LOGGER.info(imported + " new compounds imported.");

    }


	public void importMetaboliteFromChebiID(String chebiId) throws DAOException {

		chebiID2MetaboLights(chebiId);
	}

    /*
    Returns 1 if successful
     */
    private int chebiID2MetaboLights(String chebiId) throws DAOException {

        try {


            String accession = chebiID2MetaboLightsID(chebiId);

            // Check if we have already the Metabolite (since querying the WS is what takes more...)
            MetaboLightsCompound mc = mcd.findByCompoundAccession(accession);

            if (mc != null){
                // ...we already have it...don't do anything..although at some point we may want to update it..
                LOGGER.info("The compound " + accession + " is already imported into the database. Updating it");

				if (deleteExistingCHEBISpecies){
					deleteExistingCHEBISpecies(mc);
				}

            } else {

                // ...if execution reach this point....we don't have the compound and needs to be imported from chebi WS.
                mc = new MetaboLightsCompound();

            }


            // Populate the metabolite compound with chebi data.
            // Get a complete entity....
            Entity entity = chebiWS.getCompleteEntity(chebiId);

            mc.setAccession(chebiID2MetaboLightsID(entity.getChebiId()));
            mc.setChebiId(entity.getChebiId());
            mc.setName(entity.getChebiAsciiName());

            mc.setDescription(entity.getDefinition());
            mc.setInchi(entity.getInchi());
            mc.setFormula(extractFormula(entity.getFormulae()));
            mc.setIupacNames(extractIupacNames(entity.getIupacNames()));

            // Update species information
            importMetSpeciesFromChebi(mc,entity);

            mc.setHasLiterature(getLiterature(entity));
            mc.setHasReaction(getReactions(mc.getChebiId()));
            mc.setHasSpecies(mc.getMetSpecies().size() != 0);

            mcd.save(mc);

            return 1;

        } catch (DAOException e){

            Throwable cause = e.getCause();

            if (cause instanceof SQLException){

                SQLException sqle = (SQLException) cause;
                // If it's bacause a duplicate key...
                //http://stackoverflow.com/questions/1988570/how-to-catch-a-specific-exceptions-in-jdbc
                if (sqle.getSQLState().startsWith("23")){
                    LOGGER.info("The compound " + chebiId + " is already imported into the database (Duplicated primary key)", e);
                    return 0;
                } else {
                    throw e;
                }
            } else {
                throw e;
            }
        } catch (ChebiWebServiceFault_Exception e) {
            LOGGER.info("Can't get a Complete entity for " + chebiId);
            return 0;
        }
    }

	private void deleteExistingCHEBISpecies(MetaboLightsCompound mc) throws DAOException {

		// Go through the species collection
		for (MetSpecies metSpecies:mc.getMetSpecies()){

			// if the species is reported by CHEBI....
			if (metSpecies.getCrossReference().getDb().getId() == CHEBI_DB_ID){

				//...delete it in the DB.
				mspd.delete(metSpecies);

			}

		}

		// remove it from the collection
		mc.getMetSpecies().clear();


	}

	private boolean getLiterature(Entity entity) {

        boolean hasLiterature = false;
        int literatureSize = entity.getCitations().size();

        if(literatureSize != 0){
            hasLiterature = true;
        }

        LOGGER.info("Getting Literature from chebi WS");

        return hasLiterature;
    }


    private boolean getReactions(String chebiID) {
        boolean hasReactions = false;

        LOGGER.info("Initializing and getting reactions from Rhea WS");
        initializeRheaClient();
        List<RheaReaction> reactions = wsRheaClient.search(chebiID);

        if(reactions.size() != 0){
            hasReactions = true;
        }

        return hasReactions;
    }

    private void initializeRheaClient(){
        if (wsRheaClient == null){
            wsRheaClient = new RheasResourceClient();
        }

    }

    private void importMetSpeciesFromChebi(MetaboLightsCompound mc , Entity chebiEntity) throws DAOException {


        // In dev chebi db id is 1
        //Database chebiDB =  crd.findByDatabaseId(Long.valueOf(1));

        CrossReference chebiXRef = crd.findByCrossReferenceAccession(chebiEntity.getChebiId());

        //For each compound origin in chebi...
        for (CompoundOrigins origin : chebiEntity.getCompoundOrigins()){

            Species sp = getSpecies(origin);

            MetSpecies ms = new MetSpecies(sp,chebiXRef);

            if (!mc.getMetSpecies().contains(ms)){
                mc.getMetSpecies().add(ms);
            }
        }
    }

    private Species getSpecies(CompoundOrigins origin) throws DAOException {

        // Try to find the specie
        String species = origin.getSpeciesText();
		String taxon = origin.getSpeciesAccession();

		Species sp;

		//Try to get it from metabolights using the taxon first
		if (taxon != null) {

			sp = spd.findBySpeciesTaxon(taxon);

		} else {

        	sp = spd.findBySpeciesName(species);
		}

        // If not found create one...
        if (sp == null){
            sp = new Species();
            sp.setSpecies(origin.getSpeciesText());
            sp.setTaxon(origin.getSpeciesAccession());
        }

        return sp;

    }

    private String extractIupacNames(List<DataItem> iupacNames){

        if (iupacNames.size()>0) {
            return iupacNames.iterator().next().getData();
        }

        return null;
    }

    private String extractFormula(List<DataItem> formulas){

        if (formulas.size()>0) {
            return formulas.iterator().next().getData();
        }

        return null;
    }


    private String chebiID2MetaboLightsID(String chebiID){
        return (chebiID.replaceFirst("CHEBI:", "MTBLC"));
    }
}

