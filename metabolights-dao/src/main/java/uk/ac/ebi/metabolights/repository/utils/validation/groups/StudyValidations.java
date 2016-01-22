package uk.ac.ebi.metabolights.repository.utils.validation.groups;

import uk.ac.ebi.metabolights.repository.model.Contact;
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
 * Created by kalai on 18/09/15.
 */
public class StudyValidations implements IValidationProcess {


    @Override
    public String getAbout() {
        return Group.STUDY.toString();
    }

    public Collection<Validation> getValidations(Study study) {
        Collection<Validation> studyValidations = new LinkedList<>();
        studyValidations.add(getStudyTitleValidation(study));
        studyValidations.add(getStudyDescriptionValidation(study));
        studyValidations.add(studyDecodeIsSuccessfulValidation(study));
        studyValidations.add(allContactsHaveEmailValidation(study));

        return studyValidations;

    }

    public static Validation getStudyTitleValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.STUDY_TITLE, Requirement.MANDATORY, Group.STUDY);
        validation.setId(ValidationIdentifier.STUDY_TITLE.getID());
        if (!Utilities.minCharRequirementPassed(
                study.getTitle(), 60)) {
            validation.setMessage("Study Title lacks detail");
            validation.setPassedRequirement(false);
        }
        return validation;
    }

    public static Validation getStudyDescriptionValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.STUDY_DESCRIPTION, Requirement.MANDATORY, Group.STUDY);
        validation.setId(ValidationIdentifier.STUDY_DESCRIPTION.getID());
        if (!Utilities.minCharRequirementPassed(
                study.getDescription(), 200)) {
            validation.setMessage("Study Description lacks detail");
            validation.setPassedRequirement(false);
        }
        return validation;
    }

   /*
   TODO: StudyDesignDescriptorsValidation, MinimumStudyValidation
    */

    public static Validation studyDecodeIsSuccessfulValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.STUDY_TEXT, Requirement.OPTIONAL, Group.STUDY);
        validation.setId(ValidationIdentifier.STUDY_TEXT.getID());
        String message = "";
        if (Utilities.getNonUnicodeCharacters(study.getTitle()).size() > 0) {
            message += "Study title, ";
        }
        if (Utilities.getNonUnicodeCharacters(study.getDescription()).size() > 0) {
            message += "Study description, ";
        }
        if (Utilities.getNonUnicodeCharacters(getContactAsString(study.getContacts())).size() > 0) {
            message += "Study contact, ";
        }
        if (!message.isEmpty()) {
            validation.setPassedRequirement(false);
            message += "contains characters that cannot be successfully parsed";
            validation.setMessage(message);
        }
        return validation;

    }

    private static String getContactAsString(Collection<Contact> contacts) {
        String c = "";
        for (Contact contact : contacts) {
            c += contact.getFirstName() +
                    contact.getLastName() +
                    contact.getMidInitial() +
                    contact.getAffiliation() +
                    contact.getAddress() +
                    contact.getRole();
        }
        return c;
    }

    public static Validation allContactsHaveEmailValidation(Study study) {
        Validation validation = new Validation(DescriptionConstants.STUDY_CONTACT_EMAIL, Requirement.MANDATORY, Group.CONTACT);
        validation.setId(ValidationIdentifier.STUDY_CONTACT_EMAIL.getID());
        Collection<Contact> contacts = study.getContacts();
        if (contacts.size() == 0) {
            validation.setPassedRequirement(false);
            validation.setMessage("No Study Contact information is provided");
            return validation;
        }
        int noEmail = 0;
        for (Contact contact : contacts) {
            if (contact.getEmail().isEmpty()) {
                noEmail++;
            }
        }
        if (noEmail > 0) {
            validation.setPassedRequirement(false);
            validation.setMessage("Not all Study Contacts have their emails listed");
        }
        return validation;
    }


}
