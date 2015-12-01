package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import uk.ac.ebi.metabolights.repository.model.Protocol;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.ValidationIdentifier;
import uk.ac.ebi.metabolights.repository.utils.validation.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.utils.validation.Utilities;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kalai on 01/10/15.
 */

public class ProtocolValidations implements IValidationProcess {

    @Override
    public String getAbout() {
        return Group.PROTOCOLS.toString();
    }

    public Collection<Validation> getValidations(Study study) {
        Collection<Validation> protocolValidations = new LinkedList<>();
        Validation minimumProtocolValidation = getMinimumProtocolValidation(study);
        protocolValidations.add(minimumProtocolValidation);
        protocolValidations.add(getComprehensiveProtocolValidation(study));
        if (!study.getSampleTable().getData().isEmpty()) {
            protocolValidations.add(getSampleCollectionProtocolValidation(study));
        }
        if (!study.getProtocols().isEmpty()) {
            protocolValidations.add(protocolsDecodeIsSuccessfulValidation(study));
        }
        return protocolValidations;

    }


    public static Validation getMinimumProtocolValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.PROTOCOLS_MINIMUM, Requirement.MANDATORY, Group.PROTOCOLS);
        validation.setId(ValidationIdentifier.PROTOCOLS_MINIMUM.getID());
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
                validation.setMessage("Not enough protocols have descriptions");
                validation.setPassedRequirement(false);
            }
        } else {
            validation.setMessage("Protocols are empty");
            validation.setPassedRequirement(false);
        }
        return validation;

    }


    public static Validation getComprehensiveProtocolValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.PROTOCOLS_ALL, Requirement.OPTIONAL, Group.PROTOCOLS);
        validation.setId(ValidationIdentifier.PROTOCOLS_ALL.getID());
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
            validation.setMessage("Protocols are empty");
            validation.setPassedRequirement(false);
        }
        return validation;
    }

    private static String getErrMessage(List<String> emptyProtocolFields) {
        String errMessage = "Protocol description is not sufficiently detailed or not all required fields are provided. Missing field(s):";
        for (int i = 0; i < emptyProtocolFields.size(); i++) {
            errMessage += " " + emptyProtocolFields.get(i);
            if (i < emptyProtocolFields.size() - 1) {
                errMessage += ",";
            }
        }
        //errMessage += " has not been described.";
        return errMessage;
    }

    public static Validation getSampleCollectionProtocolValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.PROTOCOLS_SAMPLE_COLLECTION, Requirement.MANDATORY, Group.PROTOCOLS);
        validation.setId(ValidationIdentifier.PROTOCOLS_SAMPLE_COLLECTION.getID());
        if (!sampleCollectionProtocolIsPresent(study)) {
            validation.setPassedRequirement(false);
            validation.setMessage("Sample data is provided but no \"Sample collection\" protocol is" +
                    " described");

        }

        return validation;
    }

    public static boolean metaboliteIdentificationProtocolIsPresent(Study study) {
        return fieldIsPresent(study.getProtocols(), "Metabolite identification");
    }

    public static boolean sampleCollectionProtocolIsPresent(Study study) {
        return fieldIsPresent(study.getProtocols(), "Sample collection");
    }

    private static boolean fieldIsPresent(Collection<Protocol> protocols, String fieldName) {
        if (protocols.isEmpty()) {
            return false;
        }
        for (Protocol protocol : protocols) {
            if (protocol.getName().equalsIgnoreCase(fieldName)) {
                if (protocol.getDescription().length() > 3) {
                    return true;
                }
            }
        }
        return false;

    }

    public static Validation protocolsDecodeIsSuccessfulValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.PROTOCOLS_TEXT, Requirement.OPTIONAL, Group.PROTOCOLS);
        validation.setId(ValidationIdentifier.PROTOCOLS_TEXT.getID());
        String message = "";
        for (Protocol protocol : study.getProtocols()) {
            if (!protocol.getDescription().isEmpty()) {
                List<String> notCoded = Utilities.getNonUnicodeCharacters(protocol.getDescription());
                if (notCoded.size() > 0) {
                    message += protocol.getName() + ",\n";
                }
            }
        }
        if (!message.isEmpty()) {
            validation.setPassedRequirement(false);
            message += "contains characters that cannot be successfully parsed.";
            validation.setMessage(message);
        }

        return validation;

    }

}

