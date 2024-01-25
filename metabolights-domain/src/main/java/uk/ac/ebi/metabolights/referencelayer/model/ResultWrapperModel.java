package uk.ac.ebi.metabolights.referencelayer.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;


@JsonTypeName("ResultWrapper")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultWrapperModel {

    private String version;

    private Integer hitCount;

    private ResultListModel resultList;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getHitCount() {
        return hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }

    public ResultListModel getResultList() {
        return resultList;
    }

    public void setResultList(ResultListModel resultList) {
        this.resultList = resultList;
    }
}
