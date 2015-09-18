package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Utilities;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;

/**
 * Created by kalai on 18/09/15.
 */
public class StudyValidation extends ValidationGroup {

    public StudyValidation(Group group, Study study) {
        super(group, study);
    }

    public StudyValidation(Group group) {
        super(group);
        getValidations().add(new StudyTitleValidation());
        getValidations().add(new StudyDescriptionValidation());
        getValidations().add(new StudyDesignDescriptorsValidation());
        getValidations().add(new MinimumStudyValidation());
    }


    @Override
    public Collection<Validation> isValid(Study study) {
        setStudy(study);
        for (Validation validation : getValidations()) {
            validation.setPassedRequirement(validation.hasPassed());
            validation.setStatus();
        }
        return getValidations();
    }


    public class StudyTitleValidation extends Validation {
        public StudyTitleValidation() {
            super(DescriptionConstants.STUDY_TITLE, Requirement.MANDATORY, getGroupName());
        }

        @Override
        public boolean hasPassed() {
           return Utilities.minCharRequirementPassed(
                    getStudy().getTitle(), 15
            );
        }
    }

    public class StudyDescriptionValidation extends Validation {
        public StudyDescriptionValidation() {
            super(DescriptionConstants.STUDY_DESCRIPTION, Requirement.MANDATORY, getGroupName());
        }

        @Override
        public boolean hasPassed() {
            return false;
        }
    }

    public class StudyDesignDescriptorsValidation extends Validation {
        public StudyDesignDescriptorsValidation() {
            super(DescriptionConstants.STUDY_DESIGN_DESCRIPTORS, Requirement.MANDATORY, getGroupName());
        }

        @Override
        public boolean hasPassed() {
            return false;
        }
    }

    public class MinimumStudyValidation extends Validation {
        public MinimumStudyValidation() {
            super(DescriptionConstants.STUDY_MAX_ONE, Requirement.MANDATORY, getGroupName());
        }

        @Override
        public boolean hasPassed() {
            return false;
        }
    }
}
