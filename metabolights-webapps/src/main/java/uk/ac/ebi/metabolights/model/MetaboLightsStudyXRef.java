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

import javax.persistence.*;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true, dynamicInsert=true)
@Table(name = "ML_STUDY_XREF")
public class MetaboLightsStudyXRef {
    @Id
    @Column(name="SUBMITTED_ID")
    @NotEmpty
    private String submittedId;

    @Column(name="STUDY_ACC")
    @NotEmpty
    private String studyAcc;

	@Column(name="IS_ORIGINAL_ID")
	private boolean isOriginalId = true;


	@ManyToOne
	@JoinColumn(name="XREF_TYPE_ID")
	private MetaboLigthsStudyIdXRefType xRefType;



    public MetaboLightsStudyXRef(){};

    public MetaboLightsStudyXRef(String oldId, String newId){
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

	public MetaboLigthsStudyIdXRefType getXRefType() {
		return xRefType;
	}

	public void setXRefType(MetaboLigthsStudyIdXRefType studyIdXRefType) {
		this.xRefType = studyIdXRefType;
	}

	public boolean isOriginalId() {
		return isOriginalId;
	}

	public void setOriginalId(boolean isOriginalId) {
		this.isOriginalId = isOriginalId;
	}
}
