package uk.ac.ebi.metabolights.repository.model.studyvalidator;


import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 25/06/15.
 */
public class Validations {

    private Collection<Validation> entries = new LinkedList<>();

    private Status status = Status.RED;

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

    public boolean contains(Validation validation) {
        for (Validation v : entries) {
            if (v.getId() == validation.getId()) {
                return true;
            }
        }
        return false;
    }

    public Validation get(Integer id){
        for (Validation v : entries) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }


}
