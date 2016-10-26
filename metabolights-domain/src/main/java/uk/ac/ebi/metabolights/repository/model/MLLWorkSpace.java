package uk.ac.ebi.metabolights.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by venkata on 11/10/2016.
 */
public class MLLWorkSpace {

    public MLLWorkSpace() { }
    private String Settings;
    private String workspaceLocation;
    private MLLUser Owner;
    private List<MLLProject> projects;

    public String getSettings() {
        return Settings;
    }

    public void setSettings(String settings) {
        Settings = settings;
    }

    public MLLUser getOwner() {
        return Owner;
    }

    public void setOwner(MLLUser owner) {
        Owner = owner;
    }


    public List<MLLProject> getProjects() {
        return projects;
    }

    public void setProjects(List<MLLProject> projects) {
        this.projects = projects;
    }

    public void appendProject(MLLProject mllProject){

        projects.add(mllProject);

    }

    public String getWorkspaceLocation() {
        return workspaceLocation;
    }

    public void setWorkspaceLocation(String workspaceLocation) {
        this.workspaceLocation = workspaceLocation;
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
