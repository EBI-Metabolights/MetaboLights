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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.model.MetabolightsUser;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.search.service.Booster;
import uk.ac.ebi.metabolights.search.service.Facet;
import uk.ac.ebi.metabolights.search.service.FacetLine;
import uk.ac.ebi.metabolights.search.service.SearchQuery;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.webservice.client.MetabolightsWsClient;
import uk.ac.ebi.metabolights.webservice.client.models.MixedSearchResult;

import javax.servlet.http.HttpServletRequest;
import java.util.AbstractMap;
import java.util.Map;

/**
 * Controller for Metabolights searching using the webservice.
 *
 * @author Pablo Conesa
 */
@Controller
public class WSSearchController extends AbstractController {

    public static final String HEADER = "sHeader";
    public static final String NORESULTSMESSAGE = "noresultsmessage";
    public static final String BROWSE = "browse";
    public static final String SEARCH = "search";
    private static final String MY_SUBMISSIONS = "mysubmissions";

    // Facets
    public static final String OBJECT_TYPE = "ObjectType";
    public static final String ASSAYS_TECHNOLOGY = "assays.technology";
    public static final String SPECIES_DATA = "compound.hasSpecies";
    public static final String PATHWAY_DATA = "compound.hasPathways";
    public static final String REACTION_DATA = "compound.hasReactions";
    public static final String NMR_DATA = "compound.hasNMR";
    public static final String MS_DATA = "compound.hasMS";
    public static final String STUDY_STATUS = "studyStatus";
    public static final String ORGANISM_ORGANISM_NAME = "organism.organismName";
    public static final String ORGANISM_ORGANISM_PART = "organism.organismPart";
    public static final String USERS_FULL_NAME = "users.fullName";
    public static final String FACTORS_NAME = "factors.name";
    public static final String DESCRIPTORS_DESCRIPTION = "descriptors.description";
    public static final String VALIDATIONS_STATUS = "validations.status";
    public static final String VALIDATIONS_ENTRIES_STATUS = "validations.entries.statusExt";
    public static final String SEARCH_USER_STUDIES = "searchUserStudies";

    //private static Logger logger = LoggerFactory.getLogger(WSSearchController.class);


    /**
     * Controller for a browse (empty internalSearch) request
     */
    @RequestMapping(value = BROWSE)
    public ModelAndView browse(HttpServletRequest request) throws Exception {

        //Trigger the internalSearch based on the filter
        ModelAndView mav = internalSearch(BROWSE, request);

        return mav;
    }

