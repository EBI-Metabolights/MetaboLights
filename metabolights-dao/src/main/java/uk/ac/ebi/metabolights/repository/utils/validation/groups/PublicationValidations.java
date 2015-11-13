package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import org.apache.commons.lang.math.NumberUtils;
import uk.ac.ebi.metabolights.repository.model.Publication;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Group;
import uk.ac.ebi.metabolights.repository.utils.validation.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.utils.validation.Utilities;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 18/09/15.
 */

public class PublicationValidations implements IValidationProcess {

    @Override
    public String getAbout() {
        return Group.PUBLICATION.toString();
    }

    public Collection<Validation> getValidations(Study study) {
        Collection<Validation> publicationValidations = new LinkedList<>();

        Validation validation = getPublicationValidation(study);

        if (validation.getPassedRequirement()) {
            Collection<Validation> validations = new LinkedList<>();
            validations.add(getPublicationDOIValidation(study));
            validations.add(getPublicationPubmedIDValidation(study));
            Validation titlevalidation = getPublicationTitleValidation(study);
            if (titlevalidation.getPassedRequirement()) {
                int passedCount = Utilities.getPassedCount(validations);
                if (passedCount == 0) {
                    publicationValidations.addAll(validations);
                    publicationValidations.add(titlevalidation);
                    validation.setPassedRequirement(false);
                    validation.setMessage("Study Publication(s) is missing information");
                }
            } else {
                publicationValidations.addAll(validations);
                publicationValidations.add(titlevalidation);
                validation.setPassedRequirement(false);
                validation.setMessage("Study Publication(s) is missing information");
            }

        }
        validation.setStatus();
        publicationValidations.add(validation);
        return publicationValidations;
    }

    public static Validation getPublicationValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.PUBLICATIONS, Requirement.MANDATORY, Group.PUBLICATION);
        if (study.getPublications().isEmpty()) {
            validation.setMessage("Study Publication is missing");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }


    public static Validation getPublicationTitleValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.PUBLICATION_TITLE, Requirement.MANDATORY, Group.PUBLICATION);
        for (Publication publication : study.getPublications()) {
            if (publication.getTitle().isEmpty()) {
                validation.setMessage("Study Publication Title is not provided");
                validation.setPassedRequirement(false);
            } else if (!Utilities.minCharRequirementPassed(publication.getTitle(), 15)) {
                validation.setMessage("Study Publication Title length is too short");
                validation.setPassedRequirement(false);
            }
        }
        validation.setStatus();
        return validation;
    }


    public static Validation getPublicationPubmedIDValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.PUBLICATION_IDS, Requirement.OPTIONAL, Group.PUBLICATION);
        for (Publication publication : study.getPublications()) {
            if (publication.getPubmedId().isEmpty()) {
                validation.setMessage("Study Pubmed ID is not provided");
                validation.setPassedRequirement(false);
            } else {
                if (!NumberUtils.isNumber(publication.getPubmedId())) {
                    validation.setMessage("Study Pubmed ID is not valid");
                    validation.setPassedRequirement(false);
                }
            }
        }
        validation.setStatus();
        return validation;
    }

    public static Validation getPublicationDOIValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.PUBLICATION_DOI, Requirement.OPTIONAL, Group.PUBLICATION);
        for (Publication publication : study.getPublications()) {
            if (publication.getDoi().isEmpty()) {
                validation.setMessage("Study DOI is not provided");
                validation.setPassedRequirement(false);
            }
        }
        validation.setStatus();
        return validation;
    }


}
