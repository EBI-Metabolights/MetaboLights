/*
 * EBI MetaboLights Project - 2013.
 *
 * File: MetaboLightsSubmittedId.java
 *
 * Last modified: 2/26/13 12:10 PM
 * Modified by:   kenneth
 *
 * European Bioinformatics Institute, Wellcome Trust Genome Campus, Hinxton, Cambridgeshire, CB10 1SD, UK.
 */

package uk.ac.ebi.metabolights.model;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 26/02/2013
 * Time: 12:10
 */

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ML_SUBMITTED_STUDY_ID")
public class MetaboLightsSubmittedId {
    @Id
    @Column(name="SUBMITTED_ID")
    @NotEmpty
    private String submittedId;

    @Column(name="STUDY_ACC")
    @NotEmpty
    private String studyAcc;

    public MetaboLightsSubmittedId(){};

    public MetaboLightsSubmittedId(String oldId, String newId){
        setSubmittedId(oldId);
        setStudyAcc(newId);
    }

    public String getSubmittedId() {
        return submittedId;
    }

    public void setSubmittedId(String submittedId) {
        this.submittedId = submittedId;
    }

    public String getStudyAcc() {
        return studyAcc;
    }

    public void setStudyAcc(String studyAcc) {
        this.studyAcc = studyAcc;
    }
}
