package uk.ac.ebi.metabolights.service;

import org.springframework.context.ApplicationContext;

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
     * @return
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
    		userService = (UserService) ctx.getBean("myService");
    	}
    	return userService;
    }
    
} 
