package uk.ac.ebi.metabolights.repository.model.studyvalidator;

import uk.ac.ebi.metabolights.repository.model.Field;
import uk.ac.ebi.metabolights.repository.model.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by kalai on 18/09/15.
 */
public class Utilities {

    public static Status checkOverallStatus(Collection<Validation> validations) {
        int red = 0;
        int orange = 0;
        for (Validation validation : validations) {
            if (validation.getStatus().equals(Status.ORANGE)) {
                orange++;
            }
            if (validation.getStatus().equals(Status.RED)) {
                red++;
            }
        }
        return red > 0 ? Status.RED : orange > 0 ? Status.ORANGE : Status.GREEN;

    }

    public static boolean minCharRequirementPassed(String toCheck, int limit) {
        return toCheck.length() >= limit;
    }
}
