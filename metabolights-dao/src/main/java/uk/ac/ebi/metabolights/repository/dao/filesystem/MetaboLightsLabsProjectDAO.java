package uk.ac.ebi.metabolights.repository.dao.filesystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.model.MLLProject;
import uk.ac.ebi.metabolights.repository.model.MLLUser;
import uk.ac.ebi.metabolights.repository.model.MLLWorkSpace;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.utils.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

/**
 * Created by venkata on 11/10/2016.
 */
public class MetaboLightsLabsProjectDAO {

    private final static Logger logger = LoggerFactory.getLogger(MetaboLightsLabsProjectDAO.class.getName());

    public MLLProject getMLLProject(String projectId, String workspaceLocation, MLLWorkSpace mllWorkSpace, boolean createNewFlag, String userToken, String asperaUser, String asperaSecret){

        MLLProject mllProject = getProject(mllWorkSpace, projectId);

        if (mllProject != null)
            return  mllProject;

        if (createNewFlag){

            mllProject = createNewProject(mllWorkSpace, workspaceLocation, userToken,asperaUser, asperaSecret);

        }

        return mllProject;

    }

    public MLLProject createMLLProject(String workspaceLocation, MLLWorkSpace mllWorkSpace, String userToken, String asperaUser, String asperaSecret, String title, String description){

        return createNewProject(mllWorkSpace, workspaceLocation, userToken, asperaUser, asperaSecret, title, description);

    }

    private MLLProject getProject( MLLWorkSpace mllWorkSpace, String projectId ){


        List<MLLProject> mllProjects = mllWorkSpace.getProjects();


        for (MLLProject mllProject: mllProjects ){

            if (mllProject.getId().equals(projectId)){

                return mllProject;

            }

        }

        return null;

    }

    private MLLProject createNewProject( MLLWorkSpace mllWorkSpace, String workspaceLocation, String apiToken, String asperaUser , String asperaSecret ) {

        return createNewProject(mllWorkSpace, workspaceLocation, apiToken, asperaUser, asperaSecret, "Default Title", "Lorem ipsum");

    }

    private MLLProject createNewProject( MLLWorkSpace mllWorkSpace, String workspaceLocation, String apiToken, String asperaUser , String asperaSecret, String title, String description ){

        UUID id = UUID.randomUUID();

        MLLProject mllProject = new MLLProject();

        mllProject.setId(id.toString());

        mllProject.setOwner(mllWorkSpace.getOwner());

        mllProject.setProjectLocation(workspaceLocation + File.separator + id.toString());

        mllProject.setSettings("{}");

        mllProject.setAsperaSettings(getAsperaURL(apiToken, id.toString(), asperaUser, asperaSecret));

        mllProject.setTitle(title);

        mllProject.setDescription(description);

        mllWorkSpace.appendProject(mllProject);

        String wsInfoFile = workspaceLocation + File.separator + "workspace.info";

        String projectDir = workspaceLocation + File.separator + id.toString();

        String projectInfoFile = projectDir + File.separator + id.toString() + ".info";

        try {

            FileUtil.createFolder(projectDir);

            FileUtil.String2File(mllProject.getAsJSON(), projectInfoFile, false);
            FileUtil.String2File(mllWorkSpace.getAsJSON(), wsInfoFile, false);

        } catch (IOException e) {

            e.printStackTrace();

            return null;

        }

        String s;
        Process p;

        try {
            String[] commands = {"ssh", "ebi-004",  "/nfs/www-prod/web_hx2/cm/metabolights/scripts/priv_ftp_sync_step1.sh -s dev"};
            p = Runtime.getRuntime().exec(commands);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                logger.info("line: " + s);
            p.waitFor();

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            while ((s = stdError.readLine()) != null) {
                logger.error("line: " + s);
            }


            logger.error("exit: " + p.exitValue());
            p.destroy();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

        return mllProject;

    }

    private String getAsperaURL(String apiToken, String projectId, String asperaUser, String asperaToken){

        return "{ \"asperaURL\" : \""+ apiToken + File.separator + projectId + "\", \"asperaUser\" : \"" + asperaUser + "\",  \"asperaServer\" : \"ah01.ebi.ac.uk\", \"asperaSecret\" : \"" + asperaToken + "\" }";

    }


}
