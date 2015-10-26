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


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.search.service.IndexingFailureException;
import uk.ac.ebi.metabolights.webservice.services.IndexingService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("index")
@PreAuthorize( "hasRole('ROLE_SUPER_USER')")
public class IndexController extends BasicController {

	public static final String ALL_STUDIES = "studies";
	public static final String ALL_COMPOUNDS = "compounds";
	@Autowired
	IndexingService indexingService;


	@RequestMapping(value = "all", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse<ArrayList<String>> reindex() throws DAOException {

		logger.info("full reindex requested to the webservice");

		RestResponse<ArrayList<String>> response = new RestResponse<>();
		response.setContent(new ArrayList<String>());


		// Index all the studies
		indexingService.indexStudies(null, response);

		// Index all the compounds
		indexingService.indexCompounds(null, response);

		return response;

	}


	@RequestMapping(value = "{studyIdentifier:" + StudyController.METABOLIGHTS_ID_REG_EXP +"}", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse<ArrayList<String>> indexStudyUrl(@PathVariable("studyIdentifier") String studyIdentifier) throws DAOException {

		logger.info("Requesting " + studyIdentifier + " indexing to the webservice");

		ArrayList<String> id = new ArrayList<>();
		id.add(studyIdentifier);

		return indexingService.indexStudies(id, null);


	}

	@RequestMapping(value = "{compoundId:" + CompoundController.METABOLIGHTS_COMPOUND_ID_REG_EXP +"}", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse<ArrayList<String>> indexCompoundUrl(@PathVariable("compoundId") String compoundId) {

		logger.info("Requesting " + compoundId + " to be indexed to the webservice");

		ArrayList<String> id = new ArrayList<>();
		id.add(compoundId);

		return indexingService.indexCompounds(id, null);


	}



	@RequestMapping(value = ALL_COMPOUNDS, method = RequestMethod.POST)
	@ResponseBody
	public RestResponse<ArrayList<String>> indexCompoundsUrl(@RequestBody(required = false) List<String> ids) throws DAOException {

		logger.info("Requesting all compound to be indexed to the webservice");


		return indexingService.indexCompounds(ids, null);

	}

	@RequestMapping(value = ALL_STUDIES, method = RequestMethod.POST)
	@ResponseBody
	public RestResponse<ArrayList<String>> indexStudiesUrl(@RequestBody(required = false) List<String> ids) throws DAOException {

		logger.info("Requesting all studies to be indexed to the webservice");

		return indexingService.indexStudies(ids, null);


	}

	@RequestMapping(value = ALL_STUDIES, method = RequestMethod.DELETE)
	@ResponseBody
	public RestResponse<ArrayList<String>>  deleteIndexedStudies() throws IndexingFailureException {

		logger.info("Deleting indexed studies");

		RestResponse<ArrayList<String>> response = new RestResponse<>();

		indexingService.getSearchService().deleteStudies();

		response.setMessage("Studies deleted from the index");

		return response;

	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public RestResponse<ArrayList<String>>  deleteIndex(@RequestBody(required = false) List<String> ids) throws IndexingFailureException {

		// Forward this to the post version

		return deleteIndexPost(ids);

	}

	@RequestMapping(value="delete",method = RequestMethod.POST)
	@ResponseBody
	public RestResponse<ArrayList<String>>  deleteIndexPost(@RequestBody(required = false) List<String> ids) throws IndexingFailureException {

		logger.info("Deleting index");

		RestResponse<ArrayList<String>> response = new RestResponse<>();
		response.setContent(new ArrayList<String>());

		boolean allOk = true;

		if (ids == null) {

			indexingService.getSearchService().deleteIndex();
		} else {

			for (String id : ids) {
				try {
					indexingService.getSearchService().delete(id);
					response.getContent().add(id + " deleted from the index.");
				} catch (IndexingFailureException e) {
					response.getContent().add("Couldn't delete " + id + " from the index: " + e.getMessage());
					allOk = false;

				}

			}
		}

		if (allOk) {
			response.setMessage("Index deleted");
		} else {
			response.setMessage("Some documents couldn't be deleted from the index. Check the content.");
		}

		return response;

	}

	@RequestMapping(value = ALL_COMPOUNDS, method = RequestMethod.DELETE)
	@ResponseBody
	public RestResponse<ArrayList<String>>  deleteIndexedCompounds() throws IndexingFailureException {

		logger.info("Deleting indexed compounds");

		RestResponse<ArrayList<String>> response = new RestResponse<>();

		indexingService.getSearchService().deleteCompounds();

		response.setMessage("Compounds deleted from the index");

		return response;

	}

	@RequestMapping(value = "reset", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse<String> reset() throws  IndexingFailureException {

		logger.info("Resetting the index");

		RestResponse<String> response = new RestResponse<String>();

		indexingService.getSearchService().resetIndex();

		response.setMessage("Index successfully reset");

		return response;

	}
	@RequestMapping(value = "status", method = RequestMethod.GET)
	@ResponseBody
	public RestResponse<ArrayList<String>> status()  {

		logger.info("Getting index status");

		RestResponse<ArrayList<String>> response = new RestResponse<ArrayList<String>>();

		ArrayList<String> status = indexingService.getSearchService().getStatus();

		response.setContent(status);

		return response;

	}


}