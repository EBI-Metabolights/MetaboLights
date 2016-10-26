package uk.ac.ebi.metabolights.repository.dao.filesystem;

import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.model.MLLWorkSpace;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by venkata on 22/08/2016.
 */
public class MetaboLightsLabsWorkspaceDAO {

    private final static Logger logger = LoggerFactory.getLogger(MetaboLightsLabsWorkspaceDAO.class.getName());

    public String getWorkSpaceInfo(User user, String workspaceLocation){

        String workspaceInfoLocation = workspaceLocation + File.separator + "workspace.info";

        String workspaceInfo = null;

        if (checkFileExists(workspaceInfoLocation)){

            try {

                workspaceInfo = FileUtil.file2String(workspaceInfoLocation);

            } catch (IOException e) {

                e.printStackTrace();

            }

            logger.info("Workspace Info - Found: " + workspaceInfo);

        }else{

            if (intialiseWorkspace(user, workspaceLocation)){

                try {

                    workspaceInfo =  FileUtil.file2String(workspaceInfoLocation);

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

        return workspaceInfo;
    }

    private boolean intialiseWorkspace(User user, String workspaceLocation){

        // create user space, add settings, log and info file

        File dir = new File( workspaceLocation );

        if (!dir.exists())
            dir.mkdirs();

        try {

            // create log file
            String logFile = workspaceLocation + File.separator + "log.txt";
            FileUtil.String2File("{}", logFile, false);

            // create settings file
            String settingsFile = workspaceLocation + File.separator + "settings.json";
            FileUtil.String2File("{}", settingsFile, false);

            // create settings file
            String infoFile = workspaceLocation + File.separator + "workspace.info";

            MLLWorkSpace mllWorkSpace = new MLLWorkSpace();

            mllWorkSpace.setOwner(user);

            mllWorkSpace.setProjects(new ArrayList<MLLWorkSpace>());

            mllWorkSpace.setSettings((new JSONObject()).toJSONString());

            JSONObject info = new JSONObject();

            info.put("owner", mllWorkSpace.getOwner());

            info.put("projects", mllWorkSpace.getProjects());

            FileUtil.String2File(info.toJSONString(), infoFile, false);


        } catch (IOException e) {

            e.printStackTrace();

        }

        return false;

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
