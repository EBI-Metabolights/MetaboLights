package uk.ac.ebi.metabolights.webservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.searchplugin.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

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
    private static final int numberOfSearches = 4;

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
        String[] nameMatch = curatedMetaboliteTable.getMatchingRow(CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID(), compoundName);

        List<Future<CompoundSearchResult>> searchResultsFromChebi = new ArrayList<Future<CompoundSearchResult>>();
        List<CompoundSearchResult> totalSearchResults = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(3);
        if (thereIsAMatchInCuratedList(nameMatch)) {
            searchResultsFromChebi.add(executor.submit(new ChebiSearch(SearchTermCategory.NAME, compoundName, nameMatch)));
        } else {
            searchResultsFromChebi.add(executor.submit(new ChebiSearch(SearchTermCategory.NAME, compoundName)));
        }
        Future<Collection<CompoundSearchResult>> chemSpiderResults = executor.submit(new ChemSpiderSearch(compoundName));
        Future<Collection<CompoundSearchResult>> pubchemResults = executor.submit(new PubChemSearch(compoundName, SearchTermCategory.NAME));
        executor.shutdown();

        try {
            totalSearchResults.addAll(extract(searchResultsFromChebi));
            totalSearchResults.addAll(chemSpiderResults.get());
            totalSearchResults.addAll(pubchemResults.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSearchResults;
    }

    private List<CompoundSearchResult> extract(List<Future<CompoundSearchResult>> searchResults) throws ExecutionException, InterruptedException {
        List<CompoundSearchResult> searchHits = new ArrayList<>();
        for (Future<CompoundSearchResult> searchResult : searchResults) {
            searchHits.add(searchResult.get());
        }
        return searchHits;
    }


    @RequestMapping(value = COMPOUND_INCHI_MAPPING, method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<CompoundSearchResult>> getCompoundByInChI(@RequestBody String compoundInChI) {
        RestResponse<List<CompoundSearchResult>> response = new RestResponse();
        List<CompoundSearchResult> searchHits = getSearchHitsForInChI(compoundInChI);
        sort(searchHits);
        response.setContent(searchHits);
        return response;
    }

    private List<CompoundSearchResult> getSearchHitsForInChI(final String compoundInChI) {
        String[] inchiMatch = curatedMetaboliteTable.getMatchingRow(CuratedMetabolitesFileColumnIdentifier.INCHI.getID(), compoundInChI);

        List<Future<CompoundSearchResult>> searchResultsFromChebi = new ArrayList<Future<CompoundSearchResult>>();
        List<CompoundSearchResult> totalSearchResults = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(3);
        if (thereIsAMatchInCuratedList(inchiMatch)) {
            searchResultsFromChebi.add(executor.submit(new ChebiSearch(SearchTermCategory.INCHI, compoundInChI, inchiMatch)));
        } else {
            searchResultsFromChebi.add(executor.submit(new ChebiSearch(SearchTermCategory.INCHI, compoundInChI)));
        }
        Future<Collection<CompoundSearchResult>> chemSpiderResults = executor.submit(new ChemSpiderSearch(compoundInChI));
        Future<Collection<CompoundSearchResult>> pubchemResults = executor.submit(new PubChemSearch(compoundInChI, SearchTermCategory.INCHI));
        executor.shutdown();

        try {
            totalSearchResults.addAll(extract(searchResultsFromChebi));
            totalSearchResults.addAll(chemSpiderResults.get());
            totalSearchResults.addAll(pubchemResults.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSearchResults;
    }

    @RequestMapping(value = COMPOUND_SMILES_MAPPING, method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<CompoundSearchResult>> getCompoundBySMILES(@RequestBody String compoundSMILES) {
        RestResponse<List<CompoundSearchResult>> response = new RestResponse();
        List<CompoundSearchResult> searchHits = getSearchHitsForInChI(compoundSMILES);
        sort(searchHits);
        response.setContent(searchHits);
        return response;
    }

    private List<CompoundSearchResult> getSearchHitsForSMILES(final String compoundSMILES) {
        String[] nameMatch = curatedMetaboliteTable.getMatchingRow(CuratedMetabolitesFileColumnIdentifier.SMILES.getID(), compoundSMILES);

        List<Future<CompoundSearchResult>> searchResultsFromChebi = new ArrayList<Future<CompoundSearchResult>>();
        List<CompoundSearchResult> totalSearchResults = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(3);
        if (thereIsAMatchInCuratedList(nameMatch)) {
            searchResultsFromChebi.add(executor.submit(new ChebiSearch(SearchTermCategory.SMILES, compoundSMILES, nameMatch)));
        } else {
            searchResultsFromChebi.add(executor.submit(new ChebiSearch(SearchTermCategory.SMILES, compoundSMILES)));
        }
        Future<Collection<CompoundSearchResult>> chemSpiderResults = executor.submit(new ChemSpiderSearch(compoundSMILES));
        Future<Collection<CompoundSearchResult>> pubchemResults = executor.submit(new PubChemSearch(compoundSMILES, SearchTermCategory.SMILES));
        executor.shutdown();

        try {
            totalSearchResults.addAll(extract(searchResultsFromChebi));
            totalSearchResults.addAll(chemSpiderResults.get());
            totalSearchResults.addAll(pubchemResults.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSearchResults;
    }

    private boolean thereIsAMatchInCuratedList(String[] nameMatch) {
        return nameMatch.length > 0;
    }

    private void sort(List<CompoundSearchResult> searchHits) {
        //TODO apply logic for sorting

    }

}
