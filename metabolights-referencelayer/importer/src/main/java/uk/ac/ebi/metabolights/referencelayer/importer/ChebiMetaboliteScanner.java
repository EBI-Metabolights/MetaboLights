/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Apr-01
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

import com.google.common.io.Files;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.*;

import javax.xml.namespace.QName;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * User: conesa
 * Date: 05/09/2014
 * Time: 11:52
 */
public class ChebiMetaboliteScanner {

	private static final String CHEBI_FILE_EXPORTER_URL = "https://www.ebi.ac.uk/chebi/fileExporter.do";
	private Logger LOGGER = LoggerFactory.getLogger(ChebiMetaboliteScanner.class);

	public String  CHEBI_METABOLITE_ROLE = "CHEBI:25212";

	private Map<String, Entity> scannedEntityList;
	private boolean doFuzzyScan = true;

	private ChebiWebServiceClient chebiWS;
//	private final String chebiWSUrl = "http://ves-ebi-97:8100/chebi-tools/webservices/2.0/webservice?wsdl";
	private final String chebiWSUrl = "https://www.ebi.ac.uk/webservices/chebi/2.0/webservice?wsdl";

	private ProcessReport parentProcessReport;
	private ProcessReport processReport;

	public ChebiMetaboliteScanner() throws MalformedURLException {
		chebiWS = getChebiWS();
	}

	public ChebiWebServiceClient getChebiWS() {
		if (chebiWS == null)
			try {
				LOGGER.info("Starting a new instance of the ChEBI ChebiWebServiceClient");
				chebiWS = new ChebiWebServiceClient(new URL(chebiWSUrl),new QName("https://www.ebi.ac.uk/webservices/chebi",	"ChebiWebServiceService"));
			} catch (MalformedURLException e) {
				LOGGER.error("Error instanciating a new ChebiWebServiceClient "+ e.getMessage());
			}
		return chebiWS;
	}

	public void setChebiWS(ChebiWebServiceClient chebiWS) {
		this.chebiWS = chebiWS;
	}

	public boolean isDoFuzzyScan() {
		return doFuzzyScan;
	}

	public void setDoFuzzyScan(boolean doFuzzyScan) {
		this.doFuzzyScan = doFuzzyScan;
	}


	public ProcessReport getParnetProcessReport() {
		return parentProcessReport;
	}

	public void setParentProcessReport(ProcessReport processReport) {
		this.parentProcessReport = processReport;
	}

	public Map<String, Entity> scan() throws ChebiWebServiceFault_Exception {
		return scan(CHEBI_METABOLITE_ROLE);
	}

	// Scans chebi looking for any metabolite compound under the specified CHEBI entity.
	public Map<String, Entity> scan(String chebiId) throws ChebiWebServiceFault_Exception {


		ArrayList<String> chebiIds = new ArrayList<String>();
		chebiIds.add(chebiId);

		return scan(chebiIds);

	}

	// Scans chebi looking for any metabolite compound under the specified CHEBI entity collection.
	public Map<String, Entity> scan(Collection<String> chebiIds) throws ChebiWebServiceFault_Exception {

		// Initialize process report
		initializeProcessReport("Scanning chebi for metabolites", chebiIds.size());

		ArrayList<Entity> entities = new ArrayList<Entity>();


		ProcessReport gettingEntities = processReport.addSubProcess("Getting " + (chebiIds.size()==1?chebiIds.iterator().next() + " tree":chebiIds.size() + " entities"), chebiIds.size());
		gettingEntities.started();
		// For each of the id..
		for (String chebiId: chebiIds){

			// Get the complete entity
			Entity entity = getChebiEntity(chebiId);
			entities.add(entity);
			gettingEntities.oneMore();

		}

		gettingEntities.finished();


		Map<String,Entity> result = scanEntities(entities);

		processReport.finished();

		return result;

	}

