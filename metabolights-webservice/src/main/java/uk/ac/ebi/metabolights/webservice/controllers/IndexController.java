/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Feb-10
 * Modified by:   conesa
 *
 *
 * Copyright 2015 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.webservice.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.referencelayer.model.Compound;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import uk.ac.ebi.metabolights.repository.dao.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.search.service.IndexingFailureException;
import uk.ac.ebi.metabolights.search.service.SearchQuery;
import uk.ac.ebi.metabolights.search.service.SearchResult;
import uk.ac.ebi.metabolights.search.service.SearchService;
import uk.ac.ebi.metabolights.search.service.imp.es.ElasticSearchService;
import uk.ac.ebi.metabolights.webservice.services.ModelObjectFactory;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("index")
@PreAuthorize( "hasRole('ROLE_SUPER_USER')")
public class IndexController extends BasicController {

	// Can we share the service among controllers? Let's try.
	public static SearchService searchService = new ElasticSearchService();
	private StudyDAO _studyDAO;

	private StudyDAO getStudyDAO() throws DAOException {

		if (_studyDAO == null){
			_studyDAO = DAOFactory.getInstance().getStudyDAO();
		}

		return _studyDAO;

	}

	@RequestMapping(value = "{studyIdentifier:" + StudyController.METABOLIGHTS_ID_REG_EXP +"}", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse<String> indexStudyUrl(@PathVariable("studyIdentifier") String studyIdentifier) throws DAOException {

		logger.info("Requesting " + studyIdentifier + " indexing to the webservice");

		String message = null;

		RestResponse<String> response = new RestResponse<String>();

		// Get all the studies
		User user = getUser();

		try {

			indexStudy(studyIdentifier, user.getApiToken());

			response.setMessage(studyIdentifier + " indexed successfully.");
			logger.info(response.getMessage());

		} catch (IndexingFailureException e) {
			message = "Can't index study " + studyIdentifier + ". " + e.getMessage();

		} catch (DAOException e) {
			message = "Can't retrieve study (DAOException) " + studyIdentifier + ". " + e.getMessage();

		} catch (IsaTabException e) {
			message = "Can't retrieve study (IsaTabException) " + studyIdentifier + ". " + e.getMessage();
		} finally {

			if (message != null) {
				logger.warn(message);
				response.setMessage(message);
			}
		}

		return response;

	}


	@RequestMapping(value = "reindex", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse<String> reindex() throws DAOException {

		logger.info("full reindex requested to the webservice");

		RestResponse<String> response = new RestResponse<String>();

		// Get all the studies
		User user = getUser();
		List<String> accessions = getStudyDAO().getList(user.getApiToken());

		// Reset the index
		try {
			reset();

		} catch (IndexingFailureException e) {
			response.setMessage("Couldn't reset the index while indexing");
			response.setErr(e);
			response.setContent(e.getMessage());

			logger.error(response.getMessage(), e);

			return  response;
		}



		long indexed = 0;

		for (String accession : accessions) {

			String message = null;

			try {

				indexStudy(accession, user.getApiToken());

				indexed++;

			} catch (IndexingFailureException e) {
				message = "Can't index study " + accession + ". " + e.getMessage();

			} catch (DAOException e) {
				message = "Can't retrieve study (DAOException) " + accession + ". " + e.getMessage();

			} catch (IsaTabException e) {
				message = "Can't retrieve study (IsaTabException) " + accession + ". " + e.getMessage();
			} finally {
				if (message != null) {
					logger.warn(message);
					response.setMessage(response.getMessage() + message + "\n");
				}
			}

		}

		if (indexed == accessions.size()) {
			response.setContent("Indexing finished successfully. " + accessions.size() + " indexed. All");
		} else {
			response.setContent("Indexing finished with errors. " + indexed + " indexed out of " + accessions.size());
		}

		return response;

	}


	@RequestMapping(value = "compounds", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse<ArrayList<String>> indexCompounds() throws DAOException, IsaTabException {

		logger.info("Requesting all compound to be indexed to the webservice");

		List<String> ids = ModelObjectFactory.getAllCompoundsId();

		return indexCompounds(ids);

	}

	@RequestMapping(value = "{compoundId:" + CompoundController.METABOLIGHTS_COMPOUND_ID_REG_EXP +"}", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse<ArrayList<String>> indexCompoundUrl(@PathVariable("compoundId") String compoundId) {

		logger.info("Requesting " + compoundId + " to be indexed to the webservice");

		ArrayList<String> id = new ArrayList<>();
		id.add(compoundId);

		RestResponse<ArrayList<String>> response = indexCompounds(id);

		return response;

	}

	private RestResponse<ArrayList<String>> indexCompounds(List<String> ids) {


		RestResponse<ArrayList<String>> response = new RestResponse<>();
		response.setContent(new ArrayList<String>());

		long indexed = 0;

		for (String id : ids) {
			if (indexCompound(id, response)){
				indexed++;
			}
		}

		// If everything was indexed...
		if (indexed == ids.size()){
			response.setMessage("All compounds were successfully indexed.");
		} else {
			response.setMessage(ids.size()-indexed + " were not indexed. Please, see content for error details.");
			response.setErr(new IndexingFailureException("Some compounds were not indexed. See response content for details."));
		}
		return response;

	}

	private boolean indexCompound(String compoundId, RestResponse<ArrayList<String>> response)  {

		// Get it from the index
		// ' Avoid search service to remove the colon.
		SearchQuery query = new SearchQuery("'_id:" + compoundId);

		SearchResult result =searchService.search(query);

		// If there is any hit
		if ((result.getResults().size() != 0)) {
			response.getContent().add(compoundId + " already indexed.");
			return true;
		}

		Compound mc = ModelObjectFactory.getCompound(compoundId);

		if (mc == null) {
			response.getContent().add("Can't get " + compoundId + " to be indexed.");
			return false;
		} else {

			return indexCompound(mc, response);
		}
	}

	private boolean indexCompound(Compound mc, RestResponse<ArrayList<String>> response) {

		try {
			searchService.index(mc.getMc());
			response.getContent().add(mc.getMc().getAccession() + " indexed successfully.");
			return true;
		} catch (IndexingFailureException e) {
			logger.error("Can't index compound {}", mc.getMc().getAccession());
			response.getContent().add(mc.getMc().getAccession() + " not indexed: " + e.getMessage());
			return false;

		}


	}


	private void indexStudy(String accession, String userToken) throws DAOException, IsaTabException, IndexingFailureException {

		Study study = getStudyDAO().getStudy(accession, userToken);

		indexStudy(study);
	}

	static void indexStudy(Study study) throws IndexingFailureException {
		searchService.index(study);
	}
	static void deleteStudy(String studyIdentifier) throws IndexingFailureException {
		searchService.delete(studyIdentifier);
	}

	@RequestMapping(value = "reset", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse<String> reset() throws  IndexingFailureException {

		logger.info("Resetting the index");

		RestResponse<String> response = new RestResponse<String>();

		searchService.resetIndex();

		response.setMessage("Index successfully reset");

		return response;

	}
	@RequestMapping(value = "status", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse<String> status()  {

		logger.info("Getting index status");

		RestResponse<String> response = new RestResponse<String>();

		String status = searchService.getStatus();

		response.setMessage(status);

		return response;

	}
}