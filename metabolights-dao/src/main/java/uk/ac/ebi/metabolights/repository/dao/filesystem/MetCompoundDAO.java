package uk.ac.ebi.metabolights.repository.dao.filesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.utils.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by venkata on 05/04/2016.
 */
public class MetCompoundDAO {

    private final static Logger logger = LoggerFactory.getLogger(MzTabDAO.class.getName());

    private final static String referenceFolderLocation = "/net/isilonP/public/rw/homes/tc_cm01/metabolights/reference";

    public String getCompoundData(String compoundid){

        String metCompoundData = "";

        String JSONFilePath = getFilePath(compoundid);

        if (checkFileExists(JSONFilePath)){

            try {

                metCompoundData = FileUtil.file2String(JSONFilePath);

            } catch (IOException e) {

                e.printStackTrace();

            }

            logger.info(" MetCompound JSON File Name - Found: " + JSONFilePath);
        }

        return metCompoundData;

    }

    public String getSpectra(String compoundId, String spectraId){

        String spectraJSON = "";

        String spectraPath = getSpectraFilePath(compoundId, spectraId);

        if (checkFileExists(spectraPath)){

            try {

                spectraJSON = FileUtil.file2String(spectraPath);

            } catch (IOException e) {

                e.printStackTrace();

            }

            logger.info(" MetCompound Spectra JSON File Name - Found: " + spectraPath);
        }

        return spectraJSON;

    }

    private String getSpectraFilePath(String compoundId, String spectraId){

         return referenceFolderLocation + File.separator + compoundId.toUpperCase() + File.separator + compoundId + "_spectra" + File.separator + spectraId + File.separator + spectraId + ".json";

    }


    private String getFilePath(String compoundid){

        return referenceFolderLocation + File.separator + compoundid.toUpperCase() + File.separator + compoundid + "_data.json";

    }

    private Boolean checkFileExists(String assignmentFileName){

        if (assignmentFileName == null || assignmentFileName.isEmpty())
            return false;        // No filename given

        File f = new File(assignmentFileName);

        if(f.exists())
            return true;        // Found the file

        return false;           // Did not find the file

    }
}
