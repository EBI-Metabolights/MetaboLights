package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.StudyFactor;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.utils.validation.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.utils.validation.Utilities;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 18/09/15.
 */
public class FactorValidations implements IValidationProcess{

    @Override
    public String getAbout() {
        return Group.FACTORS.toString();
    }

    public  Collection<Validation> getValidations(Study study) {
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
            validation.setMessage("No Study Factor information is provided");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }

    /*
    TODO Factor type validation
     */

}
