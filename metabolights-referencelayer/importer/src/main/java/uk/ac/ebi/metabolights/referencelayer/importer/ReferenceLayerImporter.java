/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/23/14 4:51 PM
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
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.*;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.*;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.domain.CrossReference;
import uk.ac.ebi.metabolights.referencelayer.domain.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.domain.MetaboLightsCompound;
import uk.ac.ebi.metabolights.referencelayer.domain.Species;
import uk.ac.ebi.rhea.ws.client.RheasResourceClient;
import uk.ac.ebi.rhea.ws.response.search.RheaReaction;

import javax.xml.namespace.QName;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class ReferenceLayerImporter {

    private Logger LOGGER = Logger.getLogger(ReferenceLayerImporter.class);

    private MetaboLightsCompoundDAO mcd;
    private CrossReferenceDAO crd;
    private SpeciesDAO spd;
	private MetSpeciesDAO mspd;
	private DatabaseDAO dbd;

    private RheasResourceClient wsRheaClient;

    //private ChebiWebServiceClient chebiWS = new ChebiWebServiceClient();
     private ChebiWebServiceClient chebiWS = new ChebiWebServiceClient(new URL("http://www.ebi.ac.uk/webservices/chebi/2.0/webservice?wsdl"),new QName("http://www.ebi.ac.uk/webservices/chebi",	"ChebiWebServiceService"));

    // Temporary chebiWS for producction database.
    //private ChebiWebServiceClient chebiWS = new ChebiWebServiceClient(new URL("http://ves-ebi-97:8100/chebi-tools/webservices/2.0/webservice?wsdl"),new QName("http://www.ebi.ac.uk/webservices/chebi",	"ChebiWebServiceService"));
    // Root chebi entity that holds all the compound to import, by default is "metabolite".
	private static final Long CHEBI_DB_ID = new Long(1);
    private String chebiIDRoot = ChebiMetaboliteScanner.CHEBI_METABOLITE_ROLE;
    private RelationshipType relationshipType = RelationshipType.HAS_ROLE;

	public class ImportOptions
	{
		public static final int REFRESH_MET_SPECIES = 0x1;
		public static final int UPDATE_EXISTING_MET= 0x1<<1;
		public static final int DO_FUZZY_SEARCH = 0x1<<2;
//		public static final int FOUR = 0x1<<3;
//		public static final int FIVE = 0x1<<4;

		// COMBOS...
		public static final int SPECIES_AND_UPDATE_MET = REFRESH_MET_SPECIES + UPDATE_EXISTING_MET;
		public static final int ALL = REFRESH_MET_SPECIES + UPDATE_EXISTING_MET + DO_FUZZY_SEARCH;
	}


	// Enum that maps any chebi ontology parent to an species
	public enum OntologyParentsToSpeciesMap{

		HUMAN(new String[]{"CHEBI:75770", "CHEBI:77123", "CHEBI:76967"},"Homo sapiens (Human)", "NEWT:9606"),
		ECOLI(new String[]{"CHEBI:76971", "CHEBI:76972","CHEBI:75761"},"Escherichia coli", "NEWT:562"),
		BAKERSYEAST(new String[]{"CHEBI:75772","CHEBI:76949","CHEBI:76951"}, "Saccharomyces cerevisiae (Baker's yeast)", "NEWT:4932"),
		MOUSE(new String[]{"CHEBI:75771"}, "Mus musculus (Mouse)", "NEWT:10090"),
		STREPTOCOCCUS(new String[]{"CHEBI:76973", "CHEBI:76974", "CHEBI:75789"}, "Streptococcus pneumoniae", "NEWT:1313");

		private String[] ontologyParentIds;
		private String speciesName;
		private String taxonId;

		private OntologyParentsToSpeciesMap(String[] ontologyParentIds, String speciesName, String taxonId)
		{
			this.ontologyParentIds = ontologyParentIds;
			this.speciesName = speciesName;
			this.taxonId = taxonId;

		}

		public String[] getOntologyParentIds() {
			return ontologyParentIds;
		}

		public String getSpeciesName() {
			return speciesName;
		}

		public String getTaxonId() {
			return taxonId;
		}

	}


	private int importOptions = ImportOptions.UPDATE_EXISTING_MET;

	// Instantiate with a connection object
    public ReferenceLayerImporter(Connection connection) throws IOException {
        this.mcd = new MetaboLightsCompoundDAO(connection);
        this.crd = new CrossReferenceDAO(connection);
        this.spd = new SpeciesDAO(connection);
		this.mspd = new MetSpeciesDAO(connection);
		this.dbd = new DatabaseDAO(connection);

    }

	public int getImportOptions() {
		return importOptions;
	}

	public void setImportOptions(int importOptions) {
		this.importOptions = importOptions;
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

	public void importMetabolitesFromChebi() {

		LOGGER.info("Importing metabolites from chebi. Root: " + chebiIDRoot + ", relationship type: " + relationshipType);

		ChebiMetaboliteScanner scanner = new ChebiMetaboliteScanner();

		Map<String, Entity> allMetabolites = null;

		try {
			allMetabolites = scanner.scan(chebiIDRoot);

		} catch (ChebiWebServiceFault_Exception e) {
			LOGGER.error("Can't scan metabolites from chebi", e);
		}


		LOGGER.info(allMetabolites.size() + " Chebi metabolites returned.");

		Long imported = new Long(0);

		// Now try to save the metabolites
		try {

			// Now we should have a list of chebi ids...
			for (Entity  metabolite: allMetabolites.values()) {

				imported = imported + chebiEntity2Metabolights(metabolite);

			}

		} catch (DAOException e) {

			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		LOGGER.info(imported + " new compounds imported.");

	}

	/**
	 * Refreshes status of already existing mtblc entries in the reference layer.
	 * @throws DAOException
	 */
	public void refreshMTBLC() throws DAOException {

		// Get all the MTBLC items
		Set<MetaboLightsCompound> mtblcToRefresh = mcd.getAllCompounds();

		// For each compound
		for (MetaboLightsCompound mc: mtblcToRefresh)
		{

			chebiID2MetaboLights(mc.getChebiId());

		}


	}

    public void importMetabolitesFromChebiTSV(File chebiTSV, long startFrom){

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
                if (linesRead >= startFrom){
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
	public void importMetabolitesFromChebiTSV(File chebiTSV) {
		importMetabolitesFromChebiTSV(chebiTSV,1);
	}


	/*
	Returns 1 if successful
	 */
    private int chebiID2MetaboLights(String chebiId) throws DAOException {


		// Get a complete entity....
		Entity entity = null;
		try {
			entity = chebiWS.getCompleteEntity(chebiId);
			chebiEntity2Metabolights(entity);


		} catch (ChebiWebServiceFault_Exception e) {
			LOGGER.info("Can't get a Complete entity for " + chebiId);
			return 0;
		}

		if (entity == null) {

			// chebiID not found
			LOGGER.info("The compound " + chebiId + " wasn't found by the chebi webservice");
			return 0;
		}

		return 1;
    }

	private int chebiEntity2Metabolights(Entity entity) throws DAOException {

		try {

			String accession = chebiID2MetaboLightsID(entity.getChebiId());


			// Check if we have already the Metabolite (since querying the WS is what takes more...)
			MetaboLightsCompound mc = mcd.findByCompoundAccession(accession);

			if (mc != null){

				// If we don't need to update existing metabolites..
				if ((importOptions & ImportOptions.UPDATE_EXISTING_MET) == 0){
					LOGGER.info("The compound " + accession + " already exists and update option not selected.");
					return 0;
				}


				// ...we already have it...don't do anything..although at some point we may want to update it..
				LOGGER.info("The compound " + accession + " is already imported into the database. Updating it");

				if ((importOptions & ImportOptions.REFRESH_MET_SPECIES) == ImportOptions.REFRESH_MET_SPECIES){
					deleteExistingCHEBISpecies(mc);

				}

			} else {

				// ...if execution reach this point....we don't have the compound and needs to be imported from chebi WS.
				mc = new MetaboLightsCompound();

				// ...log new compound found
				LOGGER.info("The compound " + entity.getChebiId() + " will be imported. Importing it");

			}

			mc.setAccession(chebiID2MetaboLightsID(entity.getChebiId()));
			mc.setChebiId(entity.getChebiId());
			mc.setName(entity.getChebiAsciiName());

			mc.setDescription(entity.getDefinition());
			mc.setInchi(entity.getInchi());
			mc.setFormula(extractFormula(entity.getFormulae()));
			mc.setIupacNames(extractIupacNames(entity.getIupacNames()));


			if ((importOptions & ImportOptions.REFRESH_MET_SPECIES) == ImportOptions.REFRESH_MET_SPECIES){
				// Update species information
				CrossReference chebiXRef = getCrossReference(entity);
				importMetSpeciesFromCompundOrigins(mc, entity, chebiXRef);
				importMetSpeciesFromOntologyParents(mc, entity, chebiXRef);
			}


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
					LOGGER.info("The compound " + entity.getChebiId() + " is already imported into the database (Duplicated primary key)", e);
					return 0;
				} else {
					throw e;
				}
			} else {
				throw e;
			}

		}

	}

	private void deleteExistingCHEBISpecies(MetaboLightsCompound mc) throws DAOException {

		// Go through the species collection
		for (MetSpecies metSpecies:mc.getMetSpecies()){

			// if the species is reported by CHEBI....
			if (metSpecies.getCrossReference().getDb().getId() == CHEBI_DB_ID){

				System.out.print(true);
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

    private void importMetSpeciesFromCompundOrigins(MetaboLightsCompound mc, Entity chebiEntity, CrossReference chebiXRef) throws DAOException {

		//For each compound origin in chebi...
        for (CompoundOrigins origin : chebiEntity.getCompoundOrigins()){

            Species sp = getSpeciesFromCompoundOrigins(origin);

			addMetSpecies(mc, chebiXRef, sp);
        }
	}

	private void addMetSpecies(MetaboLightsCompound mc, CrossReference chebiXRef, Species sp) {
		MetSpecies ms = new MetSpecies(sp,chebiXRef);

		if (!mc.getMetSpecies().contains(ms)){
			mc.getMetSpecies().add(ms);
		}
	}

	/**
	 * Will scan the ontolgy parent to look for classes that imply an species
	 * Task url: https://www.pivotaltracker.com/story/show/67909198
	 * A chebi compound can has role: human metabolite, mouse metabolite, Escherichia coli metabolite....
	 * We nned to look for these classes and if found add them
	 * Unfortunately this also could be tha case: human secondary metabolite (CHEBI:77123)
	 * therefore:
	 *
	 * 1.- we scan the parents recursively until we find "human metabolite"
	 * 2.- or we have a list with not only "human metabolite" but "human secondary metabolite" as well.
	 * Doing #2.
	 *
	 * @param mc
	 * @param chebiEntity
	 */
	private void importMetSpeciesFromOntologyParents(MetaboLightsCompound mc, Entity chebiEntity, CrossReference chebiXRef) throws DAOException {

		// Get also species information from ontology classes
		for (OntologyDataItem parent: chebiEntity.getOntologyParents()){


			// Go through the ontology parents map
			for (OntologyParentsToSpeciesMap map: OntologyParentsToSpeciesMap.values())
			{

				// If the map has the id...
				if (Arrays.asList(map.getOntologyParentIds()).contains(parent.getChebiId()))
				{

					//... we've found a parent that represent an species
					// Let's create the species
					Species sp = getSpecies(map.getSpeciesName(), map.getTaxonId());

					addMetSpecies(mc, chebiXRef, sp);
					break;
				}
			}
		}
	}

	private CrossReference getCrossReference(Entity chebiEntity) throws DAOException {

		CrossReference cr = crd.findByCrossReferenceAccession(chebiEntity.getChebiId());

		// If not CrossReference was found
		if (cr == null){


			cr = new CrossReference();
			cr.setAccession(chebiEntity.getChebiId());
			cr.setDb(dbd.findByDatabaseId(CHEBI_DB_ID));
		}

		return cr;
	}


	private Species getSpeciesFromCompoundOrigins(CompoundOrigins origin) throws DAOException {

        // Try to find the specie
		return getSpecies(origin.getSpeciesText(), origin.getSpeciesAccession());

    }

	private Species getSpecies(String species, String taxon) throws DAOException {

		Species sp;//Try to get it from metabolights using the taxon first
		if (taxon != null) {

			sp = spd.findBySpeciesTaxon(taxon);

		} else {

        	sp = spd.findBySpeciesName(species);
		}

		// If not found create one...
		if (sp == null){
			sp = new Species();
			sp.setSpecies(species);
			sp.setTaxon(taxon);
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

