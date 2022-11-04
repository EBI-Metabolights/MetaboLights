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
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.cluster.metadata.MetaData;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.collect.ImmutableList;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.*;
import org.elasticsearch.indices.IndexMissingException;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.referencelayer.model.MetSpecies;
import uk.ac.ebi.metabolights.referencelayer.model.MetaboLightsCompound;
import uk.ac.ebi.metabolights.referencelayer.model.Species;
import uk.ac.ebi.metabolights.repository.model.Entity;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.Organism;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.search.service.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * User: conesa
 * Date: 03/12/14
 * Time: 17:38
 */
public class ElasticSearchService implements SearchService <Entity> {

	private static final String PROPERTIES = "properties";
	private static final String STATUS_FIELD = "studyStatus";
	private static final String USER_NAME_FIELD = "users.userName";
	private static final String STUDY_PUBLIC_RELEASE_DATE = "studyPublicReleaseDate";
	public static final String NO_ESCAPING_CHAR = "'";

	static Logger logger = LoggerFactory.getLogger(ElasticSearchService.class);

	public static final String COMPOUND_TYPE_NAME = "compound";
	public static final String STUDY_TYPE_NAME = "study";
	private  String studyPrefix = "MTBLS";
	private  String compoundPrefix = "MTBLC";

	// Index configurations
	private String indexName = "metabolights";
	private String clusterName = "metabolights";


	// Instances
	private ObjectMapper mapper;
	private TransportClient  client;

	// Shared: to have a cleaner code when generating the mapping.
	private XContentBuilder mapping = null;

	public ElasticSearchService(){


		initialiseElasticSearchClient();
	}
	public ElasticSearchService(String clusterName){

		this.clusterName = clusterName;
		initialiseElasticSearchClient();
	}

	private void initialiseElasticSearchClient() {

		// Initialise JSON deserializer.
		if (mapper == null) {

			mapper= new ObjectMapper();
			mapper.setTimeZone(Calendar.getInstance().getTimeZone());
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		}

		if (client != null) return;

		try {
			Settings settings = ImmutableSettings.settingsBuilder()
					.put("cluster.name", clusterName).build();

			client = new TransportClient(settings);

			client.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));


			logger.info("Connected to index (elasticsearch) server.");

