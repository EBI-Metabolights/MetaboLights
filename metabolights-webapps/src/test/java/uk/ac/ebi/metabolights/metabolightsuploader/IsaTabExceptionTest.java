/**
 * 
 */
package uk.ac.ebi.metabolights.metabolightsuploader;

import org.isatools.isatab.commandline.AbstractImportLayerShellCommand;
import org.isatools.tablib.utils.logging.TabLoggingEventWrapper;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.util.List;
import org.isatools.tablib.utils.logging.ListTabAppender;
import org.apache.log4j.Category;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author conesa
 *
 */
public class IsaTabExceptionTest {

	@Test
	public void testIsaTabException(){
		
	    final String LOG_APP_LAYOUT = "%d [%x]: %m";
	    final String APPENDER_NAME = "testIsaTabException";
	    ListTabAppender appender;
	    Logger vlog = null;
    
        // Need to initialize this here, config problems with log4j
        if (vlog == null) {
            vlog = Logger.getRootLogger();
        }
        vlog.setLevel(Level.INFO);

        appender = (ListTabAppender) vlog.getAppender(APPENDER_NAME);
        if (appender == null) {
            appender = new ListTabAppender();
            appender.setName(APPENDER_NAME);
            appender.setLayout(new PatternLayout(LOG_APP_LAYOUT));
            appender.setThreshold(Level.INFO);
            vlog.addAppender(appender);
        } else {
            appender.clear();
        }
		
        // Do some log activity
        vlog.info("info message");
        vlog.warn("warning message");
        vlog.error("error message");
                
		// Instantiate a new exception...
		IsaTabException ite = new IsaTabException("Hello", appender.getOutput());
		
		List<TabLoggingEventWrapper> list = ite.getLastLog();
		
		
		assertTrue("Is there the info message?",list.get(0).getFormattedMessage().indexOf("info message") != -1); 
		assertTrue("Is there the warning message?",list.get(1).getFormattedMessage().indexOf("warning message") != -1); 
		assertTrue("Is there the error message?",list.get(2).getFormattedMessage().indexOf("error message") != -1); 
		
		
		
		
		
		
		
	}
}
