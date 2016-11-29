package uk.ac.ebi.metabolights.webservice.searchplugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by kalai on 19/09/2016.
 */
public class Utilities {

    private static Logger logger = LoggerFactory.getLogger(Utilities.class);
    public static List<CompoundSearchResult> combine(List<Future<CompoundSearchResult>> searchResultsFromChebi,
                                                     Future<Collection<CompoundSearchResult>> chemSpiderResults,
                                                     Future<Collection<CompoundSearchResult>> pubchemResults) {
        List<CompoundSearchResult> totalSearchResults = new ArrayList<>();
        try {
            totalSearchResults.addAll(extract(searchResultsFromChebi));
            totalSearchResults.addAll(extract(chemSpiderResults));
            totalSearchResults.addAll(extract(pubchemResults));

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Something went wrong while combining search responses: " + e);
        }
        return totalSearchResults;

    }

    public static List<CompoundSearchResult> combine(List<Future<CompoundSearchResult>> searchResultsFromChebi,
                                                     Future<Collection<CompoundSearchResult>> chemSpiderResults, String compoundName) {
        List<CompoundSearchResult> totalSearchResults = new ArrayList<>();

        try {
            List<CompoundSearchResult> chebiCompounds = extract(searchResultsFromChebi);
            if (!chebiCompounds.isEmpty()) {
                if (hasSomeValue(chebiCompounds.get(0))) {
                    totalSearchResults.addAll(chebiCompounds);
                    return totalSearchResults;
                }
            }
            Collection<CompoundSearchResult> chemSpiderCompounds = extract(chemSpiderResults);
            if (!chemSpiderCompounds.isEmpty()) {
                if (chemSpiderCompounds.size() == 1) {
                    totalSearchResults.addAll(chemSpiderCompounds);
                } else {
                    for (CompoundSearchResult compound : chemSpiderCompounds) {
                        if (hit(compound.getName(), compoundName)) {
                            totalSearchResults.add(compound);
                            return totalSearchResults;
                        }
                    }
                    totalSearchResults.addAll(chemSpiderCompounds);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Something went wrong while combining search responses for compound: "+compoundName , e);
        }
        return totalSearchResults;

    }

    private static boolean hasSomeValue(CompoundSearchResult compound) {
        return compound.getName() != null;
    }


    public static List<CompoundSearchResult> extract(List<Future<CompoundSearchResult>> searchResults) throws ExecutionException, InterruptedException {
        List<CompoundSearchResult> searchHits = new ArrayList<>();
        for (Future<CompoundSearchResult> searchResult : searchResults) {
            searchHits.add(searchResult.get());
        }
        return searchHits;
    }

    public static Collection<CompoundSearchResult> extract(Future<Collection<CompoundSearchResult>> results) throws ExecutionException, InterruptedException {
        Collection<CompoundSearchResult> extractedResult = new ArrayList<>();
        if (results != null) {
            if (results.get() != null) {
                extractedResult = results.get();
                return extractedResult;
            }
        }
        return extractedResult;
    }


    public static void sort(List<CompoundSearchResult> searchHits) {
        if (searchHits.size() < 2) {
            return;
        }
        boolean somethingsChanged = false;
        Object o1, o2;
        do {
            somethingsChanged = false;
            for (int f = 0; f < searchHits.size() - 1; f++) {
                if (score(searchHits.get(f)) < score(searchHits.get(f + 1))) {
                    o1 = searchHits.get(f + 1);
                    searchHits.remove(f + 1);
                    searchHits.add(f, (CompoundSearchResult) o1);
                    somethingsChanged = true;
                }
            }
        } while (somethingsChanged);
    }

    public static int score(CompoundSearchResult result) {
        int values = 0;
        if (result.getName() != null) {
            values++;
            if (result.getInchi() != null) {
                values++;
                if (result.getFormula() != null) {
                    values++;
                    if (result.getSmiles() != null) {
                        values++;
                        if (result.getDatabaseId() != null) {
                            values++;
                            if (result.getDatabaseId().toLowerCase().contains("chebi")) {
                                values++;
                            }
                        }
                    }
                }
            }
        }
        return values;
    }

    public static List<CompoundSearchResult> convert(String[] curatedMatch) {
        List<CompoundSearchResult> results = new ArrayList<>();
        CompoundSearchResult searchResult = new CompoundSearchResult(SearchResource.CURATED);
        try {
            searchResult.setName(curatedMatch[CuratedMetabolitesFileColumnIdentifier.COMPOUND_NAME.getID()]);
            searchResult.setInchi(curatedMatch[CuratedMetabolitesFileColumnIdentifier.INCHI.getID()]);
            searchResult.setSmiles(curatedMatch[CuratedMetabolitesFileColumnIdentifier.SMILES.getID()]);
            searchResult.setFormula(curatedMatch[CuratedMetabolitesFileColumnIdentifier.MOLECULAR_FORMULA.getID()]);
            searchResult.setDatabaseId(curatedMatch[CuratedMetabolitesFileColumnIdentifier.CHEBI_ID.getID()]);
        } catch (Exception e) {
            logger.error("Something went wrong while converting String[] to CompoundSearchResult" , e);
            return results;
        }
        results.add(searchResult);
        return results;
    }

    public static String decode(String url) {
        String decoded = null;
        try {
            decoded = java.net.URLDecoder.decode(url, "UTF-8");
            return decoded;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("Something went wrong while decoding: " + url , e);
            return url;
        }
    }

    public static String decodeSlashesAndDots(String url) {
        String decoded = null;
        try {
            decoded = url.replaceAll("__","/");
            decoded = decoded.replaceAll("_&_","\\.");
            return decoded;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Something went wrong while decoding: " + url , e);
            return url;
        }
    }


    public static boolean hit(String synonym, String termToMatch) {
        return removeFewCharactersForConsistency(synonym).equalsIgnoreCase(removeFewCharactersForConsistency(termToMatch));
    }

    public static String removeFewCharactersForConsistency(String term) {
        String modified = term.replaceAll("/\u2013|\u2014/g", "-");
        modified = modified.replaceAll("\\p{Pd}", "");
        modified = modified.replaceAll("-", "");
        modified = modified.replaceAll("_", "");
        modified = modified.replaceAll(",", "").replaceAll("\'","");
        modified = modified.replaceAll("\\[", "").replaceAll("\\]","");
        modified = modified.replaceAll("\\(", "").replaceAll("\\)","");
        modified = modified.replaceAll("\\{", "").replaceAll("\\}", "");
        modified = modified.replaceAll("\\s", "");
        return modified;
    }
}
