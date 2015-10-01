package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;

/**
 * Created by kalai on 01/10/15.
 */
@JsonTypeName("IsatabValidations")
@JsonIgnoreProperties(ignoreUnknown = true)
public class IsatabValidations extends ValidationGroup {

    public IsatabValidations(Group group) {
        super(group);
        getValidations().add(new IsatabInvestigationFileStructureValidation());
    }

    public IsatabValidations() {
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



    @JsonTypeName("IsatabInvestigationFileStructureValidation")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IsatabInvestigationFileStructureValidation extends Validation {

        public IsatabInvestigationFileStructureValidation() {
            super(DescriptionConstants.ISATAB_INVESTIGATION, Requirement.MANDATORY, Group.ISATAB);
        }

        @Override
        public boolean hasPassed() {
            if (!getStudy().getIsatabErrorMessages().isEmpty()) {
                String errMsgs = "";
                for (String msg : getStudy().getIsatabErrorMessages()) {
                    errMsgs += msg + "\n";
                }
                setMessage(errMsgs);
                return false;
            }
            return true;
        }
    }


}
