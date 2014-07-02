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
}
