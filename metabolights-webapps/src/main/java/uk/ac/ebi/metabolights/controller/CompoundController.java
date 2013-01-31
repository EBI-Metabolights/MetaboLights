package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.biobabel.citations.CitexploreWSClient;
import uk.ac.ebi.cdb.webservice.QueryException_Exception;
import uk.ac.ebi.cdb.webservice.Result;
import uk.ac.ebi.chebi.webapps.chebiWS.model.DataItem;
import uk.ac.ebi.metabolights.referencelayer.model.Compound;
import uk.ac.ebi.metabolights.referencelayer.model.ModelObjectFactory;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.rhea.ws.client.RheaFetchDataException;
import uk.ac.ebi.rhea.ws.client.RheasResourceClient;
import uk.ac.ebi.rhea.ws.response.cmlreact.Reaction;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for reference layer compounds (=MTBLC*) details.
 * 
 */
@Controller
public class CompoundController extends AbstractController {

	private static Logger logger = Logger.getLogger(CompoundController.class);

	@RequestMapping(value = "/{compoundId:MTBLC\\d+}")

	public ModelAndView showEntry(@PathVariable("compoundId") String mtblc, HttpServletRequest request) {
		logger.info("requested compound " + mtblc);

        //ModelAndView mav = new ModelAndView("compound");
        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("compound");
        mav.addObject("compound", ModelObjectFactory.getCompound(mtblc));

        return mav;

	}


	@RequestMapping(value = "/reactions")
	private ModelAndView showReactions(
			@RequestParam(required = false, value = "chebiId") String compound){

		//Instantiate Model and view
		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("reaction");

		//Setting up resource client
		RheasResourceClient client = new RheasResourceClient();

		//Initialising and passing chebi Id as compound to Rhea
		List<Reaction> reactions = null;

		try {
			reactions = client.getRheasInCmlreact(compound);
		} catch (RheaFetchDataException e) {
			e.printStackTrace();
		}

		mav.addObject("Reactions",reactions);
		return mav;
	}

	@RequestMapping(value = "/citations")
	private ModelAndView showCitations(
			@RequestParam(required = false, value = "mtblc") String mtblc){

		//Instantiate Model and view
		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("citations");

		//Initialising Reslut
		Result rslt = null;
		
		//Passing MTBLC cmound id to Modelobjectfactory class
		Compound cmpd = ModelObjectFactory.getCompound(mtblc);
		
		//Creating a list object for DataItems
		List<DataItem> pmid = cmpd.getChebiEntity().getCitations();
		
		//Creating a list object for Result
		List<Result> rsltItems = new  ArrayList<Result>();
		
		//Iterating the dataitems to get the citation object
		for(int x=0; x<pmid.size(); x++){
			try {
				rslt = CitexploreWSClient.getCitation(pmid.get(x).getData().toString());
				rsltItems.add(x, rslt);
			} catch (QueryException_Exception e) {
				e.printStackTrace();
			}
		}

		mav.addObject("citationList", rsltItems);

		return mav;
	}
}
