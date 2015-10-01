package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import uk.ac.ebi.metabolights.repository.model.Protocol;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Utilities;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;

/**
 * Created by kalai on 01/10/15.
 */
@JsonTypeName("ProtocolValidations")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProtocolValidations extends ValidationGroup {
    public ProtocolValidations(Group group) {
        super(group);
        getValidations().add(new ProtocolValidation());
    }

    public ProtocolValidations() {

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

    @JsonTypeName("ProtocolValidation")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProtocolValidation extends Validation {
        public ProtocolValidation() {
            super(DescriptionConstants.PROTOCOLS, Requirement.MANDATORY, Group.PROTOCOLS);
        }

        @Override
        public boolean hasPassed() {
            if (!getStudy().getProtocols().isEmpty()) {
                for (Protocol protocol : getStudy().getProtocols()) {
                    if (!Utilities.minCharRequirementPassed(protocol.getDescription(), 3)) {
                        setMessage("Protocol description is not sufficient or not all required fields are provided. Example:\"" + "\n"
                        + protocol.getName() + "\" is either not provided or not sufficiently described");
                        return false;
                    }
                }
                return true;
            } else {
                setMessage("Protocols is empty");
                return false;
            }
        }
    }
}
