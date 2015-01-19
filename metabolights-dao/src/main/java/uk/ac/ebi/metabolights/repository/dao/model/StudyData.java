/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-16
 * Modified by:   conesa
 *
 *
 * Copyright 2015 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.repository.dao.model;

import uk.ac.ebi.metabolights.repository.model.Study;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User: conesa
 * Date: 16/01/15
 * Time: 11:30
 *
 * This class is meant to represent a row in the study table..it's an intermediate data structure
 * more DB like than the actual Study model.
 */
@Entity
@Table(name = "studies")
public class StudyData  {

	public String acc;
	public String obfuscationcode;
	@Id @GeneratedValue (strategy = GenerationType.IDENTITY)
	public Long id;

	@ManyToMany
	@JoinTable(name="study_user", joinColumns=@JoinColumn(name="studyid"), inverseJoinColumns=@JoinColumn(name="userid"))
	public Set<UserData> users = new HashSet<>();

	public StudyData(){}
	public StudyData(Study study){

		id = study.getId();
		obfuscationcode = study.getObfuscationCode();
		acc = study.getStudyIdentifier();
	}
}
