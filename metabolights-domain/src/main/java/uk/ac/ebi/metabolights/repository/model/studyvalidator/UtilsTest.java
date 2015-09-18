package uk.ac.ebi.metabolights.repository.model.studyvalidator;

import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.Table;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.groups.FactorValidation;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.groups.Group;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.groups.PublicationValidation;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.groups.StudyValidation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 18/09/15.
 */
public class UtilsTest {

    public static OverallValidation validate(Study study) {
        Collection<Validation> validations = generateValidations(study);
        Table validationTable = Utilities.convertValidationsListToTable(validations);
        Status status = Utilities.checkOverallStatus(validations);
        return new OverallValidation(validationTable, status);
    }


    private static Collection<Validation> generateValidations(Study study) {
        Collection<Validation> validations = new LinkedList<>();

        for (Group group : Group.values()) {
            switch (group) {
                case STUDY:
                    validations.addAll(new StudyValidation(group).isValid(study));
                case PUBLICATION:
                    validations.addAll(new PublicationValidation(group).isValid(study));
//                case FACTORS:
//                    validations.addAll(new FactorValidation(group).isValid(study));
                    break;

            }
        }
        return validations;
    }


}
