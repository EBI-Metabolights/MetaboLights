package uk.ac.ebi.metabolights.repository.dao.filesystem;

import java.io.File;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import java.io.IOException;
import java.io.BufferedReader;
import org.slf4j.LoggerFactory;
import java.io.InputStreamReader;
import uk.ac.ebi.metabolights.repository.model.*;
import uk.ac.ebi.metabolights.repository.utils.FileUtil;
import uk.ac.ebi.metabolights.repository.utils.LabsUtils;


/**
 * Created by venkata on 11/10/2016.
 */
public class MetaboLightsLabsProjectDAO {

    private final static Logger logger = LoggerFactory.getLogger(MetaboLightsLabsProjectDAO.class.getName());

    private MLLProject mllProject = null;

    public MetaboLightsLabsProjectDAO(User user, String projectId, String root){

        MLLWorkSpace mllWorkSpace = (new MetaboLightsLabsWorkspaceDAO(user, root)).getMllWorkSpace();

        mllProject = mllWorkSpace.getProject(projectId);

    }

    public MetaboLightsLabsProjectDAO(MLLWorkSpace workSpace){

        MLLProject project = new MLLProject(workSpace);
        mllProject = project;
    }

    public MetaboLightsLabsProjectDAO(MLLWorkSpace workSpace, String projectId){

        mllProject = workSpace.getProject(projectId);

    }

    public MetaboLightsLabsProjectDAO(MLLWorkSpace mllWorkSpace, String title, String description, Study study){

        mllProject = new MLLProject(mllWorkSpace, title, description);

        if (study != null) {
            mllProject.setBusy(true);
            mllProject.save();
        }

        if (study != null) {

            String sourceFolder = study.getStudyLocation();
            String destinationFolder = mllProject.getProjectLocation();

            FileUtil.copyFiles(sourceFolder, destinationFolder);

            mllProject.setBusy(false);
            mllProject.save();

            mllWorkSpace.appendOrUpdateProject(mllProject);

            mllWorkSpace.save();

        }

        String s;
        Process p;

        try {
            String[] commands = {"ssh", "ebi-004", "/nfs/www-prod/web_hx2/cm/metabolights/scripts/priv_ftp_sync_step1.sh -s dev"};
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

    }


    public MLLProject getMllProject() {
        return mllProject;
    }

    public void setMllProject(MLLProject mllProject) {
        this.mllProject = mllProject;
    }


}
