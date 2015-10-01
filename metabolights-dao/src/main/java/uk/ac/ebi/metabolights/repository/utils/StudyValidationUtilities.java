package uk.ac.ebi.metabolights.repository.utils;

import uk.ac.ebi.metabolights.repository.model.Publication;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.*;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.groups.*;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 18/09/15.
 */
public class StudyValidationUtilities {

//    public static OverallValidation validate(Study study) {
//        Collection<Validation> validations = generateValidations(study);
//        //Table validationTable = Utilities.convertValidationsListToTable(validations);
//        Status status = Utilities.checkOverallStatus(validations);
//        return new OverallValidation(validations, status);
//    }

    public static Validations validate(Study study) {
        Collection<Validation> validationList = generateValidations(study);
        Validations validations = new Validations();
        validations.getEntries().addAll(validationList);
        Status status = Utilities.checkOverallStatus(validationList);
        validations.setStatus(status);
        validations.setPassedMinimumRequirement(Utilities.checkPassedMinimumRequirement(validationList));
        return validations;
    }


    private static Collection<Validation> generateValidations(Study study) {
        Collection<Validation> validations = new LinkedList<>();
        try {
            for (Group group : Group.values()) {
                if (!group.equals(Group.EXCEPTION)) {
                    if (group.equals(Group.STUDY)) {
                        validations.addAll(new StudyValidations(group).isValid(study));
                    }
                    if (group.equals(Group.PUBLICATION)) {
                        validations.addAll(new PublicationValidations(group).isValid(study));
                    }
                    if (group.equals(Group.FACTORS)) {
                        validations.addAll(new FactorValidations(group).isValid(study));
                    }
                    if (group.equals(Group.ORGANISM)) {
                        validations.addAll(new OrganismValidations(group).isValid(study));
                    }
                    if (group.equals(Group.ISATAB)) {
                        validations.addAll(new IsatabValidations(group).isValid(study));
                    }
                    if (group.equals(Group.ASSAYS)) {
                        validations.addAll(new AssayValidations(group).isValid(study));
                    }
                    if (group.equals(Group.PROTOCOLS)) {
                        validations.addAll(new ProtocolValidations(group).isValid(study));
                    }
                    if (group.equals(Group.SAMPLES)) {
                        validations.addAll(new SampleValidations(group).isValid(study));
                    }
                }
            }
        } catch (Exception e) {
            validations.addAll(new ExceptionValidations(Group.EXCEPTION, e).isValid(study));
        }
        return validations;
    }

    public static void main(String[] args) {
        Study study = new Study();

        study.setTitle("Life is cool!");

        Collection<Publication> publicationCollection = new LinkedList<>();
        Publication publication = new Publication();
        publication.setTitle("To be published");
        publicationCollection.add(publication);
        study.setPublications(publicationCollection);

        Validations validations = StudyValidationUtilities.validate(study);
        System.out.println("Status: " + validations.getStatus());
    }


}
