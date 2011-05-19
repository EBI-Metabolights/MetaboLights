package uk.ac.ebi.metabolights.properties;

import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Enables you to find values from the properties file(s)
 * instead of using hard coded values throughout the Java classes.
 * @author markr
 */
public class PropertyLookup {
	//TODO determine somehow the current locale of the request and then call instead ResourceBundle.getBundle("messages", locale); 

	static ResourceBundle msgResources = ResourceBundle.getBundle("messages");

	public static String getMessage (String propertyName) {
		return msgResources.getString(propertyName);
	}

	public static String getMessage (String propertyName, String... substitutes	) {
		String prop= msgResources.getString(propertyName);
		String message = MessageFormat.format(prop, substitutes);
		return message;
	}

}
