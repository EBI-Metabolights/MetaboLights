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
@JsonTypeName("AssayValidations")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssayValidations extends ValidationGroup {

    public AssayValidations(Group group) {
        super(group);
        getValidations().add(new AssayValidation());
    }

    public AssayValidations() {

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

    @JsonTypeName("AssayValidation")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AssayValidation extends Validation {
        public AssayValidation() {
            super(DescriptionConstants.ASSAYS, Requirement.MANDATORY, Group.ASSAYS);
        }

        @Override
        public boolean hasPassed() {
            if (getStudy().getAssays().isEmpty()) {
                setMessage("Assay list is empty");
                return false;
            }
            return true;
        }
    }
}
