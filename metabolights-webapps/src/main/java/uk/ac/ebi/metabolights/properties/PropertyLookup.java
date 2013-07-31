package uk.ac.ebi.metabolights.properties;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import static org.apache.commons.io.FileUtils.readFileToString;

/**
 * Enables you to find values from the properties file(s)
 * instead of using hard coded values throughout the Java classes.
 * @author markr
 */
public class PropertyLookup {
	//TODO determine somehow the current locale of the request and then call instead ResourceBundle.getBundle("messages", locale); 

	static ResourceBundle msgResources = ResourceBundle.getBundle("messages");

    /**
     * Reads a text file as an email template
     * @param fileName
     * @return file context
     * @throws IOException
     */
    private static String getEmailTemplate(String fileName) throws IOException {
        File emailTemplate = new File(fileName);
        return readFileToString(emailTemplate);
    }

    /**
     * Gets the context of the email template and substitutes any other parameters in the template
     * @param FileName
     * @param substitutes
     * @return "personalised" email message, with help etc
     * @throws IOException
     */
    public static String getEmailMessage(String FileName, String... substitutes) throws IOException {
        String prop = getEmailTemplate(FileName);
        String message = MessageFormat.format(prop, substitutes);
        return message;
    }

	public static String getMessage (String propertyName) {
		return msgResources.getString(propertyName);
	}

	public static String getMessage (String propertyName, String... substitutes	) {
		String prop= msgResources.getString(propertyName);
		String message = MessageFormat.format(prop, substitutes);
		return message;
	}

}
