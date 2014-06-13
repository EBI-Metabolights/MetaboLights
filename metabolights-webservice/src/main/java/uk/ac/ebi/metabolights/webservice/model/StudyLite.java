package uk.ac.ebi.metabolights.webservice.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * User: conesa
 * Date: 13/06/2014
 * Time: 14:55
 */
@Entity
@Table(name = "STUDY")
public class StudyLite {

	@Id
	@Column(name="ID")

	private Long studyId;

	@Column(name="ACC")
	@NotEmpty
	private String accesion;

	public Long getStudyId() {
		return studyId;
	}

	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}

	public String getAccesion() {
		return accesion;
	}

	public void setAccesion(String accesion) {
		this.accesion = accesion;
	}
}
