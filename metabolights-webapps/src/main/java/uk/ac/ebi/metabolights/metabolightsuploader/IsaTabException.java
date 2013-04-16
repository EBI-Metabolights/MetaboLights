package uk.ac.ebi.metabolights.metabolightsuploader;

import java.util.List;

import org.isatools.tablib.utils.logging.TabLoggingEventWrapper;

public class IsaTabException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TabLoggingEventWrapper> lastLog;
    private String msg;

	public IsaTabException (String message, List<TabLoggingEventWrapper> lastLog){
		super(message);
		this.lastLog = lastLog;
        this.msg = message;
		
	}
	public List<TabLoggingEventWrapper> getLastLog(){
		return lastLog;
	}
	@Override
    public String getMessage(){
        return this.msg;
    }

    public String geTechnicalInfo(){
        StringBuffer logsStrBuffer = new StringBuffer();

        for(TabLoggingEventWrapper log : lastLog){
            logsStrBuffer.append(log.getFormattedMessage().toString());
            logsStrBuffer.append("\n");
        }

        return logsStrBuffer.toString();
    }
}