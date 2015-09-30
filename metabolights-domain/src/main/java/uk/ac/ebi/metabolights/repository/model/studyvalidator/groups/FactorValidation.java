package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;

/**
 * Created by kalai on 18/09/15.
 */
@JsonTypeName("FactorValidation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FactorValidation extends ValidationGroup {
    public FactorValidation(Group group) {
        super(group);
        getValidations().add(new FactorNameValidation());
        getValidations().add(new FactorTypeValidation());
    }

    public FactorValidation(){

    }

    @Override

    public Collection<Validation> isValid(Study study) {
        setStudy(study);
        for (Validation validation : getValidations()) {
            validation.setPassedRequirement(validation.hasPassed());
            validation.setStatus();
            validation.setMessage("Hello");
        }
        return getValidations();
    }

    @JsonTypeName("FactorName")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FactorNameValidation extends Validation {
        @JsonCreator
        public FactorNameValidation() {
            super(DescriptionConstants.FACTOR_NAME, Requirement.MANDATORY, Group.FACTORS);
        }
        @Override
        public boolean hasPassed() {
            return false;
        }
    }

    @JsonTypeName("FactorType")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FactorTypeValidation extends Validation {
        @JsonCreator
        public FactorTypeValidation() {
            super(DescriptionConstants.FACTOR_TYPE, Requirement.MANDATORY, Group.FACTORS);
        }
        @Override
        public boolean hasPassed() {
            return false;
        }
    }
}
