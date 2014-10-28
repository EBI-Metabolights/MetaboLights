/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 5/19/14 11:47 AM
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

package uk.ac.ebi.metabolights.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

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
