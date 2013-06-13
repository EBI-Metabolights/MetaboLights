package uk.ac.ebi.metabolights.utils.sampletab;


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
            e.printStackTrace();
        }

        return null;
    }
}
