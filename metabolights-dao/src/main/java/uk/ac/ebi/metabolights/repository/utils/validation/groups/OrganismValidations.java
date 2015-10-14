package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import uk.ac.ebi.metabolights.repository.model.Organism;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.utils.validation.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.utils.validation.Utilities;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 30/09/15.
 */
public class OrganismValidations implements IValidationProcess{


    @Override
    public String getAbout() {
        return Group.ORGANISM.toString();
    }

    public Collection<Validation> getValidations(Study study) {
        Collection<Validation> organismValidations = new LinkedList<>();
        organismValidations.add(getOrganismNameValidation(study));
        organismValidations.add(getOrganismPartValidation(study));
        return organismValidations;
    }

    public static Validation getOrganismNameValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.ORGANISM_NAME, Requirement.MANDATORY, Group.ORGANISM);
        if (!study.getOrganism().isEmpty()) {
            for (Organism organism : study.getOrganism()) {
                if (!Utilities.minCharRequirementPassed(organism.getOrganismName(), 3)) {
                    validation.setMessage("Organism name is not valid");
                    validation.setPassedRequirement(false);
                }
            }

        } else {
            validation.setMessage("Organism is empty");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }

    public static Validation getOrganismPartValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.ORGANISM_PART, Requirement.MANDATORY, Group.ORGANISM);
        if (!study.getOrganism().isEmpty()) {
            for (Organism organism : study.getOrganism()) {
                if (!Utilities.minCharRequirementPassed(organism.getOrganismPart(), 3)) {
                    validation.setMessage("Organism part is not valid");
                    validation.setPassedRequirement(false);
                }
            }

        } else {
            validation.setMessage("Organism is empty");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }

}
