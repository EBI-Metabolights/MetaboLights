package uk.ac.ebi.metabolights.repository.dao.filesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.utils.FileUtil;
import uk.ac.ebi.metabolights.webservice.client.MetExploreWsClient;


import java.io.File;
import java.io.IOException;

/**
 * Created by venkata on 17/02/2016.
 */
public class MetExploreDAO {

    private final static Logger logger = LoggerFactory.getLogger(MzTabDAO.class.getName());

    public String getMetExploreJSONData(String MetExploreJSONFileName, String studyid){

        String metExploreJSONData = "";

        if (checkFileExists(MetExploreJSONFileName)){

            try {

                metExploreJSONData = FileUtil.file2String(MetExploreJSONFileName);

            } catch (IOException e) {

                e.printStackTrace();

            }

            logger.info(" MetExploreJSON File Name - Found: " + MetExploreJSONFileName);

        }else{

            return generateMetExploreJSONFile(studyid, MetExploreJSONFileName);


        }

        return metExploreJSONData;
    }

    private String generateMetExploreJSONFile(String studyid, String MetExploreJSONFileName){

        MetExploreWsClient meWsClient = new MetExploreWsClient();

        String mapping = meWsClient.getPathwayMappings(studyid);

        try {

            FileUtil.String2File(mapping, MetExploreJSONFileName, false);

        } catch (IOException e) {

            e.printStackTrace();

        }

        return mapping;

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
