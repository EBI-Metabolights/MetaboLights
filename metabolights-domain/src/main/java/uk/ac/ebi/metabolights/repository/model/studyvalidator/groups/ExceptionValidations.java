package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 30/09/15.
 */
public class ExceptionValidations {

    public static Collection<Validation> getValidations(Exception e) {
        Collection<Validation> exceptionValidations = new LinkedList<>();
        exceptionValidations.add(getUnexpectedExceptionValidation(e));
        return exceptionValidations;
    }

    public static Validation getUnexpectedExceptionValidation(Exception exception) {
        Validation validation = new Validation(DescriptionConstants.EXCEPTION, Requirement.MANDATORY, Group.EXCEPTION);
        validation.setMessage("Something went wrong here: " + exception.getMessage());
        validation.setPassedRequirement(false);
        validation.setStatus();
        return validation;
    }

    public static Validation getSuccessfulMetaDataLoadValidation(Exception exception, String validationDescription) {
        Validation validation = new Validation(validationDescription, Requirement.MANDATORY, Group.EXCEPTION);
        validation.setMessage("We could NOT successfully run all the validations. Some validations might have passed." +
                " There was an exception during the validation: " +
                exception.getMessage() + ", " +
                exception.getClass().getName());
        validation.setPassedRequirement(false);
        validation.setStatus();
        return validation;

    }


}
