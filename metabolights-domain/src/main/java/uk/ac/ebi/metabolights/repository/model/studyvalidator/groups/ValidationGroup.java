package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 18/09/15.
 */
public abstract class ValidationGroup {

    public ValidationGroup(Group groupName, Study study ){
         this.groupName = groupName;
         this.study = study;
    }

    public ValidationGroup(Group groupName){
        this.groupName = groupName;
    }

    private Collection<uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation> validations = new LinkedList<>();

    private Group groupName;

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    private Study study;

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
