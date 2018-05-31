package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import uk.ac.ebi.metabolights.repository.model.Assay;
import uk.ac.ebi.metabolights.repository.model.Protocol;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.ValidationIdentifier;
import uk.ac.ebi.metabolights.repository.utils.validation.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.utils.validation.Utilities;

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

        //Need to check if there are MS and/or MNR (imaging) assays in the study.
        Boolean imaging = isImagingStudy(study), isNMR = false, isMS = false;
        List<Assay> assays = study.getAssays();

        for (int i = 0; i < assays.size(); i++) {
            Assay assay = assays.get(i);

            if (assay.getTechnology().equals("mass spectrometry"))
                isMS = true;

            if (assay.getTechnology().equals("NMR spectroscopy"))
                isNMR = true;

        }

        Collection<Validation> protocolValidations = new LinkedList<>();
        Validation minimumProtocolValidation = getMinimumProtocolValidation(study);
        protocolValidations.add(minimumProtocolValidation);
        protocolValidations.add(getComprehensiveProtocolValidation(study));
        if (!imaging){  //This applies to both NMR and MS imaging studies
            protocolValidations.add(getMandatoryCollectionProtocolValidation(study, "Extraction", DescriptionConstants.PROTOCOLS_EXTRACTION, ValidationIdentifier.PROTOCOLS_EXTRACTION));   //Protocol "Extraction"
        }
        protocolValidations.add(getMandatoryCollectionProtocolValidation(study, "Data transformation", DescriptionConstants.PROTOCOLS_DATA_EXTRACTION, ValidationIdentifier.PROTOCOLS_DATA_EXTRACTION));   //Protocol "Data transformation"
        protocolValidations.add(getMandatoryCollectionProtocolValidation(study, "Metabolite identification", DescriptionConstants.MAF_PROTOCOLS, ValidationIdentifier.MAF_PROTOCOLS));   //Protocol "Metabolite identification"

        if (isMS) {
            protocolValidations.add(getMandatoryCollectionProtocolValidation(study, "Mass spectrometry", DescriptionConstants.MS_PROTOCOLS_MASS_SPEC, ValidationIdentifier.MS_PROTOCOLS_MASS_SPEC));   //Protocol "Mass spectrometry"
            if (imaging) { //MS Imaging
                protocolValidations.add(getMandatoryCollectionProtocolValidation(study, "Histology", DescriptionConstants.PROTOCOLS_HISTOLOGY, ValidationIdentifier.PROTOCOLS_HISTOLOGY));   //Protocol "Histology"
                protocolValidations.add(getMandatoryCollectionProtocolValidation(study, "Preparation", DescriptionConstants.PROTOCOLS_PREPARATION, ValidationIdentifier.PROTOCOLS_PREPARATION));   //Protocol "Preparation"
            } else {
                protocolValidations.add(getMandatoryCollectionProtocolValidation(study, "Chromatography", DescriptionConstants.MS_PROTOCOLS_CROMA, ValidationIdentifier.MS_PROTOCOLS_CROMA));    //Protocol "Chromatography"}
            }
        } else if (isNMR) {
            if (imaging) {  //Magnetic resonance imaging
                protocolValidations.add(getMandatoryCollectionProtocolValidation(study, "Magnetic resonance imaging", DescriptionConstants.NMR_PROTOCOLS_NMR_MRI, ValidationIdentifier.NMR_PROTOCOLS_NMR_MRI));   //Protocol "Magnetic resonance imaging"
                protocolValidations.add(getMandatoryCollectionProtocolValidation(study, "In vivo magnetic resonance spectroscopy", DescriptionConstants.NMR_PROTOCOLS_IN_VIVO_MRS, ValidationIdentifier.NMR_PROTOCOLS_IN_VIVO_MRS));   //Protocol  "In vivo magnetic resonance spectroscopy"
                protocolValidations.add(getMandatoryCollectionProtocolValidation(study, "In vivo magnetic resonance assay", DescriptionConstants.NMR_PROTOCOLS_NMR_IN_VIVO_ASSAY, ValidationIdentifier.NMR_PROTOCOLS_NMR_IN_VIVO_ASSAY));   //Protocol "In vivo magnetic resonance assay"
            } else {
                protocolValidations.add(getMandatoryCollectionProtocolValidation(study, "NMR sample", DescriptionConstants.NMR_PROTOCOLS_NMR_SAMPLE, ValidationIdentifier.NMR_PROTOCOLS_NMR_SAMPLE));   //Protocol "NMR sample"
                protocolValidations.add(getMandatoryCollectionProtocolValidation(study, "NMR spectroscopy", DescriptionConstants.NMR_PROTOCOLS_SPECTROSCOPY, ValidationIdentifier.NMR_PROTOCOLS_SPECTROSCOPY));   //Protocol  "NMR spectroscopy"
                protocolValidations.add(getMandatoryCollectionProtocolValidation(study, "NMR assay", DescriptionConstants.NMR_PROTOCOLS_NMR_ASSAY, ValidationIdentifier.NMR_PROTOCOLS_NMR_ASSAY));   //Protocol "NMR assay"
            }
        }


        if (!study.getSampleTable().getData().isEmpty()) {
            protocolValidations.add(getSampleCollectionProtocolValidation(study));
        }
        if (!study.getProtocols().isEmpty()) {
            protocolValidations.add(protocolsDecodeIsSuccessfulValidation(study));
        }

        //Set<Validation> noDups = new LinkedHashSet<>(protocolValidations);    //Return unique values only;

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
        if (!protocolIsPresent(study, "Sample collection")) {
            validation.setPassedRequirement(false);
            validation.setMessage("Sample data is provided but no \'Sample collection\' protocol is" +
                    " described");

        }

        return validation;
    }

    public static Validation getMandatoryCollectionProtocolValidation(Study study, String protocolName, String constantType, ValidationIdentifier validationIdentifier) {
        Validation validation = new Validation(constantType, Requirement.MANDATORY, Group.PROTOCOLS);
        validation.setId(validationIdentifier.getID());
        if (!protocolIsPresent(study, protocolName)) {
            validation.setPassedRequirement(false);
            validation.setMessage(protocolName + " protocol is missing");

        }

        return validation;
    }

    public Boolean isImagingStudy(Study study){

        if (protocolIsPresent(study, "Magnetic resonance imaging"))  //Is this an NMR imaging study?
            return true;

        if (protocolIsPresent(study, "Histology"))  //Is this an NMR imaging study?
            return true;

        return false;
    }

    public static boolean metaboliteIdentificationProtocolIsPresent(Study study) {
        return mafProtocolfieldIsPresent(study.getProtocols(), "Metabolite identification");
    }

    public static int getMetaboliteIdentificationProtocolDescriptionSize(Study study) {
        return mafProtocolContentSize(study.getProtocols(), "Metabolite identification");
    }

    public static boolean protocolIsPresent(Study study, String protocolName) {
        return fieldIsPresent(study.getProtocols(), protocolName);
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

    private static boolean mafProtocolfieldIsPresent(Collection<Protocol> protocols, String fieldName) {
        if (protocols.isEmpty()) {
            return false;
        }
        for (Protocol protocol : protocols) {
            if (protocol.getName().toLowerCase().contains(fieldName.toLowerCase())) {
                if (protocol.getDescription().length() > 3) {
                    return true;
                }
            }
        }
        return false;

    }

    private static int mafProtocolContentSize(Collection<Protocol> protocols, String fieldName) {
        if (protocols.isEmpty()) {
            return -1;
        }
        for (Protocol protocol : protocols) {
            if (protocol.getName().toLowerCase().contains(fieldName.toLowerCase())) {
                return protocol.getDescription().length();
            }
        }
        return -1;
    }

    public static Validation protocolsDecodeIsSuccessfulValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.PROTOCOLS_TEXT, Requirement.OPTIONAL, Group.PROTOCOLS);
        validation.setId(ValidationIdentifier.PROTOCOLS_TEXT.getID());
        String message = "";
        for (Protocol protocol : study.getProtocols()) {
            if (!protocol.getDescription().isEmpty()) {
                List<String> notCoded = Utilities.getUnAcceptableCharacters(protocol.getDescription());
                if (notCoded.size() > 0) {
                    message += protocol.getName() + ", ";
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

