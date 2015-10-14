package uk.ac.ebi.metabolights.repository.utils;

import junit.framework.TestCase;
import org.junit.Test;
import uk.ac.ebi.metabolights.repository.model.Publication;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Status;
import uk.ac.ebi.metabolights.repository.utils.validation.StudyValidationUtilities;

import java.util.Collection;
import java.util.LinkedList;


/**
 * Created by kalai on 18/09/15.
 */
public class StudyValidationUtilitiesTest extends TestCase {
    public void tearDown() throws Exception {

    }


    @Test
    public void testValidate() throws Exception {
        Study study = new Study();

        study.setTitle("Life is so cool, all is well!");

        Collection<Publication> publicationCollection = new LinkedList<>();
        Publication publication = new Publication();
        publication.setTitle("scooby dooby doo scooby dooby doo scooby dooby doo scooby dooby doo scooby dooby doo");
        publication.setPubmedId("123456");
        publicationCollection.add(publication);
        study.setPublications(publicationCollection);

        StudyValidationUtilities.validate(study);
        assertEquals(true, study.getValidations().getEntries().size() > 0);
        assertEquals(true, study.getValidations().getStatus().equals(Status.RED));

//        Validation aValidation = (Validation) study.getValidations().getEntries().iterator().next();
//
//        Assert.assertEquals("validation hasPassed", true, aValidation.getPassedRequirement());

    }
}
