package uk.ac.ebi.metabolights.repository.model.studyvalidator;


import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kalai on 25/06/15.
 */
public class Validations {

    private Collection<Validation> entries = new LinkedList<>();

    private Status status = Status.GREEN;

    private boolean passedMinimumRequirement = false;

    public Collection<Validation> getEntries() {
        return entries;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isPassedMinimumRequirement() {
        return passedMinimumRequirement;
    }

    public void setPassedMinimumRequirement(boolean passedMinimumRequirement) {
        this.passedMinimumRequirement = passedMinimumRequirement;
    }


}
