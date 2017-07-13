package uk.ac.ebi.metabolights.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.utils.json.FileUtils;
import uk.ac.ebi.metabolights.utils.json.LabsUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by venkata on 11/10/2016.
 */
public class MLLWorkSpace {

    protected static final Logger logger = LoggerFactory.getLogger(MLLWorkSpace.class);

    private String Settings;
    private String WorkspaceLocation;
    private MLLUser Owner;
    private Timestamp CreatedAt;
    private Timestamp UpdatedAt;
    private List<MLLProject> Projects;

    public String getSettings() {
        return Settings;
    }

    public void setSettings(String settings) {
        this.Settings = settings;
    }

    public String getWorkspaceLocation() {
        return WorkspaceLocation;
    }

    public void setWorkspaceLocation(String workspaceLocation) {
        this.WorkspaceLocation = workspaceLocation;
    }

    public MLLUser getOwner() {
        return Owner;
    }

    public void setOwner(MLLUser owner) {
        Owner = owner;
    }

    public Timestamp getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt() {
        this.CreatedAt = LabsUtils.getCurrentTimeStamp();
    }

    public void setCreatedAt(Timestamp timestamp) {
        this.CreatedAt = timestamp;
    }

    public Timestamp getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt() {
        this.UpdatedAt = LabsUtils.getCurrentTimeStamp();
    }

    public void setUpdatedAt(Timestamp timestamp) {
        this.UpdatedAt = timestamp;
    }

    public List<MLLProject> getProjects() {
        return Projects;
    }

    public void setProjects(List<MLLProject> projects) {
        this.Projects = projects;
    }

    public MLLWorkSpace() {}

    public MLLWorkSpace(MLLWorkSpace mllWorkSpace) {

        this.Owner = mllWorkSpace.getOwner();
        this.CreatedAt = mllWorkSpace.getCreatedAt();
        this.WorkspaceLocation = mllWorkSpace.getWorkspaceLocation();
        this.UpdatedAt = mllWorkSpace.getUpdatedAt();
        this.Projects = mllWorkSpace.getProjects();
        this.Settings = mllWorkSpace.getSettings();

    }

    public MLLWorkSpace(User mlOwner, String root) {

        this.WorkspaceLocation = root +  File.separator + mlOwner.getApiToken();

        String workspaceInfoLocation = this.WorkspaceLocation + File.separator + "workspace.info";

        if(FileUtils.checkFileExists(workspaceInfoLocation)){

            try {

                File json = new File(workspaceInfoLocation);

                ObjectMapper mapper = new ObjectMapper();

                MLLWorkSpace tempMllWorkSpace = mapper.readValue(json, MLLWorkSpace.class);

                this.Owner = tempMllWorkSpace.getOwner();

                if(this.Owner == null){

                    this.Owner = new MLLUser(mlOwner);

                    this.save();

                }

                this.CreatedAt = tempMllWorkSpace.getCreatedAt();
                this.WorkspaceLocation = tempMllWorkSpace.getWorkspaceLocation();
                this.UpdatedAt = tempMllWorkSpace.getUpdatedAt();
                this.Settings = tempMllWorkSpace.getSettings();
                this.Projects = tempMllWorkSpace.getProjects();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }else{

            this.Owner = new MLLUser(mlOwner);

            this.CreatedAt = LabsUtils.getCurrentTimeStamp();

            this.UpdatedAt = LabsUtils.getCurrentTimeStamp();

            this.setProjects(new ArrayList<MLLProject>());

            this.setSettings((new JSONObject()).toJSONString());

            this.save();

        }
    }

    @JsonIgnore
    public boolean isProjectExist(String projectId){

        for (MLLProject mllProject: Projects){

            if (mllProject.getId().equals(projectId))
                return true;

        }

        return false;
    }

    @JsonIgnore
    public MLLProject getProject(String projectId){

        for (MLLProject mllProject: Projects){

            if (mllProject.getId().equals(projectId))
                return mllProject;

        }

        return null;
    }

    @JsonIgnore
    public String getAsJSON() {

        ObjectMapper mapper = new ObjectMapper();

        try {

            return mapper.writeValueAsString(this) ;

        } catch (JsonProcessingException e) {

            e.printStackTrace();

        }

        return null;
    }

    @JsonIgnore
    private Boolean saveProject(MLLProject mllProject){

        Projects.add(mllProject);

        return this.save();

    }

    @JsonIgnore
    public Boolean save(){

        File dir = new File( WorkspaceLocation );

        if (!dir.exists())
            try {
                this.setCreatedAt();
                FileUtils.createFolder(WorkspaceLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }

        try {

            // create log file
            String logFile = WorkspaceLocation + File.separator + "log.txt";
            FileUtils.String2File("{}", logFile);

            // create settings file
            String settingsFile = WorkspaceLocation + File.separator + "settings.json";
            FileUtils.String2File("{}", settingsFile);

        }catch (IOException e){
            e.printStackTrace();
        }

        this.setUpdatedAt();

        String wsInfoFile = WorkspaceLocation + File.separator + "workspace.info";

        try {

            FileUtils.workspace2File(this, wsInfoFile);

        } catch (IOException e) {

            e.printStackTrace();

        }

        return false;
    }

    @JsonIgnore
    public Boolean appendOrUpdateProject(MLLProject mllProject){

        Boolean projectExists = false;

        for(int i = 0; i < this.Projects.size(); i++){

            MLLProject tempProject = this.Projects.get(i);

            if (tempProject.getId().equals(mllProject.getId())){

                this.Projects.set(i, mllProject);

                projectExists = true;

            }

        }

        if(!projectExists){

            this.Projects.add(mllProject);

        }

        this.save();

        return false;
    }
}
