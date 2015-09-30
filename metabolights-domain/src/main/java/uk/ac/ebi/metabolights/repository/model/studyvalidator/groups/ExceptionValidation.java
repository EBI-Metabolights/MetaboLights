package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;

/**
 * Created by kalai on 30/09/15.
 */
@JsonTypeName("ExceptionValidation")
@JsonIgnoreProperties(ignoreUnknown = true)

public class ExceptionValidation extends ValidationGroup {

    public static Exception exception;

    public ExceptionValidation(Group group) {
        super(group);
        getValidations().add(new UnexpectedExceptionValidation());
    }

    public ExceptionValidation(Group group, Exception exception) {
        super(group);
        this.exception = exception;
        getValidations().add(new UnexpectedExceptionValidation());
    }

    public ExceptionValidation() {

    }

    @Override
    public Collection<Validation> isValid(Study study) {
        setStudy(study);
        for (Validation validation : getValidations()) {
            validation.setPassedRequirement(validation.hasPassed());
            validation.setStatus();
            validation.setMessage("We could NOT successfully run all the validations. Some validations might have passed." +
                    " There was an exception during the validation: " +
                    exception.getMessage() + ", " +
                    exception.getClass().getName());
        }
        return getValidations();
    }

    @JsonTypeName("UnexpectedExceptionValidation")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UnexpectedExceptionValidation extends Validation {
        @JsonCreator
        public UnexpectedExceptionValidation() {
            super(DescriptionConstants.EXCEPTION, Requirement.MANDATORY, Group.EXCEPTION);
        }

        @Override
        public boolean hasPassed() {
            return false;
        }
    }


}
