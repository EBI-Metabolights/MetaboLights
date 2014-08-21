/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/5/12 10:35 AM
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
