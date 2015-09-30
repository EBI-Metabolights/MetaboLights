package uk.ac.ebi.metabolights.repository.model.studyvalidator;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.groups.*;

/**
 * Created by kalai on 18/09/15.
 * Extending classes must be made static and annotated with JsonTypeInfo for serialization and de-serialization to work
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FactorValidation.FactorNameValidation.class, name = "FactorName"),
        @JsonSubTypes.Type(value = FactorValidation.FactorTypeValidation.class, name = "FactorType"),
        @JsonSubTypes.Type(value = StudyValidation.MinimumStudyValidation.class, name = "MinimumStudyValidation"),
        @JsonSubTypes.Type(value = StudyValidation.StudyDescriptionValidation.class, name = "StudyDescriptionValidation"),
        @JsonSubTypes.Type(value = StudyValidation.StudyDesignDescriptorsValidation.class, name = "StudyDesignDescriptorsValidation"),
        @JsonSubTypes.Type(value = StudyValidation.StudyTitleValidation.class, name = "StudyTitleValidation"),
        @JsonSubTypes.Type(value = PublicationValidation.PublicationAuthorValidation.class, name = "PublicationAuthorValidation"),
        @JsonSubTypes.Type(value = PublicationValidation.PublicationIDsValidation.class, name = "PublicationIDsValidation"),
        @JsonSubTypes.Type(value = PublicationValidation.PublicationTitleValidation.class, name = "PublicationTitleValidation"),
        @JsonSubTypes.Type(value = OrganismValidation.OrganismNameValidation.class, name = "OrganismNameValidation"),
        @JsonSubTypes.Type(value = ExceptionValidation.UnexpectedExceptionValidation.class, name = "UnexpectedExceptionValidation")
})
public abstract class Validation {

    public Validation(String description, Requirement type) {
        this.description = description;
        this.type = type;
    }

    public Validation(String description, Requirement type, Group group) {
        this.description = description;
        this.type = type;
        this.group = group;
    }

    public Validation() {
    }

    private String description;

    private Status status = Status.RED;

    private Group group;

    private boolean passedRequirement;

    private Requirement type = Requirement.MANDATORY;


    private String message = "OK";

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    abstract public boolean hasPassed();

    public void setStatus() {
        if (getPassedRequirement()) {
            setStatus(Status.GREEN);
        } else {
            if (getType().equals(Requirement.MANDATORY)) {
                setStatus(Status.RED);
            } else {
                setStatus(Status.ORANGE);
            }
        }

    }
}
