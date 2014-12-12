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

package uk.ac.ebi.metabolights.search.service.imp.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.search.service.IndexingFailureException;
import uk.ac.ebi.metabolights.search.service.SearchQuery;
import uk.ac.ebi.metabolights.search.service.SearchResult;
import uk.ac.ebi.metabolights.search.service.SearchService;

/**
 * User: conesa
 * Date: 03/12/14
 * Time: 17:38
 */
public class ElasticSearchService implements SearchService <Object, LiteEntity> {

	static Logger logger = LoggerFactory.getLogger(ElasticSearchService.class);

	private static final String STUDY_TYPE_NAME = "study";

	private TransportClient  client;
	private String indexName = "metabolights";

	private String clusterName = "conesa";

	public ElasticSearchService(){
		initialiseElasticSearchClient();
	}

	private void initialiseElasticSearchClient() {


		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", clusterName).build();

		client =    new TransportClient(settings);

		client.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}


	@Override
	public boolean getStatus() {

		return (client != null);
	}

	@Override
	public void delete(String id) throws IndexingFailureException {

		String documentType = getDocumentTypeById(id);

		if (documentType !=null) {
			DeleteResponse response = client.prepareDelete(indexName, documentType, id).execute().actionGet();
			if (!response.isFound()){
				throw new IndexingFailureException("Elastic search failed deleting id " + id);
			}
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
	public SearchResult<LiteEntity> search(SearchQuery query) {




		return null;
	}

	@Override
	public void index(Object entity) throws IndexingFailureException {

		indexStudy((Study) entity);
	}

	private void indexStudy(Study study) throws IndexingFailureException {

		String id=study.getStudyIdentifier();
		String studyS = study2String(study);
		IndexResponse response = client.prepareIndex(indexName, STUDY_TYPE_NAME, id).setSource(studyS).execute().actionGet();

	}
	private String study2String(Study study) throws IndexingFailureException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new GuavaModule());

		try {
			return mapper.writeValueAsString(study);

		} catch (com.fasterxml.jackson.core.JsonProcessingException e) {
			throw new IndexingFailureException(e);
		}
	}


}
