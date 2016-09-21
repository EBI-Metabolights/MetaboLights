package uk.ac.ebi.metabolights.webservice.searchplugin;

/**
 * Created by kalai on 01/08/2016.
 */
public class CompoundSearchResult {

    public CompoundSearchResult(SearchResource searchResource) {
        this.setSearchResource(searchResource);
    }

    // The name of this compound
    private String name;


    // Standard inchi of the compound
    private String inchi;

    // ChEBI id
    private String chebiId;

    // Formula
    private String formula;

    // SMILES
    private String smiles;

    private SearchResource searchResource;

    public String getSmiles() {
        return smiles;
    }

    public void setSmiles(String smiles) {
        this.smiles = smiles;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getChebiId() {
        return chebiId;
    }

    public void setChebiId(String chebiId) {
        this.chebiId = chebiId;
    }

    public String getInchi() {
        return inchi;
    }

    public void setInchi(String inchi) {
        this.inchi = inchi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SearchResource getSearchResource() {
        return searchResource;
    }

    public void setSearchResource(SearchResource searchResource) {
        this.searchResource = searchResource;
    }

    public boolean isComplete() {
        if (getName() != null) {
            if (getInchi() != null) {
                if (getFormula() != null) {
                    if (getSmiles() != null) {
                        if (getChebiId() != null) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

}
