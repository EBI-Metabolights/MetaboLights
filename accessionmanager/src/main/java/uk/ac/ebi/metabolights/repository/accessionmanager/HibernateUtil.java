package uk.ac.ebi.metabolights.repository.accessionmanager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Creates a SessionFactory, based on property files, xml, default...
 * see http://docs.jboss.org/hibernate/core/3.3/reference/en/html/session-configuration.html
 * @author conesa
 *
 */
public class HibernateUtil {
	private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
	private static final SessionFactory sessionFactory = buildSessionFactory();
	
	private static SessionFactory buildSessionFactory(){
		try{
			
			//Log
			logger.info("Building the session factory.");
			
			//Create a session factory from hibernate.cfg.xml
			//return new Configuration().configure().buildSessionFactory();
			
			
			//Create a session factory using java property files..
			Configuration cfg = new Configuration();

			//Add the main xml config file
			cfg.configure("am_hibernate.cfg.xml");
			
//			//Load connection properties from a jdbc file
//			Properties props = new Properties();
//			props.load(HibernateUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
//
//			//"translate" jdbc properties int hibernate properties.
//			cfg.setProperty("connection.driver_class", props.getProperty("jdbc.driverClassName"));
//			cfg.setProperty("connection.url", props.getProperty("jdbc.databaseurl"));
//			cfg.setProperty("connection.username", props.getProperty("jdbc.username"));
//			cfg.setProperty("connection.password", props.getProperty("jdbc.password"));
//			cfg.setProperty("dialect", props.getProperty("jdbc.dialect"));
//		
			
			return cfg.buildSessionFactory();
			
		}
		catch (Throwable ex){
			//Make sure you log the exception, as it might be swallowed
			System.err.println ("Initial SessionFactory creation failed.");
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
