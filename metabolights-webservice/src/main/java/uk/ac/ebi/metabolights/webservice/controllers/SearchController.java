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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import uk.ac.ebi.metabolights.repository.dao.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.LiteEntity;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.search.service.*;
import uk.ac.ebi.metabolights.search.service.imp.es.ElasticSearchService;

import java.util.List;

@Controller
@RequestMapping("search")
public class SearchController extends BasicController {

	private SearchService <Object,LiteEntity>searchService = new ElasticSearchService();

    @RequestMapping(method= RequestMethod.GET)
	@ResponseBody
	public RestResponse<SearchResult> emptySearch() {

		logger.debug("GET search requested to the webservice");

		return searchWithQuery(new SearchQuery(""));

	}

	@RequestMapping(method= RequestMethod.POST)
	@ResponseBody
	public RestResponse<SearchResult> searchWithQuery(@RequestBody SearchQuery query) {

		logger.info("Search requested to the webservice: " + query);

		addUserToQuery(query);

		RestResponse<SearchResult> response = new RestResponse<SearchResult>();

		// If theres no query..default empty query.
		if (query==null) {
			query = new SearchQuery("");
		}

		response.setContent(searchService.search(query));

		return response;

	}

	private void addUserToQuery (SearchQuery query){

		User currentUser = getUser();

		SearchUser sUser = new SearchUser(currentUser.getUserName(),currentUser.isCurator());
		query.setUser(sUser);

	}

	@RequestMapping(value ="reindex", method= RequestMethod.GET)
	@ResponseBody
	@PreAuthorize( "hasRole('ROLE_SUPER_USER')")
	public RestResponse<String> reindex() throws DAOException {

		logger.info("full reindex requested to the webservice");

		RestResponse<String> response = new RestResponse<String>();

		// Get all the studies
		StudyDAO studyDAO = DAOFactory.getInstance().getStudyDAO();



		User user = getUser();
		List<String> accessions = studyDAO.getList(user.getApiToken());

		long indexed = 0;

		for (String accession : accessions) {

			String message = null;
			try {

				Study study = studyDAO.getStudy(accession, user.getApiToken());

				searchService.index(study);

				indexed++;

			} catch (IndexingFailureException e) {
				message = "Can't index study " + accession + ". " + e.getMessage();

			} catch (DAOException e) {
				message = "Can't retrieve study " + accession + ". " + e.getMessage();

			} finally {
				if (message != null) {
					logger.warn(message);
					response.setMessage(response.getMessage() + message + "\n");
				}
			}

		}

		if (indexed == accessions.size()) {
			response.setContent("Reindexing finished successfully. " + accessions.size() + " reindexed. All");
		} else {
			response.setContent("Reindexing finished with errors. " + indexed + " indexed out of " + accessions.size());
		}

		return response;

	}
}
