/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/29/14 5:07 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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
