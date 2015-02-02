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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.indices.IndexMissingException;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.search.service.IndexingFailureException;
import uk.ac.ebi.metabolights.search.service.SearchQuery;
import uk.ac.ebi.metabolights.search.service.SearchResult;
import uk.ac.ebi.metabolights.search.service.SearchService;
import uk.ac.ebi.metabolights.search.service.imp.es.resultsmodel.LiteStudy;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

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

	private String clusterName = "metabolights";

	public ElasticSearchService(){
		initialiseElasticSearchClient();
	}
	public ElasticSearchService(String clusterName){

		this.clusterName = clusterName;
		initialiseElasticSearchClient();
	}

	private void initialiseElasticSearchClient() {


		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", clusterName).build();

		client =    new TransportClient(settings);

		client.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

		// If index does not exists..
		if (!doesIndexExists()) {
			// Create it and configure it.
			configureIndex();
		}
	}
	public boolean doesIndexExists(){

		IndicesExistsResponse res = client.admin().indices().prepareExists(indexName).execute().actionGet();
		return  res.isExists();
	}

	@Override
	public void resetIndex() throws IndexingFailureException {

		DeleteIndexResponse rep = null;
		try {
			rep = client.admin().
					indices().
					prepareDelete(indexName).
					execute().
					actionGet();
		}
		catch (IndexMissingException e) {
			// Index not found, fine although maigth be strange
			logger.warn("Index reset, well, index wasn't found.");

		}

		configureIndex();
	}


	private void configureIndex() {

		logger.info("Configuring " + indexName + "  index.");

		XContentBuilder mapping = null;
		try {

			final CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(indexName);

			// Create the mapping for the studies
			final XContentBuilder mappingBuilder = jsonBuilder()
					.startObject()
						.startObject(STUDY_TYPE_NAME)
							//_source configuration
							.startObject("_source")
								.array("excludes", new String[]{"assays", "protocols", "sampleTable", "contacts", "obfuscationCode",
										"users.apiToken", "users.studies", "users.userVerifyDbPassword", "users.dbPassword",
										"users.listOfAllStatus", "users.lastName","users.affiliationUrl","users.status",
										"users.joinDate","users.email", "users.address", "users.userId", "users.role", "users.affiliation", "users.firstName"})
							.endObject()

							// Timestamp
							.startObject("_timestamp")
								.field("enabled", true)
								.field("store", true)
								.field("format", "YYYY-MM-dd hh:mm:ss")
							.endObject()

							// Properties configutarion (fields types and storage)
							.startObject("properties")
								.startObject("studyPublicReleaseDate")
									.field("type", "date")
								.endObject()
								.startObject("studySubmissionDate")
									.field("type", "date")
								.endObject()
								.startObject("organism.organismName")
									.field("type", "string")
									.field("index", "not_analyzed")
								.endObject()
								.startObject("assays.technology")
									.field("type", "string")
									.field("index", "not_analyzed")
								.endObject()
								.startObject("assays.measurement")
									.field("type", "string")
									.field("index", "not_analyzed")
								.endObject()

								.startObject("publicStudy")
									.field("type", "boolean")
									.field("index", "not_analyzed")
								.endObject()
							.endObject()
							// End of properties

// Templates....in case of any use.
//							.startArray("dynamic_templates")
//								.startObject()
//									.startObject("assays_template")
//										.field("match", "assays")
//										.startObject("mapping")
//											.field("type","object")
//											.field("store","no")
//											//.field("index", "yes")
//										.endObject()
//									.endObject()
//								.endObject()
//							.endArray() // End of templates
						.endObject()
					.endObject();

			// Add the mapping for studies
			createIndexRequestBuilder.addMapping(STUDY_TYPE_NAME, mappingBuilder);
			logger.info(indexName + "/" + STUDY_TYPE_NAME + " created using this configuration: " + mappingBuilder.string());

			// Execute it
			createIndexRequestBuilder.execute().actionGet();

		} catch (IOException e) {
			logger.error("Can't create " + indexName + " index in eleasticsearch.", e);
		}
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


		if (isAStudy(id)) {
			return STUDY_TYPE_NAME;
//		} else if (id.indexOf("MTBLC") == 1){
//			return "compound";
		} else {
			logger.warn("Can't resolve elastic search document type for id " + id );
			return null;
		}

	}

	private boolean isAStudy(String id) {
		return id.indexOf("MTBLS") == 0;
	}

	@Override
	public SearchResult<LiteEntity> search(SearchQuery query) {


		// Convert our Model query into an elastic search query

		//So far let's do a plain text search.
		QueryBuilder queryBuilder = QueryBuilders.queryString(query.getText());

		SearchResponse response = client.prepareSearch(indexName).setQuery(queryBuilder).execute().actionGet();

		if (response.getHits().getTotalHits()==0){
			// Nothing hit
			logger.info("Nothing was hit by the query: " + query.toString());
		}

		return convertElasticSearchResponse2SearchResult(response);
	}

	private SearchResult<LiteEntity> convertElasticSearchResponse2SearchResult(SearchResponse esResponse){

		SearchResult<LiteEntity> searchResult = new SearchResult<LiteEntity>();


		convertHits2Entities(esResponse, searchResult);

		return searchResult;

	}

	private void convertHits2Entities(SearchResponse esResponse, SearchResult<LiteEntity> searchResult) {


		for (SearchHit hit:esResponse.getHits()){
			addLiteEntity(hit, searchResult);
		}
	}

	private void addLiteEntity(SearchHit hit, SearchResult<LiteEntity> searchResult){

		try {

			LiteEntity liteEntity = null;

			if (isAStudy(hit.getId())){
				liteEntity = hit2Study(hit);
			}

			if (liteEntity != null)
				searchResult.getResults().add(liteEntity);

		} catch (IndexingFailureException e) {

			searchResult.report("Can't convert hit: " + hit.getId() + " to LiteEntity");
			logger.error("Conversion to liteentity error", e);
		}



	}

	private LiteEntity hit2Study(SearchHit hit) throws IndexingFailureException {

		LiteStudy study = new LiteStudy();

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new GuavaModule());
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		try {
			study =  mapper.readerForUpdating(study).readValue(hit.getSourceAsString());


		} catch (IOException e) {
			throw new IndexingFailureException("Can't convert elastic search hit to StudyLite class: " + e.getMessage(),e);
		}

		return study;
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
			throw new IndexingFailureException("Can't serialize a study to a JSON String" , e);
		}
	}


}
