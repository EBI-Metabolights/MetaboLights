package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.utils.validation.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 01/10/15.
 */

public class IsatabValidations implements IValidationProcess{

    @Override
    public String getAbout() {
        return Group.ISATAB.toString();
    }

    public  Collection<Validation> getValidations(Study study) {
        Collection<Validation> isatabValidations = new LinkedList<>();
        isatabValidations.add(getIsatabInvestigationFileStructureValidation(study));
        return isatabValidations;
    }

    public static Validation getIsatabInvestigationFileStructureValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.ISATAB_INVESTIGATION, Requirement.MANDATORY, Group.ISATAB);
        if (!study.getIsatabErrorMessages().isEmpty()) {
            String errMsgs = "";
            for (String msg : study.getIsatabErrorMessages()) {
                errMsgs += msg + "\n";
            }
            validation.setMessage(errMsgs);
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }
}
