/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Apr-07
 * Modified by:   kenneth
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.*;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.*;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.model.CrossReference;
import uk.ac.ebi.metabolights.referencelayer.model.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.model.MetaboLightsCompound;
import uk.ac.ebi.metabolights.referencelayer.model.Species;
import uk.ac.ebi.metabolights.webservice.client.RheaWebserviceWsClient;
import uk.ac.ebi.metabolights.webservice.client.WikipathwaysWsClient;
import uk.ac.ebi.rhea.ws.client.RheaResourceClient;
import uk.ac.ebi.rhea.ws.response.search.RheaReaction;

import javax.xml.namespace.QName;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.*;

public class ReferenceLayerImporter {

	private Logger LOGGER = LoggerFactory.getLogger(ReferenceLayerImporter.class);

	private ConnectionProvider connectionProvider;
    private MetaboLightsCompoundDAO mcd;
    private CrossReferenceDAO crd;
    private SpeciesDAO spd;
	private MetSpeciesDAO mspd;
	private DatabaseDAO dbd;

    private RheaResourceClient wsRheaClient;
	private RheaWebserviceWsClient rheaWebserviceWsClient;
    private ChebiWebServiceClient chebiWS;

    private WikipathwaysWsClient wpWsClient;

	private ArrayList<String> failedCompounds = new ArrayList<String>();

	public ChebiWebServiceClient getChebiWS() {
		if (chebiWS == null)
			try {
				LOGGER.info("Starting a new instance of the ChEBI webservice");
				chebiWS = new ChebiWebServiceClient(new URL("http://www.ebi.ac.uk/webservices/chebi/2.0/webservice?wsdl"),new QName("http://www.ebi.ac.uk/webservices/chebi", "ChebiWebServiceService"));
			} catch (MalformedURLException e) {
				LOGGER.error("Error instantiating a new ChebiWebServiceClient " + e.getMessage());
			}

		return chebiWS;
	}

	public void setChebiWS(ChebiWebServiceClient chebiWS) {
		this.chebiWS = chebiWS;
	}

	// Temporary chebiWS for producction database.
    //private ChebiWebServiceClient chebiWS = new ChebiWebServiceClient(new URL("http://ves-ebi-97:8100/chebi-tools/webservices/2.0/webservice?wsdl"),new QName("http://www.ebi.ac.uk/webservices/chebi",	"ChebiWebServiceService"));
    // Root chebi entity that holds all the compound to import, by default is "metabolite".
	private static final Long CHEBI_DB_ID = new Long(1);
    private String chebiIDRoot = null;
	private ProcessReport processReport;

	public class ImportOptions
	{
		public static final int REFRESH_MET_SPECIES = 0x1;
		public static final int UPDATE_EXISTING_MET= 0x1<<1;
//		public static final int DO_FUZZY_SEARCH = 0x1<<2;
//		public static final int FOUR = 0x1<<3;
//		public static final int FIVE = 0x1<<4;

		// COMBOS...
		public static final int SPECIES_AND_UPDATE_MET = REFRESH_MET_SPECIES + UPDATE_EXISTING_MET;
//		public static final int ALL = REFRESH_MET_SPECIES + UPDATE_EXISTING_MET + DO_FUZZY_SEARCH;
		public static final int ALL = REFRESH_MET_SPECIES + UPDATE_EXISTING_MET;
	}


	// Enum that maps any chebi ontology parent to an species
	public enum OntologyParentsToSpeciesMap{

		HUMAN(new String[]{"CHEBI:75770", "CHEBI:77123", "CHEBI:76967"},"Homo sapiens", "NCBI:9606"),
		ECOLI(new String[]{"CHEBI:76971", "CHEBI:76972","CHEBI:75761"},"Escherichia coli", "NEWT:562"),
		BAKERSYEAST(new String[]{"CHEBI:75772","CHEBI:76949","CHEBI:76951"}, "Saccharomyces cerevisiae", "NCBI:4932"),
		MOUSE(new String[]{"CHEBI:75771"}, "Mus musculus", "NCBI:10090"),
		STREPTOCOCCUS(new String[]{"CHEBI:76973", "CHEBI:76974", "CHEBI:75789"}, "Streptococcus pneumoniae", "NEWT:1313");
//		DAPHNIA_MAGNA(new String[]{"CHEBI:83056"}, "Daphnia magna", "NEWT:35525" ),
//		DAPHNIA_GALEATA(new String[]{"CHEBI:83038"}, "Daphnia galeata", "NEWT:35525" ),
//		DAPHNIA_PULEX(new String[]{"CHEBI:83075"}, "Daphnia pulex", "NEWT:35525" ),
//		DAPHNIA_TENEBROSA(new String[]{"CHEBI:83146"}, "Daphnia tenebrosa", "NEWT:35525" );

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


