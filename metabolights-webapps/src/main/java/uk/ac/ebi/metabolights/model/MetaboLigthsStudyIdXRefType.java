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
