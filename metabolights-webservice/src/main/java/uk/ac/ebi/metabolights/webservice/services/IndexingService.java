package uk.ac.ebi.metabolights.webservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.metabolights.referencelayer.model.Compound;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import uk.ac.ebi.metabolights.repository.dao.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.search.service.IndexingFailureException;
import uk.ac.ebi.metabolights.search.service.SearchQuery;
import uk.ac.ebi.metabolights.search.service.SearchResult;
import uk.ac.ebi.metabolights.search.service.imp.es.ElasticSearchService;
import uk.ac.ebi.metabolights.webservice.controllers.BasicController;

import java.util.ArrayList;
import java.util.List;

/**
 * User: conesa
 * Date: 06/08/15
 * Time: 09:18
 */
@Service
public class IndexingService {

	private static final Logger logger = LoggerFactory.getLogger(IndexingService.class);

	@Autowired
	private ElasticSearchService searchService;


	private StudyDAO _studyDAO;

	private StudyDAO getStudyDAO() throws DAOException {

		if (_studyDAO == null){
			_studyDAO = DAOFactory.getInstance().getStudyDAO();
		}

		return _studyDAO;

	}

	public ElasticSearchService getSearchService() {
		return searchService;
	}



	// This method will be used by the StudyController to index a study after it has changed...we need to throw an exception
	public void indexStudy(Study study) throws IndexingFailureException {

		RestResponse<ArrayList<String>> response = new RestResponse<>();
		response.setContent(new ArrayList<String>());

		// If indexing failed...
		if (!indexStudy(study,response)) {

			throw new IndexingFailureException("Couldn't index " + study.getStudyIdentifier() + " study: " + response.getErr().getMessage(), response.getErr());


		}

	}

	private boolean indexStudy(String accession, String userToken, RestResponse<ArrayList<String>> response)  {

		Study study = null;
		try {
			study = getStudyDAO().getStudy(accession, userToken);

			return indexStudy(study, response);

		} catch (Exception e) {

			annotateIndexingEvent("Can't get study " + accession + " to be indexed: " + e.getMessage(), response, e);
			return false;
		}

	}

	private boolean indexStudy(Study study, RestResponse<ArrayList<String>> response) {


		try {
			searchService.index(study);
			annotateIndexingEvent("Study " + study.getStudyIdentifier() + " indexed.", response, null);
			return true;

		} catch (IndexingFailureException e) {
			annotateIndexingEvent("Couldn't index study " + study.getStudyIdentifier() + ": " + e.getMessage(), response, e);
			return false;
		}


	}

	private void annotateIndexingEvent(String annotation, RestResponse<ArrayList<String>> response, Exception exception) {

		if (exception == null) {
			logger.info(annotation);
		} else {
			logger.error(annotation, exception);
		}

		if (response != null) {
			response.getContent().add(annotation);
		}
	}

	public RestResponse<ArrayList<String>> indexStudies(List<String> studyIds, RestResponse<ArrayList<String>> response) {

		User user = BasicController.getUser();

		if (response == null) {

			response = new RestResponse<>();
			response.setContent(new ArrayList<String>());
		}


		if (studyIds == null) {
			try {
				studyIds = getStudyDAO().getList(user.getApiToken());
			} catch (DAOException e) {
				annotateIndexingEvent("Can't get the list of all study ids to index.", response, e);
				response.setMessage("Can't get the list of all study ids to index");
				response.setErr(e);
				return response;
			}
		}


		long indexed = 0;

		for (String id : studyIds) {
			if (indexStudy(id, user.getApiToken() ,response)){
				indexed++;
			}
		}

		// If everything was indexed...
		if (indexed == studyIds.size()){
			response.setMessage("All studies were successfully indexed.");
		} else {
			response.setMessage(studyIds.size()-indexed + " were not indexed. Please, see content for error details.");
			response.setErr(new IndexingFailureException("Some studies were not indexed. See response content for details."));
		}
		return response;


	}

	public void deleteStudy(String studyIdentifier) throws IndexingFailureException {
		searchService.delete(studyIdentifier);
	}




	/**
	 *
	 * COMPOUNDS INDEXING
	 *
	 */

	public RestResponse<ArrayList<String>> indexCompounds(List<String> ids, RestResponse<ArrayList<String>> response) {

		// If there is no response..
		if (response == null) {

			// ..create it
			response = new RestResponse<>();
			response.setContent(new ArrayList<String>());
		}

		// If there's no ids...
		if (ids == null) {
			// ..get all compound ids.
			ids = ModelObjectFactory.getAllCompoundsId();
		}


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
			annotateIndexingEvent("Compound " + mc.getMc().getAccession() + " indexed", response, null);
			return true;
		} catch (IndexingFailureException e) {
			annotateIndexingEvent("Can't index compound " +  mc.getMc().getAccession() + ": " + e.getMessage(), response, e);
			return false;

		}

	}
}