	//private int importOptions = ImportOptions.UPDATE_EXISTING_MET;
    private int importOptions = ImportOptions.REFRESH_MET_SPECIES;

	// Instantiate with a connection object
    public ReferenceLayerImporter(ConnectionProvider connectionProvider) throws IOException {

		this.connectionProvider = connectionProvider;

    }

	private void initializeDAOs() throws IOException {

		// If already initialized
		if (mcd != null) return;

		Connection connection = connectionProvider.getConnection();

		mcd = new MetaboLightsCompoundDAO(connection);
		crd = new CrossReferenceDAO(connection);
		spd = new SpeciesDAO(connection);
		mspd = new MetSpeciesDAO(connection);
		dbd = new DatabaseDAO(connection);

	}
	public int getImportOptions() {
		return importOptions;
	}

	public void setImportOptions(int importOptions) {
		this.importOptions = importOptions;
	}

    public String getChebiIDRoot() {
        return chebiIDRoot;
    }

    public void setChebiIDRoot(String chebiIDRoot) {
        this.chebiIDRoot = chebiIDRoot;
    }

	public ProcessReport getProcessReport() {
		return processReport;
	}

	public void importMetabolitesFromChebi() throws IOException {

		processReport = new ProcessReport("Importing/updating metabolites from chebi, from " + chebiIDRoot, 2);
		processReport.started();

		ChebiMetaboliteScanner scanner = new ChebiMetaboliteScanner();
		scanner.setParentProcessReport(processReport);

		Map<String, Entity> metabolitesToImport = null;

		try {
			metabolitesToImport = scanner.scan(chebiIDRoot==null?scanner.CHEBI_METABOLITE_ROLE:chebiIDRoot);

		} catch (ChebiWebServiceFault_Exception e) {
			LOGGER.error("Can't scan metabolites from chebi", e);
		}


		processReport.oneMore();

		ProcessReport importProcess = processReport.addSubProcess("Importing " + metabolitesToImport.size() + " compounds into MetaboLights", metabolitesToImport.size());


		importProcess.started();

		// Now try to save the metabolites
		try {

			// Initialize now DAO (will happen only the firs time).
			initializeDAOs();

			if (rheaWebserviceWsClient == null)
				this.rheaWebserviceWsClient = getRheaWsClient();

            if (wpWsClient == null)
                this.wpWsClient = getWsclient();

			// Now we should have a list of chebi ids...
			for (Entity  metabolite: metabolitesToImport.values()) {

				LOGGER.info(metabolite.getChebiId() + "- Started importing");
				chebiEntity2Metabolights(metabolite);
				importProcess.oneMore();

			}

		} catch (DAOException e) {

			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		importProcess.finished();
		processReport.oneMore();
		processReport.finished();

	}

	/**
	 * Refreshes status of already existing mtblc entries in the reference layer.
	 * @throws DAOException
	 */
	public void refreshMTBLC() throws DAOException, IOException {

		// Get all the MTBLC_PREFIX items
		Set<MetaboLightsCompound> mtblcToRefresh = mcd.getAllCompounds();

		// For each compound
		for (MetaboLightsCompound mc: mtblcToRefresh)
		{
			chebiID2MetaboLights(mc.getChebiId());
		}

	}

    public void importMetabolitesFromChebiTSV(File chebiTSV, long startFrom) throws DAOException, IOException {

        LOGGER.info("Importing metabolites from chebi. TSV: " + chebiTSV.getAbsoluteFile());

		ArrayList<String> chebiIds = ChebiTools.chebiTsvToArrayList(chebiTSV);

		chebiID2MetaboLights(chebiIds);


    }


	public void importMetabolitesFromChebiTSV(File chebiTSV) throws DAOException, IOException {
		importMetabolitesFromChebiTSV(chebiTSV, 1);
	}


	private void chebiID2MetaboLights(ArrayList<String> chebiIds) throws DAOException, IOException {

		for (String chebiId: chebiIds){
			chebiID2MetaboLights(chebiId);
		}
	}

	/*
	Returns 1 if successful
	 */
    public int chebiID2MetaboLights(String chebiId) throws DAOException, IOException {

		// Get a complete entity....
		Entity entity = null;
		try {
			LOGGER.info("Getting information from ChEBI WS for "+chebiId);
			entity = getChebiWS().getCompleteEntity(chebiId);
			initializeDAOs();
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

	private int chebiEntity2Metabolights(Entity entity) throws DAOException, IOException {

		// If the entity has no structure
		if (entity.getSmiles() == null){
			return 0;
		}

		try {

			String accession = MetaboLightsCompoundDAO.chebiID2MetaboLightsID(entity.getChebiId());

			// Check if we have already the Metabolite (since querying the WS is what takes more...)
			MetaboLightsCompound mc = mcd.findByCompoundAccession(accession);

			if (mc != null){

				if (!isUpdatedRecently(mc)){
					// If we don't need to update existing metabolites..
					if ((importOptions & ImportOptions.UPDATE_EXISTING_MET) == 0){
						LOGGER.info("The compound " + accession + " already exists and update option not selected.");
					//	return 0;
					}


					// ...we already have it...don't do anything..although at some point we may want to update it..
					LOGGER.info("The compound " + accession + " is already imported into the database. Updating it");

					if ((importOptions & ImportOptions.REFRESH_MET_SPECIES) == ImportOptions.REFRESH_MET_SPECIES){
						deleteExistingCHEBISpecies(mc);
					}
				} else {
					LOGGER.info("The compound " + entity.getChebiId() + " is updated recently. Skipping it!");
					return 1;
				}

			} else {

				// ...if execution reach this point....we don't have the compound and needs to be imported from chebi WS.
				mc = new MetaboLightsCompound();

				// ...log new compound found
				LOGGER.info("The compound " + entity.getChebiId() + " will be imported. Importing it");

			}

			mc.setAccession(MetaboLightsCompoundDAO.chebiID2MetaboLightsID(entity.getChebiId()));
			mc.setChebiId(entity.getChebiId());
			mc.setName(entity.getChebiAsciiName());

			mc.setDescription(entity.getDefinition());
			mc.setInchi(entity.getInchi());
			mc.setInchikey(entity.getInchiKey());
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



			mc.setHasPathways(getPathways(mc.getChebiId(), "Ce"));
			mc.setHasSpecies(mc.getMetSpecies().size() != 0);

			mcd.save(mc);

			return 1;

		} catch (DAOException e){

			LOGGER.error(entity.getChebiId() + " failed. "+e.getMessage());

			failedCompounds.add(entity.getChebiId());

			mcd = null;

			initializeDAOs();

			return 1;

		}

	}

	private boolean isUpdatedRecently(MetaboLightsCompound mc){
		Date mcUpdatedDate = new Date(mc.getUpdatedDate().getTime());
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(mcUpdatedDate);
		cal2.setTime(new Date());

		if(cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
		//	return true;
		}

		return false;
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

        LOGGER.debug("Getting Literature from chebi WS");

        return hasLiterature;
    }



	private boolean getPathways(String chebiID, String code){
		boolean hasPathways  = false;
		LOGGER.debug("Initializing and getting pathways from Wikipathways");
		return wpWsClient.hasWikiPathways(chebiID,code);
	}


    private boolean getReactions(String chebiID) {
        boolean hasReactions = false;
		try {
			hasReactions = rheaWebserviceWsClient.hasReaction(chebiID);
		} catch (Exception e){
			LOGGER.error("Could not get Rhea reaction for "+chebiID);
		}
        return hasReactions;
    }


    public WikipathwaysWsClient getWsclient(){

        if (wpWsClient instanceof WikipathwaysWsClient) {
            LOGGER.info("Returning the existing WikipathwaysClient");
            return wpWsClient;
        }

        if (wpWsClient == null) {
            //URL wsURL = null;
            // try {
            //     wsURL = new URL("http://webservice.wikipathways.org");
            // } catch (MalformedURLException e) {
            //     e.printStackTrace();
            // }
            wpWsClient = new WikipathwaysWsClient("http://webservice.wikipathways.org");
        }

        return wpWsClient;
    }

	public RheaWebserviceWsClient getRheaWsClient(){

		if (rheaWebserviceWsClient instanceof RheaWebserviceWsClient) {
			return rheaWebserviceWsClient;
		}
		if (rheaWebserviceWsClient == null) {
			rheaWebserviceWsClient = new RheaWebserviceWsClient();
		}
		return rheaWebserviceWsClient;
	}

	public RheaResourceClient getWsRheaClient() {
		if (wsRheaClient instanceof RheaResourceClient) {
			LOGGER.info("Returning the existing RheaResourceClient");
			return wsRheaClient;
		}

		if (wsRheaClient == null) {
			wsRheaClient = new RheaResourceClient();
			LOGGER.info("Returning a new RheaResourceClient");
		}

		return wsRheaClient;
	}


	public void setWsRheaClient(RheaResourceClient wsRheaClient) {
		this.wsRheaClient = wsRheaClient;
	}


    private void importMetSpeciesFromCompundOrigins(MetaboLightsCompound mc, Entity chebiEntity, CrossReference chebiXRef) throws DAOException {

		//For each compound origin in chebi...
        for (CompoundOriginDataItem origin : chebiEntity.getCompoundOrigins()){

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


	private Species getSpeciesFromCompoundOrigins(CompoundOriginDataItem origin) throws DAOException {

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
}

