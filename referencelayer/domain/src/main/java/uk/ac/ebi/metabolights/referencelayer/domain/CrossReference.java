package uk.ac.ebi.metabolights.referencelayer.domain;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author conesa
 *
 * Species reference table
 */
public class CrossReference {

	private long id;
    private String accession;
    private Database db;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public Database getDb() {
        return db;
    }

    public void setDb(Database db) {
        this.db = db;
    }

    @Override
    public boolean equals(Object obj){

        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof CrossReference))
            return false;

        CrossReference xr = (CrossReference)obj;

        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(accession, xr.accession).
                append(db, xr.db).
                isEquals();

    }
}