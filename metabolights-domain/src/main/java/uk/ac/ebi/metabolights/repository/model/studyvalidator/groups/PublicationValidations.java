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

/**
 * Created by kalai on 18/09/15.
 */
@JsonTypeName("PublicationValidations")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicationValidations extends ValidationGroup {

    Publication publication;

    public PublicationValidations(Group group) {
        super(group);
        getValidations().add(new PublicationTitleValidation());
        //getValidations().add(new PublicationAuthorValidation());
        getValidations().add(new PublicationIDsValidation());
    }

    public PublicationValidations(){

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

    @JsonTypeName("PublicationTitleValidation")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PublicationTitleValidation extends Validation {
        @JsonCreator
        public PublicationTitleValidation() {
            super(DescriptionConstants.PUBLICATION_TITLE, Requirement.MANDATORY, Group.PUBLICATION);
        }

        @Override
        public boolean hasPassed() {
            if (!getStudy().getPublications().isEmpty()) {
                for (Publication publication : getStudy().getPublications()) {
                    if (!Utilities.minCharRequirementPassed(publication.getTitle(), 15)) {
                        setMessage("Publication Title length is not sufficient");
                        return false;
                    }
                }

            } else {
                setMessage("Publication is empty");
                return false;
            }
            return true;
        }
    }

    @JsonTypeName("PublicationAuthorValidation")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PublicationAuthorValidation extends Validation {
        @JsonCreator
        public PublicationAuthorValidation() {
            super(DescriptionConstants.PUBLICATION_AUTHORS, Requirement.MANDATORY, Group.PUBLICATION);
        }

        @Override
        public boolean hasPassed() {
            if (!getStudy().getPublications().isEmpty()) {
                //TODO add author info to Publication class
            } else {
                setMessage("Publication is empty");
                return false;
            }
            return true;
        }
    }

    @JsonTypeName("PublicationIDsValidation")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PublicationIDsValidation extends Validation {
        @JsonCreator
        public PublicationIDsValidation() {
            super(DescriptionConstants.PUBLICATION_IDS, Requirement.OPTIONAL, Group.PUBLICATION);
        }

        @Override
        public boolean hasPassed() {
            if (!getStudy().getPublications().isEmpty()) {
                for (Publication publication : getStudy().getPublications()) {
                    if (publication.getPubmedId().isEmpty()) {
                        setMessage("Pubmed ID is not provided");
                        return false;
                    }
                }
            } else {
                setMessage("Publication is empty");
                return false;
            }
            return true;
        }
    }

}
