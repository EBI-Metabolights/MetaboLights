package uk.ac.ebi.metabolights.repository.utils.validation;

import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Status;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;
import uk.ac.ebi.metabolights.repository.utils.validation.groups.*;

/**
 * Created by kalai on 18/09/15.
 */
public class StudyValidationUtilities {

    public static void validate(Study study) {


        validateStudy(study);
        Status status = Utilities.checkOverallStatus(study.getValidations().getEntries());
        study.getValidations().setStatus(status);
        study.getValidations().setPassedMinimumRequirement(Utilities.checkPassedMinimumRequirement(study.getValidations().getEntries()));

    }


    private static void validateStudy(Study study) {
        
        try {
            invokeValidationProcess(new StudyValidations(), study);
            invokeValidationProcess(new SampleValidations(), study);
            invokeValidationProcess(new PublicationValidations(), study);
            invokeValidationProcess(new ProtocolValidations(), study);
            invokeValidationProcess(new OrganismValidations(), study);
            invokeValidationProcess(new FactorValidations(), study);
            invokeValidationProcess(new AssayValidations(), study);
            invokeValidationProcess(new IsatabValidations(), study);

        } catch (Exception e) {

            AddValidationFromException(study,"Global validation failure", "We couldn't run all the validations: " + e.getMessage());
        }
    }

    private static void invokeValidationProcess(IValidationProcess validationProcess, Study study){

        try {
            study.getValidations().getEntries().addAll(validationProcess.getValidations(study));

        } catch (Exception e){

           AddValidationFromException(study, validationProcess.getAbout() +" validation failure", "Couldn't run all the " + validationProcess.getAbout() + " validations: " + e.getMessage());

        }
    }
    public static void AddValidationFromException(Study study, String description, String message){

        Validation exceptionValidation = new Validation();

        exceptionValidation.setDescription(description);
        exceptionValidation.setGroup(Group.EXCEPTION);
        exceptionValidation.setType(Requirement.MANDATORY);
        exceptionValidation.setMessage(message);
        exceptionValidation.setStatus(Status.RED);
        exceptionValidation.setPassedRequirement(false);

        study.getValidations().getEntries().add(exceptionValidation);
    }


}
