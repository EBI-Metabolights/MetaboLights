/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-21
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

package uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel;

import javax.persistence.*;

/**
 * B is a business model entity
 * User: conesa
 * Date: 21/01/15
 * Time: 08:53
 */
@MappedSuperclass
public abstract class DataModel <B>{

	// Instance variables
	@Transient
	protected B businessModelEntity;

	protected Long id;

	@Transient
	protected String table;

	// Abstract methoods
	protected abstract void setBusinessModelId(Long id);
	protected abstract void businessModelToDataModel();
	public abstract B dataModelToBusinessModel();

	// Getter and setters
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId(){
		return id;
	}

	public void setId(Long newId){

		if (businessModelEntity != null) setBusinessModelId(newId);
		id =newId;
	}

	@Transient
	public B getBussinesModelEntity() {
		return businessModelEntity;
	}

	public void setBussinesModelEntity(B businessModelEntity) {
		this.businessModelEntity = businessModelEntity;
		if (businessModelEntity != null) businessModelToDataModel();
	}

//	protected Collection<DataModel<B>> convertBussinesModelCollection(Collection<B> bussinesCollection){
//
//		ArrayList<DataModel<B>> collection = new ArrayList<DataModel<B>>();
//
//		for (bussinesItem:bussinesCollection){
//
//			DataModel<B> dataModel =
//		}
//
//
//
//	}
}
