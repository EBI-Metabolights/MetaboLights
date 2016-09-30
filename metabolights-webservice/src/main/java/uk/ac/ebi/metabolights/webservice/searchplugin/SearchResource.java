package uk.ac.ebi.metabolights.webservice.searchplugin;

/**
 * Created by kalai on 06/09/2016.
 */
public enum SearchResource {
    CURATED(1),
    CTS(2),
    CHEBI(3),
    CHEMSPIDER(4),
    PUBCHEM(5);

    private final int searchResource;

    SearchResource(final int searchResource) {
        this.searchResource = searchResource;
    }

    public int getSearchResource() { return searchResource; }
}
