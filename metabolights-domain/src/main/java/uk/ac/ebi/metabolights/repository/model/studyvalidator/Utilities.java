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

    public static Table convertValidationsListToTable(Collection<Validation> validations) {
        Table table = new Table(
                getData(validations),
                getFields());

        return table;
    }

    private static LinkedHashMap<String, Field> getFields() {
        LinkedHashMap<String, Field> tableFieldsMap = new LinkedHashMap();
        for (int i = 0; i < FieldConstants.getFieldHeaders().size(); i++) {
            Field field = new Field(FieldConstants.getFieldHeaders().get(i), i, "basic");
            field.setHeader("test");
            tableFieldsMap.put(FieldConstants.getFieldHeaders().get(i),
                    field);
        }
        return tableFieldsMap;
    }

    private static List<List<String>> getData(Collection<Validation> validations) {
        List<List<String>> tableData = new ArrayList<List<String>>();
        while (validations.iterator().hasNext()) {
            Validation validation = validations.iterator().next();
            List<String> row = new ArrayList<>();
            row.add(validation.getStatus().toString());
            row.add(validation.getDescription());
            row.add(validation.getType().toString());
            row.add(validation.getGroup().toString());
            row.add(validation.getMessage());
            tableData.add(row);
        }
        return tableData;
    }

    public static Status checkOverallStatus(Collection<Validation> validations) {
        int red = 0;
        int orange = 0;
        while (validations.iterator().hasNext()) {
            Validation validation = validations.iterator().next();
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
