package uk.ac.ebi.metabolights.repository.utils;

import junit.framework.Assert;
import junit.framework.TestCase;
import uk.ac.ebi.metabolights.repository.model.Organism;
import uk.ac.ebi.metabolights.repository.model.Publication;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.OverallValidation;

import java.util.Collection;
import java.util.LinkedList;


/**
 * Created by kalai on 18/09/15.
 */
public class StudyValidationUtilitiesTest extends TestCase {
    public void tearDown() throws Exception {

    }

    public void testValidate() throws Exception {
        Study study = new Study();

        study.setTitle("I dont know what I am doing with my life!");

        Collection<Publication> publicationCollection = new LinkedList<>();
        Publication publication = new Publication();
        publication.setTitle("I dont want this to be published");
        publicationCollection.add(publication);
        study.setPublications(publicationCollection);

        OverallValidation overallValidation = StudyValidationUtilities.validate(study);
        assertEquals(true, overallValidation.getValidationEntries().getData().size() > 0);

//        Validation aValidation = (Validation) study.getValidations().getEntries().iterator().next();
//
//        Assert.assertEquals("validation hasPassed", true, aValidation.getPassedRequirement());

    }
}
