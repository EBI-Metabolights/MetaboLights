package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import uk.ac.ebi.metabolights.repository.model.Organism;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.ValidationIdentifier;
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
        validation.setId(ValidationIdentifier.ORGANISM_NAME.getID());
        if (!study.getOrganism().isEmpty()) {
            for (Organism organism : study.getOrganism()) {
                if (!Utilities.minCharRequirementPassed(organism.getOrganismName(), 3)) {
                    validation.setMessage("Organism name is not valid");
                    validation.setPassedRequirement(false);
                }
                if (organism.getOrganismName().equalsIgnoreCase("human")) {
                    validation.setMessage("Organism name is invalid. Please provide us with the NCBI Homo Sapiens ontology annotation");
                    validation.setPassedRequirement(false);
                }
            }

        } else {
            validation.setMessage("Organism is empty");
            validation.setPassedRequirement(false);
        }
        return validation;
    }

    public static Validation getOrganismPartValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.ORGANISM_PART, Requirement.MANDATORY, Group.ORGANISM);
        validation.setId(ValidationIdentifier.ORGANISM_PART.getID());
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
        return validation;
    }

}
