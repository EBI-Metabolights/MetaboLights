package uk.ac.ebi.metabolights.repository.model.studyvalidator;

import uk.ac.ebi.metabolights.repository.model.*;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 18/09/15.
 */
public class OverallValidation {


    public OverallValidation(Table table, Status status){
        this.validationEntries = table;
        this.status = status;
    }

    private Table validationEntries = new Table();


    private Status status = Status.GREEN;

    private boolean passedMinimumRequirement = false;


    public boolean isPassedMinimumRequirement() {
        return passedMinimumRequirement;
    }

    public void setPassedMinimumRequirement(boolean passedMinimumRequirement) {
        this.passedMinimumRequirement = passedMinimumRequirement;
    }

    public Table getValidationEntries() {
        return validationEntries;
    }

    public void setValidationEntries(Table validationEntries) {
        this.validationEntries = validationEntries;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
