/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/5/14 11:52 AM
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
import uk.ac.ebi.chebi.webapps.chebiWS.model.ChebiWebServiceFault_Exception;
import uk.ac.ebi.chebi.webapps.chebiWS.model.LiteEntity;
import uk.ac.ebi.chebi.webapps.chebiWS.model.LiteEntityList;
import uk.ac.ebi.chebi.webapps.chebiWS.model.RelationshipType;

import java.util.ArrayList;
import java.util.List;

/**
 * User: conesa
 * Date: 05/09/2014
 * Time: 11:52
 */
public class ChebiMetaboliteScanner {

	public static String  CHEBI_METABOLITE_ROLE = "CHEBI:25212";

	private boolean doFuzzyScan = true;

	private Logger LOGGER = Logger.getLogger(ChebiMetaboliteScanner.class);

	private ChebiWebServiceClient chebiWS = new ChebiWebServiceClient();
//	private ChebiWebServiceClient chebiWS = new ChebiWebServiceClient(new URL("http://www.ebi.ac.uk/webservices/chebi/2.0/webservice?wsdl"),new QName("http://www.ebi.ac.uk/webservices/chebi",	"ChebiWebServiceService"));

	public boolean isDoFuzzyScan() {
		return doFuzzyScan;
	}

	public void setDoFuzzyScan(boolean doFuzzyScan) {
		this.doFuzzyScan = doFuzzyScan;
	}


	public List<LiteEntity> scan () throws ChebiWebServiceFault_Exception {
		return scan(CHEBI_METABOLITE_ROLE);
	}

	// Scans chebi looking for any metabolite compound under the specified CHEBI entity.
	public List<LiteEntity> scan(String chebiId) throws ChebiWebServiceFault_Exception {

		List<LiteEntity> allIsA = getAllIsAChildren(chebiId);

		List<LiteEntity> finalList= new ArrayList<LiteEntity>();


		// Add the initial entity to the final array
		LiteEntity itSelf = new LiteEntity();

		itSelf.setChebiId(chebiId);
		allIsA.add(itSelf);

		// For each isA children we need to ask for structures
		for (LiteEntity isA: allIsA){

			List<LiteEntity> structures = getAllChildrenWithStructures(isA.getChebiId());


			finalList.addAll(structures);

		}

		return finalList;

	}


	//
	private List<LiteEntity> getAllIsAChildren(String chebiId) throws ChebiWebServiceFault_Exception {

		// Case: "human metabolite" is a Metabolite
		LiteEntityList el = null;

		LOGGER.info("Getting all children of " + chebiId + ". No structures and IS_A relationship" );
		el = chebiWS.getAllOntologyChildrenInPath(chebiId, RelationshipType.IS_A,false);

		return el.getListElement();
	}
	private List<LiteEntity> getAllChildrenWithStructures(String chebiEntity){

		LOGGER.info("Getting all children structures of " + chebiEntity );
		LiteEntityList childrenStructures = null;
		List<LiteEntity> finalEntities = new ArrayList<LiteEntity>();

		// Try to get the list of metabolites from chebi...
		try {
			childrenStructures = chebiWS.getAllOntologyChildrenInPath(chebiEntity, RelationshipType.HAS_ROLE,true);
		} catch (ChebiWebServiceFault_Exception e) {
			LOGGER.error("Can't get children structures of " + chebiEntity + ". Chebi WS can't be accessed");
			return childrenStructures.getListElement();
		}

		LOGGER.debug(childrenStructures.getListElement().size() + " Chebi compounds returned");

		finalEntities.addAll(childrenStructures.getListElement());

		// If we have to do a fuzzy scan
		if (doFuzzyScan){
			LOGGER.debug("Fuzzy scan active for " + chebiEntity);

			for (LiteEntity structure: childrenStructures.getListElement()){

				List<LiteEntity> relatives = getFamilyForChebiID(structure.getChebiId());

				// Add the relatives to the final list
				finalEntities.addAll(relatives);

			}

		}

		return finalEntities;
	}

	private List<LiteEntity> getFamilyForChebiID(String chebiId) {



		List<LiteEntity> chebiIdRelatives = new ArrayList<LiteEntity>();


		try {

			// ... try tautomers
			List<LiteEntity> tautomers = null;
			tautomers = getChebiIdsRelatives(chebiId, RelationshipType.IS_TAUTOMER_OF);

			chebiIdRelatives.addAll(tautomers);

			// ... try children
			List<LiteEntity>	children = getChebiIdsRelatives(chebiId, RelationshipType.IS_A);
			chebiIdRelatives.addAll(children);

			// ... try tautomers of the children
			List<LiteEntity> childrenTautomers = getChebiIdsRelatives(children, RelationshipType.IS_TAUTOMER_OF);
			chebiIdRelatives.addAll(childrenTautomers);

		} catch (ChebiWebServiceFault_Exception e) {
			LOGGER.error("Can't perform fuzy search of chebiID " + chebiId + " using chebi WS", e);
		}


		return chebiIdRelatives;

	}

	private List<LiteEntity> getChebiIdsRelatives(String chebiId, RelationshipType relType) throws ChebiWebServiceFault_Exception {

		LOGGER.debug("Getting relatives for  " + chebiId + ". Relationship: " + relType.name());

		// Get all the children of that chebi id
		LiteEntityList children = chebiWS.getAllOntologyChildrenInPath(chebiId, relType, true);


		return children.getListElement();

	}

	private List<LiteEntity> getChebiIdsRelatives(List<LiteEntity> chebiIdList, RelationshipType relType) throws ChebiWebServiceFault_Exception {


		List<LiteEntity> joinedRelatives = new ArrayList<LiteEntity>();

		// For each chebiID in the lis
		for (LiteEntity entity: chebiIdList){

			List<LiteEntity> relatives = getChebiIdsRelatives(entity.getChebiId(),relType);

			joinedRelatives.addAll(relatives);
		}

		return joinedRelatives;

	}

}
