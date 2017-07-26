package uk.ac.ebi.metabolights.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jose4j.json.internal.json_simple.JSONObject;
import uk.ac.ebi.metabolights.utils.json.LabsUtils;

import java.sql.Timestamp;


public class MLLJob {
    private String JobId;
    private String Status;
    private JSONObject Info;
    private MLLProject MLLProject;
    private Timestamp CreatedAt;
    private Timestamp UpdatedAt;

    public String getJobId() {
        return JobId;
    }

    public void setJobId(String jobId) {
        JobId = jobId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public JSONObject getInfo() {
        return Info;
    }

    public void setInfo(JSONObject info) {
        this.Info = info;
    }

    public MLLProject getMllProject() {
        return MLLProject;
    }

    public void setMllProject(MLLProject mllProject) {
        this.MLLProject = mllProject;
    }

    public Timestamp getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        CreatedAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        UpdatedAt = updatedAt;
    }


    public MLLJob(){}

    public MLLJob(MLLProject mllProject, String id, String status, JSONObject info){
        this.JobId = id;
        this.MLLProject = mllProject;
        this.Info = info;
        this.CreatedAt = LabsUtils.getCurrentTimeStamp();
        this.UpdatedAt = LabsUtils.getCurrentTimeStamp();
    }

}