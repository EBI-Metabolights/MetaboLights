package uk.ac.ebi.metabolights.repository.utils.validation;

import uk.ac.ebi.metabolights.repository.model.studyvalidator.Status;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by kalai on 18/09/15.
 */
public class Utilities {

    public static final int[] exceptionalOtherSymbols = {8482, 176, 174, 8451, 8457, 8480, 8482, 9702, 9474};

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
            String character = s.substring(i, i + 1);
            int other_Symbol = Character.OTHER_SYMBOL;
            int this_ = Character.getType(character.charAt(0));

            if (other_Symbol == this_ || Arrays.equals(character.toCharArray(), Character.toChars(0x003F))) {
                int codePoint = character.codePointAt(0);
                if (!exceptional(codePoint)) {
                    result.add(character);
                }
            }

        }
        return result;
    }

    private static boolean exceptional(int code) {
        for (int i : exceptionalOtherSymbols) {
            if (i == code) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getFileNamesInDirectory(String studyDirectory) {
        File folder = new File(studyDirectory);
        File[] listOfFiles = folder.listFiles();
        List<String> possibleRawFiles = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {


            File file = listOfFiles[i];

            // If it's a file
            if (file.isFile()) {

                // Test the size
                if (file.length() > 0) {
                    possibleRawFiles.add(file.getName());
                }

                // It's a directory
            } else {
                // Add it...
                possibleRawFiles.add(file.getName());
            }
        }
        return possibleRawFiles;
    }
}
