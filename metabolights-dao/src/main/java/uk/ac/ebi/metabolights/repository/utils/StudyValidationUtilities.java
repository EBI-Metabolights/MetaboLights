package uk.ac.ebi.metabolights.repository.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.model.Publication;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.Table;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.OverallValidation;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Status;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Utilities;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.groups.Group;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.groups.PublicationValidation;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.groups.StudyValidation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 18/09/15.
 */
public class StudyValidationUtilities {
    private static Logger logger = LoggerFactory.getLogger(StudyValidationUtilities.class);

    public static OverallValidation validate(Study study) {
        Collection<Validation> validations = generateValidations(study);
        Table validationTable = Utilities.convertValidationsListToTable(validations);
        Status status = Utilities.checkOverallStatus(validations);
        return new OverallValidation(validationTable, status);
    }


    private static Collection<Validation> generateValidations(Study study) {
        Collection<Validation> validations = new LinkedList<>();

        for (Group group : Group.values()) {
            switch (group) {
                case STUDY:
                    validations.addAll(new StudyValidation(group).isValid(study));
                case PUBLICATION:
                    validations.addAll(new PublicationValidation(group).isValid(study));
//                case FACTORS:
//                    validations.addAll(new FactorValidation(group).isValid(study));
                    break;

            }
        }
        return validations;
    }

    public static void main(String[] args) {
        Study study = new Study();

        study.setTitle("I dont know what I am doing with my life!");

        Collection<Publication> publicationCollection = new LinkedList<>();
        Publication publication = new Publication();
        publication.setTitle("I dont want this to be published");
        publicationCollection.add(publication);
        study.setPublications(publicationCollection);

        OverallValidation overallValidation = StudyValidationUtilities.validate(study);
        System.out.println("Data: " + overallValidation.getValidationEntries().getData().toString());
    }


}
