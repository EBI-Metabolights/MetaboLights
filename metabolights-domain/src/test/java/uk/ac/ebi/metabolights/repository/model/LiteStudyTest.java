package uk.ac.ebi.metabolights.repository.model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class LiteStudyTest extends TestCase {


	@Test
	public void testReciprocityStudyUser(){

		LiteStudy study = new LiteStudy();

		User user = new User();

		study.getUsers().add(user);


		// User should have the study
		Assert.assertEquals("Study added to the user when user is added to a study.", 1, user.getStudies().size());

	}
}