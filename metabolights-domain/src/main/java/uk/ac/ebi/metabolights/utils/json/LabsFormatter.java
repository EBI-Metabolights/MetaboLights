package uk.ac.ebi.metabolights.utils.json;

import java.security.Timestamp;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * Created by venkata on 20/02/2017.
 */
public class LabsFormatter extends SimpleFormatter{
    /* (non-Javadoc)
    * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
    */
    @Override
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        sb.append(record.getLevel()).append(" " + LabsUtils.getCurrentTimeStamp() + " ").append(':');
        sb.append(record.getMessage()).append("<br>");
        return sb.toString();
    }
}
