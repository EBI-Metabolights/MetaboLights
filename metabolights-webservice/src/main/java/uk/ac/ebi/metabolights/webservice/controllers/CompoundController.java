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

package uk.ac.ebi.metabolights.webservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml_cml.schema.cml2.react.Product;
import org.xml_cml.schema.cml2.react.ProductList;
import org.xml_cml.schema.cml2.react.Reactant;
import org.xml_cml.schema.cml2.react.ReactantList;
import uk.ac.ebi.cdb.webservice.*;
import uk.ac.ebi.chebi.webapps.chebiWS.model.DataItem;
import uk.ac.ebi.metabolights.referencelayer.DAO.db.MetaboLightsCompoundDAO;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.referencelayer.model.*;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.services.ModelObjectFactory;
import uk.ac.ebi.metabolights.webservice.utils.FileUtil;
import uk.ac.ebi.rhea.ws.client.RheaFetchDataException;
import uk.ac.ebi.rhea.ws.client.RheasResourceClient;

import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



/**
 * Controller for reference layer compounds (=MTBLC*) details.
 *
 */
@Controller
@RequestMapping("compounds")
public class CompoundController extends BasicController {

    private static Logger logger = LoggerFactory.getLogger(CompoundController.class);
    private @Value("#{EUPMCWebServiceURL}") String PMCurl;
    public static final String METABOLIGHTS_COMPOUND_ID_REG_EXP = "(?:MTBLC|mtblc).+";
    public static final String COMPOUND_VAR = "compoundId";
    public static final String COMPOUND_MAPPING = "/{" + COMPOUND_VAR + ":" + METABOLIGHTS_COMPOUND_ID_REG_EXP + "}";

    private WSCitationImpl PMCSearchService;


    private void initPMSearchService() throws MalformedURLException {

        if (PMCSearchService == null) {
            PMCSearchService = new WSCitationImplService(new URL(PMCurl)).getWSCitationImplPort();
        }

    }



    @RequestMapping(value = COMPOUND_MAPPING)
    @ResponseBody
    public RestResponse<Compound> getCompound(@PathVariable(COMPOUND_VAR) String compoundId) throws DAOException {

		logger.info("requested compound " + compoundId);

        Compound compound = ModelObjectFactory.getCompound(compoundId);

        if (compound == null)
            throw new DAOException("The requested compound does not exist: "+ compoundId);

        RestResponse<Compound> response = new RestResponse();
        response.setContent(compound);
        return response;

    }

    @RequestMapping(value = "/spectra/{spectraId}/json")
    public void getJsonSpectra(@PathVariable("spectraId") String spectraIdS, HttpServletResponse response) {


        // Convert the id to a long...
        long spectraId = Long.parseLong(spectraIdS);

        Spectra spectra = ModelObjectFactory.getSpectra(spectraId);

        FileUtil.streamFile(spectra.getPathToJsonSpectra(), response);


    }

    @RequestMapping(value = "/pathway/{pathwayId}/svg")
    public void getPathwayFilePng(@PathVariable("pathwayId") String pathwayIdS, HttpServletResponse response) {


        // Convert the id to a long...
        long pathwayId = Long.parseLong(pathwayIdS);

        Pathway pathway = ModelObjectFactory.getPathway(pathwayId);

        FileUtil.streamFile(pathway.getPathToPathwayFile(), response, "image/svg+xml");


    }

	@RequestMapping(value = "/pathway/{pathwayId}/png")
	public void getPathwayFileSvg(@PathVariable("pathwayId") String pathwayIdS, HttpServletResponse response) {


		// Convert the id to a long...
		long pathwayId = Long.parseLong(pathwayIdS);

		Pathway pathway = ModelObjectFactory.getPathway(pathwayId);

		FileUtil.streamFile(pathway.getPathToPathwayFile(), response, "image/png");


	}
    @RequestMapping(value = COMPOUND_MAPPING + "/reactions")
    @ResponseBody
    private RestResponse<List<Reaction>> showReactions(@PathVariable(COMPOUND_VAR) String compound) {

        // Get the chebiid from the compound id
        String chebiId = MetaboLightsCompoundDAO.MetaboLightsID2chebiID(compound);

		//Initialising and passing chebi Id as compound to Rhea
		List<Reaction> reactions = null;
        RestResponse <List<Reaction>> response = new RestResponse<>();

		try {
			reactions = getReactionsAndConvert(chebiId);
            response.setContent(reactions);
		} catch (RheaFetchDataException e) {
			e.printStackTrace();

            response.setErr(e);
		}

        return response;
    }