	private void initializeProcessReport(String processName, int total) {

		// Id there's no parent process report to hang ours...
		if (parentProcessReport == null){

			// ... we create a standalone one
			processReport = new ProcessReport();
			processReport.setProcessName(processName);
			processReport.getPercentage().setTotal(total);

		} else {

			// we create one hanging frome the parent.
			processReport = parentProcessReport.addSubProcess(processName,total);
		}

		processReport.started();

	}

	// Scans chebi looking for any metabolite compound under the specified CHEBI entity.
	private Map<String, Entity> scanEntities(Collection<Entity> entities) throws ChebiWebServiceFault_Exception {


		// Set final list to null.
		scannedEntityList = new HashMap<String, Entity>();

		for (Entity entity: entities){

			addChildrenMetabolitesForChebiID(entity);
		}


		return scannedEntityList;

	}

	/*
	Add all children of the chebiId:
		Is_a_tautomer (if structure) --> L-alanine zwiterion IS_TAUTOMER_OF L-alanine
		Is_a  --> Human metabolite IS_A metabolite, L-Alanine IS_A alanine
		Has_role (if no structure?, ask chebi).  --> adenine HAS_ROLE metabolite

	 */
	private void addChildrenMetabolitesForChebiID(Entity entity) throws ChebiWebServiceFault_Exception {


		if (entity == null){
			LOGGER.warn("addChildrenMetabolitesForChebiID received a null entity");
			return;
		}

		LOGGER.debug("Getting children of " + entity.getChebiId());

		// Check if that Chebi Id is already in our list
		if (scannedEntityList.containsKey(entity.getChebiId())) {
			// This one is already in the list, decrease the total.
			processReport.getPercentage().setTotal(processReport.getPercentage().getTotal()-1);
			return ;
		}


		// Add it to our scanned list to avoid further look ups and endless loops.
		// NOTE: classes will be added too, we may need to clean the list later or have 2 list (scanned and metabolites, or metabolites + classes).
		scannedEntityList.put(entity.getChebiId(),entity);
		processReport.oneMore();

		if (scannedEntityList.size()%100 == 0){
			LOGGER.info(scannedEntityList.size() + " entities retrieved." );
		}

		// Now explore children
		List<LiteEntity> children = new ArrayList<LiteEntity>();


		try {

			List<LiteEntity> structuralChildren = null;

			// If entity hasn't got a structure...
			if (entity.getSmiles() == null) {

				// ... try has_role
				List<LiteEntity> roles = null;
				roles = getChebiIdsRelatives((String) entity.getChebiId(), (RelationshipType) RelationshipType.HAS_ROLE,false);
				children.addAll(roles);

			}

			// If fuzzy search is enabled...
			if (doFuzzyScan) {


				// ... try is_a (is_a alanine, is a alkaloid (<-- no structure for this)
				structuralChildren = getChebiIdsRelatives((String) entity.getChebiId(), (RelationshipType) RelationshipType.IS_A, false);
				children.addAll(structuralChildren);

				// If entity has a structure...
				if (entity.getSmiles() != null) {


					// ... try tautomers
					structuralChildren = getChebiIdsRelatives((String) entity.getChebiId(), (RelationshipType) RelationshipType.IS_TAUTOMER_OF);
					children.addAll(structuralChildren);

					// ... try acids
					structuralChildren = getChebiIdsRelatives((String) entity.getChebiId(), (RelationshipType) RelationshipType.IS_CONJUGATE_ACID_OF);
					children.addAll(structuralChildren);

					// ... try bases
					structuralChildren = getChebiIdsRelatives((String) entity.getChebiId(), (RelationshipType) RelationshipType.IS_CONJUGATE_BASE_OF);
					children.addAll(structuralChildren);

				}

			}


		} catch (ChebiWebServiceFault_Exception e) {
			LOGGER.error("Can't perform fuzzy search of chebiID " + entity + " using chebi WS", e);
		}


		// Increase the cout for the process
		processReport.getPercentage().setTotal(processReport.getPercentage().getTotal()+ children.size());

		// Now we should have all children...
		// Go through the list
		for (LiteEntity child: children){

			// ...if it's in our scanned list...skip it
			if (scannedEntityList.containsKey(child.getChebiId())){
				processReport.oneMore();
				continue;
			}
			// Get the complete entity
			Entity childEntity = getChebiEntity(child.getChebiId());

			if (childEntity == null){

				LOGGER.warn("Chebi WS returned a null entity for " + child.getChebiId());
				continue;
			}

			// Get again all children...
			addChildrenMetabolitesForChebiID(childEntity);
		}

	}

