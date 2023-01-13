package uk.ac.ebi.metabolights.referencelayer.model;


public class MetaboliteAssignmentModel {

    public MetaboliteAssignmentModel(){

    }

    public String metaboliteAssignmentFileName;

    public MetaboliteData data;

    public String getMetaboliteAssignmentFileName() {
        return metaboliteAssignmentFileName;
    }

    public void setMetaboliteAssignmentFileName(String metaboliteAssignmentFileName) {
        this.metaboliteAssignmentFileName = metaboliteAssignmentFileName;
    }

    public MetaboliteData getData() {
        return data;
    }

    public void setData(MetaboliteData data) {
        this.data = data;
    }
}
