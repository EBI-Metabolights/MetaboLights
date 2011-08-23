package uk.ac.ebi.metabolights.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.metabolights.service.StudyService;

/**
 * Test class for testing through Spring services. Spring is configurated through
 * local test xml file 'test.spring.xml' which refers to test.db.properties but 
 * regular appication.properties.
 *  
 * @author markr
 */
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
		Study study = studyService.getBiiStudy("XXXX",true); //TODO -> pre-defined test set
		// System.out.println(study.getTitle());
		// do assertions here
		
	}
	
}
