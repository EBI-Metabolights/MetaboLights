package uk.ac.ebi.metabolights.repository.utils;

import uk.ac.ebi.metabolights.repository.model.Organism;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.Validation;
import uk.ac.ebi.metabolights.repository.model.Validations;

import java.util.Collection;

/**
 * Created by kalai on 07/07/15.
 */
public class StudyValidationUtils {

    public static void validate(Study study) {
        Validations validations = new Validations();
        validations.getEntries().add(organismCheck(study));
        //TODO other validations

        study.setValidations(validations);

    }

    public static Validation organismCheck(Study study) {
        Collection<Organism> organisms = study.getOrganism();

        Validation aValidation = new Validation();
        aValidation.setDescription("Organism");
        aValidation.setType(Validation.Requirement.MANDATORY);
        aValidation.setPassedRequirement(!organisms.isEmpty());
        return aValidation;
    }
}
