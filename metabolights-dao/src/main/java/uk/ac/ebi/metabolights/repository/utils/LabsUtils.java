package uk.ac.ebi.metabolights.repository.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by venkata on 30/01/2017.
 */
public class LabsUtils {

    private static Logger logger = LoggerFactory.getLogger (FileUtil.class);

    public static Timestamp getCurrentTimeStamp(){

        return new Timestamp((new Date()).getTime());

    }

}
