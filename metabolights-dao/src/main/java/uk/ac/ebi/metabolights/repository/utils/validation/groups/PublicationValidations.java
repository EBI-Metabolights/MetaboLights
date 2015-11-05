package uk.ac.ebi.metabolights.repository.utils.validation.groups;

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

public class PublicationValidations implements IValidationProcess{

    @Override
    public String getAbout() {
        return Group.PUBLICATION.toString();
    }

    public Collection<Validation> getValidations(Study study) {
        Collection<Validation> publicationValidations = new LinkedList<>();
        publicationValidations.add(getPublicationTitleValidation(study));
        publicationValidations.add(getPublicationPubmedIDValidation(study));
        return publicationValidations;
    }


    public static Validation getPublicationTitleValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.PUBLICATION_TITLE, Requirement.MANDATORY, Group.PUBLICATION);
        if (!study.getPublications().isEmpty()) {
            for (Publication publication : study.getPublications()) {
                if (!Utilities.minCharRequirementPassed(publication.getTitle(), 15)) {
                    validation.setMessage("Study Publication Title length is too short");
                    validation.setPassedRequirement(false);
                }
            }
        } else {
            validation.setMessage("Study Publications is empty");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }


    public static Validation getPublicationPubmedIDValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.PUBLICATION_IDS, Requirement.OPTIONAL, Group.PUBLICATION);
        if (!study.getPublications().isEmpty()) {
            for (Publication publication : study.getPublications()) {
                if (publication.getPubmedId().isEmpty()) {
                    validation.setMessage("Study Pubmed ID is not provided");
                    validation.setPassedRequirement(false);
                }
            }
        } else {
            validation.setMessage("Study Publications is empty");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }

     /*
    TODO PublicationAuthorValidation
    */


}
