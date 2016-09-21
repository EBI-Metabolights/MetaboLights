package uk.ac.ebi.metabolights.webservice.searchplugin;

/**
 * Created by kalai on 05/09/2016.
 */
public enum SearchTermCategory {
    SMILES(1),
    INCHI(2),
    NAME(3);

    private final int searchTerm;

    SearchTermCategory(final int termToSearch) {
        searchTerm = termToSearch;
    }

    public int getSearchTerm() { return searchTerm; }

}
