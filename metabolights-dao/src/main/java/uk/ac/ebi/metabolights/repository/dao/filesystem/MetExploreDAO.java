package uk.ac.ebi.metabolights.repository.dao.filesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.utils.FileUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Created by venkata on 17/02/2016.
 */
public class MetExploreDAO {

    private final static Logger logger = LoggerFactory.getLogger(MzTabDAO.class.getName());

    public String getMetExploreJSONData(String MetExploreJSONFileName){

        String metExploreJSONData = "";

        if (checkFileExists(MetExploreJSONFileName)){

            try {

                metExploreJSONData = FileUtil.file2String(MetExploreJSONFileName);

            } catch (IOException e) {

                e.printStackTrace();

            }

            logger.info(" MetExploreJSON File Name - Found: " + MetExploreJSONFileName);
        }

        return metExploreJSONData;
    }


    private File[] findMetExploreJSON(String folderName){

        File dir = new File(folderName);      //Folder that holds the MAF files

        File[] matches = dir.listFiles(
                new FilenameFilter(){
                    public boolean accept(File dir, String name){
                        return name.startsWith("m_") && name.endsWith(".tsv");
                    }

                }

        );

        return matches;

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
