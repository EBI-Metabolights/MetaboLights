package uk.ac.ebi.metabolights.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil extends PropertyPlaceholderConfigurer {
	private static Map<String,String> propertiesMap;

	public static Context envCtx;

	private static Logger logger = Logger.getLogger(PropertiesUtil.class);

   @Override
   protected void processProperties(ConfigurableListableBeanFactory beanFactory,
             Properties props) throws BeansException {
        super.processProperties(beanFactory, props);

        propertiesMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            propertiesMap.put(keyStr, parseStringValue(props.getProperty(keyStr), props, new HashSet()));
        }
        
        // Init context (JNDI) properties
        initContext();

    }

   	public static Map<String,String> getProperties(){
   		return propertiesMap;
   	}
    public static String getProperty(String name) {
        
    	String property;
    	
    	// First lookup in the context (priority)
    	property = getPropertyFromContext(name);
    	
    	if (property != null) {
    		
    		// If not yet in property map.
//    		if (propertiesMap.get(name) == null){
//    			propertiesMap.put(name, property);
//    		}
    		return property;
    	}

		String value = propertiesMap.get(name);

		if (value == null) logger.warn("Property \"" + name + "\" requested not found or is null");
		
    	// If not in context, return property from property map.    	
    	return propertiesMap.get(name);
    }
    
    /*
     * Gets the property from JNDI context file.
     * http://tomcat.apache.org/tomcat-6.0-doc/jndi-resources-howto.html
     */
    private static String getPropertyFromContext(String name){
    	
    	try {
			return (String)envCtx.lookup(name);
		} catch (NamingException e) {
			// TODO Auto-generated catch block

			return null;
		}

    }

	public static Context getEnvCtx() {
		return envCtx;
	}

	private static void initContext(){

    	Context initCtx;
		try {
			initCtx = new InitialContext();
			envCtx = (Context) initCtx.lookup("java:comp/env");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }
 
}