			// If index does not exists..
			if (!doesIndexExists()) {
				// Create it and configure it.
				configureIndex();
			}
		} catch (NoNodeAvailableException e){
			client = null;
			logger.error("Couldn't initialise elasticsearch service." , e);
		} catch (IndexingFailureException e) {
			client = null;
			logger.error("Couldn't configure index." , e);
		}
	}
	public boolean doesIndexExists(){

		IndicesExistsResponse res = client.admin().indices().prepareExists(indexName).execute().actionGet();
		return  res.isExists();
	}

	@Override
	public void resetIndex() throws IndexingFailureException {

		deleteIndex();

		configureIndex();
	}

	@Override
	public void deleteIndex() {
		DeleteIndexResponse rep = null;
		try {
			rep = client.admin().
					indices().
					prepareDelete(indexName).
					execute().
					actionGet();
		}
		catch (IndexMissingException e) {
			// Index not found, fine although might be strange
			logger.warn("Index reset, well, index wasn't found.");

		}
	}


	public void configureIndex() throws IndexingFailureException {


		// Add index
		addIndex();

		// Add study mapping
		addStudyMapping();

		// Add compound mapping
		addCompoundMapping();


	}

	public void addIndex() throws IndexingFailureException {

		final CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(indexName);

		CreateIndexResponse response = createIndexRequestBuilder.execute().actionGet();

		if (!response.isAcknowledged()) {
			throw new IndexingFailureException("Can't create index " + indexName + ": " + response.toString());
		}

		logger.info("Index {} created.", indexName);
	}

	/**
	 * Adds a the compound mapping
	 * @throws IOException
	 */

	public void addCompoundMapping() throws IndexingFailureException {

		try {
			mapping = jsonBuilder().startObject();

				// Add compound mapping root
				mapping.startObject(COMPOUND_TYPE_NAME);

			// Do not allow dynamic properties: strict is too strict, throws an exception
			//mapping.field("dynamic", "false");

				// Timestamp
				addObject("_timestamp", "enabled", true, "store", true, "format", "YYYY-MM-dd hh:mm:ss");

				// Properties configutarion (fields types and storage)
				mapping.startObject(PROPERTIES);

					addObject(STATUS_FIELD, "type", "string", "index", "not_analyzed");

					addObject("accession", "type", "string", "index", "not_analyzed");

					// Collections
					// Organisms
					startObject("organism")
						.startObject(PROPERTIES);
							addObject("organismName", "type", "string", "index", "not_analyzed");
						endObject()
					.endObject();

			    endObject();

		} catch (IOException e) {
			throw new IndexingFailureException("Can't build study mapping for the index.", e);
		}

	addMappingToIndex(mapping, COMPOUND_TYPE_NAME);


}

	/**
	 * Adds a the study mapping
	 * @throws IOException
	 */

	public void addStudyMapping() throws IndexingFailureException {


		// Create the mapping for the studieses mapping
		try {
			mapping = jsonBuilder().startObject();

			// Add study mapping root
			mapping.startObject(STUDY_TYPE_NAME);


				// Do not allow dynamic properties: strict is too strict, throws an exception
				/**
				 * To start allow dynamic mapping. This will cause elastic search to index almost everything
				 * and have a "ugly mapping" with items like this ones:
				 *
				 * "10~label"
				 * "10~labeled extract name"
				 * ...
				 *
				 * If this affects performance we could:
				 *
				 * 1.- Switch off dymanic AND define manually the index (we should include what is missing and want to be searchable (OrganismPart, descriptors, factors, protocols...)
				 * 2.- Leave dynamic on but customize it with templates:
				 *
				 * 		http://www.elasticsearch.org/guide/en/elasticsearch/guide/current/custom-dynamic-mapping.html
				 *      http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-root-object-type.html
				 *
				 */
				//mapping.field("dynamic", "false");

				// Timestamp
				addObject("_timestamp", "enabled", true, "store", true, "format", "YYYY-MM-dd hh:mm:ss");

				//_source configuration
				mapping.startObject("_source")
					.array("excludes", new String[]{"protocols", "sampleTable", "contacts", "studyLocation",
							"assays.assayTable", "assays.assayNumber", "assays.metaboliteAssignment", "assays.fileName",
							"users.apiToken", "users.studies", "users.userVerifyDbPassword", "users.dbPassword",
							"users.listOfAllStatus", "users.affiliationUrl", "users.status", "users.listOfAllStatus", "users.studies",
							"users.joinDate", "users.email", "users.address", "users.userId", "users.role",
							"users.affiliation", "users.curator", "users.reviewer"})
				.endObject()

				// Properties configutarion (fields types and storage)
				.startObject(PROPERTIES);

					addObject("studyPublicReleaseDate", "type", "date", "store", true);

					addObject("studySubmissionDate", "type", "date");

					addObject(STATUS_FIELD, "type", "string", "index", "not_analyzed");

					addObject("obfuscationCode", "type", "string", "index", "not_analyzed");

					addObject("studyIdentifier", "type", "string", "index", "not_analyzed");


					// Collections
					// Organisms
					startObject("organism")
						.startObject(PROPERTIES);
							addObject("organismName", "type", "string", "index", "not_analyzed");
							addObject("organismPart", "type", "string", "index", "not_analyzed");
						endObject()
					.endObject();

					// Assays
					startObject("assays")
						.startObject(PROPERTIES);
							addObject("technology", "type", "string", "index", "not_analyzed");
							addObject("measurement", "type", "string", "index", "not_analyzed");
						endObject()
					.endObject();

			// Users
			startObject("users")
					.startObject(PROPERTIES);
			addObject("userName", "type", "string", "index", "not_analyzed");
			addObject("fullName", "type", "string", "index", "not_analyzed");
			endObject()
					.endObject();

			// Factors
			startObject("factors")
					.startObject(PROPERTIES);
			addObject("name", "type", "string", "index", "not_analyzed");
			endObject()
					.endObject();

			startObject("descriptors")
					.startObject(PROPERTIES);
			addObject("description", "type", "string", "index", "not_analyzed");
			endObject()
					.endObject();

			// Validations details
			startObject("validations")
					.startObject(PROPERTIES)
					.startObject("entries")
					.startObject(PROPERTIES);
			addObject("statusExt", "type", "string", "index", "not_analyzed" );
			endObject();
			endObject();
			endObject()
					.endObject()
					.endObject() // End of Study Properties
					.endObject(); // End of study

			mapping.endObject();


		} catch (IOException e) {
			throw new IndexingFailureException("Can't build study mapping for the index.", e);
		}

		addMappingToIndex(mapping, STUDY_TYPE_NAME);


	}

	private void addMappingToIndex(XContentBuilder newMapping, String documentType) throws IndexingFailureException {

		// Put the mapping
		PutMappingRequestBuilder putMappingRequestBuilder = client.admin().indices().preparePutMapping(indexName).setType(documentType);

		putMappingRequestBuilder.setSource(newMapping);

		PutMappingResponse response = putMappingRequestBuilder.execute().actionGet();

		if (response.isAcknowledged()) {
			logger.info(indexName + "/" + documentType + " created");
		} else {
			throw new IndexingFailureException("Can't add study mapping to the index: " +  response.toString());
		}
	}


	private XContentBuilder endObject() throws IOException {
		return mapping.endObject();
	}

	private XContentBuilder startObject(String name) throws IOException {
		return mapping.startObject(name);
	}

	private void addObject(String name, Object... attributes) throws IOException {

		mapping.startObject(name);

		for (int i = 0; i < attributes.length-1; i = i+2) {

			mapping.field((String) attributes[i], attributes[i+1]);
		}
		mapping.endObject();
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

	public String getStudyPrefix() {
		return studyPrefix;
	}

	public void setStudyPrefix(String studyPrefix) {
		this.studyPrefix = studyPrefix;
	}

	public String getCompoundPrefix() {
		return compoundPrefix;
	}

	public void setCompoundPrefix(String compoundPrefix) {
		this.compoundPrefix = compoundPrefix;
	}


	@Override
	public ArrayList<String> getStatus() {

		ArrayList<String> status = new ArrayList<>();

		status.add("Client " + (client == null?"not ":"") + " instantiated");
		status.add( "Cluster name: " + clusterName );

		if (client != null) {
			status.add( "Transport addresses");
			for (TransportAddress transportAddress : client.transportAddresses()) {

				status.add( "* " + transportAddress.toString());
			}


			// Test for nodes connected....
			ImmutableList<DiscoveryNode> discoveryNodes = client.connectedNodes();

			status.add("Nodes connection:");

			if (discoveryNodes.isEmpty()) {
				status.add("Client is not connected to any node. Check elastic search is running.");

				return status;

			} else {
				for (DiscoveryNode discoveryNode : discoveryNodes) {
					status.add(discoveryNode.toString());
				}
			}


			// Get index status
			status.add("Index status:");
			status.add( "Does index exists? " + doesIndexExists());

			MappingMetaData mapping = getMapping(STUDY_TYPE_NAME);
			status.add( "Does study mapping exists? " + (mapping != null));
			if (mapping != null) {
				status.add( "Studies mapping: " + mapping.source() );
			}



			mapping = getMapping(COMPOUND_TYPE_NAME);
			status.add( "Does compounds mapping exists? " + (mapping != null));
			if (mapping != null) {
				status.add( "Compounds mapping " + (mapping.source()) );
			}


		}

		return status;
	}

	@Override
	public void delete(String id) throws IndexingFailureException {

		String documentType = getDocumentTypeById(id);

		if (documentType !=null) {

			// Kind of a hack: If the there is a * we assume you want to delete all documents of that type.
			// Example: MTBLS*....warning: MTBLS12* will delete all documents as well and not only those that match the pattern.
			if (id.indexOf("*") != -1) {


				deleteDocumentType(documentType);


			// Delete by id
			} else {

				DeleteResponse response = client.prepareDelete(indexName, documentType, id).execute().actionGet();

				if (!response.isFound()) {
					throw new IndexingFailureException("Elastic search failed deleting id " + id + ", message: " + response.toString());
				}

			}
		} else {

			throw new IndexingFailureException("Elastic search couldn't match the document type of " + id + ": expecting " + studyPrefix + " or " + compoundPrefix + " prefixes.");
		}
	}

	@Override
	public void delete(String[] ids) throws IndexingFailureException {

		for (String id : ids) {
			delete(id);
		}

	}



		public void deleteStudies() throws IndexingFailureException {

		deleteDocumentType(STUDY_TYPE_NAME);

	}

	public void deleteCompounds() throws IndexingFailureException {

		deleteDocumentType(COMPOUND_TYPE_NAME);

	}


	private DeleteMappingResponse deleteDocumentType(String documentType) throws IndexingFailureException {

		DeleteMappingRequest deleteMappingRequest = new DeleteMappingRequest(indexName).types(documentType);

		DeleteMappingResponse response = client.admin().indices().deleteMapping(deleteMappingRequest).actionGet();

		if (!response.isAcknowledged()) {
			throw new IndexingFailureException("Elastic search failed deleting all documents of type  " + documentType + ":  " + response.getContext().toString());
		}

		return response;
	}


	private String getDocumentTypeById(String id) {


		if (isAStudy(id)) {
			return STUDY_TYPE_NAME;
		} else if (isACompound(id)){
			return COMPOUND_TYPE_NAME;
		} else {

			logger.warn("Can't resolve elastic search document type for id " + id + "  trying search.");
			return searchDocumentTypeById(id);

		}

	}

	private String searchDocumentTypeById (String identifier){

		SearchQuery query = new SearchQuery(NO_ESCAPING_CHAR + "_id:" + identifier);
		SearchUser user = new SearchUser();
		user.setAdmin(true);
		query.setUser(user);
		SearchResponse result = searchToElasticSearchResponse(query);

		// If there are hits...
		if (result.getHits().getTotalHits() > 0) {

			return result.getHits().iterator().next().getType();

		} else {

			// Nothing hit
			logger.info("Nothing was hit by the query: " + query.toString());

			// Default to Study!
			return STUDY_TYPE_NAME;

		}
	}


	private boolean isAStudy(String id) {
		return id.indexOf(studyPrefix) == 0;
	}
	private boolean isACompound(String id) {
		return id.indexOf(compoundPrefix) == 0;
	}

	@Override
	public SearchResult<Entity> search(SearchQuery query) {


		SearchResponse response = searchToElasticSearchResponse(query);


		if (response.getHits().getTotalHits() == 0) {
			// Nothing hit
			logger.info("Nothing was hit by the query: " + query.toString());
		}

		return convertElasticSearchResponse2SearchResult(response, query);

	}

	private SearchResponse searchToElasticSearchResponse(SearchQuery query) {

		// Initialise the client
		initialiseElasticSearchClient();

		// Get a SearchRequest
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(indexName);

		// Convert our Model query into an elastic search query
		QueryBuilder queryBuilder = queryToQueryBuilder(query, searchRequestBuilder);


		// Set the query
		searchRequestBuilder.setQuery(queryBuilder);

		// Add or not facets
		considerFacets(query, searchRequestBuilder);

		// Set pagination
		// Elastic search first element starts at 0
		setPagination(query, searchRequestBuilder);

		return searchRequestBuilder.execute().actionGet();
	}

	private void setPagination(SearchQuery query, SearchRequestBuilder searchRequestBuilder) {

		if (query.getPagination() == null) {
			searchRequestBuilder.setFrom(0);
			searchRequestBuilder.setSize(Integer.MAX_VALUE);
		} else {
			searchRequestBuilder.setFrom(query.getPagination().getFirstPageItemNumber() - 1);
			searchRequestBuilder.setSize(query.getPagination().getPageSize());
		}
	}

	private QueryBuilder queryToQueryBuilder(SearchQuery query, SearchRequestBuilder searchRequestBuilder){

		// Convert the query to an elasticsearch query
		QueryBuilder queryBuilder = convertSearchQueryToQueryBuilder(query,searchRequestBuilder);

		// Add the filters
		queryBuilder = getFilterElasticSearchFilter(query, queryBuilder);

		return queryBuilder;

	}



	// Add facets to the query
	private void considerFacets(SearchQuery query, SearchRequestBuilder searchRequestBuilder){

		// if there isn't  any facet
		if (query.getFacets() == null) {

			// No facets?
			logger.warn("No facets found in the query. No agrregations added to elastic search request");
			return;
		}

		//To store facet filters
		ArrayList<FilterBuilder> facetFilters = new ArrayList<>();

		// For each facet
		for (Facet facet : query.getFacets()) {

			// Add an aggregation
			addAggregationFromFacet(searchRequestBuilder, facet);


			// Get a filter for the facet
			FilterBuilder facetFilter = getFilterFromFacet(facet);

			// Add it if not null
			if (facetFilter != null) {
				facetFilters.add(facetFilter);
			}

		}

		// If there isn't any filter
		if  (facetFilters.size()==0) {

			//no filter to apply
			logger.debug("Empty facets: Nothing to filter with any facet");

		} else if (facetFilters.size()==1) {

			// Only one filter
			searchRequestBuilder.setPostFilter(facetFilters.get(0));

		// More than 1 facet filter
		} else {
			// Create an and filter
			FilterBuilder[] filterBuilders =  facetFilters.toArray(new FilterBuilder[facetFilters.size()]);
			searchRequestBuilder.setPostFilter(FilterBuilders.andFilter(filterBuilders));
		}

	}

	private FilterBuilder getFilterFromFacet(Facet facet) {

		ArrayList<FilterBuilder> termFilters = new ArrayList<FilterBuilder>();

		// Loop through the lines
		for (FacetLine facetLine : facet.getLines()) {

			if (facetLine.isChecked()) {
				FilterBuilder termFilter = facetLineToTermFilter(facetLine, facet.getName());

				termFilters.add(termFilter);
			}

		}

		// If there isn't any filter
		if  (termFilters.size()==0) {

			//...Facet define for aggregation but no filter yet (empty facet)
			logger.debug("Empty facet: " + facet.getName() + ". Nothing to filter with this facet");
			return null;

		} else if (termFilters.size() == 1) {

			// Only a termFilter
			return termFilters.get(0);

			// More than 1 term filter
		} else {
			// Create an or filter and return it

			FilterBuilder[] filterBuilders = termFilters.toArray(new FilterBuilder[termFilters.size()]);
			return FilterBuilders.orFilter(filterBuilders);
		}

	}

	private FilterBuilder facetLineToTermFilter(FacetLine facetLine, String field) {
		return FilterBuilders.termFilter(field, facetLine.getValue());

	}

	private void addAggregationFromFacet(SearchRequestBuilder searchRequestBuilder, Facet facet) {

		TermsBuilder facetGroup = new TermsBuilder(facet.getName());

		// So far name matches the field but needs to change and map pretty name with field name
		facetGroup.field(facet.getName());

		// No limit the size...
		facetGroup.size(0);

		searchRequestBuilder.addAggregation(facetGroup);
	}

	/**
	 * Only public studies and owned...or all if admin
	 *
	 * Sample query
	 *
	{
		"query" : {
			"filtered" : {
				"query": {
					"query_string" : {
						"query" : "profiling"
					}
				},
				"filter" : {
					"or": {
						"filters": [
							{
								"term" : { "publicStudy" : true }
							},
							{
								"term" : { "users.userName" : "owner" }
							}
						]
					}
				}
			}
		}
	}
	**/
	private QueryBuilder getFilterElasticSearchFilter(SearchQuery query, QueryBuilder esSearchQuery) {


		FilterBuilder filter = null;

		// User dependent filter
		// If there's no user ...
		if (query.getUser() == null || !query.getUser().isAdmin()) {

			// ... only public studies are accesible.

					filter = FilterBuilders.termFilter(STATUS_FIELD, Study.StudyStatus.PUBLIC.name());

			// If user not null...
			if (query.getUser() != null) {

				// Build the filter by study status or owner
				filter = FilterBuilders.orFilter(
						filter,
						FilterBuilders.termFilter(USER_NAME_FIELD, query.getUser().getId())
				);
			}

		}

		// Facets filter...
		//facetsToFilters(query, filter);


		// If is admin (no filter)..
		if (filter == null) return esSearchQuery;

		// Otherwise crete a filteredQuery
		return QueryBuilders.filteredQuery(esSearchQuery,filter);

	}

	// Plural, convert all facets to elasticsearch filters
	private void facetsToFilters(SearchQuery query, FilterBuilder filter) {

		FilterBuilder allFacetsFilter = null;

		// Loop through each facet
		for (Facet facet : query.getFacets()) {

			FilterBuilder facetFilter = null;

			// Filter for all elements within same facet (OR filter)
			ArrayList<FilterBuilder> filters = facetToFilters(facet);

			// For each filter
			for (FilterBuilder lineFilter : filters) {

				// if it's the first one
				if (facetFilter == null){
					facetFilter = lineFilter;
				} else {
					// Create an or filter
					filter = FilterBuilders.orFilter(
							filter,
							lineFilter);
				}
			}

			// Compose all Facets filter
			// if it's the first one
			if (allFacetsFilter == null){
				allFacetsFilter = facetFilter;
			} else {
				// Create an AND filter
				allFacetsFilter = FilterBuilders.andFilter(
						facetFilter,
						allFacetsFilter);
			}

		}

		//if there is any filter
		if (allFacetsFilter != null) {
			filter = FilterBuilders.andFilter(allFacetsFilter, filter);
		}

	}

	private ArrayList<FilterBuilder> facetToFilters(Facet facet) {

		ArrayList<FilterBuilder> filters = new ArrayList();

		for (FacetLine facetLine : facet.getLines()) {
			// If line is checked...
			if (facetLine.isChecked()) {

				FilterBuilder term = FilterBuilders.termFilter(facet.getName(), facetLine.getValue());

				filters.add(term);

			}

		}

		return filters;

	}

	private QueryBuilder convertSearchQueryToQueryBuilder(SearchQuery query, SearchRequestBuilder searchRequestBuilder) {

		QueryBuilder queryBuilder;

		if (query.getText()==null || query.getText().isEmpty()) {
			queryBuilder = QueryBuilders.matchAllQuery();

			// In this case specify an order..
			searchRequestBuilder.addSort(STUDY_PUBLIC_RELEASE_DATE, SortOrder.DESC);

		} else {
			//So far let's do a plain text search.
			queryBuilder = textToQueryBuilder(query);
		}

		return queryBuilder;
	}


	/**
	 * Generates a boolean query with the text and all the "boosting fields".
	 * https://www.elastic.co/guide/en/elasticsearch/guide/current/_boosting_query_clauses.html
	 * @param query
	 * @return
	 */
	private QueryBuilder textToQueryBuilder(SearchQuery query) {

		QueryBuilder textQuery =  QueryBuilders.queryString(escapeText(query.getText()));

		// Get a boolean query with the querystring to start with
		BoolQueryBuilder booleanQuery = QueryBuilders.boolQuery().must(textQuery);

		// Add the "boosting fields"
		for (Booster booster :query.getBoosters()){

			TermQueryBuilder boostingField = QueryBuilders.termQuery(booster.getFieldName(), query.getText());
			boostingField.boost(booster.getBoost());

			booleanQuery.should(boostingField);

		}


		return booleanQuery;
	}

	private String escapeText(String text) {

		// Some queries come with fields like:
		// _id:MTBLS143
		// In this case we want to avoid escaping characters...
		// As a convention,..if the query starts with ' we will not escape it

		if (text.startsWith(NO_ESCAPING_CHAR)){

			return text.replaceFirst(NO_ESCAPING_CHAR,"");
		} else {
			return org.apache.lucene.queryparser.classic.QueryParser.escape(text);
		}

	}

	private SearchResult<Entity> convertElasticSearchResponse2SearchResult(SearchResponse esResponse, SearchQuery query){

		SearchResult<Entity> searchResult = new SearchResult<Entity>();

		// Set the query
		searchResult.setQuery(query);

		convertHits2Entities(esResponse, searchResult);

		fillPagination(esResponse,searchResult);

		fillFacets(esResponse,searchResult);

		return searchResult;

	}

	private void fillFacets(SearchResponse esResponse, SearchResult<Entity> searchResult) {

		Aggregations aggregations = esResponse.getAggregations();

		// If theres no aggregations
		if (aggregations == null) return;

		for (Aggregation aggregation : aggregations) {
			StringTerms terms = (StringTerms) aggregation;
			Facet facet = getFacetForAggregation(aggregation, searchResult);

			if (facet != null) {
				fillFacetWithAggregation(facet, terms);
			}

		}

	}

	private void fillFacetWithAggregation(Facet facet, StringTerms aggregation) {

		for (Terms.Bucket bucket : aggregation.getBuckets()) {

			fillFacetLineFromBucket (bucket,facet);

		}
	}

	private void fillFacetLineFromBucket(Terms.Bucket bucket, Facet facet) {

		for (FacetLine facetLine : facet.getLines()) {
			if (facetLine.getValue().equals(bucket.getKey())) {
				facetLine.setCount(bucket.getDocCount());
				return;
			}
		}

		// Not found?
		FacetLine newLine = new FacetLine(bucket.getKey());
		newLine.setCount(bucket.getDocCount());
		facet.getLines().add(newLine);

	}


	private Facet getFacetForAggregation(Aggregation aggregation, SearchResult<Entity> searchResult) {

		for (Facet facet : searchResult.getQuery().getFacets()) {

			if (facet.getName().equals(aggregation.getName())){
				return facet;
			}
		}

		logger.warn("facet not found for aggregation" + aggregation.getName());
		return null;

	}

	private void fillPagination(SearchResponse esResponse, SearchResult<Entity> searchResult) {

		SearchQuery query = searchResult.getQuery();
		Pagination pagination =query.getPagination();

		if (pagination != null) {
			pagination.setItemsCount((int) esResponse.getHits().getTotalHits());
		}

	}

	private void convertHits2Entities(SearchResponse esResponse, SearchResult<Entity> searchResult) {


		for (SearchHit hit:esResponse.getHits()){
			addLiteEntity(hit, searchResult);
		}
	}

	private void addLiteEntity(SearchHit hit, SearchResult<Entity> searchResult){

		try {

			Entity entity = null;

			if (hit.getType().equals(STUDY_TYPE_NAME)){
				entity = hit2Study(hit);
			}

			if (hit.getType().equals(COMPOUND_TYPE_NAME)){
				entity = hit2Compound(hit);
			}
			

			if (entity != null)
				searchResult.getResults().add(entity);

		} catch (IndexingFailureException e) {

			searchResult.report("Can't convert hit: " + hit.getId() + " to LiteEntity");
			logger.error("Conversion to lite entity error", e);
		}

	}

	private MetaboLightsCompound hit2Compound(SearchHit hit) throws IndexingFailureException {

		MetaboLightsCompound compound = new MetaboLightsCompound();

		// It's using GMT to deserialize, but JVM Timezone to serialise? Force JVM time zone here.

		try {
			compound =  mapper.readerForUpdating(compound).readValue(hit.getSourceAsString());


		} catch (IOException e) {
			throw new IndexingFailureException("Can't convert elastic search hit to Compound class: " + e.getMessage(),e);
		}

		return compound;

	}

	private Entity hit2Study(SearchHit hit) throws IndexingFailureException {

		LiteStudy study = new LiteStudy();

		// It's using GMT to deserialize, but JVM Timezone to serialise? Force JVM time zone here.

		try {
			study =  mapper.readerForUpdating(study).readValue(hit.getSourceAsString());


		} catch (IOException e) {
			throw new IndexingFailureException("Can't convert elastic search hit to StudyLite class: " + e.getMessage(),e);
		}

		return study;
	}

	@Override
	public void index(Entity entity) throws IndexingFailureException {

		// If index does not exists, this could be cached for performance reasons.
		if (!doesIndexExists()) {
			configureIndex();
		}

		if (entity instanceof Study) {
			indexStudy((Study) entity);
		} else if (entity instanceof MetaboLightsCompound){
			indexCompound((MetaboLightsCompound) entity);
		} else {

			throw new IndexingFailureException("Don't know ho to index the entity. Class:" + entity.getClass().getCanonicalName());
		}
	}

	private void indexCompound(MetaboLightsCompound compound) throws IndexingFailureException {

		String id=compound.getAccession();

		// Fill organism collection, to be used for the facets
		fillOrganisms(compound);


		String compoundS = entity2String(compound);
		IndexResponse response = client.prepareIndex(indexName, COMPOUND_TYPE_NAME, id).setSource(compoundS).execute().actionGet();

	}

	private void fillOrganisms(MetaboLightsCompound compound) {

		ArrayList<Organism> organisms = new ArrayList<>();

		for (MetSpecies metSpecies : compound.getMetSpecies()) {

			Species specie = metSpecies.getSpecies();

			Organism organism = new Organism();

			organism.setOrganismName(specie.getSpecies());

			organisms.add(organism);

		}


		compound.setOrganism(organisms);

	}

	private void indexStudy(Study study) throws IndexingFailureException {

		String id=study.getStudyIdentifier();
		String studyS = study2String(study);
		IndexResponse response = client.prepareIndex(indexName, STUDY_TYPE_NAME, id).setSource(studyS).execute().actionGet();

	}

	private String study2String(Study study) throws IndexingFailureException {
		return entity2String(study);
	}

	private String entity2String(Object entity) throws IndexingFailureException {

		try {
			return mapper.writeValueAsString(entity);

		} catch (com.fasterxml.jackson.core.JsonProcessingException e) {
			throw new IndexingFailureException("Can't serialize a " + entity.getClass().getCanonicalName() + " to a JSON String" , e);
		}
	}

	public boolean doesMappingExists(String documentTypeName) {



		return (getMapping(documentTypeName)!= null);
	}

	public MappingMetaData getMapping(String documentTypeName) {
		ClusterStateResponse resp = client.admin().cluster().prepareState().execute().actionGet();

		MetaData metadata = resp.getState().getMetaData();

		if (metadata.index(indexName) != null){
			return  resp.getState().getMetaData().index(indexName).mapping(documentTypeName);
		} else {
			return null;
		}

	}

}
