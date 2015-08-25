package uk.ac.ebi.metabolights.repository.utils;

import uk.ac.ebi.metabolights.repository.dao.filesystem.metabolightsuploader.IsaTabReplacer;
import uk.ac.ebi.metabolights.repository.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;

/**
 * Created by kalai on 07/07/15.
 */
public class StudyValidationUtils {

    public static final String STUDY_IDENTIFIER = "Study identifier";
    public static final String ORGANISMS_INVOLVED = "Organism(s) specified";
    public static final String STUDY_TITLE = "Study title";
    public static final String STUDY_DESCRIPTION = "Study description";
    public static final String STUDY_FACTORS = "Study factors used in the experiment";
    public static final String PROTOCOLS = "Experimental protocol(s) defined";
    public static final String ASSAYS = "Assay(s) reported";
    public static final String SAMPLES = "Sample(s) reported";
    public static final String PUBLICATIONS = "Publication(s) associated with this study";
    static private String validateError = "\n You must make sure your study successfully passes the ISAcreator validation (file -> validate ISAtab) before resubmitting your study!";


    public static Collection<String> getMandatoryFields() {
        Collection<String> mandatoryFields = new LinkedList<>();
        mandatoryFields.add(ORGANISMS_INVOLVED);
        mandatoryFields.add(STUDY_DESCRIPTION);
        mandatoryFields.add(PROTOCOLS);
        mandatoryFields.add(ASSAYS);
        mandatoryFields.add(SAMPLES);
        return mandatoryFields;
    }

    public static void validate(Study study) {
        Validations validations = new Validations();

        validations.getEntries().add(organismCheck(study));
        // validations.getEntries().add(studyTitle(study));
        validations.getEntries().add(studyDescriptionCheck(study));
        validations.getEntries().add(studyFactorsCheck(study));

        validations.getEntries().add(protocolsCheck(study));
        validations.getEntries().add(samplesCheck(study));
        validations.getEntries().add(assaysCheck(study));
        validations.getEntries().add(publicationCheck(study));

        //TODO other validations
        // MAF file check
        // CHEBI iD present (database identifier) REQUIRED
        // Publication: IDS OPTIONAL


        validations.setPassedMinimumRequirement(check(validations));
        study.setValidations(validations);

    }

    private static boolean check(Validations validations) {
        for (String value : getMandatoryFields()) {
            boolean isPresentAndhasPassed = false;
            for (Validation validation : validations.getEntries()) {
                if (validation.getDescription().contentEquals(value)) {
                    isPresentAndhasPassed = validation.getPassedRequirement();
                }
            }
            if (!isPresentAndhasPassed) return isPresentAndhasPassed;
        }
        return true;
    }

    private static Validation getValidationObject(String description, Validation.Requirement requirement) {
        Validation aValidation = new Validation();
        aValidation.setDescription(description);
        aValidation.setType(requirement);
        return aValidation;
    }

    public static Validation studyIdentifierCheck(Study study) {
        Validation aValidation = getValidationObject(STUDY_IDENTIFIER, Validation.Requirement.MANDATORY);
        aValidation.setPassedRequirement(!study.getStudyIdentifier().isEmpty());
        return aValidation;
    }

    public static Validation organismCheck(Study study) {
        Validation aValidation = getValidationObject(ORGANISMS_INVOLVED, Validation.Requirement.MANDATORY);
//        boolean isPresent = !study.getOrganism().isEmpty();
        boolean isPresent = false;
        aValidation.setPassedRequirement(isPresent);
        if (!isPresent) {
            aValidation.setMessage("Organisms information is missing/incorrect. Also check for Bad syntax or incorrect character in s_file header: \"+\". "
            + validateError);
        }
        return aValidation;
    }

    public static Validation studyTitle(Study study) {
        Validation aValidation = getValidationObject(STUDY_TITLE, Validation.Requirement.MANDATORY);
        aValidation.setPassedRequirement(!study.getTitle().isEmpty());
        return aValidation;
    }

    public static Validation studyDescriptionCheck(Study study) {
        Validation aValidation = getValidationObject(STUDY_DESCRIPTION, Validation.Requirement.MANDATORY);
        boolean isPresent = !study.getDescription().isEmpty();
        aValidation.setPassedRequirement(isPresent);
        if (!isPresent) {
            aValidation.setMessage("The study description is either too short and is unavailable");
        }
        return aValidation;
    }

    public static Validation studyFactorsCheck(Study study) {
        Validation aValidation = getValidationObject(STUDY_FACTORS, Validation.Requirement.OPTIONAL);
        boolean isPresent = !study.getFactors().isEmpty();
        aValidation.setPassedRequirement(isPresent);
        if (!isPresent) {
            aValidation.setMessage("The experimental study factors are not specified");
        }
        return aValidation;
    }

    public static Validation protocolsCheck(Study study) {
        Validation aValidation = getValidationObject(PROTOCOLS, Validation.Requirement.MANDATORY);
        boolean isPresent = !study.getProtocols().isEmpty();
        //TODO   change variable being set
        aValidation.setPassedRequirement(false);
        isPresent = false;
        if (!isPresent) {
            aValidation.setMessage("The protocol columns are empty");
        }
        return aValidation;
    }

    public static Validation samplesCheck(Study study) {
        Validation aValidation = getValidationObject(SAMPLES, Validation.Requirement.MANDATORY);
        boolean isPresent = !study.getSampleTable().getFields().isEmpty();
        aValidation.setPassedRequirement(isPresent);
        if (!isPresent) {
            aValidation.setMessage("The sample columns are empty");
        }
        return aValidation;
    }

    public static Validation assaysCheck(Study study) {
        Validation aValidation = getValidationObject(ASSAYS, Validation.Requirement.MANDATORY);
        boolean isPresent = !study.getAssays().isEmpty();
        aValidation.setPassedRequirement(isPresent);
        if (!isPresent) {
            aValidation.setMessage("The assay columns are empty");
        }
        return aValidation;
    }

    public static Validation publicationCheck(Study study) {
        Validation aValidation = getValidationObject(PUBLICATIONS, Validation.Requirement.OPTIONAL);
        boolean isPresent = !study.getPublications().isEmpty();
        isPresent = false;
        aValidation.setPassedRequirement(false);
        if (!isPresent) {
            aValidation.setMessage("No publication is linked");
        }
        return aValidation;
    }
}
