package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 18/09/15.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY, property="@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value=FactorValidations.class, name="FactorValidations"),
        @JsonSubTypes.Type(value= StudyValidations.class, name="StudyValidations"),
        @JsonSubTypes.Type(value= PublicationValidations.class, name="PublicationValidations"),
        @JsonSubTypes.Type(value= OrganismValidations.class, name="OrganismValidations"),
        @JsonSubTypes.Type(value= ExceptionValidations.class, name="ExceptionValidations"),
        @JsonSubTypes.Type(value= IsatabValidations.class, name="IsatabValidations"),
        @JsonSubTypes.Type(value= AssayValidations.class, name="AssayValidations"),
        @JsonSubTypes.Type(value= ProtocolValidations.class, name="ProtocolValidations"),
        @JsonSubTypes.Type(value= SampleValidations.class, name="SampleValidations")
})
public abstract class ValidationGroup {

    public ValidationGroup(Group groupName, Study study ){
         this.groupName = groupName;
         this.study = study;
    }

    public ValidationGroup(){

    }

    public ValidationGroup(Group groupName){
        this.groupName = groupName;
    }

    private Collection<uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation> validations = new LinkedList<>();

    private Group groupName;

    public static Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public static Study study;

    public Group getGroupName() {
        return groupName;
    }

    public void setGroupName(Group groupName) {
        this.groupName = groupName;
    }

    public Collection<Validation> getValidations() {
        return validations;
    }

    public void setValidations(Collection<Validation> validations) {
        this.validations = validations;
    }



    abstract public Collection<Validation> isValid(Study study);
}
