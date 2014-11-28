/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 7/1/14 5:12 PM
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

package uk.ac.ebi.metabolights.service;

import org.springframework.aop.framework.Advised;
import org.springframework.context.ApplicationContext;
import uk.ac.ebi.metabolights.webapp.StyleMAVFactory;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class provides application-wide access to the Spring ApplicationContext.
 * The ApplicationContext is injected by the class "ApplicationContextProvider".
 *
 * @author Siegfried Bolz
 */
public class AppContext {

    private static ApplicationContext ctx;
	private static EmailService emailService;
	private static UserService userService;
	private static StudyService studyService;
	private static String jndiName; 
	private static DataSource ds;
	private static StyleMAVFactory mavFactory;
	private static AccessionService accessionService;
	private static SearchService searchService;

	/**
     * Injected from the class "ApplicationContextProvider" which is automatically
     * loaded during Spring-Initialisation.
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    /**
     * Get access to the Spring ApplicationContext from everywhere in your Application.
     *
     * @return ctx
     */
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
    
    public static EmailService getEmailService(){
    	if (emailService == null){
    		emailService =ctx.getBean(EmailService.class);
    	}
    	
    	return emailService;
    }
    public static UserService getUserService(){
    	if (userService == null){
    		userService = (UserService) ctx.getBean(UserService.class);
    	}
    	return userService;
    }
    
    public static StyleMAVFactory getMAVFactory(){
    	if (mavFactory == null){
    		mavFactory = (StyleMAVFactory) ctx.getBean(StyleMAVFactory.class);
    	}
    	return mavFactory;
    }
    
    public static StudyService getStudyService(){
    	if (studyService == null)
    	{
    		//studyService = (StudyService) ctx.getBean(StudyService.class);
    		
    		Advised advised = (Advised)ctx.getBean(StudyService.class);
    		try {
				studyService = (StudyService) advised.getTargetSource().getTarget();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return studyService;
    }
    
    public static DataSource getDataSource () throws NamingException
    {
    	
     
    	if (ds == null){

    		ds = (DataSource)getApplicationContext().getBean("dataSource");
    	}
      
      return ds;
    }
    

    public static Connection getConnection () throws SQLException, NamingException
    {
      Connection conn = null;
      // Get a connection object
      conn = getDataSource().getConnection();

	  // Based on http://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html#Getting_the_actual_JDBC_connection
	  conn = ((javax.sql.PooledConnection)conn).getConnection();
     
      return conn;
    }

	public static AccessionService getAccessionService() {
		if (accessionService == null){
			accessionService = (AccessionService) ctx.getBean(AccessionService.class);
		}
		return accessionService;
	}

	public static SearchService getSearchService() {

		if (searchService == null){
			searchService = (SearchService) ctx.getBean(SearchService.class);
		}
		return searchService;

	}
}
