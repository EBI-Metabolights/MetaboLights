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

package uk.ac.ebi.metabolights.repository.dao.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;
import uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * User: conesa
 * Date: 21/01/15
 * Time: 14:55
 */
public abstract class DAO<BussinessEntity,dataModel extends DataModel> {

	protected String dataModelName;

	public DAO(){
		this.dataModelName = getDataModelName();
	}

	protected abstract String getDataModelName();


	protected Session getSession() {
		return HibernateUtil.getSessionFactory().openSession();
	}
	protected void closeSession() {
		Session session =  HibernateUtil.getSessionFactory().getCurrentSession();
		session.close();

	}

	public List<BussinessEntity> findBy(String where){

		Session session = getSession();

		Query query = session.createQuery("from " + dataModelName);

		List<dataModel> dataModels = query.list();

		return convertDataModelToBussinessModel(dataModels);



	};

	public BussinessEntity findSingle(String where){

		return findBy(where).iterator().next();

	};

	public  List<BussinessEntity>  findAll(){

		return findBy("");

	};


	private List<BussinessEntity> convertDataModelToBussinessModel(List<dataModel>dataModels){

		List<BussinessEntity> bussinessModels = new ArrayList<BussinessEntity>();

		for (dataModel datamodel: dataModels){
			Object bussinessModel = datamodel.getBussinesModelEntity();
			bussinessModels.add((BussinessEntity) bussinessModel);

		}

		return bussinessModels;
	}
}
