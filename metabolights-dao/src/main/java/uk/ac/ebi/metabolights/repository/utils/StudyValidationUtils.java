package uk.ac.ebi.metabolights.repository.utils;

import uk.ac.ebi.metabolights.repository.model.*;

import java.util.Collection;

/**
 * Created by kalai on 07/07/15.
 */
public class StudyValidationUtils {

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


        study.setValidations(validations);

    }

    private static Validation getValidationObject(String description, Validation.Requirement requirement) {
        Validation aValidation = new Validation();
        aValidation.setDescription(description);
        aValidation.setType(requirement);
        return aValidation;
    }

    public static Validation studyIdentifierCheck(Study study) {
        Validation aValidation = getValidationObject("Study identifier", Validation.Requirement.MANDATORY);
        aValidation.setPassedRequirement(!study.getStudyIdentifier().isEmpty());
        return aValidation;
    }

    public static Validation organismCheck(Study study) {
        Validation aValidation = getValidationObject("Organism(s) involved", Validation.Requirement.MANDATORY);
        aValidation.setPassedRequirement(!study.getOrganism().isEmpty());
        return aValidation;
    }

    public static Validation studyTitle(Study study) {
        Validation aValidation = getValidationObject("Study title", Validation.Requirement.MANDATORY);
        aValidation.setPassedRequirement(!study.getTitle().isEmpty());
        return aValidation;
    }

    public static Validation studyDescriptionCheck(Study study) {
        Validation aValidation = getValidationObject("Study description", Validation.Requirement.MANDATORY);
        aValidation.setPassedRequirement(!study.getDescription().isEmpty());
        return aValidation;
    }

    public static Validation studyFactorsCheck(Study study) {
        Validation aValidation = getValidationObject("Study factors used in the experiment", Validation.Requirement.MANDATORY);
        aValidation.setPassedRequirement(!study.getFactors().isEmpty());
        return aValidation;
    }

    public static Validation protocolsCheck(Study study) {
        Validation aValidation = getValidationObject("Experimental protocol(s) followed", Validation.Requirement.MANDATORY);
       // aValidation.setPassedRequirement(!study.getProtocols().isEmpty());
        aValidation.setPassedRequirement(false);
        return aValidation;
    }

    public static Validation samplesCheck(Study study) {
        Validation aValidation = getValidationObject("Sample(s) used", Validation.Requirement.MANDATORY);
        aValidation.setPassedRequirement(!study.getSampleTable().getFields().isEmpty());
        return aValidation;
    }

    public static Validation assaysCheck(Study study) {
        Validation aValidation = getValidationObject("Assay(s) carried out", Validation.Requirement.MANDATORY);
        aValidation.setPassedRequirement(!study.getAssays().isEmpty());
        return aValidation;
    }

    public static Validation publicationCheck(Study study) {
        Validation aValidation = getValidationObject("Publication(s) associated with this study", Validation.Requirement.MANDATORY);
        aValidation.setPassedRequirement(!study.getPublications().isEmpty());
        return aValidation;
    }
}
