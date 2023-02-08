package uk.ac.ebi.metabolights.referencelayer.model;

public class CrossReferenceModel extends EntityModel{


    private String accession;
    private DatabaseModel db;

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public DatabaseModel getDb() {
        return db;
    }

    public void setDb(DatabaseModel db) {
        this.db = db;
    }

}