package uk.ac.ebi.metabolights.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jose4j.json.internal.json_simple.JSONObject;
import uk.ac.ebi.metabolights.utils.json.LabsUtils;

import java.sql.Timestamp;


public class MLLJob {
    private String MLLProjectId;
    private String JobId;
    private String Status;
    private JSONObject Info;
    private Timestamp CreatedAt;
    private Timestamp UpdatedAt;
    private boolean hide;

    public String getMLLProjectId() { return MLLProjectId; }

    public void setMLLProjectId(String MLLProjectId) { this.MLLProjectId = MLLProjectId; }

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

    public boolean isHide() { return hide; }

    public void setHide(boolean hide) { this.hide = hide; }

    public MLLJob(){}

    public MLLJob(String mllProjectId, String id, String status, JSONObject info){
        this.MLLProjectId = mllProjectId;
        this.JobId = id;
        this.Status = status;
        this.Info = info;
        this.CreatedAt = LabsUtils.getCurrentTimeStamp();
        this.UpdatedAt = LabsUtils.getCurrentTimeStamp();
        this.hide = false;
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