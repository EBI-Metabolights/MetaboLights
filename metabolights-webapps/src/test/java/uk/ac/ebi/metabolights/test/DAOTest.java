package uk.ac.ebi.metabolights.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.metabolights.service.StudyService;

public class DAOTest {

	private ApplicationContext applicationContext;
	private SessionFactory sessionFactory;
    private Session session;
    private StudyService studyService;

	@Before
	public void letsGo() {
		if (applicationContext==null){
			applicationContext =new ClassPathXmlApplicationContext("test.spring.xml");
			studyService =  applicationContext.getBean(StudyService.class);
		}
	}

	@Test
	public void getStudy() {
		Study study = studyService.getBiiStudy("XXXX"); //TODO -> pre-defined test set
		System.out.println(study.getTitle());
		// do assertions here
		
	}
	
}
