/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2/19/13 12:16 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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
		try{
            Study study = studyService.getBiiStudy("XXXX",true); //TODO -> pre-defined test set
        } catch (Exception e){
            // Dont do anything
        }

		// System.out.println(study.getTitle());
		// do assertions here
		
	}
	
}
