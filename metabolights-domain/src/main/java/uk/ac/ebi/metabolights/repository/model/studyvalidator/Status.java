package uk.ac.ebi.metabolights.repository.model.studyvalidator;

/**
 * Created by kalai on 18/09/15.
 */
public enum Status {
    RED(1),
    AMBER(2),
    GREEN(3);

    private final int status;

    Status(final int validationStatus) {
        status = validationStatus;
    }

    public int getStatus() { return status; }

}
