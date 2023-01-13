
package uk.ac.ebi.metabolights.referencelayer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.StudyFactor;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.utils.json.NumberUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Lite Study to be used as a search result element.
 * User: felix
 * Date: 10/01/23
 * Time: 09:38
 */

@JsonTypeName("LiteStudy")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Study.class, name = "Study")

})
@JsonIgnoreProperties(ignoreUnknown = true)
public class LiteStudyModel extends BaseModel {

    public enum StudyStatus {
        SUBMITTED("Submitted", "The study is private to you only. You are able to make any changes, including deletion of the study. This submission will have to pass our initial automated validation checks before we can start our curation."),
        INCURATION("In curation", "At this stage you cannot make any changes. Be advised that further information may be required, and we may contact you to obtain this"),
        INREVIEW("In review", "Approved by our curation team. Ready to be shared with journals and reviewers."),
        PUBLIC("Public", "Anyone can view and download your study. It is searchable and will be exported to other search engines, such as http://metabolomexchange.org/"),
        DORMANT("Dormant","Deletion has been requested for this study, or the study has been abandoned by the submitter");
        private final String descriptiveName;

        public String getDescription() {
            return description;
        }

        public String getDescriptiveName() {
            return descriptiveName;
        }

        private final String description;

        StudyStatus(String descriptiveName, String description) {
            this.descriptiveName = descriptiveName;
            this.description = description;
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(LiteStudyModel.class);

    private String studyIdentifier;
    private String title;

    private String studyDescription;

    // Database fields
    private StudyStatus studyStatus = StudyStatus.SUBMITTED;

    // Dates
    private Date studyPublicReleaseDate;
    private Date updateDate;
    private Date studySubmissionDate;

    private String obfuscationCode = java.util.UUID.randomUUID().toString();
    private BigDecimal studySize = new BigDecimal(0);


    // Collections
    private Collection<StudyFactor> factors;
    @JsonProperty
    private List<User> users = new ArrayList<>();
    private ObservableList<User> usersObserver = FXCollections.observableList(users);

    private List<String> isatabErrorMessages = new ArrayList<>();


    private String studyHumanReadable = "";

    public String getStudyIdentifier() {
        return studyIdentifier;
    }

    public void setStudyIdentifier(String studyIdentifier) {
        this.studyIdentifier = studyIdentifier;
    }

    public Date getStudyPublicReleaseDate() {
        return studyPublicReleaseDate;
    }

    public void setStudyPublicReleaseDate(Date studyPublicReleaseDate) {


        this.studyPublicReleaseDate = studyPublicReleaseDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getStudySubmissionDate() {
        return studySubmissionDate;
    }

    public void setStudySubmissionDate(Date studySubmissionDate) {
        this.studySubmissionDate = studySubmissionDate;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPublicStudy() {
        return studyStatus.equals(StudyStatus.PUBLIC);
    }

    public StudyStatus getStudyStatus() {
        return studyStatus;
    }

    public void setStudyStatus(StudyStatus studyStatus) {
        this.studyStatus = studyStatus;
    }

    public Collection<StudyFactor> getFactors() {
        return factors;
    }

    public void setFactors(Collection<StudyFactor> factors) {
        this.factors = factors;
    }

    public List<User> getUsers() {
        return usersObserver;
    }

    public void setUsers(List<User> users) {

        // To keep observableList in sync, do not overwrite the users, since the observer will not observe the new list
//		this.users = users;

        usersObserver.clear();
        usersObserver.addAll(users);
    }

    public String getObfuscationCode() {
        return obfuscationCode;
    }

    public void setObfuscationCode(String obfuscationCode) {
        this.obfuscationCode = obfuscationCode;
    }

    public BigDecimal getStudySize() {
        return studySize;
    }

    public void setStudySize(BigDecimal studysize) {
        this.studySize = studysize;
        this.setStudyHumanReadable(NumberUtils.getHumanReadableByteSize(studysize));
    }

    public String getStudyHumanReadable() {
        return studyHumanReadable;
    }

    public void setStudyHumanReadable(String studyHumanReadable) {
        this.studyHumanReadable = studyHumanReadable;
    }
    public List<String> getIsatabErrorMessages() {
        return isatabErrorMessages;
    }

    public void setIsatabErrorMessages(List<String> isatabErrorMessages) {
        this.isatabErrorMessages = isatabErrorMessages;
    }

    public String getStudyDescription() {
        return studyDescription;
    }

    public void setStudyDescription(String studyDescription) {
        this.studyDescription = studyDescription;
    }

}
