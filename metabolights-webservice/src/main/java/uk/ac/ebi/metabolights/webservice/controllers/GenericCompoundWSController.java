package uk.ac.ebi.metabolights.webservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.*;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.searchplugin.*;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

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

    // TODO create mappings for inchi and smiles


    @RequestMapping(value = COMPOUND_NAME_MAPPING + "/{compoundName}")
    @ResponseBody
    public RestResponse<CompoundSearchResult> getCompound(@PathVariable("compoundName") String compoundName) {
        CompoundSearchResult compoundSearchResult = new CompoundSearchResult();
        RestResponse<CompoundSearchResult> response = new RestResponse();

       // String[] nameMatch = curatedMetaboliteTable.getMatchingRow(CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID(),compoundName);
//        if (nameMatchedInCuratedList(nameMatch)) {
//            chebiWS.searchAndFill(compoundName, nameMatch, compoundSearchResult);
//            response.setContent(compoundSearchResult);
//            return response;
//        }
//        else if (chebiWS.searchAndFill(compoundName,compoundSearchResult)){
//            response.setContent(compoundSearchResult);
//            return response;
//        }
     //   pubchemSearch.searchAndFill(compoundName,compoundSearchResult);
        chemSpiderSearch.searchAndFill(compoundName,compoundSearchResult);

        response.setContent(compoundSearchResult);
        return response;
    }

    private boolean nameMatchedInCuratedList(String[] nameMatch) {
        return nameMatch.length == 0 ? false : true;
    }




}
