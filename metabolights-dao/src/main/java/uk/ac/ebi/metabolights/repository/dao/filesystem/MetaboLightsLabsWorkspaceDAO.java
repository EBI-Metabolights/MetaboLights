package uk.ac.ebi.metabolights.repository.dao.filesystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.model.MLLProject;
import uk.ac.ebi.metabolights.repository.model.MLLUser;
import uk.ac.ebi.metabolights.repository.model.MLLWorkSpace;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by venkata on 22/08/2016.
 */
public class MetaboLightsLabsWorkspaceDAO {

    private final static Logger logger = LoggerFactory.getLogger(MetaboLightsLabsWorkspaceDAO.class.getName());


    /**
     * Returns MLLWorkSpace if the workspace exist
     * @param user
     * @param workspaceLocation
     * @param initialiseWorkspaceFlag
     * @return
     */
    public MLLWorkSpace getWorkSpaceInfo(User user, String workspaceLocation, boolean initialiseWorkspaceFlag){

        String workspaceInfoLocation = workspaceLocation + File.separator + "workspace.info";

        ObjectMapper mapper = new ObjectMapper();

        MLLWorkSpace mllWorkSpace = null;

        if (!checkFileExists(workspaceInfoLocation)){

            if (initialiseWorkspaceFlag) {

                logger.info("Initialising workspace: " + workspaceInfoLocation);

                if (!intialiseWorkspace(user, workspaceLocation)) {

                    return null;

                }

            }

        }

        logger.info("Workspace Found: " + workspaceInfoLocation);

        File json = new File(workspaceInfoLocation);

        try {

            mllWorkSpace = mapper.readValue(json, MLLWorkSpace.class);

        } catch (IOException e) {

            e.printStackTrace();

        }

        return mllWorkSpace;

    }

    private boolean intialiseWorkspace(User user, String workspaceLocation){

        // create user space, add settings, log and info file

        File dir = new File( workspaceLocation );

        if (!dir.exists())
            try {
                FileUtil.createFolder(workspaceLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }

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

            MLLUser mllUser = new MLLUser(user);

            mllWorkSpace.setWorkspaceLocation(workspaceLocation);

            mllWorkSpace.setOwner(mllUser);

            mllWorkSpace.setProjects(new ArrayList<MLLProject>());

            mllWorkSpace.setSettings((new JSONObject()).toJSONString());

            FileUtil.String2File(mllWorkSpace.getAsJSON(), infoFile, false);

            return true;


        } catch (IOException e) {

            e.printStackTrace();

        }

        return false;

    }

    public boolean isProjectExist(String workspaceLocation, String projectId){

        String infoFile = workspaceLocation + File.separator + "workspace.info";

        if (checkFileExists(infoFile)) {

            return false;

        }

        MLLWorkSpace mllWorkSpace = null;

        ObjectMapper mapper = new ObjectMapper();

        File json = new File(infoFile);

        try {

            mllWorkSpace = mapper.readValue(json, MLLWorkSpace.class);

        } catch (IOException e) {

            e.printStackTrace();

        }

        return isProjectExist(mllWorkSpace, projectId);
    }

    public boolean isProjectExist(MLLWorkSpace mllWorkSpace, String projectId){

        List<MLLProject> mllProjects = mllWorkSpace.getProjects();


        for (MLLProject mllProject: mllProjects ){

            if (mllProject.getId().equals(projectId))
                return true;

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
