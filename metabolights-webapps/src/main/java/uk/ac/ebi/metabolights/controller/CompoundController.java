package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.cdb.webservice.*;
import uk.ac.ebi.chebi.webapps.chebiWS.model.DataItem;
import uk.ac.ebi.metabolights.referencelayer.domain.Pathway;
import uk.ac.ebi.metabolights.referencelayer.domain.Spectra;
import uk.ac.ebi.metabolights.referencelayer.model.Compound;
import uk.ac.ebi.metabolights.referencelayer.model.ModelObjectFactory;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.rhea.ws.client.RheaFetchDataException;
import uk.ac.ebi.rhea.ws.client.RheasResourceClient;
import uk.ac.ebi.rhea.ws.response.cmlreact.Reaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for reference layer compounds (=MTBLC*) details.
 *
 */
@Controller
public class CompoundController extends AbstractController {

    private static Logger logger = Logger.getLogger(CompoundController.class);
    private @Value("#{EUPMCWebServiceURL}") String PMCurl;

    public static final String METABOLIGHTS_COMPOUND_ID_REG_EXP = "(?:MTBLC|mtblc).+";

    //String PMCurl = "http://www.ebi.ac.uk/webservices/citexplore/v3.0.1/service?wsdl";
    private WSCitationImpl PMCSearchService;

    @RequestMapping(value = "/{compoundId:" + METABOLIGHTS_COMPOUND_ID_REG_EXP + "}")
    public ModelAndView showEntry(@PathVariable("compoundId") String mtblc, HttpServletRequest request) {
        logger.info("requested compound " + mtblc);

        //ModelAndView mav = new ModelAndView("compound");
        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("compound");

        Compound compound=  ModelObjectFactory.getCompound(mtblc);

        mav.addObject("compound", compound);
		mav.addObject("pageTitle", mtblc + " - " + compound.getMc().getName());


        return mav;

    }

    @RequestMapping(value = "/spectra/{spectraId}/json")
    public void getJsonSpectra(@PathVariable("spectraId") String spectraIdS, HttpServletResponse response) {


        // Convert the id to a long...
        long spectraId = Long.parseLong(spectraIdS);

        Spectra spectra = ModelObjectFactory.getSpectra(spectraId);

        FileDispatcherController.streamFile(spectra.getPathToJsonSpectra(), response);


    }

    @RequestMapping(value = "/pathway/{pathwayId}/png")
    public void getPathwayFilePng(@PathVariable("pathwayId") String pathwayIdS, HttpServletResponse response) {


        // Convert the id to a long...
        long pathwayId = Long.parseLong(pathwayIdS);

        Pathway pathway = ModelObjectFactory.getPathway(pathwayId);

        FileDispatcherController.streamFile(pathway.getPathToPathwayFile(), response, "image/svg+xml");


    }

	@RequestMapping(value = "/pathway/{pathwayId}/svg")
	public void getPathwayFileSvg(@PathVariable("pathwayId") String pathwayIdS, HttpServletResponse response) {


		// Convert the id to a long...
		long pathwayId = Long.parseLong(pathwayIdS);

		Pathway pathway = ModelObjectFactory.getPathway(pathwayId);

		FileDispatcherController.streamFile(pathway.getPathToPathwayFile(), response, "image/png");


	}
    @RequestMapping(value = "/reactions")
    private ModelAndView showReactions(
            @RequestParam(required = false, value = "chebiId") String compound) {

        //Instantiate Model and view
        ModelAndView mav = new ModelAndView("reaction");

        //Setting up resource client
        RheasResourceClient client = new RheasResourceClient();

        //Initialising and passing chebi Id as compound to Rhea
        List<Reaction> reactions = null;

        try {
            reactions = client.getRheasInCmlreact(compound);
        } catch (RheaFetchDataException e) {
            mav.addObject("errortext", e.getMessage());
            return mav;
        }

        mav.addObject("Reactions", reactions);
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