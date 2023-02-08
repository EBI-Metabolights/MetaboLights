package uk.ac.ebi.metabolights.referencelayer.model;

public class SpeciesMembersModel extends EntityModel {

    private SpeciesGroupModel speciesGroup;
    private long parentMemberId;
    private String taxon;

    public SpeciesGroupModel getSpeciesGroup() {
        return speciesGroup;
    }

    public void setSpeciesGroup(SpeciesGroupModel speciesGroup) {
        this.speciesGroup = speciesGroup;
    }

    public long getParentMemberId() {
        return parentMemberId;
    }

    public void setParentMemberId(long parentMemberId) {
        this.parentMemberId = parentMemberId;
    }

    public String getTaxon() {
        return taxon;
    }

    public void setTaxon(String taxon) {
        this.taxon = taxon;
    }

    public String getTaxonDesc() {
        return taxonDesc;
    }

    public void setTaxonDesc(String taxonDesc) {
        this.taxonDesc = taxonDesc;
    }

    private String taxonDesc;


}
