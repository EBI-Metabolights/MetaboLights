package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Utilities;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;

/**
 * Created by kalai on 18/09/15.
 */
@JsonTypeName("StudyValidation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyValidation extends ValidationGroup {


    public StudyValidation(Group group) {
        super(group);
        getValidations().add(new StudyTitleValidation());
        getValidations().add(new StudyDescriptionValidation());
        getValidations().add(new StudyDesignDescriptorsValidation());
        getValidations().add(new MinimumStudyValidation());
    }

    public StudyValidation(Group group, Study study) {
        super(group, study);
    }

    public StudyValidation(){
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

    @JsonTypeName("StudyTitleValidation")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StudyTitleValidation extends Validation {
        @JsonCreator
        public StudyTitleValidation() {
            super(DescriptionConstants.STUDY_TITLE, Requirement.MANDATORY, Group.STUDY);
        }

        @Override
        public boolean hasPassed() {
           return Utilities.minCharRequirementPassed(
                   getStudy().getTitle(), 15
           );
        }
    }

    @JsonTypeName("StudyDescriptionValidation")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StudyDescriptionValidation extends Validation {
        @JsonCreator
        public StudyDescriptionValidation() {
            super(DescriptionConstants.STUDY_DESCRIPTION, Requirement.MANDATORY, Group.STUDY);
        }

        @Override
        public boolean hasPassed() {
            return true;
        }
    }

    @JsonTypeName("StudyDesignDescriptorsValidation")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StudyDesignDescriptorsValidation extends Validation {
        @JsonCreator
        public StudyDesignDescriptorsValidation() {
            super(DescriptionConstants.STUDY_DESIGN_DESCRIPTORS, Requirement.MANDATORY, Group.STUDY);
        }

        @Override
        public boolean hasPassed() {
            return true;
        }
    }

    @JsonTypeName("MinimumStudyValidation")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MinimumStudyValidation extends Validation {
        @JsonCreator
        public MinimumStudyValidation() {
            super(DescriptionConstants.STUDY_MAX_ONE, Requirement.MANDATORY, Group.STUDY);
        }

        @Override
        public boolean hasPassed() {
            return true;
        }
    }
}
