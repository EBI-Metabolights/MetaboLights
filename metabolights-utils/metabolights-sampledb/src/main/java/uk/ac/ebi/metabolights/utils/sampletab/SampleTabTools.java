package uk.ac.ebi.metabolights.utils.sampletab;


import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 21/05/2013
 * Time: 11:20
 */
public class SampleTabTools {

    private static Logger logger = Logger.getLogger(SampleTabTools.class);
    /**
     * Converts ISA-tab date format (string) to a SampleTab date (Date)
     * @param isaTabDate string from ISA-tab files
     * @return Date in SampleTab format
     */
    public Date parseDate(String isaTabDate){

        String isaDateFormat = "yyyy-MM-dd"; //ISA format for the date
        String sampleDateFormat = "yyyy-MM-dd HH:mm:ss.ss"; //SampleTab date format
        SimpleDateFormat isaFormatter, sampleFormatter;

        try {

            isaFormatter = new SimpleDateFormat(isaDateFormat);
            sampleFormatter = new SimpleDateFormat(sampleDateFormat);

            Date isaDate = isaFormatter.parse(isaTabDate);
            String sampleDateStr = sampleFormatter.format(isaDate);

            Date sampleDate = new SimpleDateFormat(sampleDateFormat).parse(sampleDateStr);

            return sampleDate;
        } catch (ParseException e) {
            logger.error("Cannot parse date for " + isaTabDate + " : " + e.getMessage());
            return null;
        }

    }



    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
