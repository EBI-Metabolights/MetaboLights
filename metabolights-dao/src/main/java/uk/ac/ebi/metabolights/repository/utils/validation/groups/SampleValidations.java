package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;
import uk.ac.ebi.metabolights.repository.utils.validation.DescriptionConstants;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 01/10/15.
 */

public class SampleValidations implements IValidationProcess{

    @Override
    public String getAbout() {
        return Group.SAMPLES.toString();

    }

    public  Collection<Validation> getValidations(Study study) {
        Collection<Validation> sampleValidations = new LinkedList<>();
        sampleValidations.add(getSampleValidation(study));
        return sampleValidations;

    }

    public static Validation getSampleValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.SAMPLES, Requirement.MANDATORY, Group.SAMPLES);
        if (study.getSampleTable().getData().isEmpty()) {
            validation.setMessage("No sample data is provided");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }
}
