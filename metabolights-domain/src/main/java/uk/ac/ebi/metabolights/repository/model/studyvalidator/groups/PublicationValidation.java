package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.Publication;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Utilities;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;

/**
 * Created by kalai on 18/09/15.
 */
public class PublicationValidation extends ValidationGroup {

    Publication publication;

    public PublicationValidation(Group group) {
        super(group);
        getValidations().add(new PublicationTitleValidation());
        getValidations().add(new PublicationAuthorValidation());
        getValidations().add(new PublicationIDsValidation());
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

    public class PublicationTitleValidation extends Validation {
        public PublicationTitleValidation() {
            super(DescriptionConstants.PUBLICATION_TITLE, Requirement.MANDATORY, getGroupName());
        }

        @Override
        public boolean hasPassed() {
            if (!getStudy().getPublications().isEmpty()) {
                while (getStudy().getPublications().iterator().hasNext()) {
                    Publication publication = getStudy().getPublications().iterator().next();
                    if (!Utilities.minCharRequirementPassed(publication.getTitle(), 15)) {
                        return false;
                    }
                }
            } else {
                return false;
            }
            return true;
        }
    }

    public class PublicationAuthorValidation extends Validation {
        public PublicationAuthorValidation() {
            super(DescriptionConstants.PUBLICATION_AUTHORS, Requirement.MANDATORY, getGroupName());
        }

        @Override
        public boolean hasPassed() {
            return false;
        }
    }

    public class PublicationIDsValidation extends Validation {
        public PublicationIDsValidation() {
            super(DescriptionConstants.PUBLICATION_TITLE, Requirement.OPTIONAL, getGroupName());
        }

        @Override
        public boolean hasPassed() {
            if (!getStudy().getPublications().isEmpty()) {
                while (getStudy().getPublications().iterator().hasNext()) {
                    Publication publication = getStudy().getPublications().iterator().next();
                    if (publication.getPubmedId().isEmpty()) {
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
