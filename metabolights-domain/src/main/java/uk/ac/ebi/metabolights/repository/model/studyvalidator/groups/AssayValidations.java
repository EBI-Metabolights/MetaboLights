package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Utilities;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 01/10/15.
 */

public class AssayValidations {

    public static Collection<Validation> getValidations(Study study) {
        Collection<Validation> assayValidations = new LinkedList<>();
        assayValidations.add(getAssayValidation(study));
        return assayValidations;
    }

    public static Validation getAssayValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.ASSAYS, Requirement.MANDATORY, Group.ASSAYS);
        if (study.getAssays().isEmpty()) {
            validation.setMessage("No assay data is provided");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }
}
