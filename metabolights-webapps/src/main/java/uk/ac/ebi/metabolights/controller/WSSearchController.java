/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Oct-23
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

package uk.ac.ebi.metabolights.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.search.service.Booster;
import uk.ac.ebi.metabolights.search.service.Facet;
import uk.ac.ebi.metabolights.search.service.SearchQuery;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.webservice.client.MetabolightsWsClient;
import uk.ac.ebi.metabolights.webservice.client.models.MixedSearchResult;

/**
 * Controller for Metabolights searching using the webservice.
 * @author Pablo Conesa
 */
@Controller
public class WSSearchController extends AbstractController{

	public static final String HEADER = "sHeader";
	public static final String NORESULTSMESSAGE = "noresultsmessage";
	public static final String BROWSE = "browse";
	public static final String SEARCH = "search";
	private static final String MY_SUBMISSIONS = "mysubmissions";

	private static Logger logger = LoggerFactory.getLogger(WSSearchController.class);


    /**
     * Controller for a browse (empty internalSearch) request
     */
    @RequestMapping(value = BROWSE )
    public ModelAndView browse() throws Exception {

		//Trigger the internalSearch based on the filter
		ModelAndView mav = internalSearch(BROWSE);

		return mav;
    }

	/**
	 * Controller for a internalSearch request, including possible filters.
	 *
	 */
	@RequestMapping(value = SEARCH, method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView search (@RequestParam(required=false,value="freeTextQuery") String freeTextQuery) throws Exception {


		//Trigger the internalSearch based on the filter
		ModelAndView mav = internalSearch(SEARCH);

		return mav;
	}

	private ModelAndView internalSearch(String MAVName) {

		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav(MAVName);

		//used for the JSP form
		mav.addObject("action", MAVName);

		if (MAVName.equals(SEARCH)){
			mav.addObject(HEADER, PropertyLookup.getMessage("msg.search.title"));
			mav.addObject(NORESULTSMESSAGE, PropertyLookup.getMessage("msg.search.noresults"));
		} else if (MAVName.equals(MY_SUBMISSIONS)){
			mav.addObject(HEADER, PropertyLookup.getMessage("msg.mysubmissions.title"));
			mav.addObject(NORESULTSMESSAGE, PropertyLookup.getMessage("msg.mysubmissions.noresults"));
		// Browse mode
		} else {
			mav.addObject(HEADER, PropertyLookup.getMessage("msg.browse.title"));
			mav.addObject(NORESULTSMESSAGE, PropertyLookup.getMessage("msg.browse.noresults"));
		}


		// Make the search
		// Get the query
		SearchQuery query = getEmptyQuery();

		MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();

		RestResponse<? extends MixedSearchResult> entitySearchResult = wsClient.search(query);

		mav.addObject("searchResponse", entitySearchResult);

		// Add methods
		if (entitySearchResult.getErr() == null){
			mav.addObject("pagecount", entitySearchResult.getContent().getQuery().getPagination().getPageCount());
		}



		return mav;
	}


	@RequestMapping(value = MY_SUBMISSIONS)
	public ModelAndView MySubmissionsSearch () {


		//Trigger the internalSearch based on the filter
		ModelAndView mav = internalSearch(MY_SUBMISSIONS);

		mav.addObject("usersFullName", LoginController.getLoggedUser().getFullName());

		return mav;
	}

	private SearchQuery getEmptyQuery(){

		SearchQuery emptyQuery = new SearchQuery();

		addFacet("assays.technology", emptyQuery);
		addFacet( "studyStatus", emptyQuery);
		addFacet("organism.organismName", emptyQuery);
		addFacet("organism.organismPart", emptyQuery);
		addFacet("users.fullName", emptyQuery);


//		<c:if test="${not empty usersFullName}">
//				,"lines":[{"value":"${usersFullName}","checked":true}]
//			</c:if>

		addFacet("factors.name", emptyQuery);
		addFacet("descriptors.description", emptyQuery);
		emptyQuery.getPagination().setPage(1);
		emptyQuery.getPagination().setPageSize(10);

		Booster titleBooster = new Booster();
		titleBooster.setBoost(1);
		titleBooster.setFieldName("title");

		emptyQuery.getBoosters().add(titleBooster);
//
//		<c:if test="${not empty freeTextQuery}">
//				emptyQuery.text = "${freeTextQuery}";
//		</c:if>

		return emptyQuery;


	}

	private void addFacet(String facetName, SearchQuery query) {

		query.getFacets().add(new Facet(facetName));
	}


}
