package uk.ac.ebi.metabolights.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ebi.metabolights.utils.json.FileUtils;
import uk.ac.ebi.metabolights.utils.json.LabsUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by venkata on 11/10/2016.
 */
public class MLLProject {

    private String Id;
    private String Title;
    private String Description;
    private MLLUser Owner;
    private String Settings;
    private String ProjectLocation;
    private String AsperaSettings;
    private Timestamp CreatedAt;
    private Timestamp UpdatedAt;
    private Boolean IsBusy = false;

    public String getAsperaSettings() {
        return AsperaSettings;
    }

    public void setAsperaSettings(String asperaSettings) {
        this.AsperaSettings = asperaSettings;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public MLLUser getOwner() {
        return Owner;
    }

    public void setOwner(MLLUser owner) {
        Owner = owner;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSettings() {
        return Settings;
    }

    public void setSettings(String settings) {
        this.Settings = settings;
    }

    public String getProjectLocation() {
        return ProjectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        this.ProjectLocation = projectLocation;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Timestamp getCreatedAt() { return CreatedAt; }

    public void setCreatedAt() { this.CreatedAt = LabsUtils.getCurrentTimeStamp(); }

    public void setCreatedAt(Timestamp timestamp) { this.CreatedAt = timestamp; }

    public Timestamp getUpdatedAt() { return UpdatedAt; }

    public void setUpdatedAt() { this.UpdatedAt = LabsUtils.getCurrentTimeStamp(); }

    public void setUpdatedAt(Timestamp timestamp) { this.UpdatedAt = timestamp; }

    public Boolean getBusy() { return IsBusy; }

    public void setBusy(Boolean busy) { IsBusy = busy; }

    public MLLProject(){}

    public MLLProject(MLLWorkSpace mllWorkSpace){

        Id =  UUID.randomUUID().toString();
        Title = "Untitled Project";
        Description = "This is a untitled project";
        Owner = mllWorkSpace.getOwner();
        Settings = "{}";
        ProjectLocation = mllWorkSpace.getWorkspaceLocation() + File.separator + Id;
        AsperaSettings = "{ \"asperaURL\" : \""+ mllWorkSpace.getOwner().getApiToken() + File.separator + Id + "\", \"asperaUser\" : \"\",  \"asperaServer\" : \"ah01.ebi.ac.uk\", \"asperaSecret\" :  \"\" }";
        CreatedAt = LabsUtils.getCurrentTimeStamp();
        UpdatedAt = LabsUtils.getCurrentTimeStamp();
        IsBusy = false;
        this.save();

        mllWorkSpace.getProjects().add(this);
        mllWorkSpace.save();
    }

    public MLLProject(MLLWorkSpace mllWorkSpace, String title, String description){

        Id =  UUID.randomUUID().toString();
        Title = title;
        Description = description;
        Owner = mllWorkSpace.getOwner();
        Settings = "{}";
        ProjectLocation = mllWorkSpace.getWorkspaceLocation() + File.separator + Id;
        AsperaSettings = "{ \"asperaURL\" : \""+ mllWorkSpace.getOwner().getApiToken() + File.separator + Id + "\", \"asperaUser\" : \"\",  \"asperaServer\" : \"ah01.ebi.ac.uk\", \"asperaSecret\" :  \"\" }";
        CreatedAt = LabsUtils.getCurrentTimeStamp();
        UpdatedAt = LabsUtils.getCurrentTimeStamp();
        IsBusy = false;
        this.save();

        mllWorkSpace.getProjects().add(this);
        mllWorkSpace.save();
    }


    @JsonIgnore
    public String getLogs(){

        String LogFile = ProjectLocation + File.separator + Id + ".log";

        String logs = null;

        try {

            logs =  FileUtils.file2String(LogFile);

        } catch (IOException e) {

            e.printStackTrace();

        }

        return logs;
    }



    @JsonIgnore
    public Boolean save(){

        String projectInfoFile = ProjectLocation + File.separator + Id + ".info";

        String LogFile = ProjectLocation + File.separator + Id + ".log";

        File dir = new File( ProjectLocation );

        if (!dir.exists()) {
            try {

                setCreatedAt();
                setUpdatedAt();

                FileUtils.createFolder(ProjectLocation);

                FileUtils.String2File("Created at:" + this.getCreatedAt(), LogFile);

            } catch (IOException e) {

                e.printStackTrace();

            }
        }

        try {

            setUpdatedAt();

            FileUtils.project2File(this, projectInfoFile);

        } catch (IOException e) {

            e.printStackTrace();

        }

        return false;

    }

    @JsonIgnore
    public String delete(List<String> files){

        return FileUtils.deleteFilesFromProject(files, ProjectLocation);

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
}