	private Entity getChebiEntity(String chebiId) throws ChebiWebServiceFault_Exception {
		return getChebiWS().getCompleteEntity(chebiId);

	}

	private List<LiteEntity> getChebiIdsRelatives(String chebiId, RelationshipType relType) throws ChebiWebServiceFault_Exception {

		return getChebiIdsRelatives(chebiId, relType, true);

	}

	private List<LiteEntity> getChebiIdsRelatives(String chebiId, RelationshipType relType, boolean onlyStructure) throws ChebiWebServiceFault_Exception {
		LOGGER.debug("Getting relatives for  " + chebiId + ". Relationship: " + relType.name());

		// Get all the children of that chebi id
		LiteEntityList children = getChebiWS().getAllOntologyChildrenInPath(chebiId, relType, onlyStructure);


		// If returned items is 3000, ... we've reach the WS limit. We need all and therefore workaorund this limit
		if (children.getListElement().size()==10000){

			children = getChebiIdRelativesUsingHttpPOST(chebiId, relType,onlyStructure);

		}

		return children.getListElement();
	}

	public LiteEntityList getChebiIdRelativesUsingHttpPOST(String chebiId, RelationshipType relType, boolean onlyStructure) {

		// make the http post request to get the file
		try {
			File tsv =  makePostRequest (chebiId,relType,onlyStructure);

			LiteEntityList liteEntityList = ChebiTools.chebiTsvToLiteEntityList(tsv);

			tsv.delete();

			return liteEntityList;

		} catch (IOException e) {
			LOGGER.error("Can't get " + chebiId + " relatives through post method", e);
		}

		return null;

	}


	public File makePostRequest(String chebiId, RelationshipType relType, boolean onlyStructure) throws IOException {

		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(CHEBI_FILE_EXPORTER_URL);


		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("incomingOntologyQuery[0].value", chebiId));
		params.add(new BasicNameValuePair("incomingOntologyQuery[0].operator", "AND"));
		params.add(new BasicNameValuePair("incomingOntologyQuery[0].category", relType.name().toLowerCase()));
		params.add(new BasicNameValuePair("maxResults", "20000"));
		params.add(new BasicNameValuePair("exportFormat", "tsv"));
		params.add(new BasicNameValuePair("chemicalStructure", onlyStructure?"true":"false"));
		httppost.setEntity(new UrlEncodedFormEntity(params));

		//Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();

		// Prepare output environment.
		File tmpDir = Files.createTempDir();
		// To test..
		//		File tmpDir = new File ("/var/folders/lt/nsnc50_51lj3gvkj6sb6wxm00000gq/T/1411126222453-0/");
		String tsvFileName = chebiId + "_" + relType + "_" + onlyStructure + ".tsv";
		File tsvFile = new File(tmpDir + "/" + tsvFileName);

		// If got something...
		if (entity != null) {
			InputStream is = entity.getContent();

			FileOutputStream fos = new FileOutputStream(tsvFile);
			try {

				byte[] buffer = new byte[4096];              //declare 4KB buffer
				int len;

				//while we have availble data, continue downloading and storing to local file
				while ((len = is.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}


			} finally {
				try {
					if (is != null) {
						is.close();
					}
				} finally {
					if (fos != null) {
						fos.close();
					}
				}
			}
		} else {
			throw new IOException("Couldn't get anything from " + CHEBI_FILE_EXPORTER_URL);
		}
		return tsvFile;
	}


}
