/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/28/14 4:50 PM
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

package uk.ac.ebi.metabolights.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * User: conesa
 * Date: 28/04/2014
 * Time: 14:50
 */
@Entity
@Table(name = "ML_STUDY_XREF_TYPE")
public class MetaboLigthsStudyIdXRefType {

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
	@Id
	@Column(name="ID")
	@NotEmpty
	private String id;

	@Column(name="NAME")
	@NotEmpty
	private String name;

	@Column
	private String pattern_url;

	@Column String regx_url;

	public MetaboLigthsStudyIdXRefType(){};

	public String getId() {
		return id;
	}

	public void setId(String submittedId) {
		this.id = submittedId;
	}

	public String getName() {
		return name;
	}

	public void setName(String studyAcc) {
		this.name = studyAcc;
	}

	public String getPattern_url() {
		return pattern_url;
	}

	public void setPattern_url(String pattern_url) {
		this.pattern_url = pattern_url;
	}

	public String getRegx_url() {
		return regx_url;
	}

	public void setRegx_url(String regx_url) {
		this.regx_url = regx_url;
	}


}
