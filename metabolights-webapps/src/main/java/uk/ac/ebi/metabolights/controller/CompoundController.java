/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 5/1/14 3:31 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.webservice.client.models.CitationsList;
import uk.ac.ebi.metabolights.webservice.client.models.ReactionsList;

import javax.servlet.http.HttpServletRequest;

//import uk.ac.ebi.rhea.ws.response.sbml1.cmlreact.Reaction;

/**
 * Controller for reference layer compounds (=MTBLC*) details.
 *
 */
@Controller
public class CompoundController extends AbstractController {

    private static Logger logger = LoggerFactory.getLogger(CompoundController.class);

    public static final String METABOLIGHTS_COMPOUND_ID_REG_EXP = "(?:MTBLC|compoundId).+";

    //String PMCurl = "http://www.ebi.ac.uk/webservices/citexplore/v3.0.1/service?wsdl";

    @RequestMapping(value = "/{compoundId:" + METABOLIGHTS_COMPOUND_ID_REG_EXP + "}")
    public ModelAndView showEntry(@PathVariable("compoundId") String mtblc, HttpServletRequest request) {

		logger.info("requested compound " + mtblc);

		String view =  "compound";
        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav(view);

        String metabolightsPythonWsUrl = EntryController.getMetabolightsPythonWsUrl();
        mav.addObject("metabolightsPythonWsUrl", metabolightsPythonWsUrl);

        
        mav.addObject("compound", mtblc);

        return mav;

    }


    @RequestMapping(value = "/reactions")
    private ModelAndView showReactions(
            @RequestParam(required = false, value = "compoundId") String compoundId) {

        //Instantiate Model and view
        ModelAndView mav = new ModelAndView("reaction");

        //Initialising and passing chebi Id as compoundId to Rhea
        ReactionsList reactions = null;

        RestResponse<ReactionsList> response = EntryController.getMetabolightsWsClient().getCompoundReactions(compoundId);

        if (response.getErr() != null) {
            logger.error("Can't get reaction for {}: {}", compoundId,response.getErr().getMessage(), response.getErr());
        } else {
            reactions = response.getContent();
        }

		mav.addObject("reactions", reactions);

        return mav;
    }

    @RequestMapping(value = "/citations")
    private ModelAndView showCitations(
            @RequestParam(required = false, value = "compoundId") String compoundId) {

        //Instantiate Model and view
        ModelAndView mav = new ModelAndView("citations");

        //Initialising and passing chebi Id as compoundId to Rhea
        CitationsList citations = null;

        RestResponse<CitationsList> response = EntryController.getMetabolightsWsClient().getCompoundCitations(compoundId);

        if (response.getErr() != null) {
            logger.error("Can't get citations for {}: {}", compoundId,response.getErr().getMessage(), response.getErr());
        } else {

            citations = response.getContent();
        }

        mav.addObject("citationList", citations);

        return mav;

    }
}
