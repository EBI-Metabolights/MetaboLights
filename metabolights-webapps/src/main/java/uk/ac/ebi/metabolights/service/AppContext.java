package uk.ac.ebi.metabolights.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.aop.framework.Advised;
import org.springframework.context.ApplicationContext;
import org.springframework.jndi.JndiObjectFactoryBean;

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
    public static StudyService getStudyService(){
    	if (studyService == null)
    	{
    		//studyService = (StudyService) ctx.getBean(StudyService.class);
    		
    		Advised advised = (Advised)ctx.getBean(StudyService.class);;
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
			//	      // Get a context for the JNDI look up
			//	      Context ctx = new InitialContext();
			//	      Context envContext = (Context) ctx.lookup("java:/comp/env");
			//	        
			//	      // Look up a data source
			//	      ds = (javax.sql.DataSource) envContext.lookup (getJndiName()); 

    		ds = (DataSource)getApplicationContext().getBean("dataSource");
    	}
      
      return ds;
    }
    
//    private static String getJndiName(){
//    	if (jndiName == null){
//    		
//    		JndiObjectFactoryBean ofb = (DataSource)getApplicationContext().getBean("dataSource");
//    		jndiName = ofb.getJndiName();
//    	}
//    	
//    	return jndiName;
//    }
    
    public static Connection getConnection () throws SQLException, NamingException
    {
      Connection conn = null;
      // Get a connection object
      conn = getDataSource().getConnection();
     
      return conn;
    }
    
} 
