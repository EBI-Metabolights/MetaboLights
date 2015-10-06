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
import java.util.LinkedList;

/**
 * Created by kalai on 18/09/15.
 */
public class StudyValidations {


    public static Collection<Validation> getValidations(Study study) {
        Collection<Validation> studyValidations = new LinkedList<>();
        studyValidations.add(getStudyTitleValidation(study));
        studyValidations.add(getStudyDescriptionValidation(study));

        return studyValidations;

    }

    public static Validation getStudyTitleValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.STUDY_TITLE, Requirement.MANDATORY, Group.STUDY);
        if (!Utilities.minCharRequirementPassed(
                study.getTitle(), 15)) {
            validation.setMessage("Study title is too short");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }

    public static Validation getStudyDescriptionValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.STUDY_DESCRIPTION, Requirement.MANDATORY, Group.STUDY);
        if (!Utilities.minCharRequirementPassed(
                study.getDescription(), 30)) {
            validation.setMessage("Study description is too short");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }

   /*
   TODO: StudyDesignDescriptorsValidation, MinimumStudyValidation
    */


}
