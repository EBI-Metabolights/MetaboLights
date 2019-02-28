package uk.ac.ebi.metabolights.webservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.searchplugin.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
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
    public static final String COMPOUND_DATABASE_ID_MAPPING = "/databaseid";

    private static final CuratedMetaboliteTable curatedMetaboliteTable = CuratedMetaboliteTable.getInstance();
    private static final int numberOfSearches = 3;


    @RequestMapping(value = COMPOUND_NAME_MAPPING + "/{compoundName}")
    @ResponseBody
    public RestResponse<List<CompoundSearchResult>> getCompoundByName(@PathVariable("compoundName") final String compoundName) {
        logger.info("Searching by compound name " + compoundName);
        RestResponse<List<CompoundSearchResult>> response = new RestResponse();
        //getSearchHitsFromListChebIAndChemSpiderOnly(compoundName.contains("(+)") ? compoundName : Utilities.decode(compoundName));
        List<CompoundSearchResult> searchHits = new ArrayList<>();
        if (nameIsAnUnknown(compoundName)) {
            searchHits.add(getDefaultEntryForUnknown());
        } else {
            searchHits = getSearchHitsFromListAndChebIOnly(Utilities.decodeSlashesAndDots(compoundName));
        }
        response.setContent(searchHits);
        return response;
    }

    private boolean nameIsAnUnknown(String searchTerm) {
        if (searchTerm.equalsIgnoreCase("unknown") || searchTerm.equalsIgnoreCase("unidentified")) {
            return true;
        }
        return false;
    }

    private CompoundSearchResult getDefaultEntryForUnknown() {
        CompoundSearchResult compoundSearchResult = new CompoundSearchResult(SearchResource.CURATED);
        compoundSearchResult.setName("unknown");
        return compoundSearchResult;
    }

    private List<CompoundSearchResult> getSearchHitsForName(final String compoundName) {
        String[] nameMatch = curatedMetaboliteTable.getMatchingRow(CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID(), compoundName);
        List<CompoundSearchResult> searchHits = new ArrayList<>();
        if (thereIsAMatchInCuratedList(nameMatch)) {
            searchHits.add(new ChebiSearch().searchAndFillByName(compoundName, nameMatch, new CompoundSearchResult(SearchResource.CURATED)));
            return searchHits;
        } else {
            searchHits = new CTSSearch().getSearchHitsForName(compoundName);
        }
        return searchHits;
    }

    private List<CompoundSearchResult> getSearchHitsFromListAndChebIOnly(final String compoundName) {
        String[] nameMatch = curatedMetaboliteTable.getMatchingRow(CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID(), compoundName);
        List<CompoundSearchResult> searchHits = new ArrayList<>();
        if (thereIsAMatchInCuratedList(nameMatch)) {
            searchHits.add(new ChebiSearch().searchAndFillByName(compoundName, nameMatch, new CompoundSearchResult(SearchResource.CURATED)));
            return searchHits;
        } else {
            searchHits.add(new ChebiSearch().searchAndFillByName(compoundName, new CompoundSearchResult(SearchResource.CHEBI)));
        }
        return searchHits;
    }

    private List<CompoundSearchResult> getSearchHitsFromListChebIAndChemSpiderOnly(final String compoundName) {
        String[] nameMatch = curatedMetaboliteTable.getMatchingRow(CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID(), compoundName);
        List<CompoundSearchResult> searchHits = new ArrayList<>();
        if (thereIsAMatchInCuratedList(nameMatch)) {
            searchHits.add(new ChebiSearch().searchAndFillByName(compoundName, nameMatch, new CompoundSearchResult(SearchResource.CURATED)));
            return searchHits;
        } else {

            List<Future<CompoundSearchResult>> searchResultsFromChebi = new ArrayList<Future<CompoundSearchResult>>();
            ExecutorService executor = Executors.newFixedThreadPool(2);
            searchResultsFromChebi.add(executor.submit(new ChebiSearch(SearchTermCategory.NAME, compoundName)));
            Future<Collection<CompoundSearchResult>> chemSpiderResults = executor.submit(new ChemSpiderRestSearch(SearchTermCategory.NAME, compoundName));
            awaitTerminationAfterShutdown(executor);
            return Utilities.combine(searchResultsFromChebi, chemSpiderResults, compoundName);
        }
    }


    private List<CompoundSearchResult> getSearchHitsForNameOld(final String compoundName) {
        String[] nameMatch = curatedMetaboliteTable.getMatchingRow(CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID(), compoundName);

        List<Future<CompoundSearchResult>> searchResultsFromChebi = new ArrayList<Future<CompoundSearchResult>>();
        ExecutorService executor = Executors.newFixedThreadPool(numberOfSearches);
        if (thereIsAMatchInCuratedList(nameMatch)) {
            searchResultsFromChebi.add(executor.submit(new ChebiSearch(SearchTermCategory.NAME, compoundName, nameMatch)));
        } else {
            searchResultsFromChebi.add(executor.submit(new ChebiSearch(SearchTermCategory.NAME, compoundName)));
        }
        Future<Collection<CompoundSearchResult>> chemSpiderResults = executor.submit(new ChemSpiderRestSearch(SearchTermCategory.NAME, compoundName));
        Future<Collection<CompoundSearchResult>> pubchemResults = executor.submit(new PubChemSearch(compoundName, SearchTermCategory.NAME));
        executor.shutdown();
        return Utilities.combine(searchResultsFromChebi, chemSpiderResults, pubchemResults);
    }


    @RequestMapping(value = COMPOUND_INCHI_MAPPING, method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
    @ResponseBody
    public RestResponse<List<CompoundSearchResult>> getCompoundByInChI(@RequestBody String compoundInChI) {
        RestResponse<List<CompoundSearchResult>> response = new RestResponse();
        compoundInChI = Utilities.checkForInchiPrefix(Utilities.decode(compoundInChI));
        logger.info("Searching by InChI " + compoundInChI);
        List<CompoundSearchResult> searchHits = getSearchHitsForInChI(compoundInChI);
        Utilities.sort(searchHits);
        response.setContent(searchHits);
        return response;
    }

    private List<CompoundSearchResult> getSearchHitsForInChI(final String compoundInChI) {
        String[] inchiMatch = curatedMetaboliteTable.getMatchingRow(CuratedMetabolitesFileColumnIdentifier.INCHI.getID(), compoundInChI);
        if (inchiMatch.length != 0) {
            return Utilities.convert(inchiMatch);
        }

        List<Future<CompoundSearchResult>> searchResultsFromChebi = new ArrayList<Future<CompoundSearchResult>>();
        ExecutorService executor = Executors.newFixedThreadPool(1);
        if (thereIsAMatchInCuratedList(inchiMatch)) {
            searchResultsFromChebi.add(executor.submit(new ChebiSearch(SearchTermCategory.INCHI, compoundInChI, inchiMatch)));
        } else {
            searchResultsFromChebi.add(executor.submit(new ChebiSearch(SearchTermCategory.INCHI, compoundInChI)));
        }
//        Future<Collection<CompoundSearchResult>> chemSpiderResults = executor.submit(new ChemSpiderRestSearch(SearchTermCategory.INCHI, compoundInChI));
//        Future<Collection<CompoundSearchResult>> pubchemResults = executor.submit(new PubChemSearch(compoundInChI, SearchTermCategory.INCHI));
        awaitTerminationAfterShutdown(executor);
        return Utilities.extract(searchResultsFromChebi, compoundInChI);
    }

    @RequestMapping(value = COMPOUND_SMILES_MAPPING, method = RequestMethod.POST, headers = "content-type=application/x-www-form-urlencoded")
    @ResponseBody
    public RestResponse<List<CompoundSearchResult>> getCompoundBySMILES(@RequestBody String compoundSMILES) {
        RestResponse<List<CompoundSearchResult>> response = new RestResponse();
        compoundSMILES = Utilities.checkForSmilesPrefix(Utilities.decode(compoundSMILES));
        logger.info("Searching by compound SMILES " + compoundSMILES);

        List<CompoundSearchResult> searchHits = getSearchHitsForSMILES(compoundSMILES);
        Utilities.sort(searchHits);
        response.setContent(searchHits);
        return response;
    }

    private List<CompoundSearchResult> getSearchHitsForSMILES(final String compoundSMILES) {
        String[] smilesMatch = curatedMetaboliteTable.getMatchingRow(CuratedMetabolitesFileColumnIdentifier.SMILES.getID(), compoundSMILES);
        if (smilesMatch.length != 0) {
            return Utilities.convert(smilesMatch);
        }
        List<Future<CompoundSearchResult>> searchResultsFromChebi = new ArrayList<Future<CompoundSearchResult>>();

        ExecutorService executor = Executors.newFixedThreadPool(3);
        if (thereIsAMatchInCuratedList(smilesMatch)) {
            searchResultsFromChebi.add(executor.submit(new ChebiSearch(SearchTermCategory.SMILES, compoundSMILES, smilesMatch)));
        } else {
            searchResultsFromChebi.add(executor.submit(new ChebiSearch(SearchTermCategory.SMILES, compoundSMILES)));
        }
        Future<Collection<CompoundSearchResult>> chemSpiderResults = executor.submit(new ChemSpiderRestSearch(SearchTermCategory.SMILES, compoundSMILES));
        Future<Collection<CompoundSearchResult>> pubchemResults = executor.submit(new PubChemSearch(compoundSMILES, SearchTermCategory.SMILES));
        awaitTerminationAfterShutdown(executor);
        return Utilities.combine(searchResultsFromChebi, chemSpiderResults, pubchemResults);
    }

    private boolean thereIsAMatchInCuratedList(String[] nameMatch) {
        return nameMatch.length > 0;
    }


    @RequestMapping(value = COMPOUND_DATABASE_ID_MAPPING + "/{databaseId}")
    @ResponseBody
    public RestResponse<List<CompoundSearchResult>> getCompoundByDatabaseID(@PathVariable("databaseId") final String databaseId) {
        logger.info("Searching by compound ID " + databaseId);
        RestResponse<List<CompoundSearchResult>> response = new RestResponse();
        List<CompoundSearchResult> searchHits = new ArrayList<>();
        if (databaseId.toLowerCase().contains("chebi")) {
            CompoundSearchResult chebiResult = new CompoundSearchResult(SearchResource.CHEBI);
            new ChebiSearch().fillWithChebiCompleteEntity(Utilities.decode(databaseId), chebiResult);
            if (chebiResult.getDatabaseId() != null) {
                searchHits.add(chebiResult);
            } else {
                response.setMessage("Invalid ChEBI ID");
            }
        }
        response.setContent(searchHits);
        return response;
    }

    private void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
            logger.error("Something went wrong awaiting executor termination " + ex.getMessage());
        }
    }


}