    private List<Reaction> getReactionsAndConvert(String chebiId) throws RheaFetchDataException {

        //Setting up resource client
        RheasResourceClient client = new RheasResourceClient();

        List<Reaction> reactions = new ArrayList<>();

        List<org.xml_cml.schema.cml2.react.Reaction> rheaReactions = client.getRheasInCmlreact(chebiId);

        for (org.xml_cml.schema.cml2.react.Reaction rheaReaction : rheaReactions) {

            Reaction reaction = rheaReactionToReaction(rheaReaction);

            reactions.add(reaction);

        }

        return reactions;

    }

    private Reaction rheaReactionToReaction(org.xml_cml.schema.cml2.react.Reaction rheaReaction) {

        Reaction reaction = new Reaction();


        reaction.setId(rheaReaction.getId());


        // Reactants
        String reactants = "";
        // Direction
        String direction= "<->";
        // Products
        String products = "";

        // loop through the participants
        for (Object object : rheaReaction.getReactiveCentreAndMechanismAndReactantList()) {

            if (object instanceof ReactantList){

                ReactantList reactantList = (ReactantList) object;

                for (Object reactantObject : reactantList.getReactantListOrReactant()) {

                    if (!reactants.isEmpty()){
                        reactants = reactants + " + ";
                    }
                    reactants = reactants + ((Reactant) reactantObject).getTitle();
                }
            }


            if (object instanceof ProductList){

                ProductList productList = (ProductList) object;

                for (Object productObject : productList.getProductListOrProduct()) {

                    if (!products.isEmpty()){
                        products = products + " + ";
                    }

                    products = products + ((Product) productObject).getTitle();
                }
            }
        }


        // Compose the direction
        if (rheaReaction.getConvention().equals("rhea:direction.UN")){
            direction = "<?>";
        } else if (rheaReaction.getConvention().equals("rhea:direction.LR")){
            direction = "->";
        } else if (rheaReaction.getConvention().equals("rhea:direction.BI")){
            direction = "<->";
        } else if (rheaReaction.getConvention().equals("rhea:direction.RL")){
            direction = "<-";
        } else {
            direction = "<?>";
        }

        reaction.setName(reactants + " " + direction + " " + products);



        return reaction;

    }

    @RequestMapping(value = COMPOUND_MAPPING + "/citations")
    @ResponseBody
    private RestResponse<List<Citation>> showCitations(
            @PathVariable(COMPOUND_VAR) String compound) throws DAOException, MalformedURLException {


        // Get the chebiid from the compound id
        String chebiId = MetaboLightsCompoundDAO.MetaboLightsID2chebiID(compound);

        // Get the list of pubmed ids
        List<DataItem> pmid = getChebiCitations(compound);

        initPMSearchService();

        List<Citation> citations = new ArrayList<>();

        for (DataItem dataItem : pmid) {

            Citation citation = pubMedIdToCitation(dataItem.getData());
            citations.add(citation);
        }

        RestResponse<List<Citation>> response = new RestResponse<>();

        response.setContent(citations);

        return response;
    }

    private Citation pubMedIdToCitation(String pubMedId) throws MalformedURLException, DAOException {

        //Initialising ResponseWrapper
        ResponseWrapper response = null;

        List<Result> results;
        try {

            response = PMCSearchService.searchPublications(pubMedId, "metadata", "core", 0, false, "");
            results = response.getResultList().getResult();
        } catch (QueryException_Exception e) {
            throw new DAOException("Couldn't get publications from Pubmed Central webservice. ", e);
        }


        // Convert the results to citations...there should be only one result
        for (Result result : results) {

            Citation citation = pubMedResultToCitation(result);

            return citation;
        }


        return null;

    }

    private Citation pubMedResultToCitation(Result result) {

        Citation newCitation = new Citation();

        newCitation.setTitle(result.getTitle());
        newCitation.setAbstracT(result.getAbstractText());
        newCitation.setId(result.getId());
        newCitation.setAuthorsText(result.getAuthorString());

        // TODO: Authors.

        return newCitation;
    }

    private List<DataItem> getChebiCitations(String compound) throws DAOException {
        //Passing MTBLC compound id to Modelobjectfactory class
        Compound cmpd = ModelObjectFactory.getCompound(compound);
        if (cmpd == null)
            throw  new DAOException("The requested compound does not exist: "+ compound);

        //Creating a list object for DataItems
        return cmpd.getChebiEntity().getCitations();
    }
}
