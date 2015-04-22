package uk.ac.ebi.metabolights.repository.model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LiteStudyTest extends TestCase {


	@Test
	public void testReciprocityStudyUser(){

		LiteStudy study = new LiteStudy();

		User user = new User();

		study.getUsers().add(user);


		// User should have the study
		Assert.assertEquals("Study added to the user when user is added to a study.", 1, user.getStudies().size());


		// Test setting the whole list
		user = new User();

		List<User> users = new ArrayList<>();
		users.add(user);

		study.setUsers(users);

		// New user should have the study
		Assert.assertEquals("Study added to the new user when List<user> is added to a study.", 1, user.getStudies().size());
		Assert.assertEquals("Study.users list must have 1 user.", 1, study.getUsers().size());



	}
}