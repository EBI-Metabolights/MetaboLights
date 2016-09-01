package uk.ac.ebi.metabolights.webservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.searchplugin.*;

/**
 * Created by kalai on 01/08/2016.
 * Class to resolve the incoming search request (Full compound name, InChI, Smiles, ChEBI ID)
 * Perform Multi threading to resolve the request and return a net result of resolved identifier
 */
@Controller
@RequestMapping("genericcompoundsearch")
public class GenericCompoundWSController {

    private static Logger logger = LoggerFactory.getLogger(GenericCompoundWSController.class);

    public static final String COMPOUND_NAME_MAPPING = "/name";
    public static final String COMPOUND_SMILES_MAPPING = "/smiles";
    public static final String COMPOUND_INCHI_MAPPING = "/inchi";

    private static final CuratedMetaboliteTable curatedMetaboliteTable = CuratedMetaboliteTable.getInstance();

    private ChebiSearch chebiWS = new ChebiSearch();
    private PubChemSearch pubchemSearch = new PubChemSearch();
    private ChemSpiderSearch chemSpiderSearch = new ChemSpiderSearch();


    @RequestMapping(value = COMPOUND_NAME_MAPPING + "/{compoundName}")
    @ResponseBody
    public RestResponse<CompoundSearchResult> getCompoundByName(@PathVariable("compoundName") String compoundName) {
        CompoundSearchResult compoundSearchResult = new CompoundSearchResult();
        RestResponse<CompoundSearchResult> response = new RestResponse();

        String[] nameMatch = curatedMetaboliteTable.getMatchingRow(CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID(), compoundName);
        if (thereIsAMatchInCuratedList(nameMatch)) {
            chebiWS.searchAndFillByName(compoundName, nameMatch, compoundSearchResult);
            response.setContent(compoundSearchResult);
            return response;
        } else if (chebiWS.searchAndFillByName(compoundName, compoundSearchResult)) {
            response.setContent(compoundSearchResult);
            return response;
        } else if (chemSpiderSearch.searchAndFill(compoundName, compoundSearchResult)) {
            response.setContent(compoundSearchResult);
            return response;
        } else if (pubchemSearch.searchAndFillByName(compoundName, compoundSearchResult)) {
            response.setContent(compoundSearchResult);
            return response;
        }
        response.setContent(compoundSearchResult);
        return response;

    }


    @RequestMapping(value = COMPOUND_INCHI_MAPPING + "/{compoundInChI}")
    @ResponseBody
    public RestResponse<CompoundSearchResult> getCompoundByInChI(@PathVariable("compoundInChI") String compoundInChI) {
        CompoundSearchResult compoundSearchResult = new CompoundSearchResult();
        RestResponse<CompoundSearchResult> response = new RestResponse();

        String[] inchiMatch = curatedMetaboliteTable.getMatchingRow(CuratedMetabolitesFileColumnIdentifier.INCHI.getID(), compoundInChI);
        if (thereIsAMatchInCuratedList(inchiMatch)) {
            chebiWS.searchAndFillByInChI(compoundInChI, inchiMatch, compoundSearchResult);
            response.setContent(compoundSearchResult);
            return response;
        } else if (chebiWS.searchAndFillByInChI(compoundInChI, compoundSearchResult)) {
            response.setContent(compoundSearchResult);
            return response;
        } else if (chemSpiderSearch.searchAndFill(compoundInChI, compoundSearchResult)) {
            response.setContent(compoundSearchResult);
            return response;
        } else if (pubchemSearch.searchAndFillByInChI(compoundInChI, compoundSearchResult)) {
            response.setContent(compoundSearchResult);
            return response;
        }
        response.setContent(compoundSearchResult);
        return response;
    }

    @RequestMapping(value = COMPOUND_SMILES_MAPPING + "/{compoundSMILES}")
    @ResponseBody
    public RestResponse<CompoundSearchResult> getCompoundBySMILES(@PathVariable("compoundSMILES") String compoundSMILES) {
        CompoundSearchResult compoundSearchResult = new CompoundSearchResult();
        RestResponse<CompoundSearchResult> response = new RestResponse();

        String[] smilesMatch = curatedMetaboliteTable.getMatchingRow(CuratedMetabolitesFileColumnIdentifier.SMILES.getID(), compoundSMILES);
        if (thereIsAMatchInCuratedList(smilesMatch)) {
            chebiWS.searchAndFillBySMILES(compoundSMILES, smilesMatch, compoundSearchResult);
            response.setContent(compoundSearchResult);
            return response;
        } else if (chebiWS.searchAndFillBySMILES(compoundSMILES, compoundSearchResult)) {
            response.setContent(compoundSearchResult);
            return response;
        } else if (chemSpiderSearch.searchAndFill(compoundSMILES, compoundSearchResult)) {
            response.setContent(compoundSearchResult);
            return response;
        } else if (pubchemSearch.searchAndFillBySMILES(compoundSMILES, compoundSearchResult)) {
            response.setContent(compoundSearchResult);
            return response;
        }

        response.setContent(compoundSearchResult);
        return response;

    }

    private boolean thereIsAMatchInCuratedList(String[] nameMatch) {
        return nameMatch.length > 0;
    }

}
