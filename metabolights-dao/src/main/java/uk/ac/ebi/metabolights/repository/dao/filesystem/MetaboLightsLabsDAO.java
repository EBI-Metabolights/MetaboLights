package uk.ac.ebi.metabolights.repository.dao.filesystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.utils.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by venkata on 22/08/2016.
 */
public class MetaboLightsLabsDAO {

    private final static Logger logger = LoggerFactory.getLogger(MetaboLightsLabsDAO.class.getName());

    public String getWorkbenchSettings(User user, String workbenchLocation){

        String workbenchSettingsFile = workbenchLocation + File.separator + "settings.json";

        String workbenchSettings = "";

        if (checkFileExists(workbenchSettingsFile)){

            try {

                workbenchSettings = FileUtil.file2String(workbenchSettingsFile);

            } catch (IOException e) {

                e.printStackTrace();

            }

            logger.info(" MetaboLights Settings File Name - Found: " + workbenchSettingsFile);

        }else{

            return generateMetaboLightsSettingsFile(user, workbenchLocation, workbenchSettingsFile);

        }

        return workbenchSettings;
    }

    private String generateMetaboLightsSettingsFile(User user,String workbenchLocation, String workbenchSettingsFile){

        ObjectWriter ow = new ObjectMapper().writer();

        String settings = null;

        try {

            settings = ow.writeValueAsString(user);

        } catch (JsonProcessingException e) {

            e.printStackTrace();

        }

        try {

            File dir = new File( workbenchLocation );
            if (!dir.exists())
                dir.mkdirs();

            FileUtil.String2File(settings, workbenchSettingsFile, false);

        } catch (IOException e) {

            e.printStackTrace();

        }

        return settings;

    }


    private Boolean checkFileExists(String fileName){

        if (fileName == null || fileName.isEmpty())
            return false;        // No filename given

        File f = new File(fileName);

        if(f.exists())
            return true;        // Found the file

        return false;           // Did not find the file

    }
}
