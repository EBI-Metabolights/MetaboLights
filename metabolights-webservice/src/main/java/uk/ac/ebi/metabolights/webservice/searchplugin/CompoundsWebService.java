package uk.ac.ebi.metabolights.webservice.searchplugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by kalai on 08/09/2016.
 */
//@Controller
//@RequestMapping("genericcompoundsearch")
public class CompoundsWebService {
    private static Logger logger = LoggerFactory.getLogger(CompoundSearchResult.class);

    public static final String COMPOUND_NAME_MAPPING = "/name";
    public static final String COMPOUND_SMILES_MAPPING = "/smiles";
    public static final String COMPOUND_INCHI_MAPPING = "/inchi";

    private static final CuratedMetaboliteTable curatedMetaboliteTable = CuratedMetaboliteTable.getInstance();

    private ChebiSearch chebiWS = new ChebiSearch();
    private PubChemSearch pubchemSearch = new PubChemSearch();
    private ChemSpiderSearch chemSpiderSearch = new ChemSpiderSearch();
    private static final int numberOfSearches = 3;

    //TODO handle smiles and inchi with blackslash characters by post


    @RequestMapping(value = COMPOUND_NAME_MAPPING + "/{compoundName}")
    @ResponseBody
    public RestResponse<List<CompoundSearchResult>> getCompoundByName(@PathVariable("compoundName") final String compoundName) {
        RestResponse<List<CompoundSearchResult>> response = new RestResponse();
        List<CompoundSearchResult> searchHits = getSearchHitsForName(compoundName);
        sort(searchHits);
        response.setContent(searchHits);
        return response;
    }

    private List<CompoundSearchResult> getSearchHitsForName(final String compoundName) {
      // matchNameinCuratedList --> Get back a list of matched chebi ids with priority 1 or none
        // if there is an id fetch full info from chebi; stop

        //

        //nonamematch - fetchSynonyms
        return null;
    }

    private List<CompoundSearchResult> extract(List<Future<CompoundSearchResult>> searchResults) throws ExecutionException, InterruptedException {
        List<CompoundSearchResult> searchHits = new ArrayList<>();
        for (Future<CompoundSearchResult> searchResult : searchResults) {
            searchHits.add(searchResult.get());
        }
        return searchHits;
    }

    private boolean thereIsAMatchInCuratedList(String[] nameMatch) {
        return nameMatch.length > 0;
    }

    private List<CompoundSearchResult> combine(List<Future<CompoundSearchResult>> searchResultsFromChebi,
                                               Future<Collection<CompoundSearchResult>> chemSpiderResults,
                                               Future<Collection<CompoundSearchResult>> pubchemResults) {
        List<CompoundSearchResult> totalSearchResults = new ArrayList<>();

        try {
            totalSearchResults.addAll(extract(searchResultsFromChebi));
            totalSearchResults.addAll(chemSpiderResults.get());
            totalSearchResults.addAll(pubchemResults.get());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSearchResults;

    }

    private void sort(List<CompoundSearchResult> searchHits) {
        //TODO apply logic for sorting

    }


}
