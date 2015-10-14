package uk.ac.ebi.metabolights.repository.utils.validation;

import uk.ac.ebi.metabolights.repository.model.studyvalidator.Status;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.ArrayList;
import java.util.Collection;
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

    public static boolean checkPassedMinimumRequirement(Collection<Validation> validations) {
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
        return red > 0 ? false : orange > 0 ? true : true;

    }

    public static boolean minCharRequirementPassed(String toCheck, int limit) {

        // Test for null values
        if (toCheck == null) return false;
        return toCheck.length() >= limit;
    }

    public static List<String> getNonUnicodeCharacters(String s) {
        final List<String> result = new ArrayList<String>();
        for (int i = 0, n = s.length(); i < n; i++) {
            final String character = s.substring(i, i + 1);
            int other_Symbol = Character.OTHER_SYMBOL;
            int this_ = Character.getType(character.charAt(0));
            // 24 is for ? mark
            if (other_Symbol == this_ || (24 == this_)) {
                result.add(character);
            }
        }
        return result;
    }
}
