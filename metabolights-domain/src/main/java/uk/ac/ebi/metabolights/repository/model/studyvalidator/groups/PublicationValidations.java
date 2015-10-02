package uk.ac.ebi.metabolights.repository.model.studyvalidator.groups;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import uk.ac.ebi.metabolights.repository.model.LiteStudy;
import uk.ac.ebi.metabolights.repository.model.Publication;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.DescriptionConstants;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Requirement;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Utilities;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 18/09/15.
 */

public class PublicationValidations {

    public static Collection<Validation> getValidations(Study study) {
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
                    validation.setMessage("Publication Title length is not sufficient");
                    validation.setPassedRequirement(false);
                }
            }
        } else {
            validation.setMessage("Publication is empty");
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
                    validation.setMessage("Pubmed ID is not provided");
                    validation.setPassedRequirement(false);
                }
            }
        } else {
            validation.setMessage("Publication is empty");
            validation.setPassedRequirement(false);
        }
        validation.setStatus();
        return validation;
    }

     /*
    TODO PublicationAuthorValidation
    */


}
