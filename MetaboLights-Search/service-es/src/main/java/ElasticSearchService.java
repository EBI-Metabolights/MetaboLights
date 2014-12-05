/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Dec-03
 * Modified by:   conesa
 *
 *
 * Copyright 2014 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.search.service.IndexingFailureException;
import uk.ac.ebi.metabolights.search.service.SearchQuery;
import uk.ac.ebi.metabolights.search.service.SearchResult;
import uk.ac.ebi.metabolights.search.service.SearchService;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * User: conesa
 * Date: 03/12/14
 * Time: 17:38
 */
public class ElasticSearchService extends SearchService <Study> {


	private static final String STUDY_TYPE_NAME = "study";
	Node node;
	Client client;
	private String indexName = "metabolights";


	private void initialiseNode() {
		if (node == null) {
			node = nodeBuilder().node();
			client = node.client();
			logger.info("Instantiating Elastic search Node and client");
		}
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}


	@Override
	public boolean getStatus() {


		initialiseNode();
		return (node == null);
	}

	@Override
	public void delete(String id) throws IndexingFailureException {

		String documentType = getDocumentTypeById(id);

		if (documentType !=null) {
			DeleteResponse response = client.prepareDelete(indexName, documentType, id).execute().actionGet();

		}
	}

	private String getDocumentTypeById(String id) {


		if (id.indexOf("MTBLS") == 1) {
			return STUDY_TYPE_NAME;
//		} else if (id.indexOf("MTBLC") == 1){
//			return "compound";
		} else {
			logger.warn("Can't resolve elastic search document type for id " + id );
			return null;
		}

	}

	@Override
	public SearchResult<Study> search(SearchQuery query) {
		return null;
	}

	@Override
	protected void indexThis(Study indexEntity) throws IndexingFailureException {

		String studyS = study2JSON(indexEntity);

		client.prepareIndex(indexName, STUDY_TYPE_NAME,indexEntity.getStudyIdentifier()).setSource(studyS);

	}

	private String study2JSON(Study study) throws IndexingFailureException {

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new GuavaModule());

		try {
			String studyString = mapper.writeValueAsString(study);

			return studyString;

		} catch (JsonProcessingException e) {

			logger.error("Can't convert Study into a Json string");
			throw new IndexingFailureException(e);
		}


	}

	@Override
	protected void addEntityConverters() {

	}
}
