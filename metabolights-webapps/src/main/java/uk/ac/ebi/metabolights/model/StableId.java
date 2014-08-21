/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/24/12 12:40 PM
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

import javax.persistence.*;

/**
 *  Data structure to hold information for the accession number
 *  Just a structure to be populated by hibernate
 *  Properties:
 *  Id: integer
 *  Prefix: String
 * @author conesa
 *
 */
@Entity
@Table(name = "STABLE_ID")

public class StableId {
    @Id
	@Column(name="ID")
	private Integer id;

	@Column(name="SEQ")
    @NotEmpty

	private Integer seq;

    @Column(name="PREFIX")
    @NotEmpty
	private String prefix;
	
	/**
	 * Empty constructor
	 */
	public StableId(){}

	public void setId (Integer id){
		this.id = id;
	}
	public Integer getId (){
		return this.id;
	}
	public void setSeq (Integer seq){
		this.seq = seq;
	}
	public Integer getSeq (){
		return this.seq;
	}
	public void setPrefix(String prefix){
		this.prefix = prefix;
	}
	public String getPrefix(){
		return this.prefix;
	}
}
