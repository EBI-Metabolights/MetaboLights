package uk.ac.ebi.metabolights.utils.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by venkata on 09/02/2017.
 */
public class LabsUtils {

    private static Logger logger = LoggerFactory.getLogger (LabsUtils.class);

    public static Timestamp getCurrentTimeStamp(){

        return new Timestamp((new Date()).getTime());

    }

}
