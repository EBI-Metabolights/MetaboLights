package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.StudyFactor;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Utilities;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 18/09/15.
 */
public class FactorValidations {

    public static Collection<Validation> getValidations(Study study) {
        Collection<Validation> factorValidations = new LinkedList<>();
        factorValidations.add(getFactorNameValidation(study));
        return factorValidations;
    }


    public static Validation getFactorNameValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.FACTOR_NAME, Requirement.MANDATORY, Group.FACTORS);
        if (!study.getFactors().isEmpty()) {
            for (StudyFactor studyFactor : study.getFactors()) {
                if (!Utilities.minCharRequirementPassed(studyFactor.getName(), 3)) {
                    validation.setMessage("Study Factor " + studyFactor.getName() + "is not valid");
                    validation.setPassedRequirement(false);
                }
            }
        } else {
            validation.setMessage("No study factor information is provided");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }

    /*
    TODO Factor type validation
     */

}
