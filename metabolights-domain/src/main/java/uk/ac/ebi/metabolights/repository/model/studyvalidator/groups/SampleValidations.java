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
@JsonTypeName("SampleValidations")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SampleValidations extends ValidationGroup {
    public SampleValidations(Group group) {
        super(group);
        getValidations().add(new SampleValidation());
    }

    public SampleValidations() {

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

    @JsonTypeName("SampleValidation")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SampleValidation extends Validation {
        public SampleValidation() {
            super(DescriptionConstants.SAMPLES, Requirement.MANDATORY, Group.SAMPLES);
        }

        @Override
        public boolean hasPassed() {
            if (getStudy().getSampleTable().getData().isEmpty()) {
                setMessage("Samples list is empty");
                return false;
            }
            return true;
        }
    }
}
