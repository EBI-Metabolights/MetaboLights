package uk.ac.ebi.metabolights.repository.model;

/**
 * Created by kalai on 25/06/15.
 */
public class Validation {


    private String description;


    private boolean passedRequirement;
    private Requirement type = Requirement.MANDATORY;


    private String message;

    public enum Requirement {
        MANDATORY,
        OPTIONAL
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getPassedRequirement() {
        return passedRequirement;
    }

    public void setPassedRequirement(boolean passedRequirement) {
        this.passedRequirement = passedRequirement;
    }

    public Requirement getType() {
        return type;
    }

    public void setType(Requirement type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