    /**
     * Controller for a internalSearch request, including possible filters.
     */
    @RequestMapping(value = SEARCH, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView search(@RequestParam(required = false, value = "freeTextQuery") String freeTextQuery, HttpServletRequest request) throws Exception {


        //Trigger the internalSearch based on the filter
        ModelAndView mav = internalSearch(SEARCH, request);

        return mav;
    }

    @RequestMapping({"/studies"})
    public ModelAndView searchStudies(HttpServletRequest request) {

        // Get the query
        SearchQuery query = getQuery(request);

        // Add a compound filter
        Map.Entry compoundFilter = new AbstractMap.SimpleEntry(OBJECT_TYPE, new String[]{"study"});


        populateFacet(query, compoundFilter);

        // Make the search
        return internalSearch(SEARCH, query, null);

    }

    @RequestMapping(value = SEARCH_USER_STUDIES, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView searchAuthorStudies(HttpServletRequest request) {

        // Get the query
        SearchQuery query = getQuery(request);


        // Add a compound filter
        Map.Entry searchStudyEntry = new AbstractMap.SimpleEntry(OBJECT_TYPE, new String[]{"study"});
        Map.Entry userFilter = new AbstractMap.SimpleEntry(USERS_FULL_NAME, new String[]{request.getParameter("users.fullName")});

        populateFacet(query, searchStudyEntry);
        populateFacet(query, userFilter);

        // Make the search
        return internalSearch(SEARCH, query, SEARCH_USER_STUDIES);

    }


    @RequestMapping({"/reference", "/compounds"})
    public ModelAndView searchCompounds(HttpServletRequest request) {

        // Get the query
        SearchQuery query = getQuery(request);

        // Add a compound filter
        Map.Entry compoundFilter = new AbstractMap.SimpleEntry(OBJECT_TYPE, new String[]{"compound"});


        populateFacet(query, compoundFilter);

        // Make the search
        return internalSearch(SEARCH, query, null);

    }

    private ModelAndView internalSearch(String MAVName, HttpServletRequest request) {

        // Get the query
        SearchQuery query = getQuery(request);
        // Make the search
        return internalSearch(MAVName, query, null);
    }

    private ModelAndView internalSearch(String MAVName, SearchQuery query, String action) {

        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav(MAVName);

        //used for the JSP form
        if (action == null) {
            mav.addObject("action", SEARCH);
        }else{
            mav.addObject("action", action);
        }

        if (MAVName.equals(SEARCH)) {
            mav.addObject(HEADER, PropertyLookup.getMessage("msg.search.title"));
            mav.addObject(NORESULTSMESSAGE, PropertyLookup.getMessage("msg.search.noresults"));
        } else if (MAVName.equals(MY_SUBMISSIONS)) {
            mav.addObject(HEADER, PropertyLookup.getMessage("msg.mysubmissions.title"));
            mav.addObject(NORESULTSMESSAGE, PropertyLookup.getMessage("msg.mysubmissions.noresults"));
            // Browse mode
        } else {
            mav.addObject(HEADER, PropertyLookup.getMessage("msg.browse.title"));
            mav.addObject(NORESULTSMESSAGE, PropertyLookup.getMessage("msg.browse.noresults"));
        }


        MetabolightsWsClient wsClient = EntryController.getMetabolightsWsClient();

        RestResponse<? extends MixedSearchResult> entitySearchResult = wsClient.search(query);

        mav.addObject("searchResponse", entitySearchResult);


        // Add object methods that can't be called from JSP.
        if (entitySearchResult.getErr() == null) {
            mav.addObject("pagecount", entitySearchResult.getContent().getQuery().getPagination().getPageCount());
            mav.addObject("firstPageItemNumber", entitySearchResult.getContent().getQuery().getPagination().getFirstPageItemNumber());
            mav.addObject("lastPageItemNumber", entitySearchResult.getContent().getQuery().getPagination().getLastPageItemNumber());
        }

        return mav;
    }

    private SearchQuery getQuery(HttpServletRequest request) {

        // Get an empty query
        SearchQuery query = getEmptyQuery();

        // Fill query with request parameters
        populateQueryFromRequest(query, request);


        return query;

    }

    private void populateQueryFromRequest(SearchQuery query, HttpServletRequest request) {

        // Get the parameters
        Map<String, String[]> parameters = request.getParameterMap();


        // Go through all the entries (Key + String[])
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {

            String key = entry.getKey();

            // If its the page
            if (key.equals("pageNumber")) {

                int page = Integer.parseInt(entry.getValue()[0]);

                query.getPagination().setPage(page);
            } else if (key.equals("freeTextQuery")) {
                query.setText(entry.getValue()[0]);

                //... its a facet....fill the facet
            } else {

                populateFacet(query, entry);

            }

        }

        return;

    }

    private void populateFacet(SearchQuery query, Map.Entry<String, String[]> entry) {

        // Get the facet by name
        Facet facet = getFacetByName(query, entry.getKey());

        if (facet != null) {

            for (String value : entry.getValue()) {

                FacetLine newLine = new FacetLine();
                newLine.setChecked(true);
                newLine.setValue(value);
                facet.getLines().add(newLine);

            }
        }

    }

    private Facet getFacetByName(SearchQuery query, String name) {

        for (Facet facet : query.getFacets()) {
            if (facet.getName().equals(name)) {
                return facet;
            }
        }

        //logger.warn("Facet with name {} not found.", name);
        return null;
    }


    @RequestMapping(value = MY_SUBMISSIONS)
    public ModelAndView MySubmissionsSearch(HttpServletRequest request) {


        // Get the query
        SearchQuery query = getQuery(request);

        MetabolightsUser user = LoginController.getLoggedUser();

        // Add a compound filter
        Map.Entry userFilter = new AbstractMap.SimpleEntry(USERS_FULL_NAME, new String[]{user.getFullName()});


        populateFacet(query, userFilter);


        //Trigger the internalSearch based on the filter
        ModelAndView mav = internalSearch(MY_SUBMISSIONS, query, MY_SUBMISSIONS);


        return mav;
    }

    private SearchQuery getEmptyQuery() {

        SearchQuery emptyQuery = new SearchQuery();

        addFacet(OBJECT_TYPE, emptyQuery);
        addFacet(ASSAYS_TECHNOLOGY, emptyQuery);
        addFacet(STUDY_STATUS, emptyQuery);
        addFacet(ORGANISM_ORGANISM_NAME, emptyQuery);
        addFacet(ORGANISM_ORGANISM_PART, emptyQuery);
        addFacet(SPECIES_DATA, emptyQuery);
        addFacet(PATHWAY_DATA, emptyQuery);
        addFacet(REACTION_DATA, emptyQuery);
        addFacet(NMR_DATA, emptyQuery);
        addFacet(MS_DATA, emptyQuery);

//		<c:if test="${not empty usersFullName}">
//				,"lines":[{"value":"${usersFullName}","checked":true}]
//			</c:if>
//
//        commented out temporarily
//        addFacet(USERS_FULL_NAME, emptyQuery);
//        addFacet(FACTORS_NAME, emptyQuery);
//        addFacet(DESCRIPTORS_DESCRIPTION, emptyQuery);
//        addFacet(VALIDATIONS_STATUS, emptyQuery);
//        addFacet(VALIDATIONS_ENTRIES_STATUS, emptyQuery);
        emptyQuery.getPagination().setPage(1);
        emptyQuery.getPagination().setPageSize(10);

        Booster titleBooster = new Booster();
        titleBooster.setBoost(1);
        titleBooster.setFieldName("title");

        titleBooster = new Booster();
        titleBooster.setBoost(1);
        titleBooster.setFieldName("name");

        titleBooster = new Booster();
        titleBooster.setBoost(2);
        titleBooster.setFieldName("_id");


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
