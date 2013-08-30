package uk.ac.ebi.metabolights.repository.model;

/**
 * User: conesa
 * Date: 29/08/2013
 * Time: 10:42
 */
public class Publication {

    private String abstractText;
    private String title;
    private String doi;
    private String pubmedId;

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getPubmedId() {
        return pubmedId;
    }

    public void setPubmedId(String pubmedId) {
        this.pubmedId = pubmedId;
    }
}
