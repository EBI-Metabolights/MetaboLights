package uk.ac.ebi.metabolights.referencelayer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("ResultList")
public class ResultListModel {
    private ResultModel[] result;

    public ResultModel[] getResult() {
        return result;
    }

    public void setResult(ResultModel[] result) {
        this.result = result;
    }
}
