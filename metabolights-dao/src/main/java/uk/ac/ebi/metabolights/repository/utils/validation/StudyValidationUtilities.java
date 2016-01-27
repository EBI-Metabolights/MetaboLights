package uk.ac.ebi.metabolights.repository.utils.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.*;
import uk.ac.ebi.metabolights.repository.utils.ClobJsonUtils;
import uk.ac.ebi.metabolights.repository.utils.validation.groups.*;

/**
 * Created by kalai on 18/09/15.
 */
public class StudyValidationUtilities {

    private final static Logger logger = LoggerFactory.getLogger(StudyValidationUtilities.class.getName());
    private static Validations validationsFromDB = new Validations();

    public static void validate(Study study) {
        if (!hasInvalidIsaTab(study)) {
            validationsFromDB = study.getValidations();
            validateStudy(study);
            checkForOverriding(validationsFromDB, study.getValidations());
        }
        Status status = Utilities.checkOverallStatus(study.getValidations().getEntries());
        study.getValidations().setStatus(status);
        study.getValidations().setPassedMinimumRequirement(Utilities.checkPassedMinimumRequirement(study.getValidations().getEntries()));

    }

    private static boolean hasInvalidIsaTab(Study study) {
        for (Validation v : study.getValidations().getEntries()) {
            if (v.getDescription().equals("Study metadata load")) {
                return true;
            }
        }
        return false;
    }


    private static void validateStudy(Study study) {
        study.setValidations(new Validations());
        try {
            invokeValidationProcess(new StudyValidations(), study);
            invokeValidationProcess(new SampleValidations(), study);
            invokeValidationProcess(new PublicationValidations(), study);
            invokeValidationProcess(new ProtocolValidations(), study);
            invokeValidationProcess(new OrganismValidations(), study);
            invokeValidationProcess(new FactorValidations(), study);
            invokeValidationProcess(new AssayValidations(), study);
            invokeValidationProcess(new MafValidations(), study);
            invokeValidationProcess(new IsatabValidations(), study);

        } catch (Exception e) {

            AddValidationFromException(study, "Global validation failure", "We couldn't run all the validations: " + e.getMessage());
        }
    }

    private static void invokeValidationProcess(IValidationProcess validationProcess, Study study) {

        try {
            study.getValidations().getEntries().addAll(validationProcess.getValidations(study));
        } catch (Exception e) {

            AddValidationFromException(study, validationProcess.getAbout() + " validation failure", "Couldn't run all the " + validationProcess.getAbout() + " validations: " + e.getMessage());

        }
    }

    public static void AddValidationFromException(Study study, String description, String message) {

        Validation exceptionValidation = new Validation();

        exceptionValidation.setDescription(description);
        exceptionValidation.setGroup(Group.EXCEPTION);
        exceptionValidation.setType(Requirement.MANDATORY);
        exceptionValidation.setMessage(message);
        exceptionValidation.setPassedRequirement(false);

        study.getValidations().getEntries().add(exceptionValidation);
    }

    private static void checkForOverriding(Validations fromDB, Validations generatedNow) {
        if (fromDB == null) {
            return;
        }
        for (Validation current : generatedNow.getEntries()) {
            if (!current.getPassedRequirement()) {
                if (validationsFromDB.contains(current)) {
                    boolean overRiddenValue = validationsFromDB.get(current.getId()).getPassedRequirement();
                    boolean currentValue = current.getPassedRequirement();
                    if (overRiddenValue != currentValue) {
                        current.setPassedRequirement(overRiddenValue);
                        current.setOverriden(true);
                        generatedNow.setOverriden(true);
                    }
                }
            }
        }

    }


}
