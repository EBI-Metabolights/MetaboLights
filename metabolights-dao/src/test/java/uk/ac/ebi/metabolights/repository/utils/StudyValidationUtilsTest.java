package uk.ac.ebi.metabolights.repository.utils;

import junit.framework.Assert;
import junit.framework.TestCase;
import uk.ac.ebi.metabolights.repository.model.Organism;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.Validation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by kalai on 07/07/15.
 */
public class StudyValidationUtilsTest extends TestCase {

    public void tearDown() throws Exception {

    }

    public void testValidate() throws Exception {
        Study study = new Study();

        Collection<Organism> organisms = new LinkedList<>();
        Organism organism = new Organism();
        organism.setOrganismName("HomoSapiens");
        organisms.add(organism);
        study.setOrganism(organisms);

        StudyValidationUtils.validate(study);
        assertEquals(true, study.getValidations().getEntries().size() > 0);

        Validation aValidation = (Validation) study.getValidations().getEntries().iterator().next();

        Assert.assertEquals("validation hasPassed", true, aValidation.getPassedRequirement());

    }


}