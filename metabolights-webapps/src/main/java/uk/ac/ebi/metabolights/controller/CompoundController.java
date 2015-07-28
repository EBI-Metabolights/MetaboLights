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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.cdb.webservice.ResponseWrapper;
import uk.ac.ebi.cdb.webservice.Result;
import uk.ac.ebi.cdb.webservice.WSCitationImpl;
import uk.ac.ebi.cdb.webservice.WSCitationImplService;
import uk.ac.ebi.chebi.webapps.chebiWS.model.DataItem;
import uk.ac.ebi.metabolights.model.WebCompound;
import uk.ac.ebi.metabolights.referencelayer.model.Compound;
import uk.ac.ebi.metabolights.referencelayer.model.ModelObjectFactory;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.webservice.client.models.ReactionsList;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//import uk.ac.ebi.rhea.ws.response.sbml1.cmlreact.Reaction;

/**
 * Controller for reference layer compounds (=MTBLC*) details.
 *
 */
@Controller
public class CompoundController extends AbstractController {

    private static Logger logger = LoggerFactory.getLogger(CompoundController.class);
    private @Value("#{EUPMCWebServiceURL}") String PMCurl;

    public static final String METABOLIGHTS_COMPOUND_ID_REG_EXP = "(?:MTBLC|mtblc).+";

    //String PMCurl = "http://www.ebi.ac.uk/webservices/citexplore/v3.0.1/service?wsdl";
    private WSCitationImpl PMCSearchService;

    @RequestMapping(value = "/{compoundId:" + METABOLIGHTS_COMPOUND_ID_REG_EXP + "}")
    public ModelAndView showEntry(@PathVariable("compoundId") String mtblc, HttpServletRequest request,
								  @RequestParam (required = false ,value="alt") String alt ) {

		logger.info("requested compound " + mtblc);

		String view = (alt == null? "compound": "altcompound");

        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav(view);

//        Compound compound = ModelObjectFactory.getCompound(mtblc);
        RestResponse<Compound> response = EntryController.getMetabolightsWsClient().getCompound(mtblc);

        Compound compound = response.getContent();

        if (compound == null)
            return printMessage("Couldn't get the requested compound: "+ mtblc, response.getErr().getMessage());

        // We need the species grouped
        WebCompound webCompound = new WebCompound(compound);


        mav.addObject("compound", webCompound);
		mav.addObject("pageTitle", mtblc + " - " + webCompound.getMc().getName());

        return mav;

    }

    @RequestMapping(value = "/reactions")
    private ModelAndView showReactions(
            @RequestParam(required = false, value = "chebiId") String compound) {

        //Instantiate Model and view
        ModelAndView mav = new ModelAndView("reaction");

//        //Setting up resource client
//        RheasResourceClient client = new RheasResourceClient();

        //Initialising and passing chebi Id as compound to Rhea
        ReactionsList reactions = null;

        RestResponse<ReactionsList> response = EntryController.getMetabolightsWsClient().getCompoundReactions(compound);

        if (response.getErr() != null) {
            logger.error("Can't get reaction for {}: {}", compound,response.getErr().getMessage(), response.getErr());
        } else {

            reactions = response.getContent();
        }

		mav.addObject("reactions", reactions);

        return mav;
    }

    @RequestMapping(value = "/citations")
    private ModelAndView showCitations(
            @RequestParam(required = false, value = "mtblc") String mtblc) {

        //String localException = null;

        //Instantiate Model and view
        ModelAndView mav = new ModelAndView("citations");

        try {
            PMCSearchService = new WSCitationImplService(new URL(PMCurl)).getWSCitationImplPort();
        } catch (Exception e) {
            mav.addObject("errortext", e.getMessage());
            return mav;
        }

        //Initialising ResponseWrapper
        ResponseWrapper rslt = null;

        //Passing MTBLC cmound id to Modelobjectfactory class
        Compound cmpd = ModelObjectFactory.getCompound(mtblc);
        if (cmpd == null)
            return printMessage("Error","The requested compound does not exist: "+ mtblc);

        //Creating a list object for DataItems
        List<DataItem> pmid = cmpd.getChebiEntity().getCitations();

        //Creating a list object for ResponseWrapper
        List<Result> rsltItems = new ArrayList<Result>();

        //Iterating the dataitems to get the citation object
        for (int x = 0; x < pmid.size(); x++) {
            String query = pmid.get(x).getData();
            String dataset = "metadata";
            String resultType = "core";
            int offSet = 0;
            String email = "";

            try {

                rslt = PMCSearchService.searchPublications(query, dataset, resultType, offSet, false, email);
                rsltItems.addAll(x, rslt.getResultList().getResult());
            } catch (Exception e) {
                mav.addObject("errortext", e.getMessage());
            }
        }

        mav.addObject("citationList", rsltItems);

        return mav;
    }
}
