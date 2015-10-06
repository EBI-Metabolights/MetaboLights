package uk.ac.ebi.metabolights.repository.utils;

import uk.ac.ebi.metabolights.repository.model.Publication;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.*;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.groups.*;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 18/09/15.
 */
public class StudyValidationUtilities {

    public static Validations validate(Study study) {
        Collection<Validation> validationList = generateValidations(study);
        Validations validations = new Validations();
        validations.getEntries().addAll(validationList);
        Status status = Utilities.checkOverallStatus(validationList);
        validations.setStatus(status);
        validations.setPassedMinimumRequirement(Utilities.checkPassedMinimumRequirement(validationList));
        return validations;
    }


    private static Collection<Validation> generateValidations(Study study) {
        Collection<Validation> validations = new LinkedList<>();
        try {
            validations.addAll(StudyValidations.getValidations(study));
            validations.addAll(SampleValidations.getValidations(study));
            validations.addAll(PublicationValidations.getValidations(study));
            validations.addAll(ProtocolValidations.getValidations(study));
            validations.addAll(OrganismValidations.getValidations(study));
            validations.addAll(FactorValidations.getValidations(study));
            validations.addAll(AssayValidations.getValidations(study));
        } catch (Exception e) {
            validations.addAll(ExceptionValidations.getValidations(e));
        }
        return validations;
    }
}
