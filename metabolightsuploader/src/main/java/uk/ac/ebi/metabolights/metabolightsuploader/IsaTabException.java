package uk.ac.ebi.metabolights.metabolightsuploader;

import java.util.List;

import org.isatools.tablib.utils.logging.TabLoggingEventWrapper;

public class IsaTabException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TabLoggingEventWrapper> lastLog;
	public IsaTabException (String message, List<TabLoggingEventWrapper> lastLog){
		super(message);
		this.lastLog = lastLog;
		
	}
	public List<TabLoggingEventWrapper> getLastLog(){
		return lastLog;
	}
	

	
}
