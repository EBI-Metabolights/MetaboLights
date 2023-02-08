package uk.ac.ebi.metabolights.referencelayer.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import java.util.ArrayList;

public class MetaboLightsCompoundModel extends EntityModel {


    // The public accession number of this compound.
    private String accession;

    // The name of this compound
    private String name;

    // The description of this compound
    private String description;

    // Standard inchi of the compound
    private String inchi;

    // Standard inchikey of the compound
    private String inchikey;

    // ChEBI id
    private String chebiId;

    // Formula
    private String formula;

    // Iupac Names (separated by |)
    private String iupacNames;

    //Status (Always public so far)
    private String studyStatus = "PUBLIC";

    private boolean hasLiterature;

    private boolean hasReactions;

    private boolean hasSpecies;

    private boolean hasPathways;

    private boolean hasNMR;

    private boolean hasMS;

    private String updatedDate;

    public ArrayList<SpeciesModel> getMetSpecies() {
        return metSpecies;
    }

    public void setMetSpecies(ArrayList<SpeciesModel> metSpecies) {
        this.metSpecies = metSpecies;
    }

    // Species associated with the metabolite
    private ArrayList<SpeciesModel> metSpecies = new ArrayList<SpeciesModel>();


    public ArrayList<CrossReferenceModel> getCrossReference() {
        return crossReference;
    }

    public void setCrossReference(ArrayList<CrossReferenceModel> crossReference) {
        this.crossReference = crossReference;
    }

    private ArrayList<CrossReferenceModel> crossReference = new ArrayList<CrossReferenceModel>();
    /**
     * @return the accession
     */
    public String getAccession() {
        return accession;
    }

    /**
     * @param accession the accession to set
     */
    public void setAccession(String accession) {
        this.accession = accession;
    }

    /**
     * @return the accession
     */
    public String getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the date to set
     */
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the inchikey
     */
    public String getInchikey() {
        return inchikey;
    }

    /**
     * @param inchikey the inchikey to set
     */
    public void setInchikey(String inchikey) {
        this.inchikey = inchikey;
    }

    /**
     * @return the inchi
     */
    public String getInchi() {
        return inchi;
    }

    /**
     * @param inchi the inchi to set
     */
    public void setInchi(String inchi) {
        this.inchi = inchi;
    }

    /**
     * @return the chebiId
     */
    public String getChebiId() {
        return chebiId;
    }


    /**
     * @param chebiId the chebiId to set
     */
    public void setChebiId(String chebiId) {
        this.chebiId = chebiId;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getIupacNames() {
        return iupacNames;
    }

    public void setIupacNames(String iupacNames) {
        this.iupacNames = iupacNames;
    }

    public String getStudyStatus() {
        return studyStatus;
    }

    public void setStudyStatus(String studyStatus) {
        this.studyStatus = studyStatus;
    }

    public boolean getHasSpecies() {
        return hasSpecies;
    }

    public void setHasSpecies(boolean hasSpecies) {
        this.hasSpecies = hasSpecies;
    }

    public boolean getHasPathways() {
        return hasPathways;
    }

    public void setHasPathways(boolean hasPathways) {
        this.hasPathways = hasPathways;
    }

    public boolean getHasNMR() {
        return hasNMR;
    }

    public void setHasNMR(boolean hasNMR) {
        this.hasNMR = hasNMR;
    }

    public boolean getHasMS() {
        return hasMS;
    }

    public void setHasMS(boolean hasMS) {
        this.hasMS = hasMS;
    }

    public boolean getHasReactions() {
        return hasReactions;
    }

    public void setHasReaction(boolean hasReactions) {
        this.hasReactions = hasReactions;
    }

    public boolean getHasLiterature() {
        return hasLiterature;
    }

    public void setHasLiterature(boolean hasLiterature) {
        this.hasLiterature = hasLiterature;
    }

    @Override
    public boolean equals(Object obj){

        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof MetaboLightsCompoundModel))
            return false;

        MetaboLightsCompoundModel comp1 = (MetaboLightsCompoundModel)obj;

        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                        append(accession, comp1.accession).
                isEquals();

    }

}
