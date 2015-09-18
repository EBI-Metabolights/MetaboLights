package uk.ac.ebi.metabolights.repository.model.studyvalidator;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kalai on 18/09/15.
 */
public class FieldConstants {

    public static final String STATUS = "Status";
    public static final String VALIDATION_DESCRIPTION = "Description";
    public static final String VALIDATION_REQUIREMENT = "Requirement";
    public static final String VALIDATION_GROUP = "Group";
    public static final String VALIDATION_MESSAGE = "Message";

    public static List<String> getFieldHeaders() {
        List<String> fieldHeaders = new LinkedList<>();
        fieldHeaders.add(STATUS);
        fieldHeaders.add(VALIDATION_DESCRIPTION);
        fieldHeaders.add(VALIDATION_REQUIREMENT);
        fieldHeaders.add(VALIDATION_GROUP);
        fieldHeaders.add(VALIDATION_MESSAGE);
        return fieldHeaders;
    }


}
