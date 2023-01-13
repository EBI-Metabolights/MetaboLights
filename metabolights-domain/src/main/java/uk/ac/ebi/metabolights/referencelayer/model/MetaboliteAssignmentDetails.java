package uk.ac.ebi.metabolights.referencelayer.model;

public class MetaboliteAssignmentDetails {

    public MetaboliteAssignmentDetails() {
    }

    private String index;  //V1 field, should not show in the website
    private String database_identifier; //V2 field
    private String metabolite_identification; //V2 field

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getDatabase_identifier() {
        return database_identifier;
    }

    public void setDatabase_identifier(String database_identifier) {
        this.database_identifier = database_identifier;
    }

    public String getMetabolite_identification() {
        return metabolite_identification;
    }

    public void setMetabolite_identification(String metabolite_identification) {
        this.metabolite_identification = metabolite_identification;
    }
}
