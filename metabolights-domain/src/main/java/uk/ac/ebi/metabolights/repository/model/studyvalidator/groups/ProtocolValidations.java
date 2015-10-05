package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import uk.ac.ebi.metabolights.repository.model.Protocol;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Utilities;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kalai on 01/10/15.
 */

public class ProtocolValidations {

    public static Collection<Validation> getValidations(Study study) {
        Collection<Validation> protocolValidations = new LinkedList<>();
        protocolValidations.add(getMinimumProtocolValidation(study));
        protocolValidations.add(getComprehensiveProtocolValidation(study));
        return protocolValidations;

    }


    public static Validation getMinimumProtocolValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.PROTOCOLS_MINIMUM, Requirement.MANDATORY, Group.PROTOCOLS);
        if (!study.getProtocols().isEmpty()) {
            int notPassed = 0;
            int passed = 0;
            for (Protocol protocol : study.getProtocols()) {
                if (!Utilities.minCharRequirementPassed(protocol.getDescription(), 3)) {
                    notPassed++;
                } else {
                    passed++;
                }
            }
            if (notPassed == study.getProtocols().size()) {
                validation.setMessage("No details provided");
                validation.setPassedRequirement(false);
            }
            if (passed < 4) {
                validation.setMessage("Minimum 4 fields for Protocols has to be filled in");
                validation.setPassedRequirement(false);
            }
        } else {
            validation.setMessage("Protocols is empty");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;

    }


    public static Validation getComprehensiveProtocolValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.PROTOCOLS_ALL, Requirement.OPTIONAL, Group.PROTOCOLS);
        if (!study.getProtocols().isEmpty()) {
            List<String> emptyProtocolFields = new ArrayList<>();
            for (Protocol protocol : study.getProtocols()) {
                if (!Utilities.minCharRequirementPassed(protocol.getDescription(), 3)) {
//                    validation.setMessage("Protocol description is not sufficient or not all required fields are provided. Example:"
//                            + "\"" + protocol.getName() + "\" is either not provided or not sufficiently described.");
                    emptyProtocolFields.add(protocol.getName());
//                    validation.setPassedRequirement(false);
                }
            }
            if (emptyProtocolFields.size() > 0) {
                validation.setPassedRequirement(false);
                validation.setMessage(getErrMessage(emptyProtocolFields));
            }
        } else {
            validation.setMessage("Protocols is empty");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }

    private static String getErrMessage(List<String> emptyProtocolFields) {
        String errMessage = "Protocol description is not sufficient or not all required fields are provided for field(s):";
        for (int i = 0; i < emptyProtocolFields.size(); i++) {
            errMessage += " " + emptyProtocolFields.get(i);
            if (i < emptyProtocolFields.size() - 1) {
              errMessage +=",";
            }
        }
        //errMessage += " has not been described.";
        return errMessage;
    }
}
