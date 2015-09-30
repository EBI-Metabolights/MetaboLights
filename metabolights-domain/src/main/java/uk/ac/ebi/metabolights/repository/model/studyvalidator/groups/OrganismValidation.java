package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import uk.ac.ebi.metabolights.repository.model.Organism;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Utilities;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;

/**
 * Created by kalai on 30/09/15.
 */
@JsonTypeName("OrganismValidation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganismValidation extends ValidationGroup {

    public OrganismValidation(Group group) {
        super(group);
        getValidations().add(new OrganismNameValidation());
        getValidations().add(new OrganismPartValidation());
    }

    public OrganismValidation() {

    }

    @Override
    public Collection<Validation> isValid(Study study) {
        setStudy(study);
        for (Validation validation : getValidations()) {
            validation.setPassedRequirement(validation.hasPassed());
            validation.setStatus();
        }
        return getValidations();
    }


    @JsonTypeName("OrganismNameValidation")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrganismNameValidation extends Validation {

        @JsonCreator
        public OrganismNameValidation() {
            super(DescriptionConstants.ORGANISM_NAME, Requirement.MANDATORY, Group.ORGANISM);
        }

        @Override
        public boolean hasPassed() {
            if (!getStudy().getOrganism().isEmpty()) {
                for (Organism organism : getStudy().getOrganism()) {
                    if (!Utilities.minCharRequirementPassed(organism.getOrganismName(), 3)) {
                        setMessage("Organism name is not valid");
                        return false;
                    }
                }

            } else {
                return false;
            }
            return true;
        }
    }

    @JsonTypeName("OrganismPartValidation")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrganismPartValidation extends Validation {

        @JsonCreator
        public OrganismPartValidation() {
            super(DescriptionConstants.ORGANISM_PART, Requirement.MANDATORY, Group.ORGANISM);
        }

        @Override
        public boolean hasPassed() {
            if (!getStudy().getOrganism().isEmpty()) {
                for (Organism organism : getStudy().getOrganism()) {
                    if (!Utilities.minCharRequirementPassed(organism.getOrganismPart(), 3)) {
                        setMessage("Organism part is not valid");
                        return false;
                    }
                }

            } else {
                return false;
            }
            return true;
        }
    }
}
