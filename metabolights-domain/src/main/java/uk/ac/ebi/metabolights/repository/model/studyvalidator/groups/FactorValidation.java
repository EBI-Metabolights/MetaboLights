package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;

/**
 * Created by kalai on 18/09/15.
 */
public class FactorValidation extends ValidationGroup {
    public FactorValidation(Group group) {
        super(group);
        getValidations().add(new FactorNameValidation());
        getValidations().add(new FactorTypeValidation());
    }

    @Override

    public Collection<Validation> isValid(Study study) {
        return null;
    }

    public class FactorNameValidation extends Validation {
        public FactorNameValidation() {
            super(DescriptionConstants.FACTOR_NAME, Requirement.MANDATORY, getGroupName());
        }
        @Override
        public boolean hasPassed() {
            return false;
        }
    }

    public class FactorTypeValidation extends Validation {
        public FactorTypeValidation() {
            super(DescriptionConstants.FACTOR_TYPE, Requirement.MANDATORY, getGroupName());
        }
        @Override
        public boolean hasPassed() {
            return false;
        }
    }
}
